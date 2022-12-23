package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class FrostDimensions {
	public static final ResourceKey<DimensionType> FROSTREALM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(FrostRealm.MODID, "frostrealm"));

	public static final ResourceKey<Level> FROSTREALM_LEVEL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(FrostRealm.MODID, "frostrealm"));


	public static final ResourceKey<LevelStem> FROSTREALM_LEVELSTEM = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(FrostRealm.MODID, "frostrealm"));
}