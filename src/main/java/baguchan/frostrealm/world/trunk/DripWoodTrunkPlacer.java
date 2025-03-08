package baguchan.frostrealm.world.trunk;

import baguchan.frostrealm.registry.FrostTrunkPlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DripWoodTrunkPlacer extends TrunkPlacer {
    private static final Codec<UniformInt> BRANCH_START_CODEC = UniformInt.CODEC.codec().validate((p_275181_) -> p_275181_.getMaxValue() - p_275181_.getMinValue() < 1 ? DataResult.error(() -> "Need at least 2 blocks variation for the branch starts to fit both branches") : DataResult.success(p_275181_));
    public static final MapCodec<DripWoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((p_338099_) -> trunkPlacerParts(p_338099_).and(p_338099_.group(IntProvider.codec(1, 3).fieldOf("branch_count").forGetter((p_272644_) -> p_272644_.branchCount), IntProvider.codec(2, 16).fieldOf("branch_horizontal_length").forGetter((p_273612_) -> p_273612_.branchHorizontalLength), IntProvider.validateCodec(-24, 0, BRANCH_START_CODEC).fieldOf("branch_start_offset_from_top").forGetter((p_272705_) -> p_272705_.branchStartOffsetFromTop), IntProvider.codec(-24, 16).fieldOf("branch_end_offset_from_top").forGetter((p_273633_) -> p_273633_.branchEndOffsetFromTop))).apply(p_338099_, DripWoodTrunkPlacer::new));

    private final IntProvider branchCount;
    private final IntProvider branchHorizontalLength;
    private final UniformInt branchStartOffsetFromTop;
    private final UniformInt secondBranchStartOffsetFromTop;
    private final IntProvider branchEndOffsetFromTop;

    public DripWoodTrunkPlacer(int p_70165_, int p_70166_, int p_70167_, IntProvider p_272873_, IntProvider p_272789_, UniformInt p_272917_, IntProvider p_272948_) {
        super(p_70165_, p_70166_, p_70167_);
        this.branchCount = p_272873_;
        this.branchHorizontalLength = p_272789_;
        this.branchStartOffsetFromTop = p_272917_;
        this.secondBranchStartOffsetFromTop = UniformInt.of(p_272917_.getMinValue(), p_272917_.getMaxValue() - 1);
        this.branchEndOffsetFromTop = p_272948_;
    }

    protected TrunkPlacerType<?> type() {
        return FrostTrunkPlacerTypes.DRIP_WOOD_TRUNK_PLACER.value();
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader p_226123_, BiConsumer<BlockPos, BlockState> p_226124_, RandomSource p_226125_, int p_226126_, BlockPos p_226127_, TreeConfiguration p_226128_) {
        BlockPos blockpos = p_226127_.below();
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos, p_226128_);
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos.east(), p_226128_);
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos.south(), p_226128_);
        setDirtAt(p_226123_, p_226124_, p_226125_, blockpos.south().east(), p_226128_);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        float randomA = p_226125_.nextInt(2);
        float randomB = p_226125_.nextInt(4);
        for (int i = 0; i < p_226126_; ++i) {
            float shaper = 0.15F;

            int distance = (int) (blockpos.distManhattan(blockpos$mutableblockpos) * shaper);

            this.placeLogIfFreeWithOffset(p_226123_, p_226124_, p_226125_, blockpos$mutableblockpos, p_226128_, p_226127_, 0, i, 0);

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
        int i = Math.max(0, p_226126_ - 1 + this.branchStartOffsetFromTop.sample(p_226125_));
        int j = Math.max(0, p_226126_ - 1 + this.secondBranchStartOffsetFromTop.sample(p_226125_));
        if (j >= i) {
            ++j;
        }

        int k = this.branchCount.sample(p_226125_);
        boolean flag = k == 3;
        boolean flag1 = k >= 2;

        List<FoliagePlacer.FoliageAttachment> list = new ArrayList();

        int l;
        if (flag) {
            l = p_226126_;
        } else if (flag1) {
            l = Math.max(i, j) + 1;
        } else {
            l = i + 1;
        }

        list.add(new FoliagePlacer.FoliageAttachment(p_226127_.above(p_226126_), 0, true));

        BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(p_226125_);
        Function<BlockState, BlockState> function = (p_360246_) -> (BlockState) p_360246_.trySetValue(RotatedPillarBlock.AXIS, direction.getAxis());
        list.add(this.generateBranch(p_226123_, p_226124_, p_226125_, p_226126_, p_226127_, p_226128_, function, direction, i, i < l - 1, blockpos$mutableblockpos2));
        if (flag1) {
            list.add(this.generateBranch(p_226123_, p_226124_, p_226125_, p_226126_, p_226127_, p_226128_, function, direction.getOpposite(), j, j < l - 1, blockpos$mutableblockpos2));
        }

        return list;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(LevelSimulatedReader p_272736_, BiConsumer<BlockPos, BlockState> p_273092_, RandomSource p_273449_, int p_272659_, BlockPos p_273743_, TreeConfiguration p_273027_, Function<BlockState, BlockState> p_273558_, Direction p_273712_, int p_272980_, boolean p_272719_, BlockPos.MutableBlockPos p_273496_) {
        p_273496_.set(p_273743_).move(Direction.UP, p_272980_);
        int i = p_272659_ - 1 + this.branchEndOffsetFromTop.sample(p_273449_);
        boolean flag = p_272719_ || i < p_272980_;
        int j = this.branchHorizontalLength.sample(p_273449_) + (flag ? 1 : 0);
        BlockPos blockpos = p_273743_.relative(p_273712_, j).above(i);
        int k = flag ? 2 : 1;

        for (int l = 0; l < k; ++l) {
            this.placeLog(p_272736_, p_273092_, p_273449_, p_273496_.move(p_273712_), p_273027_, p_273558_);
        }

        Direction direction = blockpos.getY() > p_273496_.getY() ? Direction.UP : Direction.DOWN;

        while (true) {
            int i1 = p_273496_.distManhattan(blockpos);
            if (i1 == 0) {
                return new FoliagePlacer.FoliageAttachment(blockpos.above(), 0, false);
            }

            float f = (float) Math.abs(blockpos.getY() - p_273496_.getY()) / (float) i1;
            boolean flag1 = p_273449_.nextFloat() < f;
            p_273496_.move(flag1 ? direction : p_273712_);
            this.placeLog(p_272736_, p_273092_, p_273449_, p_273496_, p_273027_, flag1 ? Function.identity() : p_273558_);
        }
    }

    private void placeLogIfFreeWithOffset(LevelSimulatedReader p_226130_, BiConsumer<BlockPos, BlockState> p_226131_, RandomSource p_226132_, BlockPos.MutableBlockPos p_226133_, TreeConfiguration p_226134_, BlockPos p_226135_, int p_226136_, int p_226137_, int p_226138_) {
        p_226133_.setWithOffset(p_226135_, p_226136_, p_226137_, p_226138_);
        this.placeLogIfFree(p_226130_, p_226131_, p_226132_, p_226133_, p_226134_);
    }
}
