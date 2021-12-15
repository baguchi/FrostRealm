package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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

	private static ResourceKey<Biome> register(String p_48229_) {
		return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FrostRealm.MODID, p_48229_));
	}

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> registry) {
		/*registry.getRegistry().registerAll(
				FrostRealmBiomes.tundra().setRegistryName("tundra"),
				FrostRealmBiomes.frigidForest().setRegistryName("frigid_forest"),
				FrostRealmBiomes.stoneBasedBiome().setRegistryName("frozen_canyon"),
				FrostRealmBiomes.stoneBasedBiome().setRegistryName("glaciers"),
				FrostRealmBiomes.stoneBasedBiome().setRegistryName("hot_rock"),
				FrostRealmBiomes.stoneBasedBiome().setRegistryName("warped_cliff"),
				FrostRealmBiomes.stoneBasedBiome().setRegistryName("frozen_ocean"),
				FrostRealmBiomes.stoneBasedBiome().setRegistryName("frozen_deep_ocean"));*/
	}
}
