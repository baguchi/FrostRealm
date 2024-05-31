package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.biome.FrostrealmBiomeBuilder;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

import java.util.function.Function;

public class FrostBiomeSources {
    public static final ResourceKey<MultiNoiseBiomeSourceParameterList> FROSTREALM = registerPreset("frostrealm");

    public static final MultiNoiseBiomeSourceParameterList.Preset FROSTREALM_PRESET = new MultiNoiseBiomeSourceParameterList.Preset(new ResourceLocation(FrostRealm.MODID, "frostrealm"), new MultiNoiseBiomeSourceParameterList.Preset.SourceProvider() {
        public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> p_275530_) {
            return generateFrostBiome(p_275530_);
        }
    });

    static <T> Climate.ParameterList<T> generateFrostBiome(Function<ResourceKey<Biome>, T> p_275249_) {
        ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> builder = ImmutableList.builder();
        (new FrostrealmBiomeBuilder()).addBiomes((p_275579_) -> {
            builder.add(p_275579_.mapSecond(p_275249_));
        });
        return new Climate.ParameterList<>(builder.build());
    }

    private static ResourceKey<MultiNoiseBiomeSourceParameterList> registerPreset(String p_275281_) {
        return ResourceKey.create(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, new ResourceLocation(FrostRealm.MODID, p_275281_));
    }

    public static void bootstrapPreset(BootstrapContext<MultiNoiseBiomeSourceParameterList> p_275387_) {
        HolderGetter<Biome> holdergetter = p_275387_.lookup(Registries.BIOME);
        p_275387_.register(FROSTREALM, new MultiNoiseBiomeSourceParameterList(FROSTREALM_PRESET, holdergetter));
    }
}