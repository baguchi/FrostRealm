package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostNoiseRouterData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static baguchan.frostrealm.world.gen.FrostNoiseRouterData.PILLARS;

public class FrostDensityFunctions {

    public static final ResourceKey<DensityFunction> UNDERGROUND = createKey("underground");
    private static ResourceKey<DensityFunction> createVannilaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace(name));
    }

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseHolderGetter = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> density = context.lookup(Registries.DENSITY_FUNCTION);
        FrostNoiseRouterData.bootstrapDensity(context);
        context.register(UNDERGROUND, FrostNoiseRouterData.underground(density, noiseHolderGetter, getFunction(density, FrostNoiseRouterData.SLOPED_CHEESE)));
        context.register(PILLARS, pillars(noiseHolderGetter));
    }

    private static DensityFunction pillars(HolderGetter<NormalNoise.NoiseParameters> p_255985_) {
        double d0 = 25.0;
        double d1 = 0.3;
        DensityFunction densityfunction = DensityFunctions.noise(p_255985_.getOrThrow(Noises.PILLAR), 25.0, 0.3);
        DensityFunction densityfunction1 = DensityFunctions.mappedNoise(p_255985_.getOrThrow(Noises.PILLAR_RARENESS), 0.0, -2.0);
        DensityFunction densityfunction2 = DensityFunctions.mappedNoise(p_255985_.getOrThrow(Noises.PILLAR_THICKNESS), 0.0, 1.25);
        DensityFunction densityfunction3 = DensityFunctions.add(DensityFunctions.mul(densityfunction, DensityFunctions.constant(2.0)), densityfunction1);
        return DensityFunctions.cacheOnce(DensityFunctions.mul(densityfunction3, densityfunction2.cube()));
    }


    private static DensityFunction getFunction(HolderGetter<DensityFunction> p_256312_, ResourceKey<DensityFunction> p_256077_) {
        return new DensityFunctions.HolderHolder(p_256312_.getOrThrow(p_256077_));
    }

}