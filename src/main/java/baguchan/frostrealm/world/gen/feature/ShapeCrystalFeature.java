package baguchan.frostrealm.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        BlockPos blockpos = blockstateconfiguration.origin();
        WorldGenLevel worldgenlevel = blockstateconfiguration.level();
        RandomSource random = blockstateconfiguration.random();

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(blockstateconfiguration.random());
        int height = random.nextInt(9) + 8;
        int width = random.nextInt((height / 5) + 1) + 1;
        float shaper = 1 + random.nextInt(1);
        float generationPercent = 1.0F;
        for (int y = 0; y < height; ++y) {
            BlockPos spikeCenterPos = blockpos.above(y).relative(direction, (int) (y * shaper));
            for (BlockPos blockpos1 : BlockPos.betweenClosed(spikeCenterPos.offset(-width, 0, -width), spikeCenterPos.offset(width, 0, width))) {
                if (random.nextFloat() < generationPercent) {
                    worldgenlevel.setBlock(blockpos1, blockstateconfiguration.config().state, 2);
                }
            }

            generationPercent = generationPercent * 0.85F;
        }
        return true;
    }
}