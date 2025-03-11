package baguchan.frostrealm.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CounterGoal extends Goal {
    protected final PathfinderMob attacker;
    protected boolean attack;

    protected boolean trigger;
    protected final int actionPoint;
    protected final int attackLengh;

    private int ticksUntilNextAttack;

    public CounterGoal(PathfinderMob attacker, int actionPoint, int attackLengh) {
        this.attacker = attacker;
        this.actionPoint = actionPoint;
        this.attackLengh = attackLengh;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public void trigger() {
        this.trigger = true;
    }

    @Override
    public boolean canUse() {
        return this.trigger;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksUntilNextAttack <= this.attackLengh;
    }

    @Override
    public void start() {
        super.start();
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        super.stop();
        this.attack = false;
        this.attacker.setAggressive(false);
        this.trigger = false;
    }

    public void tick() {
        LivingEntity livingentity = this.attacker.getTarget();
        if (livingentity != null) {
            this.attacker.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);

            this.checkAndPerformAttack(livingentity);
            this.ticksUntilNextAttack++;
        }
    }

    protected void checkAndPerformAttack(LivingEntity p_29589_) {
        if (this.ticksUntilNextAttack == this.actionPoint) {
            if (this.canPerformAttack(p_29589_)) {
                this.attacker.doHurtTarget(getServerLevel(this.attacker), p_29589_);
            }
        } else if (this.canPerformAttack(p_29589_)) {
            if (this.ticksUntilNextAttack == 0) {
                this.doTheAnimation();
            }
        }
    }

    protected boolean canPerformAttack(LivingEntity p_301160_) {
        return this.attacker.isWithinMeleeAttackRange(p_301160_) && this.attacker.getSensing().hasLineOfSight(p_301160_);
    }

    protected void doTheAnimation() {
        this.attacker.level().broadcastEntityEvent(this.attacker, (byte) 61);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
