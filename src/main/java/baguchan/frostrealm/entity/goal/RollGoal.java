package baguchan.frostrealm.entity.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class RollGoal extends Goal {
    private final Mob mob;
    @Nullable
    private LivingEntity target;
    private int cooldown = 0;
    private int attackTime = -40;
    private final int attackIntervalMin;
    private final int attackIntervalMax;

    public RollGoal(Mob rangedAttackMob, int attackInterval) {
        this(rangedAttackMob, attackInterval, attackInterval);
    }

    public RollGoal(Mob rangedAttackMob, int attackIntervalMin, int attackIntervalMax) {
        if (!(rangedAttackMob instanceof LivingEntity)) {
            throw new IllegalArgumentException("RollGoal requires Mob");
        } else {
            this.mob = (Mob) rangedAttackMob;
            this.attackIntervalMin = attackIntervalMin;
            this.attackIntervalMax = attackIntervalMax;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (this.cooldown <= 0) {
            if (livingentity != null && livingentity.isAlive() && this.mob.getSensing().hasLineOfSight(livingentity)) {
                this.target = livingentity;
                this.cooldown = 120;
                return true;
            }
        } else {
            this.cooldown--;
            ;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() && this.mob.getPose() != Pose.STANDING;
    }

    @Override
    public void start() {
        super.start();
        this.mob.setPose(Pose.SITTING);
    }

    @Override
    public void stop() {
        this.target = null;
        if (this.attackTime > -40) {
            this.attackTime = -40;
        }
        this.mob.setPose(Pose.STANDING);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        float f = (float) Math.sqrt(d0) / 16F;
        if (++this.attackTime >= 0) {
            if (this.attackTime == 40) {

                this.mob.setPose(Pose.SPIN_ATTACK);
            }

            if (this.attackTime < 40) {
                this.mob.getNavigation().stop();

                this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            } else {
                this.mob.getMoveControl().setWantedPosition(this.target.getX(), this.target.getY(), this.target.getZ(), 1.5F);
            }
            if (this.attackTime == 20 * Mth.floor(10)) {
                this.mob.setPose(Pose.STANDING);
                this.attackTime = -Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
            }
        }
    }
}