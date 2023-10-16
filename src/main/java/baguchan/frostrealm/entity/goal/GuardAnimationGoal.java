package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.IGuardMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GuardAnimationGoal<T extends PathfinderMob & IGuardMob> extends Goal {
    protected final T mob;
    protected final int guardLengh;

    protected final boolean onlyTrigger;
    protected boolean trigger;
    protected boolean stopTrigger;

    protected int tick;

    public GuardAnimationGoal(T mob, boolean onlyTrigger, int guardLengh) {
        super();
        this.mob = mob;
        this.onlyTrigger = onlyTrigger;
        this.guardLengh = guardLengh;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.onlyTrigger) {
            if (this.mob.hurtTime > 0 && this.mob.getRandom().nextInt(80) == 0) {
                return true;
            }
        }
        return this.trigger;
    }

    @Override
    public boolean canContinueToUse() {
        return this.tick > 0 && !this.stopTrigger;
    }

    @Override
    public void start() {
        super.start();
        this.mob.setGuard(true);
        this.guardAnimation();

        this.tick = this.guardLengh;
    }

    protected void guardAnimation() {
    }

    protected void stopGuardAnimation() {
    }

    public void stop() {
        super.stop();
        this.mob.setGuard(false);
        this.stopGuardAnimation();
        this.trigger = false;
        this.stopTrigger = false;
    }

    public void tick() {
        --this.tick;
        if (this.mob.getTarget() != null) {
            this.mob.lookAt(this.mob.getTarget(), 10F, 10F);
        }
    }

    public void trigger() {
        this.trigger = true;
    }

    public void stopTrigger() {
        this.stopTrigger = true;
    }


    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
