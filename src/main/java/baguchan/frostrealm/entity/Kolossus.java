package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.BeasterAngryGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Kolossus extends Animal implements IChargeMob, NeutralMob {

	public final AnimationState chargeAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();

	private static final UniformInt TIME_BETWEEN_CHARGE = UniformInt.of(200, 400);
	private static final UniformInt TIME_BETWEEN_CHARGE_COOLDOWN = UniformInt.of(100, 200);

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

	private int remainingPersistentAngerTime;
	@javax.annotation.Nullable
	private UUID persistentAngerTarget;

	public Kolossus(EntityType<? extends Kolossus> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	public void handleEntityEvent(byte p_219360_) {
		if (p_219360_ == 4) {
			this.attackAnimationState.start(this.tickCount);
		} else if (p_219360_ == 61) {
			this.chargeAnimationState.start(this.tickCount);
		} else {
			super.handleEntityEvent(p_219360_);
		}
	}

	public boolean doHurtTarget(Entity p_219472_) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		this.playSound(SoundEvents.WARDEN_ATTACK_IMPACT, 1.0F, this.getVoicePitch());
		return super.doHurtTarget(p_219472_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new BeasterAngryGoal<>(this, TIME_BETWEEN_CHARGE_COOLDOWN, TIME_BETWEEN_CHARGE));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.35F, true));
		this.goalSelector.addGoal(3, new BreedGoal(this, 0.95D));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.85F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new KolossusHurtByTargetGoal().setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new KolossusAttackAnythingGoal());
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 80.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 4.0F).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 15.0F).add(Attributes.ATTACK_KNOCKBACK, 1.65F);
	}

	@Override
	public float getScale() {
		return this.isBaby() ? 0.5F : 1.25F;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.KOLOSSUS.get().create(p_146743_);
	}

	@Override
	public void onCharge() {
		this.level.broadcastEntityEvent(this, (byte) 61);
	}

	@Override
	public void onChargeDamage(LivingEntity damageEntity) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		damageEntity.hurt(DamageSource.mobAttack(this), Mth.floor((float) (this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.5F)));
	}

	public void readAdditionalSaveData(CompoundTag p_29541_) {
		super.readAdditionalSaveData(p_29541_);
		this.readPersistentAngerSaveData(this.level, p_29541_);
	}

	public void addAdditionalSaveData(CompoundTag p_29548_) {
		super.addAdditionalSaveData(p_29548_);
		this.addPersistentAngerSaveData(p_29548_);
	}

	public void tick() {
		super.tick();
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level, true);
		}
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	public void setRemainingPersistentAngerTime(int p_29543_) {
		this.remainingPersistentAngerTime = p_29543_;
	}

	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	public void setPersistentAngerTarget(@javax.annotation.Nullable UUID p_29539_) {
		this.persistentAngerTarget = p_29539_;
	}

	@javax.annotation.Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}


	public static boolean checkSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	class KolossusHurtByTargetGoal extends HurtByTargetGoal {
		public KolossusHurtByTargetGoal() {
			super(Kolossus.this);
		}

		public void start() {
			super.start();
			if (Kolossus.this.isBaby()) {
				this.alertOthers();
				this.stop();
			}

		}

		protected void alertOther(Mob p_29580_, LivingEntity p_29581_) {
			if (p_29580_ instanceof Kolossus && !p_29580_.isBaby()) {
				super.alertOther(p_29580_, p_29581_);
			}

		}
	}

	class KolossusAttackAnythingGoal extends NearestAttackableTargetGoal<LivingEntity> {
		public KolossusAttackAnythingGoal() {
			super(Kolossus.this, LivingEntity.class, 20, true, true, (p_29932_) -> {
				return !(p_29932_ instanceof Kolossus);
			});
		}

		public boolean canUse() {
			if (Kolossus.this.isBaby()) {
				return false;
			} else {
				if (super.canUse()) {
					return true;
				}

				return false;
			}
		}

		protected double getFollowDistance() {
			return super.getFollowDistance() * 0.25D;
		}
	}
}
