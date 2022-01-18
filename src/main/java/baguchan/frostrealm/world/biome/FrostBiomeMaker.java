package baguchan.frostrealm.world.biome;

import baguchan.frostrealm.registry.FrostBiomes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;

public class FrostBiomeMaker {
	public static final Map<ResourceKey<Biome>, Biome> BIOMES = generateBiomes();

	private static Map<ResourceKey<Biome>, Biome> generateBiomes() {
		final ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes = ImmutableMap.builder();

		biomes.put(FrostBiomes.FRIGID_FOREST,
				FrostRealmBiomes.frigidForest()
		);
		biomes.put(FrostBiomes.TUNDRA,
				FrostRealmBiomes.tundra()
		);
		biomes.put(FrostBiomes.FROZEN_CANYON,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.FROZEN_BEACH,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.FROZEN_OCEAN,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.FROZEN_DEEP_OCEAN,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.FROST_RIVER,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.GLACIERS,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.HOT_ROCK,
				FrostRealmBiomes.hotrockBiome()
		);
		biomes.put(FrostBiomes.STAR_DUST_PEAKS,
				FrostRealmBiomes.stoneBasedBiome()
		);
		biomes.put(FrostBiomes.WARPED_CLIFFS,
				FrostRealmBiomes.stoneBasedBiome()
		);

		return biomes.build();
	}
}
