package baguchan.frostrealm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class FrostCampfireBlock extends Block implements SimpleWaterloggedBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public FrostCampfireBlock(Properties p_49795_) {
		super(p_49795_);
	}

	public void entityInside(BlockState p_51269_, Level p_51270_, BlockPos p_51271_, Entity p_51272_) {
		if (p_51269_.getValue(LIT) && p_51272_ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) p_51272_)) {
			p_51272_.hurt(DamageSource.FREEZE, 2.0F);
		}

		super.entityInside(p_51269_, p_51270_, p_51271_, p_51272_);
	}

	public void onRemove(BlockState p_51281_, Level p_51282_, BlockPos p_51283_, BlockState p_51284_, boolean p_51285_) {
		if (!p_51281_.is(p_51284_.getBlock())) {
			BlockEntity blockentity = p_51282_.getBlockEntity(p_51283_);
			if (blockentity instanceof CampfireBlockEntity) {
				Containers.dropContents(p_51282_, p_51283_, ((CampfireBlockEntity) blockentity).getItems());
			}

			super.onRemove(p_51281_, p_51282_, p_51283_, p_51284_, p_51285_);
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext p_51240_) {
		LevelAccessor levelaccessor = p_51240_.getLevel();
		BlockPos blockpos = p_51240_.getClickedPos();
		boolean flag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(flag)).setValue(LIT, Boolean.valueOf(!flag)).setValue(FACING, p_51240_.getHorizontalDirection());
	}

	public BlockState updateShape(BlockState p_51298_, Direction p_51299_, BlockState p_51300_, LevelAccessor p_51301_, BlockPos p_51302_, BlockPos p_51303_) {
		if (p_51298_.getValue(WATERLOGGED)) {
			p_51301_.scheduleTick(p_51302_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51301_));
		}

		return p_51299_ == Direction.DOWN ? p_51298_ : super.updateShape(p_51298_, p_51299_, p_51300_, p_51301_, p_51302_, p_51303_);
	}

	public VoxelShape getShape(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
		return SHAPE;
	}

	public RenderShape getRenderShape(BlockState p_51307_) {
		return RenderShape.MODEL;
	}

	public void animateTick(BlockState p_220918_, Level p_220919_, BlockPos p_220920_, RandomSource p_220921_) {
		if (p_220918_.getValue(LIT)) {
			if (p_220921_.nextInt(10) == 0) {
				p_220919_.playLocalSound((double) p_220920_.getX() + 0.5D, (double) p_220920_.getY() + 0.5D, (double) p_220920_.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + p_220921_.nextFloat(), p_220921_.nextFloat() * 0.7F + 0.6F, false);
			}
		}
	}

	public static void dowse(@Nullable Entity p_152750_, LevelAccessor p_152751_, BlockPos p_152752_, BlockState p_152753_) {
		if (p_152751_.isClientSide()) {
			for (int i = 0; i < 20; ++i) {
				makeParticles((Level) p_152751_, p_152752_);
			}
		}

		p_152751_.gameEvent(p_152750_, GameEvent.BLOCK_CHANGE, p_152752_);
	}

	public boolean placeLiquid(LevelAccessor p_51257_, BlockPos p_51258_, BlockState p_51259_, FluidState p_51260_) {
		if (!p_51259_.getValue(BlockStateProperties.WATERLOGGED) && p_51260_.getType() == Fluids.WATER) {
			boolean flag = p_51259_.getValue(LIT);
			if (flag) {
				if (!p_51257_.isClientSide()) {
					p_51257_.playSound((Player) null, p_51258_, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
				}

				dowse((Entity) null, p_51257_, p_51258_, p_51259_);
			}

			p_51257_.setBlock(p_51258_, p_51259_.setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(LIT, Boolean.valueOf(false)), 3);
			p_51257_.scheduleTick(p_51258_, p_51260_.getType(), p_51260_.getType().getTickDelay(p_51257_));
			return true;
		} else {
			return false;
		}
	}

	public void onProjectileHit(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
		BlockPos blockpos = p_51246_.getBlockPos();
		if (!p_51244_.isClientSide && p_51247_.isOnFire() && p_51247_.mayInteract(p_51244_, blockpos) && !p_51245_.getValue(LIT) && !p_51245_.getValue(WATERLOGGED)) {
			p_51244_.setBlock(blockpos, p_51245_.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
		}

	}

	public static void makeParticles(Level p_51252_, BlockPos p_51253_) {
		RandomSource randomsource = p_51252_.getRandom();
		SimpleParticleType simpleparticletype = ParticleTypes.CLOUD;
		p_51252_.addAlwaysVisibleParticle(simpleparticletype, true, (double) p_51253_.getX() + 0.5D + randomsource.nextDouble() / 3.0D * (double) (randomsource.nextBoolean() ? 1 : -1), (double) p_51253_.getY() + randomsource.nextDouble() + randomsource.nextDouble(), (double) p_51253_.getZ() + 0.5D + randomsource.nextDouble() / 3.0D * (double) (randomsource.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
	}

	public static boolean isSmokeyPos(Level p_51249_, BlockPos p_51250_) {
		for (int i = 1; i <= 5; ++i) {
			BlockPos blockpos = p_51250_.below(i);
			BlockState blockstate = p_51249_.getBlockState(blockpos);
			if (isLitCampfire(blockstate)) {
				return true;
			}

			boolean flag = Shapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, blockstate.getCollisionShape(p_51249_, blockpos, CollisionContext.empty()), BooleanOp.AND); // FORGE: Fix MC-201374
			if (flag) {
				BlockState blockstate1 = p_51249_.getBlockState(blockpos.below());
				return isLitCampfire(blockstate1);
			}
		}

		return false;
	}

	public static boolean isLitCampfire(BlockState p_51320_) {
		return p_51320_.hasProperty(LIT) && p_51320_.is(BlockTags.CAMPFIRES) && p_51320_.getValue(LIT);
	}

	public FluidState getFluidState(BlockState p_51318_) {
		return p_51318_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51318_);
	}

	public BlockState rotate(BlockState p_51295_, Rotation p_51296_) {
		return p_51295_.setValue(FACING, p_51296_.rotate(p_51295_.getValue(FACING)));
	}

	public BlockState mirror(BlockState p_51292_, Mirror p_51293_) {
		return p_51292_.rotate(p_51293_.getRotation(p_51292_.getValue(FACING)));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51305_) {
		p_51305_.add(LIT, WATERLOGGED, FACING);
	}

	public boolean isPathfindable(BlockState p_51264_, BlockGetter p_51265_, BlockPos p_51266_, PathComputationType p_51267_) {
		return false;
	}
}
