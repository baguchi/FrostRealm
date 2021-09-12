package baguchan.frostrealm.world.tree;

import baguchan.frostrealm.registry.FrostConfiguredFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;

import javax.annotation.Nullable;
import java.util.Random;

public class FrostrootTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random p_60014_, boolean p_60015_) {
		return p_60014_.nextInt(8) == 0 ? FrostConfiguredFeatures.FANCY_FROSTROOT : FrostConfiguredFeatures.FROSTROOT;
	}
}
