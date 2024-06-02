package baguchan.frostrealm.entity.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FlyingAttackEntitySensor extends NearestLivingEntitySensor<Mob> {
    public static final int BREEZE_SENSOR_RADIUS = 24;

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.copyOf(Iterables.concat(super.requires(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    protected void doTick(ServerLevel p_312447_, Mob p_312739_) {
        super.doTick(p_312447_, p_312739_);
        p_312739_.getBrain()
                .getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES)
                .stream()
                .flatMap(Collection::stream)
                .filter(EntitySelector.NO_CREATIVE_OR_SPECTATOR)
                .filter(p_312759_ -> Sensor.isEntityAttackable(p_312739_, p_312759_))
                .findFirst()
                .ifPresentOrElse(
                        p_312872_ -> p_312739_.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, p_312872_),
                        () -> p_312739_.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE)
                );
    }

    @Override
    protected int radiusXZ() {
        return 24;
    }

    @Override
    protected int radiusY() {
        return 32;
    }
}
