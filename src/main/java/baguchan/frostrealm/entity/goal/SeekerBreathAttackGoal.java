package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.boss.Seeker;
import baguchan.frostrealm.registry.FrostDamageType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class SeekerBreathAttackGoal extends Goal {
    public final Seeker seeker;
    protected int attackTicks;

    public SeekerBreathAttackGoal(Seeker seeker) {
        this.seeker = seeker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.seeker.getTarget();
        return livingentity != null && this.seeker.isEnoughNoAttack() && this.seeker.hasLineOfSight(livingentity) && this.seeker.distanceToSqr(livingentity) >= 5 * 5 && this.seeker.distanceToSqr(livingentity) < 16 * 16;

    }

    @Override
    public void start() {
        super.start();

        this.attackTicks = 0;
        this.seeker.getNavigation().stop();

        this.seeker.setState(Seeker.SeekerState.BREATH);
        this.seeker.playSound(SoundEvents.PLAYER_BREATH);
    }


    public void tick() {
        LivingEntity livingentity = this.seeker.getTarget();
        if (livingentity != null) {
            this.seeker.lookAt(livingentity, 1.25F, 1.25F);
            this.seeker.getMoveControl().strafe(-0.5F, 0.0F);
            this.checkAndPerformAttack(this.seeker, livingentity, getServerLevel(this.seeker.level()));
        }

    }

    @Override
    public boolean canContinueToUse() {
        return this.attackTicks < 60;
    }

    protected void checkAndPerformAttack(Seeker entity, LivingEntity target, ServerLevel serverLevel) {
        if (this.isTimeToAttack()) {
            if (this.canPerformAttack(entity, target)) {
                this.doAttack(entity, target);
            }
        }
        this.attackTicks = Mth.clamp(this.attackTicks + 1, 0, 80);


    }

    protected void doAttack(Seeker attacker, LivingEntity living) {
        Level var3 = attacker.level();

        if (var3 instanceof ServerLevel serverLevel) {
            List<LivingEntity> entitiesHit = serverLevel.getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox(attacker));
            for (LivingEntity entity : entitiesHit) {
                if (entity != attacker) {
                    if (attacker.canAttack(entity) && !attacker.isAlliedTo(entity)) {
                        Vec3 vec3 = entity.getEyePosition();
                        Vec3 yVector = attacker.calculateViewVector(attacker.getXRot(), attacker.getYHeadRot());
                        Vec3 vec32 = vec3.subtract(attacker.getEyePosition());
                        Vec3 vec33 = (new Vec3(vec32.x, vec32.y, vec32.z)).normalize();
                        double d0 = Math.acos(vec33.dot(yVector));
                        if (resolveAttack(d0, 15)) {
                            living.setTicksFrozen(living.getTicksFrozen() + 5);
                            living.hurtServer(serverLevel, living.damageSources().source(FrostDamageType.FREEZE_BREATH, attacker), 2.0F);
                        }
                    }
                }
            }
        }

    }

    public boolean resolveAttack(double yRot, double yRotAttackRange) {
        return !(yRot > (((float) Math.PI / 180F) * yRotAttackRange));
    }

    protected boolean isTimeToAttack() {
        return this.attackTicks > 0.75F * 20 && this.attackTicks <= 3.75F * 20;
    }

    protected boolean canPerformAttack(Seeker entity, LivingEntity p_301160_) {
        return entity.getSensing().hasLineOfSight(p_301160_);
    }

    public AABB getAttackBoundingBox(Seeker attacker) {
        Entity entity = attacker.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = attacker.getBoundingBox();
            aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            aabb = attacker.getBoundingBox();
        }

        return aabb.inflate(Math.sqrt(2.04F) - (double) 0.6F, 0.0F, Math.sqrt(2.04F) - (double) 0.6F).inflate(16, 16, 16);
    }

    @Override
    public void stop() {
        super.stop();
        this.seeker.setState(Seeker.SeekerState.PRE_ATTACK);
    }
}
