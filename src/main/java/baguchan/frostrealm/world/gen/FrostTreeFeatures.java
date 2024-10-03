package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

public class FrostTreeFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> FROST_TREE = registerKey("frostroot_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FROST_TREE_BIG = registerKey("frostroot_tree_big");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTBITE_TREE = registerKey("frostbite_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTBITE_TREE_BIG = registerKey("frostbite_tree_big");
	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, FrostRealm.prefix(name));
	}

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		FeatureUtils.register(context, FROST_TREE, Feature.TREE, createFrostRoot().build());
		FeatureUtils.register(context, FROST_TREE_BIG, Feature.TREE, createFancyFrostRoot().build());
		FeatureUtils.register(context, FROSTBITE_TREE, Feature.TREE, createFrostBite().build());
		FeatureUtils.register(context, FROSTBITE_TREE_BIG, Feature.TREE, createFancyFrostBite().build());
	}


	private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
	}


    private static TreeConfiguration.TreeConfigurationBuilder createFrostRoot() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(FrostBlocks.FROSTROOT_LOG.get()), new FancyTrunkPlacer(3, 3, 0), BlockStateProvider.simple(FrostBlocks.FROSTROOT_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT.get()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyFrostRoot() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(FrostBlocks.FROSTROOT_LOG.get()), new ForkingTrunkPlacer(4, 11, 0), BlockStateProvider.simple(FrostBlocks.FROSTROOT_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT.get()));
    }

	private static TreeConfiguration.TreeConfigurationBuilder createFrostBite() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(FrostBlocks.FROSTBITE_LOG.get()), new ForkingTrunkPlacer(8, 4, 0), BlockStateProvider.simple(FrostBlocks.FROSTBITE_LEAVES.get()), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		)).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT.get()));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFancyFrostBite() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(FrostBlocks.FROSTBITE_LOG.get()), new CherryTrunkPlacer(
				10,
				6,
				0,
				new WeightedListInt(
						SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
				),
				UniformInt.of(2, 4),
				UniformInt.of(-4, -3),
				UniformInt.of(-1, 0)
		), BlockStateProvider.simple(FrostBlocks.FROSTBITE_LEAVES.get()), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT.get())));
	}
}
