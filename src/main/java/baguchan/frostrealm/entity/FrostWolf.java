package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

import java.util.UUID;
import java.util.function.Predicate;

public class FrostWolf extends Wolf {
	public static final Predicate<LivingEntity> FROST_PREY_SELECTOR = (p_30437_) -> {
		EntityType<?> entitytype = p_30437_.getType();
		return entitytype == FrostEntities.SNOWPILE_QUAIL.get() || entitytype == FrostEntities.CRYSTAL_FOX.get();
	};


	public FrostWolf(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
		super(p_30369_, p_30370_);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, FROST_PREY_SELECTOR));
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
			return this.isTame() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.015F) * (float) Math.PI : ((float) Math.PI / 5F);
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
	public void tick() {
		super.tick();
	}

	@Override
	public void aiStep() {
		super.aiStep();
	}
}