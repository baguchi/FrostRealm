package baguchan.frostrealm.fluid;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostFluidTypes;
import baguchan.frostrealm.registry.FrostFluids;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class HotSpringFluid extends WaterFluid {

    protected HotSpringFluid() {
        super();
    }

    @Override
    public Fluid getFlowing() {
        return FrostFluids.HOT_SPRING_FLOW.get();
    }

    @Override
    public Fluid getSource() {
        return FrostFluids.HOT_SPRING.get();
    }

    @Override
    public FluidType getFluidType() {
        return FrostFluidTypes.HOT_SPRING.get();
    }

    @Override
    public Item getBucket() {
        return Items.AIR;
    }

    @Override
    public int getTickDelay(LevelReader p_76454_) {
        return 5;
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    public boolean isSame(Fluid p_207187_1_) {
        return p_207187_1_ == FrostFluids.HOT_SPRING.get() || p_207187_1_ == FrostFluids.HOT_SPRING_FLOW.get();
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    protected boolean canConvertToSource(Level p_256670_) {
        return false;
    }

    public BlockState createLegacyBlock(FluidState p_204527_1_) {
        return FrostBlocks.HOT_SPRING.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(p_204527_1_)));
    }

    public int getDropOff(LevelReader p_76252_) {
        return 1;
    }

    @Override
    public void animateTick(Level p_230606_, BlockPos p_230607_, FluidState p_230608_, RandomSource p_230609_) {
        if (!p_230608_.isSource() && !p_230608_.getValue(FALLING)) {
            if (p_230609_.nextInt(64) == 0) {
                p_230606_.playLocalSound(
                        (double) p_230607_.getX() + 0.5,
                        (double) p_230607_.getY() + 0.5,
                        (double) p_230607_.getZ() + 0.5,
                        SoundEvents.WATER_AMBIENT,
                        SoundSource.BLOCKS,
                        p_230609_.nextFloat() * 0.25F + 0.75F,
                        p_230609_.nextFloat() + 0.5F,
                        false
                );
            }
        } else if (p_230609_.nextInt(10) == 0) {
            p_230606_.addParticle(
                    ParticleTypes.WHITE_SMOKE,
                    (double) p_230607_.getX() + p_230609_.nextDouble(),
                    (double) p_230607_.getY() + p_230609_.nextDouble(),
                    (double) p_230607_.getZ() + p_230609_.nextDouble(),
                    0.0,
                    0.0,
                    0.0
            );
        }
    }

    @Override
    protected int getSlopeDistance(LevelReader p_76027_, BlockPos p_76028_, int p_76029_, Direction p_76030_, BlockState p_76031_, BlockPos p_76032_, Short2ObjectMap<Pair<BlockState, FluidState>> p_76033_, Short2BooleanMap p_76034_) {
        return 4;
    }

    private void fizz(LevelAccessor p_76213_, BlockPos p_76214_) {
        p_76213_.levelEvent(1501, p_76214_, 0);
    }

    protected void spreadTo(LevelAccessor p_76220_, BlockPos p_76221_, BlockState p_76222_, Direction p_76223_, FluidState p_76224_) {
        FluidState fluidstate = p_76220_.getFluidState(p_76221_);

        if (fluidstate.is(FluidTags.LAVA)) {
            if (p_76222_.getBlock() instanceof LiquidBlock) {
                p_76220_.setBlock(p_76221_, FrostBlocks.FRIGID_STONE.get().defaultBlockState(), 3);
            }

            this.fizz(p_76220_, p_76221_);
            return;
        }

        super.spreadTo(p_76220_, p_76221_, p_76222_, p_76223_, p_76224_);
    }

    public boolean canBeReplacedWith(FluidState p_76233_, BlockGetter p_76234_, BlockPos p_76235_, Fluid p_76236_, Direction p_76237_) {
        return !this.isSame(p_76236_);
    }

    public static class Flowing extends HotSpringFluid {
        public Flowing() {
            super();
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends HotSpringFluid {
        public Source() {
            super();
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }
}