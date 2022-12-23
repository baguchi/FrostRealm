package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import baguchan.frostrealm.world.gen.FrostTreeFeatures;
import baguchan.frostrealm.world.placement.FrostOrePlacements;
import baguchan.frostrealm.world.placement.FrostPlacements;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModConfiguredFeatures {
	public static void bootstrapConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context) {
		FrostTreeFeatures.bootstrap(context);
		FrostConfiguredFeatures.bootstrap(context);
	}

	public static void bootstrapPlacedFeature(BootstapContext<PlacedFeature> context) {
		FrostOrePlacements.bootstrap(context);
		FrostPlacements.bootstrap(context);
	}
}