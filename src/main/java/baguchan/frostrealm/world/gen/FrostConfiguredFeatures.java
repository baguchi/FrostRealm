package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.block.crop.BearBerryBushBlock;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class FrostConfiguredFeatures {
	public static final RuleTest FRIGID_ORE_REPLACEABLES = new BlockMatchTest(FrostBlocks.FRIGID_STONE.get());

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_FROST_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.FROST_CRYSTAL_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_GLIMMERROCK_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.GLIMMERROCK_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_ASTRIUM_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.ASTRIUM_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_STARDUST_CRYSRTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(FRIGID_ORE_REPLACEABLES, FrostBlocks.STARDUST_CRYSTAL_ORE.get().defaultBlockState()));

	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FROST_CRYSTAL = registerKey("ore_frost_crystal");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FROST_CRYSTAL_BURIED = registerKey("ore_frost_crystal_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GLIMMERROCK = registerKey("ore_glimmerrock");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GLIMMERROCK_SMALL = registerKey("ore_glimmerrock_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASTRIUM = registerKey("ore_astrium");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASTRIUM_SMALL = registerKey("ore_astrium_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_STARDUST_CRYSTAL = registerKey("ore_stardust_crystal");


	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TUNDRA_GRASS = registerKey("patch_tundra_grass");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BEARBERRY = registerKey("patch_bearberry");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_VIGOROSHROOM = registerKey("patch_vigoroshroom");


	public static final ResourceKey<ConfiguredFeature<?, ?>> ARCTIC_POPPY = registerKey("patch_arctic_poppy");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ARCTIC_WILLOW = registerKey("patch_arctic_willow");

	public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_ROCK = registerKey("tundra_rock");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_MOSSY_ROCK = registerKey("tundra_mossy_rock");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STAR_DUST_CLUSTER = registerKey("star_dust_cluster");

    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_SPIKE = registerKey("stone_spike");


	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_LAVA = registerKey("spring_lava_hot_rock");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_WATER = registerKey("spring_water_fall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> HOT_SPRING_DELTA = registerKey("hot_spring_delta");

	public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_CLUSTER = registerKey("ice_cluster");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ICE = registerKey("large_ice");

	public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTROOT_TREE = registerKey("frostroot_trees");

	public static final ResourceKey<ConfiguredFeature<?, ?>> LOG = registerKey("log");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CHAIN = registerKey("chain");

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, FrostRealm.prefix(name));
	}

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

		FeatureUtils.register(context, ORE_FROST_CRYSTAL, Feature.ORE, new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 20));
		FeatureUtils.register(context, ORE_FROST_CRYSTAL_BURIED, Feature.ORE, new OreConfiguration(ORE_FROST_CRYSTAL_TARGET_LIST, 20, 0.5F));
		FeatureUtils.register(context, ORE_GLIMMERROCK, Feature.ORE, new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 10));
		FeatureUtils.register(context, ORE_GLIMMERROCK_SMALL, Feature.ORE, new OreConfiguration(ORE_GLIMMERROCK_TARGET_LIST, 4));
		FeatureUtils.register(context, ORE_ASTRIUM, Feature.ORE, new OreConfiguration(ORE_ASTRIUM_TARGET_LIST, 12));
		FeatureUtils.register(context, ORE_ASTRIUM_SMALL, Feature.ORE, new OreConfiguration(ORE_ASTRIUM_TARGET_LIST, 4));
		FeatureUtils.register(context, ORE_STARDUST_CRYSTAL, Feature.ORE, new OreConfiguration(ORE_STARDUST_CRYSRTAL_TARGET_LIST, 8));


		FeatureUtils.register(context, PATCH_TUNDRA_GRASS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(FrostBlocks.COLD_GRASS.get()), 32));

		FeatureUtils.register(context, PATCH_BEARBERRY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(FrostBlocks.BEARBERRY_BUSH.get().defaultBlockState().setValue(BearBerryBushBlock.AGE, 3)), 32));

		FeatureUtils.register(context, PATCH_VIGOROSHROOM, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(FrostBlocks.VIGOROSHROOM.get()), 32));


		FeatureUtils.register(context, ARCTIC_POPPY, Feature.FLOWER, grassPatch(BlockStateProvider.simple(FrostBlocks.ARCTIC_POPPY.get()), 32));
		FeatureUtils.register(context, ARCTIC_WILLOW, Feature.FLOWER, grassPatch(BlockStateProvider.simple(FrostBlocks.ARCTIC_WILLOW.get()), 32));

		FeatureUtils.register(context, TUNDRA_ROCK, FrostFeatures.BIG_ROCK.get(), new BlockStateConfiguration(FrostBlocks.FRIGID_STONE.get().defaultBlockState()));
		FeatureUtils.register(context, TUNDRA_MOSSY_ROCK, FrostFeatures.BIG_ROCK.get(), new BlockStateConfiguration(FrostBlocks.FRIGID_STONE_MOSSY.get().defaultBlockState()));
		FeatureUtils.register(context, STAR_DUST_CLUSTER, FrostFeatures.SHAPE_CRYSTAL.get(), new BlockStateConfiguration(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get().defaultBlockState()));

        FeatureUtils.register(context, STONE_SPIKE, FrostFeatures.STONE_SPIKE.get());

		FeatureUtils.register(context, SPRING_LAVA, Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, FrostBlocks.FRIGID_STONE.get())));
		FeatureUtils.register(context, SPRING_WATER, Feature.SPRING, new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, FrostBlocks.FRIGID_STONE.get())));
		FeatureUtils.register(context, HOT_SPRING_DELTA, Feature.DELTA_FEATURE,
				new DeltaFeatureConfiguration(FrostBlocks.HOT_SPRING.get().defaultBlockState(), FrostBlocks.FRIGID_STONE.get().defaultBlockState(), UniformInt.of(5, 9), UniformInt.of(0, 2)));

		FeatureUtils.register(context, ICE_CLUSTER, FrostFeatures.ICE_CLUSTER.get(), new DripstoneClusterConfiguration(12, UniformInt.of(3, 6), UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F), ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
		FeatureUtils.register(context, LARGE_ICE, FrostFeatures.LARGE_ICE.get(), new LargeDripstoneConfiguration(30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.6F));


		Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(FrostTreeFeatures.FROST_TREE);
		Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(FrostTreeFeatures.FROST_TREE_BIG);

		FeatureUtils.register(context, FROSTROOT_TREE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(holder2), 0.33333334F)), PlacementUtils.inlinePlaced(holder1)));
		FeatureUtils.register(context, LOG, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(ConstantInt.of(32), BlockStateProvider.simple(FrostBlocks.FROSTROOT_LOG.get()))), Direction.DOWN, BlockPredicate.not(BlockPredicate.hasSturdyFace(new Vec3i(0, 1, 0), Direction.UP)), true));
		FeatureUtils.register(context, CHAIN, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(ConstantInt.of(32), BlockStateProvider.simple(Blocks.CHAIN))), Direction.UP, BlockPredicate.not(BlockPredicate.hasSturdyFace(new Vec3i(0, -1, 0), Direction.DOWN)), true));
	}
	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
		return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
	}

}