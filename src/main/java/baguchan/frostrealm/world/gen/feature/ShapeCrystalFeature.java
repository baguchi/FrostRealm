package baguchan.frostrealm.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
        Direction direction2 = Direction.Plane.HORIZONTAL.getRandomDirection(blockstateconfiguration.random());
        int height = random.nextInt(8) + 8;
        int i = random.nextInt((height / 4) + 1) + 2;
        for (int y = 0; y < height; ++y) {
            BlockPos spikeCenterPos = blockpos.above(y).relative(direction, y).relative(direction2, y);
            for (BlockPos blockpos1 : BlockPos.betweenClosed(spikeCenterPos.offset(-i, 0, -i), spikeCenterPos.offset(i, 0, i))) {
                worldgenlevel.setBlock(blockpos1, blockstateconfiguration.config().state, 2);
            }
            i -= Mth.ceil(spikeCenterPos.distManhattan(blockpos));
            if (i < 1) {
                i = 1;
            }
        }
        return true;
    }
}