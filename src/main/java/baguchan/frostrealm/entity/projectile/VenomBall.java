package baguchan.frostrealm.entity.projectile;

import baguchan.frostrealm.registry.FrostDamageSources;
import baguchan.frostrealm.registry.FrostParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class VenomBall extends ThrowableProjectile {
    public VenomBall(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public VenomBall(EntityType<? extends ThrowableProjectile> p_37456_, double p_37457_, double p_37458_, double p_37459_, Level p_37460_) {
        super(p_37456_, p_37457_, p_37458_, p_37459_, p_37460_);
    }

    public VenomBall(EntityType<? extends ThrowableProjectile> p_37462_, LivingEntity p_37463_, Level p_37464_) {
        super(p_37462_, p_37463_, p_37464_);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326003_) {

    }

    @Override
    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            double d0 = 0.08;

            for (int i = 0; i < 12; i++) {
                this.level()
                        .addParticle(
                                FrostParticleTypes.VENOM_CLOUD.get(),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                ((double) this.random.nextFloat() - 0.5) * 0.35,
                                ((double) this.random.nextFloat() - 0.5) * 0.35,
                                ((double) this.random.nextFloat() - 0.5) * 0.35
                        );
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            double d0 = 0.08;
            this.level()
                    .addParticle(
                            FrostParticleTypes.VENOM_BUBBLE.get(),
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            ((double) this.random.nextFloat() - 0.5) * 0.08,
                            ((double) this.random.nextFloat() - 0.5) * 0.08,
                            ((double) this.random.nextFloat() - 0.5) * 0.08
                    );

        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_326121_) {
        super.onHitEntity(p_326121_);
        if (!this.level().isClientSide) {
            LivingEntity livingentity = this.getOwner() instanceof LivingEntity livingentity1 ? livingentity1 : null;
            Entity entity = p_326121_.getEntity();
            if (livingentity != null) {
                livingentity.setLastHurtMob(entity);
            }

            int poison = this.level().getDifficulty().getId();
            DamageSource damagesource = this.damageSources().source(FrostDamageSources.VENOM_BALL, this, livingentity);
            if (entity.hurt(damagesource, 2.0F) && entity instanceof LivingEntity livingentity2) {
                EnchantmentHelper.doPostAttackEffects((ServerLevel) this.level(), livingentity2, damagesource);
                livingentity2.addEffect(new MobEffectInstance(MobEffects.POISON, 40 + 20 * poison));
            }
        }
    }

    @Override
    protected void onHit(HitResult p_326337_) {
        super.onHit(p_326337_);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

            this.discard();
        }
    }
}
