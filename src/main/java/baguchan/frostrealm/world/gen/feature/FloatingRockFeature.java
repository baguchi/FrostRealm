package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.utils.BlockPlaceUtils;
import baguchan.frostrealm.world.gen.feature.config.FloatingRockConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FloatingRockFeature extends Feature<FloatingRockConfiguration> {
    public FloatingRockFeature(Codec<FloatingRockConfiguration> codec) {
        super(codec);
    }


    public boolean place(FeaturePlaceContext<FloatingRockConfiguration> config) {
        BlockPos pos = config.origin();
        WorldGenLevel level = config.level();
        RandomSource random = config.random();


        float radius = config.config().baseRadius().sample(random);
        float addtionalRadius = config.config().additionalRadius().sample(random);
        float height = radius + addtionalRadius;
        int offset = (int) (-radius * 20 + radius * 16);

        float lake_edge = Math.min(radius - 2, radius - random.nextInt((int) (radius - 4)));

        float range = 0;

        for (int i = offset; i < 0; ++i) {
            BlockPlaceUtils.placeDisk(
                    level,
                    config.config().block(),
                    new BlockPos(pos.getX(), (int) (pos.getY() + i + height + (int) radius), pos.getZ()),
                    radius + i,
                    random,
                    true);

            BlockPlaceUtils.placeDisk(
                    level,
                    BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                    new BlockPos(pos.getX(), (int) (pos.getY() + i + height + radius), pos.getZ()),
                    lake_edge + i,
                    random,
                    true);
            radius += 0.25F * random.nextFloat();
            lake_edge += 0.15F * random.nextFloat();
        }


        return true;
    }

}