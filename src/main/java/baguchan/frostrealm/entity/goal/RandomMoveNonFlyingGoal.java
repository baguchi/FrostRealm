package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.IFlyMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class RandomMoveNonFlyingGoal<T extends PathfinderMob & IFlyMob> extends WaterAvoidingRandomStrollGoal {
    protected final T flyMob;

    public RandomMoveNonFlyingGoal(T flyMob, double p_25754_, int p_25755_) {
        super(flyMob, p_25754_, p_25755_);
        this.flyMob = flyMob;
    }

    @Override
    public boolean canUse() {
        return !flyMob.isFlying() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !flyMob.isFlying() && super.canContinueToUse();
    }
}
