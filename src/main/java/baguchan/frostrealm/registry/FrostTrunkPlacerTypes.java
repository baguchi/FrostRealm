package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.tree.trunk.DripWoodTrunkPlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FrostTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_TYPES = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, FrostRealm.MODID);
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<DripWoodTrunkPlacer>> DRIP_WOOD_TRUNK_PLACER = TRUNK_TYPES.register("drip_wood_trunk",
            () -> new TrunkPlacerType<>(DripWoodTrunkPlacer.CODEC));

}
