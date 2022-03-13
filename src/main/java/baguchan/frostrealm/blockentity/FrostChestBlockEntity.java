package baguchan.frostrealm.blockentity;

import baguchan.frostrealm.registry.FrostBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FrostChestBlockEntity extends ChestBlockEntity {
	protected FrostChestBlockEntity(BlockEntityType<?> p_155327_, BlockPos p_155328_, BlockState p_155329_) {
		super(p_155327_, p_155328_, p_155329_);
	}

	public FrostChestBlockEntity(BlockPos p_155331_, BlockState p_155332_) {
		super(FrostBlockEntitys.FROST_CHEST.get(), p_155331_, p_155332_);
	}
}