package baguchan.frostrealm.entity.goal;

import baguchi.bagus_lib.entity.goal.AnimateAttackGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

public class NonMoveAnimateAttackGoal extends AnimateAttackGoal {
    public NonMoveAnimateAttackGoal(PathfinderMob attacker, int actionPoint, int attackLength) {
        super(attacker, 0.0F, actionPoint, attackLength);
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.canPerformAttack(target);
        }
    }
}
