package baguchan.frostrealm.world.placement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FrostOrePlacements {
	public static final ResourceKey<PlacedFeature> FROST_CRYSTAL_ORE_UPPER = registerKey("frost_crystal_ore_upper");
	public static final ResourceKey<PlacedFeature> FROST_CRYSTAL_ORE_LOWER = registerKey("frost_crystal_ore_lower");

	public static final ResourceKey<PlacedFeature> GLIMMER_ORE_LOWER = registerKey("glimmer_ore_lower");
	public static final ResourceKey<PlacedFeature> GLIMMER_ORE_SMALL = registerKey("glimmer_ore_small");
	public static final ResourceKey<PlacedFeature> GLIMMER_ORE_EXTRA = registerKey("glimmer_ore_extra");

	public static final ResourceKey<PlacedFeature> ASTRIUM_ORE_LOWER = registerKey("astrium_ore_lower");
	public static final ResourceKey<PlacedFeature> ASTRIUM_ORE_UPPER = registerKey("astrium_ore_upper");

	public static final ResourceKey<PlacedFeature> STARDUST_ORE_UPPER = registerKey("stardust_ore_upper");


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, FrostRealm.prefix(name));
	}

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

		PlacementUtils.register(context, FROST_CRYSTAL_ORE_UPPER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_FROST_CRYSTAL), commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(92), VerticalAnchor.top())));
		PlacementUtils.register(context, FROST_CRYSTAL_ORE_LOWER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_FROST_CRYSTAL_BURIED), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.belowTop(120))));

		PlacementUtils.register(context, GLIMMER_ORE_LOWER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_GLIMMERROCK), commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(0))));
		PlacementUtils.register(context, GLIMMER_ORE_SMALL, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_GLIMMERROCK_SMALL), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(48))));
		PlacementUtils.register(context, GLIMMER_ORE_EXTRA, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_FROST_CRYSTAL_BURIED), commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256))));

		PlacementUtils.register(context, ASTRIUM_ORE_LOWER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_ASTRIUM), commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-44), VerticalAnchor.absolute(76))));
		PlacementUtils.register(context, ASTRIUM_ORE_UPPER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_ASTRIUM_SMALL), commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(60), VerticalAnchor.absolute(384))));

		PlacementUtils.register(context, STARDUST_ORE_UPPER, configuredFeature.getOrThrow(FrostConfiguredFeatures.ORE_STARDUST_CRYSTAL), commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(60), VerticalAnchor.absolute(384))));
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
