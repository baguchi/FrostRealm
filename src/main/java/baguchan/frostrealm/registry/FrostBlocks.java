package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.block.*;
import baguchan.frostrealm.block.crop.BearBerryBushBlock;
import baguchan.frostrealm.block.crop.RyeBlock;
import baguchan.frostrealm.block.crop.SugarBeetBlock;
import baguchan.frostrealm.item.block.DeferredDoubleHighBlockItem;
import baguchan.frostrealm.world.tree.FrostTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;


public class FrostBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FrostRealm.MODID);
	public static final DeferredBlock<LiquidBlock> HOT_SPRING = noItemRegisterWithEmpty("hot_spring", (properties) -> new LiquidBlock(FrostFluids.HOT_SPRING.value(), properties.mapColor(MapColor.WATER).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));


	public static final DeferredBlock<FrostPortalBlock> FROST_PORTAL = noItemRegisterWithEmpty("frostrealm_portal", (properties) -> new FrostPortalBlock(properties.noOcclusion().noCollission().randomTicks().lightLevel((state) -> {
		return 11;
	}).strength(-1.0F).sound(SoundType.GLASS)));

	public static final DeferredBlock<Block> FROZEN_DIRT = register("frozen_dirt", (properties) -> new Block(properties.strength(0.5F).sound(SoundType.GRAVEL)));
	public static final DeferredBlock<Block> FROZEN_GRASS_BLOCK = register("frozen_grass_block", (properties) -> new FrostGrassBlock(properties.randomTicks().strength(0.6F).sound(SoundType.GRASS), FrostBlocks.FROZEN_DIRT));
	public static final DeferredBlock<Block> FROZEN_FARMLAND = register("frozen_farmland", (properties) -> new FrozenFarmBlock(properties.noOcclusion().strength(0.5F).randomTicks().sound(SoundType.GRAVEL)));

	public static final DeferredBlock<Block> POINTED_ICE = register("pointed_ice", (properties) -> new PointedIceBlock(properties.friction(0.98F).randomTicks().strength(0.5F).dynamicShape().sound(SoundType.GLASS)));

	public static final DeferredBlock<Block> PERMA_SLATE = register("perma_slate", (properties) -> new Block(properties.mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final DeferredBlock<Block> PERMA_SLATE_SMOOTH = register("perma_slate_smooth", (properties) -> new Block(properties.mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final DeferredBlock<Block> PERMA_SLATE_BRICK = register("perma_slate_brick", (properties) -> new Block(properties.mapColor(MapColor.COLOR_CYAN).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
	public static final DeferredBlock<SlabBlock> PERMA_SLATE_BRICK_SLAB = register("perma_slate_brick_slab", (properties) -> new SlabBlock(properties.mapColor(MapColor.COLOR_CYAN).strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
	public static final DeferredBlock<StairBlock> PERMA_SLATE_BRICK_STAIRS = register("perma_slate_brick_stairs", (properties) -> new StairBlock(PERMA_SLATE_BRICK.get().defaultBlockState(), properties.mapColor(MapColor.COLOR_CYAN).strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
	public static final DeferredBlock<Block> PERMA_MAGMA = register("perma_magma", (properties) -> new PermaMagmaBlock(properties.mapColor(MapColor.COLOR_CYAN)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(1F, 3.0F)
			.isValidSpawn((p_187421_, p_187422_, p_187423_, p_187424_) -> p_187424_.fireImmune())
			.hasPostProcess(FrostBlocks::always)));

	public static final DeferredBlock<Block> FRIGID_STONE = register("frigid_stone", (properties) -> new Block(properties.strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<Block> FRIGID_GRASS_BLOCK = register("frigid_grass_block", (properties) -> new FrostGrassBlock(properties.randomTicks().strength(1.5F, 6.0F).sound(SoundType.NYLIUM), FrostBlocks.FRIGID_STONE));

	public static final DeferredBlock<SlabBlock> FRIGID_STONE_SLAB = register("frigid_stone_slab", (properties) -> new SlabBlock(properties.strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<StairBlock> FRIGID_STONE_STAIRS = register("frigid_stone_stairs", (properties) -> new StairBlock(FRIGID_STONE.get().defaultBlockState(), properties.noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<Block> FRIGID_STONE_BRICK = register("frigid_stone_brick", (properties) -> new Block(properties.strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<SlabBlock> FRIGID_STONE_BRICK_SLAB = register("frigid_stone_brick_slab", (properties) -> new SlabBlock(properties.strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<StairBlock> FRIGID_STONE_BRICK_STAIRS = register("frigid_stone_brick_stairs", (properties) -> new StairBlock(FRIGID_STONE_BRICK.get().defaultBlockState(), properties.strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<Block> FRIGID_STONE_SMOOTH = register("frigid_stone_smooth", (properties) -> new Block(properties.strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<Block> CHISELED_FRIGID_STONE_BRICK = register("chiseled_frigid_stone_brick", (properties) -> new Block(properties.strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	public static final DeferredBlock<Block> FRIGID_STONE_MOSSY = register("frigid_stone_mossy", (properties) -> new Block(properties.strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<SlabBlock> FRIGID_STONE_MOSSY_SLAB = register("frigid_stone_mossy_slab", (properties) -> new SlabBlock(properties.strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<StairBlock> FRIGID_STONE_MOSSY_STAIRS = register("frigid_stone_mossy_stairs", (properties) -> new StairBlock(FRIGID_STONE_MOSSY.get().defaultBlockState(), properties.noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));

	public static final DeferredBlock<Block> FRIGID_STONE_BRICK_MOSSY = register("frigid_stone_brick_mossy", (properties) -> new Block(properties.strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<SlabBlock> FRIGID_STONE_BRICK_MOSSY_SLAB = register("frigid_stone_brick_mossy_slab", (properties) -> new SlabBlock(properties.strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<StairBlock> FRIGID_STONE_BRICK_MOSSY_STAIRS = register("frigid_stone_brick_mossy_stairs", (properties) -> new StairBlock(FRIGID_STONE_BRICK_MOSSY.get().defaultBlockState(), properties.strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	public static final DeferredBlock<Block> SHERBET_SAND = register("sherbet_sand", (properties) -> new ColoredFallingBlock(new ColorRGBA(0xFFB9EB), properties.mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
	public static final DeferredBlock<Block> SHERBET_SANDSTONE = register("sherbet_sandstone", (properties) -> new Block(properties.strength(0.8F).requiresCorrectToolForDrops().sound(SoundType.STONE)));
	public static final DeferredBlock<SlabBlock> SHERBET_SANDSTONE_SLAB = register("sherbet_sandstone_slab", (properties) -> new SlabBlock(properties.strength(0.8F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.STONE)));
	public static final DeferredBlock<StairBlock> SHERBET_SANDSTONE_STAIRS = register("sherbet_sandstone_stairs", (properties) -> new StairBlock(FRIGID_STONE.get().defaultBlockState(), properties.noOcclusion().strength(0.8F).requiresCorrectToolForDrops().sound(SoundType.STONE)));



	//FROSTROOT
	public static final DeferredBlock<RotatedPillarBlock> FROSTROOT_LOG = register("frostroot_log", (properties) -> new RotatedPillarBlock(properties.mapColor(DyeColor.PURPLE).strength(2.0F).sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROSTROOT_LOG = register("stripped_frostroot_log", (properties) -> new RotatedPillarBlock(properties.mapColor(DyeColor.PURPLE).strength(2.0F).sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<LeavesBlock> FROSTROOT_LEAVES = register("frostroot_leaves", (properties) -> new LeavesBlock(properties.mapColor(DyeColor.PURPLE).strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).isViewBlocking(FrostBlocks::never).sound(SoundType.GRASS)));
	public static final DeferredBlock<SaplingBlock> FROSTROOT_SAPLING = register("frostroot_sapling", (properties) -> new SaplingBlock(FrostTrees.FROSTROOT, properties.mapColor(DyeColor.PURPLE).randomTicks().noCollission().noOcclusion().sound(SoundType.GRASS)));
	public static final DeferredBlock<Block> FROSTROOT_PLANKS = register("frostroot_planks", (properties) -> new Block(properties.mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<SlabBlock> FROSTROOT_PLANKS_SLAB = register("frostroot_planks_slab", (properties) -> new SlabBlock(properties.mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<StairBlock> FROSTROOT_PLANKS_STAIRS = register("frostroot_planks_stairs", (properties) -> new StairBlock(FROSTROOT_PLANKS.get().defaultBlockState(), properties.mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<FenceBlock> FROSTROOT_FENCE = register("frostroot_fence", (properties) -> new FenceBlock(properties.mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<FenceGateBlock> FROSTROOT_FENCE_GATE = register("frostroot_fence_gate", (properties) -> new FenceGateBlock(FrostWoodTypes.FROSTROOT, properties.mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<DoorBlock> FROSTROOT_DOOR = register("frostroot_door", (properties) -> new DoorBlock(FrostBlockSetTypes.FROSTROOT, properties.mapColor(DyeColor.PURPLE).strength(3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<TrapDoorBlock> FROSTROOT_TRAPDOOR = register("frostroot_trapdoor", (properties) -> new TrapDoorBlock(FrostBlockSetTypes.FROSTROOT, properties.mapColor(DyeColor.PURPLE).strength(3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<ButtonBlock> FROSTROOT_BUTTON = register("frostroot_button", (properties) -> woodenButton(properties, FrostBlockSetTypes.FROSTBITE));
	public static final DeferredBlock<PressurePlateBlock> FROSTROOT_PRESSURE_PLATE = register(
			"frostroot_pressure_plate",
			(properties) -> new PressurePlateBlock(
					FrostBlockSetTypes.FROSTROOT,
					properties
							.mapColor(FROSTROOT_PLANKS.get().defaultMapColor())
							.forceSolidOn()
							.instrument(NoteBlockInstrument.BASS)
							.noCollission()
							.strength(0.5F)
							.ignitedByLava()
							.pushReaction(PushReaction.DESTROY)
			)
	);
	public static final DeferredBlock<RotatedPillarBlock> FROSTBITE_LOG = register("frostbite_log", (properties) -> new FrostBiteLogBlock(properties.mapColor(DyeColor.CYAN).strength(2.0F).randomTicks().sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROSTBITE_LOG = register("stripped_frostbite_log", (properties) -> new RotatedPillarBlock(properties.mapColor(DyeColor.CYAN).strength(2.0F).sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<LeavesBlock> FROSTBITE_LEAVES = register("frostbite_leaves", (properties) -> new LeavesBlock(properties.mapColor(DyeColor.CYAN).strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).isViewBlocking(FrostBlocks::never).sound(SoundType.GRASS)));
	public static final DeferredBlock<SaplingBlock> FROSTBITE_SAPLING = register("frostbite_sapling", (properties) -> new SaplingBlock(FrostTrees.FROSTBITE, properties.mapColor(DyeColor.CYAN).randomTicks().noCollission().noOcclusion().sound(SoundType.GRASS)));
	public static final DeferredBlock<Block> FROSTBITE_PLANKS = register("frostbite_planks", (properties) -> new Block(properties.mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<SlabBlock> FROSTBITE_PLANKS_SLAB = register("frostbite_planks_slab", (properties) -> new SlabBlock(properties.mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<StairBlock> FROSTBITE_PLANKS_STAIRS = register("frostbite_planks_stairs", (properties) -> new StairBlock(FROSTBITE_PLANKS.get().defaultBlockState(), properties.mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<FenceBlock> FROSTBITE_FENCE = register("frostbite_fence", (properties) -> new FenceBlock(properties.mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<FenceGateBlock> FROSTBITE_FENCE_GATE = register("frostbite_fence_gate", (properties) -> new FenceGateBlock(FrostWoodTypes.FROSTBITE, properties.mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD)));
	public static final DeferredBlock<DoorBlock> FROSTBITE_DOOR = register("frostbite_door", (properties) -> new DoorBlock(FrostBlockSetTypes.FROSTBITE, properties.strength(3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));
	public static final DeferredBlock<TrapDoorBlock> FROSTBITE_TRAPDOOR = register("frostbite_trapdoor", (properties) -> new TrapDoorBlock(FrostBlockSetTypes.FROSTBITE, properties.mapColor(DyeColor.CYAN).strength(3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD)));

	public static final DeferredBlock<ButtonBlock> FROSTBITE_BUTTON = register("frostbite_button", (properties) -> woodenButton(properties, FrostBlockSetTypes.FROSTBITE));
	public static final DeferredBlock<PressurePlateBlock> FROSTBITE_PRESSURE_PLATE = register(
			"frostbite_pressure_plate",
			(properties) -> new PressurePlateBlock(
					FrostBlockSetTypes.FROSTBITE,
					properties
							.mapColor(FROSTBITE_PLANKS.get().defaultMapColor())
							.forceSolidOn()
							.instrument(NoteBlockInstrument.BASS)
							.noCollission()
							.strength(0.5F)
							.ignitedByLava()
							.pushReaction(PushReaction.DESTROY)
			)
	);

	//PLANT
	public static final DeferredBlock<Block> VIGOROSHROOM = register("vigoroshroom", (properties) -> new VigoroMushroomBlock(properties.noOcclusion().noCollission().lightLevel(state -> {
		return 10;
	}).sound(SoundType.GRASS)));
	public static final DeferredBlock<Block> ARCTIC_POPPY = register("arctic_poppy", (properties) -> new FlowerBlock(FrostEffects.COLD_RESISTANCE, 200, properties.noOcclusion().noCollission().sound(SoundType.GRASS)));
	public static final DeferredBlock<Block> ARCTIC_WILLOW = register("arctic_willow", (properties) -> new FlowerBlock(FrostEffects.COLD_RESISTANCE, 200, properties.noOcclusion().noCollission().sound(SoundType.GRASS)));

	public static final DeferredBlock<Block> COLD_GRASS = register("cold_grass", (properties) -> new ColdTallGrassBlock(properties.noOcclusion().noCollission().replaceable().sound(SoundType.GRASS)));
	public static final DeferredBlock<DoublePlantBlock> COLD_TALL_GRASS = registerDoubleBlockItem("cold_tall_grass", (properties) -> new DoublePlantBlock(properties.noOcclusion().noCollission().replaceable().sound(SoundType.GRASS)), BlockBehaviour.Properties.of());

	public static final DeferredBlock<Block> BEARBERRY_BUSH = noItemRegister("bearberry_bush", (properties) -> new BearBerryBushBlock(properties.noOcclusion().noCollission().sound(SoundType.GRASS)), BlockBehaviour.Properties.of());
	//CROP
	public static final DeferredBlock<Block> SUGARBEET = noItemRegister("sugarbeet", (properties) -> new SugarBeetBlock(properties.noOcclusion().noCollission().sound(SoundType.CROP)), BlockBehaviour.Properties.of());
	public static final DeferredBlock<Block> RYE = noItemRegister("rye", (properties) -> new RyeBlock(properties.noOcclusion().noCollission().sound(SoundType.CROP)), BlockBehaviour.Properties.of());

	public static final DeferredBlock<Block> RYE_BLOCK = register("rye_block", (properties) -> new HayBlock(properties.sound(SoundType.GRASS)));
	//EGG
	public static final DeferredBlock<Block> SNOWPILE_QUAIL_EGG = register("snowpile_quail_egg", (properties) -> new SnowPileQuailEggBlock(properties.noOcclusion().strength(0.2F, 0.25F).sound(SoundType.METAL)));

	//ORE
	public static final DeferredBlock<Block> FROST_CRYSTAL_ORE = register("frost_crystal_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 2), properties.strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<Block> GLIMMERROCK_ORE = register("glimmerrock_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 3), properties.strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<Block> ASTRIUM_ORE = register("astrium_ore", (properties) -> new Block(properties.strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final DeferredBlock<Block> FROST_CRYSTAL_SLATE_ORE = register("frost_crystal_slate_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 2), properties.strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final DeferredBlock<Block> GLIMMERROCK_SLATE_ORE = register("glimmerrock_slate_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 3), properties.strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final DeferredBlock<Block> ASTRIUM_SLATE_ORE = register("astrium_slate_ore", (properties) -> new Block(properties.strength(4.6F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final DeferredBlock<Block> FROST_CRYSTAL_BLOCK = register("frost_crystal_block", (properties) -> new Block(properties.strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final DeferredBlock<Block> ASTRIUM_BLOCK = register("astrium_block", (properties) -> new Block(properties.strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final DeferredBlock<Block> RAW_ASTRIUM_BLOCK = register("raw_astrium_block", (properties) -> new Block(properties.strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

	public static final DeferredBlock<Block> GLIMMERROCK_BLOCK = register("glimmerrock_block", (properties) -> new Block(properties.strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));


	public static final DeferredBlock<Block> GLACINIUM_ORE = register("glacinium_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 6), properties.noOcclusion().requiresCorrectToolForDrops()
			.strength(10.0F, 100.0F).sound(SoundType.DEEPSLATE)));

	public static final DeferredBlock<Block> RAW_GLACINIUM_BLOCK = register("raw_glacinium_block", (properties) -> new Block(properties.noOcclusion().requiresCorrectToolForDrops()
			.strength(15.0F, 100.0F).sound(SoundType.GLASS)));
	public static final DeferredBlock<Block> GLACINIUM_BLOCK = register("glacinium_block", (properties) -> new Block(properties.noOcclusion().requiresCorrectToolForDrops()
			.strength(15.0F, 100.0F).sound(SoundType.GLASS)));


	public static final DeferredBlock<Block> STARDUST_CRYSTAL_ORE = register("stardust_crystal_ore", (properties) -> new DropExperienceBlock(UniformInt.of(2, 4), properties.strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
	public static final DeferredBlock<Block> STARDUST_CRYSTAL_CLUSTER = register("stardust_crystal_cluster", (properties) -> new StarDustCrystalBlock(properties.isSuffocating(FrostBlocks::never).requiresCorrectToolForDrops().lightLevel((state) -> {
		return 10;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.DEEPSLATE)));


	public static final DeferredBlock<Block> WARPED_CRYSTAL_BLOCK = register("warped_crystal_block", (properties) -> new Block(properties.requiresCorrectToolForDrops().lightLevel((state) -> {
		return 12;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.GLASS)));


	public static final DeferredBlock<Block> WALL_FROST_TORCH = noItemRegister("wall_frost_torch", (properties) -> new WallFrostTorchBlock(properties.noCollission().instabreak().lightLevel(p_220871_ -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)), BlockBehaviour.Properties.of());
	public static final DeferredBlock<Block> FROST_TORCH = registerTorchBlock("frost_torch", (properties) -> new FrostTorchBlock(properties.noCollission().instabreak().lightLevel(p_220871_ -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)), WALL_FROST_TORCH, BlockBehaviour.Properties.of());

	public static final DeferredBlock<Block> FROST_CAMPFIRE = register("frost_campfire", (properties) -> new FrostCampfireBlock(properties.strength(2.0F).noOcclusion().lightLevel(litBlockEmission(13)).sound(SoundType.WOOD)));

	public static final DeferredBlock<Block> AURORA_INFUSER = register("aurora_infuser", (properties) -> new AuroraInfuserBlock(properties.requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.METAL)));

	private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
		return (p_50763_) -> {
			return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
		};
	}

	private static ButtonBlock woodenButton(BlockBehaviour.Properties properties, BlockSetType p_278239_) {
		return new ButtonBlock(p_278239_, 30, properties.noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY));
	}

	private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
		return true;
	}

	private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
		return false;
	}

	public static void burnables() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFlammable(FROSTROOT_SAPLING.get(), 60, 100);
		fireblock.setFlammable(FROSTROOT_LEAVES.get(), 60, 100);
		fireblock.setFlammable(FROSTROOT_LOG.get(), 5, 5);
		fireblock.setFlammable(STRIPPED_FROSTROOT_LOG.get(), 5, 5);
		fireblock.setFlammable(FROSTROOT_PLANKS.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_PLANKS_SLAB.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_PLANKS_STAIRS.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_FENCE.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_FENCE_GATE.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_DOOR.get(), 5, 20);
		fireblock.setFlammable(FROSTROOT_TRAPDOOR.get(), 5, 20);

		fireblock.setFlammable(FROSTBITE_SAPLING.get(), 60, 100);
		fireblock.setFlammable(FROSTBITE_LEAVES.get(), 60, 100);
		fireblock.setFlammable(FROSTBITE_LOG.get(), 5, 5);
		fireblock.setFlammable(STRIPPED_FROSTBITE_LOG.get(), 5, 5);
		fireblock.setFlammable(FROSTBITE_PLANKS.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_PLANKS_SLAB.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_PLANKS_STAIRS.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_FENCE.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_FENCE_GATE.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_DOOR.get(), 5, 20);
		fireblock.setFlammable(FROSTBITE_TRAPDOOR.get(), 5, 20);



		fireblock.setFlammable(COLD_TALL_GRASS.get(), 60, 100);
		fireblock.setFlammable(RYE_BLOCK.get(), 60, 20);
	}

	private static <B extends Block> DeferredBlock<B> noItemRegister(String name, Function<BlockBehaviour.Properties, ? extends B> block, BlockBehaviour.Properties properties) {
		DeferredBlock<B> register = BLOCKS.registerBlock(name, block, BlockBehaviour.Properties.of());
		return register;
	}

	private static <B extends Block> DeferredBlock<B> noItemRegisterWithEmpty(String name, Function<BlockBehaviour.Properties, ? extends B> block) {
		DeferredBlock<B> register = noItemRegister(name, block, BlockBehaviour.Properties.of());
		return register;
	}

	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> block) {
		DeferredBlock<B> bDeferredBlock = noItemRegister(name, block, BlockBehaviour.Properties.of());
		FrostBlocks.registerBlockItem(name, bDeferredBlock);
		return bDeferredBlock;
	}

	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> block, BlockBehaviour.Properties p_368547_) {
		DeferredBlock<B> bDeferredBlock = noItemRegister(name, block, BlockBehaviour.Properties.of());
		FrostBlocks.registerBlockItem(name, bDeferredBlock);
		return bDeferredBlock;
	}

	public static <T extends Block> DeferredBlock<T> registerDoubleBlockItem(String name, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
		DeferredBlock<T> ret = BLOCKS.register(name, () -> block.apply(properties.setId(ResourceKey.create(Registries.BLOCK, FrostRealm.prefix(name)))));
		FrostItems.ITEMS.registerItem(name, itemProps -> new DeferredDoubleHighBlockItem(ret, itemProps), new Item.Properties());
		return ret;
	}

	private static <T extends Block> DeferredBlock<T> registerTorchBlock(String name, Function<BlockBehaviour.Properties, T> block, DeferredBlock<T> wallTorchBlock, BlockBehaviour.Properties properties) {
		DeferredBlock<T> ret = BLOCKS.register(name, () -> block.apply(properties.setId(ResourceKey.create(Registries.BLOCK, FrostRealm.prefix(name)))));
		FrostItems.ITEMS.registerItem(name, itemProps -> new StandingAndWallBlockItem(ret.get(), wallTorchBlock.get(), Direction.DOWN, itemProps), new Item.Properties());
		return ret;

	}

	private static <B extends Block> DeferredItem<? extends BlockItem> registerBlockItem(String name, DeferredBlock<B> bDeferredBlock) {
		if (Objects.requireNonNull(bDeferredBlock) == FrostBlocks.FROST_TORCH) {
			return FrostItems.ITEMS.registerItem(name, properties -> new StandingAndWallBlockItem(FrostBlocks.FROST_TORCH.get(), FrostBlocks.WALL_FROST_TORCH.get(), Direction.DOWN, properties));
		}
		return FrostItems.ITEMS.registerSimpleBlockItem(name, bDeferredBlock);

	}
}
