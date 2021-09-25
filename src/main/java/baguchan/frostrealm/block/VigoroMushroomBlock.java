package baguchan.frostrealm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class VigoroMushroomBlock extends BushBlock implements BonemealableBlock {
	protected static final float AABB_OFFSET = 3.0F;
	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

	public VigoroMushroomBlock(BlockBehaviour.Properties p_153983_) {
		super(p_153983_);
	}

	public VoxelShape getShape(BlockState p_54889_, BlockGetter p_54890_, BlockPos p_54891_, CollisionContext p_54892_) {
		return SHAPE;
	}

	public void randomTick(BlockState p_54868_, ServerLevel p_54865_, BlockPos p_54867_, Random p_54866_) {
		if (p_54866_.nextInt(25) == 0) {
			int i = 5;
			int j = 4;

			for (BlockPos blockpos : BlockPos.betweenClosed(p_54867_.offset(-4, -1, -4), p_54867_.offset(4, 1, 4))) {
				if (p_54865_.getBlockState(blockpos).is(this)) {
					--i;
					if (i <= 0) {
						return;
					}
				}
			}

			BlockPos blockpos1 = p_54867_.offset(p_54866_.nextInt(3) - 1, p_54866_.nextInt(2) - p_54866_.nextInt(2), p_54866_.nextInt(3) - 1);

			for (int k = 0; k < 4; ++k) {
				if (p_54865_.isEmptyBlock(blockpos1) && p_54868_.canSurvive(p_54865_, blockpos1)) {
					p_54867_ = blockpos1;
				}

				blockpos1 = p_54867_.offset(p_54866_.nextInt(3) - 1, p_54866_.nextInt(2) - p_54866_.nextInt(2), p_54866_.nextInt(3) - 1);
			}

			if (p_54865_.isEmptyBlock(blockpos1) && p_54868_.canSurvive(p_54865_, blockpos1)) {
				p_54865_.setBlock(blockpos1, p_54868_, 2);
			}
		}

	}

	public boolean isValidBonemealTarget(BlockGetter p_54870_, BlockPos p_54871_, BlockState p_54872_, boolean p_54873_) {
		return true;
	}

	public boolean isBonemealSuccess(Level p_54875_, Random p_54876_, BlockPos p_54877_, BlockState p_54878_) {
		return (double) p_54876_.nextFloat() < 0.4D;
	}

	public void performBonemeal(ServerLevel p_54865_, Random p_54866_, BlockPos p_54867_, BlockState p_54868_) {
		int i = 3;

		for (BlockPos blockpos : BlockPos.betweenClosed(p_54867_.offset(-4, -1, -4), p_54867_.offset(4, 1, 4))) {
			if (p_54865_.getBlockState(blockpos).is(this)) {
				--i;
				if (i <= 0) {
					return;
				}
			}
		}

		BlockPos blockpos1 = p_54867_.offset(p_54866_.nextInt(3) - 1, p_54866_.nextInt(2) - p_54866_.nextInt(2), p_54866_.nextInt(3) - 1);

		for (int k = 0; k < 4; ++k) {
			if (p_54865_.isEmptyBlock(blockpos1) && p_54868_.canSurvive(p_54865_, blockpos1)) {
				p_54867_ = blockpos1;
			}

			blockpos1 = p_54867_.offset(p_54866_.nextInt(3) - 1, p_54866_.nextInt(2) - p_54866_.nextInt(2), p_54866_.nextInt(3) - 1);
		}

		if (p_54865_.isEmptyBlock(blockpos1) && p_54868_.canSurvive(p_54865_, blockpos1)) {
			p_54865_.setBlock(blockpos1, p_54868_, 2);
		}
	}
}
