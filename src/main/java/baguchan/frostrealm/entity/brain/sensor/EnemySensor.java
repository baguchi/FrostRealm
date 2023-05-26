package baguchan.frostrealm.entity.brain.sensor;

import baguchan.frostrealm.registry.FrostMemoryModuleType;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.List;
import java.util.Set;

public class EnemySensor extends Sensor<Mob> {
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get());
    }

    protected void doTick(ServerLevel p_26659_, Mob mob) {
        Brain<?> brain = mob.getBrain();
        if (mob.getTarget() != null) {
            //brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, this.findNearestRepellent(p_26659_, livingEntity));
            List<LivingEntity> list = Lists.newArrayList();
            NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());

            nearestvisiblelivingentities.findAll((target) -> {
                return target.getLastHurtMob() == mob || target instanceof Mob && ((Mob) target).getTarget() == mob;
            }).forEach(list::add);

            brain.setMemory(FrostMemoryModuleType.NEAREST_ENEMYS.get(), list);
            brain.setMemory(FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), list.size());
        }
    }
}