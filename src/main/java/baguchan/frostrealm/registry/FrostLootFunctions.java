package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.loot.GemRandomlyFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class FrostLootFunctions {
    public static final LootItemFunctionType SET_GEM_FUNCTION = register("set_gem_function", new GemRandomlyFunction.Serializer());


    private static LootItemFunctionType register(String p_80763_, Serializer<? extends LootItemFunction> p_80764_) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(FrostRealm.MODID, p_80763_), new LootItemFunctionType(p_80764_));
    }

    public static void init() {

    }
}