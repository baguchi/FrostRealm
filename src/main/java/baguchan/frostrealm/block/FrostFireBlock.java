package baguchan.frostrealm.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

public class FrostFireBlock extends BaseFireBlock {

    public static final MapCodec<FrostFireBlock> CODEC = simpleCodec(FrostFireBlock::new);

    @Override
    public MapCodec<FrostFireBlock> codec() {
        return CODEC;
    }

    public FrostFireBlock(Properties p_49241_) {
        super(p_49241_, 2.0F);
    }

    @Override
    protected BlockState updateShape(
            BlockState p_56659_,
            LevelReader p_374511_,
            ScheduledTickAccess p_374567_,
            BlockPos p_56663_,
            Direction p_56660_,
            BlockPos p_56664_,
            BlockState p_56661_,
            RandomSource p_374480_
    ) {
        return this.canSurvive(p_56659_, p_374511_, p_56663_) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState p_56655_, LevelReader p_56656_, BlockPos p_56657_) {
        return !p_56655_.getCollisionShape(p_56656_, p_56657_.below()).getFaceShape(Direction.UP).isEmpty() || p_56656_.getBlockState(p_56657_.below()).isCollisionShapeFullBlock(p_56656_, p_56657_);
    }

    @Override
    protected void entityInside(BlockState p_49260_, Level p_49261_, BlockPos p_49262_, Entity p_49263_, InsideBlockEffectApplier p_404795_) {
        if (p_49261_ instanceof ServerLevel serverLevel) {
            p_49263_.setTicksFrozen(Mth.clamp(p_49263_.getTicksFrozen() + 5, 0, 600));
        }
    }

    @Override
    protected boolean canBurn(BlockState p_49284_) {
        return false;
    }

    @Override
    public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.DANGER_OTHER;
    }

    @Override
    public @Nullable PathType getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, PathType originalType) {
        return PathType.DAMAGE_OTHER;
    }
}
