package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.biome.FrostrealmBiomeBuilder;
import baguchan.frostrealm.world.gen.FrostNoiseRouterData;
import baguchan.frostrealm.world.gen.FrostSurfaceRuleData;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.OptionalLong;

public class FrostDimensionSettings {
	public static final DeferredRegister<NoiseGeneratorSettings> NOISE_GENERATORS = DeferredRegister.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, FrostRealm.MODID);
	public static final DeferredRegister<DimensionType> DIMENSION_TYPES = DeferredRegister.create(Registry.DIMENSION_TYPE_REGISTRY, FrostRealm.MODID);

	public static final RegistryObject<NoiseGeneratorSettings> FROST_NOISE_GEN = NOISE_GENERATORS.register("frostrealm_noise", FrostDimensionSettings::frostrealm);
	public static final RegistryObject<DimensionType> FROST_DIM_TYPE = DIMENSION_TYPES.register("frostrealm_type", FrostDimensionSettings::frostDimType);


	public static final ResourceLocation EFFECTS = new ResourceLocation(FrostRealm.MODID, "renderer");

	static final NoiseSettings FROST_NOISE_SETTINGS = create(-80, 384, 1, 2);

	public static NoiseGeneratorSettings frostrealm() {
		return new NoiseGeneratorSettings(FROST_NOISE_SETTINGS, FrostBlocks.FRIGID_STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), FrostNoiseRouterData.frostrealm(BuiltinRegistries.DENSITY_FUNCTION, false, false), FrostSurfaceRuleData.frostrealm(), (new FrostrealmBiomeBuilder()).spawnTarget(), 64, false, true, false, false);
	}

	private static DimensionType frostDimType() {
		return new DimensionType(
				OptionalLong.of(13000),
				true, //skylight
				false, //ceiling
				false, //ultrawarm
				true, //natural
				1.0D, //coordinate scale
				true, //bed works
				false, //respawn anchor works
				-80,
				400,
				400, // Logical Height
				BlockTags.INFINIBURN_OVERWORLD, //infiburn
				FrostRealm.prefix("renderer"), // DimensionRenderInfo
				0f, // Wish this could be set to -0.05 since it'll make the world truly blacked out if an area is not sky-lit (see: Dark Forests) Sadly this also messes up night vision so it gets 0
				new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 7)
		);
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

	public static NoiseSettings create(int p_224526_, int p_224527_, int p_224528_, int p_224529_) {
		NoiseSettings noisesettings = new NoiseSettings(p_224526_, p_224527_, p_224528_, p_224529_);
		guardY(noisesettings).error().ifPresent((p_158719_) -> {
			throw new IllegalStateException(p_158719_.message());
		});
		return noisesettings;
	}


}