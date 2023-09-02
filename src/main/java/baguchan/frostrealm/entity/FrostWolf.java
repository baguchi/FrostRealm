package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class FrostWolf extends TamableAnimal implements NeutralMob, PlayerRideableJumping, RiderShieldingMount {
	private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(FrostWolf.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(FrostWolf.class, EntityDataSerializers.INT);
	public static final Predicate<LivingEntity> PREY_SELECTOR = (p_289448_) -> {
		EntityType<?> entitytype = p_289448_.getType();
		return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.FOX;
	};
	public static final Predicate<LivingEntity> FROST_PREY_SELECTOR = (p_30437_) -> {
		EntityType<?> entitytype = p_30437_.getType();
		return entitytype == FrostEntities.SNOWPILE_QUAIL.get() || entitytype == FrostEntities.CRYSTAL_FOX.get();
	};

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	@Nullable
	private UUID persistentAngerTarget;

	protected float playerJumpPendingScale;

	protected boolean isJumping;

	public FrostWolf(EntityType<? extends FrostWolf> p_30369_, Level p_30370_) {
		super(p_30369_, p_30370_);
		this.setMaxUpStep(1.0F);
		this.setTame(false);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
		this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));

		this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, FROST_PREY_SELECTOR));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
		this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
	}


	protected void playStepSound(BlockPos p_30415_, BlockState p_30416_) {
		this.playSound(SoundEvents.WOLF_STEP, 0.5F, 1.0F);
	}

	public void addAdditionalSaveData(CompoundTag p_30418_) {
		super.addAdditionalSaveData(p_30418_);
		p_30418_.putByte("CollarColor", (byte) this.getCollarColor().getId());
		this.addPersistentAngerSaveData(p_30418_);
	}

	public void readAdditionalSaveData(CompoundTag p_30402_) {
		super.readAdditionalSaveData(p_30402_);
		if (p_30402_.contains("CollarColor", 99)) {
			this.setCollarColor(DyeColor.byId(p_30402_.getInt("CollarColor")));
		}

		this.readPersistentAngerSaveData(this.level(), p_30402_);
	}

	protected SoundEvent getAmbientSound() {
		if (this.isAngry()) {
			return SoundEvents.WOLF_GROWL;
		} else if (this.random.nextInt(3) == 0) {
			return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
		} else {
			return SoundEvents.WOLF_AMBIENT;
		}
	}

	protected SoundEvent getHurtSound(DamageSource p_30424_) {
		return SoundEvents.WOLF_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.WOLF_DEATH;
	}

	protected float getSoundVolume() {
		return 1.5F;
	}

	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}

	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 18.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	public static boolean checkFrostWolfSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	@Override
	public FrostWolf getBreedOffspring(ServerLevel p_149088_, AgeableMob p_149089_) {
		FrostWolf wolf = FrostEntities.FROST_WOLF.get().create(p_149088_);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			wolf.setOwnerUUID(uuid);
			wolf.setTame(true);
		}

		return wolf;
	}

	public boolean hurt(DamageSource p_30386_, float p_30387_) {
		if (this.isInvulnerableTo(p_30386_)) {
			return false;
		} else {
			Entity entity = p_30386_.getEntity();
			if (!this.level().isClientSide) {
				this.setOrderedToSit(false);
			}

			if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
				p_30387_ = (p_30387_ + 1.0F) / 2.0F;
			}

			return super.hurt(p_30386_, p_30387_);
		}
	}

	public boolean doHurtTarget(Entity p_30372_) {
		boolean flag = p_30372_.hurt(this.damageSources().mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
		if (flag) {
			this.doEnchantDamageEffects(this, p_30372_);
		}

		return flag;
	}

	public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Item item = itemstack.getItem();
		if (this.level().isClientSide) {
			boolean flag = this.isOwnedBy(p_30412_) || this.isTame() || this.isFood(itemstack) && !this.isTame() && !this.isAngry();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else if (this.isTame()) {
			if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
				this.heal((float) itemstack.getFoodProperties(this).getNutrition());
				if (!p_30412_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				this.gameEvent(GameEvent.EAT, this);
				return InteractionResult.SUCCESS;
			} else {
				if (item instanceof DyeItem) {
					DyeItem dyeitem = (DyeItem) item;
					if (this.isOwnedBy(p_30412_)) {
						DyeColor dyecolor = dyeitem.getDyeColor();
						if (dyecolor != this.getCollarColor()) {
							this.setCollarColor(dyecolor);
							if (!p_30412_.getAbilities().instabuild) {
								itemstack.shrink(1);
							}

							return InteractionResult.SUCCESS;
						}

						return super.mobInteract(p_30412_, p_30413_);
					}
				}

				InteractionResult interactionresult = super.mobInteract(p_30412_, p_30413_);
				if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(p_30412_)) {
					this.setOrderedToSit(!this.isOrderedToSit());
					this.jumping = false;
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					return InteractionResult.SUCCESS;
				} else if (itemstack.isEmpty()) {
					p_30412_.startRiding(this);
					return InteractionResult.SUCCESS;
				} else {
					return interactionresult;
				}
			}
		} else if (this.isFood(itemstack) && !this.isAngry()) {
			if (!p_30412_.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_30412_)) {
				this.tame(p_30412_);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				this.setOrderedToSit(true);
				this.level().broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level().broadcastEntityEvent(this, (byte) 6);
			}

			return InteractionResult.SUCCESS;
		} else {
			return super.mobInteract(p_30412_, p_30413_);
		}
	}

	public float getTailAngle() {
		if (this.isAngry()) {
			return 1.5393804F;
		} else {
			return this.isTame() ? (((this.getHealth() - this.getMaxHealth()) - 1.0F) * 0.015F) * (float) Math.PI : ((float) Math.PI / 5F);
		}
	}

	public void setTame(boolean p_30443_) {
		super.setTame(p_30443_);
		if (p_30443_) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(28.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(18.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7.0D);
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int p_30404_) {
		this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
		this.persistentAngerTarget = p_30400_;
	}

	public DyeColor getCollarColor() {
		return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
	}

	public void setCollarColor(DyeColor p_30398_) {
		this.entityData.set(DATA_COLLAR_COLOR, p_30398_.getId());
	}

	public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
		return p_27574_.getBlockState(p_27573_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) ? 10.0F : p_27574_.getPathfindingCostFromLightLevels(p_27573_) - 0.5F;
	}

	public boolean isFood(ItemStack p_30440_) {
		Item item = p_30440_.getItem();
		return item.isEdible() && p_30440_.getFoodProperties(this).isMeat();
	}

	public int getMaxSpawnClusterSize() {
		return 8;
	}

	public boolean canMate(Animal p_30392_) {
		if (p_30392_ == this) {
			return false;
		} else if (!this.isTame()) {
			return false;
		} else if (!(p_30392_ instanceof FrostWolf)) {
			return false;
		} else {
			FrostWolf wolf = (FrostWolf) p_30392_;
			if (!wolf.isTame()) {
				return false;
			} else if (wolf.isInSittingPose()) {
				return false;
			} else {
				return this.isInLove() && wolf.isInLove();
			}
		}
	}


	public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
		if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
			if (p_30389_ instanceof Wolf) {
				Wolf wolf = (Wolf) p_30389_;
				return !wolf.isTame() || wolf.getOwner() != p_30390_;
			} else if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player) p_30390_).canHarmPlayer((Player) p_30389_)) {
				return false;
			} else if (p_30389_ instanceof AbstractHorse && ((AbstractHorse) p_30389_).isTamed()) {
				return false;
			} else {
				return !(p_30389_ instanceof TamableAnimal) || !((TamableAnimal) p_30389_).isTame();
			}
		} else {
			return false;
		}
	}

	public boolean isJumping() {
		return this.isJumping;
	}

	public void setIsJumping(boolean p_30656_) {
		this.isJumping = p_30656_;
	}

	protected void tickRidden(Player p_278233_, Vec3 p_275693_) {
		super.tickRidden(p_278233_, p_275693_);
		Vec2 vec2 = this.getRiddenRotation(p_278233_);
		this.setRot(vec2.y, vec2.x);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
		if (this.isControlledByLocalInstance()) {

			if (this.onGround()) {
				this.setIsJumping(false);
				if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
					this.executeRidersJump(this.playerJumpPendingScale, p_275693_);
				}

				this.playerJumpPendingScale = 0.0F;
			}
		}

	}

	protected Vec2 getRiddenRotation(LivingEntity p_275502_) {
		return new Vec2(p_275502_.getXRot() * 0.5F, p_275502_.getYRot());
	}

	protected Vec3 getRiddenInput(Player p_278278_, Vec3 p_275506_) {
		if (this.onGround() && this.playerJumpPendingScale == 0.0F) {
			return Vec3.ZERO;
		} else {
			float f = p_278278_.xxa * 0.5F;
			float f1 = p_278278_.zza;
			if (f1 <= 0.0F) {
				f1 *= 0.25F;
			}

			return new Vec3((double) f, 0.0D, (double) f1);
		}
	}

	protected float getRiddenSpeed(Player p_278336_) {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
	}

	protected void executeRidersJump(float p_248808_, Vec3 p_275435_) {
		double d0 = 1.0F * (double) p_248808_ * (double) this.getBlockJumpFactor();
		double d1 = d0 + (double) this.getJumpBoostPower();
		Vec3 vec3 = this.getDeltaMovement();
		this.setDeltaMovement(vec3.x, d1, vec3.z);
		this.setIsJumping(true);
		this.hasImpulse = true;
		net.minecraftforge.common.ForgeHooks.onLivingJump(this);
		if (p_275435_.z > 0.0D) {
			float f = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
			float f1 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
			this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f * p_248808_), 0.0D, (double) (0.4F * f1 * p_248808_)));
		}

	}

	protected void playJumpSound() {
		this.playSound(SoundEvents.WOLF_STEP, 0.4F, 1.0F);
	}


	@Override
	public void onPlayerJump(int p_30591_) {
		if (p_30591_ < 0) {
			p_30591_ = 0;
		}

		if (p_30591_ >= 90) {
			this.playerJumpPendingScale = 1.0F;
		} else {
			this.playerJumpPendingScale = 0.4F + 0.4F * (float) p_30591_ / 90.0F;
		}

	}

	@Override
	public boolean canJump() {
		return true;
	}

	@Override
	public void handleStartJump(int p_21695_) {
		this.playJumpSound();
	}

	@Override
	public void handleStopJump() {

	}

	@Override
	public double getRiderShieldingHeight() {
		return 0.25F;
	}
}