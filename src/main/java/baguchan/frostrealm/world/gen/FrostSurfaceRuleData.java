package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FrostSurfaceRuleData {
	private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
	private static final SurfaceRules.RuleSource FROZEN_GRASS_BLOCK = makeStateRule(FrostBlocks.FROZEN_GRASS_BLOCK.get());
	private static final SurfaceRules.RuleSource FROZEN_DIRT = makeStateRule(FrostBlocks.FROZEN_DIRT.get());
	private static final SurfaceRules.RuleSource FRIGID_STONE = makeStateRule(FrostBlocks.FRIGID_STONE.get());
    private static final SurfaceRules.RuleSource FRIGID_STONE_GRASS = makeStateRule(FrostBlocks.FRIGID_GRASS_BLOCK.get());
	private static final SurfaceRules.RuleSource SHERBET_SAND = makeStateRule(FrostBlocks.SHERBET_SAND.get());
	private static final SurfaceRules.RuleSource SHERBET_SANDSTONE = makeStateRule(FrostBlocks.SHERBET_SANDSTONE.get());

	private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(FrostBlocks.PERMA_SLATE.get());
	private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
	private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);

	private static SurfaceRules.RuleSource makeStateRule(Block p_194811_) {
		return SurfaceRules.state(p_194811_.defaultBlockState());
	}

	public static SurfaceRules.RuleSource frostrealm() {
		return frostrealmLike(true, false, true);
	}

	public static SurfaceRules.RuleSource frostrealmLike(boolean p_198381_, boolean p_198382_, boolean p_198383_) {
		ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
		if (p_198382_) {
			builder.add(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
		}

		if (p_198383_) {
			builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
		}

		SurfaceRules.ConditionSource surfacerules$conditionsource8 = SurfaceRules.waterBlockCheck(0, 0);

		SurfaceRules.RuleSource surfacerules$rulesource5 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D), SurfaceRules.ifTrue(surfacerules$conditionsource8, POWDER_SNOW));
		SurfaceRules.RuleSource powderSnow = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.ifTrue(SurfaceRules.isBiome(FrostBiomes.GLACIERS), SurfaceRules.sequence(surfacerules$rulesource5, SurfaceRules.ifTrue(surfacerules$conditionsource8, SNOW_BLOCK))));


		SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), FROZEN_GRASS_BLOCK), FROZEN_DIRT);

        SurfaceRules.RuleSource surfaceStone = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), FRIGID_STONE_GRASS));

        SurfaceRules.RuleSource skyLike = SurfaceRules.ifTrue(SurfaceRules.isBiome(FrostBiomes.CRYSTAL_FALL), SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surfaceStone)
        ));
		SurfaceRules.RuleSource grassLike = SurfaceRules.ifTrue(SurfaceRules.isBiome(FrostBiomes.FRIGID_FOREST, FrostBiomes.TUNDRA, FrostBiomes.FROSTBITE_FOREST), SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
				SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, FROZEN_DIRT)
		));

		SurfaceRules.RuleSource sandRule = SurfaceRules.sequence(
				SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR, SHERBET_SAND),
				SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SHERBET_SAND),
				SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SHERBET_SANDSTONE),
				SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SHERBET_SANDSTONE)
		);


		SurfaceRules.RuleSource sandLike = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.ifTrue(SurfaceRules.isBiome(FrostBiomes.SHERBET_DESERT), sandRule));

		SurfaceRules.RuleSource surfacerules$rulesource9 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), grassLike);
        SurfaceRules.RuleSource surfacerules$rulesource10 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), skyLike);

		builder.add(surfacerules$rulesource9);
        builder.add(surfacerules$rulesource10);
		builder.add(sandLike);
		builder.add(powderSnow);
		builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), DEEPSLATE));

		return SurfaceRules.sequence(builder.build().toArray((p_198379_) -> {
			return new SurfaceRules.RuleSource[p_198379_];
		}));
	}

	private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
		return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
	}
}