package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FrostStructures {
    public static final ResourceKey<Structure> IGLOO = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo"));
    public static final ResourceKey<Structure> FROST_CASTLE = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle"));

    public static final ResourceKey<StructureSet> IGLOO_SET = ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo"));
    public static final ResourceKey<StructureSet> FROST_CASTLE_SET = ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle"));

    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_ENTRANCE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/entrance"));
    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_BRIDGE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/bridge"));
    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_BRIDGE_END = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/bridge/bridge_end"));
    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_MAIN = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/main"));

    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_LESSER_WARRIOR = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/mobs/lesser_warrior"));
    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_WARRIOR = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/mobs/warrior"));
    public static final ResourceKey<StructureTemplatePool> FROST_CASTLE_WALKER = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/mobs/walker"));


    public static final ResourceKey<StructureTemplatePool> IGLOO_ROAD = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/road"));
    public static final ResourceKey<StructureTemplatePool> IGLOO_HOUSE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/igloo"));
    public static final ResourceKey<StructureTemplatePool> IGLOO_ENTRANCE = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/igloo_entrance"));

    public static final ResourceKey<StructureTemplatePool> YETI = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo/mobs/yeti"));


    public static final ResourceKey<StructureProcessorList> IGLOO_ROAD_PROCESSOR = ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "igloo_road"));
    public static final ResourceKey<StructureProcessorList> FROST_CASTLE_MAGMA_PROCESSOR = ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_castle/magma"));

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
                FROST_CASTLE,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(FrostTags.Biomes.HAS_CASTLE))
                                .generationStep(GenerationStep.Decoration.UNDERGROUND_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                                .spawnOverrides(
                                        Arrays.stream(MobCategory.values())
                                                .collect(
                                                        Collectors.toMap(
                                                                p_344252_ -> (MobCategory) p_344252_,
                                                                p_392604_ -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedList.of())
                                                        )
                                                )
                                )
                                .build(),
                        pools.getOrThrow(FROST_CASTLE_ENTRANCE),
                        Optional.empty(),
                        10,
                        ConstantHeight.of(VerticalAnchor.absolute(-46)),
                        false,
                        Optional.empty(),
                        116,
                        List.of(),
                        new DimensionPadding(0),
                        LiquidSettings.IGNORE_WATERLOGGING
                )
        );
    }

    public static void bootstrapSets(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(IGLOO_SET, new StructureSet(List.of(StructureSet.entry(structures.getOrThrow(IGLOO), 1))
                , new RandomSpreadStructurePlacement(26, 8, RandomSpreadType.LINEAR, 16324620)));
        context.register(FROST_CASTLE_SET, new StructureSet(List.of(StructureSet.entry(structures.getOrThrow(FROST_CASTLE), 1))
                , new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14284620)));
    }

    public static void bootstrapPools(BootstrapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        HolderGetter<StructureTemplatePool> holdergetter2 = context.lookup(Registries.TEMPLATE_POOL);

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

        context.register(FROST_CASTLE_ENTRANCE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/entrance/entrance"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 1)
        ), StructureTemplatePool.Projection.RIGID));

        Holder<StructureTemplatePool> holder7 = holdergetter2.getOrThrow(FROST_CASTLE_BRIDGE_END);

        context.register(FROST_CASTLE_MAIN, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/main_tower"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(FROST_CASTLE_BRIDGE_END, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/bridge/bridge_end"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(FROST_CASTLE_BRIDGE, new StructureTemplatePool(holder7, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/bridge/bridge_watchtower"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 2),
                Pair.of(StructurePoolElement.single(name("frost_castle/bridge/bridge_1"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 5),
                Pair.of(StructurePoolElement.single(name("frost_castle/bridge/mini_watch_tower"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 2),
                Pair.of(StructurePoolElement.single(name("frost_castle/bridge/fogotten_legend"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 1),
                Pair.of(StructurePoolElement.single(name("frost_castle/bridge/t_bridge"), processors.getOrThrow(FROST_CASTLE_MAGMA_PROCESSOR)), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(FROST_CASTLE_LESSER_WARRIOR, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/mobs/lesser_warrior")), 1),
                Pair.of(StructurePoolElement.single(name("frost_castle/mobs/empty")), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(FROST_CASTLE_WARRIOR, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/mobs/lesser_warrior")), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(FROST_CASTLE_WALKER, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(name("frost_castle/mobs/walker")), 1),
                Pair.of(StructurePoolElement.single(name("frost_castle/mobs/empty")), 2)
        ), StructureTemplatePool.Projection.RIGID));
    }

    public static void bootstrapProcessors(BootstrapContext<StructureProcessorList> context) {
        context.register(IGLOO_ROAD_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new RuleProcessor(
                        ImmutableList.of(
                                new ProcessorRule(new BlockMatchTest(FrostBlocks.FRIGID_STONE.get()), new BlockMatchTest(Blocks.WATER), Blocks.WATER.defaultBlockState()),
                                new ProcessorRule(new RandomBlockMatchTest(FrostBlocks.FRIGID_STONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState()),
                                new ProcessorRule(new BlockMatchTest(FrostBlocks.FROZEN_GRASS_BLOCK.get()), new BlockMatchTest(Blocks.WATER), Blocks.WATER.defaultBlockState()))))));

        context.register(FROST_CASTLE_MAGMA_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new RuleProcessor(
                        ImmutableList.of(
                                new ProcessorRule(new RandomBlockMatchTest(FrostBlocks.PERMA_MAGMA.get(), 1F), AlwaysTrueTest.INSTANCE, new LinearPosTest(1, 0.25F, 0, 500), Blocks.LAVA.defaultBlockState())
                        )))));

    }

    private static String name(String name) {
        return ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name).toString();
    }
}