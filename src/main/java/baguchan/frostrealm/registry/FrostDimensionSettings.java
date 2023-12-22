package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.biome.FrostrealmBiomeBuilder;
import baguchan.frostrealm.world.gen.FrostNoiseRouterData;
import baguchan.frostrealm.world.gen.FrostSurfaceRuleData;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

import java.util.OptionalLong;

public class FrostDimensionSettings {
	public static final ResourceLocation EFFECTS = new ResourceLocation(FrostRealm.MODID, "renderer");

	static final NoiseSettings FROST_NOISE_SETTINGS = create(-80, 384, 1, 2);

	public static final ResourceKey<NoiseGeneratorSettings> FROSTREALM_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(FrostRealm.MODID, "frostrealm_noise"));

	public static final ResourceKey<LevelStem> FROSTREALM_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, FrostRealm.prefix("frostrealm"));


	public static NoiseGeneratorSettings frostrealmNoise(BootstapContext<NoiseGeneratorSettings> p_256365_) {
		return new NoiseGeneratorSettings(FROST_NOISE_SETTINGS, FrostBlocks.FRIGID_STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), FrostNoiseRouterData.frostrealm(p_256365_.lookup(Registries.DENSITY_FUNCTION), p_256365_.lookup(Registries.NOISE)), FrostSurfaceRuleData.frostrealm(), (new FrostrealmBiomeBuilder()).spawnTarget(), 63, false, true, false, false);
	}

	public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> p_256365_) {
		p_256365_.register(FROSTREALM_NOISE, frostrealmNoise(p_256365_));
	}

	public static void bootstrapDimensionType(BootstapContext<DimensionType> p_256376_) {
		p_256376_.register(FrostDimensions.FROSTREALM_TYPE, frostDimType());
	}

	public static void bootstrapLevelStem(BootstapContext<LevelStem> context) {
		HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
		HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

		HolderGetter<MultiNoiseBiomeSourceParameterList> multiNoiseBiomeSourceParameterLists = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
		Holder.Reference<MultiNoiseBiomeSourceParameterList> reference = multiNoiseBiomeSourceParameterLists.getOrThrow(FrostBiomeSources.FROSTREALM);

		NoiseBasedChunkGenerator wrappedChunkGenerator =
				new NoiseBasedChunkGenerator(
						MultiNoiseBiomeSource.createFromPreset(reference),
						noiseGenSettings.getOrThrow(FrostDimensionSettings.FROSTREALM_NOISE));

		LevelStem stem = new LevelStem(
				dimTypes.getOrThrow(FrostDimensions.FROSTREALM_TYPE),
				wrappedChunkGenerator);
		context.register(FROSTREALM_LEVEL_STEM, stem);
	}

	private static DimensionType frostDimType() {
		return new DimensionType(
				OptionalLong.empty(),
				true, //skylight
				false, //ceiling
				false, //ultrawarm
				true, //natural
				1.0D, //coordinate scale
				true, //bed works
				false, //respawn anchor works
				-64,
				384,
				384, // Logical Height
				BlockTags.INFINIBURN_OVERWORLD, //infiburn
				FrostRealm.prefix("renderer"), // DimensionRenderInfo
				0f,
				new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
		);
	}

	public static NoiseSettings create(int p_224526_, int p_224527_, int p_224528_, int p_224529_) {
		NoiseSettings noisesettings = new NoiseSettings(p_224526_, p_224527_, p_224528_, p_224529_);
		return noisesettings;
	}


}