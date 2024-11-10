package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.entity.SnowChargeMob;
import baguchan.frostrealm.entity.projectile.FlyingBlockEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;

public class SnowBallAttack<E extends Mob & SnowChargeMob, T extends LivingEntity> extends Behavior<E> {
    private static final int TIMEOUT = 1200;
    private int attackDelay;
    private SnowState snowState = SnowState.UNCHARGED;

    public SnowBallAttack() {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), 1200);
    }

    protected boolean checkExtraStartConditions(ServerLevel p_22778_, E p_22779_) {
        LivingEntity livingentity = getAttackTarget(p_22779_);
        return BehaviorUtils.canSee(p_22779_, livingentity) && (p_22779_.getBrain().hasMemoryValue(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)
                || livingentity.closerThan(p_22779_, (double) 16) && !livingentity.closerThan(p_22779_, (double) 8));
    }

    protected boolean canStillUse(ServerLevel p_22781_, E p_22782_, long p_22783_) {
        return p_22782_.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && this.checkExtraStartConditions(p_22781_, p_22782_);
    }

    protected void tick(ServerLevel p_22794_, E p_22795_, long p_22796_) {
        LivingEntity livingentity = getAttackTarget(p_22795_);
        this.lookAtTarget(p_22795_, livingentity);
        this.crossbowAttack(p_22795_, livingentity);
    }

    protected void stop(ServerLevel p_22805_, E p_22806_, long p_22807_) {
        p_22806_.ejectPassengers();
    }

    private void crossbowAttack(E p_22787_, LivingEntity p_22788_) {
        if (this.snowState == SnowState.UNCHARGED) {
            if (p_22787_.isSnowCharge()) {
                this.snowState = SnowState.READY_TO_ATTACK;
                this.attackDelay = 20 + p_22787_.getRandom().nextInt(20);
            } else {
                this.snowState = SnowState.CHARGING;
            }
        } else if (this.snowState == SnowState.CHARGING) {
            this.snowState = SnowState.CHARGED;
            p_22787_.setSnowCharge(true);
            this.attackDelay = 35;
        } else if (this.snowState == SnowState.CHARGED) {
            this.attackDelay--;
            if (this.attackDelay == 0) {
                this.makeBlock(p_22787_);
                this.snowState = SnowState.READY_TO_ATTACK;
                this.attackDelay = 20 + p_22787_.getRandom().nextInt(20);
            }
        } else if (this.snowState == SnowState.READY_TO_ATTACK) {
            this.attackDelay--;
            if (this.attackDelay == 0) {
                this.performBlockAttack(p_22787_, p_22788_);
                this.snowState = SnowState.UNCHARGED;
                p_22787_.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, 80);
            }
        }
    }


    public void makeBlock(E entity) {
        if (entity.level().getBlockState(entity.blockPosition()).is(Blocks.SNOW)) {
            FlyingBlockEntity flyingBlockEntity = new FlyingBlockEntity(entity.level(), entity, Blocks.SNOW_BLOCK.defaultBlockState());
            flyingBlockEntity.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), 0.0F);
            entity.startRiding(flyingBlockEntity);
            entity.level().addFreshEntity(flyingBlockEntity);
        } else if (entity.level().getBlockState(entity.getOnPos()).is(BlockTags.DIRT) || entity.level().getBlockState(entity.getOnPos()).is(Blocks.SNOW_BLOCK)) {
            FlyingBlockEntity flyingBlockEntity = new FlyingBlockEntity(entity.level(), entity, entity.level().getBlockState(entity.getOnPos()));
            flyingBlockEntity.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), 0.0F);
            entity.startRiding(flyingBlockEntity);
            entity.level().addFreshEntity(flyingBlockEntity);
        } else {
            this.snowState = SnowState.UNCHARGED;
            entity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, 60);
        }
    }

    public void performBlockAttack(E entity, LivingEntity living) {
        if (entity.getPassengers() instanceof FlyingBlockEntity flyingBlockEntity) {
            flyingBlockEntity.unRide();
            double d0 = living.getX() - entity.getX();
            double d1 = living.getEyeY() - entity.getEyeY();
            double d2 = living.getZ() - entity.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            flyingBlockEntity.shoot(d0, d1 + d3 * 0.35, d2, 0.8F, 8.0F);

        }
    }

    private void lookAtTarget(Mob p_22798_, LivingEntity p_22799_) {
        p_22798_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(p_22799_, true));
    }

    private static LivingEntity getAttackTarget(LivingEntity p_22785_) {
        return p_22785_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }


    static enum SnowState {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;
    }
}
