package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.boss.Seeker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SeekerAttackGoal extends WideMeleeAttackGoal {
    public final Seeker seeker;
    public final double speed;

    public SeekerAttackGoal(Seeker seeker, double speed, int actionPoint, int attackLength, double range) {
        super(seeker, speed, actionPoint, attackLength, range);
        this.seeker = seeker;
        this.speed = speed;
    }

    @Override
    public boolean canUse() {
        return this.seeker.getState() != Seeker.SeekerState.BREATH_STOP && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            if (actionPoint > attackTicks && this.actionPoint - 2 < attackTicks) {
                this.mob.getNavigation().stop();
                this.seeker.lookAt(livingentity, 1.25F, 1.25F);
                this.mob.moveRelative(1.0F, new Vec3(0, 0, 1F));

                this.checkAndPerformAttack(livingentity);
            } else if (!this.attack || this.attackTicks < 2) {
                super.tick();
            } else {
                this.mob.getNavigation().stop();
                this.seeker.lookAt(livingentity, 1.25F, 1.25F);
                this.checkAndPerformAttack(livingentity);
            }
        }

    }

    @Override
    protected void doTheAnimation() {
        super.doTheAnimation();

        this.seeker.setState(Seeker.SeekerState.ATTACK);
    }

    @Override
    public AABB getAttackBoundingBox(PathfinderMob attacker) {
        return super.getAttackBoundingBox(attacker).inflate(2.0F, 2.0F, 2.0F);
    }
}
