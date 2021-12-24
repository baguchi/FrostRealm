package baguchan.frostrealm.block;

import baguchan.frostrealm.entity.Yeti;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class StarDustCrystalBlock extends Block {
	public StarDustCrystalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void playerWillDestroy(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
		super.playerWillDestroy(p_49852_, p_49853_, p_49854_, p_49855_);
		Yeti.angerNearbyYeti(p_49855_, false);
	}
}
