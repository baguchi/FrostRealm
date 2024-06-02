package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.entity.hostile.Warpy;
import baguchan.frostrealm.entity.projectile.WarpedCrystalShard;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;

public class WarpyShoot extends Behavior<Warpy> {
    private static final int ATTACK_RANGE_MIN_SQRT = 4;
    private static final int ATTACK_RANGE_MAX_SQRT = 256;
    private static final int UNCERTAINTY_BASE = 5;
    private static final int UNCERTAINTY_MULTIPLIER = 4;
    private static final float PROJECTILE_MOVEMENT_SCALE = 0.7F;
    private static final int SHOOT_INITIAL_DELAY_TICKS = Math.round(15.0F);
    private static final int SHOOT_RECOVER_DELAY_TICKS = Math.round(4.0F);
    private static final int SHOOT_COOLDOWN_TICKS = Math.round(10.0F);

    @VisibleForTesting
    public WarpyShoot() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        FrostMemoryModuleType.SHOOT_COOLDOWN.get(),
                        MemoryStatus.VALUE_ABSENT
                ),
                SHOOT_INITIAL_DELAY_TICKS + 1 + SHOOT_RECOVER_DELAY_TICKS
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel p_312041_, Warpy p_312169_) {
        return p_312169_.getPose() != Pose.STANDING
                ? false
                : p_312169_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(p_312632_ -> isTargetWithinRange(p_312169_, p_312632_)).map(p_312737_ -> {
            if (!p_312737_) {
            }

            return (Boolean) p_312737_;
        }).orElse(false);
    }

    protected boolean canStillUse(ServerLevel p_312535_, Warpy p_312174_, long p_311812_) {
        return p_312174_.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
    }

    protected void start(ServerLevel p_311932_, Warpy p_312618_, long p_311781_) {
        p_312618_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(p_312833_ -> p_312618_.setPose(Pose.SHOOTING));
        p_312618_.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);
        p_312618_.getBrain().setMemoryWithExpiry(FrostMemoryModuleType.SHOOT.get(), Unit.INSTANCE, 18);
    }

    protected void stop(ServerLevel p_312137_, Warpy p_311803_, long p_312309_) {
        if (p_311803_.getPose() == Pose.SHOOTING) {
            p_311803_.setPose(Pose.STANDING);
        }
    }

    protected void tick(ServerLevel p_312907_, Warpy warpy, long p_312804_) {
        Brain<Warpy> brain = warpy.getBrain();
        LivingEntity livingentity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingentity != null && !brain.hasMemoryValue(FrostMemoryModuleType.SHOOT.get())) {
            warpy.lookAt(EntityAnchorArgument.Anchor.EYES, livingentity.position());
            if (isFacingTarget(warpy, livingentity)) {
                warpy.playSound(SoundEvents.ARROW_SHOOT, 1.5F, 1.0F);

                for (int i = 0; i < 6; i++) {
                    double d0 = warpy.distanceToSqr(livingentity);
                    double d4 = Math.sqrt(Math.sqrt(d0)) * 0.25;
                    double d1 = livingentity.getX() - warpy.getX();
                    double d2 = livingentity.getY(0.3) - warpy.getY(0.5);
                    double d3 = livingentity.getZ() - warpy.getZ();
                    WarpedCrystalShard projectile = new WarpedCrystalShard(p_312907_, warpy);
                    projectile.setXRot(projectile.getXRot() - -20.0F);
                    projectile.shoot(warpy.getRandom().triangle(d1, 2.297 * d4), d2, warpy.getRandom().triangle(d3, 2.297 * d4), 1.2F, (float) (5 - p_312907_.getDifficulty().getId() * 4));
                    p_312907_.addFreshEntity(projectile);
                    brain.setMemoryWithExpiry(FrostMemoryModuleType.SHOOT_COOLDOWN.get(), Unit.INSTANCE, 100);
                }
            }

        }
    }

    @VisibleForTesting
    public static boolean isFacingTarget(Warpy p_311845_, LivingEntity p_312453_) {
        Vec3 vec3 = p_311845_.getViewVector(1.0F);
        Vec3 vec31 = p_312453_.position().subtract(p_311845_.position()).normalize();
        return vec3.dot(vec31) > 0.5;
    }

    private static boolean isTargetWithinRange(Warpy p_312114_, LivingEntity p_312647_) {
        double d0 = p_312114_.position().distanceToSqr(p_312647_.position());
        return d0 < 256.0;
    }
}
