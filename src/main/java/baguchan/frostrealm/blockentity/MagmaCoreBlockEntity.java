package baguchan.frostrealm.blockentity;

import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.entity.projectile.FlyingBlockEntity;
import baguchan.frostrealm.registry.FrostBlockEntitys;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostWeathers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class MagmaCoreBlockEntity extends BlockEntity {
    public boolean active;
    public int cooldown;
    public int activeTick;
    public int range = 6;

    public MagmaCoreBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(FrostBlockEntitys.MAGMA_CORE.get(), p_155229_, p_155230_);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MagmaCoreBlockEntity magmaCoreBlockEntity) {
        if (level.isClientSide()) {
            clientTick(level, blockPos, blockState, magmaCoreBlockEntity);
        } else {
            serverTick(level, blockPos, blockState, magmaCoreBlockEntity);
        }
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, MagmaCoreBlockEntity magmaCoreBlockEntity) {
        if (magmaCoreBlockEntity.active) {
            if (level.random.nextFloat() < 0.2) {
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockPos.getX() + level.random.nextFloat(), blockPos.getY() + 1, blockPos.getZ() + level.random.nextFloat(), 0, 0.15F, 0);
            }
            if (level.random.nextFloat() < 0.1 && magmaCoreBlockEntity.cooldown <= 0) {
                level.addParticle(ParticleTypes.LAVA, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5,
                        level.random.nextFloat() / 2.0F,
                        2F,
                        level.random.nextFloat() / 2.0F);
            }
        }
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, MagmaCoreBlockEntity magmaCoreBlockEntity) {
        FrostWeatherSavedData frostWeatherSavedData = FrostWeatherSavedData.get(level);
        if (!magmaCoreBlockEntity.active && (frostWeatherSavedData.getFrostWeather() == FrostWeathers.PURPLE_FOG.get() || frostWeatherSavedData.getFrostWeather() == FrostWeathers.STARFALL.get())) {
            magmaCoreBlockEntity.active = true;
            magmaCoreBlockEntity.activeTick = 400 + level.random.nextInt(400);
            magmaCoreBlockEntity.cooldown = 600 + level.random.nextInt(600);
            magmaCoreBlockEntity.inventoryChanged();
        } else if (magmaCoreBlockEntity.active && !(frostWeatherSavedData.getFrostWeather() == FrostWeathers.PURPLE_FOG.get() || frostWeatherSavedData.getFrostWeather() == FrostWeathers.STARFALL.get())) {
            magmaCoreBlockEntity.active = false;
            magmaCoreBlockEntity.inventoryChanged();
        }
        if (magmaCoreBlockEntity.active) {
            if (magmaCoreBlockEntity.cooldown <= 0) {
                if (--magmaCoreBlockEntity.activeTick > 0) {
                    if (level.random.nextFloat() < 0.05F) {
                        generateMagmaEntity(level, blockPos, blockState, magmaCoreBlockEntity);
                        level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 3.0F, 1.0F);
                    }
                } else {
                    magmaCoreBlockEntity.activeTick = 400 + level.random.nextInt(400);
                    magmaCoreBlockEntity.cooldown = 600 + level.random.nextInt(600);
                    magmaCoreBlockEntity.inventoryChanged();
                }
            } else {
                --magmaCoreBlockEntity.cooldown;
            }
        }
    }

    public void setRange(int range) {
        this.range = range;
        this.inventoryChanged();
    }

    public int getRange() {
        return range;
    }

    protected static void generateMagmaEntity(Level level, BlockPos blockPos, BlockState blockState, MagmaCoreBlockEntity magmaCoreBlockEntity) {
        FlyingBlockEntity flyingBlockEntity = new FlyingBlockEntity(FrostEntities.FLYING_BLOCK.get(), level);
        float velocity = 1.2F;

        Vec3 originVec = blockPos.getCenter();
        int targetOffsetX = level.random.nextBoolean() ? magmaCoreBlockEntity.getRange() : -magmaCoreBlockEntity.getRange();
        int targetOffsetZ = level.random.nextBoolean() ? magmaCoreBlockEntity.getRange() : -magmaCoreBlockEntity.getRange();

        targetOffsetX += level.random.nextInt(6) - level.random.nextInt(6);
        targetOffsetZ += level.random.nextInt(6) - level.random.nextInt(6);

        Vec3 targetVec = blockPos.offset(targetOffsetX, 2, targetOffsetZ).getCenter();

        double theta = (Mth.TWO_PI) * (level.getRandom().nextDouble());

        double x1 = targetVec.subtract(originVec).length();
        double y1 = targetVec.y() - originVec.y();
        double gravity = flyingBlockEntity.getGravity();


        double root = velocity * velocity * velocity * velocity - gravity * (gravity * x1 * x1 + 2.0 * y1 * velocity * velocity);

        double yTrajectory = Math.atan((velocity * velocity + root) / (gravity * x1));

        yTrajectory = Math.max(yTrajectory, 60 * Mth.DEG_TO_RAD);

        double vec2Dx = Math.cos(yTrajectory);
        double vec2Dy = Math.sin(yTrajectory);

        double vec3Dx = vec2Dx * Math.cos(theta);
        double vec3Dz = vec2Dx * Math.sin(theta);

        Vec3 trajectory = new Vec3(vec3Dx, vec2Dy, vec3Dz);
        flyingBlockEntity.setFromVolcano(true);
        flyingBlockEntity.setBlockState(FrostBlocks.MAGMA_CORE.get().defaultBlockState());
        flyingBlockEntity.snapTo(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, 0.0F, 0.0F);
        flyingBlockEntity.shoot(trajectory.x, trajectory.y, trajectory.z, velocity, 1.0F);
        level.addFreshEntity(flyingBlockEntity);

    }

    @Override
    protected void loadAdditional(ValueInput p_422607_) {
        super.loadAdditional(p_422607_);
        this.cooldown = p_422607_.getIntOr("cooldown", 0);
        this.activeTick = p_422607_.getIntOr("active_tick", 0);
        this.active = p_422607_.getBooleanOr("active", false);
    }

    @Override
    protected void saveAdditional(ValueOutput p_422639_) {
        super.saveAdditional(p_422639_);
        p_422639_.putBoolean("active", this.active);
        p_422639_.putInt("active_tick", this.activeTick);
        p_422639_.putInt("cooldown", this.cooldown);
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider p_323910_) {
        return saveCustomOnly(p_323910_);
    }

    protected void inventoryChanged() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}
