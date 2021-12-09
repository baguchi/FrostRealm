package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostSurfaceRuleData;
import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

import java.util.Map;
import java.util.Optional;

public class FrostNoiseGeneratorSettings {

	public static final ResourceKey<NoiseGeneratorSettings> FROSTREALM = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));

	public static NoiseGeneratorSettings frostrealm() {
		Map<StructureFeature<?>, StructureFeatureConfiguration> map = Maps.newHashMap();
		return new NoiseGeneratorSettings(new StructureSettings(Optional.empty(), map), NoiseSettings.create(-64, 384, new NoiseSamplingSettings(1.0D, 1.0D, 80.0D, 160.0D), new NoiseSlider(-0.078125D, 2, 8), new NoiseSlider(0.1171875D, 3, 0), 1, 2, false, false, false, TerrainProvider.overworld(false)), FrostBlocks.FRIGID_STONE.defaultBlockState(), Blocks.WATER.defaultBlockState(), FrostSurfaceRuleData.frostrealm(), 63, false, true, true, true, true, false);
	}

	public static void register(ResourceKey<NoiseGeneratorSettings> p_198263_, NoiseGeneratorSettings p_198264_) {
		BuiltinRegistries.register(BuiltinRegistries.NOISE_GENERATOR_SETTINGS, p_198263_.location(), p_198264_);
	}

	public static void init() {
		register(FROSTREALM, frostrealm());
	}
}