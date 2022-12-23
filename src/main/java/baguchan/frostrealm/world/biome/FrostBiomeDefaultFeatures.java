package baguchan.frostrealm.world.biome;

import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.world.caver.FrostConfiguredWorldCarvers;
import baguchan.frostrealm.world.placement.FrostOrePlacements;
import baguchan.frostrealm.world.placement.FrostPlacements;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FrostBiomeDefaultFeatures {
	public static void addDefaultCarvers(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addCarver(GenerationStep.Carving.AIR, FrostConfiguredWorldCarvers.CAVE);
		p_194721_.addCarver(GenerationStep.Carving.AIR, FrostConfiguredWorldCarvers.CAVE_EXTRA_UNDERGROUND);
		p_194721_.addCarver(GenerationStep.Carving.AIR, FrostConfiguredWorldCarvers.CANYON);
	}

	public static void monsterSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_WRAITH.get(), 100, 2, 3));
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.CLUST_WRAITH.get(), 80, 1, 2));
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_BEASTER.get(), 30, 1, 2));
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUR.get(), 20, 1, 2));
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUDILLO.get(), 10, 1, 1));
	}

	public static void plainCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.KOLOSSUS.get(), 2, 2, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.CRYSTAL_FOX.get(), 8, 2, 3));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.MARMOT.get(), 10, 2, 3));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 12, 3, 5));
	}

	public static void forestCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_WOLF.get(), 6, 2, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 10, 3, 5));
	}

	public static void mountainCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOW_MOLE.get(), 6, 2, 4));
	}

	public static void mountainMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUR.get(), 30, 1, 2));
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUDILLO.get(), 10, 1, 1));
	}

	public static void underGroundMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUR.get(), 30, 1, 2));
		p_126813_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUDILLO.get(), 10, 1, 1));
	}

	public static void addDefaultOres(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.ASTRIUM_ORE_UPPER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.ASTRIUM_ORE_LOWER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_LOWER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_UPPER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_LOWER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_SMALL);
	}

	public static void addForestFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_FOREST);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_VIGOROSHROOM);
	}

	public static void addPlainsFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_PLAINS);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_ROCK);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_MOSSY_ROCK);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_BEAR_BERRY);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_POPPY);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_WILLOW);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS);
	}

	public static void addSpringFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_WATER);
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA);
	}


	public static void addWaterSpringOnlyFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_WATER);
	}

	public static void addHotRockFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA_HOTROCK_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.LAVA_DELTA);
	}

	public static void addUnderGroundFeature(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA_HOTROCK_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.LAVA_DELTA);
	}

	public static void addIceCaveFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, FrostPlacements.ICE_CLUSTER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, FrostPlacements.LARGE_ICE);
	}
}