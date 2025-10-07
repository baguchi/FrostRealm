package baguchan.frostrealm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrostCampfireBlock extends CampfireBlock implements SimpleWaterloggedBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
	private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public FrostCampfireBlock(Properties p_49795_) {
		super(false, 2, p_49795_);
	}


	@Override
	protected void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity p_60498_, InsideBlockEffectApplier p_405359_) {
		if (p_60495_.getValue(LIT) && p_60498_ instanceof LivingEntity) {
			p_60498_.hurt(p_60498_.damageSources().freeze(), 2.0F);
		}
	}

	@Override
	public VoxelShape getShape(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
		return SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_51307_) {
		return RenderShape.MODEL;
	}

	@Override
	public void animateTick(BlockState p_220918_, Level p_220919_, BlockPos p_220920_, RandomSource p_220921_) {
		if (p_220918_.getValue(LIT)) {
			if (p_220921_.nextInt(10) == 0) {
				p_220919_.playLocalSound((double) p_220920_.getX() + 0.5D, (double) p_220920_.getY() + 0.5D, (double) p_220920_.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + p_220921_.nextFloat(), p_220921_.nextFloat() * 0.7F + 0.6F, false);
			}
		}
	}


	@Override
	public void onProjectileHit(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
		BlockPos blockpos = p_51246_.getBlockPos();
		if (p_51244_ instanceof ServerLevel serverLevel) {
			if (!p_51244_.isClientSide() && p_51247_.isOnFire() && p_51247_.mayInteract(serverLevel, blockpos) && !p_51245_.getValue(LIT) && !p_51245_.getValue(WATERLOGGED)) {
				p_51244_.setBlock(blockpos, p_51245_.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
			}
		}
	}


	@Override
	public @org.jetbrains.annotations.Nullable PathType getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @org.jetbrains.annotations.Nullable Mob mob, PathType originalType) {
		return PathType.DANGER_OTHER;
	}

	@Override
	public @org.jetbrains.annotations.Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @org.jetbrains.annotations.Nullable Mob mob) {
		return PathType.DAMAGE_OTHER;
	}

	@Override
	protected boolean isPathfindable(BlockState p_60475_, PathComputationType p_60478_) {
		return false;
	}
}
