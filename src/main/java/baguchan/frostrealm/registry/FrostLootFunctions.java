package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FrostLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_REIGSTER = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, FrostRealm.MODID);
}