package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostChunkGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostChunkGenerators {
    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR = DeferredRegister.create(BuiltInRegistries.CHUNK_GENERATOR, FrostRealm.MODID);

    public static final Supplier<MapCodec<? extends ChunkGenerator>> FROSTREALM = CHUNK_GENERATOR.register(
            "frostrealm", () -> FrostChunkGenerator.FROST_CODEC);
}
