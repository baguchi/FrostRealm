package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class PointedIceBlock extends Block implements Fallable, SimpleWaterloggedBlock {
	public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
	private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
	private static final VoxelShape BASE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	private static final float MAX_HORIZONTAL_OFFSET = 0.125F;

	public PointedIceBlock(BlockBehaviour.Properties p_154025_) {
		super(p_154025_);
		this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_154157_) {
		p_154157_.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
	}

	public boolean canSurvive(BlockState p_154137_, LevelReader p_154138_, BlockPos p_154139_) {
		return isValidPointedDripstonePlacement(p_154138_, p_154139_, p_154137_.getValue(TIP_DIRECTION));
	}

	public BlockState updateShape(BlockState p_154147_, Direction p_154148_, BlockState p_154149_, LevelAccessor p_154150_, BlockPos p_154151_, BlockPos p_154152_) {
		if (p_154147_.getValue(WATERLOGGED)) {
			p_154150_.scheduleTick(p_154151_, Fluids.WATER, Fluids.WATER.getTickDelay(p_154150_));
		}

		if (p_154148_ != Direction.UP && p_154148_ != Direction.DOWN) {
			return p_154147_;
		} else {
			Direction direction = p_154147_.getValue(TIP_DIRECTION);
			if (direction == Direction.DOWN && p_154150_.getBlockTicks().hasScheduledTick(p_154151_, this)) {
				return p_154147_;
			} else if (p_154148_ == direction.getOpposite() && !this.canSurvive(p_154147_, p_154150_, p_154151_)) {
				if (direction == Direction.DOWN) {
					this.scheduleStalactiteFallTicks(p_154147_, p_154150_, p_154151_);
				} else {
					p_154150_.scheduleTick(p_154151_, this, 1);
				}

				return p_154147_;
			} else {
				boolean flag = p_154147_.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
				DripstoneThickness dripstonethickness = calculateDripstoneThickness(p_154150_, p_154151_, direction, flag);
				return p_154147_.setValue(THICKNESS, dripstonethickness);
			}
		}
	}

	public void onProjectileHit(Level p_154042_, BlockState p_154043_, BlockHitResult p_154044_, Projectile p_154045_) {
		BlockPos blockpos = p_154044_.getBlockPos();
		if (!p_154042_.isClientSide && p_154045_.mayInteract(p_154042_, blockpos) && p_154045_ instanceof ThrownTrident && p_154045_.getDeltaMovement().length() > 0.6D) {
			p_154042_.destroyBlock(blockpos, true);
		}

	}

	public void fallOn(Level p_154047_, BlockState p_154048_, BlockPos p_154049_, Entity p_154050_, float p_154051_) {
		if (p_154048_.getValue(TIP_DIRECTION) == Direction.UP && p_154048_.getValue(THICKNESS) == DripstoneThickness.TIP) {
			p_154050_.causeFallDamage(p_154051_ + 2.0F, 2.0F, p_154047_.damageSources().stalagmite());
            p_154047_.destroyBlock(p_154049_, false);
		} else {
			super.fallOn(p_154047_, p_154048_, p_154049_, p_154050_, p_154051_);
		}

	}

	public void animateTick(BlockState p_154122_, Level p_154123_, BlockPos p_154124_, RandomSource p_154125_) {
		if (canDrip(p_154122_)) {
			float f = p_154125_.nextFloat();
			if (!(f > 0.12F)) {
				getFluidAboveStalactite(p_154123_, p_154124_, p_154122_).filter((p_154031_) -> {
					return f < 0.02F || canFillCauldron(p_154031_);
				}).ifPresent((p_154220_) -> {
					spawnDripParticle(p_154123_, p_154124_, p_154122_, p_154220_);
				});
			}
		}
	}

	public void tick(BlockState p_154107_, ServerLevel p_154108_, BlockPos p_154109_, RandomSource p_154110_) {
		if (isStalagmite(p_154107_) && !this.canSurvive(p_154107_, p_154108_, p_154109_)) {
			p_154108_.destroyBlock(p_154109_, true);
		} else {
			spawnFallingStalactite(p_154107_, p_154108_, p_154109_);
		}
	}

	public void randomTick(BlockState p_154199_, ServerLevel p_154200_, BlockPos p_154201_, RandomSource p_154202_) {
		if (p_154202_.nextFloat() < 0.011377778F && isStalactiteStartPos(p_154199_, p_154200_, p_154201_)) {
			growStalactiteOrStalagmiteIfPossible(p_154199_, p_154200_, p_154201_, p_154202_);
		}

	}

	public PushReaction getPistonPushReaction(BlockState p_154237_) {
		return PushReaction.DESTROY;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext p_154040_) {
		LevelAccessor levelaccessor = p_154040_.getLevel();
		BlockPos blockpos = p_154040_.getClickedPos();
		Direction direction = p_154040_.getNearestLookingVerticalDirection().getOpposite();
		Direction direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
		if (direction1 == null) {
			return null;
		} else {
			boolean flag = !p_154040_.isSecondaryUseActive();
			DripstoneThickness dripstonethickness = calculateDripstoneThickness(levelaccessor, blockpos, direction1, flag);
			return dripstonethickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction1).setValue(THICKNESS, dripstonethickness).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
		}
	}

	public FluidState getFluidState(BlockState p_154235_) {
		return p_154235_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_154235_);
	}

	public VoxelShape getOcclusionShape(BlockState p_154170_, BlockGetter p_154171_, BlockPos p_154172_) {
		return Shapes.empty();
	}

	public VoxelShape getShape(BlockState p_154117_, BlockGetter p_154118_, BlockPos p_154119_, CollisionContext p_154120_) {
		DripstoneThickness dripstonethickness = p_154117_.getValue(THICKNESS);
		VoxelShape voxelshape;
		if (dripstonethickness == DripstoneThickness.TIP_MERGE) {
			voxelshape = TIP_MERGE_SHAPE;
		} else if (dripstonethickness == DripstoneThickness.TIP) {
			if (p_154117_.getValue(TIP_DIRECTION) == Direction.DOWN) {
				voxelshape = TIP_SHAPE_DOWN;
			} else {
				voxelshape = TIP_SHAPE_UP;
			}
		} else if (dripstonethickness == DripstoneThickness.FRUSTUM) {
			voxelshape = FRUSTUM_SHAPE;
		} else if (dripstonethickness == DripstoneThickness.MIDDLE) {
			voxelshape = MIDDLE_SHAPE;
		} else {
			voxelshape = BASE_SHAPE;
		}

		Vec3 vec3 = p_154117_.getOffset(p_154118_, p_154119_);
		return voxelshape.move(vec3.x, 0.0D, vec3.z);
	}

	public boolean isCollisionShapeFullBlock(BlockState p_181235_, BlockGetter p_181236_, BlockPos p_181237_) {
		return false;
    }

    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }

    public float getMaxHorizontalOffset() {
        return 0.125F;
    }

    @Override
    public DamageSource getFallDamageSource(Entity p_253907_) {
        return p_253907_.damageSources().stalagmite();
    }

    public Predicate<Entity> getHurtsEntitySelector() {
        return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
    }

    private void scheduleStalactiteFallTicks(BlockState p_154127_, LevelAccessor p_154128_, BlockPos p_154129_) {
        BlockPos blockpos = findTip(p_154127_, p_154128_, p_154129_, Integer.MAX_VALUE, true);
        if (blockpos != null) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();

			while (isStalactite(p_154128_.getBlockState(blockpos$mutableblockpos))) {
				p_154128_.scheduleTick(blockpos$mutableblockpos, this, 2);
				blockpos$mutableblockpos.move(Direction.UP);
			}

		}
	}

	private static int getStalactiteSizeFromTip(ServerLevel p_154175_, BlockPos p_154176_, int p_154177_) {
		int i = 1;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154176_.mutable().move(Direction.UP);

		while (i < p_154177_ && isStalactite(p_154175_.getBlockState(blockpos$mutableblockpos))) {
			++i;
			blockpos$mutableblockpos.move(Direction.UP);
		}

		return i;
	}

	private static void spawnFallingStalactite(BlockState p_154098_, ServerLevel p_154099_, BlockPos p_154100_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154100_.mutable();
		BlockState blockstate = p_154098_;

		while (isStalactite(blockstate)) {
			FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(p_154099_, blockpos$mutableblockpos, blockstate);
			if (isTip(blockstate, true)) {
				int i = Math.max(1 + p_154100_.getY() - blockpos$mutableblockpos.getY(), 6);
				float f = 1.0F * (float) i;
				fallingblockentity.setHurtsEntities(f, 40);
				break;
			}

			blockpos$mutableblockpos.move(Direction.DOWN);
			blockstate = p_154099_.getBlockState(blockpos$mutableblockpos);
		}
	}
	@VisibleForTesting
	public static void growStalactiteOrStalagmiteIfPossible(BlockState p_154226_, ServerLevel p_154227_, BlockPos p_154228_, RandomSource p_154229_) {
		BlockState blockstate = p_154227_.getBlockState(p_154228_.above(1));
		BlockState blockstate1 = p_154227_.getBlockState(p_154228_.above(2));
		if (canGrow(blockstate, blockstate1)) {
			BlockPos blockpos = findTip(p_154226_, p_154227_, p_154228_, 7, false);
			if (blockpos != null) {
				BlockState blockstate2 = p_154227_.getBlockState(blockpos);
				if (canDrip(blockstate2) && canTipGrow(blockstate2, p_154227_, blockpos)) {
					if (p_154229_.nextBoolean()) {
						grow(p_154227_, blockpos, Direction.DOWN);
					} else {
						growStalagmiteBelow(p_154227_, blockpos);
					}

				}
			}
		}
	}

	private static void growStalagmiteBelow(ServerLevel p_154033_, BlockPos p_154034_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154034_.mutable();

		for (int i = 0; i < 10; ++i) {
			blockpos$mutableblockpos.move(Direction.DOWN);
			BlockState blockstate = p_154033_.getBlockState(blockpos$mutableblockpos);
			if (!blockstate.getFluidState().isEmpty()) {
				return;
			}

			if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, p_154033_, blockpos$mutableblockpos)) {
				grow(p_154033_, blockpos$mutableblockpos, Direction.UP);
				return;
			}

			if (isValidPointedDripstonePlacement(p_154033_, blockpos$mutableblockpos, Direction.UP) && !p_154033_.isWaterAt(blockpos$mutableblockpos.below())) {
				grow(p_154033_, blockpos$mutableblockpos.below(), Direction.UP);
				return;
			}
		}

	}

	private static void grow(ServerLevel p_154036_, BlockPos p_154037_, Direction p_154038_) {
		BlockPos blockpos = p_154037_.relative(p_154038_);
		BlockState blockstate = p_154036_.getBlockState(blockpos);
		if (isUnmergedTipWithDirection(blockstate, p_154038_.getOpposite())) {
			createMergedTips(blockstate, p_154036_, blockpos);
		} else if (blockstate.isAir() || blockstate.is(Blocks.WATER)) {
			createDripstone(p_154036_, blockpos, p_154038_, DripstoneThickness.TIP);
		}

	}

	private static void createDripstone(LevelAccessor p_154088_, BlockPos p_154089_, Direction p_154090_, DripstoneThickness p_154091_) {
		BlockState blockstate = FrostBlocks.POINTED_ICE.get().defaultBlockState().setValue(TIP_DIRECTION, p_154090_).setValue(THICKNESS, p_154091_).setValue(WATERLOGGED, Boolean.valueOf(p_154088_.getFluidState(p_154089_).getType() == Fluids.WATER));
		p_154088_.setBlock(p_154089_, blockstate, 3);
	}

	private static void createMergedTips(BlockState p_154231_, LevelAccessor p_154232_, BlockPos p_154233_) {
		BlockPos blockpos;
		BlockPos blockpos1;
		if (p_154231_.getValue(TIP_DIRECTION) == Direction.UP) {
			blockpos1 = p_154233_;
			blockpos = p_154233_.above();
		} else {
			blockpos = p_154233_;
			blockpos1 = p_154233_.below();
		}

		createDripstone(p_154232_, blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
		createDripstone(p_154232_, blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
	}

	private static void spawnDripParticle(Level p_154072_, BlockPos p_154073_, BlockState p_154074_, Fluid p_154075_) {
		Vec3 vec3 = p_154074_.getOffset(p_154072_, p_154073_);
		double d0 = 0.0625D;
		double d1 = (double) p_154073_.getX() + 0.5D + vec3.x;
		double d2 = (double) ((float) (p_154073_.getY() + 1) - 0.6875F) - 0.0625D;
		double d3 = (double) p_154073_.getZ() + 0.5D + vec3.z;
		Fluid fluid = getDripFluid(p_154072_, p_154075_);
		ParticleOptions particleoptions = ParticleTypes.DRIPPING_DRIPSTONE_WATER;
		p_154072_.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
	}

	@Nullable
	private static BlockPos findTip(BlockState p_154131_, LevelAccessor p_154132_, BlockPos p_154133_, int p_154134_, boolean p_154135_) {
		if (isTip(p_154131_, p_154135_)) {
			return p_154133_;
		} else {
			Direction direction = p_154131_.getValue(TIP_DIRECTION);
			Predicate<BlockState> predicate = (p_154212_) -> {
				return p_154212_.is(FrostBlocks.POINTED_ICE.get()) && p_154212_.getValue(TIP_DIRECTION) == direction;
			};
			return findBlockVertical(p_154132_, p_154133_, direction.getAxisDirection(), predicate, (p_154168_) -> {
				return isTip(p_154168_, p_154135_);
			}, p_154134_).orElse(null);
		}
	}

	@Nullable
	private static Direction calculateTipDirection(LevelReader p_154191_, BlockPos p_154192_, Direction p_154193_) {
		Direction direction;
		if (isValidPointedDripstonePlacement(p_154191_, p_154192_, p_154193_)) {
			direction = p_154193_;
		} else {
			if (!isValidPointedDripstonePlacement(p_154191_, p_154192_, p_154193_.getOpposite())) {
				return null;
			}

			direction = p_154193_.getOpposite();
		}

		return direction;
	}

	private static DripstoneThickness calculateDripstoneThickness(LevelReader p_154093_, BlockPos p_154094_, Direction p_154095_, boolean p_154096_) {
		Direction direction = p_154095_.getOpposite();
		BlockState blockstate = p_154093_.getBlockState(p_154094_.relative(p_154095_));
		if (isPointedDripstoneWithDirection(blockstate, direction)) {
			return !p_154096_ && blockstate.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
		} else if (!isPointedDripstoneWithDirection(blockstate, p_154095_)) {
			return DripstoneThickness.TIP;
		} else {
			DripstoneThickness dripstonethickness = blockstate.getValue(THICKNESS);
			if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE) {
				BlockState blockstate1 = p_154093_.getBlockState(p_154094_.relative(direction));
				return !isPointedDripstoneWithDirection(blockstate1, p_154095_) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
			} else {
				return DripstoneThickness.FRUSTUM;
			}
		}
	}

	public static boolean canDrip(BlockState p_154239_) {
		return isStalactite(p_154239_) && p_154239_.getValue(THICKNESS) == DripstoneThickness.TIP && !p_154239_.getValue(WATERLOGGED);
	}

	private static boolean canTipGrow(BlockState p_154195_, ServerLevel p_154196_, BlockPos p_154197_) {
		Direction direction = p_154195_.getValue(TIP_DIRECTION);
		BlockPos blockpos = p_154197_.relative(direction);
		BlockState blockstate = p_154196_.getBlockState(blockpos);
		if (!blockstate.getFluidState().isEmpty()) {
			return false;
		} else {
			return blockstate.isAir() || isUnmergedTipWithDirection(blockstate, direction.getOpposite());
		}
	}

	private static Optional<BlockPos> findRootBlock(Level p_154067_, BlockPos p_154068_, BlockState p_154069_, int p_154070_) {
		Direction direction = p_154069_.getValue(TIP_DIRECTION);
		Predicate<BlockState> predicate = (p_154165_) -> {
			return p_154165_.is(FrostBlocks.POINTED_ICE.get()) && p_154165_.getValue(TIP_DIRECTION) == direction;
		};
		return findBlockVertical(p_154067_, p_154068_, direction.getOpposite().getAxisDirection(), predicate, (p_154245_) -> {
			return !p_154245_.is(FrostBlocks.POINTED_ICE.get());
		}, p_154070_);
	}

	private static boolean isValidPointedDripstonePlacement(LevelReader p_154222_, BlockPos p_154223_, Direction p_154224_) {
		BlockPos blockpos = p_154223_.relative(p_154224_.getOpposite());
		BlockState blockstate = p_154222_.getBlockState(blockpos);
		return blockstate.isFaceSturdy(p_154222_, blockpos, p_154224_) || isPointedDripstoneWithDirection(blockstate, p_154224_);
	}

	private static boolean isTip(BlockState p_154154_, boolean p_154155_) {
		if (!p_154154_.is(FrostBlocks.POINTED_ICE.get())) {
			return false;
		} else {
			DripstoneThickness dripstonethickness = p_154154_.getValue(THICKNESS);
			return dripstonethickness == DripstoneThickness.TIP || p_154155_ && dripstonethickness == DripstoneThickness.TIP_MERGE;
		}
	}

	private static boolean isUnmergedTipWithDirection(BlockState p_154144_, Direction p_154145_) {
		return isTip(p_154144_, false) && p_154144_.getValue(TIP_DIRECTION) == p_154145_;
	}

	private static boolean isStalactite(BlockState p_154241_) {
		return isPointedDripstoneWithDirection(p_154241_, Direction.DOWN);
	}

	private static boolean isStalagmite(BlockState p_154243_) {
		return isPointedDripstoneWithDirection(p_154243_, Direction.UP);
	}

	private static boolean isStalactiteStartPos(BlockState p_154204_, LevelReader p_154205_, BlockPos p_154206_) {
		return isStalactite(p_154204_) && !p_154205_.getBlockState(p_154206_.above()).is(FrostBlocks.POINTED_ICE.get());
	}

	public boolean isPathfindable(BlockState p_154112_, BlockGetter p_154113_, BlockPos p_154114_, PathComputationType p_154115_) {
		return false;
	}

	private static boolean isPointedDripstoneWithDirection(BlockState p_154208_, Direction p_154209_) {
		return p_154208_.is(FrostBlocks.POINTED_ICE.get()) && p_154208_.getValue(TIP_DIRECTION) == p_154209_;
	}

	private static Optional<Fluid> getFluidAboveStalactite(Level p_154182_, BlockPos p_154183_, BlockState p_154184_) {
		return !isStalactite(p_154184_) ? Optional.empty() : findRootBlock(p_154182_, p_154183_, p_154184_, 11).map((p_154215_) -> {
			return p_154182_.getFluidState(p_154215_.above()).getType();
		});
	}

	private static boolean canFillCauldron(Fluid p_154159_) {
		return p_154159_ == Fluids.WATER;
	}

	private static boolean canGrow(BlockState p_154141_, BlockState p_154142_) {
		return p_154141_.is(Blocks.DRIPSTONE_BLOCK) && p_154142_.is(Blocks.WATER) && p_154142_.getFluidState().isSource();
	}

	private static Fluid getDripFluid(Level p_154053_, Fluid p_154054_) {
		if (p_154054_.isSame(Fluids.EMPTY)) {
			return Fluids.WATER;
		} else {
			return p_154054_;
		}
	}

	private static Optional<BlockPos> findBlockVertical(LevelAccessor p_154081_, BlockPos p_154082_, Direction.AxisDirection p_154083_, Predicate<BlockState> p_154084_, Predicate<BlockState> p_154085_, int p_154086_) {
		Direction direction = Direction.get(p_154083_, Direction.Axis.Y);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154082_.mutable();

		for (int i = 1; i < p_154086_; ++i) {
			blockpos$mutableblockpos.move(direction);
			BlockState blockstate = p_154081_.getBlockState(blockpos$mutableblockpos);
			if (p_154085_.test(blockstate)) {
				return Optional.of(blockpos$mutableblockpos.immutable());
			}

			if (p_154081_.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !p_154084_.test(blockstate)) {
				return Optional.empty();
			}
		}

		return Optional.empty();
	}

	@Override
	public void onBrokenAfterFall(Level p_154059_, BlockPos p_154060_, FallingBlockEntity p_154061_) {
		if (!p_154061_.isSilent()) {

			p_154059_.levelEvent(2001, p_154060_, Block.getId(FrostBlocks.POINTED_ICE.get().defaultBlockState()));
		}
	}
}
