package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.animal.FrostBoar;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FrostMemoryModuleType {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, FrostRealm.MODID);

    public static final Supplier<MemoryModuleType<List<FrostBoar>>> NEAREST_FROST_BOARS = MEMORY_MODULE_TYPES.register("nearest_frost_boar", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<Integer>> FROST_BOAR_COUNT = MEMORY_MODULE_TYPES.register("frost_boar_count", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<List<Yeti>>> NEAREST_YETIS = MEMORY_MODULE_TYPES.register("nearest_yeti", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<Integer>> YETI_COUNT = MEMORY_MODULE_TYPES.register("yeti_count", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<List<LivingEntity>>> NEAREST_ENEMYS = MEMORY_MODULE_TYPES.register("nearest_enemy", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<Integer>> NEAREST_ENEMY_COUNT = MEMORY_MODULE_TYPES.register("nearest_enemy_count", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<Unit>> SHOOT_COOLDOWN = MEMORY_MODULE_TYPES.register("shoot_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));
    public static final Supplier<MemoryModuleType<Unit>> SHOOT = MEMORY_MODULE_TYPES.register("shoot", () -> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));

}
