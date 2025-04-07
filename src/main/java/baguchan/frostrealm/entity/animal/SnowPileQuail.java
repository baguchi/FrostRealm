package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.block.SnowPileQuailEggBlock;
import baguchan.frostrealm.entity.IHasEgg;
import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.goal.BreedAndEggGoal;
import baguchan.frostrealm.entity.goal.FindAndPlaceEggGoal;
import baguchan.frostrealm.entity.goal.StealFromYetiGoal;
import baguchan.frostrealm.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SnowPileQuail extends FrostAnimal implements IHasEgg {
	private static final EntityDimensions BABY_DIMENSIONS = FrostEntities.SNOWPILE_QUAIL.get().getDimensions().scale(0.5F).withEyeHeight(0.2F);
	private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.BOOLEAN);
	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;
	private float nextFlap = 1.0F;
	@Nullable
	private BlockPos homeTarget;
	@Nullable
	private LivingEntity stealTarget;
	private int ticksShake = 24000;
	private int ticksSinceEaten;
	public final AnimationState shakeAnimationState = new AnimationState();
	public final AnimationState popEggAnimationState = new AnimationState();
	public SnowPileQuail(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
		this.setCanPickUpLoot(true);
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_316516_) {
		return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(p_316516_);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, CrystalFox.class, 8.0F, 1.55D, 1.45D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolfflue.class, 8.0F, 1.55D, 1.45D, (p_28590_) -> {
			return !((Wolfflue) p_28590_).isTame();
		}));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Ferret.class, 8.0F, 1.55D, 1.45D, (p_28590_) -> {
			return !((Ferret) p_28590_).isTame();
		}));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Yeti.class, 8.0F, 1.55D, 1.45D, (p_28590_) -> {
			return p_28590_ == getStealTarget();
		}));
		this.goalSelector.addGoal(3, new FindAndPlaceEggGoal<>(this, 0.8D) {
			@Override
			public void afterPlaceEgg() {
				level().playSound(null, blockPos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level().random.nextFloat() * 0.2F);
				level().setBlock(blockPos, FrostBlocks.SNOWPILE_QUAIL_EGG.get().defaultBlockState().setValue(SnowPileQuailEggBlock.EGGS, random.nextInt(1) + 1), 3);
				setHomeTarget(blockPos);
				//egg animation
				level().broadcastEntityEvent(this.mob, (byte) 7);
			}

			@Override
			protected boolean isValidTarget(LevelReader p_25619_, BlockPos p_25620_) {
				return SnowPileQuailEggBlock.onDirt(p_25619_, p_25620_) && p_25619_.getBlockState(p_25620_).isAir();
			}
		});
		this.goalSelector.addGoal(4, new BreedAndEggGoal<>(this, 1.0D));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, (item) -> item.is(FrostTags.Items.SNOWPILE_FOODS), false));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new StealFromYetiGoal(this, 1.0D));

		this.goalSelector.addGoal(8, new MoveToGoal(this, 8.0D, 1.1D));
		this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ANGRY, false);
		builder.define(HAS_EGG, false);
	}

	@Override
	public void handleEntityEvent(byte p_21807_) {
		if (p_21807_ == 5) {
			this.shakeAnimationState.start(this.tickCount);
		} else if (p_21807_ == 7) {
			this.popEggAnimationState.start(this.tickCount);
		} else {
			super.handleEntityEvent(p_21807_);
		}

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, EntitySpawnReason p_146748_, @Nullable SpawnGroupData p_146749_) {
		this.populateDefaultEquipmentSlots(p_146746_.getRandom(), p_146747_);
		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource p_218171_, DifficultyInstance p_218172_) {
		float f = p_218171_.nextFloat();
		ItemStack itemstack;
		if (f < 0.25F) {
			if (f < 0.1F) {
				itemstack = new ItemStack(FrostItems.YETI_FUR.get());
			} else {
				itemstack = new ItemStack(Items.STICK);
			}

			this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return FrostSounds.SNOWPILE_QUAIL_IDLE.get();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return FrostSounds.SNOWPILE_QUAIL_HURT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return FrostSounds.SNOWPILE_QUAIL_DEATH.get();
	}

	@Override
	protected void customServerAiStep(ServerLevel serverLevel) {
		super.customServerAiStep(serverLevel);

		if (this.isAlive() && this.isEffectiveAi()) {
			++this.ticksSinceEaten;
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (this.canEat(itemstack)) {
				if (this.ticksSinceEaten > 600) {
					ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
					this.heal(2);
					if (!itemstack1.isEmpty()) {
						this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
					}

					this.ticksSinceEaten = 0;
				} else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
					this.playSound(SoundEvents.PARROT_EAT, 1.0F, 1.0F);
					this.level().broadcastEntityEvent(this, (byte) 45);
				}
			}

			if (--this.ticksShake <= 0) {
				this.level().broadcastEntityEvent(this, (byte) 5);
				this.playSound(SoundEvents.WOLF_SHAKE);
				this.spawnAtLocation(serverLevel, new ItemStack(Items.FEATHER, 1 + this.random.nextInt(2)));
				this.ticksShake = 12000 + this.random.nextIntBetweenInclusive(6000, 12000);
			}
		}
	}

	@Override
	public void aiStep() {
		this.oFlap = this.flap;
		this.oFlapSpeed = this.flapSpeed;
		this.flapSpeed = this.flapSpeed + (this.onGround() ? -1.0F : 4.0F) * 0.3F;
		this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
		if (!this.onGround() && this.flapping < 1.0F) {
			this.flapping = 1.0F;
		}

		this.flapping *= 0.9F;
		this.flap = this.flap + this.flapping * 2.0F;

		if (this.isSleeping() || this.isImmobile()) {
			this.jumping = false;
			this.xxa = 0.0F;
			this.zza = 0.0F;
		}

		super.aiStep();

		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0D) {
			this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
		}


		if (this.homeTarget != null && !this.level().getBlockState(this.homeTarget).is(FrostBlocks.SNOWPILE_QUAIL_EGG.get())) {
			this.setHomeTarget(null);
		}

	}

	@Override
	protected void pickUpItem(ServerLevel serverLevel, ItemEntity p_28514_) {
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
		if (p_28578_.is(Items.FEATHER) || p_28578_.is(FrostBlocks.SNOWPILE_QUAIL_EGG.asItem()) || p_28578_.is(FrostItems.COOKED_SNOWPILE_QUAIL_EGG.asItem())) {
			return false;
		}

		return itemstack.isEmpty() || this.ticksSinceEaten > 0 && p_28578_.get(DataComponents.FOOD) != null && !p_28578_.is(ItemTags.MEAT) && p_28578_.get(DataComponents.FOOD) == null;
	}

	private boolean canEat(ItemStack p_28598_) {
		return p_28598_.get(DataComponents.FOOD) != null && !p_28598_.is(ItemTags.MEAT) && this.getTarget() == null && this.onGround() && !this.isSleeping();
	}

	@Override
	public boolean isFood(ItemStack p_28271_) {
		return p_28271_.is(FrostTags.Items.SNOWPILE_FOODS);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.homeTarget != null) {
			compoundTag.store("HomeTarget", BlockPos.CODEC, this.homeTarget);
		}
		compoundTag.putBoolean("HasEgg", hasEgg());
		compoundTag.putInt("TickShake", this.ticksShake);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("HomeTarget")) {
			this.homeTarget = compoundTag.read("HomeTarget", BlockPos.CODEC).orElse(null);
		}
		this.setHasEgg(compoundTag.getBooleanOr("HasEgg", false));
		this.ticksShake = compoundTag.getIntOr("TickShake", 0);
	}

	public void setHomeTarget(@Nullable BlockPos pos) {
		this.homeTarget = pos;
	}

	@Nullable
	private BlockPos getHomeTarget() {
		return this.homeTarget;
	}

	public void setStealTarget(@Nullable LivingEntity stealTarget) {
		this.stealTarget = stealTarget;
	}

	public @Nullable LivingEntity getStealTarget() {
		return stealTarget;
	}

	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}

	public void setHasEgg(boolean hasEgg) {
		this.entityData.set(HAS_EGG, hasEgg);
	}


	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
		return FrostEntities.SNOWPILE_QUAIL.get().create(level, EntitySpawnReason.BREEDING);
	}

	@Override
	protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
	}


	class MoveToGoal extends Goal {
		final SnowPileQuail quail;
		final double stopDistance;
		final double speedModifier;

		MoveToGoal(SnowPileQuail snowPileQuail, double distance, double speed) {
			this.quail = snowPileQuail;
			this.stopDistance = distance;
			this.speedModifier = speed;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public void stop() {
			SnowPileQuail.this.navigation.stop();
		}

		public boolean canUse() {
			BlockPos blockpos = this.quail.getHomeTarget();

			double distance = this.quail.level().isBrightOutside() ? this.stopDistance : this.stopDistance / 4.0F;

			return blockpos != null && this.isTooFarAway(blockpos, distance);
		}

		public void tick() {
			BlockPos blockpos = this.quail.getHomeTarget();
			if (blockpos != null && SnowPileQuail.this.navigation.isDone()) {
				if (this.isTooFarAway(blockpos, 10.0D)) {
					Vec3 vector3d = (new Vec3((double) blockpos.getX() - this.quail.getX(), (double) blockpos.getY() - this.quail.getY(), (double) blockpos.getZ() - this.quail.getZ())).normalize();
					Vec3 vector3d1 = vector3d.scale(10.0D).add(this.quail.getX(), this.quail.getY(), this.quail.getZ());
					SnowPileQuail.this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
				} else {
					SnowPileQuail.this.navigation.moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos pos, double distance) {
			return !pos.closerThan(this.quail.blockPosition(), distance);
		}
	}
}
