package baguchan.frostrealm.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class ShapeCrystalFeature extends Feature<BlockStateConfiguration> {
    public ShapeCrystalFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> blockstateconfiguration) {
        BlockPos blockpos = blockstateconfiguration.origin().below(3);
        WorldGenLevel worldgenlevel = blockstateconfiguration.level();
        RandomSource random = blockstateconfiguration.random();

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(blockstateconfiguration.random());
        int height = random.nextInt(12) + 24;
        int width = (int) (height / 4F);
        float shaper = width / (4F + random.nextInt(5));
        for (int y = 0; y < height; ++y) {
            BlockPos spikePos = blockpos.above(y).relative(direction, (int) (y * shaper));
            for (BlockPos blockpos1 : BlockPos.betweenClosed(spikePos.offset(-width, 0, -width), spikePos.offset(width, 0, width))) {
                if (blockpos1.distSqr(spikePos) > (height - (width * width * 2)) / (y + 2)) {
                    if (blockpos1.distSqr(spikePos) < (height + (width * width * 2)) / (y + 2)) {
                        if (!worldgenlevel.getBlockState(blockpos1).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                            worldgenlevel.setBlock(blockpos1, blockstateconfiguration.config().state, 2);
                        }
                    }
                }
            }
            /*   for (BlockPos blockpos1 : BlockPos.betweenClosed(spikePos.offset(-width, 0, -width), spikePos.offset(width, 0, width))) {
                if (blockpos.distSqr(blockpos1.below(y)) > ((height - width))) {
                    if (blockpos.distSqr(blockpos1) < (width * width)) {
                        if (blockpos.distSqr(blockpos1.above(y)) < (height + width)) {
                            worldgenlevel.setBlock(blockpos1, blockstateconfiguration.config().state, 2);
                        }
                    }
                }
            }
           */
        }
        return true;
    }
}