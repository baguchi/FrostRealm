package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.api.entity.WolfflueVariant;
import baguchan.frostrealm.data.resource.registries.WolfflueVariants;
import baguchan.frostrealm.entity.goal.LeapAtTargetWolfflueGoal;
import baguchan.frostrealm.entity.goal.WolfflueBegGoal;
import baguchan.frostrealm.item.WolfflueArmorItem;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostEntityDatas;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class Wolfflue extends TamableAnimal implements NeutralMob, VariantHolder<Holder<WolfflueVariant>> {
    private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(Wolfflue.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Wolfflue.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Wolfflue.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Holder<WolfflueVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Wolfflue.class, FrostEntityDatas.WOLFFLUE_VARIANT.get());


    public static final Predicate<LivingEntity> PREY_SELECTOR = p_348295_ -> {
        EntityType<?> entitytype = p_348295_.getType();
        return entitytype == FrostEntities.CRYSTAL_FOX || entitytype == FrostEntities.SNOWPILE_QUAIL || entitytype == EntityType.FOX;
    };
    private static final float START_HEALTH = 20.0F;
    private static final float TAME_HEALTH = 80.0F;
    private static final float ARMOR_REPAIR_UNIT = 0.125F;
    private float interestedAngle;
    private float interestedAngleO;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;

    public final AnimationState idleSitAnimationState = new AnimationState();
    public final AnimationState idleSit2AnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int idleAnimationRemainTick = 0;

    private float runningScale;
    private float runningScaleO;

    public Wolfflue(EntityType<? extends Wolfflue> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_312373_) {
        if (this.level().isClientSide() && DATA_POSE.equals(p_312373_)) {
            this.stopAllAnimation();
            Pose pose = this.getPose();
            switch (pose) {
                case LONG_JUMPING:
                    this.jumpAnimationState.startIfStopped(this.tickCount);
                    break;
            }
        }

        super.onSyncedDataUpdated(p_312373_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetWolfflueGoal(this, 2.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.goalSelector.addGoal(9, new WolfflueBegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.SAFE_FALL_DISTANCE, 6.0).add(Attributes.FOLLOW_RANGE, 18.0F).add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(80) + 80;
            if (this.isInSittingPose()) {
                if (this.random.nextBoolean()) {
                    this.stopIdleAnimation();
                    this.idleSitAnimationState.start(this.tickCount);
                    this.idleAnimationRemainTick = 20 * 2;
                } else {
                    this.stopIdleAnimation();
                    this.idleSit2AnimationState.start(this.tickCount);
                    this.idleAnimationRemainTick = (int) (20 * 1.75F);
                }
            }
        } else {
            this.idleAnimationTimeout--;
        }

        if (this.idleAnimationRemainTick <= 0) {
            this.stopIdleAnimation();
        } else {
            this.idleAnimationRemainTick--;
        }
        if (!this.isInSittingPose()) {
            this.stopIdleAnimation();
        }

        runningScaleO = runningScale;
        if (this.isMoving()) {

            if (isDashing()) {
                runningScale = Mth.clamp(runningScale + 0.1F, 0, 1);
            } else {
                runningScale = Mth.clamp(runningScale - 0.1F, 0, 1);
            }
        } else {
            //idleAnimationState.startIfStopped(this.tickCount);
        }
    }


    private boolean isDashing() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 0.02D;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRunningScale(float p_29570_) {
        return Mth.lerp(p_29570_, this.runningScaleO, this.runningScale);
    }

    protected void stopIdleAnimation() {
        if (this.idleSitAnimationState.isStarted()) {
            this.idleSitAnimationState.stop();
        }
        if (this.idleSit2AnimationState.isStarted()) {
            this.idleSit2AnimationState.stop();
        }
    }

    protected void stopAllAnimation() {
        this.jumpAnimationState.stop();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326027_) {
        super.defineSynchedData(p_326027_);
        p_326027_.define(DATA_INTERESTED_ID, false);
        p_326027_.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
        p_326027_.define(DATA_REMAINING_ANGER_TIME, 0);
        RegistryAccess registryaccess = this.registryAccess();
        Registry<WolfflueVariant> registry = registryaccess.registryOrThrow(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY);
        p_326027_.define(DATA_VARIANT_ID, registry.getHolder(WolfflueVariants.DEFAULT).or(registry::getAny).orElseThrow());

    }

    public ResourceLocation getTexture() {
        WolfflueVariant wolfvariant = this.getVariant().value();
        if (this.isTame()) {
            return wolfvariant.wildTexture();
        } else {
            return this.isAngry() ? wolfvariant.angryTexture() : wolfvariant.wildTexture();
        }
    }

    public Holder<WolfflueVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<WolfflueVariant> p_332777_) {
        this.entityData.set(DATA_VARIANT_ID, p_332777_);
    }


    @Override
    protected void playStepSound(BlockPos p_30415_, BlockState p_30416_) {
        this.playSound(SoundEvents.WOLF_STEP, 0.5F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_30418_) {
        super.addAdditionalSaveData(p_30418_);
        p_30418_.putByte("CollarColor", (byte) this.getCollarColor().getId());
        this.getVariant().unwrapKey().ifPresent(p_344339_ -> p_30418_.putString("variant", p_344339_.location().toString()));


        this.addPersistentAngerSaveData(p_30418_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_30402_) {
        super.readAdditionalSaveData(p_30402_);
        if (p_30402_.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(p_30402_.getInt("CollarColor")));
        }

        Optional.ofNullable(ResourceLocation.tryParse(p_30402_.getString("variant")))
                .map(p_332608_ -> ResourceKey.create(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY, p_332608_))
                .flatMap(p_352803_ -> this.registryAccess().registryOrThrow(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY).getHolder((ResourceKey<WolfflueVariant>) p_352803_))
                .ifPresent(this::setVariant);

        this.readPersistentAngerSaveData(this.level(), p_30402_);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {

        Holder<Biome> holder = serverLevelAccessor.getBiome(this.blockPosition());
        Holder<WolfflueVariant> holder1;
        if (spawnGroupData instanceof WolffluePackData wolf$wolfpackdata) {
            holder1 = wolf$wolfpackdata.type;
        } else {
            holder1 = WolfflueVariants.getSpawnVariant(this.registryAccess(), holder);
            spawnGroupData = new WolffluePackData(holder1);
        }

        this.setVariant(holder1);
        this.populateDefaultEquipmentSlots(random, difficultyInstance);
        this.populateDefaultEquipmentEnchantments(serverLevelAccessor, random, difficultyInstance);

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource p_217055_, DifficultyInstance p_217056_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.SILVER_MOON.get()));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.WOLF_GROWL;
        } else if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 40.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_30424_) {
        return this.canArmorAbsorb(p_30424_) ? SoundEvents.WOLF_ARMOR_DAMAGE : SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 1.2F;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.interestedAngleO = this.interestedAngle;
            if (this.isInterested()) {
                this.interestedAngle = this.interestedAngle + (1.0F - this.interestedAngle) * 0.4F;
            } else {
                this.interestedAngle = this.interestedAngle + (0.0F - this.interestedAngle) * 0.4F;
            }
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public float getHeadRollAngle(float p_30449_) {
        return Mth.lerp(p_30449_, this.interestedAngleO, this.interestedAngle) * 0.15F * (float) Math.PI;
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    @Override
    public boolean hurt(DamageSource p_30386_, float p_30387_) {
        if (this.isInvulnerableTo(p_30386_)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }

            return super.hurt(p_30386_, p_30387_);
        }
    }

    @Override
    public boolean canUseSlot(EquipmentSlot p_348657_) {
        return true;
    }

    @Override
    protected void actuallyHurt(DamageSource p_331935_, float p_330695_) {
        if (!this.canArmorAbsorb(p_331935_)) {
            super.actuallyHurt(p_331935_, p_330695_);
        } else {
            ItemStack itemstack = this.getBodyArmorItem();
            int i = itemstack.getDamageValue();
            int j = itemstack.getMaxDamage();
            itemstack.hurtAndBreak(Mth.ceil(p_330695_), this, EquipmentSlot.BODY);
            if (Crackiness.WOLF_ARMOR.byDamage(i, j) != Crackiness.WOLF_ARMOR.byDamage(this.getBodyArmorItem())) {
                this.playSound(SoundEvents.WOLF_ARMOR_CRACK);
                if (this.level() instanceof ServerLevel serverlevel) {
                    serverlevel.sendParticles(
                            new ItemParticleOption(ParticleTypes.ITEM, Items.ARMADILLO_SCUTE.getDefaultInstance()),
                            this.getX(),
                            this.getY() + 1.0,
                            this.getZ(),
                            20,
                            0.2,
                            0.1,
                            0.2,
                            0.1
                    );
                }
            }
        }
    }

    private boolean canArmorAbsorb(DamageSource p_331524_) {
        return false;
    }

    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80.0);
            this.setHealth(80.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0);
        }
    }

    @Override
    protected void hurtArmor(DamageSource p_332118_, float p_330593_) {
        this.doHurtEquipment(p_332118_, p_330593_, EquipmentSlot.BODY);
    }

    @Override
    public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
        ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
        Item item = itemstack.getItem();
        if (!this.level().isClientSide || this.isBaby() && this.isFood(itemstack)) {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    FoodProperties foodproperties = itemstack.getFoodProperties(this);
                    float f = foodproperties != null ? (float) foodproperties.nutrition() : 1.0F;
                    this.heal(4.0F * f);
                    itemstack.consume(1, p_30412_);
                    this.gameEvent(GameEvent.EAT); // Neo: add EAT game event
                    return InteractionResult.sidedSuccess(this.level().isClientSide());
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

                    if (itemstack.is(FrostItems.WOLFFLUE_ASTRIUM_ARMOR.get()) && this.isOwnedBy(p_30412_) && this.getBodyArmorItem().isEmpty() && !this.isBaby()) {
                        this.setBodyArmorItem(itemstack.copyWithCount(1));
                        itemstack.consume(1, p_30412_);
                        return InteractionResult.SUCCESS;
                    } else if (itemstack.canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHEARS_REMOVE_ARMOR)
                            && this.isOwnedBy(p_30412_)
                            && this.hasArmor()
                            && (!EnchantmentHelper.has(this.getBodyArmorItem(), EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) || p_30412_.isCreative())) {
                        itemstack.hurtAndBreak(1, p_30412_, getSlotForHand(p_30413_));
                        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);
                        ItemStack itemstack1 = this.getBodyArmorItem();
                        this.setBodyArmorItem(ItemStack.EMPTY);
                        this.spawnAtLocation(itemstack1);
                        return InteractionResult.SUCCESS;
                    } else {
                    InteractionResult interactionresult = super.mobInteract(p_30412_, p_30413_);
                    if (!interactionresult.consumesAction() && this.isOwnedBy(p_30412_)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return InteractionResult.SUCCESS_NO_ITEM_USED;
                    } else {
                        return interactionresult;
                    }
                    }
                }
            } else if (this.isFood(itemstack) && !this.isAngry()) {
                itemstack.consume(1, p_30412_);
                this.tryToTame(p_30412_);
                return InteractionResult.SUCCESS;
            } else {
                return super.mobInteract(p_30412_, p_30413_);
            }
        } else {
            boolean flag = this.isOwnedBy(p_30412_) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
    }

    private void tryToTame(Player p_333736_) {
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
    public void handleEntityEvent(byte p_30379_) {
        if (p_30379_ == 8) {
        } else if (p_30379_ == 56) {
        } else {
            super.handleEntityEvent(p_30379_);
        }
    }

    public float getTailAngle() {
        if (this.isAngry() && !this.isTame()) {
            return 1.5393804F;
        } else if (this.isTame()) {
            float f = this.getMaxHealth();
            float f1 = (f - this.getHealth()) / f;
            return 0.2F - (f1 * 0.4F) * (float) Math.PI;
        } else {
            return (float) (0.0F);
        }
    }

    @Override
    public boolean isFood(ItemStack p_30440_) {
        return p_30440_.is(FrostTags.Items.WOLFFLUE_FOODS);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_30404_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
        this.persistentAngerTarget = p_30400_;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
    }

    public boolean hasArmor() {
        return this.getBodyArmorItem().getItem() instanceof WolfflueArmorItem;
    }

    private void setCollarColor(DyeColor p_30398_) {
        this.entityData.set(DATA_COLLAR_COLOR, p_30398_.getId());
    }

    @Nullable
    public Wolfflue getBreedOffspring(ServerLevel p_149088_, AgeableMob p_149089_) {
        Wolfflue wolf = FrostEntities.WOLFFLUE.get().create(p_149088_);
        if (wolf != null && p_149089_ instanceof Wolfflue wolf1) {
            if (this.random.nextBoolean()) {
                wolf.setVariant(this.getVariant());
            } else {
                wolf.setVariant(wolf1.getVariant());
            }
            if (this.isTame()) {
                wolf.setOwnerUUID(this.getOwnerUUID());
                wolf.setTame(true, true);
                if (this.random.nextBoolean()) {
                    wolf.setCollarColor(this.getCollarColor());
                } else {
                    wolf.setCollarColor(wolf1.getCollarColor());
                }
            }
        }

        return wolf;
    }

    public void setIsInterested(boolean p_30445_) {
        this.entityData.set(DATA_INTERESTED_ID, p_30445_);
    }

    @Override
    public boolean canMate(Animal p_30392_) {
        if (p_30392_ == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(p_30392_ instanceof Wolfflue wolf)) {
            return false;
        } else if (!wolf.isTame()) {
            return false;
        } else {
            return !wolf.isInSittingPose() && this.isInLove() && wolf.isInLove();
        }
    }

    public boolean isInterested() {
        return this.entityData.get(DATA_INTERESTED_ID);
    }

    @Override
    public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
        if (p_30389_ instanceof Creeper || p_30389_ instanceof Ghast || p_30389_ instanceof ArmorStand) {
            return false;
        } else if (p_30389_ instanceof Wolfflue wolf) {
            return !wolf.isTame() || wolf.getOwner() != p_30390_;
        } else {
            if (p_30389_ instanceof Player player && p_30390_ instanceof Player player1 && !player1.canHarmPlayer(player)) {
                return false;
            }

            if (p_30389_ instanceof AbstractHorse abstracthorse && abstracthorse.isTamed()) {
                return false;
            }

            return !(p_30389_ instanceof TamableAnimal tamableanimal) || !tamableanimal.isTame();
        }
    }

    @Override
    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public static boolean checkWolfSpawnRules(
            EntityType<Wolfflue> p_218292_, LevelAccessor p_218293_, MobSpawnType p_218294_, BlockPos p_218295_, RandomSource p_218296_
    ) {
        return p_218293_.getBlockState(p_218295_.below()).is(FrostTags.Blocks.ANIMAL_SPAWNABLE) && isBrightEnoughToSpawn(p_218293_, p_218295_);
    }

    private class WolffluePackData extends AgeableMob.AgeableMobGroupData {
        public final Holder<WolfflueVariant> type;

        public WolffluePackData(Holder<WolfflueVariant> p_332792_) {
            super(false);
            this.type = p_332792_;
        }
    }
}
