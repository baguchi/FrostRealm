package baguchan.frostrealm.mixin;

import baguchan.frostrealm.data.resource.FrostNoises;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SurfaceSystem.class)
public abstract class SurfaceSystemMixin {
    @Shadow
    private int seaLevel;

    private NormalNoise height;
    private NormalNoise bottomHeight;
    @Shadow
    @Final
    private PositionalRandomFactory noiseRandom;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void init(RandomState p_224637_, BlockState p_224638_, int p_224639_, PositionalRandomFactory p_224640_, CallbackInfo callbackInfo) {
        this.height = p_224637_.getOrCreateNoise(FrostNoises.ISLANDS_HEIGHT);
        this.bottomHeight = p_224637_.getOrCreateNoise(FrostNoises.ISLANDS_BOTTOM);
    }

    @Inject(method = "buildSurface", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Holder;is(Lnet/minecraft/resources/ResourceKey;)Z", ordinal = 0))
    private void onGetBiome(RandomState p_224649_, BiomeManager p_224650_, Registry<Biome> p_224651_, boolean p_224652_, WorldGenerationContext p_224653_, ChunkAccess chunkAccess, NoiseChunk p_224655_, SurfaceRules.RuleSource p_224656_, CallbackInfo ci, BlockPos.MutableBlockPos $$8, ChunkPos $$9, int $$10, int $$11, BlockColumn blockcolumn, SurfaceRules.Context $$13, SurfaceRules.SurfaceRule surfaceRule, BlockPos.MutableBlockPos blockpos$mutableblockpos1, int real, int $$17, int x, int z, int y, Holder holder2) {

        Holder<Biome> holder = p_224650_.getBiome(blockpos$mutableblockpos1.set(x, 180, z));

        if (holder.is(FrostBiomes.CRYSTAL_FALL)) {
            crystalFallExtension(surfaceRule, chunkAccess,
                    blockpos$mutableblockpos1.set(x, 180, z), x, z);
        }
    }

    private void crystalFallExtension(SurfaceRules.SurfaceRule surfaceRule, ChunkAccess p_189937_, BlockPos.MutableBlockPos p_189938_, int p_189939_, int p_189940_) {
        double height = 180;

        double d5 = this.height.getValue((double) p_189939_, 0.0D, (double) p_189940_) * 30D;
        double d1 = this.bottomHeight.getValue((double) p_189939_, 0.0D, (double) p_189940_) * d5;
        int j = (int) (height - d1);

        if (d5 > 1F) {
            for (int l = (int) (d5 + height); l >= j; --l) {
                if (p_189937_.getBlockState(p_189938_.setY(l)).isAir()) {

                    if ((d5 + height) - 1 <= l) {
                        p_189937_.setBlockState(p_189938_.setY(l), FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState(), false);
                    } else if ((d5 + height) - 4 < l) {
                        p_189937_.setBlockState(p_189938_.setY(l), FrostBlocks.FROZEN_DIRT.get().defaultBlockState(), false);
                    } else {
                        p_189937_.setBlockState(p_189938_.setY(l), FrostBlocks.FRIGID_STONE.get().defaultBlockState(), false);
                    }
                }
            }
        }
    }
}
