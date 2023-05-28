package baguchan.frostrealm.entity.brain.sensor;

import baguchan.frostrealm.entity.FrostBoar;
import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.brain.YetiAi;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class YetiSensor extends Sensor<Yeti> {
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, FrostMemoryModuleType.NEAREST_YETIS.get(), FrostMemoryModuleType.YETI_COUNT.get(), MemoryModuleType.AVOID_TARGET);
    }

    protected void doTick(ServerLevel p_26659_, Yeti p_26660_) {
        Brain<?> brain = p_26660_.getBrain();
        //brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, this.findNearestRepellent(p_26659_, p_26660_));
        List<Yeti> list = Lists.newArrayList();
        List<FrostBoar> list2 = Lists.newArrayList();
        List<LivingEntity> list3 = Lists.newArrayList();
        NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
        Optional<ItemEntity> itemEntity = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);


        for (LivingEntity livingentity : nearestvisiblelivingentities.findAll((p_186150_) -> {
            return !p_186150_.isBaby();
        })) {

            if (livingentity instanceof Yeti yeti) {
                list.add(yeti);
            }
            if (livingentity instanceof FrostBoar boar) {
                list2.add(boar);
            }
            if (YetiAi.isWearingFear(livingentity)) {
                list3.add(livingentity);
            }
        }
        brain.setMemory(FrostMemoryModuleType.NEAREST_YETIS.get(), list);
        brain.setMemory(FrostMemoryModuleType.YETI_COUNT.get(), list.size());
        brain.setMemory(FrostMemoryModuleType.NEAREST_FROST_BOARS.get(), list2);
        brain.setMemory(FrostMemoryModuleType.FROST_BOAR_COUNT.get(), list2.size());
        if (!list3.isEmpty()) {
            if (!brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
                brain.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, list3.get(p_26660_.getRandom().nextInt(list3.size())), (long) YetiAi.RETREAT_DURATION.sample(p_26660_.getRandom()));
            }
        }
    }
}