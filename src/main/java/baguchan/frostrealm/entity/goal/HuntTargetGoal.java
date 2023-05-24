package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.HuntMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class HuntTargetGoal<T extends LivingEntity, M extends Mob & HuntMob> extends NearestAttackableTargetGoal<T> {
    public HuntTargetGoal(M p_27966_, Class<T> tClass) {
        super(p_27966_, tClass, 10, true, false, null);
    }

    public boolean canUse() {
        return this.canTarget() && super.canUse();
    }

    public boolean canContinueToUse() {
        boolean flag = this.canTarget();
        if (flag && this.mob.getTarget() != null) {
            return super.canContinueToUse();
        } else {
            this.targetMob = null;
            return false;
        }
    }

    private boolean canTarget() {
        M huntMob = (M) this.mob;
        return !huntMob.isBaby() && !huntMob.isHunted() && huntMob.isHunt();
    }
}