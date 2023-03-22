package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Gokkudillo extends Gokkur {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("a0431f61-9dbb-d872-8a44-7e5e4204ae3e");
	private static final UUID NO_ARMOR_MODIFIER_UUID = UUID.fromString("3748f92b-6aa9-db9d-dce8-33a6d73df14a");
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(ARMOR_MODIFIER_UUID, "Armor bonus", 10.0D, AttributeModifier.Operation.ADDITION);
	private static final AttributeModifier NO_ARMOR_MODIFIER = new AttributeModifier(NO_ARMOR_MODIFIER_UUID, "No Armor bonus", -8.0D, AttributeModifier.Operation.ADDITION);

	public Gokkudillo(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}


	public static boolean checkGokkudilloSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FRIGID_STONE.get()) && Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 10.0F).add(Attributes.ARMOR_TOUGHNESS, 0.1F).add(Attributes.KNOCKBACK_RESISTANCE, 0.25F).add(Attributes.MOVEMENT_SPEED, 0.24D);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_29615_) {
		if (IS_ROLLING.equals(p_29615_)) {
			this.refreshDimensions();
		}

		super.onSyncedDataUpdated(p_29615_);
	}


	@Override
	public void setRolling(boolean roll) {
		if (!this.level.isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER);
			if (roll) {
				this.getAttribute(Attributes.ARMOR).addPermanentModifier(ARMOR_MODIFIER);
				this.gameEvent(GameEvent.ENTITY_INTERACT);
			} else {
				this.gameEvent(GameEvent.ENTITY_INTERACT);
			}
		}
		super.setRolling(roll);
	}

	@Override
	public void setStun(boolean stun) {
		if (!this.level.isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(NO_ARMOR_MODIFIER);
			if (stun) {
				this.getAttribute(Attributes.ARMOR).addPermanentModifier(NO_ARMOR_MODIFIER);
			}
		}
		super.setStun(stun);
	}

	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) {
		if (this.isRolling()) {
			Entity entity = p_21016_.getDirectEntity();
			if (entity instanceof AbstractArrow) {
				return false;
			}
		}

		return super.hurt(p_21016_, p_21017_);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return FrostSounds.GOKKUDILLO_IDLE.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) {
		return FrostSounds.GOKKUDILLO_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FrostSounds.GOKKUDILLO_DEATH.get();
	}

	protected float stopRollingPercent() {
		return 0.25F;
	}

	protected void dealDamage(LivingEntity livingentity) {
		if (this.isAlive() && isRolling()) {
			boolean flag = livingentity.isDamageSourceBlocked(this.damageSources().mobAttack(this));
            float f1 = (float) Mth.clamp(livingentity.getDeltaMovement().horizontalDistanceSqr() * 1.5F, 0.2F, 3.0F);
			float f2 = flag ? 0.25F : 1.0F;
			double d1 = this.getX() - livingentity.getX();
			double d2 = this.getZ() - livingentity.getZ();
			double d3 = livingentity.getX() - this.getX();
			double d4 = livingentity.getZ() - this.getZ();
            if (livingentity.hurt(this.damageSources().mobAttack(this), Mth.floor(getAttackDamage() * 1.5F))) {
                this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, livingentity);
                if (this.getTarget() != null && this.getTarget() == livingentity && getRollingGoal() != null) {
                    getRollingGoal().setStopTrigger(true);
                }
                livingentity.knockback(f2 * f1, d1, d2);
            }
		}
	}

	@Override
	protected void blockedByShield(LivingEntity p_21246_) {
		super.blockedByShield(p_21246_);
		if (this.isAlive() && isRolling()) {
			this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			if (getRollingGoal() != null) {
				getRollingGoal().setStopTrigger(true);
			}
			this.knockback(0.8F, p_21246_.getX() - this.getX(), p_21246_.getZ() - this.getZ());
			this.setStun(true);
		}
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
