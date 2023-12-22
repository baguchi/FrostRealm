package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FrostNoises {
    public static final ResourceKey<NormalNoise.NoiseParameters> ISLANDS_HEIGHT = createKey("islands_height");
    public static final ResourceKey<NormalNoise.NoiseParameters> ISLANDS_BOTTOM_HEIGHT = createKey("islands_bottom_height");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(FrostRealm.MODID, name));
    }

    public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> ctx) {
        register(ctx, ISLANDS_HEIGHT, -9, 1.0D, 1.0D, 1.0D);
        register(ctx, ISLANDS_BOTTOM_HEIGHT, -7, 1.0D, 1.0D, 1.0D);
    }


    private static void registerBiomeNoises(BootstapContext<NormalNoise.NoiseParameters> p_256503_, int p_236479_, ResourceKey<NormalNoise.NoiseParameters> p_236482_, ResourceKey<NormalNoise.NoiseParameters> p_236483_) {
        register(p_256503_, p_236482_, -9 + p_236479_, 1.0D, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D);
        register(p_256503_, p_236483_, -9 + p_236479_, 1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
    }

    public static void register(BootstapContext<NormalNoise.NoiseParameters> ctx, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        ctx.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}