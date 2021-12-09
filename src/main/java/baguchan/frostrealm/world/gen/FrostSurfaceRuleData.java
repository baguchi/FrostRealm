package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FrostSurfaceRuleData {
	private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
	private static final SurfaceRules.RuleSource FROZEN_GRASS_BLOCK = makeStateRule(FrostBlocks.FROZEN_GRASS_BLOCK);
	private static final SurfaceRules.RuleSource STONE = makeStateRule(FrostBlocks.FRIGID_STONE);

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

		SurfaceRules.ConditionSource surfacerules$conditionsource7 = SurfaceRules.waterBlockCheck(0, 0);
		SurfaceRules.ConditionSource surfacerules$conditionsource9 = SurfaceRules.hole();
		builder.add(SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource9, SurfaceRules.ifTrue(surfacerules$conditionsource7, AIR))));
		SurfaceRules.RuleSource surfacerules$rulesource1 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(FrostBiomes.TUNDRA, FrostBiomes.FRIGID_FOREST), FROZEN_GRASS_BLOCK))));
		SurfaceRules.RuleSource surfacerules$rulesource2 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(FrostBiomes.TUNDRA), FROZEN_GRASS_BLOCK))));


		builder.add(surfacerules$rulesource1);
		builder.add(surfacerules$rulesource2);

		return SurfaceRules.sequence(builder.build().toArray((p_198379_) -> {
			return new SurfaceRules.RuleSource[p_198379_];
		}));
	}
}