package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class FrostConfiguredFeatures {
	public static final RuleTest FRIGID_ORE_REPLACEABLES = new BlockMatchTest(FrostBlocks.FRIGID_STONE);

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_FROST_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.FROST_CRYSTAL_ORE.defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_GLIMMERROCK_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.GLIMMERROCK_ORE.defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_STARDUST_CRYSRTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.STARDUST_CRYSTAL_ORE.defaultBlockState()));

	public static final ConfiguredFeature<?, ?> ORE_FROST_CRYSTAL = register(prefix("ore_frost_crystal"), Feature.ORE.configured(new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 20)));
	public static final ConfiguredFeature<?, ?> ORE_FROST_CRYSTAL_BURIED = register(prefix("ore_frost_crystal_buried"), Feature.ORE.configured(new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 20, 0.5F)));
	public static final ConfiguredFeature<?, ?> ORE_GLIMMERROCK = register(prefix("ore_glimmerrock"), Feature.ORE.configured(new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 10)));
	public static final ConfiguredFeature<?, ?> ORE_GLIMMERROCK_SMALL = register(prefix("ore_glimmerrock_small"), Feature.ORE.configured(new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 4)));
	public static final ConfiguredFeature<?, ?> ORE_STARDUST_CRYSTAL = register(prefix("ore_stardust_crystal"), Feature.ORE.configured(new OreConfiguration(ORE_STARDUST_CRYSRTAL_TARGET_LIST, 8)));


	public static final ConfiguredFeature<?, ?> PATCH_TUNDRA_GRASS = register(prefix("patch_tundra_grass"), Feature.RANDOM_PATCH.configured(grassPatch(BlockStateProvider.simple(FrostBlocks.COLD_GRASS), 32)));

	public static final ConfiguredFeature<?, ?> PATCH_BEARBERRY = register(prefix("patch_bearberry"), Feature.RANDOM_PATCH.configured(grassPatch(BlockStateProvider.simple(FrostBlocks.BEARBERRY_BUSH), 32)));

	public static final ConfiguredFeature<?, ?> ARCTIC_POPPY = register(prefix("patch_arctic_poppy"), Feature.FLOWER.configured(grassPatch(BlockStateProvider.simple(FrostBlocks.ARCTIC_POPPY), 32)));
	public static final ConfiguredFeature<?, ?> ARCTIC_WILLOW = register(prefix("patch_arctic_willow"), Feature.FLOWER.configured(grassPatch(BlockStateProvider.simple(FrostBlocks.ARCTIC_WILLOW), 32)));

	public static final ConfiguredFeature<BlockStateConfiguration, ?> TUNDRA_ROCK = register(prefix("tundra_rock"), FrostFeatures.BIG_ROCK.configured(new BlockStateConfiguration(FrostBlocks.FRIGID_STONE.defaultBlockState())));
	public static final ConfiguredFeature<BlockStateConfiguration, ?> TUNDRA_MOSSY_ROCK = register(prefix("tundra_mossy_rock"), FrostFeatures.BIG_ROCK.configured(new BlockStateConfiguration(FrostBlocks.FRIGID_STONE_MOSSY.defaultBlockState())));

	public static final ConfiguredFeature<RandomFeatureConfiguration, ?> FROSTROOT_TREE = register(prefix("frostroot_tree"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(FrostTreeFeatures.FROST_TREE_BIG.placed(), 0.33333334F)), FrostTreeFeatures.FROST_TREE.placed())));

	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}

	private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> p_243968_1_) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, p_243968_1_);
	}


	private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
		return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(p_195203_)).onlyWhenEmpty());
	}

	public static void init() {
	}
}