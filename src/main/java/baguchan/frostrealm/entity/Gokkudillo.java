package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Gokkudillo extends Monster implements IGuardMob {
	private static final EntityDataAccessor<Boolean> DATA_GUARD = SynchedEntityData.defineId(Gokkudillo.class, EntityDataSerializers.BOOLEAN);

	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("a0431f61-9dbb-d872-8a44-7e5e4204ae3e");
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(ARMOR_MODIFIER_UUID, "Armor bonus", 16.0D, AttributeModifier.Operation.ADDITION);

	public Gokkudillo(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_GUARD, false);
	}

	public void setGuard(boolean guard) {
		this.entityData.set(DATA_GUARD, guard);
		if (!this.level().isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER);
			if (guard) {
				this.getAttribute(Attributes.ARMOR).addPermanentModifier(ARMOR_MODIFIER);
				this.gameEvent(GameEvent.ENTITY_INTERACT);
			} else {
				this.gameEvent(GameEvent.ENTITY_INTERACT);
			}
		}
	}

	public boolean isGuard() {
		return this.entityData.get(DATA_GUARD);
	}


	public static boolean checkGokkudilloSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FRIGID_STONE.get()) && Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 10.0F).add(Attributes.KNOCKBACK_RESISTANCE, 0.25F).add(Attributes.MOVEMENT_SPEED, 0.24D);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_29615_) {
		super.onSyncedDataUpdated(p_29615_);
	}


	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) {
		if (this.isGuard()) {
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

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected float getStandingEyeHeight(Pose p_21131_, EntityDimensions p_21132_) {
		return p_21132_.height * 0.65F;
	}
}
