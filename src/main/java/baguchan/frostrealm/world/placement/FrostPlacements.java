package baguchan.frostrealm.world.placement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

import java.util.List;

public class FrostPlacements {
	private static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

	public static final ResourceKey<PlacedFeature> FROSTROOT_TREES_FOREST = registerKey("frostroot_tree_forest");
	public static final ResourceKey<PlacedFeature> FROSTROOT_TREES_PLAINS = registerKey("frostroot_tree_plains");

	public static final ResourceKey<PlacedFeature> PATCH_TUNDRA_GRASS = registerKey("patch_tundra_grass");

	public static final ResourceKey<PlacedFeature> PATCH_BEAR_BERRY = registerKey("patch_bear_berry");


	public static final ResourceKey<PlacedFeature> PATCH_ARTIC_POPPY = registerKey("patch_artic_poppy");
	public static final ResourceKey<PlacedFeature> PATCH_ARTIC_WILLOW = registerKey("patch_artic_willow");
	public static final ResourceKey<PlacedFeature> PATCH_VIGOROSHROOM = registerKey("patch_vigoroshroom");


	public static final ResourceKey<PlacedFeature> PATCH_TUNDRA_ROCK = registerKey("patch_tundra_rock");
	public static final ResourceKey<PlacedFeature> PATCH_TUNDRA_MOSSY_ROCK = registerKey("patch_tundra_mossy_rock");

	public static final ResourceKey<PlacedFeature> BIG_WARPED_ISLAND = registerKey("big_warped_island");


	public static final ResourceKey<PlacedFeature> LAVA_DELTA = registerKey("delta");

	public static final ResourceKey<PlacedFeature> SPRING_LAVA = registerKey("spring_lava");
	public static final ResourceKey<PlacedFeature> SPRING_LAVA_HOTROCK_EXTRA = registerKey("spring_lava_hotrock_extra");
	public static final ResourceKey<PlacedFeature> SPRING_WATER = registerKey("spring_water");
	public static final ResourceKey<PlacedFeature> SPRING_WATER_EXTRA = registerKey("spring_water_extra");

	public static final ResourceKey<PlacedFeature> ICE_CLUSTER = registerKey("ice_cluster");
	public static final ResourceKey<PlacedFeature> LARGE_ICE = registerKey("large_ice");

	public static final ResourceKey<PlacedFeature> LOG_PLACE = registerKey("log");
	public static final ResourceKey<PlacedFeature> CHAIN_PLACE = registerKey("chain");


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, FrostRealm.prefix(name));
	}

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

		PlacementUtils.register(context, FROSTROOT_TREES_FOREST, configuredFeature.getOrThrow(FrostConfiguredFeatures.FROSTROOT_TREE), treePlacement(PlacementUtils.countExtra(10, 0.1F, 1), FrostBlocks.FROSTROOT_SAPLING.get()));
		PlacementUtils.register(context, FROSTROOT_TREES_PLAINS, configuredFeature.getOrThrow(FrostConfiguredFeatures.FROSTROOT_TREE), treePlacement(PlacementUtils.countExtra(0, 0.01F, 1), FrostBlocks.FROSTROOT_SAPLING.get()));

		PlacementUtils.register(context, PATCH_TUNDRA_GRASS, configuredFeature.getOrThrow(FrostConfiguredFeatures.PATCH_TUNDRA_GRASS), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		PlacementUtils.register(context, PATCH_BEAR_BERRY, configuredFeature.getOrThrow(FrostConfiguredFeatures.PATCH_BEARBERRY), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());


		PlacementUtils.register(context, PATCH_ARTIC_POPPY, configuredFeature.getOrThrow(FrostConfiguredFeatures.ARCTIC_POPPY), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PATCH_ARTIC_WILLOW, configuredFeature.getOrThrow(FrostConfiguredFeatures.ARCTIC_WILLOW), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PATCH_VIGOROSHROOM, configuredFeature.getOrThrow(FrostConfiguredFeatures.PATCH_VIGOROSHROOM), CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());


		PlacementUtils.register(context, PATCH_TUNDRA_ROCK, configuredFeature.getOrThrow(FrostConfiguredFeatures.TUNDRA_ROCK), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PATCH_TUNDRA_MOSSY_ROCK, configuredFeature.getOrThrow(FrostConfiguredFeatures.TUNDRA_MOSSY_ROCK), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		PlacementUtils.register(context, BIG_WARPED_ISLAND, configuredFeature.getOrThrow(FrostConfiguredFeatures.BIG_WARPED_ISLAND), RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.countExtra(1, 0.25F, 1), HeightRangePlacement.uniform(VerticalAnchor.absolute(80), VerticalAnchor.top()), BiomeFilter.biome());


		PlacementUtils.register(context, LAVA_DELTA, configuredFeature.getOrThrow(NetherFeatures.DELTA), CountOnEveryLayerPlacement.of(8), BiomeFilter.biome());

		PlacementUtils.register(context, SPRING_LAVA, configuredFeature.getOrThrow(FrostConfiguredFeatures.SPRING_LAVA), CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(8), 8)), BiomeFilter.biome());
		PlacementUtils.register(context, SPRING_LAVA_HOTROCK_EXTRA, configuredFeature.getOrThrow(FrostConfiguredFeatures.SPRING_LAVA), CountPlacement.of(10), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.aboveBottom(64), VerticalAnchor.top(), 8)), BiomeFilter.biome());
		PlacementUtils.register(context, SPRING_WATER, configuredFeature.getOrThrow(FrostConfiguredFeatures.SPRING_WATER), CountPlacement.of(25), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)), BiomeFilter.biome());
		PlacementUtils.register(context, SPRING_WATER_EXTRA, configuredFeature.getOrThrow(FrostConfiguredFeatures.SPRING_WATER), CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)), BiomeFilter.biome());

		PlacementUtils.register(context, ICE_CLUSTER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ICE_CLUSTER), CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
		PlacementUtils.register(context, LARGE_ICE, configuredFeature.getOrThrow(FrostConfiguredFeatures.LARGE_ICE), CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

		PlacementUtils.register(context, LOG_PLACE, configuredFeature.getOrThrow(FrostConfiguredFeatures.LOG), EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.anyOf(BlockPredicate.hasSturdyFace(new Vec3i(0, 1, 0), Direction.UP), BlockPredicate.hasSturdyFace(Direction.UP)), 32));
		PlacementUtils.register(context, CHAIN_PLACE, configuredFeature.getOrThrow(FrostConfiguredFeatures.CHAIN), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.anyOf(BlockPredicate.hasSturdyFace(new Vec3i(0, -1, 0), Direction.DOWN), BlockPredicate.hasSturdyFace(Direction.DOWN)), 32));

	}

	private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier p_195485_) {
		return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(TREE_THRESHOLD).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome());
	}

	public static List<PlacementModifier> treePlacement(PlacementModifier p_195480_) {
		return treePlacementBase(p_195480_).build();
	}

	public static List<PlacementModifier> treePlacement(PlacementModifier p_195482_, Block p_195483_) {
		return treePlacementBase(p_195482_).add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(p_195483_.defaultBlockState(), BlockPos.ZERO))).build();
	}

	private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
		return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
		return orePlacement(CountPlacement.of(p_195344_), p_195345_);
	}

	private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
		return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
	}
}
