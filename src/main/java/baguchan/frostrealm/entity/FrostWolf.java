package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.vehicle.ChestSledge;
import baguchan.frostrealm.entity.vehicle.Sledge;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.Predicate;

public class FrostWolf extends Wolf {
	public static final Predicate<LivingEntity> FROST_PREY_SELECTOR = (p_30437_) -> {
		EntityType<?> entitytype = p_30437_.getType();
		return entitytype == FrostEntities.SNOWPILE_QUAIL.get() || entitytype == FrostEntities.CRYSTAL_FOX.get();
	};


	public FrostWolf(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
		super(p_30369_, p_30370_);
		this.maxUpStep = 1.0F;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, FROST_PREY_SELECTOR));
	}

	@Override
	protected boolean canRide(Entity p_20339_) {
		return !(p_20339_ instanceof Sledge) && !(p_20339_ instanceof ChestSledge);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.ATTACK_DAMAGE, 3.0D);
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
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}

	public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
		return p_27574_.getBlockState(p_27573_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) ? 10.0F : p_27574_.getPathfindingCostFromLightLevels(p_27573_) - 0.5F;
	}

	@Override
	protected boolean shouldStayCloseToLeashHolder() {
		return super.shouldStayCloseToLeashHolder() && !(this.getLeashHolder() instanceof Sledge) && !(this.getLeashHolder() instanceof Sledge);
	}

	@Override
	protected void tickLeash() {
		if (this.getLeashHolder() != null) {
			if (!this.isAlive() || !this.getLeashHolder().isAlive()) {
				this.dropLeash(true, true);
			}

		}
		Entity entity = this.getLeashHolder();
		if (entity != null && entity.level == this.level) {
			this.restrictTo(entity.blockPosition(), 5);
			float f = this.distanceTo(entity);
			if (this instanceof TamableAnimal && ((TamableAnimal) this).isInSittingPose()) {
				if (f > 10.0F) {
					this.dropLeash(true, true);
				}

				return;
			}

			this.onLeashDistance(f);
			if (f > 10.0F) {
				this.dropLeash(true, true);
				this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
			} else if (f > 6.0F) {
				double d0 = (entity.getX() - this.getX()) / (double) f;
				double d1 = (entity.getY() - this.getY()) / (double) f;
				double d2 = (entity.getZ() - this.getZ()) / (double) f;
				this.setDeltaMovement(this.getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
				this.checkSlowFallDistance();
			} else if (this.shouldStayCloseToLeashHolder()) {
				this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
				float f1 = 2.0F;
				Vec3 vec3 = (new Vec3(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ())).normalize().scale((double) Math.max(f - 2.0F, 0.0F));
				this.getNavigation().moveTo(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z, this.followLeashSpeed());
			}
		}

	}
}