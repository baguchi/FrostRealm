package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ColdTallGrassBlock extends BushBlock implements BonemealableBlock {
	protected static final float AABB_OFFSET = 6.0F;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

	public ColdTallGrassBlock(BlockBehaviour.Properties p_57318_) {
		super(p_57318_);
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return null;
	}

	public VoxelShape getShape(BlockState p_57336_, BlockGetter p_57337_, BlockPos p_57338_, CollisionContext p_57339_) {
		return SHAPE;
	}

	@Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel p_220874_, RandomSource p_220875_, BlockPos p_220876_, BlockState p_220877_) {
		DoublePlantBlock doubleplantblock = (DoublePlantBlock) FrostBlocks.COLD_TALL_GRASS.get();
		if (doubleplantblock.defaultBlockState().canSurvive(p_220874_, p_220876_) && p_220874_.isEmptyBlock(p_220876_.above())) {
			DoublePlantBlock.placeAt(p_220874_, doubleplantblock.defaultBlockState(), p_220876_, 2);
		}

	}
}
