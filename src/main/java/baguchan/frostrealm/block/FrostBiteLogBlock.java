package baguchan.frostrealm.block;

import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class FrostBiteLogBlock extends RotatedPillarBlock {
    public static final MapCodec<FrostBiteLogBlock> CODEC = simpleCodec(FrostBiteLogBlock::new);
    public static final BooleanProperty WAXED = BooleanProperty.create("wax");

    @Override
    public MapCodec<? extends FrostBiteLogBlock> codec() {
        return CODEC;
    }


    public FrostBiteLogBlock(Properties p_55926_) {
        super(p_55926_);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(WAXED, false));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack p_316304_, BlockState p_316362_, Level p_316459_, BlockPos p_316366_, Player p_316132_, InteractionHand p_316595_, BlockHitResult p_316140_) {
        if (p_316304_.is(FrostItems.CRYONITE_CREAM) && !p_316362_.getValue(WAXED)) {
            p_316459_.setBlock(p_316366_, p_316362_.setValue(WAXED, true), 3);
            p_316459_.playLocalSound(p_316366_, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1, false);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(p_316304_, p_316362_, p_316459_, p_316366_, p_316132_, p_316595_, p_316140_);
    }

    @Override
    protected void randomTick(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_) {
        super.randomTick(p_222954_, p_222955_, p_222956_, p_222957_);
        if (!p_222954_.getValue(WAXED)) {
            if (p_222955_.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)
                    && p_222957_.nextInt(6000) < p_222955_.getDifficulty().getId()) {


                Direction direction = Direction.getRandom(p_222957_);
                BlockPos blockPos = p_222956_.offset(direction.getUnitVec3i());
                float f = p_222955_.getLightLevelDependentMagicValue(blockPos);
                if (f < 0.5F) {
                    if (p_222955_.getBlockState(blockPos).isAir()) {
                        FrostEntities.ROOT_DEER.get().spawn(p_222955_, blockPos, EntitySpawnReason.NATURAL);
                    }
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55933_) {
        p_55933_.add(AXIS, WAXED);
    }

}
