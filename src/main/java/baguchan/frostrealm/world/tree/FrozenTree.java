package baguchan.frostrealm.world.tree;

import baguchan.frostrealm.world.gen.FrostTreeFeatures;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import javax.annotation.Nullable;
import java.util.Random;

public class FrozenTree extends AbstractMegaTreeGrower {
	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random p_60014_, boolean p_60015_) {
		return FrostTreeFeatures.FROZEN_TREE;
	}

	protected ConfiguredFeature<?, ?> getConfiguredMegaFeature(Random p_60032_) {
		return FrostTreeFeatures.MEGA_FROZEN_TREE;
	}

}
