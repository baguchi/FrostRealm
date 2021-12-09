package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.block.PointedIceBlock;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Consumer;

public class SmallIceFeature extends Feature<DripstoneClusterConfiguration> {
	public SmallIceFeature(Codec<DripstoneClusterConfiguration> p_160345_) {
		super(p_160345_);
	}

	protected static boolean isEmptyOrWater(LevelAccessor p_159629_, BlockPos p_159630_) {
		return p_159629_.isStateAtPosition(p_159630_, DripstoneUtils::isEmptyOrWater);
	}

	public boolean place(FeaturePlaceContext<DripstoneClusterConfiguration> p_159605_) {
		WorldGenLevel worldgenlevel = p_159605_.level();
		BlockPos blockpos = p_159605_.origin();
		DripstoneClusterConfiguration dripstoneclusterconfiguration = p_159605_.config();
		Random random = p_159605_.random();
		if (!isEmptyOrWater(worldgenlevel, blockpos)) {
			return false;
		} else {
			int i = dripstoneclusterconfiguration.height.sample(random);
			float f = dripstoneclusterconfiguration.wetness.sample(random);
			float f1 = dripstoneclusterconfiguration.density.sample(random);
			int j = dripstoneclusterconfiguration.radius.sample(random);
			int k = dripstoneclusterconfiguration.radius.sample(random);

			for(int l = -j; l <= j; ++l) {
				for(int i1 = -k; i1 <= k; ++i1) {
					double d0 = this.getChanceOfStalagmiteOrStalactite(j, k, l, i1, dripstoneclusterconfiguration);
					BlockPos blockpos1 = blockpos.offset(l, 0, i1);
					this.placeColumn(worldgenlevel, random, blockpos1, l, i1, f, d0, i, f1, dripstoneclusterconfiguration);
				}
			}

			return true;
		}
	}

	private void placeColumn(WorldGenLevel p_159594_, Random p_159595_, BlockPos p_159596_, int p_159597_, int p_159598_, float p_159599_, double p_159600_, int p_159601_, float p_159602_, DripstoneClusterConfiguration p_159603_) {
		Optional<Column> optional = Column.scan(p_159594_, p_159596_, p_159603_.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, SmallIceFeature::isIceBase);
		if (optional.isPresent()) {
			OptionalInt optionalint = optional.get().getCeiling();
			OptionalInt optionalint1 = optional.get().getFloor();
			if (optionalint.isPresent() || optionalint1.isPresent()) {
				boolean flag = p_159595_.nextFloat() < p_159599_;
				Column column;
				if (flag && optionalint1.isPresent() && this.canPlacePool(p_159594_, p_159596_.atY(optionalint1.getAsInt()))) {
					int i = optionalint1.getAsInt();
					column = optional.get().withFloor(OptionalInt.of(i - 1));
					p_159594_.setBlock(p_159596_.atY(i), Blocks.WATER.defaultBlockState(), 2);
				} else {
					column = optional.get();
				}

				OptionalInt optionalint2 = column.getFloor();
				boolean flag1 = p_159595_.nextDouble() < p_159600_;
				int j;
				if (optionalint.isPresent() && flag1) {
					int k = p_159603_.dripstoneBlockLayerThickness.sample(p_159595_);
					this.replaceBlocksWithDripstoneBlocks(p_159594_, p_159596_.atY(optionalint.getAsInt()), k, Direction.UP);
					int l;
					if (optionalint2.isPresent()) {
						l = Math.min(p_159601_, optionalint.getAsInt() - optionalint2.getAsInt());
					} else {
						l = p_159601_;
					}

					j = this.getDripstoneHeight(p_159595_, p_159597_, p_159598_, p_159602_, l, p_159603_);
				} else {
					j = 0;
				}

				boolean flag2 = p_159595_.nextDouble() < p_159600_;
				int i3;
				if (optionalint2.isPresent() && flag2) {
					int i1 = p_159603_.dripstoneBlockLayerThickness.sample(p_159595_);
					this.replaceBlocksWithDripstoneBlocks(p_159594_, p_159596_.atY(optionalint2.getAsInt()), i1, Direction.DOWN);
					if (optionalint.isPresent()) {
						i3 = Math.max(0, j + Mth.randomBetweenInclusive(p_159595_, -p_159603_.maxStalagmiteStalactiteHeightDiff, p_159603_.maxStalagmiteStalactiteHeightDiff));
					} else {
						i3 = this.getDripstoneHeight(p_159595_, p_159597_, p_159598_, p_159602_, p_159601_, p_159603_);
					}
				} else {
					i3 = 0;
				}

				int j1;
				int j3;
				if (optionalint.isPresent() && optionalint2.isPresent() && optionalint.getAsInt() - j <= optionalint2.getAsInt() + i3) {
					int k1 = optionalint2.getAsInt();
					int l1 = optionalint.getAsInt();
					int i2 = Math.max(l1 - j, k1 + 1);
					int j2 = Math.min(k1 + i3, l1 - 1);
					int k2 = Mth.randomBetweenInclusive(p_159595_, i2, j2 + 1);
					int l2 = k2 - 1;
					j3 = l1 - k2;
					j1 = l2 - k1;
				} else {
					j3 = j;
					j1 = i3;
				}

				boolean flag3 = p_159595_.nextBoolean() && j3 > 0 && j1 > 0 && column.getHeight().isPresent() && j3 + j1 == column.getHeight().getAsInt();
				if (optionalint.isPresent()) {
					growPointedIce(p_159594_, p_159596_.atY(optionalint.getAsInt() - 1), Direction.DOWN, j3, flag3);
				}

				if (optionalint2.isPresent()) {
					growPointedIce(p_159594_, p_159596_.atY(optionalint2.getAsInt() + 1), Direction.UP, j1, flag3);
				}

			}
		}
	}

