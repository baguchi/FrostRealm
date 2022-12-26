package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.menu.CrystalSmithingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FrostRealm.MODID);


	public static final RegistryObject<MenuType<CrystalSmithingMenu>> CRYSTAL_SMITHING = MENU_TYPES.register("crystal_smithing", () -> new MenuType<>(CrystalSmithingMenu::new));
}