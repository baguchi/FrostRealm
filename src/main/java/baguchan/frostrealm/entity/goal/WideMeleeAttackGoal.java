package baguchan.frostrealm.entity.goal;

import baguchi.bagus_lib.entity.goal.AnimateAttackGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WideMeleeAttackGoal extends AnimateAttackGoal {
    protected final double range;

    public WideMeleeAttackGoal(PathfinderMob attacker, double speed, int actionPoint, int attackLength, double range) {
        super(attacker, speed, actionPoint, attackLength);
        this.range = range;
    }


    @Override
    protected void doAttack(LivingEntity living) {
        Level var3 = this.mob.level();

        if (var3 instanceof ServerLevel serverLevel) {
            List<LivingEntity> entitiesHit = serverLevel.getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox(this.mob));
            for (LivingEntity entity : entitiesHit) {
                if (entity != this.mob) {
                    if (this.mob.canAttack(entity) && !this.mob.isAlliedTo(entity)) {
                        Vec3 viewVector = this.mob.calculateViewVector(0.0F, this.mob.getYHeadRot());
                        Vec3 vectorTo = entity.position().subtract(this.mob.position());
                        vectorTo = new Vec3(vectorTo.x, 0.0, vectorTo.z).normalize();
                        double angle = Math.acos(vectorTo.dot(viewVector));

                        if (resolveAttack(angle, range)) {
                            this.mob.doHurtTarget(serverLevel, entity);
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

        return aabb.inflate(Math.sqrt(2.04F) - (double) 0.6F, 0.0F, Math.sqrt(2.04F) - (double) 0.6F);
    }

    public boolean resolveAttack(double yRot, double yRotAttackRange) {
        return !(yRot > (((float) Math.PI / 180F) * yRotAttackRange));
    }
}
