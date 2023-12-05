package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.loot.GemRandomlyFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_REIGSTER = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, FrostRealm.MODID);

    public static final Supplier<LootItemFunctionType> RANDOM_GEM_FUNCTION = LOOT_REIGSTER.register("random_gem_function", () -> new LootItemFunctionType(GemRandomlyFunction.CODEC));
}