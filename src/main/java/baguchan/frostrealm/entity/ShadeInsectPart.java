package baguchan.frostrealm.entity;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.HurtMultipartMessage;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

import static baguchan.frostrealm.entity.ShadeInsect.TICKS_PER_FLAP;

public class ShadeInsectPart extends LivingEntity implements IHurtableMultipart {
    private static final EntityDataAccessor<Integer> BODYINDEX = SynchedEntityData.defineId(ShadeInsectPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(ShadeInsectPart.class, EntityDataSerializers.OPTIONAL_UUID);
    public EntityDimensions multipartSize;
    protected float radius;
    protected float angleYaw;
    protected float offsetY;
    protected float damageMultiplier = 0.95F;

    public ShadeInsectPart(EntityType t, Level world) {
        super(t, world);
        multipartSize = t.getDimensions();
    }

    public ShadeInsectPart(EntityType t, LivingEntity parent, float radius, float angleYaw, float offsetY) {
        super(t, parent.level);
        this.setParent(parent);
        this.radius = radius;
        this.angleYaw = (angleYaw + 90.0F) * (float) Math.PI / 180.0F;
        this.offsetY = offsetY;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PARENT_UUID, Optional.empty());
        this.entityData.define(BODYINDEX, 0);
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getParentId() != null) {
            compound.putUUID("ParentUUID", this.getParentId());
        }
        compound.putInt("BodyIndex", getBodyIndex());
        compound.putFloat("PartAngle", angleYaw);
        compound.putFloat("PartRadius", radius);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("ParentUUID")) {
            this.setParentId(compound.getUUID("ParentUUID"));
        }
        this.setBodyIndex(compound.getInt("BodyIndex"));
        this.angleYaw = compound.getFloat("PartAngle");
        this.radius = compound.getFloat("PartRadius");
    }

    protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
    }

    public int getBodyIndex() {
        return this.entityData.get(BODYINDEX);
    }

    public void setBodyIndex(int index) {
        this.entityData.set(BODYINDEX, index);
    }

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }

    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    @Override
    public void tick() {
        this.setNoGravity(true);
        isInsidePortal = false;
        if (this.tickCount > 10) {
            Entity parent = getParent();
            refreshDimensions();
            if (!level.isClientSide) {
                if (parent != null) {
                    Vec3 vec3 = this.calculateViewVector(parent.xRotO, parent.yRotO);
                    Vec3 vec32 = new Vec3(parent.xo - vec3.x * radius, parent.yo + vec3.y * radius, parent.zo - vec3.z * radius);

                    double d0 = vec32.x - this.getX();
                    double d1 = vec32.y - this.getY();
                    double d2 = vec32.z - this.getZ();
                    double d3 = Math.sqrt(d0 * d0 + d2 * d2);

                    this.setPos(vec32.x, vec32.y, vec32.z);

                    if (d3 > (double) 2.5000003E-7F) {
                        float xRot = ((float) (Mth.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
                        this.setXRot(this.rotlerp(this.getXRot(), xRot, 1.0F));

                        float yRot = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                        this.setYRot(this.rotlerp(this.getYRot(), yRot, (float) 3.5F));
                    }

                    this.markHurt();
                    this.yHeadRot = this.getYRot();
                    this.yBodyRot = this.yRotO;
                    if (parent instanceof LivingEntity) {
                        if (!level.isClientSide && (((LivingEntity) parent).hurtTime > 0 || ((LivingEntity) parent).deathTime > 0)) {
                            FrostRealm.sendMSGToAll(new HurtMultipartMessage(this.getId(), parent.getId(), 0));
                            this.hurtTime = ((LivingEntity) parent).hurtTime;
                            this.deathTime = ((LivingEntity) parent).deathTime;
                        }
                    }
                    this.pushEntities();
                    if (parent.isRemoved() && !level.isClientSide) {
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 60);
                    this.remove(Entity.RemovalReason.KILLED);
                    this.dropExperience();
                }
            }
        }
        super.tick();

        if (this.level.isClientSide) {
            float f = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
            float f1 = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }
        }
    }

    protected float rotlerp(float p_24992_, float p_24993_, float p_24994_) {
        float f = Mth.wrapDegrees(p_24993_ - p_24992_);
        if (f > p_24994_) {
            f = p_24994_;
        }

        if (f < -p_24994_) {
            f = -p_24994_;
        }

        float f1 = p_24992_ + f;
        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        if (this.deathTime >= 20) {
            this.dropExperience();
        }
    }

    protected boolean isAlwaysExperienceDropper() {
        return true;
    }

    public int getExperienceReward() {
        return 10;
    }

    public void setInitialPartPos(Entity parent) {
        Vec3 vec3 = this.calculateViewVector(parent.xRotO, parent.yRotO);
        this.setPos(parent.xo - vec3.x * radius, parent.yo + vec3.y * radius, parent.zo - vec3.z * radius);
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
    public void push(Entity p_33636_) {
        if (p_33636_ != this && !(p_33636_ instanceof ShadeInsect) && !(p_33636_ instanceof ShadeInsectPart)) {
            super.push(p_33636_);
            this.dealDamage(p_33636_);
        }

    }

    @Override
    public void playerTouch(Player p_33611_) {
        this.dealDamage(p_33611_);
    }


    protected void dealDamage(Entity p_33638_) {
        if (this.isAlive()) {
            if (p_33638_.hurt(this.damageSources().mobAttack(this), this.getAttackDamage())) {
                this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 4.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, p_33638_);
            }
        }
    }

    protected float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        Entity parent = getParent();

        return parent != null ? parent.interact(player, hand) : InteractionResult.PASS;
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (source.is(DamageTypes.CRAMMING) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN)) {
            return false;
        }

        final Entity parent = getParent();
        final boolean prev = parent != null && parent.hurt(source, damage * this.damageMultiplier);
        if (prev && !level.isClientSide) {
            FrostRealm.sendMSGToAll(new HurtMultipartMessage(this.getId(), parent.getId(), damage * this.damageMultiplier));
        }
        return super.hurt(source, damage);
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

    public void travel(Vec3 p_20818_) {
        if (this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                BlockPos ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
                float f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
            }
        }

        this.calculateEntityAnimation(false);
    }

    public boolean onClimbable() {
        return false;
    }

    public boolean isFlapping() {
        return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
    }

    protected float getStandingEyeHeight(Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.height * 0.35F;
    }

    public int getUniqueFlapTickOffset() {
        return this.getId() * 3;
    }

    public boolean shouldRenderAtSqrDistance(double p_33107_) {
        return true;
    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
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

    protected float getSoundVolume() {
        return 1.0F;
    }

    public boolean canAttackType(EntityType<?> p_33111_) {
        return true;
    }

    public double getPassengersRidingOffset() {
        return (double) this.getEyeHeight();
    }
}
