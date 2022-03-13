package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class FrostConfiguredFeatures {
	public static final RuleTest FRIGID_ORE_REPLACEABLES = new BlockMatchTest(FrostBlocks.FRIGID_STONE.get());

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_FROST_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.FROST_CRYSTAL_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_GLIMMERROCK_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.GLIMMERROCK_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_STARDUST_CRYSRTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.STARDUST_CRYSTAL_ORE.get().defaultBlockState()));

	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_FROST_CRYSTAL = FeatureUtils.register(prefix("ore_frost_crystal"), Feature.ORE, new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 20));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_FROST_CRYSTAL_BURIED = FeatureUtils.register(prefix("ore_frost_crystal_buried"), Feature.ORE, new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 20, 0.5F));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GLIMMERROCK = FeatureUtils.register(prefix("ore_glimmerrock"), Feature.ORE, new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 10));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GLIMMERROCK_SMALL = FeatureUtils.register(prefix("ore_glimmerrock_small"), Feature.ORE, new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 4));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_STARDUST_CRYSTAL = FeatureUtils.register(prefix("ore_stardust_crystal"), Feature.ORE, new OreConfiguration(ORE_STARDUST_CRYSRTAL_TARGET_LIST, 8));


	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_TUNDRA_GRASS = FeatureUtils.register(prefix("patch_tundra_grass"), Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(FrostBlocks.COLD_GRASS.get()), 32));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BEARBERRY = FeatureUtils.register(prefix("patch_bearberry"), Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(FrostBlocks.BEARBERRY_BUSH.get()), 32));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ARCTIC_POPPY = FeatureUtils.register(prefix("patch_arctic_poppy"), Feature.FLOWER, grassPatch(BlockStateProvider.simple(FrostBlocks.ARCTIC_POPPY.get()), 32));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ARCTIC_WILLOW = FeatureUtils.register(prefix("patch_arctic_willow"), Feature.FLOWER, grassPatch(BlockStateProvider.simple(FrostBlocks.ARCTIC_WILLOW.get()), 32));

	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TUNDRA_ROCK = FeatureUtils.register(prefix("tundra_rock"), FrostFeatures.BIG_ROCK, new BlockStateConfiguration(FrostBlocks.FRIGID_STONE.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TUNDRA_MOSSY_ROCK = FeatureUtils.register(prefix("tundra_mossy_rock"), FrostFeatures.BIG_ROCK, new BlockStateConfiguration(FrostBlocks.FRIGID_STONE_MOSSY.get().defaultBlockState()));

	public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> SPRING_LAVA = FeatureUtils.register(prefix("spring_lava_hot_rock"), Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, FrostBlocks.FRIGID_STONE.get())));
	public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> SPRING_WATER = FeatureUtils.register(prefix("spring_wate_fall"), Feature.SPRING, new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, FrostBlocks.FRIGID_STONE.get())));

	public static final Holder<PlacedFeature> FROSTROOT_CHECKED = PlacementUtils.register(prefix("frostroot_checked"), FrostTreeFeatures.FROST_TREE, PlacementUtils.filteredByBlockSurvival(FrostBlocks.FROSTROOT_SAPLING.get()));
	public static final Holder<PlacedFeature> FROZEN_CHECKED = PlacementUtils.register(prefix("frozen_checked"), FrostTreeFeatures.FROZEN_TREE, PlacementUtils.filteredByBlockSurvival(FrostBlocks.FROZEN_SAPLING.get()));


	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FROSTROOT_TREE = FeatureUtils.register(prefix("frostroot_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FrostTreeFeatures.FROST_TREE_BIG), 0.33333334F)), FROSTROOT_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FROZEN_TREE = FeatureUtils.register(prefix("frozen_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FrostTreeFeatures.MEGA_FROZEN_TREE), 0.4F)), FROZEN_CHECKED));


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
		return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
	}

}