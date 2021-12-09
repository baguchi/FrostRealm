package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.biome.FrostrealmBiomeBuilder;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.function.Supplier;

public class FrostDimensions {
	public static final ResourceKey<DimensionType> FROSTREALM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));

	public static final ResourceKey<Level> FROSTREALM_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));


	public static final ResourceKey<LevelStem> FROSTREALM_LEVELSTEM = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));

	public static final MultiNoiseBiomeSource.Preset FROSTREALM_PRESET = new MultiNoiseBiomeSource.Preset(new ResourceLocation(FrostRealm.MODID, "frostrealm"), (p_187108_) -> {
		ImmutableList.Builder<Pair<Climate.ParameterPoint, Supplier<Biome>>> builder = ImmutableList.builder();
		(new FrostrealmBiomeBuilder()).addBiomes((p_187098_) -> {
			builder.add(p_187098_.mapSecond((p_187103_) -> {
				return () -> {
					return p_187108_.getOrThrow(p_187103_);
				};
			}));
		});
		return new Climate.ParameterList<>(builder.build());
	});
}