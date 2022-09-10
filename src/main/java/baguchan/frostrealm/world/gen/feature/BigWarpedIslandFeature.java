package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.registry.FrostBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BigWarpedIslandFeature extends Feature<BlockStateConfiguration> {
	public BigWarpedIslandFeature(Codec<BlockStateConfiguration> codec) {
		super(codec);
	}


	public boolean place(FeaturePlaceContext<BlockStateConfiguration> p_159471_) {
		BlockPos blockpos = p_159471_.origin();
		WorldGenLevel worldgenlevel = p_159471_.level();
		RandomSource random = p_159471_.random();

		float height = 10 + random.nextInt(10);
		float lake_edge = Math.min(15, height - random.nextInt(6) - random.nextInt(6));

		if (random.nextFloat() >= 0.15F) {
			for (int i = 0; height > 0.5F; --i) {
				for (int j = Mth.floor(-height); j <= Mth.ceil(height); ++j) {
					for (int k = Mth.floor(-height); k <= Mth.ceil(height); ++k) {
						if ((float) (j * j + k * k) <= (height + 1.0F) * (height + 1.0F)) {
							if ((float) (j * j + k * k) <= (height - lake_edge) * (height + lake_edge)) {
								this.setBlock(worldgenlevel, blockpos.offset(j, i, k), Blocks.WATER.defaultBlockState());
							} else {
								this.setBlock(worldgenlevel, blockpos.offset(j, i, k), FrostBlocks.FRIGID_STONE.get().defaultBlockState());
							}

						}
					}
				}

				height -= (float) random.nextInt(2) + 0.5F;
			}
		}


		return true;
	}
}