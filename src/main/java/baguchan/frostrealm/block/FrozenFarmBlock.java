package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class FrozenFarmBlock extends FarmBlock {
	public FrozenFarmBlock(Properties p_53247_) {
		super(p_53247_);
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_53249_) {
		return !this.defaultBlockState().canSurvive(p_53249_.getLevel(), p_53249_.getClickedPos()) ? FrostBlocks.FROZEN_DIRT.defaultBlockState() : super.getStateForPlacement(p_53249_);
	}

	public void randomTick(BlockState p_53285_, ServerLevel p_53286_, BlockPos p_53287_, Random p_53288_) {
		int i = p_53285_.getValue(MOISTURE);
		if (!isNearWater(p_53286_, p_53287_) && !p_53286_.isRainingAt(p_53287_.above())) {
			if (i > 0) {
				p_53286_.setBlock(p_53287_, p_53285_.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
			} else if (!isUnderCrops(p_53286_, p_53287_)) {
				turnToDirt(p_53285_, p_53286_, p_53287_);
			}
		} else if (i < 7) {
			p_53286_.setBlock(p_53287_, p_53285_.setValue(MOISTURE, Integer.valueOf(7)), 2);
		}

	}

	private static boolean isUnderCrops(BlockGetter p_53251_, BlockPos p_53252_) {
		BlockState plant = p_53251_.getBlockState(p_53252_.above());
		BlockState state = p_53251_.getBlockState(p_53252_);
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(p_53251_, p_53252_, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
	}

	private static boolean isNearWater(LevelReader p_53259_, BlockPos p_53260_) {
		for (BlockPos blockpos : BlockPos.betweenClosed(p_53260_.offset(-4, 0, -4), p_53260_.offset(4, 1, 4))) {
			if (p_53259_.getFluidState(blockpos).is(FluidTags.WATER)) {
				return true;
			}
		}

		return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_53259_, p_53260_);
	}

	public void fallOn(Level p_153227_, BlockState p_153228_, BlockPos p_153229_, Entity p_153230_, float p_153231_) {
		if (!p_153227_.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(p_153227_, p_153229_, FrostBlocks.FROZEN_DIRT.defaultBlockState(), p_153231_, p_153230_)) { // Forge: Move logic to Entity#canTrample
			turnToDirt(p_153228_, p_153227_, p_153229_);
		}

		super.fallOn(p_153227_, p_153228_, p_153229_, p_153230_, p_153231_);
	}

	public static void turnToDirt(BlockState p_53297_, Level p_53298_, BlockPos p_53299_) {
		p_53298_.setBlockAndUpdate(p_53299_, pushEntitiesUp(p_53297_, FrostBlocks.FROZEN_DIRT.defaultBlockState(), p_53298_, p_53299_));
	}
}
