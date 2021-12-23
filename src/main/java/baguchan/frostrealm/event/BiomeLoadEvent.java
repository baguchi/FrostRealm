package baguchan.frostrealm.event;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID)
public class BiomeLoadEvent {

	private static ResourceLocation name(String name) {
		return new ResourceLocation(FrostRealm.MODID, name);
	}


	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void loadingBiome(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		/*if (event.getName().toString().contains("frostrealm:frigid_forest")) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_POPPY);
		}

		if (event.getName().toString().contains("frostrealm:tundra")) {
			//generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.FROSTROOT_TREES_PLAINS);
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_WILLOW);
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_ARTIC_POPPY);
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_BEAR_BERRY);
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FrostPlacements.PATCH_TUNDRA_GRASS);
		}
		if (event.getName().toString().contains("frostrealm")) {
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_UPPER);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.FROST_CRYSTAL_ORE_LOWER);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_LOWER);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrostOrePlacements.GLIMMER_ORE_SMALL);
		}*/
	}
}
