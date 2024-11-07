package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class FrostBiteLogBlock extends RotatedPillarBlock {
    public static final MapCodec<FrostBiteLogBlock> CODEC = simpleCodec(FrostBiteLogBlock::new);
    public static final BooleanProperty WAXED = BooleanProperty.create("wax");

    @Override
    public MapCodec<? extends FrostBiteLogBlock> codec() {
        return CODEC;
    }


    public FrostBiteLogBlock(Properties p_55926_) {
        super(p_55926_);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(WAXED, false));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack p_316304_, BlockState p_316362_, Level p_316459_, BlockPos p_316366_, Player p_316132_, InteractionHand p_316595_, BlockHitResult p_316140_) {
        if (p_316304_.is(FrostItems.CRYONITE_CREAM) && !p_316362_.getValue(WAXED)) {
            p_316459_.setBlock(p_316366_, p_316362_.setValue(WAXED, true), 3);
            p_316459_.playLocalSound(p_316366_, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1, false);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(p_316304_, p_316362_, p_316459_, p_316366_, p_316132_, p_316595_, p_316140_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55933_) {
        p_55933_.add(AXIS, WAXED);
    }

}
