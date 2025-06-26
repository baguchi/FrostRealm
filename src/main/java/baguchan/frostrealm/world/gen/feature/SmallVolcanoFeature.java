package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.world.gen.feature.config.HeightBlockStateConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SmallVolcanoFeature extends Feature<HeightBlockStateConfiguration> {
    public SmallVolcanoFeature(Codec<HeightBlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HeightBlockStateConfiguration> blockstateconfiguration) {
        BlockPos blockpos = blockstateconfiguration.origin().below(3);
        WorldGenLevel worldgenlevel = blockstateconfiguration.level();
        RandomSource random = blockstateconfiguration.random();

        if (worldgenlevel.getBlockState(blockpos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
            return false;
        }

        int height = blockstateconfiguration.config().baseHeight().sample(random);
        int width = (int) (height / 2F);
        for (int y = -height; y < height; ++y) {
            BlockPos spikePos = blockpos.above(y);

            for (BlockPos blockpos1 : BlockPos.betweenClosed(spikePos.offset(-width, 0, -width), spikePos.offset(width, 0, width))) {
                if (blockpos.below(y).distSqr(blockpos1) > ((width))) {
                    if (blockpos.distSqr(blockpos1) < (width * width + height)) {
                        if (!worldgenlevel.getBlockState(blockpos1).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                            if (blockpos.above(y).distSqr(blockpos1) >= (width)) {
                                worldgenlevel.setBlock(blockpos1, blockstateconfiguration.config().block().getState(random, blockpos1), 2);
                            } else if (y >= 1) {
                                worldgenlevel.setBlock(blockpos1, Blocks.LAVA.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
        }
        worldgenlevel.setBlock(blockpos.above(), FrostBlocks.MAGMA_CORE.get().defaultBlockState(), 2);


        return true;
    }
}