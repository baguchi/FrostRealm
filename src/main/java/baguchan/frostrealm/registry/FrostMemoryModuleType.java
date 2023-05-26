package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.FrostBoar;
import baguchan.frostrealm.entity.Yeti;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public class FrostMemoryModuleType {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, FrostRealm.MODID);

    public static final RegistryObject<MemoryModuleType<List<FrostBoar>>> NEAREST_FROST_BOARS = MEMORY_MODULE_TYPES.register("nearest_frost_boar", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Integer>> FROST_BOAR_COUNT = MEMORY_MODULE_TYPES.register("frost_boar_count", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<List<Yeti>>> NEAREST_YETIS = MEMORY_MODULE_TYPES.register("nearest_yeti", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Integer>> YETI_COUNT = MEMORY_MODULE_TYPES.register("yeti_count", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<List<LivingEntity>>> NEAREST_ENEMYS = MEMORY_MODULE_TYPES.register("nearest_enemy", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Integer>> NEAREST_ENEMY_COUNT = MEMORY_MODULE_TYPES.register("nearest_enemy_count", () -> new MemoryModuleType<>(Optional.empty()));

}
