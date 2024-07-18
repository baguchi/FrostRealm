package baguchan.frostrealm.entity;

import baguchan.frostrealm.message.UpdateMultipartPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FrostPart<T extends Entity> extends net.neoforged.neoforge.entity.PartEntity<T> {

    private static final EntityDataAccessor<Float> DATA_SCALE = SynchedEntityData.defineId(FrostPart.class, EntityDataSerializers.FLOAT);
    private EntityDimensions size;
    protected int newPosRotationIncrements;
    protected double interpTargetX;
    protected double interpTargetY;
    protected double interpTargetZ;
    protected double interpTargetYaw;
    protected double interpTargetPitch;
    public float renderYawOffset;
    public float prevRenderYawOffset;

    private float appliedScale = 1.0F;

    public FrostPart(T p_31014_, float width, float height) {
        super(p_31014_);
        this.size = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_325943_) {
        p_325943_.define(DATA_SCALE, 1.0F);
    }

    public float getScale() {
        return this.entityData.get(DATA_SCALE);
    }

    public void setScale(float scale) {
        this.entityData.set(DATA_SCALE, scale);
    }

    public void setSize(EntityDimensions size) {
        this.size = size;
        this.refreshDimensions();
    }

    public EntityDimensions getSize() {
        if (this.getParent() == null) {
            return size;
        }

        return size.scale(getScale());
    }

    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements) {
        this.interpTargetX = x;
        this.interpTargetY = y;
        this.interpTargetZ = z;
        this.interpTargetYaw = yaw;
        this.interpTargetPitch = pitch;
        this.newPosRotationIncrements = posRotationIncrements;
    }

    @Override
    public void tick() {
        updateLastPos();
        super.tick();
        if (this.newPosRotationIncrements > 0) {
            double d0 = this.getX() + (this.interpTargetX - this.getX()) / (double) this.newPosRotationIncrements;
            double d2 = this.getY() + (this.interpTargetY - this.getY()) / (double) this.newPosRotationIncrements;
            double d4 = this.getZ() + (this.interpTargetZ - this.getZ()) / (double) this.newPosRotationIncrements;
            double d6 = Mth.wrapDegrees(this.interpTargetYaw - (double) this.getYRot());
            this.setYRot((float) ((double) this.getYRot() + d6 / (double) this.newPosRotationIncrements));
            this.setXRot((float) ((double) this.getXRot() + (this.interpTargetPitch - (double) this.getXRot()) / (double) this.newPosRotationIncrements));
            --this.newPosRotationIncrements;
            this.setPos(d0, d2, d4);
            this.setRot(this.getYRot(), this.getXRot());
        }

        while (getYRot() - this.yRotO < -180F) this.yRotO -= 360F;
        while (getYRot() - this.yRotO >= 180F) this.yRotO += 360F;

        while (this.renderYawOffset - this.prevRenderYawOffset < -180F) this.prevRenderYawOffset -= 360F;
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180F) this.prevRenderYawOffset += 360F;

        while (getXRot() - this.xRotO < -180F) this.xRotO -= 360F;
        while (getXRot() - this.xRotO >= 180F) this.xRotO += 360F;

        float f6 = this.getScale();
        if (f6 != this.appliedScale) {
            this.appliedScale = f6;
            this.refreshDimensions();
        }
    }

    public final void updateLastPos() {
        this.moveTo(this.getX(), this.getY(), this.getZ());
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.tickCount++;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag p_31025_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_31028_) {
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return this.getParent().isCurrentlyGlowing();
    }

    @Override
    public boolean isInvisible() {
        return this.getParent().isInvisible();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return this.getParent().interact(player, hand);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.getParent().getPickResult();
    }

    @Override
    public boolean is(Entity p_31031_) {
        return this == p_31031_ || this.getParent() == p_31031_;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        throw new UnsupportedOperationException();
    }

    public UpdateMultipartPacket.PartDataHolder writeData() {
        return new UpdateMultipartPacket.PartDataHolder(
                this.getX(),
                this.getY(),
                this.getZ(),
                this.getYRot(),
                this.getXRot(),
                this.getDimensions(this.getPose()).width(),
                this.getDimensions(this.getPose()).height(),
                this.getDimensions(this.getPose()).fixed(),
                getEntityData().packDirty());

    }

    public void readData(UpdateMultipartPacket.PartDataHolder data) {
        Vec3 vec = new Vec3(data.x(), data.y(), data.z());
        this.setPositionAndRotationDirect(vec.x(), vec.y(), vec.z(), data.yRot(), data.xRot(), 3);
        final float w = data.width();
        final float h = data.height();
        this.setSize(data.fixed() ? EntityDimensions.fixed(w, h) : EntityDimensions.scalable(w, h));
        if (data.data() != null)
            getEntityData().assignValues(data.data());
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose p_31023_) {
        return this.size;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

}