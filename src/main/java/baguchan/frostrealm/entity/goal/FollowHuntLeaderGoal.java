package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.Yeti;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class FollowHuntLeaderGoal extends Goal {
    private static final int INTERVAL_TICKS = 200;
    private final Yeti mob;
    private Yeti target;
    private int timeToRecalcPath;
    private int nextStartTick;

    public FollowHuntLeaderGoal(Yeti p_25249_) {
        this.mob = p_25249_;
        this.nextStartTick = this.nextStartTick(p_25249_);
    }

    protected int nextStartTick(Yeti p_25252_) {
        return reducedTickDelay(200 + p_25252_.getRandom().nextInt(200) % 20);
    }

    public boolean canUse() {
        if (this.mob.isHuntLeader() || !this.mob.isHunt()) {
            return false;
        } else if (this.mob.getTarget() != null) {
            return false;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            Predicate<Yeti> predicate = (p_25258_) -> {
                return p_25258_.isHunt() && !p_25258_.isHuntLeader();
            };
            List<Yeti> list = this.mob.level().getEntitiesOfClass(Yeti.class, this.mob.getBoundingBox().inflate(24.0D, 24.0D, 24.0D), predicate);
            if (!list.isEmpty()) {
                this.target = list.get(0);
            }
            return this.target != null;
        }
    }

    public boolean canContinueToUse() {
        return this.target != null && this.target.isAlive() && this.mob.getTarget() == null;
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void stop() {
        this.mob.getNavigation().stop();
    }

    public void tick() {
        if (this.target != null && this.mob.distanceToSqr(this.target) > 32) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                this.mob.getNavigation().moveTo(this.target, 1.0F);
            }
        }
    }
}