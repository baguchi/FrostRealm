package baguchan.frostrealm.world.placement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FrostOrePlacements {
	public static final Holder<PlacedFeature> FROST_CRYSTAL_ORE_UPPER = PlacementUtils.register(prefix("frost_crystal_ore_upper"), FrostConfiguredFeatures.ORE_FROST_CRYSTAL, commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(92), VerticalAnchor.top())));
	public static final Holder<PlacedFeature> FROST_CRYSTAL_ORE_LOWER = PlacementUtils.register(prefix("frost_crystal_ore_lower"), FrostConfiguredFeatures.ORE_FROST_CRYSTAL_BURIED, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.belowTop(120))));

	public static final Holder<PlacedFeature> GLIMMER_ORE_LOWER = PlacementUtils.register(prefix("glimmer_ore_lower"), FrostConfiguredFeatures.ORE_GLIMMERROCK, commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(0))));
	public static final Holder<PlacedFeature> GLIMMER_ORE_SMALL = PlacementUtils.register(prefix("glimmer_ore_small"), FrostConfiguredFeatures.ORE_GLIMMERROCK_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(48))));
	public static final Holder<PlacedFeature> GLIMMER_ORE_EXTRA = PlacementUtils.register(prefix("glimmer_ore_extra"), FrostConfiguredFeatures.ORE_FROST_CRYSTAL_BURIED, commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256))));


	public static String prefix(String name) {
		return FrostRealm.MODID + ":" + name;
	}


	public static void init() {

	}

	private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
		return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
		return orePlacement(CountPlacement.of(p_195344_), p_195345_);
	}

	private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
		return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
	}
}