	private int getDripstoneHeight(Random p_159613_, int p_159614_, int p_159615_, float p_159616_, int p_159617_, DripstoneClusterConfiguration p_159618_) {
		if (p_159613_.nextFloat() > p_159616_) {
			return 0;
		} else {
			int i = Math.abs(p_159614_) + Math.abs(p_159615_);
			float f = (float)Mth.clampedMap((double)i, 0.0D, (double)p_159618_.maxDistanceFromCenterAffectingHeightBias, (double)p_159617_ / 2.0D, 0.0D);
			return (int)randomBetweenBiased(p_159613_, 0.0F, (float)p_159617_, f, (float)p_159618_.heightDeviation);
		}
	}

	private boolean canPlacePool(WorldGenLevel p_159620_, BlockPos p_159621_) {
		BlockState blockstate = p_159620_.getBlockState(p_159621_);
		if (!blockstate.is(Blocks.WATER) && !blockstate.is(Blocks.DRIPSTONE_BLOCK) && !blockstate.is(Blocks.POINTED_DRIPSTONE)) {
			for(Direction direction : Direction.Plane.HORIZONTAL) {
				if (!this.canBeAdjacentToWater(p_159620_, p_159621_.relative(direction))) {
					return false;
				}
			}

			return this.canBeAdjacentToWater(p_159620_, p_159621_.below());
		} else {
			return false;
		}
	}

	private boolean canBeAdjacentToWater(LevelAccessor p_159583_, BlockPos p_159584_) {
		BlockState blockstate = p_159583_.getBlockState(p_159584_);
		return blockstate.is(FrostTags.Blocks.BASE_STONE_FROSTREALM) || blockstate.getFluidState().is(FluidTags.WATER);
	}

	private void replaceBlocksWithDripstoneBlocks(WorldGenLevel p_159589_, BlockPos p_159590_, int p_159591_, Direction p_159592_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159590_.mutable();

		for(int i = 0; i < p_159591_; ++i) {
			if (!placeIceBlockIfPossible(p_159589_, blockpos$mutableblockpos)) {
				return;
			}

			blockpos$mutableblockpos.move(p_159592_);
		}

	}

	private double getChanceOfStalagmiteOrStalactite(int p_159577_, int p_159578_, int p_159579_, int p_159580_, DripstoneClusterConfiguration p_159581_) {
		int i = p_159577_ - Math.abs(p_159579_);
		int j = p_159578_ - Math.abs(p_159580_);
		int k = Math.min(i, j);
		return (double)Mth.clampedMap((float)k, 0.0F, (float)p_159581_.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, p_159581_.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0F);
	}

	private static float randomBetweenBiased(Random p_159607_, float p_159608_, float p_159609_, float p_159610_, float p_159611_) {
		return ClampedNormalFloat.sample(p_159607_, p_159610_, p_159611_, p_159608_, p_159609_);
	}

	public static boolean isIceBase(BlockState p_159663_) {
		return p_159663_.is(Blocks.PACKED_ICE) || p_159663_.is(FrostTags.Blocks.BASE_STONE_FROSTREALM);
	}

	protected static void growPointedIce(WorldGenLevel p_159644_, BlockPos p_159645_, Direction p_159646_, int p_159647_, boolean p_159648_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159645_.mutable();
		buildBaseToTipColumn(p_159646_, p_159647_, p_159648_, (p_159635_) -> {
			if (p_159635_.is(FrostBlocks.POINTED_ICE)) {
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
		return FrostBlocks.POINTED_ICE.defaultBlockState().setValue(PointedIceBlock.TIP_DIRECTION, p_159657_).setValue(PointedIceBlock.THICKNESS, p_159658_);
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
}