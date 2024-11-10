package baguchan.frostrealm.entity.projectile;

import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
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
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("BlockState", NbtUtils.writeBlockState(this.getBlockState()));
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
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState")));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particle = new BlockParticleOption(ParticleTypes.BLOCK, getBlockState());
            for (int i = 0; i < 20; i++) {
                this.level().addParticle(particle, false, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, this.random.nextDouble() * 0.2D, this.random.nextGaussian() * 0.05D);
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