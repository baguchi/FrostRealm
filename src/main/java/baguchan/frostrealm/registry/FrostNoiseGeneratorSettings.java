package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostNoiseRouterData;
import baguchan.frostrealm.world.gen.FrostSurfaceRuleData;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.TerrainShaper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSlider;

public class FrostNoiseGeneratorSettings {


	static final NoiseSettings FROST_NOISE_SETTINGS = create(-64, 320, new NoiseSamplingSettings(1.0D, 1.0D, 80.0D, 160.0D), new NoiseSlider(-0.078125D, 2, 8), new NoiseSlider(0.1171875D, 3, 0), 1, 2, TerrainProvider.overworld(false));
	static final NoiseSettings NETHER_NOISE_SETTINGS = create(0, 128, new NoiseSamplingSettings(1.0D, 3.0D, 80.0D, 60.0D), new NoiseSlider(0.9375D, 3, 0), new NoiseSlider(2.5D, 4, -1), 1, 2, TerrainProvider.nether());


	public static final ResourceKey<NoiseGeneratorSettings> FROSTREALM = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));

	public static NoiseGeneratorSettings frostrealm() {
		return new NoiseGeneratorSettings(FROST_NOISE_SETTINGS, FrostBlocks.FRIGID_STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), FrostNoiseRouterData.overworld(FROST_NOISE_SETTINGS, NETHER_NOISE_SETTINGS, false), FrostSurfaceRuleData.frostrealm(), 64, false, true, false, false);
	}

	private static DataResult<NoiseSettings> guardY(NoiseSettings p_158721_) {
		if (p_158721_.minY() + p_158721_.height() > DimensionType.MAX_Y + 1) {
			return DataResult.error("min_y + height cannot be higher than: " + (DimensionType.MAX_Y + 1));
		} else if (p_158721_.height() % 16 != 0) {
			return DataResult.error("height has to be a multiple of 16");
		} else {
			return p_158721_.minY() % 16 != 0 ? DataResult.error("min_y has to be a multiple of 16") : DataResult.success(p_158721_);
		}
	}

	public static NoiseSettings create(int p_212299_, int p_212300_, NoiseSamplingSettings p_212301_, NoiseSlider p_212302_, NoiseSlider p_212303_, int p_212304_, int p_212305_, TerrainShaper p_212306_) {
		NoiseSettings noisesettings = new NoiseSettings(p_212299_, p_212300_, p_212301_, p_212302_, p_212303_, p_212304_, p_212305_, p_212306_);
		guardY(noisesettings).error().ifPresent((p_158719_) -> {
			throw new IllegalStateException(p_158719_.message());
		});
		return noisesettings;
	}

	public static void register(ResourceKey<NoiseGeneratorSettings> p_198263_, NoiseGeneratorSettings p_198264_) {
		BuiltinRegistries.register(BuiltinRegistries.NOISE_GENERATOR_SETTINGS, p_198263_.location(), p_198264_);
	}

	public static void init() {
		register(FROSTREALM, frostrealm());
	}
}