package baguchan.frostrealm.entity.brain;

import bagu_chan.bagus_lib.register.ModSensors;
import baguchan.frostrealm.entity.brain.behavior.AttackAnimationWithoutWalk;
import baguchan.frostrealm.entity.brain.behavior.StartAttackingNoWalking;
import baguchan.frostrealm.entity.hostile.MindVine;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MindVineAi {
    public static final List<SensorType<? extends Sensor<? super MindVine>>> SENSOR_TYPES = ImmutableList.of(
            ModSensors.SMART_NEAREST_LIVING_ENTITY_SENSOR.get(), SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS
    );
    public static final List<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY
    );

    public static Brain<?> makeBrain(MindVine mindVine, Brain<MindVine> p_312887_) {
        initCoreActivity(p_312887_);
        initFightActivity(mindVine, p_312887_);
        p_312887_.setCoreActivities(Set.of(Activity.CORE));
        p_312887_.setDefaultActivity(Activity.FIGHT);
        p_312887_.useDefaultActivity();
        return p_312887_;
    }

    private static void initCoreActivity(Brain<MindVine> p_312774_) {
        p_312774_.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90)));
    }

    private static void initFightActivity(MindVine mindVine, Brain<MindVine> p_312350_) {
        p_312350_.addActivityWithConditions(
                Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, StartAttackingNoWalking.create(p_312881_ -> p_312881_.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY))),
                        Pair.of(0, StartAttackingNoWalking.create(p_312881_ -> p_312881_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER))),
                        Pair.of(0, StartAttackingNoWalking.create(p_312881_ -> findNearestValidAttackTarget(mindVine))),
                        Pair.of(1, StopAttackingIfTargetInvalid.create()),
                        Pair.of(2, new AttackAnimationWithoutWalk<>((int) (30 - (0.38 * 20)), 30, 10)),
                        Pair.of(6, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1))))
                ),
                Set.of()
        );
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(MindVine mindVine) {
        Optional<Player> optional = mindVine.getBrain()
                .getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            return optional;
        }

        return mindVine.getBrain()
                .getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                .orElse(NearestVisibleLivingEntities.empty())
                .findClosest(mindVine::canAttack);
    }
}
