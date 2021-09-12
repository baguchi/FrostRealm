package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.Locale;
import java.util.OptionalInt;

public class FrostConfiguredFeatures {
	public static final RuleTest FRIGID_ORE_REPLACEABLES = new BlockMatchTest(FrostBlocks.FRIGID_STONE.get());

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_FROST_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.FROST_CRYSTAL_ORE.get().defaultBlockState()));

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_GLIMMERROCK_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.GLIMMERROCK_ORE.get().defaultBlockState()));

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_STARDUST_CRYSRTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.STARDUST_CRYSTAL_ORE.get().defaultBlockState()));


	public static final ConfiguredFeature<?, ?> FROST_CRYSTAL = register(prefix("frost_crystal_ore"), Feature.ORE.configured(new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 12, 0.5F)).rangeTriangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(248)).squared().count(20));
	public static final ConfiguredFeature<?, ?> GLIMMERROCK_ORE = register(prefix("glimmerrock_ore"), Feature.ORE.configured(new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 8, 0.7F)).rangeTriangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(120)).squared().count(12));
	public static final ConfiguredFeature<?, ?> STARDUST_CRYSRTAL_ORE = register(prefix("stardust_crystal_ore"), Feature.ORE.configured(new OreConfiguration(ORE_STARDUST_CRYSRTAL_TARGET_LIST, 8, 0.7F)).rangeTriangle(VerticalAnchor.absolute(32), VerticalAnchor.absolute(160)).squared().count(8));

	public static final ConfiguredFeature<TreeConfiguration, ?> FROSTROOT = register(prefix("frostroot"), Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(FrostBlocks.FROSTROOT_LOG.get().defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(FrostBlocks.FROSTROOT_LEAVES.get().defaultBlockState()), new SimpleStateProvider(FrostBlocks.FROSTROOT_SAPLING.get().defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> FANCY_FROSTROOT = register(prefix("fancy_frostroot"), Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(FrostBlocks.FROSTROOT_LOG.get().defaultBlockState()), new FancyTrunkPlacer(3, 11, 0), new SimpleStateProvider(FrostBlocks.FROSTROOT_LEAVES.get().defaultBlockState()), new SimpleStateProvider(FrostBlocks.FROSTROOT_SAPLING.get().defaultBlockState()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().build()));

	public static final ConfiguredFeature<?, ?> FROSTROOT_FOREST = register(prefix("frostroot_forest_vegitation"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(FANCY_FROSTROOT.weighted(0.1F)), FROSTROOT)).decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> FROSTROOT_PLAIN = register(prefix("frostroot_plain_vegitation"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(FANCY_FROSTROOT.weighted(0.33333334F)), FROSTROOT)).decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.05F, 1))));

	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}

	private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> p_243968_1_) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, p_243968_1_);
	}

	public static void init() {
	}
}