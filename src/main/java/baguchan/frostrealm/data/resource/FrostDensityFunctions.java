package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FrostDensityFunctions {
    private static final DensityFunction BLENDING_JAGGEDNESS = DensityFunctions.zero();
    private static final DensityFunction BLENDING_FACTOR = DensityFunctions.constant(10.0D);
    public static final ResourceKey<DensityFunction> FACTOR = createKey("factor");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("depth");
    private static final ResourceKey<DensityFunction> SHIFT_X = createVannilaKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createVannilaKey("shift_z");
    public static final ResourceKey<DensityFunction> OFFSET = createVannilaKey("overworld/offset");

    public static final ResourceKey<DensityFunction> CONTINENTS = createVannilaKey("overworld/continents");
    public static final ResourceKey<DensityFunction> EROSION = createVannilaKey("overworld/erosion");
    public static final ResourceKey<DensityFunction> RIDGES = createVannilaKey("overworld/ridges");
    public static final ResourceKey<DensityFunction> RIDGES_FOLDED = createVannilaKey("overworld/ridges_folded");

    private static ResourceKey<DensityFunction> createVannilaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(name));
    }

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(FrostRealm.MODID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseHolderGetter = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> density = context.lookup(Registries.DENSITY_FUNCTION);
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate = new DensityFunctions.Spline.Coordinate(density.getOrThrow(CONTINENTS));
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate1 = new DensityFunctions.Spline.Coordinate(density.getOrThrow(EROSION));
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate2 = new DensityFunctions.Spline.Coordinate(density.getOrThrow(RIDGES));
        DensityFunctions.Spline.Coordinate densityfunctions$spline$coordinate3 = new DensityFunctions.Spline.Coordinate(density.getOrThrow(RIDGES_FOLDED));
        DensityFunction densityfunction = new DensityFunctions.HolderHolder(density.getOrThrow(OFFSET));

        DensityFunction densityfunction1 = registerAndWrap(context, FACTOR, splineWithBlending(DensityFunctions.spline(TerrainProvider.overworldFactor(densityfunctions$spline$coordinate, densityfunctions$spline$coordinate1, densityfunctions$spline$coordinate2, densityfunctions$spline$coordinate3, true)), BLENDING_FACTOR));
        DensityFunction densityfunction2 = registerAndWrap(context, DEPTH, DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5D, -1.5D), densityfunction));
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