package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.registry.FrostMemoryModuleType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class SlideFly extends Behavior<PathfinderMob> {
    public SlideFly() {
        super(
                Map.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        FrostMemoryModuleType.SHOOT.get(),
                        MemoryStatus.VALUE_ABSENT
                )
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel p_311853_, PathfinderMob p_311894_) {
        return p_311894_.getPose() == Pose.STANDING;
    }

    protected void start(ServerLevel p_312325_, PathfinderMob p_312534_, long p_311789_) {
        LivingEntity livingentity = p_312534_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingentity != null) {
            boolean flag = this.withinInnerCircleRange(p_312534_, livingentity.position());
            Vec3 vec3 = null;
            if (flag) {
                Vec3 vec31 = DefaultRandomPos.getPosAway(p_312534_, 5, 5, livingentity.position());
                if (vec31 != null
                        && hasLineOfSight(p_312534_, vec31)
                        && livingentity.distanceToSqr(vec31.x, vec31.y, vec31.z) > livingentity.distanceToSqr(p_312534_)) {
                    vec3 = vec31;
                }
            }

            if (vec3 == null) {
                vec3 = p_312534_.getRandom().nextBoolean()
                        ? randomPointBehindTarget(livingentity, p_312534_.getRandom())
                        : randomPointInMiddleCircle(p_312534_, livingentity);
            }

            p_312534_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.containing(vec3), 0.6F, 1));
        }
    }

    public static Vec3 randomPointBehindTarget(LivingEntity p_316886_, RandomSource p_316867_) {
        int i = 90;
        float f = p_316886_.yHeadRot + 180.0F + (float) p_316867_.nextGaussian() * 90.0F / 2.0F;
        float f1 = Mth.lerp(p_316867_.nextFloat(), 4.0F, 8.0F);
        Vec3 vec3 = Vec3.directionFromRotation(0.0F, f).scale((double) f1);
        return p_316886_.position().add(vec3);
    }

    public static boolean hasLineOfSight(LivingEntity owner, Vec3 target) {
        Vec3 vec3 = new Vec3(owner.getX(), owner.getY(), owner.getZ());
        return owner.level().clip(new ClipContext(vec3, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner)).getType()
                == HitResult.Type.MISS;
    }

    public boolean withinInnerCircleRange(Mob mob, Vec3 target) {
        Vec3 vec3 = mob.blockPosition().getCenter();
        return target.closerThan(vec3, 4.0, 10.0);
    }


    private static Vec3 randomPointInMiddleCircle(LivingEntity owner, LivingEntity target) {
        Vec3 vec3 = target.position().subtract(owner.position());
        double d0 = vec3.length() - Mth.lerp(owner.getRandom().nextDouble(), 8.0, 4.0);
        Vec3 vec31 = vec3.normalize().multiply(d0, d0, d0);
        return owner.position().add(vec31);
    }
}
