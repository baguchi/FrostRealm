package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.FrostChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class FrostDimensions {
	public static final ResourceKey<DimensionType> FROSTREALM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));

	public static final ResourceKey<Level> FROSTREALM_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));


	public static final ResourceKey<LevelStem> FROSTREALM_LEVELSTEM = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));


	public static void init() {
		Registry.register(Registry.CHUNK_GENERATOR, FrostRealm.prefix("chunk_generator"), FrostChunkGenerator.CODEC);
	}
}