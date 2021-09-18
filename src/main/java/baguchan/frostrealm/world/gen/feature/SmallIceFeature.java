package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.block.PointedIceBlock;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SmallDripstoneConfiguration;

import java.util.Random;
import java.util.function.Consumer;

public class SmallIceFeature extends Feature<SmallDripstoneConfiguration> {
	public SmallIceFeature(Codec<SmallDripstoneConfiguration> p_160345_) {
		super(p_160345_);
	}

	protected static boolean isEmptyOrWater(LevelAccessor p_159629_, BlockPos p_159630_) {
		return p_159629_.isStateAtPosition(p_159630_, DripstoneUtils::isEmptyOrWater);
	}

	public boolean place(FeaturePlaceContext<SmallDripstoneConfiguration> p_160362_) {
		WorldGenLevel worldgenlevel = p_160362_.level();
		BlockPos blockpos = p_160362_.origin();
		Random random = p_160362_.random();
		SmallDripstoneConfiguration smalldripstoneconfiguration = p_160362_.config();
		if (!isEmptyOrWater(worldgenlevel, blockpos)) {
			return false;
		} else {
			int i = Mth.randomBetweenInclusive(random, 1, smalldripstoneconfiguration.maxPlacements);
			boolean flag = false;

			for (int j = 0; j < i; ++j) {
				BlockPos blockpos1 = randomOffset(random, blockpos, smalldripstoneconfiguration);
				if (searchAndTryToPlaceIce(worldgenlevel, random, blockpos1, smalldripstoneconfiguration)) {
					flag = true;
				}
			}

			return flag;
		}
	}

	private static boolean searchAndTryToPlaceIce(WorldGenLevel p_160351_, Random p_160352_, BlockPos p_160353_, SmallDripstoneConfiguration p_160354_) {
		Direction direction = Direction.getRandom(p_160352_);
		Direction direction1 = p_160352_.nextBoolean() ? Direction.UP : Direction.DOWN;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_160353_.mutable();

		for (int i = 0; i < p_160354_.emptySpaceSearchRadius; ++i) {
			if (!isEmptyOrWater(p_160351_, blockpos$mutableblockpos)) {
				return false;
			}

			if (tryToPlaceIce(p_160351_, p_160352_, blockpos$mutableblockpos, direction1, p_160354_)) {
				return true;
			}

			if (tryToPlaceIce(p_160351_, p_160352_, blockpos$mutableblockpos, direction1.getOpposite(), p_160354_)) {
				return true;
			}

			blockpos$mutableblockpos.move(direction);
		}

		return false;
	}

	private static boolean tryToPlaceIce(WorldGenLevel p_160356_, Random p_160357_, BlockPos p_160358_, Direction p_160359_, SmallDripstoneConfiguration p_160360_) {
		if (!isEmptyOrWater(p_160356_, p_160358_)) {
			return false;
		} else {
			BlockPos blockpos = p_160358_.relative(p_160359_.getOpposite());
			BlockState blockstate = p_160356_.getBlockState(blockpos);
			if (!isIceBase(blockstate)) {
				return false;
			} else {
				createPatchOfDripstoneBlocks(p_160356_, p_160357_, blockpos);
				int i = p_160357_.nextFloat() < p_160360_.chanceOfTallerDripstone && isEmptyOrWater(p_160356_, p_160358_.relative(p_160359_)) ? 2 : 1;
				growPointedIce(p_160356_, p_160358_, p_160359_, i, false);
				return true;
			}
		}
	}

	public static boolean isIceBase(BlockState p_159663_) {
		return p_159663_.is(Blocks.PACKED_ICE) || p_159663_.is(FrostTags.Blocks.BASE_STONE_FROSTREALM);
	}

	protected static void growPointedIce(WorldGenLevel p_159644_, BlockPos p_159645_, Direction p_159646_, int p_159647_, boolean p_159648_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159645_.mutable();
		buildBaseToTipColumn(p_159646_, p_159647_, p_159648_, (p_159635_) -> {
			if (p_159635_.is(FrostBlocks.POINTED_ICE.get())) {
				p_159635_ = p_159635_.setValue(PointedIceBlock.WATERLOGGED, Boolean.valueOf(p_159644_.isWaterAt(blockpos$mutableblockpos)));
			}

			p_159644_.setBlock(blockpos$mutableblockpos, p_159635_, 2);
			blockpos$mutableblockpos.move(p_159646_);
		});
	}

	protected static void buildBaseToTipColumn(Direction p_159652_, int p_159653_, boolean p_159654_, Consumer<BlockState> p_159655_) {
		if (p_159653_ >= 3) {
			p_159655_.accept(createPointedIce(p_159652_, DripstoneThickness.BASE));

			for (int i = 0; i < p_159653_ - 3; ++i) {
				p_159655_.accept(createPointedIce(p_159652_, DripstoneThickness.MIDDLE));
			}
		}

		if (p_159653_ >= 2) {
			p_159655_.accept(createPointedIce(p_159652_, DripstoneThickness.FRUSTUM));
		}

		if (p_159653_ >= 1) {
			p_159655_.accept(createPointedIce(p_159652_, p_159654_ ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
		}

	}

	private static BlockState createPointedIce(Direction p_159657_, DripstoneThickness p_159658_) {
		return FrostBlocks.POINTED_ICE.get().defaultBlockState().setValue(PointedIceBlock.TIP_DIRECTION, p_159657_).setValue(PointedIceBlock.THICKNESS, p_159658_);
	}

	protected static boolean placeIceBlockIfPossible(WorldGenLevel p_159637_, BlockPos p_159638_) {
		BlockState blockstate = p_159637_.getBlockState(p_159638_);
		if (blockstate.is(FrostTags.Blocks.BASE_STONE_FROSTREALM)) {
			p_159637_.setBlock(p_159638_, Blocks.PACKED_ICE.defaultBlockState(), 2);
			return true;
		} else {
			return false;
		}
	}

	private static void createPatchOfDripstoneBlocks(WorldGenLevel p_160347_, Random p_160348_, BlockPos p_160349_) {
		placeIceBlockIfPossible(p_160347_, p_160349_);

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(p_160348_.nextFloat() < 0.3F)) {
				BlockPos blockpos = p_160349_.relative(direction);
				placeIceBlockIfPossible(p_160347_, blockpos);
				if (!p_160348_.nextBoolean()) {
					BlockPos blockpos1 = blockpos.relative(Direction.getRandom(p_160348_));
					placeIceBlockIfPossible(p_160347_, blockpos1);
					if (!p_160348_.nextBoolean()) {
						BlockPos blockpos2 = blockpos1.relative(Direction.getRandom(p_160348_));
						placeIceBlockIfPossible(p_160347_, blockpos2);
					}
				}
			}
		}

	}

	private static BlockPos randomOffset(Random p_160364_, BlockPos p_160365_, SmallDripstoneConfiguration p_160366_) {
		return p_160365_.offset(Mth.randomBetweenInclusive(p_160364_, -p_160366_.maxOffsetFromOrigin, p_160366_.maxOffsetFromOrigin), Mth.randomBetweenInclusive(p_160364_, -p_160366_.maxOffsetFromOrigin, p_160366_.maxOffsetFromOrigin), Mth.randomBetweenInclusive(p_160364_, -p_160366_.maxOffsetFromOrigin, p_160366_.maxOffsetFromOrigin));
	}
}