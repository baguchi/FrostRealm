package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.boss.Seeker;
import baguchan.frostrealm.registry.FrostDamageType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class SeekerBreathGoal extends Goal {
    private final Seeker mob;
    private int cooldown = 0;
    private int attackTime;

    public SeekerBreathGoal(Seeker rangedAttackMob) {
        this.mob = rangedAttackMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));

    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (this.mob.getState() == Seeker.SeekerState.PRE_ATTACK) {
            if (this.cooldown <= 0) {
                this.cooldown = 100 + this.mob.getRandom().nextInt(100);

                if (livingentity != null && livingentity.isAlive() && this.mob.getSensing().hasLineOfSight(livingentity)) {
                    this.mob.lookAt(livingentity, 3F, 3F);

                    return true;
                }
            } else {
                this.cooldown--;
            }
        }
        return false;
    }


    @Override
    public boolean canContinueToUse() {
        return this.mob.getState() == Seeker.SeekerState.BREATH_PRE || this.mob.getState() == Seeker.SeekerState.BREATH;
    }

    @Override
    public void start() {
        super.start();
        this.attackTime = 0;
        this.mob.setState(Seeker.SeekerState.BREATH_PRE);
        this.mob.playSound(SoundEvents.PLAYER_BREATH, 2.0F, 1.0F);
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            this.mob.lookAt(livingentity, 3F, 3F);
            if (this.mob.getState() == Seeker.SeekerState.BREATH) {
                doAttack();
            }
        }

        if (this.attackTime >= 160 && this.mob.getState() != Seeker.SeekerState.BREATH_STOP) {
            this.mob.setState(Seeker.SeekerState.BREATH_STOP);
        } else {
            ++this.attackTime;
        }

    }


    protected void doAttack() {
        Level var3 = this.mob.level();

        if (var3 instanceof ServerLevel serverLevel) {
            List<LivingEntity> entitiesHit = serverLevel.getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox(this.mob));
            for (LivingEntity entity : entitiesHit) {
                if (entity != this.mob) {
                    if (this.mob.canAttack(entity) && !this.mob.isAlliedTo(entity)) {
                        Vec3 vec3 = entity.getEyePosition();
                        Vec3 yVector = this.mob.calculateViewVector(this.mob.getXRot(), this.mob.getYHeadRot());
                        Vec3 vec32 = vec3.subtract(this.mob.getEyePosition());
                        Vec3 vec33 = (new Vec3(vec32.x, vec32.y, vec32.z)).normalize();
                        double d0 = Math.acos(vec33.dot(yVector));
                        if (resolveAttack(d0, 60)) {
                            entity.setTicksFrozen(entity.getTicksFrozen() + 3);
                            entity.hurt(this.mob.damageSources().source(FrostDamageType.FREEZE_BREATH, this.mob), 3.0F);
                        }
                    }
                }
            }
        }

    }

    public AABB getAttackBoundingBox(PathfinderMob attacker) {
        Entity entity = attacker.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = attacker.getBoundingBox();
            aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            aabb = attacker.getBoundingBox();
        }

        return aabb.inflate(Math.sqrt(2.04F) - (double) 0.6F, 0.0F, Math.sqrt(2.04F) - (double) 0.6F).inflate(12.0F);
    }

    public boolean resolveAttack(double yRot, double yRotAttackRange) {
        return !(yRot > (((float) Math.PI / 180F) * yRotAttackRange));
    }
}