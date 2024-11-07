package baguchan.frostrealm.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class AppearGoal extends Goal {
    private final PathfinderMob mob;

    private int tick;
    private final int maxTick;

    public AppearGoal(PathfinderMob p_25919_, int maxTick) {
        this.mob = p_25919_;
        this.maxTick = maxTick;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.mob.getPose() == Pose.EMERGING;
    }

    public void start() {
        this.mob.getNavigation().stop();
        this.tick = 0;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        if (++tick > this.maxTick) {
            this.mob.setPose(Pose.STANDING);
        }
    }
}