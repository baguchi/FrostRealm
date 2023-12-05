package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FrostRealm.MODID);


    public static final Supplier<CreativeModeTab> FROSTREALM = CREATIVE_MODE_TABS.register("frostrealm", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + "frostrealm"))
            .icon(() -> FrostItems.FROST_BOAR_MEAT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(FrostItems.ITEMS.getEntries().stream().map(sup -> {
                    return sup.get().getDefaultInstance();
                }).toList());
            }).build());

}