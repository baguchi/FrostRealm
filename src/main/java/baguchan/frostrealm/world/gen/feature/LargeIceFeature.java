package baguchan.frostrealm.world.gen.feature;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class LargeIceFeature extends Feature<LargeDripstoneConfiguration> {
	public LargeIceFeature(Codec<LargeDripstoneConfiguration> p_159960_) {
		super(p_159960_);
	}

	public boolean place(FeaturePlaceContext<LargeDripstoneConfiguration> p_159967_) {
		WorldGenLevel worldgenlevel = p_159967_.level();
		BlockPos blockpos = p_159967_.origin();
		LargeDripstoneConfiguration largedripstoneconfiguration = p_159967_.config();
		RandomSource random = p_159967_.random();
		if (!isEmptyOrWater(worldgenlevel, blockpos)) {
			return false;
		} else {
			Optional<Column> optional = Column.scan(worldgenlevel, blockpos, largedripstoneconfiguration.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, LargeIceFeature::isIceBase);
			if (optional.isPresent() && optional.get() instanceof Column.Range) {
				Column.Range column$range = (Column.Range) optional.get();
				if (column$range.height() < 4) {
					return false;
				} else {
					int i = (int) ((float) column$range.height() * largedripstoneconfiguration.maxColumnRadiusToCaveHeightRatio);
					int j = Mth.clamp(i, largedripstoneconfiguration.columnRadius.getMinValue(), largedripstoneconfiguration.columnRadius.getMaxValue());
					int k = Mth.randomBetweenInclusive(random, largedripstoneconfiguration.columnRadius.getMinValue(), j);
					LargeIceFeature.LargeDripstone LargeIceFeature$largedripstone = makeDripstone(blockpos.atY(column$range.ceiling() - 1), false, random, k, largedripstoneconfiguration.stalactiteBluntness, largedripstoneconfiguration.heightScale);
					LargeIceFeature.LargeDripstone LargeIceFeature$largedripstone1 = makeDripstone(blockpos.atY(column$range.floor() + 1), true, random, k, largedripstoneconfiguration.stalagmiteBluntness, largedripstoneconfiguration.heightScale);
					LargeIceFeature.WindOffsetter LargeIceFeature$windoffsetter;
					if (LargeIceFeature$largedripstone.isSuitableForWind(largedripstoneconfiguration) && LargeIceFeature$largedripstone1.isSuitableForWind(largedripstoneconfiguration)) {
						LargeIceFeature$windoffsetter = new LargeIceFeature.WindOffsetter(blockpos.getY(), random, largedripstoneconfiguration.windSpeed);
					} else {
						LargeIceFeature$windoffsetter = LargeIceFeature.WindOffsetter.noWind();
					}

					boolean flag = LargeIceFeature$largedripstone.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldgenlevel, LargeIceFeature$windoffsetter);
					boolean flag1 = LargeIceFeature$largedripstone1.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldgenlevel, LargeIceFeature$windoffsetter);
					if (flag) {
						LargeIceFeature$largedripstone.placeBlocks(worldgenlevel, random, LargeIceFeature$windoffsetter);
					}

					if (flag1) {
						LargeIceFeature$largedripstone1.placeBlocks(worldgenlevel, random, LargeIceFeature$windoffsetter);
					}

					return true;
				}
			} else {
				return false;
			}
		}
	}

	public static boolean isIceBase(BlockState p_159663_) {
		return p_159663_.is(Blocks.PACKED_ICE) || p_159663_.is(FrostTags.Blocks.BASE_STONE_FROSTREALM);
	}

	private static LargeIceFeature.LargeDripstone makeDripstone(BlockPos p_159969_, boolean p_159970_, RandomSource p_159971_, int p_159972_, FloatProvider p_159973_, FloatProvider p_159974_) {
		return new LargeIceFeature.LargeDripstone(p_159969_, p_159970_, p_159972_, p_159973_.sample(p_159971_), p_159974_.sample(p_159971_));
	}

	static final class LargeDripstone {
		private BlockPos root;
		private final boolean pointingUp;
		private int radius;
		private final double bluntness;
		private final double scale;

		LargeDripstone(BlockPos p_159981_, boolean p_159982_, int p_159983_, double p_159984_, double p_159985_) {
			this.root = p_159981_;
			this.pointingUp = p_159982_;
			this.radius = p_159983_;
			this.bluntness = p_159984_;
			this.scale = p_159985_;
		}

		private int getHeight() {
			return this.getHeightAtRadius(0.0F);
		}

		private int getMinY() {
			return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
		}

		private int getMaxY() {
			return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
		}

		boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel p_159990_, LargeIceFeature.WindOffsetter p_159991_) {
			while (this.radius > 1) {
				BlockPos.MutableBlockPos blockpos$mutableblockpos = this.root.mutable();
				int i = Math.min(10, this.getHeight());

				for (int j = 0; j < i; ++j) {
					if (p_159990_.getBlockState(blockpos$mutableblockpos).is(Blocks.LAVA)) {
						return false;
					}

					if (isCircleMostlyEmbeddedInStone(p_159990_, p_159991_.offset(blockpos$mutableblockpos), this.radius)) {
						this.root = blockpos$mutableblockpos;
						return true;
					}

					blockpos$mutableblockpos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
				}

				this.radius /= 2;
			}

			return false;
		}

		private int getHeightAtRadius(float p_159988_) {
			return (int) getIceHeight(p_159988_, this.radius, this.scale, this.bluntness);
		}

		void placeBlocks(WorldGenLevel p_159993_, RandomSource p_159994_, LargeIceFeature.WindOffsetter p_159995_) {
			for (int i = -this.radius; i <= this.radius; ++i) {
				for (int j = -this.radius; j <= this.radius; ++j) {
					float f = Mth.sqrt((float) (i * i + j * j));
					if (!(f > (float) this.radius)) {
						int k = this.getHeightAtRadius(f);
						if (k > 0) {
							if ((double) p_159994_.nextFloat() < 0.2D) {
								k = (int) ((float) k * Mth.randomBetween(p_159994_, 0.8F, 1.0F));
							}

							BlockPos.MutableBlockPos blockpos$mutableblockpos = this.root.offset(i, 0, j).mutable();
							boolean flag = false;

							for (int l = 0; l < k; ++l) {
								BlockPos blockpos = p_159995_.offset(blockpos$mutableblockpos);
								if (isEmptyOrWater(p_159993_, blockpos)) {
									flag = true;
									Block block = Blocks.PACKED_ICE;
									p_159993_.setBlock(blockpos, block.defaultBlockState(), 2);
								} else if (flag && p_159993_.getBlockState(blockpos).is(FrostBlocks.POINTED_ICE.get())) {
									break;
								}

								blockpos$mutableblockpos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
							}
						}
					}
				}
			}

		}

		boolean isSuitableForWind(LargeDripstoneConfiguration p_159997_) {
			return this.radius >= p_159997_.minRadiusForWind && this.bluntness >= (double) p_159997_.minBluntnessForWind;
		}
	}

	protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel p_159640_, BlockPos p_159641_, int p_159642_) {
		if (isEmptyOrWater(p_159640_, p_159641_)) {
			return false;
		} else {
			float f = 6.0F;
			float f1 = 6.0F / (float) p_159642_;

			for (float f2 = 0.0F; f2 < ((float) Math.PI * 2F); f2 += f1) {
				int i = (int) (Mth.cos(f2) * (float) p_159642_);
				int j = (int) (Mth.sin(f2) * (float) p_159642_);
				if (isEmptyOrWater(p_159640_, p_159641_.offset(i, 0, j))) {
					return false;
				}
			}

			return true;
		}
	}

	protected static boolean isEmptyOrWater(LevelAccessor p_159629_, BlockPos p_159630_) {
		return p_159629_.isStateAtPosition(p_159630_, DripstoneUtils::isEmptyOrWater);
	}

	protected static double getIceHeight(double p_159624_, double p_159625_, double p_159626_, double p_159627_) {
		if (p_159624_ < p_159627_) {
			p_159624_ = p_159627_;
		}

		double d0 = 0.384D;
		double d1 = p_159624_ / p_159625_ * 0.384D;
		double d2 = 0.75D * Math.pow(d1, 1.3333333333333333D);
		double d3 = Math.pow(d1, 0.6666666666666666D);
		double d4 = 0.3333333333333333D * Math.log(d1);
		double d5 = p_159626_ * (d2 - d3 - d4);
		d5 = Math.max(d5, 0.0D);
		return d5 / 0.384D * p_159625_;
	}

	static final class WindOffsetter {
		private final int originY;
		@Nullable
		private final Vec3 windSpeed;

		WindOffsetter(int p_160004_, RandomSource p_160005_, FloatProvider p_160006_) {
			this.originY = p_160004_;
			float f = p_160006_.sample(p_160005_);
			float f1 = Mth.randomBetween(p_160005_, 0.0F, (float) Math.PI);
			this.windSpeed = new Vec3(Mth.cos(f1) * f, 0.0D, Mth.sin(f1) * f);
		}

		private WindOffsetter() {
			this.originY = 0;
			this.windSpeed = null;
		}

		static LargeIceFeature.WindOffsetter noWind() {
			return new LargeIceFeature.WindOffsetter();
		}

		BlockPos offset(BlockPos p_160009_) {
			if (this.windSpeed == null) {
				return p_160009_;
			} else {
				int i = this.originY - p_160009_.getY();
				Vec3 vec3 = this.windSpeed.scale(i);
				return p_160009_.offset(Mth.floor(vec3.x), 0, Mth.floor(vec3.z));
			}
		}
	}
}