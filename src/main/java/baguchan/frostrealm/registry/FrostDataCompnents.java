package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.item.component.ItemAuroraPower;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class FrostDataCompnents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FrostRealm.MODID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemAuroraPower>> AURORA_POWER = register("aurora_power", (p_341840_) -> {
        return p_341840_.persistent(ItemAuroraPower.CODEC).networkSynchronized(ItemAuroraPower.STREAM_CODEC).cacheEncoding();
    });

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String p_332092_, UnaryOperator<DataComponentType.Builder<T>> p_331261_) {
        return DATA_COMPONENT_TYPES.register(p_332092_, () -> p_331261_.apply(DataComponentType.builder()).build());
    }
}