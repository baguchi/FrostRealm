package baguchan.frostrealm.world.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HeightBlockStateConfiguration(BlockStateProvider block,
                                            UniformInt baseHeight) implements FeatureConfiguration {
    public static final Codec<HeightBlockStateConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(HeightBlockStateConfiguration::block),
            UniformInt.MAP_CODEC.fieldOf("base_height").forGetter(HeightBlockStateConfiguration::baseHeight)
    ).apply(instance, HeightBlockStateConfiguration::new));
}