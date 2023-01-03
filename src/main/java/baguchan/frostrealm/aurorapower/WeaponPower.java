package baguchan.frostrealm.aurorapower;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class WeaponPower extends AuroraPower {
	public WeaponPower(Properties properties, EquipmentSlot[] equipmentSlots) {
		super(properties, equipmentSlots);
	}

	public int getMinCost(int auroraPowerLevel) {
		return 1 + (auroraPowerLevel - 1) * 10;
	}

	public int getMaxCost(int auroraPowerLevel) {
		return this.getMinCost(auroraPowerLevel) + 20;
	}

	@Override
	protected boolean canApplyItem(ItemStack stack) {
		return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
	}
}
