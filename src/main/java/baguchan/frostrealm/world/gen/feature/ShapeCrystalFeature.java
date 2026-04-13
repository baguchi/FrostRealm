package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.world.gen.feature.config.HeightBlockStateConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ShapeCrystalFeature extends Feature<HeightBlockStateConfiguration> {
    public ShapeCrystalFeature(Codec<HeightBlockStateConfiguration> codec) {
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
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(blockstateconfiguration.random());
        int height = blockstateconfiguration.config().baseHeight().sample(random);
        int width = (int) (height / 4F);
        float shaper = width / (4F + random.nextInt(5));
        for (int y = 0; y < height; ++y) {
            BlockPos spikePos = blockpos.above(y).relative(direction, (int) (y * shaper));
            for (BlockPos blockpos1 : BlockPos.betweenClosed(spikePos.offset(-width, 0, -width), spikePos.offset(width, 0, width))) {
                if (blockpos1.distSqr(spikePos) > (height - (width * width * 2)) / (y + 2)) {
                    if (blockpos1.distSqr(spikePos) < (height + (width * width * 2)) / (y + 2)) {
                        if (!worldgenlevel.getBlockState(blockpos1).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                            worldgenlevel.setBlock(blockpos1, blockstateconfiguration.config().block().getState(worldgenlevel, random, blockpos1), 2);
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