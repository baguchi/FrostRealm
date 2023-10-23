package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.block.*;
import baguchan.frostrealm.blockentity.FrostChestBlockEntity;
import baguchan.frostrealm.world.tree.FrostbiteTree;
import baguchan.frostrealm.world.tree.FrostrootTree;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FrostBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrostRealm.MODID);


	public static final RegistryObject<FrostPortalBlock> FROST_PORTAL = noItemRegister("frostrealm_portal", () -> new FrostPortalBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().randomTicks().lightLevel((state) -> {
		return 11;
	}).strength(-1.0F).sound(SoundType.GLASS)));

	public static final RegistryObject<Block> FROZEN_DIRT = register("frozen_dirt", () -> new Block(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block> FROZEN_GRASS_BLOCK = register("frozen_grass_block", () -> new FrostGrassBlock(BlockBehaviour.Properties.of().randomTicks().strength(0.6F).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> FROZEN_FARMLAND = register("frozen_farmland", () -> new FrozenFarmBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.5F).randomTicks().sound(SoundType.GRAVEL)));

	public static final RegistryObject<Block> POINTED_ICE = register("pointed_ice", () -> new PointedIceBlock(BlockBehaviour.Properties.of().friction(0.98F).randomTicks().strength(0.5F).dynamicShape().sound(SoundType.GLASS)));


	public static final RegistryObject<Block> FRIGID_STONE = register("frigid_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final RegistryObject<SlabBlock> FRIGID_STONE_SLAB = register("frigid_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final RegistryObject<StairBlock> FRIGID_STONE_STAIRS = register("frigid_stone_stairs", () -> new StairBlock(FRIGID_STONE.get()::defaultBlockState, BlockBehaviour.Properties.of().noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final RegistryObject<Block> FRIGID_STONE_BRICK = register("frigid_stone_brick", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<SlabBlock> FRIGID_STONE_BRICK_SLAB = register("frigid_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<StairBlock> FRIGID_STONE_BRICK_STAIRS = register("frigid_stone_brick_stairs", () -> new StairBlock(FRIGID_STONE_BRICK.get()::defaultBlockState, BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> FRIGID_STONE_SMOOTH = register("frigid_stone_smooth", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> CHISELED_FRIGID_STONE_BRICK = register("chiseled_frigid_stone_brick", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> FRIGID_STONE_MOSSY = register("frigid_stone_mossy", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final RegistryObject<SlabBlock> FRIGID_STONE_MOSSY_SLAB = register("frigid_stone_mossy_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final RegistryObject<StairBlock> FRIGID_STONE_MOSSY_STAIRS = register("frigid_stone_mossy_stairs", () -> new StairBlock(FRIGID_STONE_MOSSY.get()::defaultBlockState, BlockBehaviour.Properties.of().noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));

	public static final RegistryObject<Block> FRIGID_STONE_BRICK_MOSSY = register("frigid_stone_brick_mossy", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<SlabBlock> FRIGID_STONE_BRICK_MOSSY_SLAB = register("frigid_stone_brick_mossy_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<StairBlock> FRIGID_STONE_BRICK_MOSSY_STAIRS = register("frigid_stone_brick_mossy_stairs", () -> new StairBlock(FRIGID_STONE_BRICK_MOSSY.get()::defaultBlockState, BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	//FROSTROOT
	public static final RegistryObject<RotatedPillarBlock> FROSTROOT_LOG = register("frostroot_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_FROSTROOT_LOG = register("stripped_frostroot_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<LeavesBlock> FROSTROOT_LEAVES = register("frostroot_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).sound(SoundType.GRASS)));
	public static final RegistryObject<SaplingBlock> FROSTROOT_SAPLING = register("frostroot_sapling", () -> new SaplingBlock(new FrostrootTree(), BlockBehaviour.Properties.of().randomTicks().noCollission().noOcclusion().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> FROSTROOT_PLANKS = register("frostroot_planks", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<SlabBlock> FROSTROOT_PLANKS_SLAB = register("frostroot_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final RegistryObject<StairBlock> FROSTROOT_PLANKS_STAIRS = register("frostroot_planks_stairs", () -> new StairBlock(FROSTROOT_PLANKS.get()::defaultBlockState, BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final RegistryObject<FenceBlock> FROSTROOT_FENCE = register("frostroot_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final RegistryObject<FenceGateBlock> FROSTROOT_FENCE_GATE = register("frostroot_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD), WoodType.OAK));
	public static final RegistryObject<DoorBlock> FROSTROOT_DOOR = register("frostroot_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F).noOcclusion().sound(SoundType.WOOD), BlockSetType.OAK));
	public static final RegistryObject<Block> FROSTROOT_CRAFTING_TABLE = register("frostroot_crafting_table", () -> new FrostCraftingTableBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistryObject<RotatedPillarBlock> FROSTBITE_LOG = register("frostbite_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_FROSTBITE_LOG = register("stripped_frostbite_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<LeavesBlock> FROSTBITE_LEAVES = register("frostbite_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).sound(SoundType.GRASS)));
	public static final RegistryObject<SaplingBlock> FROSTBITE_SAPLING = register("frostbite_sapling", () -> new SaplingBlock(new FrostbiteTree(), BlockBehaviour.Properties.of().randomTicks().noCollission().noOcclusion().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> FROSTBITE_PLANKS = register("frostbite_planks", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<SlabBlock> FROSTBITE_PLANKS_SLAB = register("frostbite_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final RegistryObject<StairBlock> FROSTBITE_PLANKS_STAIRS = register("frostbite_planks_stairs", () -> new StairBlock(FROSTBITE_PLANKS.get()::defaultBlockState, BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final RegistryObject<FenceBlock> FROSTBITE_FENCE = register("frostbite_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final RegistryObject<FenceGateBlock> FROSTBITE_FENCE_GATE = register("frostbite_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD), WoodType.OAK));


	//PLANT
	public static final RegistryObject<Block> VIGOROSHROOM = register("vigoroshroom", () -> new VigoroMushroomBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().lightLevel(state -> {
		return 10;
	}).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> ARCTIC_POPPY = register("arctic_poppy", () -> new BushBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> ARCTIC_WILLOW = register("arctic_willow", () -> new BushBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));

	public static final RegistryObject<Block> COLD_GRASS = register("cold_grass", () -> new ColdTallGrassBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));
	public static final RegistryObject<DoublePlantBlock> COLD_TALL_GRASS = register("cold_tall_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));

	public static final RegistryObject<Block> BEARBERRY_BUSH = noItemRegister("bearberry_bush", () -> new BearBerryBushBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));
	//CROP
	public static final RegistryObject<Block> SUGARBEET = noItemRegister("sugarbeet", () -> new SugarBeetBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.CROP)));
	//EGG
	public static final RegistryObject<Block> SNOWPILE_QUAIL_EGG = register("snowpile_quail_egg", () -> new SnowPileQuailEggBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.2F, 0.25F).sound(SoundType.METAL)));

	//ORE
	public static final RegistryObject<Block> FROST_CRYSTAL_ORE = register("frost_crystal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK), UniformInt.of(1, 2)));
	public static final RegistryObject<Block> GLIMMERROCK_ORE = register("glimmerrock_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK), UniformInt.of(1, 3)));
	public static final RegistryObject<Block> ASTRIUM_ORE = register("astrium_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final RegistryObject<Block> ASTRIUM_BLOCK = register("astrium_block", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> STARDUST_CRYSTAL_ORE = register("stardust_crystal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK), UniformInt.of(2, 4)));
	public static final RegistryObject<Block> STARDUST_CRYSTAL_CLUSTER = register("stardust_crystal_cluster", () -> new StarDustCrystalBlock(BlockBehaviour.Properties.of().isSuffocating(FrostBlocks::never).requiresCorrectToolForDrops().lightLevel((state) -> {
		return 10;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.DEEPSLATE)));


	public static final RegistryObject<Block> WARPED_CRYSTAL_BLOCK = register("warped_crystal_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().lightLevel((state) -> {
		return 12;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.GLASS)));


	public static final RegistryObject<Block> FROST_TORCH = register("frost_torch", () -> new FrostTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH)));
	public static final RegistryObject<Block> WALL_FROST_TORCH = noItemRegister("wall_frost_torch", () -> new WallFrostTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH)));

	public static final RegistryObject<Block> FROST_CAMPFIRE = register("frost_campfire", () -> new FrostCampfireBlock(BlockBehaviour.Properties.of().strength(2.0F).noOcclusion().lightLevel(litBlockEmission(13)).sound(SoundType.WOOD)));

	public static final RegistryObject<Block> FROSTROOT_CHEST = register("frostroot_chest", () -> new FrostChestBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), FrostBlockEntitys.FROST_CHEST::get));

	private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
		return (p_50763_) -> {
			return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
		};
	}

	private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
		return true;
	}

	private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
		return false;
	}

	public static void stripable() {
		AxeItem.STRIPPABLES.put(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
		AxeItem.STRIPPABLES.put(FrostBlocks.FROSTBITE_LOG.get(), FrostBlocks.STRIPPED_FROSTBITE_LOG.get());
	}

	public static void burnables() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFlammable(FROSTROOT_LEAVES.get(), 60, 100);
		fireblock.setFlammable(FROSTROOT_LOG.get(), 5, 5);
		fireblock.setFlammable(STRIPPED_FROSTROOT_LOG.get(), 5, 5);
		fireblock.setFlammable(FROSTROOT_PLANKS.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_PLANKS_SLAB.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_PLANKS_STAIRS.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_FENCE.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_FENCE_GATE.get(), 5, 20);

		fireblock.setFlammable(FROSTBITE_LEAVES.get(), 60, 100);
		fireblock.setFlammable(FROSTBITE_LOG.get(), 5, 5);
		fireblock.setFlammable(STRIPPED_FROSTBITE_LOG.get(), 5, 5);
		fireblock.setFlammable(FROSTBITE_PLANKS.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_PLANKS_SLAB.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_PLANKS_STAIRS.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_FENCE.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_FENCE_GATE.get(), 5, 20);
		fireblock.setFlammable(COLD_GRASS.get(), 60, 100);
		fireblock.setFlammable(COLD_TALL_GRASS.get(), 60, 100);
	}

	private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> register = BLOCKS.register(name, block);
		FrostItems.ITEMS.register(name, item.apply(register));
		return register;
	}

	private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
		RegistryObject<T> register = BLOCKS.register(name, block);
		return register;
	}

	private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
		return (RegistryObject<B>) baseRegister(name, block, FrostBlocks::registerBlockItem);
	}

	private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
		return () -> {
			if (Objects.requireNonNull(block.get()) == FROSTROOT_CHEST.get()) {
				return new BlockItem(FROSTROOT_CHEST.get(), (new Item.Properties())) {
					@Override
					public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
						consumer.accept(new IClientItemExtensions() {
							BlockEntityWithoutLevelRenderer myRenderer;


							@Override
							public BlockEntityWithoutLevelRenderer getCustomRenderer() {
								if (Minecraft.getInstance().getEntityRenderDispatcher() != null && myRenderer == null) {
									myRenderer = new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
										private FrostChestBlockEntity blockEntity;

										@Override
										public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
											if (blockEntity == null) {
												blockEntity = new FrostChestBlockEntity(BlockPos.ZERO, FrostBlocks.FROSTROOT_CHEST.get().defaultBlockState());
											}
											Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, matrix, buffer, x, y);
										}
									};
								}

								return myRenderer;
							}
						});
					}
				};
			} else if (Objects.requireNonNull(block.get()) instanceof DoublePlantBlock || Objects.requireNonNull(block.get()) instanceof DoorBlock) {
				return new DoubleHighBlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
			} else if (Objects.requireNonNull(block.get()) == FrostBlocks.FROST_TORCH.get()) {
				return new StandingAndWallBlockItem(FrostBlocks.FROST_TORCH.get(), FrostBlocks.WALL_FROST_TORCH.get(), new Item.Properties(), Direction.DOWN);
			} else {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
			}
		};
	}
}
