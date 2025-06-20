package baguchan.frostrealm.entity.projectile;

import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class FlyingBlockEntity extends ThrowableProjectile {
    public static final EntityDataAccessor<BlockState> STATE = SynchedEntityData.defineId(FlyingBlockEntity.class, EntityDataSerializers.BLOCK_STATE);

    private boolean canPlace = false;

    public FlyingBlockEntity(EntityType<? extends FlyingBlockEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public FlyingBlockEntity(Level world, LivingEntity thrower, @Nullable BlockState state) {
        super(FrostEntities.FLYING_BLOCK.get(), thrower.getX(), thrower.getY(), thrower.getZ(), world);
        this.setOwner(thrower);
        if (state != null) {
            this.setBlockState(state);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.store("BlockState", BlockState.CODEC, this.getBlockState());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326113_) {
        p_326113_.define(STATE, Blocks.SNOW_BLOCK.defaultBlockState());
    }

    public void setBlockState(BlockState p_307211_) {
        this.entityData.set(STATE, p_307211_);
    }

    public BlockState getBlockState() {
        return this.entityData.get(STATE);
    }


    @Override
    protected void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.setBlockState(tag.read("BlockState", BlockState.CODEC).orElse(Blocks.SNOW_BLOCK.defaultBlockState()));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particle = new BlockParticleOption(ParticleTypes.BLOCK, getBlockState());
            for (int i = 0; i < 20; i++) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, this.random.nextDouble() * 0.2D, this.random.nextGaussian() * 0.05D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    public DamageSource blockAttack(@Nullable Entity p_270857_) {
        return this.damageSources().source(DamageTypes.FALLING_BLOCK, this, p_270857_);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide()) {
            if (this.getOwner() != result.getEntity()) {
                if (!(this.getOwner() instanceof LivingEntity living) || !living.isAlliedTo(result.getEntity())) {
                    result.getEntity().hurt(blockAttack(this.getOwner()), 3);
                    this.playSound(getBlockState().getSoundType().getBreakSound());
                    this.level().broadcastEntityEvent(this, (byte) 3);
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide() && !this.isPassenger()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.gameEvent(GameEvent.BLOCK_DESTROY, this.getOwner());
            this.playSound(getBlockState().getSoundType().getBreakSound());
            if (this.canPlace) {
                this.level().setBlock(this.blockPosition(), getBlockState(), 2);
            }
            this.discard();
        }
    }

    public void setCanPlace(boolean canPlace) {
        this.canPlace = canPlace;
    }

}