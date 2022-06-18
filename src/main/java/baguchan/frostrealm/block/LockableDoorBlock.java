package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class LockableDoorBlock extends DoorBlock {
	public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;

	public LockableDoorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)).setValue(HINGE, DoorHingeSide.LEFT).setValue(HALF, DoubleBlockHalf.LOWER).setValue(POWERED, Boolean.valueOf(false)).setValue(LOCKED, Boolean.valueOf(false)));

	}

	@Override
	public InteractionResult use(BlockState p_52769_, Level p_52770_, BlockPos p_52771_, Player p_52772_, InteractionHand p_52773_, BlockHitResult p_52774_) {
		if (p_52769_.getValue(LOCKED) && p_52772_.getItemInHand(p_52773_).getItem() != FrostItems.ASTRIUM_KEY.get()) {
			p_52772_.displayClientMessage(Component.translatable("frostrealm.need_key"), true);
			return InteractionResult.PASS;
		} else if (p_52769_.getValue(LOCKED) && p_52772_.getItemInHand(p_52773_).getItem() == FrostItems.ASTRIUM_KEY.get()) {
			p_52769_ = p_52769_.cycle(OPEN);
			p_52769_ = p_52769_.cycle(LOCKED);
			if (!p_52772_.isCreative()) {
				p_52772_.getItemInHand(p_52773_).shrink(1);
			}
			p_52770_.setBlock(p_52771_, p_52769_, 10);
			p_52770_.levelEvent(p_52772_, p_52769_.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), p_52771_, 0);
			p_52770_.gameEvent(p_52772_, this.isOpen(p_52769_) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, p_52771_);
			return InteractionResult.sidedSuccess(p_52770_.isClientSide);
		}
		return super.use(p_52769_, p_52770_, p_52771_, p_52772_, p_52773_, p_52774_);
	}

	public void setOpen(@Nullable Entity p_153166_, Level p_153167_, BlockState p_153168_, BlockPos p_153169_, boolean p_153170_) {
		if (p_153168_.is(this) && p_153168_.getValue(OPEN) != p_153170_ && !p_153168_.getValue(LOCKED)) {
			p_153167_.setBlock(p_153169_, p_153168_.setValue(OPEN, Boolean.valueOf(p_153170_)), 10);
			this.playSound(p_153167_, p_153169_, p_153170_);
			p_153167_.gameEvent(p_153166_, p_153170_ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, p_153169_);
		}
	}

	private void playSound(Level p_52760_, BlockPos p_52761_, boolean p_52762_) {
		p_52760_.levelEvent((Player) null, p_52762_ ? this.getOpenSound() : this.getCloseSound(), p_52761_, 0);
	}

	private int getCloseSound() {
		return this.material == Material.METAL ? 1011 : 1012;
	}

	private int getOpenSound() {
		return this.material == Material.METAL ? 1005 : 1006;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_52803_) {
		p_52803_.add(HALF, FACING, OPEN, HINGE, POWERED, LOCKED);
	}
}
