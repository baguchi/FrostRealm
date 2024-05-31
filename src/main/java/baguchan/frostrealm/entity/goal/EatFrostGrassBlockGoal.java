package baguchan.frostrealm.entity.goal;

import bagu_chan.bagus_lib.entity.goal.TimeConditionGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatFrostGrassBlockGoal extends TimeConditionGoal {
    private static final UniformInt TIME_BETWEEN = UniformInt.of(400, 600);
    private static final UniformInt TIME_BETWEEN_COOLDOWN = UniformInt.of(400, 600);


    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(FrostBlocks.COLD_GRASS.get());
    private final Mob mob;
    private final Level level;
    private int eatAnimationTick;

    public EatFrostGrassBlockGoal(PathfinderMob p_25207_) {
        super(p_25207_, TIME_BETWEEN, TIME_BETWEEN_COOLDOWN);
        this.mob = p_25207_;
        this.level = p_25207_.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean isMatchCondition() {
        BlockPos blockpos = this.mob.blockPosition();
        if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
            return true;
        } else {
            return this.level.getBlockState(blockpos.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get());
        }
    }

    @Override
    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(40);
        this.level.broadcastEntityEvent(this.mob, (byte) 10);
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.eatAnimationTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    @Override
    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
            BlockPos blockpos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                if (net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level, this.mob)) {
                    this.level.destroyBlock(blockpos, false);
                }

                this.mob.ate();
            } else {
                BlockPos blockpos1 = blockpos.below();
                if (this.level.getBlockState(blockpos1).is(FrostBlocks.FROZEN_GRASS_BLOCK.get())) {
                    if (net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level, this.mob)) {
                        this.level.levelEvent(2001, blockpos1, Block.getId(FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState()));
                        this.level.setBlock(blockpos1, FrostBlocks.FROZEN_DIRT.get().defaultBlockState(), 2);
                    }

                    this.mob.ate();
                }
            }
        }
    }
}
