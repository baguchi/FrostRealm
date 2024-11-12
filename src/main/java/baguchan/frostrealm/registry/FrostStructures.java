package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_DIMENSION_PADDING;

public class FrostStructures {
    public static final ResourceKey<Structure> IGLOO = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo"));

    public static final ResourceKey<StructureSet> IGLOO_SET = ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo"));

    public static final ResourceKey<StructureTemplatePool> IGLOO_ROAD = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/road"));
    public static final ResourceKey<StructureTemplatePool> IGLOO_HOUSE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/igloo"));
    public static final ResourceKey<StructureTemplatePool> IGLOO_ENTRANCE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/igloo_entrance"));

    public static final ResourceKey<StructureTemplatePool> YETI = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/mobs/yeti"));


    public static final ResourceKey<StructureProcessorList> IGLOO_ROAD_PROCESSOR = ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo_road"));

    public static final ResourceKey<Structure> UNDER_HIDEOUT = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "under_hideout"));

    public static final ResourceKey<StructureSet> UNDER_HIDEOUT_SET = ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "under_hideout"));

    public static final ResourceKey<StructureTemplatePool> UNDER_HIDEOUT_CORRIDOR = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "under_hideout/corridor"));
    public static final ResourceKey<StructureTemplatePool> UNDER_HIDEOUT_ROOM = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "under_hideout/room"));
    public static final ResourceKey<StructureTemplatePool> UNDER_HIDEOUT_ROOM2 = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "under_hideout/room2"));
    public static final ResourceKey<StructureTemplatePool> UNDER_HIDEOUT_ENTRANCE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "under_hideout/under_hideout_entrance"));


    public static void bootstrapStructures(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        context.register(
                IGLOO,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(FrostTags.Biomes.HAS_IGLOO))
                                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                                .build(),
                        pools.getOrThrow(IGLOO_ENTRANCE),
                        8,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                UNDER_HIDEOUT,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(FrostTags.Biomes.HAS_UNDER_HIDEOUT))
                                .terrainAdapation(TerrainAdjustment.BEARD_BOX)
                                .spawnOverrides(Map.of())
                                .build(),
                        pools.getOrThrow(UNDER_HIDEOUT_ENTRANCE),
                        Optional.empty(),
                        16,
                        ConstantHeight.of(VerticalAnchor.absolute(28)),
                        false,
                        Optional.empty(),
                        80,
                        List.of(),
                        DEFAULT_DIMENSION_PADDING,
                        LiquidSettings.IGNORE_WATERLOGGING
                )
        );
    }

    public static void bootstrapSets(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(IGLOO_SET, new StructureSet(List.of(StructureSet.entry(structures.getOrThrow(IGLOO), 1))
                , new RandomSpreadStructurePlacement(26, 8, RandomSpreadType.LINEAR, 16324620)));
        context.register(UNDER_HIDEOUT_SET, new StructureSet(List.of(StructureSet.entry(structures.getOrThrow(UNDER_HIDEOUT), 1))
                , new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 1243620)));
    }

    public static void bootstrapPools(BootstrapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

        context.register(IGLOO_ENTRANCE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("igloo/igloo_entrance")), 1)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(IGLOO_HOUSE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("igloo/igloo_1")), 2),
                Pair.of(StructurePoolElement.single(name("igloo/igloo_2")), 1),
                Pair.of(StructurePoolElement.single(name("igloo/rest_place")), 1),
                Pair.of(StructurePoolElement.single(name("igloo/igloo_3")), 4)
        ), StructureTemplatePool.Projection.RIGID));


        context.register(IGLOO_ROAD, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("igloo/road_1"), processors.getOrThrow(IGLOO_ROAD_PROCESSOR)), 5),
                Pair.of(StructurePoolElement.single(name("igloo/road_2"), processors.getOrThrow(IGLOO_ROAD_PROCESSOR)), 2),
                Pair.of(StructurePoolElement.single(name("igloo/road_3"), processors.getOrThrow(IGLOO_ROAD_PROCESSOR)), 2),
                Pair.of(StructurePoolElement.single(name("igloo/road_4"), processors.getOrThrow(IGLOO_ROAD_PROCESSOR)), 4),
                Pair.of(StructurePoolElement.single(name("igloo/road_5"), processors.getOrThrow(IGLOO_ROAD_PROCESSOR)), 1)), StructureTemplatePool.Projection.TERRAIN_MATCHING));


        context.register(YETI, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("igloo/mobs/yeti")), 3),
                Pair.of(StructurePoolElement.single(name("igloo/mobs/yeti_child")), 1)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(UNDER_HIDEOUT_ENTRANCE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("under_hideout/room/entrance")), 1)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(UNDER_HIDEOUT_ROOM, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("under_hideout/room/room_1")), 2),
                Pair.of(StructurePoolElement.single(name("under_hideout/room/room_farm")), 3),
                Pair.of(StructurePoolElement.single(name("under_hideout/room/room_rest")), 3)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(UNDER_HIDEOUT_ROOM2, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("under_hideout/room2/spawner")), 1)
        ), StructureTemplatePool.Projection.RIGID));


        context.register(UNDER_HIDEOUT_CORRIDOR, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("under_hideout/corridor/corridor_2")), 5),
                Pair.of(StructurePoolElement.single(name("under_hideout/corridor/corridor_3")), 3)), StructureTemplatePool.Projection.RIGID));


    }

    public static void bootstrapProcessors(BootstrapContext<StructureProcessorList> context) {
        context.register(IGLOO_ROAD_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new RuleProcessor(
                        ImmutableList.of(
                                new ProcessorRule(new BlockMatchTest(FrostBlocks.FRIGID_STONE.get()), new BlockMatchTest(Blocks.WATER), Blocks.WATER.defaultBlockState()),
                                new ProcessorRule(new RandomBlockMatchTest(FrostBlocks.FRIGID_STONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState()),
                                new ProcessorRule(new BlockMatchTest(FrostBlocks.FROZEN_GRASS_BLOCK.get()), new BlockMatchTest(Blocks.WATER), Blocks.WATER.defaultBlockState()))))));
    }

    private static String name(String name) {
        return ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name).toString();
    }
}