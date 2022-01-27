package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.structure.IglooStructure;
import baguchan.frostrealm.world.gen.structure.RuinsStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostStructures {
	public static final StructureTemplatePool IGLOO_START = Pools.register(new StructureTemplatePool(new ResourceLocation("frostrealm:igloo/igloo_entrance"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single("frostrealm:igloo/igloo_entrance", ProcessorLists.EMPTY), 1)), StructureTemplatePool.Projection.RIGID));
	public static final StructureTemplatePool RUINS_START = Pools.register(new StructureTemplatePool(new ResourceLocation("frostrealm:ruins/main_ruins"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single("frostrealm:ruins/main_ruins", ProcessorLists.EMPTY), 1)), StructureTemplatePool.Projection.RIGID));


	public static final StructureFeature<JigsawConfiguration> IGLOO = new IglooStructure(JigsawConfiguration.CODEC);

	public static final StructureFeature<JigsawConfiguration> RUINS = new RuinsStructure(JigsawConfiguration.CODEC);


	public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> IGLOO_FEATURES = register("frostrealm:igloo", IGLOO.configured(new JigsawConfiguration(() -> {
		return IGLOO_START;
	}, 6)));

	public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> RUINS_FEATURES = register("frostrealm:ruins", RUINS.configured(new JigsawConfiguration(() -> {
		return RUINS_START;
	}, 6)));


	static StructurePieceType setPieceId(StructurePieceType p_67164_, String p_67165_) {
		return Registry.register(Registry.STRUCTURE_PIECE, p_67165_.toLowerCase(Locale.ROOT), p_67164_);
	}

	@SubscribeEvent
	public static void registerStructure(RegistryEvent.Register<StructureFeature<?>> registry) {
		registry.getRegistry().register(IGLOO.setRegistryName("igloo"));
		setupStructure(IGLOO, new StructureFeatureConfiguration(24, 8, 14320045), true);

		registry.getRegistry().register(RUINS.setRegistryName("ruins"));
		setupStructure(RUINS, new StructureFeatureConfiguration(28, 6, 16425045), true);

	}

	private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String p_127268_, ConfiguredStructureFeature<FC, F> p_127269_) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, p_127268_, p_127269_);
	}

	private static String prefix(String path) {
		return "frostrealm:" + path;
	}

	private static <F extends StructureFeature<?>> void setupStructure(F structure, StructureFeatureConfiguration structureSeparationSettings, boolean transformSurroundingLand) {
		StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) {
			StructureFeature.NOISE_AFFECTING_FEATURES =
					ImmutableList.<StructureFeature<?>>builder()
							.addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
							.add(structure)
							.build();
		}

		StructureSettings.DEFAULTS =
				ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
						.putAll(StructureSettings.DEFAULTS)
						.put(structure, structureSeparationSettings)
						.build();
	}

	public static void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel level) {
			ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
			if (chunkGenerator instanceof FlatLevelSource && level.dimension().equals(Level.OVERWORLD)) {
				return;
			}
			StructureSettings worldStructureConfig = chunkGenerator.getSettings();
			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());

			tempMap.putIfAbsent(IGLOO, StructureSettings.DEFAULTS.get(IGLOO));
			tempMap.putIfAbsent(RUINS, StructureSettings.DEFAULTS.get(RUINS));
			worldStructureConfig.structureConfig = tempMap;
		}
	}
}
