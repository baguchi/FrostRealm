package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

public class FrostTreeFeatures {

	public static final ConfiguredFeature<TreeConfiguration, ?> FROST_TREE = register(prefix("frostroot_tree"), Feature.TREE.configured(createFrostRoot().build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> FROST_TREE_BIG = register(prefix("frostroot_tree_big"), Feature.TREE.configured(createFancyFrostRoot().build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> FROZEN_TREE = register(prefix("frozen_tree"), Feature.TREE.configured(createFrozen().build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> MEGA_FROZEN_TREE = register(prefix("mega_frozen_tree"), Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(FrostBlocks.FROZEN_LOG), new MegaJungleTrunkPlacer(10, 2, 19), BlockStateProvider.simple(FrostBlocks.FROZEN_LEAVES), new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, LeaveVineDecorator.INSTANCE)).dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT)).build()));


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String p_195006_, ConfiguredFeature<FC, ?> p_195007_) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, p_195006_, p_195007_);
	}

	private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFrostRoot() {
		return createStraightBlobTree(FrostBlocks.FROSTROOT_LOG, FrostBlocks.FROSTROOT_LEAVES, 4, 2, 0, 2).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFrozen() {
		return createStraightBlobTree(FrostBlocks.FROZEN_LOG, FrostBlocks.FROZEN_LEAVES, 8, 2, 0, 2).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFancyFrostRoot() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(FrostBlocks.FROSTROOT_LOG), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(FrostBlocks.FROSTROOT_LEAVES), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().dirt(BlockStateProvider.simple(FrostBlocks.FROZEN_DIRT));
	}

	public static void init() {

	}
}
