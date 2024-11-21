package baguchan.frostrealm.block;

import baguchan.frostrealm.entity.animal.SilkMoon;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class SilkMoonCocoonBlock extends Block {
    public static final int MAX_HATCH_LEVEL = 2;
    public static final int MIN_EGGS = 1;
    public static final int MAX_EGGS = 3;
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    public SilkMoonCocoonBlock(Properties p_57759_) {
        super(p_57759_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)));
    }

    @Override
    public void onPlace(BlockState p_221227_, Level p_221228_, BlockPos p_221229_, BlockState p_221230_, boolean p_221231_) {
        p_221228_.scheduleTick(p_221229_, this, 1200);
    }

    @Override
    public void fallOn(Level p_154845_, BlockState p_154846_, BlockPos p_154847_, Entity p_154848_, float p_154849_) {
        if (!(p_154848_ instanceof Zombie)) {
            this.destroyEgg(p_154845_, p_154846_, p_154847_, p_154848_, 5);
        }

        super.fallOn(p_154845_, p_154846_, p_154847_, p_154848_, p_154849_);
    }

    private void destroyEgg(Level p_154851_, BlockState p_154852_, BlockPos p_154853_, Entity p_154854_, int p_154855_) {
        if (!p_154851_.isClientSide && p_154851_.random.nextInt(p_154855_) == 0 && p_154852_.is(FrostBlocks.SILK_MOON_COCOON.get())) {
            this.decreaseEggs(p_154851_, p_154853_, p_154852_);
        }
    }

    private void decreaseEggs(Level p_57792_, BlockPos p_57793_, BlockState p_57794_) {
        p_57792_.playSound(null, p_57793_, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + p_57792_.random.nextFloat() * 0.2F);
        p_57792_.destroyBlock(p_57793_, false);
        ItemStack itemstack = new ItemStack(Items.STRING);
        ItemEntity itementity = new ItemEntity(p_57792_, (double) p_57793_.getX(), (double) p_57793_.getY(), (double) p_57793_.getZ(), itemstack);
        itementity.setDefaultPickUpDelay();
        p_57792_.addFreshEntity(itementity);
    }

    @Override
    public BlockState playerWillDestroy(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
        super.playerWillDestroy(p_49852_, p_49853_, p_49854_, p_49855_);
        return p_49854_;
    }

	@Override
    public void tick(BlockState p_221194_, ServerLevel p_221195_, BlockPos p_221196_, RandomSource p_221197_) {
		this.hatchEgg(p_221194_, p_221195_, p_221196_, p_221197_);
    }

    private void hatchEgg(BlockState p_221194_, ServerLevel p_221182_, BlockPos p_221183_, RandomSource p_221184_) {
		p_221182_.playSound((Player) null, p_221183_, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (this.shouldUpdateHatchLevel(p_221182_, p_221183_)) {
            int i = p_221194_.getValue(HATCH);
            if (i < 2) {
                p_221182_.playSound(null, p_221183_, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + p_221184_.nextFloat() * 0.2F);
                p_221182_.setBlock(p_221183_, p_221194_.setValue(HATCH, Integer.valueOf(i + 1)), 2);
            } else {
                p_221182_.playSound(null, p_221183_, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + p_221184_.nextFloat() * 0.2F);
                p_221182_.removeBlock(p_221183_, false);

                p_221182_.levelEvent(2001, p_221183_, Block.getId(p_221194_));
                SilkMoon silkMoonWorm = FrostEntities.SILK_MOON.get().create(p_221182_, EntitySpawnReason.BREEDING);
                silkMoonWorm.moveTo((double) p_221183_.getX() + 0.5, p_221183_.getY(), (double) p_221183_.getZ() + 0.5D, 0.0F, 0.0F);
                p_221182_.addFreshEntity(silkMoonWorm);

            }
        }
    }

    private void destroyBlock(Level p_221191_, BlockPos p_221192_) {
        p_221191_.destroyBlock(p_221192_, false);
    }


    private boolean shouldUpdateHatchLevel(Level p_57766_, BlockPos p_57816_) {
        float f = p_57766_.getTimeOfDay(1.0F);
        return p_57766_.getRawBrightness(p_57816_, 0) <= 8 || p_57766_.isNight();
    }

	@Override
    public void playerDestroy(Level p_57771_, Player p_57772_, BlockPos p_57773_, BlockState p_57774_, @Nullable BlockEntity p_57775_, ItemStack p_57776_) {
        super.playerDestroy(p_57771_, p_57772_, p_57773_, p_57774_, p_57775_, p_57776_);
        this.decreaseEggs(p_57771_, p_57773_, p_57774_);
    }

	@Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(HATCH);
    }
}
