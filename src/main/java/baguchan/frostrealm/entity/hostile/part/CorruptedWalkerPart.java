package baguchan.frostrealm.entity.hostile.part;

import baguchan.frostrealm.message.UpdateMultipartPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class CorruptedWalkerPart<T extends CorruptedWalker> extends net.neoforged.neoforge.entity.PartEntity<T> {

    private static final EntityDataAccessor<Float> DATA_SCALE = SynchedEntityData.defineId(CorruptedWalkerPart.class, EntityDataSerializers.FLOAT);

    public final CorruptedWalker parentMob;
    private EntityDimensions size;
    protected int newPosRotationIncrements;
    protected double interpTargetX;
    protected double interpTargetY;
    protected double interpTargetZ;
    protected double interpTargetYaw;
    protected double interpTargetPitch;
    public float renderYawOffset;
    public float prevRenderYawOffset;


    protected double targetX;
    protected double targetY;
    protected double targetZ;
    private float appliedScale = 1.0F;

    public CorruptedWalkerPart(T p_31014_, float width, float height) {
        super(p_31014_);
        this.size = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
        this.parentMob = p_31014_;
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

    protected void setSize(EntityDimensions size) {
        this.size = size;
        this.refreshDimensions();
    }

    public EntityDimensions getSize() {
        if (parentMob == null) {
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
    public boolean isPickable() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (!this.isInvulnerableTo(damageSource)) {
            return this.parentMob.hurt(damageSource, damage * 0.8F);
        } else {
            return false;
        }
    }

    @Override
    public void resetFallDistance() {
        super.resetFallDistance();
        this.parentMob.resetFallDistance();
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
        /*if (!this.isInWater()) {
            this.updateInWaterStateAndDoWaterCurrentPushing();
        }
*/
        if (this.level() instanceof ServerLevel serverlevel && p_20991_ && this.fallDistance > 0.0F) {
            double d7 = 2F; //fall Distance
            if ((double) this.fallDistance > d7 && !p_20992_.isAir()) {
                double d0 = this.getX();
                double d1 = this.getY();
                double d2 = this.getZ();
                BlockPos blockpos = this.blockPosition();
                if (p_20993_.getX() != blockpos.getX() || p_20993_.getZ() != blockpos.getZ()) {
                    double d3 = d0 - (double) p_20993_.getX() - 0.5;
                    double d5 = d2 - (double) p_20993_.getZ() - 0.5;
                    double d6 = Math.max(Math.abs(d3), Math.abs(d5));
                    d0 = (double) p_20993_.getX() + 0.5 + d3 / d6 * 0.5;
                    d2 = (double) p_20993_.getZ() + 0.5 + d5 / d6 * 0.5;
                }

                float f = (float) Mth.ceil((double) this.fallDistance - d7);
                double d4 = Math.min((double) (0.2F + f / 15.0F), 2.5);
                int i = (int) (150.0 * d4);
                if (this.fallDistance > 10F) {
                    SoundEvent soundevent = this.fallDistance > 20.0F ? SoundEvents.MACE_SMASH_GROUND_HEAVY : SoundEvents.MACE_SMASH_GROUND;
                    serverlevel.playSound(
                            null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.5F, 1.0F
                    );
                    //make big earth shake
                    knockback(serverlevel, this);
                }

                serverlevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), knockbackPredicate(this))
                        .forEach(p_347296_ -> {
                            p_347296_.hurt(this.damageSources().mobAttack(this.parentMob), (float) this.fallDistance * 2);
                            this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK);
                        });
                if (!p_20992_.addLandingEffects((ServerLevel) this.level(), p_20993_, p_20992_, this.parentMob, (int) (i * getScale())))
                    ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, p_20992_).setPos(p_20993_), d0, d1, d2, i, 0.0, 0.0, 0.0, 0.15F);
            }
            BlockPos blockpos1 = this.getOnPos();
            BlockState blockstate1 = this.level().getBlockState(blockpos1);

            this.vibrationAndSoundEffectsFromBlock(blockpos1, blockstate1, true, this.getMovementEmission().emitsEvents());
        }

        super.checkFallDamage(p_20990_, p_20991_, p_20992_, p_20993_);
    }

    private boolean vibrationAndSoundEffectsFromBlock(BlockPos p_286221_, BlockState p_286549_, boolean p_286708_, boolean p_286543_) {
        if (p_286549_.isAir()) {
            return false;
        } else {
            boolean flag = false;
            if ((this.onGround() || flag || this.isOnRails()) && !this.isSwimming()) {
                if (p_286708_) {
                    this.walkingStepSound(p_286221_, p_286549_);
                }

                if (p_286543_) {
                    this.level().gameEvent(GameEvent.STEP, this.position(), GameEvent.Context.of(this, p_286549_));
                }

                return true;
            } else {
                return false;
            }
        }
    }

    private void walkingStepSound(BlockPos p_281828_, BlockState p_282118_) {
        this.playStepSound(p_281828_, p_282118_);
    }

    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundEvents.STONE_STEP, (float) (1.0F + 0.25F * getScale()), 1.0F);
        SoundType soundtype = p_20136_.getSoundType(this.level(), p_20135_, this);
        this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 1.0F + 0.25F * getScale(), soundtype.getPitch());
    }

    private static void knockback(Level p_335716_, CorruptedWalkerPart part) {
        p_335716_.levelEvent(2013, part.getOnPos(), 750);
        p_335716_.getEntitiesOfClass(LivingEntity.class, part.getBoundingBox().inflate(3.5), knockbackPredicate(part))
                .forEach(p_347296_ -> {
                    Vec3 vec3 = p_347296_.position().subtract(part.position());
                    double d0 = getKnockbackPower(part, p_347296_, vec3);
                    Vec3 vec31 = vec3.normalize().scale(d0);
                    if (d0 > 0.0) {
                        p_347296_.push(vec31.x, 0.7F * d0, vec31.z);
                        if (p_347296_ instanceof ServerPlayer serverplayer) {
                            serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
                        }
                    }
                });
    }

    private static Predicate<Entity> knockbackPredicate(CorruptedWalkerPart part) {
        return p_344407_ -> {
            boolean flag;
            boolean flag1;
            boolean flag2;
            boolean flag6;
            label62:
            {
                flag = !p_344407_.isSpectator();
                flag1 = p_344407_ != part && p_344407_ != part.parentMob;
                flag2 = !part.isAlliedTo(p_344407_);
                if (p_344407_ instanceof TamableAnimal tamableanimal && tamableanimal.isTame() && part.getUUID().equals(tamableanimal.getOwnerUUID())) {
                    flag6 = true;
                    break label62;
                }

                flag6 = false;
            }

            boolean flag3;
            label55:
            {
                flag3 = !flag6;
                if (p_344407_ instanceof ArmorStand armorstand && armorstand.isMarker()) {
                    flag6 = false;
                    break label55;
                }

                flag6 = true;
            }

            boolean flag4 = flag6;
            boolean flag5 = part.distanceToSqr(p_344407_) <= Math.pow(3.5, 2.0);
            return flag && flag1 && flag2 && flag3 && flag4 && flag5;
        };
    }

    private static double getKnockbackPower(CorruptedWalkerPart p_338265_, LivingEntity p_338630_, Vec3 p_338866_) {
        return (3.5 - p_338866_.length())
                * 0.5F
                * (double) (p_338265_.fallDistance > 10.0F ? 2 : 1)
                * (1.0 - p_338630_.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    @Override
    public boolean causeFallDamage(float p_146828_, float p_146829_, DamageSource p_146830_) {
        boolean flag = super.causeFallDamage(p_146828_, p_146829_, p_146830_);
        if (flag) {
            parentMob.causeFallDamage(p_146828_, p_146829_, p_146830_);
            return true;
        } else {
            return flag;
        }
    }

    @Override
    public boolean is(Entity p_31031_) {
        return this == p_31031_ || this.parentMob == p_31031_;
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