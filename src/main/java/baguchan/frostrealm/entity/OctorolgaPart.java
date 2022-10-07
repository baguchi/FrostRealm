package baguchan.frostrealm.entity;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.MessageHurtMultipart;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.Mob;
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

public class OctorolgaPart extends Mob implements IHurtableMultipart {

	private static final EntityDataAccessor<Integer> BODYINDEX = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(OctorolgaPart.class, EntityDataSerializers.DIRECTION);


	public EntityDimensions multipartSize;
	protected float damageMultiplier = 1;

	public double prevXPart;
	public double prevYPart;
	public double prevZPart;
	public double xPart;
	public double yPart;
	public double zPart;
	public Vec3 targetOffset;

	public OctorolgaPart(EntityType t, Level world) {
		super(t, world);
		multipartSize = t.getDimensions();
	}

	public OctorolgaPart(EntityType t, LivingEntity parent) {
		super(t, parent.level);
		this.setParent(parent);
	}

	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public boolean startRiding(Entity entityIn) {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.MOVEMENT_SPEED, 0.15F);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.getParentId() != null) {
			compound.putUUID("ParentUUID", this.getParentId());
		}
		compound.putInt("BodyIndex", getBodyIndex());
		if (this.getTargetPos() != null) {
			compound.putDouble("TargetPosX", this.getTargetPos().x);
			compound.putDouble("TargetPosY", this.getTargetPos().y);
			compound.putDouble("TargetPosZ", this.getTargetPos().z);
		}

		compound.putByte("Direction", (byte) this.getDirection().get3DDataValue());
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.hasUUID("ParentUUID")) {
			this.setParentId(compound.getUUID("ParentUUID"));
		}
		this.setBodyIndex(compound.getInt("BodyIndex"));
		if (compound.contains("TargetPosX") && compound.contains("TargetPosY") && compound.contains("TargetPosZ")) {
			this.setTargetPos(new Vec3(compound.getInt("TargetPosX"), compound.getInt("TargetPosY"), compound.getInt("TargetPosZ")));
		}

		this.setDirection(Direction.from3DDataValue(compound.getByte("Direction")));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PARENT_UUID, Optional.empty());
		this.entityData.define(BODYINDEX, 0);
		this.entityData.define(DIRECTION, Direction.NORTH);
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

	public Vec3 getTargetPos() {
		return this.targetOffset;
	}

	public void setTargetPos(Vec3 offset) {
		this.targetOffset = offset;
	}


	public Direction getDirection() {
		return this.entityData.get(DIRECTION);
	}

	public void setDirection(Direction direction) {
		this.entityData.set(DIRECTION, direction);
	}


	public void setInitialPartPos(Entity parent) {
		this.setPos(parent.xo, parent.yo, parent.zo);
	}

	@Override
	public void tick() {
		isInsidePortal = false;
		Entity parent = getParent();
		refreshDimensions();

		if (parent != null && !level.isClientSide) {
			if (this.isAlive()) {
				this.setNoGravity(true);
			}
			Vec3 vec3 = new Vec3(this.getDirection().getStepX(), this.getDirection().getStepY(), this.getDirection().getStepZ()); // -53 = 3.3125
			vec3 = vec3.yRot((-(this.getParent().getYRot()) * Mth.PI) / 180.0F);


			double startX = vec3.x();
			double startY = vec3.y();
			double startZ = vec3.z();

			float idleY = Mth.sin(this.tickCount / 10.0f) * 0.07F * getBodyIndex() * 0.2F;

			if (this.onGround) {
				idleY += 1.0F;
			} else {
				idleY -= 0.2F;
			}
			this.movePart();
			this.setPos(parent.xo + this.xPart + (startX * 0.4F), parent.yo + this.yPart + (idleY + (startY * 0.4F)), parent.zo + this.zPart + (startZ * 0.4F));

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
			this.remove(RemovalReason.DISCARDED);
		}

		super.tick();
	}

	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20 && !this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.DISCARDED);
		}
	}

	private void movePart() {

		if (getParent() != null && getTargetPos() != null) {
			double d0 = this.getTargetPos().x - getParent().getX();
			double d1 = this.getTargetPos().y - getParent().getY();
			double d2 = this.getTargetPos().z - getParent().getZ();
			if (d0 > 0.25F) {
				this.xPart = 0.25F;
				this.prevXPart = this.xPart;
			}

			if (d2 > 0.25F) {
				this.zPart = 0.25F;
				this.prevZPart = this.zPart;
			}

			if (d1 > 0.25F) {
				this.yPart = 0.25F;
				this.prevYPart = this.yPart;
			}

			if (d0 < -0.25F) {
				this.xPart = -0.25F;
				this.prevXPart = this.xPart;
			}

			if (d2 < -0.25F) {
				this.zPart = -0.25F;
				this.prevZPart = this.zPart;
			}

			if (d1 < -0.25F) {
				this.yPart = -0.25F;
				this.prevYPart = this.yPart;
			}

			this.xPart += d0 * 0.25F * 0.005F;
			this.zPart += d2 * 0.25F * 0.005F;
			this.yPart += d1 * 0.25F * 0.005F;
		}
		if (getParent() != null && getParent() instanceof Mob && ((Mob) getParent()).getTarget() != null) {
			//set target pos and setting pos for next child 's target pos
			setTargetPos(((Mob) getParent()).getTarget().position());
			setTarget(((Mob) getParent()).getTarget());
		} else {
			setTargetPos(null);
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
		if (this.isAlive()) {
			List<Entity> entities = this.level.getEntities(this, this.getBoundingBox().expandTowards(0.20000000298023224D, 0.0D, 0.20000000298023224D));
			Entity parent = this.getParent();
			if (parent != null && parent instanceof LivingEntity) {
				entities.stream().filter(entity -> entity != parent && !(entity instanceof OctorolgaPart) && !entity.fireImmune()).forEach(entity -> {
					entity.setSecondsOnFire(10);
					entity.hurt(DamageSource.mobAttack((LivingEntity) parent).setIsFire(), 6.0F);
				});
			}
		}
	}

	@Override
	public boolean canBeLeashed(Player p_21418_) {
		return false;
	}

	@Override
	public boolean isLeashed() {
		return false;
	}

	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		Entity parent = getParent();

		return parent != null ? parent.interact(player, hand) : InteractionResult.PASS;
	}

	@Override
	public boolean hurt(DamageSource source, float damage) {
		Entity parent = getParent();
		boolean prev = parent != null;
		boolean flag = false;


		if (source.isFire() || source.isProjectile() && !source.isMagic()) {
			return false;
		} else {
			if (source == DamageSource.DROWN) {
				damage *= 3F;
			} else if (source == DamageSource.FREEZE) {
				damage *= 1.5F;
			} else if (source.isExplosion()) {
				damage *= 1.5F;
			} else {
				damage *= 0.65F;
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
		return source == DamageSource.IN_WALL || source == DamageSource.CRAMMING || source == DamageSource.FALLING_BLOCK || source == DamageSource.LAVA || source.isFire() || super.isInvulnerableTo(source);
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
	public void push(Entity p_21294_) {
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