package baguchan.frostrealm.entity.hostile.part;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

/*
 * Based from Twilight Forest Hydra Code.
 * Thanks Twilight Forest Team!
 * https://github.com/TeamTwilight/twilightforest/blob/1.21.x/src/main/java/twilightforest/entity/boss/HydraHeadContainer.java
 */
public class CorruptedWalkerPartContainer {
    private final int parentPartNum;
    private final int connectPartNum;
    private final CorruptedWalkerPart[] parts;
    private final CorruptedWalkerPart parentPart;
    private double targetX;
    private double targetY;
    private double targetZ;
    private final CorruptedWalker parent;
    private boolean targetMove;
    private boolean stuckMode;
    private Vec3 offset = Vec3.ZERO;

    // state positions, where is each state positioned?
    private final float[] stateLength = new float[CorruptedWalker.MAX_PART];
    private final float[] stateXRotations = new float[CorruptedWalker.MAX_PART];
    private final float[] stateYRotations = new float[CorruptedWalker.MAX_PART];

    @SuppressWarnings({"this-escape", "unchecked"})
    public CorruptedWalkerPartContainer(CorruptedWalker parent, int number, int connectPartNum, CorruptedWalkerPart parentPart, EntityDimensions connectPartSize) {
        this.parentPartNum = number;
        this.parent = parent;
        this.connectPartNum = connectPartNum;
        this.parentPart = parentPart;

        this.parts = new CorruptedWalkerPart[connectPartNum];
        for (int i = 0; i < this.parts.length; i++) {
            this.parts[i] = new CorruptedWalkerPart(parent, connectPartSize.width(), connectPartSize.height());
        }
        for (int i = 0; i < CorruptedWalker.MAX_PART; i++) {
            this.stateLength[i] = 0;
            this.stateXRotations[i] = 0;
            this.stateYRotations[i] = 0;
        }

        this.setPosition();
        this.setConnectPartPosition();
        this.setupStateRotations();
    }

    protected void setupStateRotations() {
        this.setInitState(0, 0, 60, this.connectPartNum * 0.3F);
        this.setInitState(1, 0, -60, this.connectPartNum * 0.3F);
        this.setInitState(2, 0, 120, this.connectPartNum * 0.3F);
        this.setInitState(3, 0, -120, this.connectPartNum * 0.3F);

    }

    private void setInitState(int head, float xRotation, float yRotation, float partLength) {
        this.stateXRotations[head] = xRotation;
        this.stateYRotations[head] = yRotation;
        this.stateLength[head] = partLength;
    }

