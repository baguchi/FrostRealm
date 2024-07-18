package baguchan.frostrealm.entity.hostile.part;

import baguchan.frostrealm.entity.FrostPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class CorruptedWalkerPart<T extends CorruptedWalker> extends FrostPart<T> {
    public final CorruptedWalker parentMob;

    public CorruptedWalkerPart(T p_31014_, float width, float height) {
        super(p_31014_, width, height);
        this.refreshDimensions();
        this.parentMob = p_31014_;
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
            double d7 = 1F; //fall Distance
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

                float speed = this.parentMob.getSpeed() / 0.3F;

                serverlevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), knockbackPredicate(this))
                        .forEach(p_347296_ -> {
                            p_347296_.hurt(this.damageSources().mobAttack(this.parentMob), (float) this.fallDistance * 2 * speed);
                            this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK);
                        });
                if (!p_20992_.addLandingEffects((ServerLevel) this.level(), p_20993_, p_20992_, this.parentMob, (int) (i * getScale())))
                    ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, p_20992_).setPos(p_20993_), d0, d1, d2, i, 0.0, 0.0, 0.0, 0.15F);
            }
            BlockPos blockpos1 = this.getOnPos();
            BlockState blockstate1 = this.level().getBlockState(blockpos1);
            if (this.fallDistance > 0.5F) {
                this.vibrationAndSoundEffectsFromBlock(blockpos1, blockstate1, true, this.getMovementEmission().emitsEvents());
            }
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
}