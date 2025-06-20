package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class UnderGokkur extends Gokkur {
    protected static final EntityDataAccessor<Boolean> MAGMA = SynchedEntityData.defineId(UnderGokkur.class, EntityDataSerializers.BOOLEAN);

    public UnderGokkur(EntityType<? extends UnderGokkur> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MAGMA, true);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Magma", this.isMagma());
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.setMagma(tag.getBooleanOr("Magma", true));
    }

    @Override
    public void setSnowProgress(float progress) {
    }

    @Override
    public float getSnowProgress() {
        return 0.0F;
    }

    @Override
    public void setGrass(boolean grass) {
    }

    @Override
    public boolean isGrass() {
        return false;
    }


    public void setMagma(boolean magma) {
        this.entityData.set(MAGMA, magma);
    }


    public boolean isMagma() {
        return this.entityData.get(MAGMA);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.isInWater() && this.isMagma()) {
                this.setMagma(false);
            }

            if (this.isInLava() && !this.isMagma()) {
                this.setMagma(true);
            }
        } else {
            if (this.isMagma() && this.random.nextFloat() < 0.25F) {
                Vec3 vec3 = this.getDeltaMovement().scale(0.65);
                this.level().addParticle(ParticleTypes.FALLING_LAVA, this.getRandomX(this.getBbWidth() / 2), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 2), vec3.x, vec3.y, vec3.z);
            }
        }
    }

    public BlockState getEntityBlocks() {
        return FrostBlocks.PERMA_SLATE.get().defaultBlockState();
    }
}
