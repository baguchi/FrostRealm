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
    public static void addDesertFeature(BiomeGenerationSettings.Builder p_194721_) {
        p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROST_FIRE_DESERT);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.BIG_ROCK_WOOD);
	}

	public static void addDefaultCarvers(BiomeGenerationSettings.Builder p_194721_) {
        p_194721_.addCarver(FrostConfiguredWorldCarvers.CAVE);
        p_194721_.addCarver(FrostConfiguredWorldCarvers.CAVE_EXTRA_UNDERGROUND);
        p_194721_.addCarver(FrostConfiguredWorldCarvers.CANYON);
	}

	public static void monsterSpawns(MobSpawnSettings.Builder p_126813_) {
		defaultMonsterSpawns(p_126813_);
		purpleFogMonsterSpawns(p_126813_);
	}

	public static void defaultMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(FrostEntities.VENOCHEM.get(), 1, 2));
		p_126813_.addSpawn(MobCategory.MONSTER, 80, new MobSpawnSettings.SpawnerData(FrostEntities.GOKKUR.get(), 2, 3));
		//p_126813_.addSpawn(MobCategory.MONSTER, 30, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_CRAWLER.get(), 3, 4));
		//p_126813_.addSpawn(FrostMobCategory.FROSTREALM_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(FrostEntities.ROOT_DEER.get(), 10, 4, 6));
	}

	public static void purpleFogMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(FrostMobCategory.FROSTREALM_WEATHER_MONSTER, 100, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_WRAITH.get(), 2, 3));
	}

	public static void plainCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(FrostEntities.CRYSTAL_FOX.get(), 2, 3));
		p_126813_.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(FrostEntities.MARMOT.get(), 3, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 4, 6));
		p_126813_.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(FrostEntities.FROST_BOAR.get(), 4, 5));
	}

	public static void crystalFallCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(FrostEntities.MARMOT.get(), 3, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(FrostEntities.FERRET.get(), 3, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 4, 6));
	}

	public static void forestCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(FrostEntities.SNOWPILE_QUAIL.get(), 4, 6));
		p_126813_.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(FrostEntities.WOLFFLUE.get(), 4, 4));
	}

	public static void frostBiteCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(FrostEntities.CRYSTAL_FOX.get(), 2, 4));
		p_126813_.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(FrostEntities.SILK_MOON.get(), 2, 3));
	}

	public static void mountainCreatureSpawns(MobSpawnSettings.Builder p_126813_) {
		p_126813_.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(FrostEntities.SNOW_MOLE.get(), 2, 4));
	}

	public static void mountainMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
		monsterSpawns(p_126813_);
	}

	public static void underGroundMonsterSpawns(MobSpawnSettings.Builder p_126813_) {
		monsterSpawns(p_126813_);
		p_126813_.addSpawn(MobCategory.MONSTER, 80, new MobSpawnSettings.SpawnerData(FrostEntities.UNDER_GOKKUR.get(), 2, 3));

	}

	public static void addDefaultOres(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.ASTRIUM_ORE_UPPER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.ASTRIUM_ORE_LOWER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_LOWER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_UPPER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_LOWER);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_SMALL);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLACINIUM_ORE);
		p_194721_.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLACINIUM_ORE_SMALL);
	}

	public static void addForestFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_FOREST);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_WILLOW);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS);
	}

	public static void addFrostBiteFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTBITE_TREES);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_VIGOROSHROOM);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS);
	}

	public static void addDripFeatures(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.DRIP_TREES_BIG);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_VIGOROSHROOM);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_WILLOW);
		p_194721_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_DRIP_GRASS);
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


    public static void addWarpedCliffFeatures(BiomeGenerationSettings.Builder p_194721_) {
		//p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.FLOATING_ROCK);
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
        p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.SMALL_VOLCANO);

	}

	public static void addUnderGroundFeature(BiomeGenerationSettings.Builder p_194721_) {
		p_194721_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FrostPlacements.SPRING_LAVA_HOTROCK_EXTRA);
		p_194721_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FrostPlacements.UNDERGRAUND_DELTA);
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