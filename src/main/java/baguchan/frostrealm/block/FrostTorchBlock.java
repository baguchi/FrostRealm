package baguchan.frostrealm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrostTorchBlock extends TorchBlock {
	protected static final int AABB_STANDING_OFFSET = 2;
	protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

	public FrostTorchBlock(BlockBehaviour.Properties p_57491_) {
		super(p_57491_, ParticleTypes.SOUL_FIRE_FLAME);
	}

	public VoxelShape getShape(BlockState p_57510_, BlockGetter p_57511_, BlockPos p_57512_, CollisionContext p_57513_) {
		return AABB;
	}

	public void animateTick(BlockState p_57494_, Level p_57495_, BlockPos p_57496_, RandomSource p_57497_) {
		double d0 = (double) p_57496_.getX() + 0.5D;
		double d1 = (double) p_57496_.getY() + 0.7D;
		double d2 = (double) p_57496_.getZ() + 0.5D;
		p_57495_.addParticle(ParticleTypes.CLOUD, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}