package baguchan.frostrealm.entity;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.HurtMultipartMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;

public class FrostormDragonPart extends PartEntity<FrostormDragon> {
    public final FrostormDragon parentMob;
    public final String name;
    private final EntityDimensions size;
    private final float damageMultiplier;


    public FrostormDragonPart(FrostormDragon p_31014_, String p_31015_, float p_31016_, float p_31017_, float damageMultiplier) {
        super(p_31014_);
        this.damageMultiplier = damageMultiplier;
        this.size = EntityDimensions.scalable(p_31016_, p_31017_);
        this.refreshDimensions();
        this.parentMob = p_31014_;
        this.name = p_31015_;
    }

    protected void defineSynchedData() {
    }

    protected void readAdditionalSaveData(CompoundTag p_31025_) {
    }

    protected void addAdditionalSaveData(CompoundTag p_31028_) {
    }

    public boolean isPickable() {
        return true;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    public boolean hurt(DamageSource source, float amount) {
        if (!this.isInvulnerableTo(source) && parentMob != null) {
            final Entity parent = getParent();
            final boolean prev = parent != null && parent.hurt(source, amount * this.damageMultiplier);
            if (prev && !level().isClientSide) {
                FrostRealm.sendMSGToAll(new HurtMultipartMessage(this.getId(), parent.getId(), amount * this.damageMultiplier));
            }
        }
        return false;
    }

    public boolean is(Entity p_31031_) {
        return this == p_31031_ || this.parentMob == p_31031_;
    }

    @Override
    public boolean save(CompoundTag tag) {
        return false;
    }

    public boolean shouldBeSaved() {
        return false;
    }

    public EntityDimensions getDimensions(Pose p_31023_) {
        return this.size;
    }
}