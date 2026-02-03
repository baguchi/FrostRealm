package baguchan.frostrealm.entity.projectile;

import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class GroundedWeapon extends Entity implements TraceableEntity {
    protected static final EntityDataAccessor<Direction> DATA_ATTACH_FACE_ID = SynchedEntityData.defineId(GroundedWeapon.class, EntityDataSerializers.DIRECTION);

    public static final int ATTACK_TRIGGER_TICKS = 52;
    private boolean sentSpikeEvent;
    private int lifeTicks = 52;
    private float weaponAnimation;
    private float weaponAnimationOld;
    private float glowAnimation;
    private float glowAnimationOld;

    private @Nullable EntityReference<LivingEntity> owner;

    public GroundedWeapon(EntityType<? extends GroundedWeapon> p_36923_, Level p_36924_) {
        super(p_36923_, p_36924_);
    }

    public GroundedWeapon(Level p_36926_, double p_36927_, double p_36928_, double p_36929_, float p_36930_, int p_36931_, LivingEntity p_36932_) {
        this(FrostEntities.GROUNDED_WEAPON.get(), p_36926_);
        this.setOwner(p_36932_);
        this.setYRot(p_36930_ * (180F / (float) Math.PI));
        this.setPos(p_36927_, p_36928_, p_36929_);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ATTACH_FACE_ID, Direction.DOWN);
    }

    public Direction getAttachFace() {
        return this.entityData.get(DATA_ATTACH_FACE_ID);
    }

    public void setAttachFace(Direction p_149789_) {
        this.entityData.set(DATA_ATTACH_FACE_ID, p_149789_);
    }

    @Override
    protected AABB makeBoundingBox(Vec3 vec3) {
        Direction direction = this.getAttachFace().getOpposite();
        double d0 = (double) this.getX();
        double d1 = (double) this.getY();
        double d2 = (double) this.getZ();
        float scale = 1.0F;

        double d6 = (double) this.getType().getWidth() / 2 * scale;
        double d7 = (double) this.getType().getHeight() / 2 * scale;
        double d8 = (double) this.getType().getWidth() / 2 * scale;
        if (direction.getAxis() == Direction.Axis.Z) {
            d8 = this.getType().getHeight() / 2 * scale;
            d7 = this.getType().getWidth() / 2 * scale;
        } else if (direction.getAxis() == Direction.Axis.X) {
            d6 = this.getType().getHeight() / 2 * scale;
            d7 = this.getType().getWidth() / 2 * scale;
        } else if (direction == Direction.UP) {
            d7 = (double) this.getType().getHeight() / 2 * scale;
            d1 += (double) this.getType().getHeight() / 2 * scale;
        }

        return new AABB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
    }

    public void setOwner(@Nullable LivingEntity p_36939_) {
        this.owner = EntityReference.of(p_36939_);
    }

    public @Nullable LivingEntity getOwner() {
        return EntityReference.getLivingEntity(this.owner, this.level());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput p_422390_) {
        this.owner = EntityReference.read(p_422390_, "Owner");
        this.setAttachFace(Direction.from3DDataValue(p_422390_.getByteOr("AttachFace", (byte) 0)));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput p_422710_) {
        EntityReference.store(this.owner, p_422710_, "Owner");
        p_422710_.putByte("AttachFace", (byte) this.getAttachFace().get3DDataValue());
    }

    public void tick() {
        super.tick();
        --this.lifeTicks;

        if (this.level().isClientSide()) {
            this.weaponAnimationOld = this.weaponAnimation;


            this.glowAnimationOld = this.glowAnimation;

            if (this.lifeTicks <= 5) {
                this.glowAnimation = Mth.clamp(this.glowAnimation - 0.5F, 0, 1F);
            } else {
                this.glowAnimation = Mth.clamp(this.glowAnimation + 0.1F, 0, 1F);
            }

            if (this.lifeTicks > 40 && this.lifeTicks < 44) {
                this.weaponAnimation = Mth.clamp(this.weaponAnimation + 0.5F, 0, 1F);
            }

            if (this.lifeTicks == 43) {
                if (!this.isSilent()) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.SPEAR_HIT.value(), this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 1.25F, false);
                }
            }

            if (this.lifeTicks == 42) {
                for (int i = 0; i < 12; ++i) {
                    double d0 = this.getX() + (this.random.nextDouble() * (double) 2.0F - (double) 1.0F) * (double) this.getBbWidth() * (double) 0.5F;
                    double d1 = this.getY() + 0.05 + this.random.nextDouble();
                    double d2 = this.getZ() + (this.random.nextDouble() * (double) 2.0F - (double) 1.0F) * (double) this.getBbWidth() * (double) 0.5F;
                    double d3 = (this.random.nextDouble() * (double) 2.0F - (double) 1.0F) * 0.3;
                    double d4 = 0.3 + this.random.nextDouble() * 0.3;
                    double d5 = (this.random.nextDouble() * (double) 2.0F - (double) 1.0F) * 0.3;
                    this.level().addParticle(ParticleTypes.CRIT, d0, d1 + (double) 1.0F, d2, d3, d4, d5);
                }
            }

            if (this.lifeTicks <= 15) {
                this.weaponAnimation = Mth.clamp(this.weaponAnimation - 0.25F, 0, 1F);
            }
        } else {
            if (this.lifeTicks == 42) {
                for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.1, (double) 0.0F, 0.1))) {
                    this.dealDamageTo(entity);
                }
            } else if (this.lifeTicks > 15 && this.lifeTicks < 42) {
                for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox())) {
                    this.dealTouchDamageTo(entity);
                }
            }
            if (this.lifeTicks < 0) {
                this.discard();
            }
        }

    }

    protected void dealDamageTo(Entity p_36945_) {
        LivingEntity livingentity = this.getOwner();
        if (p_36945_.isAlive() && p_36945_.isAttackable() && !p_36945_.isInvulnerable() && p_36945_ != livingentity) {
            if (livingentity == null) {
                p_36945_.hurt(this.damageSources().magic(), 6.0F);
            } else {
                if (livingentity.isAlliedTo(p_36945_)) {
                    return;
                }

                DamageSource damagesource = this.damageSources().indirectMagic(this, livingentity);
                Level var5 = this.level();
                if (var5 instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel) var5;
                    if (p_36945_.hurtServer(serverlevel, damagesource, 6.0F)) {
                        EnchantmentHelper.doPostAttackEffects(serverlevel, p_36945_, damagesource);
                    }
                }
            }
        }

    }

    protected void dealTouchDamageTo(Entity p_36945_) {
        LivingEntity livingentity = this.getOwner();
        if (p_36945_.isAlive() && p_36945_.isAttackable() && !p_36945_.isInvulnerable() && p_36945_ != livingentity) {
            if (livingentity == null) {
                p_36945_.hurt(this.damageSources().magic(), 2.0F);
            } else {
                if (livingentity.isAlliedTo(p_36945_)) {
                    return;
                }

                DamageSource damagesource = this.damageSources().indirectMagic(this, livingentity);
                Level var5 = this.level();
                if (var5 instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel) var5;
                    if (p_36945_.hurtServer(serverlevel, damagesource, 2.0F)) {
                        EnchantmentHelper.doPostAttackEffects(serverlevel, p_36945_, damagesource);
                    }
                }
            }
        }

    }

    public float getAnimationScale(float p_480499_) {
        return Mth.lerp(p_480499_, this.weaponAnimationOld, this.weaponAnimation);
    }

    public float getGlowAnimationScale(float p_480499_) {
        return Mth.lerp(p_480499_, this.glowAnimationOld, this.glowAnimation);
    }

    @Override
    public EntityDimensions getDimensions(Pose p_19975_) {
        float f = this.weaponAnimation * 0.9F;
        float f1 = 0.1F + f;
        return super.getDimensions(p_19975_).scale(1.0F, f1);
    }

    @Override
    public boolean hurtServer(ServerLevel p_376750_, DamageSource p_376281_, float p_376935_) {
        return false;
    }
}
