package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class FrigidStoveBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

	public FrigidStoveBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(false)).setValue(AGE, 0));
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
		return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
		return p_48722_.setValue(FACING, p_48723_.rotate(p_48722_.getValue(FACING)));
	}

	public BlockState mirror(BlockState p_48719_, Mirror p_48720_) {
		return p_48719_.rotate(p_48720_.getRotation(p_48719_.getValue(FACING)));
	}

	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_51279_) {
		ItemStack stack = player.getItemInHand(hand);
		boolean lit = state.getValue(LIT);

		if (!lit) {
			if (stack.getItem() == Items.FIRE_CHARGE || stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL || stack.getItem() == FrostItems.FROST_CRYSTAL.get()) {
				level.setBlock(pos, state.setValue(LIT, Boolean.valueOf(true)).setValue(AGE, 0), 3);
				stack.shrink(1);
				return InteractionResult.SUCCESS;
			}

			if (stack.getItem() == Items.FLINT_AND_STEEL) {
				level.setBlock(pos, state.setValue(LIT, Boolean.valueOf(true)).setValue(AGE, 0), 3);
				stack.hurtAndBreak(1, player, (p_147232_) -> {
					p_147232_.broadcastBreakEvent(hand);
				});
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.randomTick(state, level, pos, random);
		int i = state.getValue(AGE);
		boolean lit = state.getValue(LIT);

		if (lit) {
			setHotAir(state, level, pos, random);

			if (level.random.nextInt(6) == 0) {
				if (i < 7) {
					level.setBlock(pos, state.setValue(LIT, Boolean.valueOf(true)).setValue(AGE, i + 1), 3);
				} else {
					level.setBlock(pos, state.setValue(LIT, Boolean.valueOf(false)).setValue(AGE, 0), 3);
				}
			}
		}
	}

	public void setHotAir(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		Direction direction = state.getValue(FACING);
		BlockState state2 = level.getBlockState(pos.relative(direction));

		if (state2.getBlock() == Blocks.AIR) {
			level.setBlock(pos.relative(direction), FrostBlocks.HOT_AIR.get().defaultBlockState(), 2);
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51305_) {
		p_51305_.add(FACING, LIT, AGE);
	}
}
