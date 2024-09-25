package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.resource.FrostDensityFunctions;
import baguchan.frostrealm.data.resource.FrostNoises;
import baguchan.frostrealm.data.resource.ModConfiguredFeatures;
import baguchan.frostrealm.data.resource.registries.WolfflueVariants;
import baguchan.frostrealm.registry.FrostBiomeSources;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostDamageSources;
import baguchan.frostrealm.registry.FrostDimensionSettings;
import baguchan.frostrealm.world.caver.FrostConfiguredWorldCarvers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.DAMAGE_TYPE, FrostDamageSources::bootstrap)
            .add(Registries.NOISE, FrostNoises::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrapConfiguredFeature)
            .add(Registries.PLACED_FEATURE, ModConfiguredFeatures::bootstrapPlacedFeature)
            .add(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, FrostBiomeSources::bootstrapPreset)
            .add(Registries.CONFIGURED_CARVER, FrostConfiguredWorldCarvers::bootstrap)
            .add(Registries.NOISE_SETTINGS, FrostDimensionSettings::bootstrapNoise)
			.add(Registries.DENSITY_FUNCTION, FrostDensityFunctions::bootstrap)
            .add(Registries.DIMENSION_TYPE, FrostDimensionSettings::bootstrapDimensionType)
            .add(Registries.BIOME, FrostBiomes::bootstrap)
			.add(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY, WolfflueVariants::bootstrap)
            .add(Registries.LEVEL_STEM, FrostDimensionSettings::bootstrapLevelStem);
	;


	public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", FrostRealm.MODID));
	}



}