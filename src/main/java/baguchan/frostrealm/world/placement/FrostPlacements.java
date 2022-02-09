package baguchan.frostrealm.world.placement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;

public class FrostPlacements {
	public static final PlacedFeature FROSTROOT_TREES_FOREST = PlacementUtils.register(prefix("frostroot_tree_forest"), FrostConfiguredFeatures.FROSTROOT_TREE.placed(treePlacement(PlacementUtils.countExtra(10, 0.1F, 1), FrostBlocks.FROSTROOT_SAPLING)));
	public static final PlacedFeature FROSTROOT_TREES_PLAINS = PlacementUtils.register(prefix("frostroot_tree_plains"), FrostConfiguredFeatures.FROSTROOT_TREE.placed(treePlacement(PlacementUtils.countExtra(0, 0.01F, 1), FrostBlocks.FROSTROOT_SAPLING)));

	public static final PlacedFeature FROZEN_TREES_JUNGLE = PlacementUtils.register(prefix("frozen_tree_jungle"), FrostConfiguredFeatures.FROZEN_TREE.placed(treePlacement(PlacementUtils.countExtra(5, 0.1F, 1), FrostBlocks.FROZEN_SAPLING)));

	public static final PlacedFeature PATCH_TUNDRA_GRASS = PlacementUtils.register(prefix("patch_tundra_grass"), FrostConfiguredFeatures.PATCH_TUNDRA_GRASS.placed(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

	public static final PlacedFeature PATCH_BEAR_BERRY = PlacementUtils.register(prefix("patch_bear_berry"), FrostConfiguredFeatures.PATCH_BEARBERRY.placed(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));


	public static final PlacedFeature PATCH_ARTIC_POPPY = PlacementUtils.register(prefix("patch_artic_poppy"), FrostConfiguredFeatures.ARCTIC_POPPY.placed(CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
	public static final PlacedFeature PATCH_ARTIC_WILLOW = PlacementUtils.register(prefix("patch_artic_willow"), FrostConfiguredFeatures.ARCTIC_WILLOW.placed(CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

	public static final PlacedFeature PATCH_TUNDRA_ROCK = PlacementUtils.register(prefix("patch_tundra_rock"), FrostConfiguredFeatures.TUNDRA_ROCK.placed(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
	public static final PlacedFeature PATCH_TUNDRA_MOSSY_ROCK = PlacementUtils.register(prefix("patch_tundra_mossy_rock"), FrostConfiguredFeatures.TUNDRA_MOSSY_ROCK.placed(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

	public static final PlacedFeature LAVA_DELTA = PlacementUtils.register(prefix("delta"), NetherFeatures.DELTA.placed(CountOnEveryLayerPlacement.of(8), InSquarePlacement.spread(), BiomeFilter.biome()));

	public static final PlacedFeature SPRING_LAVA = PlacementUtils.register(prefix("spring_lava"), FrostConfiguredFeatures.SPRING_LAVA.placed(CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(8), 8)), BiomeFilter.biome()));
	public static final PlacedFeature SPRING_LAVA_HOTROCK_EXTRA = PlacementUtils.register(prefix("spring_lava_hotrock_extra"), FrostConfiguredFeatures.SPRING_LAVA.placed(CountPlacement.of(10), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.aboveBottom(64), VerticalAnchor.top(), 8)), BiomeFilter.biome()));
	public static final PlacedFeature SPRING_WATER = PlacementUtils.register(prefix("spring_water"), FrostConfiguredFeatures.SPRING_WATER.placed(CountPlacement.of(25), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)), BiomeFilter.biome()));
	public static final PlacedFeature SPRING_WATER_EXTRA = PlacementUtils.register(prefix("spring_water_extra"), FrostConfiguredFeatures.SPRING_WATER.placed(CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)), BiomeFilter.biome()));


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	public static void init() {

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
