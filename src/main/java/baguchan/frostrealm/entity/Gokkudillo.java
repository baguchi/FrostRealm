package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class Gokkudillo extends Gokkur {
	public Gokkudillo(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}


	public static boolean checkGokkudilloSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FRIGID_STONE) && Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 10.0F).add(Attributes.MOVEMENT_SPEED, 0.24D);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_29615_) {
		if (IS_ROLLING.equals(p_29615_)) {
			this.refreshDimensions();
		}

		super.onSyncedDataUpdated(p_29615_);
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

	protected void dealDamage(LivingEntity livingentity) {
		if (this.isAlive() && isRolling()) {
			boolean flag = livingentity.isDamageSourceBlocked(DamageSource.mobAttack(this));
			float f1 = (float) Mth.clamp(livingentity.getDeltaMovement().horizontalDistanceSqr() * 1.5F, 0.2F, 3.0F);
			float f2 = flag ? 0.25F : 1.0F;
			double d1 = this.getX() - livingentity.getX();
			double d2 = this.getZ() - livingentity.getZ();
			double d3 = livingentity.getX() - this.getX();
			double d4 = livingentity.getZ() - this.getZ();
			if (!flag) {
				if (livingentity.hurt(DamageSource.mobAttack(this), Mth.floor(getAttackDamage() * 1.5F))) {
					this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
					this.doEnchantDamageEffects(this, livingentity);
					if (this.getTarget() != null && this.getTarget() == livingentity && getRollingGoal() != null) {
						getRollingGoal().setStopTrigger(true);
					}
					livingentity.knockback(f2 * f1, d1, d2);
				}
			} else {
				this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				if (getRollingGoal() != null) {
					getRollingGoal().setStopTrigger(true);
				}
				this.knockback(f1 * 0.8F, d3, d4);
				if (this.random.nextFloat() < 0.25F) {
					this.setStun(true);
				}
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
				this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 1.2F, soundtype.getPitch());
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

	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) {
		super.addAdditionalSaveData(p_21484_);
	}

	@Override
	public float getScale() {
		return this.isBaby() ? 1.0F : 1.8F;
	}


	@Override
	protected float getStandingEyeHeight(Pose p_21131_, EntityDimensions p_21132_) {
		return p_21132_.height * 0.65F;
	}

	public EntityDimensions getDimensions(Pose p_29608_) {
		return this.isRolling() ? EntityDimensions.fixed(0.65F, 0.45F) : super.getDimensions(p_29608_);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) {
		super.readAdditionalSaveData(p_21450_);
	}
}
