package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.world.biome.FrostBiomeDefaultFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FrostBiomeBuilders {

	public static Biome undergroundBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addUnderGroundFeature(builder);
		FrostBiomeDefaultFeatures.underGroundMonsterSpawns(builder1);
		return makeDefaultHotBiome(builder, builder1).build();
	}

	public static Biome iceBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addIceCaveFeatures(builder);
		FrostBiomeDefaultFeatures.addWaterSpringOnlyFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome waterFallBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addWaterSpringOnlyFeatures(builder);
		FrostBiomeDefaultFeatures.addHotSpringDelta(builder);
		FrostBiomeDefaultFeatures.addCrystalFallPlantsFeatures(builder);
		FrostBiomeDefaultFeatures.crystalFallCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
        return makeSkyBiome(builder, builder1);
	}

	public static Biome sherbetDesert(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDesertBiome(builder, builder1);
	}

	public static Biome hotrockBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addHotRockFeatures(builder);
		FrostBiomeDefaultFeatures.mountainMonsterSpawns(builder1);
		return makeDefaultHotBiome(builder, builder1).build();
	}

	public static Biome beachBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);

		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome forestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addForestFeatures(builder);
		FrostBiomeDefaultFeatures.forestCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome frostbiteBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addFrostBiteFeatures(builder);
		FrostBiomeDefaultFeatures.frostBiteCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome dripBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addDripFeatures(builder);
		FrostBiomeDefaultFeatures.forestCreatureSpawns(builder1);
        builder1.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(FrostEntities.GLACIER_BOAR.get(), 4, 6));

        FrostBiomeDefaultFeatures.addSpringFeatures(builder);

        FrostBiomeDefaultFeatures.monsterSpawns(builder1);
        builder1.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(FrostEntities.STRAY_WOLFFLUE.get(), 1, 1));

		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome tundraBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addPlainsFeatures(builder);
		FrostBiomeDefaultFeatures.plainCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
        builder1.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(FrostEntities.STRAY_WOLFFLUE.get(), 1, 1));

		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome riverBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.addPlainsFeatures(builder);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome oceanBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.addIcebergs(builder);
		BiomeDefaultFeatures.addBlueIce(builder);
		FrostBiomeDefaultFeatures.addPlainsFeatures(builder);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);

		builder1.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(FrostEntities.SEAL.get(), 5, 6));

		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
        return makeOceanBiome(builder, builder1);
	}

    public static Biome warpedCliffBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
        FrostBiomeDefaultFeatures.addWarpedCliffFeatures(builder);
        FrostBiomeDefaultFeatures.addSpringFeatures(builder);
        FrostBiomeDefaultFeatures.mountainCreatureSpawns(builder1);
        FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
    }

	public static Biome mountainBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.mountainCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
	}

	public static Biome stardustPeakBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
		FrostBiomeDefaultFeatures.mountainCreatureSpawns(builder1);
		FrostBiomeDefaultFeatures.addSpringFeatures(builder);
		FrostBiomeDefaultFeatures.addStarDustHillFeatures(builder);
		FrostBiomeDefaultFeatures.monsterSpawns(builder1);
		return makeDefaultBiome(builder, builder1).build();
	}


	public static Biome makeOceanBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
		FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
		FrostBiomeDefaultFeatures.addDefaultOres(builder);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
		return fullDefinition(
				-1.2F,
				0.5F,
				new BiomeSpecialEffects.Builder()
						.grassColorOverride(7115607)
						.foliageColorOverride(7115607)
                        .waterColor(4020182)
						.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                     	.build(),
				mobSpawnSetting.build(),
				builder.build(),
				Biome.TemperatureModifier.FROZEN
		).build();
	}

	public static Biome.BiomeBuilder makeDefaultBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
		FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
		FrostBiomeDefaultFeatures.addDefaultOres(builder);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

		return fullDefinition(
				-1.2F,
				0.5F,
                new BiomeSpecialEffects.Builder()
                        .waterColor(0x3f_76_e4)
                        .grassColorOverride(7115607)
                        .foliageColorOverride(7115607)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .build(),
                mobSpawnSetting.build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeSkyBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
        FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
        FrostBiomeDefaultFeatures.addDefaultOres(builder);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

        return fullDefinition(
                0.3F,
                0.8F,
                new BiomeSpecialEffects.Builder()
                        .waterColor(0x3f_76_e4)
						.grassColorOverride(7115607)
						.foliageColorOverride(7115607)
						.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                      	.build(),
				mobSpawnSetting.build(),
				builder.build(),
				Biome.TemperatureModifier.NONE
		).build();
	}

    public static Biome makeDesertBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
        FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
        FrostBiomeDefaultFeatures.addDesertFeature(builder);
        FrostBiomeDefaultFeatures.addDefaultOres(builder);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

        return fullDefinition(
                0.5F,
                0.8F,
                new BiomeSpecialEffects.Builder()
                        .waterColor(0x3f_76_e4)
                        .grassColorOverride(7115607)
                        .foliageColorOverride(7115607)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .build(),
                mobSpawnSetting.build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
		).build();
    }

	public static Biome.BiomeBuilder makeDefaultHotBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
		FrostBiomeDefaultFeatures.addDefaultCarvers(builder);
		FrostBiomeDefaultFeatures.addDefaultOres(builder);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);

		return fullDefinition(
				0.8F,
				0.6F,
                new BiomeSpecialEffects.Builder()
                        .waterColor(0x3f_76_e4)
                        .grassColorOverride(7115607)
                        .foliageColorOverride(7115607)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .build(),
				mobSpawnSetting.build(),
				builder.build(),
				Biome.TemperatureModifier.NONE
		);
	}

	public static Biome.BiomeBuilder fullDefinition(float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
		return new Biome.BiomeBuilder()
				.putAttributes(EnvironmentAttributeMap.builder().set(EnvironmentAttributes.MONSTERS_BURN, false).build())
				.temperature(temperature)
				.downfall(downfall)
				.specialEffects(effects)
				.mobSpawnSettings(spawnSettings)
				.generationSettings(generationSettings)
				.temperatureAdjustment(temperatureModifier);
	}
}