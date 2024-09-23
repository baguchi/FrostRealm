package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.entity.movecontrol.CellingMoveControl;
import baguchan.frostrealm.entity.path.CellingPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CellingMonster extends Monster {
    private static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(CellingMonster.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(CellingMonster.class, EntityDataSerializers.BYTE);

    private boolean isUpsideDownNavigator;
    public float attachChangeProgress;
    public float prevAttachChangeProgress;
    public Direction prevAttachDir = Direction.DOWN;

    protected CellingMonster(EntityType<? extends CellingMonster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        switchNavigator(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACHED_FACE, Direction.DOWN);
        builder.define(CLIMBING, (byte) 0);
    }

    @Override
    public void travel(Vec3 p_32394_) {
        if (this.isControlledByLocalInstance() && this.getAttachFacing() != Direction.DOWN) {
            this.moveRelative(0.01F, p_32394_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(p_32394_);
        }
    }

    public void tick() {
        this.prevAttachChangeProgress = this.attachChangeProgress;
        super.tick();
        if (attachChangeProgress > 0F) {
            attachChangeProgress -= 0.1F;
        }
        Vec3 vector3d = this.getDeltaMovement();
        if (!this.level().isClientSide) {
            if (this.onGround() || this.isInWaterOrBubble() || this.isInLava() || this.isInFluidType()) {
                this.entityData.set(ATTACHED_FACE, Direction.DOWN);
            } else if (this.verticalCollision) {
                this.entityData.set(ATTACHED_FACE, Direction.UP);
            } else {
                Direction closestDirection = Direction.DOWN;
                double closestDistance = 100D;
                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    BlockPos antPos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));
                    BlockPos offsetPos = antPos.relative(dir);
                    Vec3 offset = Vec3.atCenterOf(offsetPos);
                    if (closestDistance > this.position().distanceTo(offset) && level().loadedAndEntityCanStandOnFace(offsetPos, this, dir.getOpposite())) {
                        closestDistance = this.position().distanceTo(offset);
                        closestDirection = dir;
                    }
                }
                this.entityData.set(ATTACHED_FACE, closestDirection);
            }
        }
        boolean flag = false;
        final Direction attachmentFacing = this.getAttachFacing();
        if (attachmentFacing != Direction.DOWN) {
            if (attachmentFacing == Direction.UP) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 1, 0));
            } else {
                if (!this.horizontalCollision) {
                    Vec3 vec = Vec3.atLowerCornerOf(attachmentFacing.getNormal());
                    this.setDeltaMovement(this.getDeltaMovement().add(vec.normalize().multiply(0.5F, 0.5F, 0.5F)));
                }
            }
        }
        if (attachmentFacing != Direction.DOWN) {
            this.setNoGravity(true);
        } else {
            this.setNoGravity(false);
        }
        if (prevAttachDir != attachmentFacing) {
            attachChangeProgress = 1F;
        }
        this.prevAttachDir = attachmentFacing;
        if (!this.level().isClientSide) {
            if (attachmentFacing != Direction.UP && !this.isUpsideDownNavigator) {
                switchNavigator(false);
            }
            if (attachmentFacing == Direction.DOWN && this.isUpsideDownNavigator) {
                switchNavigator(true);
            }
        }
    }

    public boolean onClimbable() {
        return false;
    }

    @Override
    protected float getFlyingSpeed() {
        return this.getSpeed() * 0.1F;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_33432_) {
        super.readAdditionalSaveData(p_33432_);
        this.setAttachFace(Direction.from3DDataValue(p_33432_.getByte("AttachFace")));
    }


    @Override
    public void addAdditionalSaveData(CompoundTag p_33443_) {
        super.addAdditionalSaveData(p_33443_);
        p_33443_.putByte("AttachFace", (byte) this.getAttachFacing().get3DDataValue());
    }

    private void setAttachFace(Direction attachFace) {
        this.entityData.set(ATTACHED_FACE, attachFace);
    }

    public Direction getAttachFacing() {
        return this.entityData.get(ATTACHED_FACE);
    }


    public void switchNavigator(boolean rightsideUp) {
        if (rightsideUp) {
            this.moveControl = new CellingMoveControl(this);
            this.navigation = new CellingPathNavigation(this, level());
            this.isUpsideDownNavigator = false;
        } else {
            this.moveControl = new CellingMoveControl(this);
            this.navigation = new CellingPathNavigation(this, level());
            this.isUpsideDownNavigator = true;
        }
    }
}
