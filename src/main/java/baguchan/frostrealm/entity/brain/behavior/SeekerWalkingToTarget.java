package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.entity.boss.Seeker;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SeekerWalkingToTarget<E extends Seeker> extends Behavior<E> {

    public SeekerWalkingToTarget() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));
    }

    protected boolean checkExtraStartConditions(ServerLevel level, E mob) {
        LivingEntity livingentity = this.getAttackTarget(mob);
        return livingentity != null && mob.hasLineOfSight(livingentity);
    }

    protected void start(ServerLevel p_23524_, E p_23525_, long p_23526_) {
        LivingEntity livingentity = this.getAttackTarget(p_23525_);
        if (p_23525_.getState().canWalk()) {
            p_23525_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(livingentity, 1.15F, 1));
        }
        if (p_23525_.getState().canLook()) {
            p_23525_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(livingentity, true));
        }
    }

    private LivingEntity getAttackTarget(E p_23533_) {
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent() ? (LivingEntity) p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get() : null;
    }
}
