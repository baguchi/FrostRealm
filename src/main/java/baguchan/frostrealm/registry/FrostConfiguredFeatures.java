package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.OptionalInt;

public class FrostConfiguredFeatures {
	public static final RuleTest FRIGID_ORE_REPLACEABLES = new BlockMatchTest(FrostBlocks.FRIGID_STONE.get());

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_FROST_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.FROST_CRYSTAL_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_GLIMMERROCK_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.GLIMMERROCK_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_STARDUST_CRYSRTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.STARDUST_CRYSTAL_ORE.get().defaultBlockState()));


	public static final ConfiguredFeature<?, ?> FROST_CRYSTAL = register(prefix("frost_crystal_ore"), Feature.ORE.configured(new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 12)).rangeTriangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(248)).squared().count(20));
	public static final ConfiguredFeature<?, ?> GLIMMERROCK_ORE = register(prefix("glimmerrock_ore"), Feature.ORE.configured(new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 8)).rangeTriangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(32)).squared().count(12));
	public static final ConfiguredFeature<?, ?> STARDUST_CRYSRTAL_ORE = register(prefix("stardust_crystal_ore"), Feature.ORE.configured(new OreConfiguration(ORE_STARDUST_CRYSRTAL_TARGET_LIST, 8)).rangeTriangle(VerticalAnchor.absolute(32), VerticalAnchor.absolute(220)).squared().count(8));
	public static final ConfiguredFeature<?, ?> FROST_CRYSTAL_EXTRA = register(prefix("frost_crystal_ore_extra"), Feature.ORE.configured(new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 12, 0.7F)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(248)).squared().count(5));


	public static final ConfiguredFeature<TreeConfiguration, ?> FROSTROOT = register(prefix("frostroot"), Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(FrostBlocks.FROSTROOT_LOG.get().defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(FrostBlocks.FROSTROOT_LEAVES.get().defaultBlockState()), new SimpleStateProvider(FrostBlocks.FROSTROOT_SAPLING.get().defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> FANCY_FROSTROOT = register(prefix("fancy_frostroot"), Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(FrostBlocks.FROSTROOT_LOG.get().defaultBlockState()), new FancyTrunkPlacer(3, 11, 0), new SimpleStateProvider(FrostBlocks.FROSTROOT_LEAVES.get().defaultBlockState()), new SimpleStateProvider(FrostBlocks.FROSTROOT_SAPLING.get().defaultBlockState()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().build()));

	public static final ConfiguredFeature<?, ?> FROSTROOT_FOREST = register(prefix("frostroot_forest_vegitation"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(FANCY_FROSTROOT.weighted(0.1F)), FROSTROOT)).decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> FROSTROOT_PLAIN = register(prefix("frostroot_plain_vegitation"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(FANCY_FROSTROOT.weighted(0.33333334F)), FROSTROOT)).decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.05F, 1))));

	public static final ConfiguredFeature<?, ?> POWDER_SNOW = register(prefix("powder_snow"), (Feature.DELTA_FEATURE.configured(new DeltaFeatureConfiguration(Blocks.POWDER_SNOW.defaultBlockState(), Blocks.SNOW_BLOCK.defaultBlockState(), UniformInt.of(3, 7), UniformInt.of(0, 2))).decorated(FeatureDecorator.COUNT_MULTILAYER.configured(new CountConfiguration(15)))));

	public static final ConfiguredFeature<?, ?> LARGE_ICE_FEATURE = register(prefix("large_ice"), FrostFeatures.LARGE_ICE.get().configured(new LargeDripstoneConfiguration(30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.6F)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(32)).squared().count(UniformInt.of(2, 10)));
	public static final ConfiguredFeature<?, ?> SMALL_ICE_FEATURE = register(prefix("small_ice"), FrostFeatures.SMALL_ICE.get().configured(new SmallDripstoneConfiguration(5, 10, 2, 0.2F)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(59)).squared().count(UniformInt.of(40, 120)));

	public static final ConfiguredFeature<?, ?> RARE_SMALL_ICE_FEATURE = register(prefix("rare_small_ice"), FrostFeatures.SMALL_ICE.get().configured(new SmallDripstoneConfiguration(5, 10, 2, 0.2F)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(32)).squared().count(UniformInt.of(40, 80)).rarity(10));

	public static final ConfiguredFeature<?, ?> FROSTROOT_CAVE = register(prefix("frostroot_cave_vegitation"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(FANCY_FROSTROOT.weighted(0.1F)), FROSTROOT)).decorated(FeatureDecorator.CAVE_SURFACE.configured(new CaveDecoratorConfiguration(CaveSurface.FLOOR, 12))).range(Features.Decorators.RANGE_BOTTOM_TO_60).squared().count(5));

	public static final ConfiguredFeature<SimpleBlockConfiguration, ?> TUNDRA_VEGETATION = register(prefix("tundra_vegetation"), Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(new WeightedStateProvider(weightedBlockStateBuilder().add(Blocks.GRASS.defaultBlockState(), 50).add(Blocks.TALL_GRASS.defaultBlockState(), 10).add(FrostBlocks.VIGOROSHROOM.get().defaultBlockState(), 5)))));
	public static final ConfiguredFeature<VegetationPatchConfiguration, ?> TUNDRA_PATCH = register(prefix("tundra_patch"), Feature.VEGETATION_PATCH.configured(new VegetationPatchConfiguration(FrostTags.Blocks.TUNDRA_REPLACEABLE.getName(), new SimpleStateProvider(FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState()), () -> {
		return TUNDRA_VEGETATION;
	}, CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F)));

	public static final ConfiguredFeature<?, ?> TUNDRA_BIG_ROCK = register(prefix("tundra_big_rock"), FrostFeatures.BIG_ROCK.get().configured(new BlockStateConfiguration(FrostBlocks.FRIGID_STONE.get().defaultBlockState())).decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(2));

	public static final ConfiguredFeature<?, ?> TUNDRA_CAVES_VEGETATION = register(prefix("tundra_caves_vegetation"), TUNDRA_PATCH.decorated(FeatureDecorator.CAVE_SURFACE.configured(new CaveDecoratorConfiguration(CaveSurface.FLOOR, 12))).range(Features.Decorators.RANGE_BOTTOM_TO_60).squared().count(40));

	public static final RandomPatchConfiguration DEFAULT_COLD_GRASS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(FrostBlocks.COLD_GRASS.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(32).build();

	public static final ConfiguredFeature<?, ?> PATCH_GRASS_PLAIN = register(prefix("patch_grass_plain"), Feature.RANDOM_PATCH.configured(DEFAULT_COLD_GRASS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 5, 10))));

	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}

	private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> p_243968_1_) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, p_243968_1_);
	}

	static SimpleWeightedRandomList.Builder<BlockState> weightedBlockStateBuilder() {
		return SimpleWeightedRandomList.builder();
	}

	public static void init() {
	}
}