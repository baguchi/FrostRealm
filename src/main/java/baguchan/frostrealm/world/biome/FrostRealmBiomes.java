package baguchan.frostrealm.world.biome;

import baguchan.frostrealm.registry.FrostMusics;
import baguchan.frostrealm.world.placement.FrostPlacements;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FrostRealmBiomes {

	public static Biome tundra() {
		MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
		FrostDefaultFeatures.commonCaves(biomegenerationsettings$builder);
		FrostDefaultFeatures.oreCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.commonSpring(biomegenerationsettings$builder);
		FrostDefaultFeatures.snow(biomegenerationsettings$builder);

		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_PLAINS);
		FrostDefaultFeatures.tundraCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.tundraSpawn(mobspawnsettings$builder);
		FrostDefaultFeatures.commonSpawns(mobspawnsettings$builder);

		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.PLAINS).temperature(0.15F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(5337276).waterFogColor(329011).fogColor(4630224).skyColor(7907327).grassColorOverride(8228713).foliageColorOverride(8228713).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(FrostMusics.FRSOT_MOON).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static Biome frigidForest() {
		MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
		FrostDefaultFeatures.commonCaves(biomegenerationsettings$builder);
		FrostDefaultFeatures.oreCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.frigidForestCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.commonSpring(biomegenerationsettings$builder);
		FrostDefaultFeatures.snow(biomegenerationsettings$builder);

		FrostDefaultFeatures.frigidForestSpawn(mobspawnsettings$builder);
		FrostDefaultFeatures.commonSpawns(mobspawnsettings$builder);

		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_FOREST);

		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.FOREST).temperature(-0.25F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(5337276).waterFogColor(329011).fogColor(4630224).skyColor(7907327).grassColorOverride(7115607).foliageColorOverride(7115607).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(FrostMusics.CALM_NIGHT).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static Biome stoneBasedBiome() {
		MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
		FrostDefaultFeatures.commonCaves(biomegenerationsettings$builder);
		FrostDefaultFeatures.oreCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.commonSpring(biomegenerationsettings$builder);
		FrostDefaultFeatures.snow(biomegenerationsettings$builder);

		FrostDefaultFeatures.commonSpawns(mobspawnsettings$builder);
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.MOUNTAIN).temperature(-0.5F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(5337276).waterFogColor(329011).fogColor(4630224).skyColor(7907327).grassColorOverride(7115607).foliageColorOverride(7115607).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(FrostMusics.CALM_NIGHT).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static Biome hotrockBiome() {
		MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
		FrostDefaultFeatures.commonCaves(biomegenerationsettings$builder);
		FrostDefaultFeatures.oreCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.commonSpring(biomegenerationsettings$builder);
		FrostDefaultFeatures.hotrockSpring(biomegenerationsettings$builder);
		FrostDefaultFeatures.hotRockCommon(biomegenerationsettings$builder);
		FrostDefaultFeatures.snow(biomegenerationsettings$builder);

		FrostDefaultFeatures.hotBiomeMonster(mobspawnsettings$builder);
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.MOUNTAIN).temperature(0.5F).downfall(0.6F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(5337276).waterFogColor(329011).fogColor(4630224).skyColor(7907327).grassColorOverride(7115607).foliageColorOverride(7115607).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(FrostMusics.CALM_NIGHT).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	protected static int calculateSkyColor(float p_194844_) {
		float $$1 = p_194844_ / 3.0F;
		$$1 = Mth.clamp($$1, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
	}
}
