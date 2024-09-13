package baguchan.frostrealm.entity.projectile;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class VenomBall extends ThrowableProjectile {
    public VenomBall(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    protected VenomBall(EntityType<? extends ThrowableProjectile> p_37456_, double p_37457_, double p_37458_, double p_37459_, Level p_37460_) {
        super(p_37456_, p_37457_, p_37458_, p_37459_, p_37460_);
    }

    protected VenomBall(EntityType<? extends ThrowableProjectile> p_37462_, LivingEntity p_37463_, Level p_37464_) {
        super(p_37462_, p_37463_, p_37464_);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326003_) {

    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        super.onHitEntity(p_37259_);
    }
}
