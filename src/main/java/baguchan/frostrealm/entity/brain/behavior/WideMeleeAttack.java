package baguchan.frostrealm.entity.brain.behavior;

import baguchi.bagus_lib.entity.brain.behaviors.AttackWithAnimation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WideMeleeAttack<E extends PathfinderMob> extends AttackWithAnimation<E> {

    protected final double range;

    public WideMeleeAttack(int actionPoint, int attackLength, int cooldownBetweenAttacks, float speed, double range) {
        super(actionPoint, attackLength, cooldownBetweenAttacks, speed);
        this.range = range;
    }


    @Override
    protected void doAttack(E attacker, LivingEntity living) {
        Level var3 = attacker.level();

        if (var3 instanceof ServerLevel serverLevel) {
            List<LivingEntity> entitiesHit = serverLevel.getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox(attacker));
            for (LivingEntity entity : entitiesHit) {
                if (entity != attacker) {
                    if (attacker.canAttack(entity) && !attacker.isAlliedTo(entity) && attacker.isWithinMeleeAttackRange(entity)) {
                        Vec3 vec3 = entity.position();
                        Vec3 yVector = attacker.calculateViewVector(0, attacker.getYHeadRot());
                        Vec3 vec32 = vec3.subtract(attacker.position());
                        Vec3 vec33 = (new Vec3(vec32.x, (double) vec32.y, vec32.z)).normalize();
                        double d0 = Math.acos(vec33.dot(yVector));
                        if (resolveAttack(d0, range)) {
                            attacker.doHurtTarget(serverLevel, entity);
                        }
                    }
                }
            }
        }

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

        return aabb.inflate(Math.sqrt((double) 2.04F) - (double) 0.6F, (double) 0.0F, Math.sqrt((double) 2.04F) - (double) 0.6F);
    }

    public boolean resolveAttack(double yRot, double yRotAttackRange) {
        if (yRot > (double) (((float) Math.PI / 180F) * yRotAttackRange)) {
            return false;
        } else {
            return true;
        }
    }

}
