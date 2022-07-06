package baguchan.frostrealm.entity;

import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.entity.projectile.WarpedCrystalShard;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.EnumSet;

public class ClustWraith extends FrostWraith implements RangedAttackMob {
	private float clientSideStandAnimationO;
	private float clientSideStandAnimation;

	public ClustWraith(EntityType<? extends FrostWraith> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new AttackGoal(this));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.ARMOR, 2.0F).add(Attributes.FLYING_SPEED, 0.24F).add(Attributes.MOVEMENT_SPEED, 0.21F).add(Attributes.FOLLOW_RANGE, 18.0F);
	}

	public void tick() {
		super.tick();
		this.clientSideStandAnimationO = this.clientSideStandAnimation;
		if (this.isAggressive()) {
			this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation + 0.1F, 0.0F, 1.0F);
		} else {
			this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation - 0.1F, 0.0F, 1.0F);
		}
	}

	public float getStandingAnimationScale(float p_29570_) {
		return Mth.lerp(p_29570_, this.clientSideStandAnimationO, this.clientSideStandAnimation);
	}

	public static boolean checkClustWraithSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		FrostWeatherCapability capability = FrostWeatherCapability.get(p_27579_.getLevel()).orElse(null);
		if (p_27580_ == MobSpawnType.SPAWNER) {
			return Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
		}
		return Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
	}

	static class AttackGoal extends Goal {
		private final ClustWraith wraith;
		private int attackStep;
		private int attackTime;
		private int lastSeen;

		public AttackGoal(ClustWraith p_32247_) {
			this.wraith = p_32247_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.wraith.getTarget();
			return livingentity != null && livingentity.isAlive() && this.wraith.canAttack(livingentity);
		}

		public void start() {
			this.attackStep = 0;
			this.wraith.setAggressive(true);
		}

		public void stop() {
			this.lastSeen = 0;
			this.wraith.setAggressive(false);
		}

		public void tick() {
			--this.attackTime;
			LivingEntity livingentity = this.wraith.getTarget();
			if (livingentity != null) {
				boolean flag = this.wraith.getSensing().hasLineOfSight(livingentity);
				if (flag) {
					this.lastSeen = 0;
				} else {
					++this.lastSeen;
				}

				double d0 = this.wraith.distanceToSqr(livingentity);
				if (d0 < 32.0D) {
					if (!flag) {
						return;
					}
					this.attackStep = 0;

					if (d0 < 4.0D + this.wraith.getBbWidth() && this.attackTime <= 0) {
						this.attackTime = 20;
						this.wraith.doHurtTarget(livingentity);
					}

					this.wraith.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
					this.wraith.getNavigation().moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0F);
				} else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
					if (this.attackTime <= 0) {
						++this.attackStep;
						if (this.attackStep == 1) {
							this.attackTime = 30;
						} else if (this.attackStep <= 3) {
							this.attackTime = 10;
						} else {
							this.attackTime = 30;
							this.attackStep = 0;
						}

						if (this.attackStep > 1) {
							double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;

							this.wraith.performRangedAttack(livingentity, attackTime);
						}
					}

					this.wraith.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
					this.wraith.getNavigation().stop();
				} else if (this.lastSeen < 5) {
					this.wraith.getNavigation().moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0F);
				}

				super.tick();
			}
		}

		private double getFollowDistance() {
			return this.wraith.getAttributeValue(Attributes.FOLLOW_RANGE) * 0.95F;
		}
	}

	@Override
	public void performRangedAttack(LivingEntity p_29912_, float p_29913_) {
		this.playSound(SoundEvents.TRIDENT_THROW, 2.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

		for (int i = 0; i < 3; i++) {
			WarpedCrystalShard crystal = new WarpedCrystalShard(this.level, this);
			double d1 = p_29912_.getX() - this.getX();
			double d2 = p_29912_.getEyeY() - this.getEyeY();
			double d3 = p_29912_.getZ() - this.getZ();
			float f = Mth.sqrt((float) (d1 * d1 + d3 * d3)) * 0.2F;
			crystal.shoot(d1 + f * (this.random.nextDouble() - this.random.nextDouble()), d2 + f * (this.random.nextDouble() - this.random.nextDouble()), d3 + f * (this.random.nextDouble() - this.random.nextDouble()), 0.85F, 0.5F);
			this.level.addFreshEntity(crystal);
		}
	}
}
