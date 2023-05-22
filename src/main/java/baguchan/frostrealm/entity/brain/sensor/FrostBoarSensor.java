package baguchan.frostrealm.entity.brain.sensor;

import baguchan.frostrealm.entity.FrostBoar;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.List;
import java.util.Set;

public class FrostBoarSensor extends Sensor<FrostBoar> {
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, FrostMemoryModuleType.NEAREST_FROST_BOARS.get(), FrostMemoryModuleType.FROST_BOAR_COUNT.get());
    }

    protected void doTick(ServerLevel p_26659_, FrostBoar p_26660_) {
        Brain<?> brain = p_26660_.getBrain();
        //brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, this.findNearestRepellent(p_26659_, p_26660_));
        List<FrostBoar> list = Lists.newArrayList();
        NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());

        for (LivingEntity livingentity : nearestvisiblelivingentities.findAll((p_186150_) -> {
            return !p_186150_.isBaby() && (p_186150_ instanceof FrostBoar);
        })) {

            if (livingentity instanceof FrostBoar boar) {
                list.add(boar);
            }
        }

        brain.setMemory(FrostMemoryModuleType.NEAREST_FROST_BOARS.get(), list);
        brain.setMemory(FrostMemoryModuleType.FROST_BOAR_COUNT.get(), list.size());
    }
}