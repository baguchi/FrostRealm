package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.IGuardMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GuardAnimationGoal<T extends PathfinderMob & IGuardMob> extends Goal {
    private final T mob;
    private final int leftActionPoint;
    private final int leftStopActionPoint;
    private final int guardLengh;

    private int tick;

    public GuardAnimationGoal(T mob, int leftActionPoint, int leftStopActionPoint, int guardLengh) {
        super();
        this.mob = mob;
        this.leftActionPoint = leftActionPoint;
        this.leftStopActionPoint = leftStopActionPoint;
        this.guardLengh = guardLengh;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob.hurtTime > 0 && this.mob.getRandom().nextInt(30) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.tick > 0;
    }

    @Override
    public void start() {
        super.start();
        this.mob.level().broadcastEntityEvent(this.mob, (byte) 61);
        this.tick = this.guardLengh;
    }

    public void stop() {
        super.stop();
        this.mob.setGuard(false);
    }

    public void tick() {
        if (this.tick == this.leftActionPoint) {
            this.mob.setGuard(true);
        } else if (this.tick == this.leftStopActionPoint) {
            this.mob.setGuard(false);
        }
        --this.tick;
        if (this.mob.getTarget() != null) {
            this.mob.lookAt(this.mob.getTarget(), 10F, 10F);
        }
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
