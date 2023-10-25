package baguchan.frostrealm.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CounterGoal extends Goal {
    protected final PathfinderMob attacker;
    protected boolean attack;

    protected boolean trigger;
    protected final int leftActionPoint;
    protected final int attackLengh;

    private int ticksUntilNextAttack;

    public CounterGoal(PathfinderMob attacker, int leftActionPoint, int attackLengh) {
        this.attacker = attacker;
        this.leftActionPoint = leftActionPoint;
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
        return this.ticksUntilNextAttack > 0;
    }

    @Override
    public void start() {
        super.start();
        this.ticksUntilNextAttack = attackLengh + 1;
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
            double d0 = this.attacker.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(livingentity, d0);
        }
    }

    protected void checkAndPerformAttack(LivingEntity p_29589_, double p_29590_) {
        double d0 = this.getAttackReachSqr(p_29589_);
        if (this.ticksUntilNextAttack == this.leftActionPoint) {
            if (p_29590_ <= d0) {
                this.attacker.doHurtTarget(p_29589_);
            }
        } else if (p_29590_ <= d0) {
            if (this.ticksUntilNextAttack == this.attackLengh) {
                this.doTheAnimation();
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity p_25556_) {
        return (double) (this.attacker.getBbWidth() * 2.0F * this.attacker.getBbWidth() * 2.0F + p_25556_.getBbWidth());
    }

    protected void doTheAnimation() {
        this.attacker.level().broadcastEntityEvent(this.attacker, (byte) 61);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
