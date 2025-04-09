package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.loot.AuroraWithLevelsFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FrostLootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_REIGSTER = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, FrostRealm.MODID);
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<AuroraWithLevelsFunction>> AURORA_WITH_LEVEL = LOOT_REIGSTER.register("aurora_with_levels", () -> new LootItemFunctionType<>(AuroraWithLevelsFunction.CODEC));

}