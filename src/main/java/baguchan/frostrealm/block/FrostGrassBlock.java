package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LayerLightEngine;

import java.util.List;
public class FrostGrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {
	public FrostGrassBlock(BlockBehaviour.Properties p_53685_) {
		super(p_53685_);
	}

	public boolean isValidBonemealTarget(BlockGetter p_53692_, BlockPos p_53693_, BlockState p_53694_, boolean p_53695_) {
		return p_53692_.getBlockState(p_53693_.above()).isAir();
	}

	public boolean isBonemealSuccess(Level p_53697_, RandomSource p_53698_, BlockPos p_53699_, BlockState p_53700_) {
		return true;
	}

	public void performBonemeal(ServerLevel p_53687_, RandomSource p_53688_, BlockPos p_53689_, BlockState p_53690_) {
		BlockPos blockpos = p_53689_.above();
		BlockState blockstate = Blocks.GRASS.defaultBlockState();

		label48:
		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;

			for (int j = 0; j < i / 16; ++j) {
				blockpos1 = blockpos1.offset(p_53688_.nextInt(3) - 1, (p_53688_.nextInt(3) - 1) * p_53688_.nextInt(3) / 2, p_53688_.nextInt(3) - 1);
				if (!p_53687_.getBlockState(blockpos1.below()).is(this) || p_53687_.getBlockState(blockpos1).isCollisionShapeFullBlock(p_53687_, blockpos1)) {
					continue label48;
				}
			}

			BlockState blockstate2 = p_53687_.getBlockState(blockpos1);
			if (blockstate2.is(blockstate.getBlock()) && p_53688_.nextInt(10) == 0) {
				((BonemealableBlock)blockstate.getBlock()).performBonemeal(p_53687_, p_53688_, blockpos1, blockstate2);
			}

			if (blockstate2.isAir()) {
				PlacedFeature placedfeature;
				if (p_53688_.nextInt(8) == 0) {
					List<ConfiguredFeature<?, ?>> list = p_53687_.getBiome(blockpos1).value().getGenerationSettings().getFlowerFeatures();
					if (list.isEmpty()) {
						continue;
					}

					placedfeature = ((RandomPatchConfiguration) list.get(0).config()).feature().value();
				} else {
					placedfeature = VegetationPlacements.GRASS_BONEMEAL.value();
				}

				placedfeature.place(p_53687_, p_53687_.getChunkSource().getGenerator(), p_53688_, blockpos1);
			}
		}

	}

	public void randomTick(BlockState p_56819_, ServerLevel p_56820_, BlockPos p_56821_, RandomSource p_56822_) {
		if (!canBeGrass(p_56819_, p_56820_, p_56821_)) {
			if (!p_56820_.isAreaLoaded(p_56821_, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			p_56820_.setBlockAndUpdate(p_56821_, FrostBlocks.FROZEN_DIRT.get().defaultBlockState());
		} else {
			if (p_56820_.getMaxLocalRawBrightness(p_56821_.above()) >= 9) {
				BlockState blockstate = this.defaultBlockState();

				for (int i = 0; i < 4; ++i) {
					BlockPos blockpos = p_56821_.offset(p_56822_.nextInt(3) - 1, p_56822_.nextInt(5) - 3, p_56822_.nextInt(3) - 1);
					if (p_56820_.getBlockState(blockpos).is(FrostBlocks.FROZEN_DIRT.get()) && canPropagate(blockstate, p_56820_, blockpos)) {
						p_56820_.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, Boolean.valueOf(p_56820_.getBlockState(blockpos.above()).is(Blocks.SNOW))));
					}
				}
			}

		}
	}

	private static boolean canBeGrass(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
		BlockPos blockpos = p_56826_.above();
		BlockState blockstate = p_56825_.getBlockState(blockpos);
		if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LayerLightEngine.getLightBlockInto(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_56825_, blockpos));
			return i < p_56825_.getMaxLightLevel();
		}
	}

	private static boolean canPropagate(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
		BlockPos blockpos = p_56830_.above();
		return canBeGrass(p_56828_, p_56829_, p_56830_) && !p_56829_.getFluidState(blockpos).is(FluidTags.WATER);
	}
}