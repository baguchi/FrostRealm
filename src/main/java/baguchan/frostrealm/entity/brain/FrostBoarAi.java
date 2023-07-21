package baguchan.frostrealm.entity.brain;

import baguchan.frostrealm.entity.FrostBoar;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.entity.ai.behavior.BehaviorUtils.isBreeding;

public class FrostBoarAi {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(6, 16);
    private static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(10, 30);

    private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_LINE_OF_SIGHT = TargetingConditions.forCombat().range(20.0D).ignoreLineOfSight();
    private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_AND_LINE_OF_SIGHT = TargetingConditions.forCombat().range(20.0D).ignoreLineOfSight().ignoreInvisibilityTesting();


    public static boolean isEntityAttackableIgnoringLineOfSight(LivingEntity p_182378_, LivingEntity p_182379_) {
        return p_182378_.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, p_182379_) ? ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_AND_LINE_OF_SIGHT.test(p_182378_, p_182379_) : ATTACK_TARGET_CONDITIONS_IGNORE_LINE_OF_SIGHT.test(p_182378_, p_182379_);
    }

    public static Brain<?> makeBrain(FrostBoar frostBoar, Brain<FrostBoar> p_149291_) {
        initCoreActivity(p_149291_);
        initIdleActivity(p_149291_);
        initFightActivity(p_149291_);
        initRetreatActivity(p_149291_);
        p_149291_.setCoreActivities(ImmutableSet.of(Activity.CORE));
        p_149291_.setDefaultActivity(Activity.IDLE);
        p_149291_.useDefaultActivity();
        return p_149291_;
    }

    public static void updateActivity(FrostBoar boar) {
        Brain<FrostBoar> brain = boar.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity) null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
        Activity activity1 = brain.getActiveNonCoreActivity().orElse((Activity) null);
        if (activity != activity1) {
            getSoundForCurrentActivity(boar).ifPresent(boar::playSound);
        }

        boar.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    private static void initFightActivity(Brain<FrostBoar> p_149303_) {
        p_149303_.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(StopAttackingIfTargetInvalid.create(), SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(FrostBoarAi::getSpeedModifierChasing), BehaviorBuilder.triggerIf(FrostBoar::isAdult, MeleeAttack.create(40)), EraseMemoryIf.<Mob>create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
    }

    private static void initCoreActivity(Brain<FrostBoar> p_149307_) {
        p_149307_.addActivity(Activity.CORE, 0, ImmutableList.of(StartAttacking.create(FrostBoarAi::findNearestValidAttackTarget), new Swim(0.8F), new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    private static void initIdleActivity(Brain<FrostBoar> p_149309_) {
        p_149309_.addActivityWithConditions(Activity.IDLE, ImmutableList.of(Pair.of(0, new AnimalMakeLove(FrostEntities.FROST_BOAR.get(), 0.75F)), Pair.of(1, new FollowTemptation(FrostBoarAi::getSpeedModifier)), Pair.of(3, createIdleMovementBehaviors()), Pair.of(0, createLookBehaviors()), Pair.of(0, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.85F))), ImmutableSet.of());
    }

    private static void initRetreatActivity(Brain<FrostBoar> p_34616_) {
        p_34616_.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.3F, 15, false), createIdleMovementBehaviors(), SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)), EraseMemoryIf.create(FrostBoarAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<LivingEntity> createLookBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60)), 2), Pair.of(SetEntityLookTargetSometimes.create(6.0F, UniformInt.of(30, 60)), 2)));
    }

    private static RunOne<FrostBoar> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.stroll(0.8F), 2), Pair.of(SetWalkTargetFromLookTarget.create(0.8F, 3), 2), Pair.of(new DoNothing(30, 60), 1)));
    }

    private static boolean wantsToStopFleeing(FrostBoar p_34618_) {
        return p_34618_.isAdult() && isEnoughFrostBoarOrHealth(p_34618_);
    }

    private static boolean isEnoughFrostBoarOrHealth(FrostBoar p_34623_) {
        if (p_34623_.isBaby()) {
            return false;
        } else {
            int j = p_34623_.getBrain().getMemory(FrostMemoryModuleType.FROST_BOAR_COUNT.get()).orElse(0) + 1;
            int i = p_34623_.getBrain().getMemory(FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get()).orElse(0);

            return j > i + 2 || (p_34623_.getHealth() > p_34623_.getMaxHealth() / 2);
        }
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(FrostBoar p_34611_) {
        boolean flag = !isBreeding(p_34611_);

        if (flag) {
            Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(p_34611_, MemoryModuleType.ANGRY_AT);
            if (optional.isPresent() && isEntityAttackableIgnoringLineOfSight(p_34611_, optional.get())) {
                return optional;
            } else {
                Optional<List<LivingEntity>> listOptional = p_34611_.getBrain().getMemory(FrostMemoryModuleType.NEAREST_ENEMYS.get());
                if (listOptional.isPresent() && !listOptional.get().isEmpty()) {
                    return Optional.of(listOptional.get().get(p_34611_.getRandom().nextInt(listOptional.get().size())));
                }
            }
        }

        return flag ? p_34611_.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE) : Optional.empty();
    }

    private static float getSpeedModifier(LivingEntity livingEntity) {
        return 1.0F;
    }

    private static float getSpeedModifierChasing(LivingEntity p_149289_) {
        return 1.2F;
    }

    private static void retreatFromNearestTarget(FrostBoar p_34613_, LivingEntity p_34614_) {
        Brain<FrostBoar> brain = p_34613_.getBrain();
        LivingEntity $$2 = BehaviorUtils.getNearestTarget(p_34613_, brain.getMemory(MemoryModuleType.AVOID_TARGET), p_34614_);
        $$2 = BehaviorUtils.getNearestTarget(p_34613_, brain.getMemory(MemoryModuleType.ATTACK_TARGET), $$2);
        setAvoidTarget(p_34613_, $$2);
    }

    private static void setAvoidTarget(FrostBoar p_34620_, LivingEntity p_34621_) {
        p_34620_.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        p_34620_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        p_34620_.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, p_34621_, (long) RETREAT_DURATION.sample(p_34620_.level().random));
    }


    public static void wasHurtBy(FrostBoar p_34596_, LivingEntity p_34597_) {
        if (!(p_34597_ instanceof FrostBoar)) {
            Brain<FrostBoar> brain = p_34596_.getBrain();
            brain.eraseMemory(MemoryModuleType.BREED_TARGET);
            if (p_34596_.isBaby()) {
                retreatFromNearestTarget(p_34596_, p_34597_);
                if (isEntityAttackableIgnoringLineOfSight(p_34596_, p_34597_)) {
                    broadcastAttackTarget(p_34596_, p_34597_);
                }
            } else if (!isEnoughFrostBoarOrHealth(p_34596_)) {
                retreatFromNearestTarget(p_34596_, p_34597_);
                if (isEntityAttackableIgnoringLineOfSight(p_34596_, p_34597_)) {
                    setAttackTarget(p_34596_, p_34597_);
                }
            } else {
                maybeRetaliate(p_34596_, p_34597_);
            }
        }
    }


    private static void maybeRetaliate(FrostBoar p_34625_, LivingEntity p_34626_) {
        if (!p_34625_.getBrain().isActive(Activity.AVOID) || p_34626_.getType() != FrostEntities.YETI.get()) {
            if (p_34626_.getType() != FrostEntities.FROST_BOAR.get()) {
                if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(p_34625_, p_34626_, 4.0D)) {
                    if (isEntityAttackableIgnoringLineOfSight(p_34625_, p_34626_)) {
                        setAttackTarget(p_34625_, p_34626_);
                        broadcastAttackTarget(p_34625_, p_34626_);
                    }
                }
            }
        }
    }

    private static void setAttackTarget(FrostBoar p_34630_, LivingEntity p_34631_) {
        Brain<FrostBoar> brain = p_34630_.getBrain();
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.eraseMemory(MemoryModuleType.BREED_TARGET);
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, p_34631_, 200L);
    }

    public static void onHitTarget(FrostBoar p_34580_, LivingEntity p_34581_) {
        if (!p_34580_.isBaby()) {
            broadcastAttackTarget(p_34580_, p_34581_);
        }
    }

    private static void broadcastAttackTarget(FrostBoar p_34635_, LivingEntity p_34636_) {
        getVisibleAdultFrostBoars(p_34635_).forEach((p_34574_) -> {
            setAttackTargetIfCloserThanCurrent(p_34574_, p_34636_);
        });
    }

    public static List<FrostBoar> getVisibleAdultFrostBoars(LivingEntity p_34874_) {
        return p_34874_.getBrain().getMemory(FrostMemoryModuleType.NEAREST_FROST_BOARS.get()).orElse(ImmutableList.of());
    }

    private static void setAttackTargetIfCloserThanCurrent(FrostBoar p_34640_, LivingEntity p_34641_) {
        Optional<LivingEntity> optional = p_34640_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        LivingEntity livingentity = BehaviorUtils.getNearestTarget(p_34640_, optional, p_34641_);
        setAttackTarget(p_34640_, livingentity);
    }

    public static Optional<SoundEvent> getSoundForCurrentActivity(FrostBoar boar) {
        return boar.getBrain().getActiveNonCoreActivity().map((activity) -> {
            return getSoundForActivity(boar, activity);
        });
    }

    private static SoundEvent getSoundForActivity(FrostBoar p_34583_, Activity p_34584_) {
        if (p_34584_ != Activity.AVOID) {
            if (p_34584_ == Activity.FIGHT) {
                return SoundEvents.HOGLIN_ANGRY;
            } else {
                return SoundEvents.HOGLIN_AMBIENT;
            }
        } else {
            return SoundEvents.HOGLIN_RETREAT;
        }
    }
}
