package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DripHangingMossBlock extends HangingMossBlock {
    public DripHangingMossBlock(Properties p_379903_) {
        super(p_379903_);
    }

    @Override
    public void animateTick(BlockState p_379410_, Level p_379865_, BlockPos p_379365_, RandomSource p_380130_) {
    }

    @Override
    protected boolean canSurvive(BlockState p_380096_, LevelReader p_379969_, BlockPos p_380283_) {
        return this.canStayAtPosition(p_379969_, p_380283_);
    }

    private boolean canStayAtPosition(BlockGetter p_379546_, BlockPos p_379355_) {
        BlockPos blockpos = p_379355_.relative(Direction.UP);
        BlockState blockstate = p_379546_.getBlockState(blockpos);
        return MultifaceBlock.canAttachTo(p_379546_, Direction.UP, blockpos, blockstate) || blockstate.is(FrostBlocks.DRIP_HANGING_LEAVES);
    }

    @Override
    protected BlockState updateShape(
            BlockState p_380182_,
            LevelReader p_380219_,
            ScheduledTickAccess p_380011_,
            BlockPos p_380024_,
            Direction p_380101_,
            BlockPos p_380258_,
            BlockState p_379654_,
            RandomSource p_379547_
    ) {
        if (!this.canStayAtPosition(p_380219_, p_380024_)) {
            p_380011_.scheduleTick(p_380024_, this, 1);
        }

        return p_380182_.setValue(TIP, !p_380219_.getBlockState(p_380024_.below()).is(this));
    }

    @Override
    protected void tick(BlockState p_381085_, ServerLevel p_381014_, BlockPos p_381010_, RandomSource p_380962_) {
        if (!this.canStayAtPosition(p_381014_, p_381010_)) {
            p_381014_.destroyBlock(p_381010_, true);
        }
    }

}
