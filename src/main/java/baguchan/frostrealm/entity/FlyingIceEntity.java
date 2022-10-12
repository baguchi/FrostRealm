package baguchan.frostrealm.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FlyingIceEntity extends PathfinderMob {

	public FlyingIceEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		setNoGravity(true);
	}

	@Override
	public void tick() {
		super.tick();
		onCollideChange();

		if (this.onGround) {
			setNoGravity(true);
		}
	}


	@Override
	public void travel(Vec3 p_27490_) {
		if (this.isEffectiveAi() && this.isNoGravity()) {
			this.moveRelative(this.getSpeed(), p_27490_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		} else {
			super.travel(p_27490_);
		}
	}


	public boolean canBeCollidedWith() {
		return true;
	}

	public void push(Entity p_33474_) {
	}

	public float getPickRadius() {
		return 0.0F;
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
		if (p_21436_ == MobSpawnType.STRUCTURE) {
			setNoGravity(true);
		} else if (p_21436_ == MobSpawnType.SPAWNER) {
			setNoGravity(true);
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}

	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) {
		if (p_21016_.isBypassInvul() || p_21016_.isFire()) {
			setNoGravity(false);
			return super.hurt(p_21016_, p_21017_);
		} else {
			this.playSound(SoundEvents.GLASS_BREAK, 0.6F, 1.25F);
			setNoGravity(false);
			return false;
		}
	}

	private void onCollideChange() {
		this.reapplyPosition();

		for (Entity entity : this.level.getEntities(this, this.getBoundingBox().move(this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z), EntitySelector.NO_SPECTATORS.and((p_149771_) -> {
			return !p_149771_.isPassengerOfSameVehicle(this);
		}))) {
			if (!(entity instanceof FlyingIceEntity) && !entity.noPhysics) {
				entity.move(MoverType.SHULKER, new Vec3(this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z));
			}
		}

	}
}
