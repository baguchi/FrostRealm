package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostSounds;
import baguchan.frostrealm.world.biome.FrostBiomeDefaultFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class FrostBiomeBuilders {

	public static Biome undergroundBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addUnderGroundFeature(builder);
		FrostBiomeDefaultFeatures.underGroundMonsterSpawns(builder1);
		return makeDefaultHotBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
	}

	public static Biome iceBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addIceCaveFeatures(builder);
		FrostBiomeDefaultFeatures.addWaterSpringOnlyFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1);
	}

	public static Biome waterFallBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addWaterSpringOnlyFeatures(builder);
		FrostBiomeDefaultFeatures.addHotSpringDelta(builder);
		FrostBiomeDefaultFeatures.addCrystalFallPlantsFeatures(builder);
		FrostBiomeDefaultFeatures.crystalFallCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
        return makeSkyBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
	}

	public static Biome sherbetDesert(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDesertBiome(builder, builder1, FrostSounds.FROST_MOON_BGM);
	}

	public static Biome hotrockBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addHotRockFeatures(builder);
		FrostBiomeDefaultFeatures.mountainMonsterSpawns(builder1);
		return makeDefaultHotBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
	}

	public static Biome beachBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);

		return makeDefaultBiome(builder, builder1);
	}

	public static Biome forestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addForestFeatures(builder);
		FrostBiomeDefaultFeatures.forestCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1);
	}

	public static Biome tundraBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addPlainsFeatures(builder);
		FrostBiomeDefaultFeatures.plainCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1);
	}

	public static Biome riverBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addPlainsFeatures(builder);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1);
	}

	public static Biome oceanBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.addIcebergs(builder);
		BiomeDefaultFeatures.addBlueIce(builder);
		FrostBiomeDefaultFeatures.addPlainsFeatures(builder);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);

		builder1.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SEAL.get(), 10, 5, 6));

		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeOceanBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
	}

    public static Biome warpedCliffBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
        FrostBiomeDefaultFeatures.addWarpedCliffFeatures(builder);
        FrostBiomeDefaultFeatures.addSpringFeatures(builder);
        FrostBiomeDefaultFeatures.mountainCreatureSpawns(builder1);
        FrostBiomeDefaultFeatures.monsterSpawns(builder1);
        return makeDefaultBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
    }

	public static Biome mountainBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.mountainCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
	}

	public static Biome stardustPeakBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.mountainCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.addStarDustHillFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1, FrostSounds.CALM_NIGHT_BGM);
	}

	public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
		return makeDefaultBiome(builder, mobSpawnSetting, FrostSounds.FROST_MOON_BGM);
	}

	public static Biome makeOceanBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting, Supplier<SoundEvent> soundEvent) {
		FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
		FrostBiomeDefaultFeatures.addDefaultOres(builder);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
		return fullDefinition(
				-1.2F,
				0.5F,
				new BiomeSpecialEffects.Builder()
						.fogColor(4630224)
						.skyColor(7907327)
						.waterColor(0x3f_76_e4)
						.waterFogColor(0x05_05_33)
						.grassColorOverride(7115607)
						.foliageColorOverride(7115607)
						.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
						.backgroundMusic(new Music(Holder.direct(soundEvent.get()), 12000, 24000, false))
						.build(),
				mobSpawnSetting.build(),
				builder.build(),
				Biome.TemperatureModifier.FROZEN
		);
	}

	public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting, Supplier<SoundEvent> soundEvent) {
		FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
		FrostBiomeDefaultFeatures.addDefaultOres(builder);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

		return fullDefinition(
				-1.2F,
				0.5F,
				new BiomeSpecialEffects.Builder()
						.fogColor(4630224)
						.skyColor(7907327)
						.waterColor(0x3f_76_e4)
						.waterFogColor(0x05_05_33)
                        .grassColorOverride(7115607)
                        .foliageColorOverride(7115607)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic(new Music(Holder.direct(soundEvent.get()), 12000, 24000, false))
                        .build(),
                mobSpawnSetting.build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeSkyBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting, Supplier<SoundEvent> soundEvent) {
        FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
        FrostBiomeDefaultFeatures.addDefaultOres(builder);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

        return fullDefinition(
                0.3F,
                0.8F,
                new BiomeSpecialEffects.Builder()
                        .fogColor(4630224)
                        .skyColor(7907327)
                        .waterColor(0x3f_76_e4)
                        .waterFogColor(0x05_05_33)
						.grassColorOverride(7115607)
						.foliageColorOverride(7115607)
						.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
						.backgroundMusic(new Music(Holder.direct(soundEvent.get()), 12000, 24000, false))
						.build(),
				mobSpawnSetting.build(),
				builder.build(),
				Biome.TemperatureModifier.NONE
		);
	}

    public static Biome makeDesertBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting, Supplier<SoundEvent> soundEvent) {
        FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
        FrostBiomeDefaultFeatures.addDefaultOres(builder);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

        return fullDefinition(
                0.5F,
                0.8F,
                new BiomeSpecialEffects.Builder()
                        .fogColor(0xFFB9EB)
                        .skyColor(7907327)
                        .waterColor(0x3f_76_e4)
                        .waterFogColor(0x05_05_33)
                        .grassColorOverride(7115607)
                        .foliageColorOverride(7115607)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic(new Music(Holder.direct(soundEvent.get()), 12000, 24000, false))
                        .build(),
                mobSpawnSetting.build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

	public static Biome makeDefaultHotBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting, Supplier<SoundEvent> soundEvent) {
		FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
		FrostBiomeDefaultFeatures.addDefaultOres(builder);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

		return fullDefinition(
                1.0F,
				0.6F,
				new BiomeSpecialEffects.Builder()
						.fogColor(4630224)
						.skyColor(7907327)
						.waterColor(0x3f_76_e4)
						.waterFogColor(0x05_05_33)
						.grassColorOverride(7115607)
						.foliageColorOverride(7115607)
						.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
						.backgroundMusic(new Music(Holder.direct(soundEvent.get()), 12000, 24000, false))
						.build(),
				mobSpawnSetting.build(),
				builder.build(),
				Biome.TemperatureModifier.NONE
		);
	}

	public static Biome fullDefinition(float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
		return new Biome.BiomeBuilder()
				.temperature(temperature)
				.downfall(downfall)
				.specialEffects(effects)
				.mobSpawnSettings(spawnSettings)
				.generationSettings(generationSettings)
				.temperatureAdjustment(temperatureModifier)
				.build();
	}
}