    protected void setConnectPartPosition(double startX, double startY, double startZ, float startYaw) {

        double endX = this.parentPart.getX();
        double endY = this.parentPart.getY();
        double endZ = this.parentPart.getZ();
        float endYaw = this.parentPart.getYRot();
        float endPitch = this.parentPart.getXRot();

        for (; startYaw - endYaw < -180F; endYaw -= 360F) {
        }
        for (; startYaw - endYaw >= 180F; endYaw += 360F) {
        }
        for (; 0.0F - endPitch < -180F; endPitch -= 360F) {
        }
        for (; 0.0F - endPitch >= 180F; endPitch += 360F) {
        }

        // translate the end position back 1 unit
        /*if (endPitch > 0) {
            // if we are looking down, don't raise the first part position, it looks weird
            Vec3 vector = new Vec3(0.0D, 0.0D, -1.0D).yRot((-endYaw * 3.141593F) / 180.0F);
            endX += vector.x();
            endY += vector.y();
            endZ += vector.z();
        } else {
            // but if we are looking up, lower it or it goes through the crest
            Vec3 vector = this.parentPart.getLookAngle();
            float dist = 1.0f;

            endX -= vector.x() * dist;
            endY -= vector.y() * dist;
            endZ -= vector.z() * dist;

        }*/

        float factor;

        factor = 0.00F;
        for (int i = 0; i < this.parts.length; ++i) {
            CorruptedWalkerPart part = this.parts[i];
            float length = (float) i / this.parts.length;
            factor = length;
            part.setPos(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
            part.setYRot(endYaw + (startYaw - endYaw) * factor);
            part.setXRot(endPitch + ((float) 0 - endPitch) * factor);
        }

    }

    protected void setConnectPartPosition() {
        // set part positions
        Vec3 vector = null;
        float width = this.parent.getBbWidth() / 2;
        if (this.parentPartNum == 0) {
            vector = new Vec3(-width, 0, width);
        }
        if (this.parentPartNum == 1) {
            vector = new Vec3(width, 0, width);
        }
        if (this.parentPartNum == 2) {
            vector = new Vec3(-width, 0, -width);
        }
        if (this.parentPartNum == 3) {
            vector = new Vec3(width, 0, -width);
        }
        if (this.parentPartNum == 4) {
            vector = new Vec3(0, 0, -width);
        }

        if (this.parentPartNum == 5) {
            vector = new Vec3(-width, 0, 0);
        }
        vector = vector.yRot((-(this.parent.yBodyRot) * Mth.PI) / 180.0F);
        this.setConnectPartPosition(this.parent.getX() + vector.x(), this.parent.getY() + vector.y(), this.parent.getZ() + vector.z(), this.parent.yBodyRot);
    }

    protected void setPosition() {
        // set parentPart positions
        Vec3 vector;
        double dx, dy, dz;

        float partLength = this.getCurrentPartLength();
        float xRotation = this.getCurrentPartXRotation();
        float yRotation = this.getCurrentPartYRotation();


        vector = new Vec3(0, 0, partLength); // -53 = 3.3125
        vector = vector.xRot((xRotation * Mth.PI) / 180.0F);
        vector = vector.yRot((-(this.parent.yBodyRot + yRotation) * Mth.PI) / 180.0F);

        //move when move target has
        dx = this.parent.getX() + vector.x();
        dy = this.parent.getY() + vector.y();
        dz = this.parent.getZ() + vector.z();
        offset = vector;
        this.parentPart.setPos(dx, dy, dz);
    }

    protected void updateOffset() {
        Vec3 vector;
        float partLength = this.getCurrentPartLength();
        float xRotation = this.getCurrentPartXRotation();
        float yRotation = this.getCurrentPartYRotation();

        vector = new Vec3(0, 0, partLength); // -53 = 3.3125
        vector = vector.xRot((xRotation * Mth.PI) / 180.0F);
        vector = vector.yRot((-(this.parent.yBodyRot + yRotation) * Mth.PI) / 180.0F);

        offset = vector.scale(this.parent.getScale());
    }

    protected void movePosition() {
        double dx, dy, dz;
        dx = 0;
        dy = this.parentPart.noPhysics ? 0 : -this.parent.getAttributeValue(Attributes.GRAVITY) * 8F;
        dz = 0;

        float partLength = this.getCurrentPartLength();
        float xRotation = this.getCurrentPartXRotation();
        float yRotation = this.getCurrentPartYRotation();

        float speed = parent.getSpeed() / 0.2F;
        float scale = this.getParentPart().getScale();

        boolean flag = Mth.abs(Mth.degreesDifference(parent.getYRot() + yRotation, this.parent.yBodyRot + yRotation)) != 0;
        Vec3 vector = offset.yRot((-(this.parent.yBodyRot) * Mth.PI) / 180.0F);

        //if segment rotate is wrong. change position
        if (flag) {

            dx = (this.parent.getX() + vector.x - this.parentPart.getX()) * 0.2 * speed;
            dy = (this.parent.getY() + vector.y - this.parentPart.getY()) * 0.2 * speed;
            dz = (this.parent.getZ() + vector.z - this.parentPart.getZ()) * 0.2 * speed;
        }

        float f = this.connectPartNum * 0.3F * scale;
        if (this.targetMove) {
            dx += (targetX - this.parentPart.getX()) * 0.2 * speed;
            dy += (targetY - this.parentPart.getY()) * 0.2 * speed;
            dz += (targetZ - this.parentPart.getZ()) * 0.2 * speed;

        } else if (this.parentPart.distanceToSqr(new Vec3(this.parent.getX() + this.offset.x, this.parent.getY() + this.offset.y, this.parent.getZ() + this.offset.z)) > f * f + 4 * 4) {
            this.stuckMode = true;
            this.targetMove = false;
        }

        if (this.targetMove && this.parentPart.distanceToSqr(new Vec3(this.parent.getX() + this.offset.x, this.parent.getY() + this.offset.y, this.parent.getZ() + this.offset.z)) > f * f + 4 * 4) {
            this.stuckMode = true;
            this.targetMove = false;
        }


        //If stuck. start moving segment
        if (this.stuckMode) {
            if (this.parentPart.distanceToSqr(this.parent) > f * f * 1.0F) {

                dx = (this.parent.getX() + vector.x - this.parentPart.getX()) * 0.2 * speed;
                dy = (this.parent.getY() + vector.y - this.parentPart.getY()) * 0.2 * speed;
                dz = (this.parent.getZ() + vector.z - this.parentPart.getZ()) * 0.2 * speed;

            } else {
                this.stuckMode = false;
                this.parentPart.noPhysics = false;
            }
        }

        this.parentPart.setDeltaMovement(new Vec3(dx, dy, dz));
        if (this.targetMove && this.parentPart.distanceToSqr(new Vec3(targetX, targetY, targetZ)) <= 0.5F) {
            this.parentPart.noPhysics = false;
            this.stuckMode = false;
            this.targetMove = false;
        }
    }

    public boolean hasEmptyCollisionOnLeg(BlockPos blockPos) {
        var rBlock = this.parentPart.level().noCollision(this.parentPart, this.parentPart.getBoundingBox());
        return rBlock;
    }

    private float getCurrentPartLength() {
        float prevLength = this.stateLength[this.parentPartNum];
        float curLength = this.stateLength[this.parentPartNum];

        return Mth.clampedLerp(prevLength, curLength, 1F);
    }

    private float getCurrentPartXRotation() {
        float prevRotation = this.stateXRotations[this.parentPartNum];
        float currentRotation = this.stateXRotations[this.parentPartNum];

        return Mth.clampedLerp(prevRotation, currentRotation, 1F);
    }

    private float getCurrentPartYRotation() {
        float prevRotation = this.stateYRotations[this.parentPartNum];
        float currentRotation = this.stateYRotations[this.parentPartNum];
        return Mth.clampedLerp(prevRotation, currentRotation, 1F);
    }

    private void faceIdle(float yawConstraint, float pitchConstraint) {
        float angle = (((this.parent.getYRot()) * 3.141593F) / 180F);
        float distance = 30.0F;

        double dx = this.parent.getX() - Mth.sin(angle) * distance;
        double dy = this.parent.getY();
        double dz = this.parent.getZ() + Mth.cos(angle) * distance;

        faceVec(dx, dy, dz, yawConstraint, pitchConstraint);
    }

    private void faceVec(double x, double y, double z, float yawConstraint, float pitchConstraint) {
        double xOffset = x - this.parentPart.getX();
        double zOffset = z - this.parentPart.getZ();
        double yOffset = (this.parentPart.getY() + 1.0) - y;

        double distance = Mth.sqrt((float) (xOffset * xOffset + zOffset * zOffset));
        float xyAngle = (float) ((Math.atan2(zOffset, xOffset) * 180D) / Math.PI) - 90F;
        float zdAngle = (float) (-((Math.atan2(yOffset, distance) * 180D) / Math.PI));
        this.parentPart.setXRot(-updateRotation(this.parentPart.getXRot(), zdAngle, pitchConstraint));
        this.parentPart.setYRot(updateRotation(this.parentPart.getYRot(), xyAngle, yawConstraint));
    }


    private float updateRotation(float current, float intended, float increment) {
        float delta = Mth.wrapDegrees(intended - current);

        if (delta > increment) {
            delta = increment;
        }

        if (delta < -increment) {
            delta = -increment;
        }

        return Mth.wrapDegrees(current + delta);
    }


    public void tick() {
        //setup
        if (this.parentPart.tickCount < 4) {
            this.setPosition();
        } else {
            //make apply scale
            this.updateOffset();
        }
        // check head state
        this.parentPart.tick();
        // part updates
        this.performOnAllParts(CorruptedWalkerPart::tick);
        // only actually do these things on the server
        if (!this.parent.level().isClientSide()) {
            this.movePosition();
            this.parentPart.move(MoverType.SELF, this.parentPart.getDeltaMovement());

            this.faceIdle(1.5F, this.parent.getMaxHeadXRot());

            this.pathFind();
        }
        this.setConnectPartPosition();
    }

    public void setTarget(Vec3 vec3) {
        this.setTarget(vec3.x, vec3.y, vec3.z);
    }

    public void setTarget(double targetX, double targetY, double targetZ) {
        //prevent do the path moving
        if (!this.targetMove) {
            this.targetX = targetX;
            this.targetY = targetY;
            this.targetZ = targetZ;
            this.targetMove = true;
            this.parentPart.noPhysics = true;
        }
    }


    private void pathFind() {

        if (!this.parent.getNavigation().isDone() && this.parent.getNavigation().getPath() != null && this.parent.movingPartIndex == parentPartNum) {
            Vec3 vec3 = this.parent.getNavigation().getPath().getNextNodePos().getCenter().add(offset);

            BlockPos blockPos = getTopNonCollidingPos(parent.level(), (int) vec3.x, (int) vec3.y, (int) vec3.z);

            this.setTarget(blockPos.getCenter().add(0, -0.5F, 0));
        } else if (this.parent.movingPartIndex != parentPartNum) {
            this.parentPart.noPhysics = false;
            this.stuckMode = false;
            this.targetMove = false;
        }
    }

    private static BlockPos getTopNonCollidingPos(LevelReader p_47066_, int x, int y, int z) {
        int count = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);
        if (!p_47066_.getBlockState(blockpos$mutableblockpos).isAir()) {
            do {
                blockpos$mutableblockpos = blockpos$mutableblockpos.move(Direction.UP);
                count++;
            } while (!p_47066_.getBlockState(blockpos$mutableblockpos).isAir() && count < 4);
        }
        return blockpos$mutableblockpos.immutable();
    }

    public void performOnAllParts(Consumer<CorruptedWalkerPart> consumer) {
        for (int i = 0; i < this.getParts().length; i++) {
            consumer.accept(this.getParts()[i]);
        }
    }

    public CorruptedWalkerPart getParentPart() {
        return parentPart;
    }

    public int getConnectPartNum() {
        return connectPartNum;
    }

    public CorruptedWalkerPart[] getParts() {
        return parts;
    }
}
