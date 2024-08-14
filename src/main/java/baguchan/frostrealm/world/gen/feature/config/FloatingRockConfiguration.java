package baguchan.frostrealm.world.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FloatingRockConfiguration(BlockStateProvider block, UniformFloat baseRadius,
                                        UniformInt additionalRadius) implements FeatureConfiguration {
    public static final Codec<FloatingRockConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FloatingRockConfiguration::block),
            UniformFloat.CODEC.fieldOf("base_radius").forGetter(FloatingRockConfiguration::baseRadius),
            UniformInt.CODEC.fieldOf("additional_radius").forGetter(FloatingRockConfiguration::additionalRadius)
    ).apply(instance, FloatingRockConfiguration::new));
}