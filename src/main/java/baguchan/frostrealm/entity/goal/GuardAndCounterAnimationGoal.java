package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.IGuardMob;
import net.minecraft.world.entity.PathfinderMob;

import java.util.EnumSet;

public class GuardAndCounterAnimationGoal<T extends PathfinderMob & IGuardMob> extends GuardAnimationGoal<T> {

    public GuardAndCounterAnimationGoal(T mob, boolean onlyTrigger, int guardLengh) {
        super(mob, onlyTrigger, guardLengh);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    protected void stopGuardAndAttackAnimation() {
    }

    public void stop() {
        super.stop();
        this.mob.setGuard(false);
        this.stopGuardAnimation();
        if (!this.stopTrigger) {

        }
        this.stopGuardAndAttackAnimation();
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
