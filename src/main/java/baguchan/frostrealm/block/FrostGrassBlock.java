package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.world.placement.FrostPlacements;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FrostGrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {
	public final Supplier<Block> turnBlock;

	public FrostGrassBlock(Properties p_53685_, Supplier<Block> turnBlock) {
		super(p_53685_);
		this.turnBlock = turnBlock;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader p_53692_, BlockPos p_53693_, BlockState p_53694_) {
		return p_53692_.getBlockState(p_53693_.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level p_53697_, RandomSource p_53698_, BlockPos p_53699_, BlockState p_53700_) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel p_221270_, RandomSource p_221271_, BlockPos p_221272_, BlockState p_221273_) {
		BlockPos blockpos = p_221272_.above();
		BlockState blockstate = FrostBlocks.COLD_GRASS.get().defaultBlockState();
		Optional<Holder.Reference<PlacedFeature>> optional = p_221270_.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(FrostPlacements.PATCH_TUNDRA_GRASS_BONEMEAL);

		label49:
		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;

			for (int j = 0; j < i / 16; ++j) {
				blockpos1 = blockpos1.offset(p_221271_.nextInt(3) - 1, (p_221271_.nextInt(3) - 1) * p_221271_.nextInt(3) / 2, p_221271_.nextInt(3) - 1);
				if (!p_221270_.getBlockState(blockpos1.below()).is(this) || p_221270_.getBlockState(blockpos1).isCollisionShapeFullBlock(p_221270_, blockpos1)) {
					continue label49;
				}
			}

			BlockState blockstate1 = p_221270_.getBlockState(blockpos1);
			if (blockstate1.is(blockstate.getBlock()) && p_221271_.nextInt(10) == 0) {
				((BonemealableBlock) blockstate.getBlock()).performBonemeal(p_221270_, p_221271_, blockpos1, blockstate1);
			}

			if (blockstate1.isAir()) {
				Holder<PlacedFeature> holder;
				if (p_221271_.nextInt(8) == 0) {
					List<ConfiguredFeature<?, ?>> list = p_221270_.getBiome(blockpos1).value().getGenerationSettings().getFlowerFeatures();
					if (list.isEmpty()) {
						continue;
					}

					holder = ((RandomPatchConfiguration) list.get(0).config()).feature();
				} else {
					if (!optional.isPresent()) {
						continue;
					}

					holder = optional.get();
				}

				holder.value().place(p_221270_, p_221270_.getChunkSource().getGenerator(), p_221271_, blockpos1);
			}
		}

	}

	public void randomTick(BlockState p_56819_, ServerLevel p_56820_, BlockPos p_56821_, RandomSource p_56822_) {
		if (!canBeGrass(p_56819_, p_56820_, p_56821_)) {
			if (!p_56820_.isAreaLoaded(p_56821_, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			p_56820_.setBlockAndUpdate(p_56821_, this.turnBlock.get().defaultBlockState());
		} else {
			if (p_56820_.getMaxLocalRawBrightness(p_56821_.above()) >= 9) {
				BlockState blockstate = this.defaultBlockState();

				for (int i = 0; i < 4; ++i) {
					BlockPos blockpos = p_56821_.offset(p_56822_.nextInt(3) - 1, p_56822_.nextInt(5) - 3, p_56822_.nextInt(3) - 1);
					if (p_56820_.getBlockState(blockpos).is(this.turnBlock.get()) && canPropagate(blockstate, p_56820_, blockpos)) {
						p_56820_.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, Boolean.valueOf(p_56820_.getBlockState(blockpos.above()).is(Blocks.SNOW))));
					}
				}
			}

		}
	}

	private static boolean canBeGrass(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
		BlockPos blockpos = p_56826_.above();
		BlockState blockstate = p_56825_.getBlockState(blockpos);
		if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) <= 3) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LightEngine.getLightBlockInto(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_56825_, blockpos));
			return i < p_56825_.getMaxLightLevel();
		}
	}

	@Override
	protected MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
		return null;
	}

	private static boolean canPropagate(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
		BlockPos blockpos = p_56830_.above();
		return canBeGrass(p_56828_, p_56829_, p_56830_) && !p_56829_.getFluidState(blockpos).is(FluidTags.WATER);
	}
}