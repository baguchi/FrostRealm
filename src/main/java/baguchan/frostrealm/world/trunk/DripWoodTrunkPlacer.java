package baguchan.frostrealm.world.trunk;

import baguchan.frostrealm.registry.FrostTrunkPlacerTypes;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DripWoodTrunkPlacer extends TrunkPlacer {
    private static final Codec<UniformInt> BRANCH_START_CODEC = UniformInt.CODEC.codec().validate((p_275181_) -> p_275181_.getMaxValue() - p_275181_.getMinValue() < 1 ? DataResult.error(() -> "Need at least 2 blocks variation for the branch starts to fit both branches") : DataResult.success(p_275181_));
    public static final MapCodec<DripWoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((p_338099_) -> trunkPlacerParts(p_338099_).apply(p_338099_, DripWoodTrunkPlacer::new));

    public DripWoodTrunkPlacer(int p_70165_, int p_70166_, int p_70167_) {
        super(p_70165_, p_70166_, p_70167_);
   }

    protected TrunkPlacerType<?> type() {
        return FrostTrunkPlacerTypes.DRIP_WOOD_TRUNK_PLACER.value();
    }

    private static float treeShape(int p_70133_, int p_70134_) {
        if (p_70134_ < p_70133_ * 0.3F) {
            return -1.0F;
        } else {
            float f = p_70133_ / 2.0F;
            float f1 = f - p_70134_;
            float f2 = Mth.sqrt(f * f - f1 * f1);
            if (f1 == 0.0F) {
                f2 = f;
            } else if (Math.abs(f1) >= f) {
                return 0.0F;
            }

            return f2 * 0.5F;
        }
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader p_226123_, BiConsumer<BlockPos, BlockState> p_226124_, RandomSource p_226125_, int p_226126_, BlockPos p_226127_, TreeConfiguration p_226128_) {

        int j = p_226126_ + 2;
        int k = Mth.floor(j * 0.618);

        int i1 = p_226127_.getY() + k;
        int l = Math.min(1, Mth.floor(1.382 + Math.pow(1.0 * j / 13.0, 2.0)));
        int j1 = j - p_226126_ + 5;
        BlockPos blockpos = p_226127_.below();
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos, p_226128_);
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos.east(), p_226128_);
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos.south(), p_226128_);
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos.south().east(), p_226128_);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        List<FoliageCoords> list = Lists.newArrayList();
        float randomA = p_226125_.nextInt(2);
        float randomB = p_226125_.nextInt(4);
        for (int i = 0; i < p_226126_; ++i) {
            float shaper = 0.15F;

            int distance = (int) (blockpos.distManhattan(blockpos$mutableblockpos) * shaper);

            this.placeLogIfFreeWithOffset(p_226123_, p_226124_, p_226125_, blockpos$mutableblockpos, p_226128_, p_226127_, 0, i, 0);
            float f = treeShape(j, j1);
            if (!(f < 0.0F)) {
                for (int k1 = 0; k1 < l; k1++) {
                    double d1 = 1.0;
                    double d2 = 1.0 * f * (p_226125_.nextFloat() + 0.328);
                    double d3 = p_226125_.nextFloat() * 2.0F * Math.PI;
                    double d4 = d2 * Math.sin(d3) + 0.5;
                    double d5 = d2 * Math.cos(d3) + 0.5;
                    BlockPos blockpos3 = p_226127_.offset(Mth.floor(d4), j1 - 1, Mth.floor(d5));
                    BlockPos blockpos1 = blockpos3.above(5);
                    if (this.makeLimb(p_226123_, p_226124_, p_226125_, blockpos3, blockpos1, false, p_226128_)) {
                        int l1 = p_226127_.getX() - blockpos3.getX();
                        int i2 = p_226127_.getZ() - blockpos3.getZ();
                        double d6 = blockpos3.getY() - Math.sqrt(l1 * l1 + i2 * i2) * 0.381;
                        int j2 = d6 > i1 ? i1 : (int)d6;
                        BlockPos blockpos2 = new BlockPos(p_226127_.getX(), j2, p_226127_.getZ());
                        if (this.makeLimb(p_226123_, p_226124_, p_226125_, blockpos2, blockpos3, false, p_226128_)) {
                            list.add(new FoliageCoords(blockpos3, blockpos2.getY()));
                        }
                    }
                }
            }
            if (distance < p_226126_ / (16 + randomA) || i == 0) {
                this.placeLogIfFreeWithOffset(p_226123_, p_226124_, p_226125_, blockpos$mutableblockpos, p_226128_, p_226127_, 1, i, 1);
            }
            if (distance < p_226126_ / (9 + randomB) || i == 0) {
                this.placeLogIfFreeWithOffset(p_226123_, p_226124_, p_226125_, blockpos$mutableblockpos, p_226128_, p_226127_, 1, i, 0);

            }
            if (distance < p_226126_ / 7 || i == 0) {

                this.placeLogIfFreeWithOffset(p_226123_, p_226124_, p_226125_, blockpos$mutableblockpos, p_226128_, p_226127_, 0, i, 1);
            }

        }

        list.add(new FoliageCoords(p_226127_.above(j + 1), i1));

        this.makeLimb(p_226123_, p_226124_, p_226125_, p_226127_, p_226127_.above(k), true, p_226128_);

        this.makeBranches(p_226123_, p_226124_, p_226125_, j, p_226127_, list, p_226128_);


        List<FoliagePlacer.FoliageAttachment> list1 = Lists.newArrayList();

        for (FoliageCoords fancytrunkplacer$foliagecoords : list) {
            if (this.trimBranches(j, fancytrunkplacer$foliagecoords.getBranchBase() - p_226127_.getY())) {
                list1.add(fancytrunkplacer$foliagecoords.attachment);
            }
        }

        return list1;
    }

    private boolean makeLimb(
            LevelSimulatedReader p_226108_,
            BiConsumer<BlockPos, BlockState> p_226109_,
            RandomSource p_226110_,
            BlockPos p_226111_,
            BlockPos p_226112_,
            boolean p_226113_,
            TreeConfiguration p_226114_
    ) {
        if (!p_226113_ && Objects.equals(p_226111_, p_226112_)) {
            return true;
        } else {
            BlockPos blockpos = p_226112_.offset(-p_226111_.getX(), -p_226111_.getY(), -p_226111_.getZ());
            int i = this.getSteps(blockpos);
            float f = (float)blockpos.getX() / i;
            float f1 = (float)blockpos.getY() / i;
            float f2 = (float)blockpos.getZ() / i;

            for (int j = 0; j <= i; j++) {
                BlockPos blockpos1 = p_226111_.offset(Mth.floor(0.5F + j * f), Mth.floor(0.5F + j * f1), Mth.floor(0.5F + j * f2));
                if (p_226113_) {
                    this.placeLog(
                            p_226108_,
                            p_226109_,
                            p_226110_,
                            blockpos1,
                            p_226114_,
                            p_360249_ -> p_360249_.trySetValue(RotatedPillarBlock.AXIS, this.getLogAxis(p_226111_, blockpos1))
                    );
                } else if (!this.isFree(p_226108_, blockpos1)) {
                    return false;
                }
            }

            return true;
        }
    }

    private int getSteps(BlockPos p_70128_) {
        int i = Mth.abs(p_70128_.getX());
        int j = Mth.abs(p_70128_.getY());
        int k = Mth.abs(p_70128_.getZ());
        return Math.max(i, Math.max(j, k));
    }

    private Direction.Axis getLogAxis(BlockPos p_70130_, BlockPos p_70131_) {
        Direction.Axis direction$axis = Direction.Axis.Y;
        int i = Math.abs(p_70131_.getX() - p_70130_.getX());
        int j = Math.abs(p_70131_.getZ() - p_70130_.getZ());
        int k = Math.max(i, j);
        if (k > 0) {
            if (i == k) {
                direction$axis = Direction.Axis.X;
            } else {
                direction$axis = Direction.Axis.Z;
            }
        }

        return direction$axis;
    }

    private boolean trimBranches(int p_70099_, int p_70100_) {
        return p_70100_ >= p_70099_ * 0.2;
    }


    private void makeBranches(
            LevelSimulatedReader p_226100_,
            BiConsumer<BlockPos, BlockState> p_226101_,
            RandomSource p_226102_,
            int p_226103_,
            BlockPos p_226104_,
            List<FoliageCoords> p_226105_,
            TreeConfiguration p_226106_
    ) {
        for (FoliageCoords fancytrunkplacer$foliagecoords : p_226105_) {
            int i = fancytrunkplacer$foliagecoords.getBranchBase();
            BlockPos blockpos = new BlockPos(p_226104_.getX(), i, p_226104_.getZ());
            if (!blockpos.equals(fancytrunkplacer$foliagecoords.attachment.pos()) && this.trimBranches(p_226103_, i - p_226104_.getY())) {
                this.makeLimb(p_226100_, p_226101_, p_226102_, blockpos, fancytrunkplacer$foliagecoords.attachment.pos(), true, p_226106_);
            }
        }
    }


    private void placeLogIfFreeWithOffset(LevelSimulatedReader p_226130_, BiConsumer<BlockPos, BlockState> p_226131_, RandomSource p_226132_, BlockPos.MutableBlockPos p_226133_, TreeConfiguration p_226134_, BlockPos p_226135_, int p_226136_, int p_226137_, int p_226138_) {
        p_226133_.setWithOffset(p_226135_, p_226136_, p_226137_, p_226138_);
        this.placeLogIfFree(p_226130_, p_226131_, p_226132_, p_226133_, p_226134_);
    }

    static class FoliageCoords {
        final FoliagePlacer.FoliageAttachment attachment;
        private final int branchBase;

        public FoliageCoords(BlockPos p_70140_, int p_70141_) {
            this.attachment = new FoliagePlacer.FoliageAttachment(p_70140_, 0, false);
            this.branchBase = p_70141_;
        }

        public int getBranchBase() {
            return this.branchBase;
        }
    }
}
