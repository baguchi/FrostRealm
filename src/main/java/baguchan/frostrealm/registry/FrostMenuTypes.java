package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.menu.AuroraInfuserMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, FrostRealm.MODID);
    public static final Supplier<MenuType<AuroraInfuserMenu>> AURORA_INFUSER = MENU_TYPES.register("aurora_infuser", () -> new MenuType<>(AuroraInfuserMenu::new, FeatureFlags.VANILLA_SET));

}
