package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.entity.boss.Seeker;
import baguchan.frostrealm.registry.FrostDamageType;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SeekerBreathAttack<E extends Seeker> extends Behavior<E> {
    protected int attackTicks;

    public SeekerBreathAttack() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, FrostMemoryModuleType.BREATH_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT));

    }

    protected boolean checkExtraStartConditions(ServerLevel level, E mob) {
        LivingEntity livingentity = this.getAttackTarget(mob);
        return livingentity != null && mob.isEnoughNoAttack() && mob.hasLineOfSight(livingentity) && mob.distanceToSqr(livingentity) >= 5 * 5 && mob.distanceToSqr(livingentity) < 16 * 16;
    }

    protected void start(ServerLevel p_23524_, E p_23525_, long p_23526_) {
        LivingEntity livingentity = this.getAttackTarget(p_23525_);
        p_23525_.getLookControl().setLookAt(livingentity, 80F, 80F);
        this.attackTicks = 0;
        p_23525_.setState(Seeker.SeekerState.BREATH);
    }


    protected void tick(ServerLevel p_22551_, E p_22552_, long p_22553_) {
        super.tick(p_22551_, p_22552_, p_22553_);
        LivingEntity livingentity = this.getAttackTarget(p_22552_);
        if (livingentity != null) {
            p_22552_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            this.checkAndPerformAttack(p_22552_, livingentity, p_22551_);
        }

    }

    protected boolean canStillUse(ServerLevel p_22545_, E p_22546_, long p_22547_) {
        return this.attackTicks < 60;
    }

    protected void checkAndPerformAttack(E entity, LivingEntity target, ServerLevel serverLevel) {
        if (this.isTimeToAttack()) {
            if (this.canPerformAttack(entity, target)) {
                this.doAttack(entity, target);
            }
        }
        this.attackTicks = Mth.clamp(this.attackTicks + 1, 0, 60);


    }

    protected void doAttack(E attacker, LivingEntity living) {
        Level var3 = attacker.level();

        if (var3 instanceof ServerLevel serverLevel) {
            List<LivingEntity> entitiesHit = serverLevel.getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox(attacker));
            for (LivingEntity entity : entitiesHit) {
                if (entity != attacker) {
                    if (attacker.canAttack(entity) && !attacker.isAlliedTo(entity)) {
                        Vec3 vec3 = entity.position();
                        Vec3 yVector = attacker.calculateViewVector(attacker.getXRot(), attacker.getYHeadRot());
                        Vec3 vec32 = vec3.subtract(attacker.position());
                        Vec3 vec33 = (new Vec3(vec32.x, (double) vec32.y, vec32.z)).normalize();
                        double d0 = Math.acos(vec33.dot(yVector));
                        if (resolveAttack(d0, 15)) {
                            living.setTicksFrozen(living.getTicksFrozen() + 400);
                            living.hurtServer(serverLevel, living.damageSources().source(FrostDamageType.FREEZE_BREATH, attacker), 10.0F);
                        }
                    }
                }
            }
        }

    }

    public boolean resolveAttack(double yRot, double yRotAttackRange) {
        if (yRot > (double) (((float) Math.PI / 180F) * yRotAttackRange)) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean isTimeToAttack() {
        return this.attackTicks == 2.5F * 20;
    }

    protected boolean canPerformAttack(E entity, LivingEntity p_301160_) {
        return entity.getSensing().hasLineOfSight(p_301160_);
    }

    public AABB getAttackBoundingBox(E attacker) {
        Entity entity = attacker.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = attacker.getBoundingBox();
            aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            aabb = attacker.getBoundingBox();
        }

        return aabb.inflate(Math.sqrt((double) 2.04F) - (double) 0.6F, (double) 0.0F, Math.sqrt((double) 2.04F) - (double) 0.6F).inflate(16, 16, 16);
    }

    protected void stop(ServerLevel p_22548_, E p_22549_, long p_22550_) {
        super.stop(p_22548_, p_22549_, p_22550_);
        p_22549_.getBrain().setMemoryWithExpiry(FrostMemoryModuleType.BREATH_COOLDOWN.get(), Unit.INSTANCE, 300L);
        p_22549_.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, 20);
    }

    private LivingEntity getAttackTarget(E p_23533_) {
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent() ? (LivingEntity) p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get() : null;
    }
}
