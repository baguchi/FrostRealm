package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.entity.boss.Seeker;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SeekerMeleeAttack<E extends Seeker> extends WideMeleeAttack<E> {
    public SeekerMeleeAttack(int actionPoint, int attackLength, int cooldownBetweenAttacks, float speed, double range) {
        super(actionPoint, attackLength, cooldownBetweenAttacks, speed, range);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E mob) {
        LivingEntity livingentity = this.getAttackTarget(mob);
        return livingentity != null && super.checkExtraStartConditions(level, mob) && mob.distanceToSqr(livingentity) < 5 * 5;
    }

    @Override
    protected boolean canStillUse(ServerLevel p_22545_, E p_22546_, long p_22547_) {
        LivingEntity livingentity = this.getAttackTarget(p_22546_);

        return livingentity != null && super.canStillUse(p_22545_, p_22546_, p_22547_) && p_22546_.distanceToSqr(livingentity) < 5 * 5;
    }

    protected void tick(ServerLevel p_22551_, E p_22552_, long p_22553_) {
        LivingEntity livingentity = this.getAttackTarget(p_22552_);
        if (livingentity != null) {
            if (actionPoint > attackTicks && this.actionPoint - 2 < attackTicks) {
                //p_22552_.lookAt(EntityAnchorArgument.Anchor.EYES, livingentity.position());

                p_22552_.moveRelative(1.0F, new Vec3(0, 0, 1F));
            } else if (actionPoint - 5 >= attackTicks && this.attack) {
                p_22552_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            } else if (!this.attack) {
                p_22552_.lookAt(EntityAnchorArgument.Anchor.EYES, livingentity.position());
                p_22552_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(livingentity.position(), this.speed, 0));

            }
            this.checkAndPerformAttack(p_22552_, livingentity, p_22551_);
        }

    }

    @Override
    public AABB getAttackBoundingBox(E attacker) {
        return super.getAttackBoundingBox(attacker).inflate(1.0F, 0, 1.0F);
    }

    private LivingEntity getAttackTarget(E p_23533_) {
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent() ? (LivingEntity) p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get() : null;
    }
}
