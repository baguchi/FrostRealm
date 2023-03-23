package baguchan.frostrealm.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class MoveAttackerGoal extends Goal {
    private final PathfinderMob attacker;

    public MoveAttackerGoal(PathfinderMob p_25319_) {
        this.attacker = p_25319_;
    }

    public boolean canUse() {
        return this.attacker.getTarget() != null && this.attacker.hasLineOfSight(this.attacker.getTarget());
    }

    public void tick() {
        if (this.attacker.getTarget() != null) {
            this.attacker.getNavigation().moveTo(this.attacker.getTarget(), 1.1D);
        }
    }
}