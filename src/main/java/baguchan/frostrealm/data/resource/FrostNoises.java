package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FrostNoises {

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(FrostRealm.MODID, name));
    }

    public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> ctx) {
        //register(ctx, FACTOR, -11, 1.0D, 1.0D, 2.0D, 3.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D);
    }

    public static void register(BootstapContext<NormalNoise.NoiseParameters> ctx, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        ctx.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}