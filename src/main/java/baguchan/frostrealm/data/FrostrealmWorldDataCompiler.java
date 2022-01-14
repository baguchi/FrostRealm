package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostCarvers;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostNoiseGeneratorSettings;
import baguchan.frostrealm.world.FrostChunkGenerator;
import baguchan.frostrealm.world.biome.FrostBiomeMaker;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.Map;
import java.util.OptionalLong;
import java.util.stream.Collectors;

//Thanks to the twilight forest team for the first data conversion!
public class FrostrealmWorldDataCompiler extends WorldDataCompilerAndOps<JsonElement> {

	public FrostrealmWorldDataCompiler(DataGenerator generator) {
		super(generator, JsonOps.INSTANCE, GSON::toJson, DimensionType.registerBuiltin(new RegistryAccess.RegistryHolder()));
	}

	@Override
	public void generate(HashCache directoryCache) {
		FrostCarvers.registerConfigurations(this.dynamicRegistries.registryOrThrow(Registry.CONFIGURED_CARVER_REGISTRY));
		FrostDimensions.init();

		Map<ResourceLocation, Biome> biomes = this.getBiomes();
		biomes.forEach((rl, biome) -> this.dynamicRegistries.registry(Registry.BIOME_REGISTRY).ifPresent(reg -> Registry.register(reg, rl, biome)));
		biomes.forEach((rl, biome) -> this.serialize(Registry.BIOME_REGISTRY, rl, biome, Biome.DIRECT_CODEC));

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

		DimensionType dimensionType = this.dynamicRegistries.registry(Registry.DIMENSION_TYPE_REGISTRY).map(reg -> Registry.register(reg, new ResourceLocation(FrostRealm.MODID, "frostrealm_type"), frostType)).get();
		NoiseGeneratorSettings worldNoiseSettings = this.dynamicRegistries.registry(BuiltinRegistries.NOISE_GENERATOR_SETTINGS.key()).map(reg -> Registry.register(reg, new ResourceLocation(FrostRealm.MODID, "frostrealm"), FrostNoiseGeneratorSettings.frostrealm())).get();

		NoiseBasedChunkGenerator aetherChunkGen = new FrostChunkGenerator(RegistryAccess.builtin().registryOrThrow(Registry.NOISE_REGISTRY), FrostBiomes.FROSTREALM_BIOMESOURCE.biomeSource(RegistryAccess.builtin().registryOrThrow(Registry.BIOME_REGISTRY), true), 0L, () -> worldNoiseSettings);

		this.serialize(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"), new LevelStem(() -> dimensionType, aetherChunkGen), LevelStem.CODEC);

	}

	@Override
	protected JsonElement intercept(ResourceKey<?> key, JsonElement element) {
		if (key == Registry.LEVEL_STEM_REGISTRY) {
			element.getAsJsonObject().get("generator").getAsJsonObject().remove("seed");
			element.getAsJsonObject().get("generator").getAsJsonObject().get("biome_source").getAsJsonObject().remove("seed");
		}
		return super.intercept(key, element);
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