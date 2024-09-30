package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.block.*;
import baguchan.frostrealm.block.crop.BearBerryBushBlock;
import baguchan.frostrealm.block.crop.RyeBlock;
import baguchan.frostrealm.block.crop.SugarBeetBlock;
import baguchan.frostrealm.world.tree.FrostTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;


public class FrostBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, FrostRealm.MODID);
	public static final Supplier<LiquidBlock> HOT_SPRING = noItemRegister("hot_spring", () -> new NonBucketableLiquidBlock(FrostFluids.HOT_SPRING, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));


    public static final Supplier<FrostPortalBlock> FROST_PORTAL = noItemRegister("frostrealm_portal", () -> new FrostPortalBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().randomTicks().lightLevel((state) -> {
		return 11;
	}).strength(-1.0F).sound(SoundType.GLASS)));

    public static final Supplier<Block> FROZEN_DIRT = register("frozen_dirt", () -> new Block(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GRAVEL)));
	public static final Supplier<Block> FROZEN_GRASS_BLOCK = register("frozen_grass_block", () -> new FrostGrassBlock(BlockBehaviour.Properties.of().randomTicks().strength(0.6F).sound(SoundType.GRASS), FrostBlocks.FROZEN_DIRT));
	public static final Supplier<Block> FROZEN_FARMLAND = register("frozen_farmland", () -> new FrozenFarmBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.5F).randomTicks().sound(SoundType.GRAVEL)));

    public static final Supplier<Block> POINTED_ICE = register("pointed_ice", () -> new PointedIceBlock(BlockBehaviour.Properties.of().friction(0.98F).randomTicks().strength(0.5F).dynamicShape().sound(SoundType.GLASS)));

	public static final Supplier<Block> PERMA_SLATE = register("perma_slate", () -> new Block(BlockBehaviour.Properties.of().strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Supplier<Block> PERMA_SLATE_SMOOTH = register("perma_slate_smooth", () -> new Block(BlockBehaviour.Properties.of().strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Supplier<Block> PERMA_SLATE_BRICK = register("perma_slate_brick", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
	public static final Supplier<SlabBlock> PERMA_SLATE_BRICK_SLAB = register("perma_slate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
	public static final Supplier<StairBlock> PERMA_SLATE_BRICK_STAIRS = register("perma_slate_brick_stairs", () -> new StairBlock(PERMA_SLATE_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));

	public static final Supplier<Block> FRIGID_STONE = register("frigid_stone", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final Supplier<Block> FRIGID_GRASS_BLOCK = register("frigid_grass_block", () -> new FrostGrassBlock(BlockBehaviour.Properties.of().randomTicks().strength(1.5F, 6.0F).sound(SoundType.NYLIUM), FrostBlocks.FRIGID_STONE));

	public static final Supplier<SlabBlock> FRIGID_STONE_SLAB = register("frigid_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final Supplier<StairBlock> FRIGID_STONE_STAIRS = register("frigid_stone_stairs", () -> new StairBlock(FRIGID_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of().noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
    public static final Supplier<Block> FRIGID_STONE_BRICK = register("frigid_stone_brick", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final Supplier<SlabBlock> FRIGID_STONE_BRICK_SLAB = register("frigid_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final Supplier<StairBlock> FRIGID_STONE_BRICK_STAIRS = register("frigid_stone_brick_stairs", () -> new StairBlock(FRIGID_STONE_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final Supplier<Block> FRIGID_STONE_SMOOTH = register("frigid_stone_smooth", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final Supplier<Block> CHISELED_FRIGID_STONE_BRICK = register("chiseled_frigid_stone_brick", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static final Supplier<Block> FRIGID_STONE_MOSSY = register("frigid_stone_mossy", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
    public static final Supplier<SlabBlock> FRIGID_STONE_MOSSY_SLAB = register("frigid_stone_mossy_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final Supplier<StairBlock> FRIGID_STONE_MOSSY_STAIRS = register("frigid_stone_mossy_stairs", () -> new StairBlock(FRIGID_STONE_MOSSY.get().defaultBlockState(), BlockBehaviour.Properties.of().noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));

    public static final Supplier<Block> FRIGID_STONE_BRICK_MOSSY = register("frigid_stone_brick_mossy", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final Supplier<SlabBlock> FRIGID_STONE_BRICK_MOSSY_SLAB = register("frigid_stone_brick_mossy_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final Supplier<StairBlock> FRIGID_STONE_BRICK_MOSSY_STAIRS = register("frigid_stone_brick_mossy_stairs", () -> new StairBlock(FRIGID_STONE_BRICK_MOSSY.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static final Supplier<Block> SHERBET_SAND = register("sherbet_sand", () -> new ColoredFallingBlock(new ColorRGBA(0xFFB9EB), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final Supplier<Block> SHERBET_SANDSTONE = register("sherbet_sandstone", () -> new Block(BlockBehaviour.Properties.of().strength(0.8F).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final Supplier<SlabBlock> SHERBET_SANDSTONE_SLAB = register("sherbet_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(0.8F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.STONE)));
	public static final Supplier<StairBlock> SHERBET_SANDSTONE_STAIRS = register("sherbet_sandstone_stairs", () -> new StairBlock(FRIGID_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of().noOcclusion().strength(0.8F).requiresCorrectToolForDrops().sound(SoundType.STONE)));



	//FROSTROOT
    public static final Supplier<RotatedPillarBlock> FROSTROOT_LOG = register("frostroot_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD)));
    public static final Supplier<RotatedPillarBlock> STRIPPED_FROSTROOT_LOG = register("stripped_frostroot_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD)));
    public static final Supplier<LeavesBlock> FROSTROOT_LEAVES = register("frostroot_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).sound(SoundType.GRASS)));
	public static final Supplier<SaplingBlock> FROSTROOT_SAPLING = register("frostroot_sapling", () -> new SaplingBlock(FrostTrees.FROSTROOT, BlockBehaviour.Properties.of().randomTicks().noCollission().noOcclusion().sound(SoundType.GRASS)));
    public static final Supplier<Block> FROSTROOT_PLANKS = register("frostroot_planks", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<SlabBlock> FROSTROOT_PLANKS_SLAB = register("frostroot_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final Supplier<StairBlock> FROSTROOT_PLANKS_STAIRS = register("frostroot_planks_stairs", () -> new StairBlock(FROSTROOT_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
    public static final Supplier<FenceBlock> FROSTROOT_FENCE = register("frostroot_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final Supplier<FenceGateBlock> FROSTROOT_FENCE_GATE = register("frostroot_fence_gate", () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
	public static final Supplier<DoorBlock> FROSTROOT_DOOR = register("frostroot_door", () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(3.0F).noOcclusion().sound(SoundType.WOOD)));
    public static final Supplier<Block> FROSTROOT_CRAFTING_TABLE = register("frostroot_crafting_table", () -> new FrostCraftingTableBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));


	//PLANT
    public static final Supplier<Block> VIGOROSHROOM = register("vigoroshroom", () -> new VigoroMushroomBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().lightLevel(state -> {
		return 10;
	}).sound(SoundType.GRASS)));
	public static final Supplier<Block> ARCTIC_POPPY = register("arctic_poppy", () -> new FlowerBlock(FrostEffects.COLD_RESISTANCE, 200, BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));
	public static final Supplier<Block> ARCTIC_WILLOW = register("arctic_willow", () -> new FlowerBlock(FrostEffects.COLD_RESISTANCE, 200, BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));

	public static final Supplier<Block> COLD_GRASS = register("cold_grass", () -> new ColdTallGrassBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().replaceable().sound(SoundType.GRASS)));
	public static final Supplier<DoublePlantBlock> COLD_TALL_GRASS = register("cold_tall_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().replaceable().sound(SoundType.GRASS)));

    public static final Supplier<Block> BEARBERRY_BUSH = noItemRegister("bearberry_bush", () -> new BearBerryBushBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.GRASS)));
	//CROP
    public static final Supplier<Block> SUGARBEET = noItemRegister("sugarbeet", () -> new SugarBeetBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.CROP)));
    public static final Supplier<Block> RYE = noItemRegister("rye", () -> new RyeBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.CROP)));
	//EGG
    public static final Supplier<Block> SNOWPILE_QUAIL_EGG = register("snowpile_quail_egg", () -> new SnowPileQuailEggBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.2F, 0.25F).sound(SoundType.METAL)));

	//ORE
	public static final Supplier<Block> FROST_CRYSTAL_ORE = register("frost_crystal_ore", () -> new DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final Supplier<Block> GLIMMERROCK_ORE = register("glimmerrock_ore", () -> new DropExperienceBlock(UniformInt.of(1, 3), BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final Supplier<Block> ASTRIUM_ORE = register("astrium_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Supplier<Block> FROST_CRYSTAL_SLATE_ORE = register("frost_crystal_slate_ore", () -> new DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Supplier<Block> GLIMMERROCK_SLATE_ORE = register("glimmerrock_slate_ore", () -> new DropExperienceBlock(UniformInt.of(1, 3), BlockBehaviour.Properties.of().strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Supplier<Block> ASTRIUM_SLATE_ORE = register("astrium_slate_ore", () -> new Block(BlockBehaviour.Properties.of().strength(4.6F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Supplier<Block> FROST_CRYSTAL_BLOCK = register("frost_crystal_block", () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final Supplier<Block> ASTRIUM_BLOCK = register("astrium_block", () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final Supplier<Block> RAW_ASTRIUM_BLOCK = register("raw_astrium_block", () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	public static final Supplier<Block> GLIMMERROCK_BLOCK = register("glimmerrock_block", () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));


	public static final Supplier<Block> GLACINIUM_ORE = register("glacinium_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()
			.strength(10.0F, 100.0F).sound(SoundType.DEEPSLATE)));

	public static final Supplier<Block> GLACINIUM_BLOCK = register("glacinium_block", () -> new Block(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()
			.strength(15.0F, 100.0F).sound(SoundType.GLASS)));


	public static final Supplier<Block> STARDUST_CRYSTAL_ORE = register("stardust_crystal_ore", () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
    public static final Supplier<Block> STARDUST_CRYSTAL_CLUSTER = register("stardust_crystal_cluster", () -> new StarDustCrystalBlock(BlockBehaviour.Properties.of().isSuffocating(FrostBlocks::never).requiresCorrectToolForDrops().lightLevel((state) -> {
		return 10;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.DEEPSLATE)));


    public static final Supplier<Block> WARPED_CRYSTAL_BLOCK = register("warped_crystal_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().lightLevel((state) -> {
		return 12;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.GLASS)));


	public static final Supplier<Block> FROST_TORCH = register("frost_torch", () -> new FrostTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
	public static final Supplier<Block> WALL_FROST_TORCH = noItemRegister("wall_frost_torch", () -> new WallFrostTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)));

    public static final Supplier<Block> FROST_CAMPFIRE = register("frost_campfire", () -> new FrostCampfireBlock(BlockBehaviour.Properties.of().strength(2.0F).noOcclusion().lightLevel(litBlockEmission(13)).sound(SoundType.WOOD)));

    public static final Supplier<Block> FROSTROOT_CHEST = register("frostroot_chest", () -> new FrostChestBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), FrostBlockEntitys.FROST_CHEST::get));
    public static final Supplier<Block> AURORA_INFUSER = register("aurora_infuser", () -> new AuroraInfuserBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.METAL)));

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
		fireblock.setFlammable(COLD_TALL_GRASS.get(), 60, 100);
	}

    private static <T extends Block> Supplier<T> baseRegister(String name, Supplier<? extends T> block, Function<Supplier<T>, Supplier<? extends Item>> item) {
        Supplier<T> register = BLOCKS.register(name, block);
		FrostItems.ITEMS.register(name, item.apply(register));
		return register;
	}

    private static <T extends Block> Supplier<T> noItemRegister(String name, Supplier<? extends T> block) {
        Supplier<T> register = BLOCKS.register(name, block);
		return register;
	}

    private static <B extends Block> Supplier<B> register(String name, Supplier<? extends Block> block) {
        return (Supplier<B>) baseRegister(name, block, FrostBlocks::registerBlockItem);
	}

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final Supplier<T> block) {
		return () -> {
			if (Objects.requireNonNull(block.get()) == FROSTROOT_CHEST.get()) {
				return new BlockItem(FROSTROOT_CHEST.get(), (new Item.Properties()));
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
