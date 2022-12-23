package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.resource.ModConfiguredFeatures;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostDimensionSettings;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.world.caver.FrostConfiguredWorldCarvers;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.Map;

public class WorldGenerator extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.NOISE, (context) -> {
			})
			.add(Registries.DENSITY_FUNCTION, (context) -> {
			})
			.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrapConfiguredFeature)
			.add(Registries.PLACED_FEATURE, ModConfiguredFeatures::bootstrapPlacedFeature)
			.add(Registries.CONFIGURED_CARVER, FrostConfiguredWorldCarvers::bootstrap)
			.add(Registries.NOISE_SETTINGS, FrostDimensionSettings::bootstrapNoise)
			.add(Registries.DIMENSION_TYPE, FrostDimensionSettings::bootstrapDimensionType)
			.add(Registries.BIOME, FrostBiomes::bootstrap);
	;


	public WorldGenerator(PackOutput output) {
		super(output, WorldGenerator::createLookup);
	}

	public static HolderLookup.Provider createLookup() {
		return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
	}

	public static DataProvider createLevelStem(PackOutput output, ExistingFileHelper helper) {
		HolderLookup.Provider registry = createLookup();
		RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registry);
		HolderGetter<Biome> biomeRegistry = registry.lookupOrThrow(Registries.BIOME);
		HolderGetter<DimensionType> dimTypes = registry.lookupOrThrow(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGenSettings = registry.lookupOrThrow(Registries.NOISE_SETTINGS);

		NoiseBasedChunkGenerator wrappedChunkGenerator =
				new NoiseBasedChunkGenerator(
						FrostBiomes.FROSTREALM_BIOMESOURCE.biomeSource(biomeRegistry, true),
						noiseGenSettings.getOrThrow(FrostDimensionSettings.FROSTREALM_NOISE));

		LevelStem stem = new LevelStem(
				dimTypes.getOrThrow(FrostDimensions.FROSTREALM_TYPE),
				wrappedChunkGenerator);
		return new JsonCodecProvider<>(output, helper, FrostRealm.MODID, ops, PackType.SERVER_DATA, Registries.LEVEL_STEM.location().getPath(), LevelStem.CODEC, Map.of(FrostDimensions.FROSTREALM_LEVELSTEM.location(), stem));
	}
}