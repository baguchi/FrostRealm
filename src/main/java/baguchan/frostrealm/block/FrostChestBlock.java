package baguchan.frostrealm.block;

import baguchan.frostrealm.blockentity.FrostChestBlockEntity;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class FrostChestBlock extends AbstractChestBlock<FrostChestBlockEntity> implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final int EVENT_SET_OPEN_COUNT = 1;
	protected static final int AABB_OFFSET = 1;
	protected static final int AABB_HEIGHT = 14;
	protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape EAST_AABB = Block.box(1.0D, 0.0D, 1.0D, 16.0D, 14.0D, 15.0D);
	protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	private static final DoubleBlockCombiner.Combiner<FrostChestBlockEntity, Optional<Container>> CHEST_COMBINER = new DoubleBlockCombiner.Combiner<FrostChestBlockEntity, Optional<Container>>() {
		public Optional<Container> acceptDouble(FrostChestBlockEntity p_51591_, FrostChestBlockEntity p_51592_) {
			return Optional.of(new CompoundContainer(p_51591_, p_51592_));
		}

		public Optional<Container> acceptSingle(FrostChestBlockEntity p_51589_) {
			return Optional.of(p_51589_);
		}

		public Optional<Container> acceptNone() {
			return Optional.empty();
		}
	};
	private static final DoubleBlockCombiner.Combiner<FrostChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<FrostChestBlockEntity, Optional<MenuProvider>>() {
		public Optional<MenuProvider> acceptDouble(final FrostChestBlockEntity p_51604_, final FrostChestBlockEntity p_51605_) {
			final Container container = new CompoundContainer(p_51604_, p_51605_);
			return Optional.of(new MenuProvider() {
				@Nullable
				public AbstractContainerMenu createMenu(int p_51622_, Inventory p_51623_, Player p_51624_) {
					if (p_51604_.canOpen(p_51624_) && p_51605_.canOpen(p_51624_)) {
						p_51604_.unpackLootTable(p_51623_.player);
						p_51605_.unpackLootTable(p_51623_.player);
						return ChestMenu.sixRows(p_51622_, p_51623_, container);
					} else {
						return null;
					}
				}

				public Component getDisplayName() {
					if (p_51604_.hasCustomName()) {
						return p_51604_.getDisplayName();
					} else {
						return p_51605_.hasCustomName() ? p_51605_.getDisplayName() : Component.translatable("container.chestDouble");
					}
				}
			});
		}

		public Optional<MenuProvider> acceptSingle(FrostChestBlockEntity p_51602_) {
			return Optional.of(p_51602_);
		}

		public Optional<MenuProvider> acceptNone() {
			return Optional.empty();
		}
	};

	public FrostChestBlock(BlockBehaviour.Properties p_51490_, Supplier<BlockEntityType<? extends FrostChestBlockEntity>> p_51491_) {
		super(p_51490_, p_51491_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	protected MapCodec<? extends AbstractChestBlock<FrostChestBlockEntity>> codec() {
		return null;
	}

	public static DoubleBlockCombiner.BlockType getBlockType(BlockState p_51583_) {
		ChestType chesttype = p_51583_.getValue(TYPE);
		if (chesttype == ChestType.SINGLE) {
			return DoubleBlockCombiner.BlockType.SINGLE;
		} else {
			return chesttype == ChestType.RIGHT ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
		}
	}

	public RenderShape getRenderShape(BlockState p_51567_) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	public BlockState updateShape(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
		if (p_51555_.getValue(WATERLOGGED)) {
			p_51558_.scheduleTick(p_51559_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51558_));
		}

		if (p_51557_.is(this) && p_51556_.getAxis().isHorizontal()) {
			ChestType chesttype = p_51557_.getValue(TYPE);
			if (p_51555_.getValue(TYPE) == ChestType.SINGLE && chesttype != ChestType.SINGLE && p_51555_.getValue(FACING) == p_51557_.getValue(FACING) && getConnectedDirection(p_51557_) == p_51556_.getOpposite()) {
				return p_51555_.setValue(TYPE, chesttype.getOpposite());
			}
		} else if (getConnectedDirection(p_51555_) == p_51556_) {
			return p_51555_.setValue(TYPE, ChestType.SINGLE);
		}

		return super.updateShape(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
	}

	public VoxelShape getShape(BlockState p_51569_, BlockGetter p_51570_, BlockPos p_51571_, CollisionContext p_51572_) {
		if (p_51569_.getValue(TYPE) == ChestType.SINGLE) {
			return AABB;
		} else {
			switch (getConnectedDirection(p_51569_)) {
				case NORTH:
				default:
					return NORTH_AABB;
				case SOUTH:
					return SOUTH_AABB;
				case WEST:
					return WEST_AABB;
				case EAST:
					return EAST_AABB;
			}
		}
	}

	public static Direction getConnectedDirection(BlockState p_51585_) {
		Direction direction = p_51585_.getValue(FACING);
		return p_51585_.getValue(TYPE) == ChestType.LEFT ? direction.getClockWise() : direction.getCounterClockWise();
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_51493_) {
		ChestType chesttype = ChestType.SINGLE;
		Direction direction = p_51493_.getHorizontalDirection().getOpposite();
		FluidState fluidstate = p_51493_.getLevel().getFluidState(p_51493_.getClickedPos());
		boolean flag = p_51493_.isSecondaryUseActive();
		Direction direction1 = p_51493_.getClickedFace();
		if (direction1.getAxis().isHorizontal() && flag) {
			Direction direction2 = this.candidatePartnerFacing(p_51493_, direction1.getOpposite());
			if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
				direction = direction2;
				chesttype = direction2.getCounterClockWise() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
			}
		}

		if (chesttype == ChestType.SINGLE && !flag) {
			if (direction == this.candidatePartnerFacing(p_51493_, direction.getClockWise())) {
				chesttype = ChestType.LEFT;
			} else if (direction == this.candidatePartnerFacing(p_51493_, direction.getCounterClockWise())) {
				chesttype = ChestType.RIGHT;
			}
		}

		return this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, chesttype).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

	public FluidState getFluidState(BlockState p_51581_) {
		return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
	}

	@Nullable
	private Direction candidatePartnerFacing(BlockPlaceContext p_51495_, Direction p_51496_) {
		BlockState blockstate = p_51495_.getLevel().getBlockState(p_51495_.getClickedPos().relative(p_51496_));
		return blockstate.is(this) && blockstate.getValue(TYPE) == ChestType.SINGLE ? blockstate.getValue(FACING) : null;
	}

	public void setPlacedBy(Level p_51503_, BlockPos p_51504_, BlockState p_51505_, LivingEntity p_51506_, ItemStack p_51507_) {
		/*if (p_51507_.has) {
			BlockEntity blockentity = p_51503_.getBlockEntity(p_51504_);
			if (blockentity instanceof FrostChestBlockEntity) {
				((FrostChestBlockEntity) blockentity).setCustomName(p_51507_.getHoverName());
			}
		}
*/
	}

	public void onRemove(BlockState p_51538_, Level p_51539_, BlockPos p_51540_, BlockState p_51541_, boolean p_51542_) {
		if (!p_51538_.is(p_51541_.getBlock())) {
			BlockEntity blockentity = p_51539_.getBlockEntity(p_51540_);
			if (blockentity instanceof Container) {
				Containers.dropContents(p_51539_, p_51540_, (Container) blockentity);
				p_51539_.updateNeighbourForOutputSignal(p_51540_, this);
			}

			super.onRemove(p_51538_, p_51539_, p_51540_, p_51541_, p_51542_);
		}
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, BlockHitResult p_60508_) {
        if (p_60504_.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
            MenuProvider menuprovider = this.getMenuProvider(p_60503_, p_60504_, p_60505_);
			if (menuprovider != null) {
                p_60506_.openMenu(menuprovider);
                p_60506_.awardStat(this.getOpenChestStat());
                PiglinAi.angerNearbyPiglins(p_60506_, true);
			}

			return InteractionResult.CONSUME;
		}
	}

	protected Stat<ResourceLocation> getOpenChestStat() {
		return Stats.CUSTOM.get(Stats.OPEN_CHEST);
	}

	public BlockEntityType<? extends FrostChestBlockEntity> blockEntityType() {
		return this.blockEntityType.get();
	}

	@Nullable
	public static Container getContainer(FrostChestBlock p_51512_, BlockState p_51513_, Level p_51514_, BlockPos p_51515_, boolean p_51516_) {
		return p_51512_.combine(p_51513_, p_51514_, p_51515_, p_51516_).apply(CHEST_COMBINER).orElse(null);
	}

	public DoubleBlockCombiner.NeighborCombineResult<? extends FrostChestBlockEntity> combine(BlockState p_51544_, Level p_51545_, BlockPos p_51546_, boolean p_51547_) {
		BiPredicate<LevelAccessor, BlockPos> bipredicate;
		if (p_51547_) {
			bipredicate = (p_51578_, p_51579_) -> {
				return false;
			};
		} else {
			bipredicate = FrostChestBlock::isFrostChestBlockedAt;
		}

		return DoubleBlockCombiner.combineWithNeigbour(this.blockEntityType.get(), FrostChestBlock::getBlockType, FrostChestBlock::getConnectedDirection, FACING, p_51544_, p_51545_, p_51546_, bipredicate);
	}

	@Nullable
	public MenuProvider getMenuProvider(BlockState p_51574_, Level p_51575_, BlockPos p_51576_) {
		return this.combine(p_51574_, p_51575_, p_51576_, false).apply(MENU_PROVIDER_COMBINER).orElse(null);
	}

	public static DoubleBlockCombiner.Combiner<FrostChestBlockEntity, Float2FloatFunction> opennessCombiner(final LidBlockEntity p_51518_) {
		return new DoubleBlockCombiner.Combiner<FrostChestBlockEntity, Float2FloatFunction>() {
			public Float2FloatFunction acceptDouble(FrostChestBlockEntity p_51633_, FrostChestBlockEntity p_51634_) {
				return (p_51638_) -> {
					return Math.max(p_51633_.getOpenNess(p_51638_), p_51634_.getOpenNess(p_51638_));
				};
			}

			public Float2FloatFunction acceptSingle(FrostChestBlockEntity p_51631_) {
				return p_51631_::getOpenNess;
			}

			public Float2FloatFunction acceptNone() {
				return p_51518_::getOpenNess;
			}
		};
	}

	public BlockEntity newBlockEntity(BlockPos p_153064_, BlockState p_153065_) {
		return new FrostChestBlockEntity(p_153064_, p_153065_);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153055_, BlockState p_153056_, BlockEntityType<T> p_153057_) {
		return p_153055_.isClientSide ? createTickerHelper(p_153057_, this.blockEntityType(), FrostChestBlockEntity::lidAnimateTick) : null;
	}

	public static boolean isFrostChestBlockedAt(LevelAccessor p_51509_, BlockPos p_51510_) {
		return isBlockedChestByBlock(p_51509_, p_51510_) || isCatSittingOnChest(p_51509_, p_51510_);
	}

	private static boolean isBlockedChestByBlock(BlockGetter p_51500_, BlockPos p_51501_) {
		BlockPos blockpos = p_51501_.above();
		return p_51500_.getBlockState(blockpos).isRedstoneConductor(p_51500_, blockpos);
	}

	private static boolean isCatSittingOnChest(LevelAccessor p_51564_, BlockPos p_51565_) {
		List<Cat> list = p_51564_.getEntitiesOfClass(Cat.class, new AABB(p_51565_.getX(), p_51565_.getY() + 1, p_51565_.getZ(), p_51565_.getX() + 1, p_51565_.getY() + 2, p_51565_.getZ() + 1));
		if (!list.isEmpty()) {
			for (Cat cat : list) {
				if (cat.isInSittingPose()) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasAnalogOutputSignal(BlockState p_51520_) {
		return true;
	}

	public int getAnalogOutputSignal(BlockState p_51527_, Level p_51528_, BlockPos p_51529_) {
		return AbstractContainerMenu.getRedstoneSignalFromContainer(getContainer(this, p_51527_, p_51528_, p_51529_, false));
	}

	public BlockState rotate(BlockState p_51552_, Rotation p_51553_) {
		return p_51552_.setValue(FACING, p_51553_.rotate(p_51552_.getValue(FACING)));
	}

	public BlockState mirror(BlockState p_51549_, Mirror p_51550_) {
		BlockState rotated = p_51549_.rotate(p_51550_.getRotation(p_51549_.getValue(FACING)));
		return p_51550_ == Mirror.NONE ? rotated : rotated.setValue(TYPE, rotated.getValue(TYPE).getOpposite());  // Forge: Fixed MC-134110 Structure mirroring breaking apart double chests
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51562_) {
		p_51562_.add(FACING, TYPE, WATERLOGGED);
	}

	public boolean isPathfindable(BlockState p_51522_, BlockGetter p_51523_, BlockPos p_51524_, PathComputationType p_51525_) {
		return false;
	}

	public void tick(BlockState p_153059_, ServerLevel p_153060_, BlockPos p_153061_, RandomSource p_153062_) {
		BlockEntity blockentity = p_153060_.getBlockEntity(p_153061_);
		if (blockentity instanceof FrostChestBlockEntity) {
			((FrostChestBlockEntity) blockentity).recheckOpen();
		}

	}
}