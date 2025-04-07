package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.path.SunAvoidFlyingPathNavigation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class RestrictFlyingSunGoal extends Goal {
    private final PathfinderMob mob;

    public RestrictFlyingSunGoal(PathfinderMob p_25861_) {
        this.mob = p_25861_;
    }

    @Override
    public boolean canUse() {
        return this.mob.level().isBrightOutside() && this.mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && this.mob.getNavigation() instanceof SunAvoidFlyingPathNavigation;
    }

    @Override
    public void start() {
        ((SunAvoidFlyingPathNavigation) this.mob.getNavigation()).setAvoidSun(true);
    }

    @Override
    public void stop() {
        if (this.mob.getNavigation() instanceof SunAvoidFlyingPathNavigation) {
            ((SunAvoidFlyingPathNavigation) this.mob.getNavigation()).setAvoidSun(false);
        }
    }
}