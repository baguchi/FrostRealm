package baguchan.frostrealm.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FrostGroups {
	public static final CreativeModeTab TAB_FROSTREALM = new CreativeModeTab("frostrealm") {
		@Override
		public ItemStack makeIcon() {
			return FrostItems.FROST_CRYSTAL.getDefaultInstance();
		}
	};
}
