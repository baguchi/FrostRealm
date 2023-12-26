package baguchan.frostrealm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class NonBucketableLiquidBlock extends LiquidBlock {
    public NonBucketableLiquidBlock(Supplier<? extends FlowingFluid> fluid, Properties p_54695_) {
        super(fluid, p_54695_);
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player p_295410_, LevelAccessor p_153772_, BlockPos p_153773_, BlockState p_153774_) {
        return ItemStack.EMPTY;
    }
}
