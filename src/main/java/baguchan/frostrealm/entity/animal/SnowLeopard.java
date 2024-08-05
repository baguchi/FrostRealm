package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class SnowLeopard extends Animal {
    private static final EntityDimensions BABY_DIMENSIONS = FrostEntities.SNOW_LEOPARD.get().getDimensions().scale(0.5F).withEyeHeight(0.2F);

    private static final Predicate<Entity> AVOID_PLAYERS = (p_28463_) -> {
        return !p_28463_.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(p_28463_);
    };

    public static final Predicate<LivingEntity> LEOPARD_PREY_SELECTOR = p_348295_ -> {
        EntityType<?> entitytype = p_348295_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.GOAT || entitytype == EntityType.RABBIT || entitytype == FrostEntities.SNOWPILE_QUAIL.get();
    };

    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.MUTTON.asItem());

    public final AnimationState eatAnimationState = new AnimationState();

    private int ticksSinceEaten;

    public SnowLeopard(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose p_316516_) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(p_316516_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2F));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.95D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 16.0F, 1.6D, 1.4D, AVOID_PLAYERS::test));
        this.goalSelector.addGoal(7, new SeekShelterEvenBlizzardGoal(this, 1.25D, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Animal.class, false, LEOPARD_PREY_SELECTOR));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return FOOD_ITEMS.test(p_27600_);
    }

    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemstack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
                    this.heal(2);
                    if (!itemstack1.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
                    }

                    if (itemstack1.is(Items.MUTTON)) {
                        itemstack1.shrink(1);
                    }

                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                    this.level().broadcastEntityEvent(this, (byte) 45);
                }
            }

            LivingEntity livingentity = this.getTarget();
        }

        if (this.isSleeping() || this.isImmobile()) {
            this.jumping = false;
            this.xxa = 0.0F;
            this.zza = 0.0F;
        }

        super.aiStep();
    }

    protected void pickUpItem(ItemEntity p_28514_) {
        ItemStack itemstack = p_28514_.getItem();
        if (this.canHoldItem(itemstack)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.dropItemStack(itemstack.split(i - 1));
            }

            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(p_28514_);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(p_28514_, itemstack.getCount());
            p_28514_.discard();
            this.ticksSinceEaten = 0;
        }

    }

    private void spitOutItem(ItemStack p_28602_) {
        if (!p_28602_.isEmpty() && !this.level().isClientSide) {
            ItemEntity itementity = new ItemEntity(this.level(), this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, p_28602_);
            itementity.setPickUpDelay(40);
            itementity.setThrower(this);
            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level().addFreshEntity(itementity);
        }
    }

    private void dropItemStack(ItemStack p_28606_) {
        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), p_28606_);
        this.level().addFreshEntity(itementity);
    }

    public boolean canHoldItem(ItemStack p_28578_) {
        Item item = p_28578_.getItem();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemstack.isEmpty() || this.ticksSinceEaten > 0 && (p_28578_.getFoodProperties(this) != null || item == Items.MUTTON) && p_28578_.getFoodProperties(this) == null && !itemstack.is(Items.MUTTON);
    }

    private boolean canEat(ItemStack p_28598_) {
        return (p_28598_.getFoodProperties(this) != null || p_28598_.is(Items.MUTTON)) && this.getTarget() == null && this.onGround() && !this.isSleeping();
    }

    @Nullable
    @Override
    @javax.annotation.Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return SoundEvents.FOX_SLEEP;
        } else {
            return FrostSounds.LEOPARD_IDLE.get();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return FrostSounds.LEOPARD_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return FrostSounds.LEOPARD_DEATH.get();
    }

    @Override
    public SoundEvent getEatingSound(ItemStack p_21202_) {
        return SoundEvents.STRIDER_EAT;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return FrostEntities.SNOW_LEOPARD.get().create(p_146743_);
    }
}
