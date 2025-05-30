package baguchan.frostrealm.entity.brain;

import baguchan.frostrealm.entity.boss.Seeker;
import baguchan.frostrealm.entity.brain.behavior.SeekerBreathAttack;
import baguchan.frostrealm.entity.brain.behavior.SeekerMeleeAttack;
import baguchan.frostrealm.entity.brain.behavior.SeekerWalkingToTarget;
import baguchan.frostrealm.entity.hostile.LesserWarrior;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import baguchan.frostrealm.registry.FrostSensors;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.entity.ai.sensing.Sensor.isEntityAttackableIgnoringLineOfSight;

public class SeekerAi {
    public static final ImmutableList<? extends SensorType<? extends Sensor<? super Seeker>>> SENSOR_TYPES = ImmutableList.of(baguchi.bagus_lib.register.ModSensors.SMART_NEAREST_LIVING_ENTITY_SENSOR.get(), SensorType.HURT_BY
            , FrostSensors.ENEMY_SENSOR.get(), SensorType.NEAREST_PLAYERS);
    public static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYERS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE
            , FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), MemoryModuleType.AVOID_TARGET
            , MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.HOME, FrostMemoryModuleType.BREATH_COOLDOWN.get(), FrostMemoryModuleType.JUMP_COOLDOWN.get());

    public static Brain<?> makeBrain(Seeker frostBoar, Brain<Seeker> p_149291_) {
        initCoreActivity(p_149291_);
        initIdleActivity(p_149291_);
        initFightActivity(p_149291_);
        p_149291_.setCoreActivities(ImmutableSet.of(Activity.CORE));
        p_149291_.setDefaultActivity(Activity.IDLE);
        p_149291_.useDefaultActivity();
        return p_149291_;
    }

    public static void updateActivity(Seeker boar) {
        Brain<Seeker> brain = boar.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity) null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activity1 = brain.getActiveNonCoreActivity().orElse((Activity) null);
        /*if (activity != activity1) {
            getSoundForCurrentActivity(boar).ifPresent(boar::playSound);
        }*/

        boar.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    private static void initFightActivity(Brain<Seeker> p_149303_) {
        p_149303_.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(StopAttackingIfTargetInvalid.create()
                , BehaviorBuilder.triggerIf(SeekerAi::checkExtraAttackMoveCondition, BackUpIfTooClose.create(8, 0.85F))
                , new SeekerWalkingToTarget<>()
                , new GateBehavior<>(
                        ImmutableMap.of(),
                        ImmutableSet.of(),
                        GateBehavior.OrderPolicy.ORDERED,
                        GateBehavior.RunningPolicy.RUN_ONE,
                        ImmutableList.of(Pair.of(new SeekerBreathAttack<>(), 1), Pair.of(new SeekerMeleeAttack<>(Seeker.attackAnimationActionPoint, Seeker.attackAnimationLength + 10, 10, 1.15F, 60F), 2))
                )), MemoryModuleType.ATTACK_TARGET);
    }


    private static void initCoreActivity(Brain<Seeker> p_149307_) {
        p_149307_.addActivity(Activity.CORE, 0, ImmutableList.of(StartAttacking.create(SeekerAi::findNearestValidAttackTarget), new Swim<>(0.8F), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static void initIdleActivity(Brain<Seeker> p_149309_) {
        p_149309_.addActivityWithConditions(Activity.IDLE, ImmutableList.of(Pair.of(3, createIdleMovementBehaviors()), Pair.of(0, createLookBehaviors()), Pair.of(2, StrollToPoi.create(MemoryModuleType.HOME, 0.85F, 3, 600))), ImmutableSet.of());
    }


    private static RunOne<LivingEntity> createLookBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60)), 2), Pair.of(SetEntityLookTargetSometimes.create(6.0F, UniformInt.of(30, 60)), 2)));
    }

    private static RunOne<Seeker> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.stroll(0.8F), 2), Pair.of(SetWalkTargetFromLookTarget.create(0.8F, 3), 2), Pair.of(new DoNothing(30, 60), 1)));
    }


    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel serverLevel, Seeker p_34611_) {

        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(p_34611_, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && isEntityAttackableIgnoringLineOfSight(serverLevel, p_34611_, optional.get())) {
            return optional;
        } else {
            Optional<List<LivingEntity>> listOptional = p_34611_.getBrain().getMemory(FrostMemoryModuleType.NEAREST_ENEMYS.get());
            if (listOptional.isPresent() && !listOptional.get().isEmpty()) {
                return Optional.of(listOptional.get().get(p_34611_.getRandom().nextInt(listOptional.get().size())));
            }
        }


        return p_34611_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
    }

    private static float getSpeedModifier(LivingEntity livingEntity) {
        return 1.0F;
    }

    private static float getSpeedModifierChasing(LivingEntity p_149289_) {
        return 1.2F;
    }


    public static void wasHurtBy(ServerLevel serverLevel, Seeker p_34596_, LivingEntity p_34597_) {
        if (!(p_34597_ instanceof Seeker) && !(p_34597_ instanceof LesserWarrior)) {
            Brain<Seeker> brain = p_34596_.getBrain();

            maybeRetaliate(serverLevel, p_34596_, p_34597_);

        }
    }


    private static void maybeRetaliate(ServerLevel serverLevel, Seeker p_34625_, LivingEntity p_34626_) {
        if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(p_34625_, p_34626_, 4.0D)) {
            if (isEntityAttackableIgnoringLineOfSight(serverLevel, p_34625_, p_34626_)) {
                setAttackTarget(p_34625_, p_34626_);
                //broadcastAttackTarget(p_34625_, p_34626_);
            }
        }
    }

    private static void setAttackTarget(Seeker p_34630_, LivingEntity p_34631_) {
        Brain<Seeker> brain = p_34630_.getBrain();
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, p_34631_, 200L);
    }


    public static Optional<SoundEvent> getSoundForCurrentActivity(Seeker boar) {
        return boar.getBrain().getActiveNonCoreActivity().map((activity) -> {
            return getSoundForActivity(boar, activity);
        });
    }

    protected static boolean checkExtraAttackMoveCondition(Seeker mob) {
        LivingEntity livingentity = getAttackTarget(mob);
        return livingentity != null && mob.getState() != Seeker.SeekerState.PRE_ATTACK && mob.getState() != Seeker.SeekerState.ATTACK;
    }

    private static LivingEntity getAttackTarget(Seeker p_23533_) {
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent() ? (LivingEntity) p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get() : null;
    }

    private static SoundEvent getSoundForActivity(Seeker p_34583_, Activity p_34584_) {
        if (p_34584_ != Activity.AVOID) {
            if (p_34584_ == Activity.FIGHT) {
                return SoundEvents.STRAY_AMBIENT;
            } else {
                return SoundEvents.HOGLIN_AMBIENT;
            }
        } else {
            return SoundEvents.HOGLIN_RETREAT;
        }
    }

    public static void initMemories(Seeker p_219206_, RandomSource p_219207_, EntitySpawnReason p_29535_) {
        p_219206_.getBrain().setMemoryWithExpiry(FrostMemoryModuleType.BREATH_COOLDOWN.get(), Unit.INSTANCE, (long) 600L);
        if (p_29535_ == EntitySpawnReason.STRUCTURE) {
            GlobalPos globalpos = GlobalPos.of(p_219206_.level().dimension(), p_219206_.blockPosition());
            p_219206_.getBrain().setMemory(MemoryModuleType.HOME, globalpos);
        }
    }
}
