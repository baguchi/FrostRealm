package baguchan.frostrealm.entity;

import baguchan.frostrealm.api.animation.Animation;
import baguchan.frostrealm.api.animation.IAnimatable;
import baguchan.frostrealm.block.SnowPileQuailEggBlock;
import baguchan.frostrealm.entity.goal.AngryGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

public class SnowPileQuail extends Animal implements IAnimatable {
	private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.BOOLEAN);

	private static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.INT);


	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

	public static final Animation IDLE_ANIMATION = Animation.create(60);

	private int animationTick;

	@Nullable
	private BlockPos homeTarget;

	public SnowPileQuail(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new AngryGoal(this));
		this.goalSelector.addGoal(3, new SnowPileQuailBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new MoveToGoal(this, 8.0D, 1.1D));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGRY, false);
		this.entityData.define(HAS_EGG, false);
		this.entityData.define(ANIMATION_ID, -1);
	}

	@Override
	public void playAmbientSound() {
		super.playAmbientSound();
		this.setAnimation(IDLE_ANIMATION);
	}

	public static boolean checkQuailSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public boolean isFood(ItemStack p_28271_) {
		return FOOD_ITEMS.test(p_28271_);
	}

	@Override
	protected float getStandingEyeHeight(Pose p_21131_, EntityDimensions p_21132_) {
		return p_21132_.height * 0.45F;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.homeTarget != null) {
			compoundTag.put("HomeTarget", NbtUtils.writeBlockPos(this.homeTarget));
		}
		compoundTag.putBoolean("HasEgg", hasEgg());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("HomeTarget")) {
			this.homeTarget = NbtUtils.readBlockPos(compoundTag.getCompound("HomeTarget"));
		}
		this.setHasEgg(compoundTag.getBoolean("HasEgg"));
	}

	public void setHomeTarget(@Nullable BlockPos pos) {
		this.homeTarget = pos;
	}

	@Nullable
	private BlockPos getHomeTarget() {
		return this.homeTarget;
	}

	public void aiStep() {
		super.aiStep();
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround && vec3.y < 0.0D) {
			this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
		}

		if (this.isAlive() && this.hasEgg() && this.getTarget() == null) {
			BlockPos blockpos = this.blockPosition();
			if (SnowPileQuailEggBlock.onDirt(this.level, blockpos)) {
				level.playSound((Player) null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
				level.setBlock(blockpos, FrostBlocks.SNOWPILE_QUAIL_EGG.defaultBlockState().setValue(SnowPileQuailEggBlock.EGGS, Integer.valueOf(this.random.nextInt(1) + 1)), 3);
				this.setAnimation(IDLE_ANIMATION);
				this.setHasEgg(false);
				this.setHomeTarget(blockpos);
				this.setAge(2400);
			}
		}
	}


	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}

	public void setHasEgg(boolean hasEgg) {
		this.entityData.set(HAS_EGG, hasEgg);
	}

	public boolean isAngry() {
		return this.entityData.get(ANGRY);
	}

	public void setAngry(boolean angry) {
		this.entityData.set(ANGRY, angry);
	}


	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
		return FrostEntities.SNOWPILE_QUAIL.create(level);
	}

	@Override
	public boolean causeFallDamage(float p_147105_, float p_147106_, DamageSource p_147107_) {
		return false;
	}

	@Override
	protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
	}

	@Override
	public void tick() {
		super.tick();
		updateAnimations(this);
	}

	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		this.animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		int index = this.entityData.get(ANIMATION_ID);
		if (index < 0) {
			return NO_ANIMATION;
		} else {
			return this.getAnimations()[index];
		}
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{
				IDLE_ANIMATION
		};
	}

	@Override
	public void setAnimation(Animation animation) {
		this.entityData.set(ANIMATION_ID, ArrayUtils.indexOf(this.getAnimations(), animation));
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

			double distance = this.quail.level.isDay() ? this.stopDistance : this.stopDistance / 4.0F;

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
					SnowPileQuail.this.navigation.moveTo((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos pos, double distance) {
			return !pos.closerThan(this.quail.position(), distance);
		}
	}

	static class SnowPileQuailBreedGoal extends BreedGoal {
		private final SnowPileQuail quail;

		SnowPileQuailBreedGoal(SnowPileQuail quail, double speed) {
			super(quail, speed);
			this.quail = quail;
		}

		public boolean canUse() {
			return super.canUse() && !this.quail.hasEgg();
		}

		protected void breed() {
			ServerPlayer serverplayer = this.animal.getLoveCause();
			if (serverplayer == null && this.partner.getLoveCause() != null) {
				serverplayer = this.partner.getLoveCause();
			}

			if (serverplayer != null) {
				serverplayer.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverplayer, this.animal, this.partner, (AgeableMob) null);
			}

			this.quail.setHasEgg(true);
			this.animal.resetLove();
			this.partner.resetLove();
			Random random = this.animal.getRandom();
			if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
				this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
			}

		}
	}
}
