package baguchan.frostrealm.world.biome;

import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostMobCategory;
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
        p_126813_.addSpawn(FrostMobCategory.FROSTREALM_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_WRAITH.get(), 100, 2, 3));
	}

	public static void plainCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.CRYSTAL_FOX.get(), 4, 2, 3));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.MARMOT.get(), 6, 3, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 12, 4, 6));
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_BOAR.get(), 8, 4, 5));
	}

	public static void crystalFallCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.MARMOT.get(), 6, 3, 4));
	}

	public static void forestCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 10, 4, 6));
        p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.BUSH_BUG.get(), 10, 4, 6));
	}

	public static void mountainCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOW_MOLE.get(), 6, 2, 4));
	}

	public static void mountainMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
	}

	public static void underGroundMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
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
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_WILLOW);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS);
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

	public static void addCrystalFallPlantsFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_PLAINS);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_POPPY_SKY);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS_SKY);
        p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.STONE_SPIKE);
	}


	public static void addWaterSpringOnlyFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_WATER);
	}

	public static void addHotSpringDelta(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.HOT_SPRING_DELTA);
	}

	public static void addHotRockFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA_HOTROCK_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.LAVA_DELTA);
	}

	public static void addUnderGroundFeature(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA_HOTROCK_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.LAVA_DELTA);
	}

	public static void addStarDustHillFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.STARDUST_CRUSTER);
        p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.STARDUST_ORE_UPPER);
	}

	public static void addIceCaveFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, FrostPlacements.ICE_CLUSTER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, FrostPlacements.LARGE_ICE);
	}
}