package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.ConditionGoal;
import baguchan.frostrealm.entity.goal.StunGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostSounds;
import baguchan.utils.MovementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

public class Gokkur extends Monster {
	private static final UniformInt TIME_BETWEEN_ROLLING = UniformInt.of(80, 160);
	private static final UniformInt TIME_BETWEEN_ROLLING_COOLDOWN = UniformInt.of(100, 200);

	protected static final EntityDataAccessor<Boolean> IS_ROLLING = SynchedEntityData.defineId(Gokkur.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_STUN = SynchedEntityData.defineId(Gokkur.class, EntityDataSerializers.BOOLEAN);


	@javax.annotation.Nullable
	private RollingGoal rollingGoal;

	public Gokkur(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	protected void registerGoals() {
		rollingGoal = new RollingGoal(this, TIME_BETWEEN_ROLLING_COOLDOWN, TIME_BETWEEN_ROLLING);

		this.goalSelector.addGoal(0, new StunGoal(this));
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, rollingGoal);
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0F, true));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.1F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Gokkur.class, 8.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static boolean checkGokkurSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FRIGID_STONE) && Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 1.0F).add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 4.0F).add(Attributes.MOVEMENT_SPEED, 0.26D);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_ROLLING, false);
		this.entityData.define(IS_STUN, false);
	}

	@javax.annotation.Nullable
	public RollingGoal getRollingGoal() {
		return rollingGoal;
	}

	public void aiStep() {
		super.aiStep();

		if (this.isStun()) {
			this.level.addParticle(ParticleTypes.CRIT, this.getRandomX(0.6D), this.getEyeY() + 0.5F, this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return FrostSounds.GOKKUR_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) {
		return FrostSounds.GOKKUR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FrostSounds.GOKKUR_DEATH;
	}

	public void push(Entity p_33636_) {
		if (p_33636_ instanceof LivingEntity && !(p_33636_ instanceof Gokkur) && !(p_33636_ instanceof Player)) {
			this.dealDamage((LivingEntity) p_33636_);
		}
		super.push(p_33636_);
	}

	protected void dealDamage(LivingEntity livingentity) {
		if (this.isAlive() && isRolling()) {
			boolean flag = livingentity.isDamageSourceBlocked(DamageSource.mobAttack(this));
			float f1 = (float) Mth.clamp(livingentity.getDeltaMovement().horizontalDistanceSqr() * 1.15F, 0.2F, 3.0F);
			float f2 = flag ? 0.25F : 1.0F;
			double d1 = this.getX() - livingentity.getX();
			double d2 = this.getZ() - livingentity.getZ();
			double d3 = livingentity.getX() - this.getX();
			double d4 = livingentity.getZ() - this.getZ();
			if (!flag) {
				if (livingentity.hurt(DamageSource.mobAttack(this), Mth.floor(getAttackDamage() * 2.0F * (MovementUtils.movementDamageDistanceSqr(this))))) {
					this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
					this.doEnchantDamageEffects(this, livingentity);
					if (this.getTarget() != null && this.getTarget() == livingentity && rollingGoal != null) {
						rollingGoal.setStopTrigger(true);
					}
					livingentity.knockback(f2 * f1, d1, d2);
				}
			} else {
				this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				if (rollingGoal != null) {
					rollingGoal.setStopTrigger(true);
				}
				this.knockback(f1 * 2.0F, d3, d4);
				this.setStun(true);
			}
		}
	}

	@Override
	public void playerTouch(Player p_20081_) {
		super.playerTouch(p_20081_);
		this.dealDamage(p_20081_);
	}

	protected float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}


	@Override
	protected float nextStep() {
		return this.isRolling() ? super.nextStep() + 3 : super.nextStep();
	}

	@Override
	protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
		if (!this.isRolling()) {
			super.playStepSound(p_20135_, p_20136_);
		} else {
			if (!p_20136_.getMaterial().isLiquid()) {
				BlockState blockstate = this.level.getBlockState(p_20135_.above());
				SoundType soundtype = blockstate.is(Blocks.SNOW) ? blockstate.getSoundType(level, p_20135_, this) : p_20136_.getSoundType(level, p_20135_, this);
				this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch());
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isRolling() && !this.isInWater() && !this.isSpectator() && !this.isCrouching() && !this.isInLava() && this.isAlive()) {
			this.spawnSprintParticle();
		}
	}

	public void setRolling(boolean standing) {
		this.entityData.set(IS_ROLLING, standing);
	}

	public boolean isRolling() {
		return this.entityData.get(IS_ROLLING);
	}

	public void setStun(boolean stun) {
		this.entityData.set(IS_STUN, stun);
	}

	public boolean isStun() {
		return this.entityData.get(IS_STUN);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) {
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putBoolean("Stun", this.isStun());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) {
		super.readAdditionalSaveData(p_21450_);
		this.setStun(p_21450_.getBoolean("Stun"));
	}

	public static class RollingGoal extends ConditionGoal {
		protected final Gokkur gokkur;
		private boolean stopTrigger;

		public RollingGoal(Gokkur gokkur, UniformInt uniformInt, UniformInt uniformInt2) {
			super(gokkur, uniformInt, uniformInt2);
			this.gokkur = gokkur;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean isMatchCondition() {
			return this.gokkur.getTarget() != null && this.gokkur.hasLineOfSight(this.gokkur.getTarget()) && !this.stopTrigger;
		}

		@Override
		public boolean canContinueToUse() {
			return this.isMatchCondition() && super.canContinueToUse();
		}

		@Override
		public void start() {
			super.start();
			this.gokkur.setRolling(true);
			this.stopTrigger = false;
		}

		@Override
		public void stop() {
			super.stop();
			this.gokkur.setRolling(false);
			this.stopTrigger = false;
		}

		public void setStopTrigger(boolean stopTrigger) {
			this.stopTrigger = stopTrigger;
		}

		@Override
		public void tick() {
			super.tick();
			LivingEntity livingentity = this.gokkur.getTarget();
			if (livingentity != null) {
				this.gokkur.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 2.5D);
			}
		}
	}
}
