package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.EatFrostGrassBlockGoal;
import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BushBug extends Animal implements IShearable {
    private static final EntityDataAccessor<Boolean> SHEARABLE = SynchedEntityData.defineId(BushBug.class, EntityDataSerializers.BOOLEAN);

    public static final Ingredient FOOD_ITEMS = Ingredient.of(Tags.Items.SEEDS);
    private int eatAnimationTick;
    private int makingCount;

    public BushBug(EntityType<? extends BushBug> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHEARABLE, true);
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
        return this.readyForShearing();
    }

    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby() && isShearableWithoutConditions();
    }

    public boolean isShearableWithoutConditions() {
        return this.entityData.get(SHEARABLE);
    }

    public void setShearable(boolean shear) {
        this.entityData.set(SHEARABLE, shear);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2F));
        this.goalSelector.addGoal(2, new SeekShelterEvenBlizzardGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.95D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new EatFrostGrassBlockGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public void handleEntityEvent(byte p_29814_) {
        if (p_29814_ == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(p_29814_);
        }
    }

    public float getHeadEatScale(float p_29881_) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? (float) this.eatAnimationTick / 4 : ((float) 4 / (this.eatAnimationTick - 36));
        }
    }

    @Override
    public void ate() {
        super.ate();
        this.setShearable(true);
        if (this.isBaby()) {
            this.ageUp(60);
        }
        this.makingCount += 1;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        } else {
            if (this.makingCount >= 3) {
                this.makingCount = 0;
                this.spawnAtLocation(new ItemStack(Items.STRING));
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.ARMOR, 2D).add(Attributes.MOVEMENT_SPEED, 0.18D);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return FOOD_ITEMS.test(p_27600_);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return FrostEntities.BUSH_BUG.get().create(p_146743_);
    }

    @javax.annotation.Nonnull
    @Override
    public java.util.List<ItemStack> onSheared(@javax.annotation.Nullable Player player, @javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {

        world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        this.gameEvent(GameEvent.SHEAR, player);
        if (!world.isClientSide) {
            this.setShearable(false);
            this.makingCount = 0;
            int i = 1;

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int j = 0; j < i; ++j) {

                items.add(new ItemStack(FrostBlocks.FROSTROOT_LEAVES.get()));
            }
            return items;
        }

        return java.util.Collections.emptyList();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_27576_) {
        super.readAdditionalSaveData(p_27576_);

        this.setShearable(p_27576_.getBoolean("Shearable"));
        this.makingCount = p_27576_.getInt("StringCount");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_27587_) {
        super.addAdditionalSaveData(p_27587_);
        p_27587_.putBoolean("Shearable", this.isShearableWithoutConditions());
        p_27587_.putInt("StringCount", this.makingCount);
    }
}
