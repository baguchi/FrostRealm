package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostNoiseRouterData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FrostDensityFunctions {

    public static final ResourceKey<DensityFunction> UNDERGROUND = createKey("underground");
    private static ResourceKey<DensityFunction> createVannilaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(name));
    }

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseHolderGetter = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> density = context.lookup(Registries.DENSITY_FUNCTION);
        FrostNoiseRouterData.bootstrapDensity(context);
        context.register(UNDERGROUND, FrostNoiseRouterData.underground(density, noiseHolderGetter, getFunction(density, FrostNoiseRouterData.SLOPED_CHEESE)));

    }


    private static DensityFunction getFunction(HolderGetter<DensityFunction> p_256312_, ResourceKey<DensityFunction> p_256077_) {
        return new DensityFunctions.HolderHolder(p_256312_.getOrThrow(p_256077_));
    }

}