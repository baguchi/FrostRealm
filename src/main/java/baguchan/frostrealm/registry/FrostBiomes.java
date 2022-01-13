package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.biome.FrostrealmBiomeBuilder;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostBiomes {
	public static final ResourceKey<Biome> TUNDRA = register("tundra");
	public static final ResourceKey<Biome> FRIGID_FOREST = register("frigid_forest");
	public static final ResourceKey<Biome> FROZEN_CANYON = register("frozen_canyon");
	public static final ResourceKey<Biome> GLACIERS = register("glaciers");
	public static final ResourceKey<Biome> HOT_ROCK = register("hot_rock");
	public static final ResourceKey<Biome> STAR_DUST_PEAKS = register("star_dust_peaks");
	public static final ResourceKey<Biome> WARPED_CLIFFS = register("warped_cliffs");
	public static final ResourceKey<Biome> FROZEN_OCEAN = register("frozen_ocean");
	public static final ResourceKey<Biome> FROZEN_DEEP_OCEAN = register("frozen_deep_ocean");
	public static final ResourceKey<Biome> FROZEN_BEACH = register("frozen_beach");
	public static final ResourceKey<Biome> FROST_RIVER = register("frost_river");

	public static final MultiNoiseBiomeSource.Preset FROSTREALM_BIOMESOURCE = new MultiNoiseBiomeSource.Preset(FrostRealm.prefix("frostrealm"), (p_187108_) -> {
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

	private static ResourceKey<Biome> register(String p_48229_) {
		return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FrostRealm.MODID, p_48229_));
	}
}
