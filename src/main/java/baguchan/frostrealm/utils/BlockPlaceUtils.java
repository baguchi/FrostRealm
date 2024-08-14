package baguchan.frostrealm.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/*
 * Based From Aether II 's BlockPlacementUtil
 * https://github.com/The-Aether-Team/The-Aether-II/blob/1.21.0-develop/src/main/java/com/aetherteam/aetherii/world/BlockPlacementUtil.java
 */
public class BlockPlaceUtils {

    public static void placeDisk(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random, boolean replaceBlocks) {
        float radiusSq = radius * radius;
        placeProvidedBlock(level, blockProvider, center, random, replaceBlocks);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                placeProvidedBlock(level, blockProvider, center.offset(x, 0, z), random, replaceBlocks);
                placeProvidedBlock(level, blockProvider, center.offset(-x, 0, -z), random, replaceBlocks);
                placeProvidedBlock(level, blockProvider, center.offset(-z, 0, x), random, replaceBlocks);
                placeProvidedBlock(level, blockProvider, center.offset(z, 0, -x), random, replaceBlocks);
            }
        }
    }

    /**
     * Places a block if there is not already one at the position.<br><br>
     * Warning for "UnusedReturnValue" is suppressed because the boolean from {@link WorldGenLevel#setBlock(BlockPos, BlockState, int)} needs to be retained.
     *
     * @param level    The {@link WorldGenLevel} for generation.
     * @param provider The {@link BlockStateProvider} for the block to be placed.
     * @param pos      The {@link BlockPos} for the block.
     * @param random   The {@link RandomSource} used for generation.
     * @return A {@link Boolean} for whether the block was placed successfully.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random, boolean replaceBlocks) {
        if (replaceBlocks) {
            return level.setBlock(pos, provider.getState(random, pos), 2);
        } else if (level.getBlockState(pos).isAir()) {
            return level.setBlock(pos, provider.getState(random, pos), 2);
        } else {
            return false;
        }
    }
}
