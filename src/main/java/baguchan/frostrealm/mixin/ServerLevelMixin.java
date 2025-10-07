package baguchan.frostrealm.mixin;

import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.data.resource.FrostDimensions;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import baguchan.frostrealm.registry.FrostWeathers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static baguchan.frostrealm.CommonEvents.canPlaceSnowLayer;
import static baguchan.frostrealm.CommonEvents.makeParticles;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    protected ServerLevelMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Shadow
    public abstract <T extends ParticleOptions> int sendParticles(T p_8768_, double p_8769_, double p_8770_, double p_8771_, int p_8772_, double p_8773_, double p_8774_, double p_8775_, double p_8776_);

    @Inject(method = "tickPrecipitation", at = @At("HEAD"))

    public void tickPrecipitation(BlockPos p_295060_, CallbackInfo ci) {

        ProfilerFiller profilerfiller = Profiler.get();

        if (this.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
            FrostWeatherSavedData frostWeatherSavedData = FrostWeatherSavedData.get(this);
            ProfilerFiller profiler = Profiler.get();

            if (frostWeatherSavedData.isWeatherActive() && frostWeatherSavedData.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
                profiler.push("freeze_weather");
                if (this.random.nextInt(8) == 0) {
                    BlockPos pos = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, p_295060_);
                    BlockPos posDown = pos.below();
                    if (!this.getBiome(pos).is(FrostTags.Biomes.HOT_BIOME)) {
                        if (this.isAreaLoaded(posDown, 1)) {
                            BlockState snowState = this.getBlockState(pos);
                            BlockState snowStateBelow = this.getBlockState(pos.below());
                            if (snowState.getBlock() == Blocks.FIRE) {
                                this.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            } else if (snowStateBelow.getBlock() == Blocks.CAMPFIRE) {
                                makeParticles(this, pos.below());
                                this.setBlockAndUpdate(pos.below(), snowStateBelow.setValue(BlockStateProperties.LIT, false));
                            } else if (canPlaceSnowLayer(this, pos)) {
                                this.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
                            }
                        }
                    }
                }
                profiler.popPush("freeze_weather");

                profiler.push("freeze");
                BlockPos pos = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, p_295060_);
                BlockPos posDown = pos.below();
                if (this.isAreaLoaded(posDown, 1)) {
                    BlockState snowState = this.getBlockState(pos);
                    BlockState snowStateBelow = this.getBlockState(pos.below());
                    if (snowState.getBlock() instanceof CropBlock) {
                        if (!snowState.is(FrostTags.Blocks.NON_FREEZE_CROP)) {
                            this.playSound(null, pos, SoundEvents.PLAYER_HURT_FREEZE, SoundSource.BLOCKS);
                            this.sendParticles(ParticleTypes.SNOWFLAKE, pos.getX() + this.random.nextFloat(), pos.getY() + this.random.nextFloat(), pos.getZ() + this.random.nextFloat(), 4, 0.0D, 0.0D, 0.0D, 0.0F);
                            this.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }
                    if (!snowState.is(FrostTags.Blocks.NON_FREEZE_SAPLING) && !snowState.is(FrostBlocks.FROSTBITE_SAPLING) && snowState.getBlock() instanceof SaplingBlock) {
                        this.playSound(null, pos, SoundEvents.PLAYER_HURT_FREEZE, SoundSource.BLOCKS);
                        this.setBlockAndUpdate(pos, FrostBlocks.FROSTBITE_SAPLING.get().defaultBlockState());
                        this.sendParticles(ParticleTypes.SNOWFLAKE, pos.getX() + this.random.nextFloat(), pos.getY() + this.random.nextFloat(), pos.getZ() + this.random.nextFloat(), 4, 0.0D, 0.0D, 0.0D, 0.0F);
                    }
                }
            }
            profiler.popPush("freeze");
        }
    }
}
