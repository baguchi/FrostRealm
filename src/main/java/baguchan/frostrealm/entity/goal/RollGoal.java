package baguchan.frostrealm.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class RollGoal extends Goal {
    private final Mob mob;
    @Nullable
    private BlockPos target;
    private int cooldown = 0;
    private int attackTime = -20;
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
                Vec3 vec3 = calculateViewVector(0.0F, this.mob.getYRot()).scale(6.0F);
                this.target = BlockPos.containing(vec3.add(livingentity.position()));
                this.cooldown = 120;
                return true;
            }
        } else {
            this.cooldown--;
            ;
        }
        return false;
    }

    protected Vec3 calculateViewVector(float p_20172_, float p_20173_) {
        float f = p_20172_ * (float) (Math.PI / 180.0);
        float f1 = -p_20173_ * (float) (Math.PI / 180.0);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double) (f3 * f4), (double) (-f5), (double) (f2 * f4));
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getPose() != Pose.STANDING;
    }

    @Override
    public void start() {
        super.start();
        this.mob.setPose(Pose.SITTING);
    }

    @Override
    public void stop() {
        this.target = null;
        if (this.attackTime > -20) {
            this.attackTime = -20;
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
            if (this.attackTime == 10) {
                this.mob.setPose(Pose.SPIN_ATTACK);
            }

            if (this.attackTime < 10) {
                this.mob.getNavigation().stop();

                this.mob.getLookControl().setLookAt(this.target.getX(), this.target.getY(), this.target.getZ(), 30.0F, 30.0F);
            } else {
                this.mob.getMoveControl().setWantedPosition(this.target.getX(), this.target.getY(), this.target.getZ(), 1.5F);

                double x = this.target.getX() - this.mob.getX();
                double z = this.target.getZ() - this.mob.getZ();

                if (this.attackTime == 20 * 6 || x * x + z * z < 1.0F) {
                    this.mob.setPose(Pose.STANDING);
                    this.attackTime = -Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
                }
            }
        }
    }
}