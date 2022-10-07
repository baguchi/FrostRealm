package baguchan.frostrealm.entity;

import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.entity.goal.RandomSnowSwimGoal;
import baguchan.frostrealm.entity.movecontrol.LavaMoveControl;
import baguchan.frostrealm.entity.path.LavaPathNavigation;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostWeathers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class Octorolga extends Monster {
	private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(Octorolga.class, EntityDataSerializers.OPTIONAL_UUID);

	private boolean isGeneratedPart;

	public Octorolga(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.moveControl = new LavaMoveControl(this, 85, 10, 1.25F);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
		this.maxUpStep = 1.0F;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.16F).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new Octorolga.OctolgaAttackGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new Octorolga.OctolgaGoToLavaGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new RandomSnowSwimGoal(this, 1.0F, 10));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
	}

	@Override
	protected PathNavigation createNavigation(Level p_27480_) {
		return new LavaPathNavigation(this, p_27480_);
	}

	@Override
	public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
		return p_27574_.getBlockState(p_27573_).is(Blocks.LAVA) ? 10.0F : super.getWalkTargetValue(p_27573_, p_27574_);
	}

	@Override
	public void travel(Vec3 p_27490_) {
		if (this.isEffectiveAi() && this.isInLava()) {
			this.moveRelative(this.getSpeed(), p_27490_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		} else {
			super.travel(p_27490_);
		}
	}

	@Override
	public boolean isSensitiveToWater() {
		return true;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	public boolean canBeLeashed(Player player) {
		return true;
	}

	public boolean checkSpawnObstruction(LevelReader worldIn) {
		return worldIn.isUnobstructed(this);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.getChildId() != null) {
			compound.putUUID("ChildUUID", this.getChildId());
		}
		compound.putBoolean("GeneratedPart", this.isGeneratedPart);
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.hasUUID("ChildUUID")) {
			this.setChildId(compound.getUUID("ChildUUID"));
		}
		this.isGeneratedPart = compound.getBoolean("GeneratedPart");
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(CHILD_UUID, Optional.empty());
	}

	public static boolean checkSpawnRules(EntityType<Octorolga> p_219129_, ServerLevelAccessor p_219130_, MobSpawnType p_219131_, BlockPos p_219132_, RandomSource p_219133_) {
		FrostWeatherCapability capability = FrostWeatherCapability.get(p_219130_.getLevel()).orElse(null);
		if (p_219131_ == MobSpawnType.SPAWNER || capability.isWeatherActive() && capability.getFrostWeather() == FrostWeathers.PURPLE_FOG.get()) {

			return p_219130_.getFluidState(p_219132_).is(FluidTags.LAVA) && p_219130_.getFluidState(p_219132_.above()).is(FluidTags.LAVA);
		}

		return false;
	}

	@Nullable
	public UUID getChildId() {
		return this.entityData.get(CHILD_UUID).orElse(null);
	}

	public void setChildId(@Nullable UUID uniqueId) {
		this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
	}

	public Entity getChild() {
		UUID id = getChildId();
		if (id != null && !level.isClientSide) {
			return ((ServerLevel) level).getEntity(id);
		}
		return null;
	}

	public void tick() {
		super.tick();
		isInsidePortal = false;
		boolean ground = !this.isInLava() && !this.isInWater() && this.isOnGround();
		if (!level.isClientSide) {
			Entity child = getChild();
			if (child == null && !isGeneratedPart) {
				LivingEntity partParent = this;
				int segments = 4 + getRandom().nextInt(3);
				for (Direction direction : new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST}) {
					for (int i = 0; i < segments; i++) {
						OctorolgaPart part = new OctorolgaPart(FrostEntities.OCTOROLGA_PART.get(), partParent);
						//switch parent when segment's number is 0
						if (i != 0) {
							part.setParent(partParent);
						} else {
							part.setParent(this);
						}
						part.setBodyIndex(i + 1);
						if (partParent == this) {
							this.setChildId(part.getUUID());
						}
						part.setPos(this.getX(), this.getY(), this.getZ());
						part.setDirection(direction);
						partParent = part;
						level.addFreshEntity(part);
						isGeneratedPart = true;
					}
				}
			}
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		return source == DamageSource.IN_WALL || source == DamageSource.CRAMMING || source == DamageSource.FALLING_BLOCK || source == DamageSource.LAVA || source.isFire() || super.isInvulnerableTo(source);
	}

	@Override
	public boolean hurt(DamageSource source, float damage) {
		if (source.isFire() || source.isProjectile() && !source.isMagic()) {
			return false;
		} else {
			if (source == DamageSource.DROWN) {
				damage *= 3F;
			} else if (source == DamageSource.FREEZE) {
				damage *= 1.25F;
			} else if (source.isExplosion()) {
				damage *= 1.25F;
			} else {
				damage *= 0.5F;
			}
		}
		return super.hurt(source, damage);
	}

	@Override
	public float getScale() {
		return isBaby() ? 1.0F : 2.0F;
	}

	public int getMaxSpawnClusterSize() {
		return 1;
	}

	public boolean isMaxGroupSizeReached(int sizeIn) {
		return false;
	}

	static class OctolgaGoToLavaGoal extends MoveToBlockGoal {
		private final Octorolga octorolga;

		OctolgaGoToLavaGoal(Octorolga p_33955_, double p_33956_) {
			super(p_33955_, p_33956_, 8, 2);
			this.octorolga = p_33955_;
		}

		public BlockPos getMoveToTarget() {
			return this.blockPos;
		}

		public boolean canContinueToUse() {
			return !this.octorolga.isInLava() && this.isValidTarget(this.octorolga.level, this.blockPos);
		}

		public boolean canUse() {
			return !this.octorolga.isInLava() && super.canUse();
		}

		public boolean shouldRecalculatePath() {
			return this.tryTicks % 20 == 0;
		}

		protected boolean isValidTarget(LevelReader p_33963_, BlockPos p_33964_) {
			return p_33963_.getBlockState(p_33964_).is(Blocks.LAVA) && p_33963_.getBlockState(p_33964_.above()).isPathfindable(p_33963_, p_33964_, PathComputationType.LAND);
		}
	}

	static class OctolgaAttackGoal extends MeleeAttackGoal {
		private final Octorolga octorolga;

		OctolgaAttackGoal(Octorolga p_33955_, double p_33956_) {
			super(p_33955_, p_33956_, false);
			this.octorolga = p_33955_;
		}

		@Override
		public void tick() {
			super.tick();
		}
	}
}
