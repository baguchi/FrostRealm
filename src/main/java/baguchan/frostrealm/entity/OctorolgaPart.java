package baguchan.frostrealm.entity;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.MessageHurtMultipart;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OctorolgaPart extends LivingEntity implements IHurtableMultipart {

	private static final EntityDataAccessor<Boolean> TAIL = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BODYINDEX = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<BlockPos>> OFFSET_POS = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);


	public EntityDimensions multipartSize;
	protected float radius;
	protected float angleYaw;
	protected float offsetY;
	protected float damageMultiplier = 1;

	public double prevXPart;
	public double prevYPart;
	public double prevZPart;
	public double xPart;
	public double yPart;
	public double zPart;

	public OctorolgaPart(EntityType t, Level world) {
		super(t, world);
		multipartSize = t.getDimensions();
	}

	public OctorolgaPart(EntityType t, LivingEntity parent, float radius, float angleYaw, float offsetY) {
		super(t, parent.level);
		this.setParent(parent);
		this.radius = radius;
		this.angleYaw = (angleYaw + 90.0F) * ((float) Math.PI / 180.0F);
		this.offsetY = offsetY;
	}

	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public boolean startRiding(Entity entityIn) {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.15F);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.getParentId() != null) {
			compound.putUUID("ParentUUID", this.getParentId());
		}
		compound.putBoolean("TailPart", isTail());
		compound.putInt("BodyIndex", getBodyIndex());
		compound.putFloat("PartAngle", angleYaw);
		compound.putFloat("PartRadius", radius);
		if (this.getOffsetPos() != null) {
			compound.put("OffsetPos", NbtUtils.writeBlockPos(this.getOffsetPos()));
		}
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.hasUUID("ParentUUID")) {
			this.setParentId(compound.getUUID("ParentUUID"));
		}
		this.setTail(compound.getBoolean("TailPart"));
		this.setBodyIndex(compound.getInt("BodyIndex"));
		this.angleYaw = compound.getFloat("PartAngle");
		this.radius = compound.getFloat("PartRadius");
		if (compound.contains("OffsetPos")) {
			this.setOffsetPos(NbtUtils.readBlockPos(compound.getCompound("OffsetPos")));
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PARENT_UUID, Optional.empty());
		this.entityData.define(TAIL, false);
		this.entityData.define(BODYINDEX, 0);
		this.entityData.define(OFFSET_POS, Optional.empty());
	}

	public boolean isSensitiveToWater() {
		return true;
	}

	public boolean isOnFire() {
		return false;
	}

	public boolean isPushedByFluid() {
		return false;
	}

	@Nullable
	public UUID getParentId() {
		return this.entityData.get(PARENT_UUID).orElse(null);
	}

	public void setParentId(@Nullable UUID uniqueId) {
		this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
	}

	@Nullable
	public BlockPos getOffsetPos() {
		return this.entityData.get(OFFSET_POS).orElse(null);
	}

	public void setOffsetPos(@Nullable BlockPos offset) {
		this.entityData.set(OFFSET_POS, Optional.ofNullable(offset));
	}


	public void setInitialPartPos(Entity parent) {
		this.setPos(parent.xo + this.radius * Math.cos(parent.getYRot() * (Math.PI / 180.0F) + this.angleYaw), parent.yo + this.offsetY, parent.zo + this.radius * Math.sin(parent.getYRot() * (Math.PI / 180.0F) + this.angleYaw));
	}

	@Override
	public void tick() {
		isInsidePortal = false;
		if (this.tickCount > 10) {
			Entity parent = getParent();
			refreshDimensions();

			if (parent != null && !level.isClientSide) {
				this.setNoGravity(true);

				if (this.getOffsetPos() == null) {
					this.setPos(parent.xo, parent.yo + this.offsetY + 0.25F * (getBodyIndex() + 1), parent.zo);
				} else {
					this.movePart();
					this.setPos(parent.xo - this.xPart, parent.yo + this.offsetY - this.yPart, parent.zo - this.zPart);
				}
				double d0 = parent.getX() - this.getX();
				double d1 = parent.getY() - this.getY();
				double d2 = parent.getZ() - this.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double) (180F / (float) Math.PI)));
				this.setXRot(this.limitAngle(this.getXRot(), f2, 5.0F));
				this.markHurt();
				this.setYRot(parent.yRotO);
				this.yHeadRot = this.getYRot();
				this.yBodyRot = this.yRotO;
				if (parent instanceof LivingEntity) {
					if (!level.isClientSide && (((LivingEntity) parent).hurtTime > 0 || ((LivingEntity) parent).deathTime > 0)) {
						FrostRealm.sendMSGToAll(new MessageHurtMultipart(this.getId(), parent.getId(), 0));
						this.hurtTime = ((LivingEntity) parent).hurtTime;
						this.deathTime = ((LivingEntity) parent).deathTime;
					}
				}
				this.pushEntities();
				if (parent.isRemoved() && !level.isClientSide) {
					this.remove(RemovalReason.DISCARDED);
				}
			} else if (tickCount > 20 && !level.isClientSide) {
				tickDeath();
			}
		}
		super.tick();
	}

	protected void tickDeath() {
		this.setNoGravity(false);
		++this.deathTime;
		if (this.deathTime == 100 && !this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.DISCARDED);
		}


		Vec3 vec3 = this.getDeltaMovement();
		double d5 = vec3.x;
		double d6 = vec3.y;
		double d1 = vec3.z;

		if (!this.onGround) {
			this.setDeltaMovement(d5, d6 - 0.1F, d1);
		}
		double d7 = this.getX() + d5;
		double d2 = this.getY() + d6;
		double d3 = this.getZ() + d1;

		this.setPos(d7, d2, d3);
	}

	private void movePart() {
		if (getOffsetPos() != null) {

			this.prevXPart = this.xPart;
			this.prevYPart = this.yPart;
			this.prevZPart = this.zPart;
			double d0 = this.getX() - this.getOffsetPos().getX();
			double d1 = this.getY() - this.getOffsetPos().getY();
			double d2 = this.getZ() - this.getOffsetPos().getZ();
			double d3 = 15.0D;
			if (d0 > 1.0D) {
				this.xPart = this.getX() + 6.0D;
				this.prevXPart = this.xPart;
			}

			if (d2 > 6.0D) {
				this.zPart = this.getZ() + 6.0D;
				this.prevZPart = this.zPart;
			}

			if (d1 > 6.0D) {
				this.yPart = this.getY() + 6.0D;
				this.prevYPart = this.yPart;
			}

			if (d0 < -6.0D) {
				this.xPart = this.getX() - 6.0D;
				this.prevXPart = this.xPart;
			}

			if (d2 < -6.0D) {
				this.zPart = this.getZ() - 6.0D;
				this.prevZPart = this.zPart;
			}

			if (d1 < -6.0D) {
				this.yPart = this.getY() - 6.0D;
				this.prevYPart = this.yPart;
			}

			this.xPart += d0 * 0.25D;
			this.zPart += d2 * 0.25D;
			this.yPart += d1 * 0.25D;
		}
	}

	public Entity getParent() {
		UUID id = getParentId();
		if (id != null && !level.isClientSide) {
			return ((ServerLevel) level).getEntity(id);
		}
		return null;
	}

	public void setParent(Entity entity) {
		this.setParentId(entity.getUUID());
	}

	@Override
	public boolean is(net.minecraft.world.entity.Entity entity) {
		return this == entity || this.getParent() == entity;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public HumanoidArm getMainArm() {
		return null;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void pushEntities() {
		List<Entity> entities = this.level.getEntities(this, this.getBoundingBox().expandTowards(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		Entity parent = this.getParent();
		if (parent != null) {
			entities.stream().filter(entity -> entity != parent && !(entity instanceof OctorolgaPart) && !entity.fireImmune()).forEach(entity -> entity.setSecondsOnFire(10));

		}
	}

	public InteractionResult interact(Player player, InteractionHand hand) {
		Entity parent = getParent();

		return parent != null ? parent.interact(player, hand) : InteractionResult.PASS;
	}

	@Override
	public boolean hurt(DamageSource source, float damage) {
		Entity parent = getParent();
		boolean prev = parent != null;
		boolean flag = false;


		if (source.isFire() || source.isProjectile() && !source.isBypassArmor() && !source.isBypassMagic() && !source.isBypassInvul() && !source.isMagic()) {
			return false;
		} else {

			if (source == DamageSource.DROWN) {
				damage *= 3F;
			}
			if (source == DamageSource.FREEZE) {
				damage *= 1.5F;
			} else if (source.isExplosion()) {
				damage *= 1.5F;
			} else {
				damage *= 0.15F;
			}
		}

		if (prev) {

			if (!this.level.isClientSide() && parent.hurt(source, damage * 0.25F)) {
				FrostRealm.sendMSGToAll(new MessageHurtMultipart(this.getId(), parent.getId(), damage * this.damageMultiplier));
			}
			flag = super.hurt(source, damage);
		}
		return flag;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		return source == DamageSource.FALL || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || source == DamageSource.LAVA || source.isFire() || super.isInvulnerableTo(source);
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return ImmutableList.of();
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {

	}

	public boolean isTail() {
		return this.entityData.get(TAIL).booleanValue();
	}

	public void setTail(boolean tail) {
		this.entityData.set(TAIL, Boolean.valueOf(tail));
	}

	public int getBodyIndex() {
		return this.entityData.get(BODYINDEX);
	}

	public void setBodyIndex(int index) {
		this.entityData.set(BODYINDEX, index);
	}

	public boolean shouldNotExist() {
		Entity parent = getParent();
		return !parent.isAlive();
	}

	@Override
	public void onAttackedFromServer(LivingEntity parent, float damage, DamageSource damageSource) {
		if (parent.deathTime > 0) {
			this.deathTime = parent.deathTime;
		}
		if (parent.hurtTime > 0) {
			this.hurtTime = parent.hurtTime;
		}
	}

	public boolean shouldContinuePersisting() {
		return isAddedToWorld() || this.isRemoved();
	}
}