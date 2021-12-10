package baguchan.frostrealm.world.placement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;

public class FrostPlacements {
	public static final PlacedFeature FROSTROOT_TREES_FOREST = register(prefix("frostroot_tree_forest"), FrostConfiguredFeatures.FROSTROOT_TREE.placed(treePlacement(PlacementUtils.countExtra(10, 0.1F, 1), FrostBlocks.FROSTROOT_SAPLING)));
	public static final PlacedFeature FROSTROOT_TREES_PLAINS = register(prefix("frostroot_tree_plains"), FrostConfiguredFeatures.FROSTROOT_TREE.placed(treePlacement(PlacementUtils.countExtra(0, 0.01F, 1), FrostBlocks.FROSTROOT_SAPLING)));

	public static final PlacedFeature PATCH_TUNDRA_GRASS = register(prefix("patch_tundra_grass"), FrostConfiguredFeatures.PATCH_TUNDRA_GRASS.placed(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

	public static final PlacedFeature PATCH_BEAR_BERRY = register(prefix("patch_bear_berry"), FrostConfiguredFeatures.PATCH_BEARBERRY.placed(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));


	public static final PlacedFeature PATCH_ARTIC_POPPY = register(prefix("patch_artic_poppy"), FrostConfiguredFeatures.ARCTIC_POPPY.placed(CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
	public static final PlacedFeature PATCH_ARTIC_WILLOW = register(prefix("patch_artic_willow"), FrostConfiguredFeatures.ARCTIC_WILLOW.placed(CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	public static void init() {

	}

	public static PlacedFeature register(String p_195369_, PlacedFeature p_195370_) {
		return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, p_195369_, p_195370_);
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
