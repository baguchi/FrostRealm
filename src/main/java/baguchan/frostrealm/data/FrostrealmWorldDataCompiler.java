package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostCarvers;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostNoiseGeneratorSettings;
import baguchan.frostrealm.world.biome.FrostBiomeMaker;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

import java.util.Map;
import java.util.OptionalLong;
import java.util.stream.Collectors;

//Thanks to the twilight forest team for the first data conversion!
public class FrostrealmWorldDataCompiler extends WorldDataCompilerAndOps<JsonElement> {
	public FrostrealmWorldDataCompiler(DataGenerator generator) {
		super(generator, JsonOps.INSTANCE, GSON::toJson, new RegistryAccess.RegistryHolder());
	}

	@Override
	public void generate(HashCache directoryCache) {
		FrostCarvers.registerConfigurations(this.dynamicRegistries.registryOrThrow(Registry.CONFIGURED_CARVER_REGISTRY));

		Map<ResourceLocation, Biome> biomes = this.getBiomes();
		biomes.forEach((rl, biome) -> this.dynamicRegistries.registry(Registry.BIOME_REGISTRY).ifPresent(reg -> Registry.register(reg, rl, biome)));
		biomes.forEach((rl, biome) -> this.serialize(Registry.BIOME_REGISTRY, rl, biome, Biome.DIRECT_CODEC));

		this.getDimensions().forEach((rl, dimension) -> this.serialize(Registry.LEVEL_STEM_REGISTRY, rl, dimension, LevelStem.CODEC));
	}

	private Map<ResourceLocation, LevelStem> getDimensions() {

		// Register the dimension noise settings in the local datagen registry.
		this.getOrCreateInRegistry(this.dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, FrostRealm.prefix("frostrealm_noise_config")), FrostNoiseGeneratorSettings::frostrealm);
		FrostDimensions.init();

		NoiseBasedChunkGenerator frostChunkGen =
				new NoiseBasedChunkGenerator(RegistryAccess.builtin().registryOrThrow(Registry.NOISE_REGISTRY), FrostBiomes.FROSTREALM_BIOMESOURCE.biomeSource(dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY)), 0L, FrostNoiseGeneratorSettings::frostrealm);

		final DimensionType frostType = DimensionType.create(
				OptionalLong.empty(),
				true,
				false,
				false,
				true,
				1.0D,
				false,
				false,
				true,
				false,
				false,
				-64, // Minimum Y Level
				64 + 256, // Height + Min Y = Max Y
				64 + 256, // Logical Height
				new ResourceLocation("infiniburn_overworld"),
				FrostRealm.prefix("renderer"), // DimensionRenderInfo
				0.0F
		);

		// Register the type in the local datagen registry. Hacky.
		this.getOrCreateInRegistry(this.dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm")), () -> frostType);
		return ImmutableMap.of(
				FrostRealm.prefix("frostrealm"), new LevelStem(() -> frostType, frostChunkGen));
	}

	private Map<ResourceLocation, Biome> getBiomes() {
		return FrostBiomeMaker
				.BIOMES
				.entrySet()
				.stream()
				.peek(registryKeyBiomeEntry -> registryKeyBiomeEntry.getValue().setRegistryName(registryKeyBiomeEntry.getKey().location()))
				.collect(Collectors.toMap(entry -> entry.getKey().location(), Map.Entry::getValue));
	}
}