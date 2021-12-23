package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.BonemealableBlock;

public class SugarBeetBlock extends BeetrootBlock implements BonemealableBlock {

	public SugarBeetBlock(Properties properties) {
		super(properties);
	}

	protected ItemLike getBaseSeedId() {
		return FrostItems.SUGARBEET_SEEDS;
	}
}
