package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.resource.FrostBiomeBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FrostBiomes {

	public static final ResourceKey<Biome> TUNDRA = register("tundra");
	public static final ResourceKey<Biome> FRIGID_FOREST = register("frigid_forest");

	public static final ResourceKey<Biome> FROZEN_CANYON = register("frozen_canyon");
	public static final ResourceKey<Biome> GLACIERS = register("glaciers");
	public static final ResourceKey<Biome> HOT_ROCK = register("hot_rock");
	public static final ResourceKey<Biome> STAR_DUST_PEAKS = register("star_dust_peaks");
	public static final ResourceKey<Biome> WARPED_CLIFFS = register("warped_cliffs");
	public static final ResourceKey<Biome> FROZEN_OCEAN = register("frozen_ocean");
	public static final ResourceKey<Biome> FROZEN_DEEP_OCEAN = register("frozen_deep_ocean");
	public static final ResourceKey<Biome> FROZEN_BEACH = register("frozen_beach");
	public static final ResourceKey<Biome> FROST_RIVER = register("frost_river");

	public static final ResourceKey<Biome> CRYSTAL_FALL = register("crystal_fall");

	public static final ResourceKey<Biome> ICE_CAVE = register("ice_cave");
	public static final ResourceKey<Biome> DEEP_UNDERGROUND = register("deep_underground");

	public static void bootstrap(BootstapContext<Biome> context) {
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
		context.register(TUNDRA, FrostBiomeBuilders.tundraBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(FRIGID_FOREST, FrostBiomeBuilders.forestBiome(placedFeatures, vanillaConfiguredCarvers));

		context.register(FROZEN_CANYON, FrostBiomeBuilders.mountainBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(GLACIERS, FrostBiomeBuilders.mountainBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(HOT_ROCK, FrostBiomeBuilders.hotrockBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(STAR_DUST_PEAKS, FrostBiomeBuilders.stardustPeakBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(WARPED_CLIFFS, FrostBiomeBuilders.mountainBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(FROZEN_OCEAN, FrostBiomeBuilders.oceanBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(FROZEN_DEEP_OCEAN, FrostBiomeBuilders.oceanBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(FROZEN_BEACH, FrostBiomeBuilders.beachBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(FROST_RIVER, FrostBiomeBuilders.riverBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(CRYSTAL_FALL, FrostBiomeBuilders.waterFallBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(ICE_CAVE, FrostBiomeBuilders.iceBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(DEEP_UNDERGROUND, FrostBiomeBuilders.undergroundBiome(placedFeatures, vanillaConfiguredCarvers));
	}

	private static ResourceKey<Biome> register(String p_48229_) {
		return ResourceKey.create(Registries.BIOME, new ResourceLocation(FrostRealm.MODID, p_48229_));
	}

	public static void addBiomeTypes() {
	}
}
