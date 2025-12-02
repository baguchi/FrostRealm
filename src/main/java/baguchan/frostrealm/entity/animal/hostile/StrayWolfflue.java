package baguchan.frostrealm.entity.animal.hostile;

import baguchan.frostrealm.api.entity.WolfflueVariant;
import baguchan.frostrealm.data.resource.registries.WolfflueVariants;
import baguchan.frostrealm.entity.animal.AbstractWolfflue;
import baguchan.frostrealm.entity.animal.Wolfflue;
import baguchan.frostrealm.entity.goal.LeapAtTargetWolfflueGoal;
import baguchan.frostrealm.entity.goal.WolfflueBegGoal;
import baguchan.frostrealm.entity.hostile.LesserWarrior;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostEntityDatas;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostTags;
import baguchi.bagus_lib.entity.ISmartJump;
import baguchi.bagus_lib.entity.path.node.SmartNodeEvaluator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariant;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class StrayWolfflue extends AbstractWolfflue {
    public StrayWolfflue(EntityType<? extends StrayWolfflue> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.SAFE_FALL_DISTANCE, 8.0).add(Attributes.FOLLOW_RANGE, 18.0F).add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetWolfflueGoal(this, 2.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.5F, 12.0F, 6.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 0.8F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.goalSelector.addGoal(9, new WolfflueBegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public void addAdditionalSaveData(ValueOutput p_30418_) {
        super.addAdditionalSaveData(p_30418_);
    }

    @Override
    public void readAdditionalSaveData(ValueInput p_30402_) {
        super.readAdditionalSaveData(p_30402_);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (mobSpawnType == EntitySpawnReason.NATURAL || mobSpawnType == EntitySpawnReason.SPAWN_ITEM_USE && serverLevelAccessor.getRandom().nextFloat() < 0.1F) {
            LesserWarrior lesserWarrior = FrostEntities.LESSER_WARRIOR.get().create(this.level(), EntitySpawnReason.JOCKEY);
            if (lesserWarrior != null) {
                lesserWarrior.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                lesserWarrior.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, null);
                lesserWarrior.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.ASTRIUM_SPEAR.asItem()));
                lesserWarrior.startRiding(this, false, false);
            }
        }

        this.setSoundVariant(WolfSoundVariants.pickRandomSoundVariant(this.registryAccess(), this.random));

        this.populateDefaultEquipmentSlots(random, difficultyInstance);
        this.populateDefaultEquipmentEnchantments(serverLevelAccessor, random, difficultyInstance);

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance p_217056_) {
        if (randomSource.nextFloat() < 0.1F) {
            this.setItemSlot(EquipmentSlot.BODY, new ItemStack(FrostItems.WOLFFLUE_GLACIER_BOAR_ARMOR.get()));
        }
    }

    @Override
    public boolean canHoldItem(ItemStack p_28578_) {
        Item item = p_28578_.getItem();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemstack.isEmpty() && this.isFood(p_28578_);
    }

    @Override
    public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
        ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
        Item item = itemstack.getItem();
        this.setPersistenceRequired();
        if (this.isTame()) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                FoodProperties foodproperties = itemstack.get(DataComponents.FOOD);
                float f = foodproperties != null ? (float) foodproperties.nutrition() : 1.0F;
                this.heal(4.0F * f);
                itemstack.consume(1, p_30412_);
                this.gameEvent(GameEvent.EAT); // Neo: add EAT game event
                return InteractionResult.SUCCESS_SERVER;
            } else {
                if (item instanceof DyeItem dyeitem && this.isOwnedBy(p_30412_)) {
                    DyeColor dyecolor = dyeitem.getDyeColor();
                    if (dyecolor != this.getCollarColor()) {
                        this.setCollarColor(dyecolor);
                        itemstack.consume(1, p_30412_);
                        return InteractionResult.SUCCESS;
                    }

                    return super.mobInteract(p_30412_, p_30413_);
                }


                if (itemstack.is(FrostItems.SILVER_MOON) && this.isOwnedBy(p_30412_) && this.getMainHandItem().isEmpty() && !this.isBaby()) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.copyWithCount(1));
                    itemstack.consume(1, p_30412_);
                    this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                    return InteractionResult.SUCCESS;
                }

                if (itemstack.isEmpty() && p_30412_.isSecondaryUseActive() && p_30412_.getMainHandItem().isEmpty() && this.isOwnedBy(p_30412_) && !this.getMainHandItem().isEmpty()) {
                    ItemStack itemstack1 = this.getMainHandItem();
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    if (this.level() instanceof ServerLevel serverLevel) {
                        this.spawnAtLocation(serverLevel, itemstack1);
                    }
                    return InteractionResult.SUCCESS;
                }

                if (this.isEquippableInSlot(itemstack, EquipmentSlot.SADDLE) && this.isOwnedBy(p_30412_) && !this.isBaby()) {
                    return itemstack.interactLivingEntity(p_30412_, this, p_30413_);
                } else if (this.isEquippableInSlot(itemstack, EquipmentSlot.BODY) && this.isOwnedBy(p_30412_) && this.getBodyArmorItem().isEmpty() && !this.isBaby()) {
                    this.setBodyArmorItem(itemstack.copyWithCount(1));
                    this.setGuaranteedDrop(EquipmentSlot.BODY);
                    itemstack.consume(1, p_30412_);
                    return InteractionResult.SUCCESS;
                } /*else if (itemstack.canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHEARS_REMOVE_ARMOR)
                            && this.isOwnedBy(p_30412_)) {
                        itemstack.hurtAndBreak(1, p_30412_, getSlotForHand(p_30413_));
                        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);
                        this.setItemSlot(EquipmentSlot.SADDLE, ItemStack.EMPTY);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            this.spawnAtLocation(serverLevel, Items.SADDLE);
                        }
                        return InteractionResult.SUCCESS;
                    } else if (itemstack.canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHEARS_REMOVE_ARMOR)
                            && this.isOwnedBy(p_30412_)
                            && this.hasArmor()
                            && (!EnchantmentHelper.has(this.getBodyArmorItem(), EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) || p_30412_.isCreative())) {
                        itemstack.hurtAndBreak(1, p_30412_, getSlotForHand(p_30413_));
                        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);
                        ItemStack itemstack1 = this.getBodyArmorItem();
                        this.setBodyArmorItem(ItemStack.EMPTY);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            this.spawnAtLocation(serverLevel, itemstack1);
                        }
                        return InteractionResult.SUCCESS;
                    }*/ else if (!this.getItemBySlot(EquipmentSlot.SADDLE).isEmpty() && !p_30412_.isSecondaryUseActive() && this.isOwnedBy(p_30412_)) {
                    this.doPlayerRide(p_30412_);
                    if (this.isInSittingPose()) {
                        this.setInSittingPose(false);
                    }
                    return InteractionResult.SUCCESS.withoutItem();
                }

                InteractionResult interactionresult = super.mobInteract(p_30412_, p_30413_);
                if (!interactionresult.consumesAction() && this.isOwnedBy(p_30412_)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS.withoutItem();
                }

                return interactionresult;
            }
        } else if (!this.level().isClientSide() && this.isFood(itemstack) && !this.isAngry()) {
            itemstack.consume(1, p_30412_);
            this.tryToTame(p_30412_);
            return InteractionResult.SUCCESS_SERVER;
        } else {
            return super.mobInteract(p_30412_, p_30413_);
        }
    }

    @Override
    protected boolean canDispenserEquipIntoSlot(EquipmentSlot p_371599_) {
        return (p_371599_ == EquipmentSlot.BODY || p_371599_ == EquipmentSlot.SADDLE) && this.isTame() || super.canDispenserEquipIntoSlot(p_371599_);
    }

    @Override
    public boolean canUseSlot(EquipmentSlot p_397737_) {
        return p_397737_ != EquipmentSlot.SADDLE ? super.canUseSlot(p_397737_) : this.isAlive() && !this.isBaby() && this.isTame();
    }

    public void tryToTame(Player p_333736_) {
        if (this.random.nextInt(3) == 0 && !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, p_333736_)) {
            this.tame(p_333736_);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }
    }


    @Override
    public boolean isFood(ItemStack p_30440_) {
        return p_30440_.is(FrostTags.Items.WOLFFLUE_FOODS);
    }

    @Override
    public boolean hasArmor() {
        return !this.getBodyArmorItem().isEmpty();
    }

    @Override
    protected EquipmentSlot sunProtectionSlot() {
        return EquipmentSlot.BODY;
    }

    @Nullable
    public StrayWolfflue getBreedOffspring(ServerLevel p_149088_, AgeableMob p_149089_) {
        return null;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double p_479978_) {
        return true;
    }


    @Override
    public boolean canBeLeashed() {
        return !this.isAngry() && this.isTame();
    }
}
