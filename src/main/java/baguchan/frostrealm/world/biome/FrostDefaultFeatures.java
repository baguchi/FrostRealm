package baguchan.frostrealm.world.biome;

import baguchan.frostrealm.registry.FrostCarvers;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.world.placement.FrostOrePlacements;
import baguchan.frostrealm.world.placement.FrostPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FrostDefaultFeatures {
	public static void commonSpawns(MobSpawnSettings.Builder p_126789_) {
		monsters(p_126789_);
	}

	public static void commonCaves(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addCarver(GenerationStep.Carving.AIR, FrostCarvers.CAVE);
		p_126789_.addCarver(GenerationStep.Carving.AIR, FrostCarvers.CAVE_UNDERGROUND);
		p_126789_.addCarver(GenerationStep.Carving.AIR, FrostCarvers.CANYON);
		p_126789_.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
	}

	public static void commonSpring(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA);
		p_126789_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_WATER);
	}

	public static void hotrockSpring(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA_HOTROCK_EXTRA);
	}

	public static void oreCommon(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_LOWER);
		p_126789_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_UPPER);
		p_126789_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_LOWER);
		p_126789_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_SMALL);
	}

	public static void tundraCommon(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_BEAR_BERRY);
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_POPPY);
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_WILLOW);
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS);
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_ROCK);
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_MOSSY_ROCK);
	}

	public static void hotRockCommon(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.LAVA_DELTA);
		p_126789_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_EXTRA);
	}

	public static void frigidForestCommon(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_POPPY);
	}

	public static void snow(BiomeGenerationSettings.Builder p_126789_) {
		p_126789_.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
	}

	public static void monsters(MobSpawnSettings.Builder p_194726_) {
		p_194726_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_WRAITH, 100, 4, 4));
		p_194726_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUR, 40, 2, 4));
		p_194726_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUDILLO, 5, 1, 2));
	}

	public static void hotBiomeMonster(MobSpawnSettings.Builder p_194726_) {
		p_194726_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUR, 40, 2, 4));
		p_194726_.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUDILLO, 5, 1, 2));
	}

	public static void tundraSpawn(MobSpawnSettings.Builder p_194726_) {
		p_194726_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL, 10, 3, 6));
		p_194726_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.MARMOT, 6, 2, 4));
	}

	public static void frigidForestSpawn(MobSpawnSettings.Builder p_194726_) {
		p_194726_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL, 10, 3, 6));
		p_194726_.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_WOLF, 6, 3, 4));
	}
}
