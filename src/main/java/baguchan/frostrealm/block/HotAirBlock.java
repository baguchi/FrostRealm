package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

public class HotAirBlock extends AirBlock {
	public HotAirBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		super.tick(state, level, pos, random);
		if (hasNoBlocksAbove(level, pos) || !level.getFluidState(pos).isEmpty()) {
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
		}
	}

	public static boolean hasNoBlocksAbove(ServerLevel level, BlockPos pos) {
		return level.canSeeSky(pos) && (double) level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() <= pos.getY();
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		super.randomTick(state, level, pos, random);
		this.setAirMove(level, pos, random);
	}

	public void setAirMove(ServerLevel level, BlockPos pos, Random random) {
		BlockState state2 = level.getBlockState(pos.above());

		if (state2.getBlock() == Blocks.AIR) {
			level.setBlock(pos.above(), FrostBlocks.HOT_AIR.get().defaultBlockState(), 2);
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
		} else if (state2.getBlock() == FrostBlocks.HOT_AIR.get()) {
			Direction direction = Direction.getRandom(random);
			if (direction != Direction.UP && direction != Direction.DOWN) {
				BlockPos blockpos = pos.above().relative(direction);
				BlockState state3 = level.getBlockState(blockpos);
				if (state3.getBlock() == Blocks.AIR) {
					level.setBlock(blockpos, FrostBlocks.HOT_AIR.get().defaultBlockState(), 2);
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
				}
			}
		}
	}
}
