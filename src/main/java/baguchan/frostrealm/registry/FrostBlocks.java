package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.block.*;
import baguchan.frostrealm.world.tree.FrostrootTree;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FrostBlocks {
	public static final FrostPortalBlock FROST_PORTAL = new FrostPortalBlock(BlockBehaviour.Properties.of(Material.PORTAL).noOcclusion().noCollission().randomTicks().lightLevel((state) -> {
		return 11;
	}).strength(-1.0F).sound(SoundType.GLASS));

	public static final Block FROZEN_DIRT = new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.5F).sound(SoundType.GRAVEL));
	public static final Block FROZEN_GRASS_BLOCK = new FrostGrassBlock(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
	public static final Block FROZEN_FARMLAND = new FrozenFarmBlock(BlockBehaviour.Properties.of(Material.DIRT).noOcclusion().strength(0.5F).sound(SoundType.GRAVEL));

	public static final Block POINTED_ICE = new PointedIceBlock(BlockBehaviour.Properties.of(Material.ICE).friction(0.98F).randomTicks().strength(0.5F).dynamicShape().sound(SoundType.GLASS));


	public static final Block FRIGID_STONE = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final SlabBlock FRIGID_STONE_SLAB = new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final StairBlock FRIGID_STONE_STAIRS = new StairBlock(FRIGID_STONE.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final Block FRIGID_STONE_BRICK = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
	public static final SlabBlock FRIGID_STONE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
	public static final StairBlock FRIGID_STONE_BRICK_STAIRS = new StairBlock(FRIGID_STONE_BRICK.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
	public static final Block FRIGID_STONE_SMOOTH_BRICK = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));

	public static final Block FRIGID_STONE_MOSSY = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final SlabBlock FRIGID_STONE_MOSSY_SLAB = new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final StairBlock FRIGID_STONE_MOSSY_STAIRS = new StairBlock(FRIGID_STONE_MOSSY.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));

	//FROSTROOT
	public static final RotatedPillarBlock FROSTROOT_LOG = new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
	public static final LeavesBlock FROSTROOT_LEAVES = new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).noOcclusion().sound(SoundType.GRASS));
	public static final SaplingBlock FROSTROOT_SAPLING = new SaplingBlock(new FrostrootTree(), BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().noOcclusion().sound(SoundType.GRASS));
	public static final Block FROSTROOT_PLANKS = new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final SlabBlock FROSTROOT_PLANKS_SLAB = new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD));
	public static final StairBlock FROSTROOT_PLANKS_STAIRS = new StairBlock(FROSTROOT_PLANKS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD));
	public static final FenceBlock FROSTROOT_FENCE = new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD));
	public static final FenceGateBlock FROSTROOT_FENCE_GATE = new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD));
	public static final DoorBlock FROSTROOT_DOOR = new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).noOcclusion().sound(SoundType.WOOD));

	//PLANT
	public static final Block VIGOROSHROOM = new VigoroMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().noCollission().lightLevel(state -> {
		return 10;
	}).sound(SoundType.GRASS));
	public static final Block ARCTIC_POPPY = new BushBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().noCollission().sound(SoundType.GRASS));
	public static final Block ARCTIC_WILLOW = new BushBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().noCollission().sound(SoundType.GRASS));

	public static final Block COLD_GRASS = new ColdTallGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noOcclusion().noCollission().sound(SoundType.GRASS));
	public static final Block COLD_TALL_GRASS = new DoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noOcclusion().noCollission().sound(SoundType.GRASS));

	public static final Block BEARBERRY_BUSH = new BearBerryBushBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().noCollission().sound(SoundType.GRASS));
	//CROP
	public static final Block SUGARBEET = new SugarBeetBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().noCollission().sound(SoundType.CROP));

	//ORE
	public static final Block FROST_CRYSTAL_ORE = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final Block GLIMMERROCK_ORE = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().lightLevel((state) -> {
		return 10;
	}).sound(SoundType.NETHERRACK));
	public static final Block STARDUST_CRYSTAL_ORE = new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
	public static final Block STARDUST_CRYSTAL_CLUSTER = new Block(BlockBehaviour.Properties.of(Material.GLASS).requiresCorrectToolForDrops().lightLevel((state) -> {
		return 10;
	}).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.GLASS));

	public static final Block FROST_TORCH = new FrostTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH));
	public static final Block WALL_FROST_TORCH = new WallFrostTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH));


	public static void burnables() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFlammable(FROSTROOT_LEAVES, 60, 100);
		fireblock.setFlammable(FROSTROOT_LOG, 5, 5);
		fireblock.setFlammable(FROSTROOT_PLANKS, 5, 20);
		fireblock.setFlammable(FROSTROOT_PLANKS_SLAB, 5, 20);
		fireblock.setFlammable(FROSTROOT_PLANKS_STAIRS, 5, 20);
		fireblock.setFlammable(FROSTROOT_FENCE, 5, 20);
		fireblock.setFlammable(FROSTROOT_FENCE_GATE, 5, 20);
		fireblock.setFlammable(COLD_GRASS, 60, 100);
		fireblock.setFlammable(COLD_TALL_GRASS, 60, 100);
	}

	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> registry) {
		registry.getRegistry().register(FROST_PORTAL.setRegistryName("frostrealm_portal"));
		registry.getRegistry().register(FROZEN_DIRT.setRegistryName("frozen_dirt"));
		registry.getRegistry().register(FROZEN_GRASS_BLOCK.setRegistryName("frozen_grass_block"));
		registry.getRegistry().register(FROZEN_FARMLAND.setRegistryName("frozen_farmland"));
		registry.getRegistry().register(POINTED_ICE.setRegistryName("pointed_ice"));
		registry.getRegistry().register(FRIGID_STONE.setRegistryName("frigid_stone"));
		registry.getRegistry().register(FRIGID_STONE_SLAB.setRegistryName("frigid_stone_slab"));
		registry.getRegistry().register(FRIGID_STONE_STAIRS.setRegistryName("frigid_stone_stairs"));
		registry.getRegistry().register(FRIGID_STONE_BRICK.setRegistryName("frigid_stone_brick"));
		registry.getRegistry().register(FRIGID_STONE_BRICK_SLAB.setRegistryName("frigid_stone_brick_slab"));
		registry.getRegistry().register(FRIGID_STONE_BRICK_STAIRS.setRegistryName("frigid_stone_brick_stairs"));
		registry.getRegistry().register(FRIGID_STONE_SMOOTH_BRICK.setRegistryName("frigid_stone_smooth_brick"));

		registry.getRegistry().register(FRIGID_STONE_MOSSY.setRegistryName("frigid_stone_mossy"));
		registry.getRegistry().register(FRIGID_STONE_MOSSY_SLAB.setRegistryName("frigid_stone_mossy_slab"));
		registry.getRegistry().register(FRIGID_STONE_MOSSY_STAIRS.setRegistryName("frigid_stone_mossy_stairs"));

		registry.getRegistry().register(FROSTROOT_LOG.setRegistryName("frostroot_log"));
		registry.getRegistry().register(FROSTROOT_LEAVES.setRegistryName("frostroot_leaves"));
		registry.getRegistry().register(FROSTROOT_SAPLING.setRegistryName("frostroot_sapling"));
		registry.getRegistry().register(FROSTROOT_PLANKS.setRegistryName("frostroot_planks"));
		registry.getRegistry().register(FROSTROOT_PLANKS_SLAB.setRegistryName("frostroot_planks_slab"));
		registry.getRegistry().register(FROSTROOT_PLANKS_STAIRS.setRegistryName("frostroot_planks_stairs"));
		registry.getRegistry().register(FROSTROOT_FENCE.setRegistryName("frostroot_fence"));
		registry.getRegistry().register(FROSTROOT_FENCE_GATE.setRegistryName("frostroot_fence_gate"));
		registry.getRegistry().register(FROSTROOT_DOOR.setRegistryName("frostroot_door"));

		registry.getRegistry().register(VIGOROSHROOM.setRegistryName("vigoroshroom"));
		registry.getRegistry().register(ARCTIC_POPPY.setRegistryName("arctic_poppy"));
		registry.getRegistry().register(ARCTIC_WILLOW.setRegistryName("arctic_willow"));

		registry.getRegistry().register(BEARBERRY_BUSH.setRegistryName("bearberry_bush"));

		registry.getRegistry().register(SUGARBEET.setRegistryName("sugarbeet"));

		registry.getRegistry().register(COLD_GRASS.setRegistryName("cold_grass"));
		registry.getRegistry().register(COLD_TALL_GRASS.setRegistryName("cold_tall_grass"));

		registry.getRegistry().register(FROST_CRYSTAL_ORE.setRegistryName("frost_crystal_ore"));
		registry.getRegistry().register(GLIMMERROCK_ORE.setRegistryName("glimmerrock_ore"));
		registry.getRegistry().register(STARDUST_CRYSTAL_ORE.setRegistryName("stardust_crystal_ore"));
		registry.getRegistry().register(STARDUST_CRYSTAL_CLUSTER.setRegistryName("stardust_crystal_cluster"));
		registry.getRegistry().register(FROST_TORCH.setRegistryName("frost_torch"));
		registry.getRegistry().register(WALL_FROST_TORCH.setRegistryName("wall_frost_torch"));
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
		FrostItems.register(registry, new BlockItem(FROZEN_DIRT, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROZEN_GRASS_BLOCK, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROZEN_FARMLAND, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

		FrostItems.register(registry, new BlockItem(POINTED_ICE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_SLAB, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_STAIRS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_BRICK, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_BRICK_SLAB, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_BRICK_STAIRS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_SMOOTH_BRICK, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

		FrostItems.register(registry, new BlockItem(FRIGID_STONE_MOSSY, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_MOSSY_SLAB, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FRIGID_STONE_MOSSY_STAIRS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

		FrostItems.register(registry, new BlockItem(FROSTROOT_LOG, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_LEAVES, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_SAPLING, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_PLANKS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_PLANKS_SLAB, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_PLANKS_STAIRS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_FENCE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(FROSTROOT_FENCE_GATE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new DoubleHighBlockItem(FROSTROOT_DOOR, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));


		FrostItems.register(registry, new BlockItem(VIGOROSHROOM, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(ARCTIC_POPPY, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(ARCTIC_WILLOW, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

		FrostItems.register(registry, new BlockItem(COLD_GRASS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new DoubleHighBlockItem(COLD_TALL_GRASS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));


		FrostItems.register(registry, new BlockItem(FROST_CRYSTAL_ORE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(GLIMMERROCK_ORE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(STARDUST_CRYSTAL_ORE, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
		FrostItems.register(registry, new BlockItem(STARDUST_CRYSTAL_CLUSTER, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	}
}
