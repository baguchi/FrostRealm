package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FrostDensityFunctions {
    private static final ResourceKey<DensityFunction> SHIFT_X = createVannilaKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createVannilaKey("shift_z");

    private static final DensityFunction BLENDING_JAGGEDNESS = DensityFunctions.zero();
    private static final DensityFunction BLENDING_FACTOR = DensityFunctions.constant(10.0D);
    public static final ResourceKey<DensityFunction> FACTOR = createKey("factor");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("depth");

    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_OVERWORLD = createVannilaKey("overworld/base_3d_noise");

    public static final ResourceKey<DensityFunction> OFFSET_VANNILA = createVannilaKey("overworld/offset");

    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("erosion");
    public static final ResourceKey<DensityFunction> RIDGES = createVannilaKey("overworld/ridges");
    public static final ResourceKey<DensityFunction> RIDGES_FOLDED = createVannilaKey("overworld/ridges_folded");
    public static final ResourceKey<DensityFunction> OFFSET = createKey("offset");
    public static final ResourceKey<DensityFunction> JAGGEDNESS = createKey("jaggedness");
    public static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("sloped_cheese");

    private static ResourceKey<DensityFunction> createVannilaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(name));
    }

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(FrostRealm.MODID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseHolderGetter = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> density = context.lookup(Registries.DENSITY_FUNCTION);
        DensityFunction densityfunction = getFunction(density, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(density, SHIFT_Z);

        Holder<DensityFunction> holder = context.register(CONTINENTS, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, noiseHolderGetter.getOrThrow(FrostNoises.CONTINENTALNESS))));
        Holder<DensityFunction> holder1 = context.register(EROSION, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, noiseHolderGetter.getOrThrow(FrostNoises.EROSION))));
        DensityFunction densityfunction3 = DensityFunctions.noise(noiseHolderGetter.getOrThrow(Noises.JAGGED), 1500.0D, 0.0D);
        registerTerrainNoises(context, density, densityfunction3, holder, holder1, OFFSET, FACTOR, JAGGEDNESS, DEPTH, SLOPED_CHEESE, false);
    }

    private static void registerTerrainNoises(BootstapContext<DensityFunction> p_256336_, HolderGetter<DensityFunction> p_256393_, DensityFunction p_224476_, Holder<DensityFunction> p_224477_, Holder<DensityFunction> p_224478_, ResourceKey<DensityFunction> p_224479_, ResourceKey<DensityFunction> p_224480_, ResourceKey<DensityFunction> p_224481_, ResourceKey<DensityFunction> p_224482_, ResourceKey<DensityFunction> p_224483_, boolean p_224484_) {
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate = new DensityFunctions.Spline.Coordinate(p_224477_);
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate1 = new DensityFunctions.Spline.Coordinate(p_224478_);
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate2 = new DensityFunctions.Spline.Coordinate(p_256393_.getOrThrow(RIDGES));
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate3 = new DensityFunctions.Spline.Coordinate(p_256393_.getOrThrow(RIDGES_FOLDED));
        DensityFunction densityfunction = registerAndWrap(p_256336_, p_224479_, splineWithBlending(DensityFunctions.add(DensityFunctions.constant((double) -0.50375F), DensityFunctions.spline(TerrainProvider.overworldOffset(densityfunctions$spline$coordinate, densityfunctions$spline$coordinate1, densityfunctions$spline$coordinate3, p_224484_))), DensityFunctions.blendOffset()));
        DensityFunction densityfunction1 = registerAndWrap(p_256336_, p_224480_, splineWithBlending(DensityFunctions.spline(TerrainProvider.overworldFactor(densityfunctions$spline$coordinate, densityfunctions$spline$coordinate1, densityfunctions$spline$coordinate2, densityfunctions$spline$coordinate3, p_224484_)), BLENDING_FACTOR));
        DensityFunction densityfunction2 = registerAndWrap(p_256336_, p_224482_, DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5D, -1.5D), densityfunction));
        DensityFunction densityfunction3 = registerAndWrap(p_256336_, p_224481_, splineWithBlending(DensityFunctions.spline(TerrainProvider.overworldJaggedness(densityfunctions$spline$coordinate, densityfunctions$spline$coordinate1, densityfunctions$spline$coordinate2, densityfunctions$spline$coordinate3, p_224484_)), BLENDING_JAGGEDNESS));
        DensityFunction densityfunction4 = DensityFunctions.mul(densityfunction3, p_224476_.halfNegative());
        DensityFunction densityfunction5 = noiseGradientDensity(densityfunction1, DensityFunctions.add(densityfunction2, densityfunction4));
        p_256336_.register(p_224483_, DensityFunctions.add(densityfunction5, getFunction(p_256393_, BASE_3D_NOISE_OVERWORLD)));
    }

    private static DensityFunction noiseGradientDensity(DensityFunction p_212272_, DensityFunction p_212273_) {
        DensityFunction densityfunction = DensityFunctions.mul(p_212273_, p_212272_);
        return DensityFunctions.mul(DensityFunctions.constant(4.0D), densityfunction.quarterNegative());
    }

    private static DensityFunction splineWithBlending(DensityFunction p_224454_, DensityFunction p_224455_) {
        DensityFunction densityfunction = DensityFunctions.lerp(DensityFunctions.blendAlpha(), p_224455_, p_224454_);
        return DensityFunctions.flatCache(DensityFunctions.cache2d(densityfunction));
    }

    private static DensityFunction registerAndWrap(BootstapContext<DensityFunction> p_256149_, ResourceKey<DensityFunction> p_255905_, DensityFunction p_255856_) {
        return new DensityFunctions.HolderHolder(p_256149_.register(p_255905_, p_255856_));
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> p_256312_, ResourceKey<DensityFunction> p_256077_) {
        return new DensityFunctions.HolderHolder(p_256312_.getOrThrow(p_256077_));
    }

}