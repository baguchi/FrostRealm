package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBiomeSources;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostMusics;
import baguchan.frostrealm.world.biome.FrostrealmBiomeBuilder;
import baguchan.frostrealm.world.gen.FrostChunkGenerator;
import baguchan.frostrealm.world.gen.FrostNoiseRouterData;
import baguchan.frostrealm.world.gen.FrostSurfaceRuleData;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.timeline.Timeline;

import java.util.Optional;

public class FrostDimensionSettings {
	public static final Identifier EFFECTS = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "renderer");

	static final NoiseSettings FROST_NOISE_SETTINGS = create(-80, 384, 1, 2);

	public static final ResourceKey<NoiseGeneratorSettings> FROSTREALM_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS, Identifier.fromNamespaceAndPath(FrostRealm.MODID, "frostrealm_noise"));

	public static final ResourceKey<LevelStem> FROSTREALM_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, FrostRealm.prefix("frostrealm"));


	public static NoiseGeneratorSettings frostrealmNoise(BootstrapContext<NoiseGeneratorSettings> p_256365_) {
		return new NoiseGeneratorSettings(FROST_NOISE_SETTINGS, FrostBlocks.FRIGID_STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), FrostNoiseRouterData.frostrealm(p_256365_.lookup(Registries.DENSITY_FUNCTION), p_256365_.lookup(Registries.NOISE)), FrostSurfaceRuleData.frostrealm(), (new FrostrealmBiomeBuilder()).spawnTarget(), 63, false, true, false, false);
	}

	public static void bootstrapNoise(BootstrapContext<NoiseGeneratorSettings> p_256365_) {
		p_256365_.register(FROSTREALM_NOISE, frostrealmNoise(p_256365_));
	}

	public static void bootstrapDimensionType(BootstrapContext<DimensionType> p_256376_) {
		p_256376_.register(FrostDimensions.FROSTREALM_TYPE, frostDimType(p_256376_));
	}

	public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
		HolderGetter<MapCodec<? extends ChunkGenerator>> chunk = context.lookup(Registries.CHUNK_GENERATOR);
		HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

		HolderGetter<MultiNoiseBiomeSourceParameterList> multiNoiseBiomeSourceParameterLists = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
		Holder.Reference<MultiNoiseBiomeSourceParameterList> reference = multiNoiseBiomeSourceParameterLists.getOrThrow(FrostBiomeSources.FROSTREALM);

        FrostChunkGenerator wrappedChunkGenerator =
                new FrostChunkGenerator(
						MultiNoiseBiomeSource.createFromPreset(reference),
						noiseGenSettings.getOrThrow(FrostDimensionSettings.FROSTREALM_NOISE));

		LevelStem stem = new LevelStem(
				dimTypes.getOrThrow(FrostDimensions.FROSTREALM_TYPE),
				wrappedChunkGenerator);
		context.register(FROSTREALM_LEVEL_STEM, stem);
	}

	private static DimensionType frostDimType(BootstrapContext<DimensionType> context) {
		HolderGetter<Timeline> timelines = context.lookup(Registries.TIMELINE);
		HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
		EnvironmentAttributeMap environmentattributemap = EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.FOG_COLOR, -4138753)
                .set(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(0.8F))
                .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.white(0.8F))
                .set(EnvironmentAttributes.CLOUD_HEIGHT, 192.33F)
                .set(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FrostMusics.FROSTREALM))
                .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
                .build();
		return new DimensionType(
                false,
                true,
                false,
                1.0,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                0.0F,
                new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0),
                DimensionType.Skybox.OVERWORLD,
                DimensionType.CardinalLightType.DEFAULT,
                environmentattributemap,
				timelines.getOrThrow(TimelineTags.IN_OVERWORLD),
				Optional.of(clocks.getOrThrow(WorldClocks.OVERWORLD))
        );
	}

	public static NoiseSettings create(int p_224526_, int p_224527_, int p_224528_, int p_224529_) {
		NoiseSettings noisesettings = new NoiseSettings(p_224526_, p_224527_, p_224528_, p_224529_);
		return noisesettings;
	}


}