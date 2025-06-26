package baguchan.frostrealm.block;

import baguchan.frostrealm.blockentity.MagmaCoreBlockEntity;
import baguchan.frostrealm.registry.FrostBlockEntitys;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MagmaCoreBlock extends BaseEntityBlock {
    public static final MapCodec<MagmaCoreBlock> CODEC = simpleCodec(MagmaCoreBlock::new);

    @Override
    public MapCodec<? extends MagmaCoreBlock> codec() {
        return CODEC;
    }


    public MagmaCoreBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagmaCoreBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_, FrostBlockEntitys.MAGMA_CORE.get(), MagmaCoreBlockEntity::tick);
    }
}
