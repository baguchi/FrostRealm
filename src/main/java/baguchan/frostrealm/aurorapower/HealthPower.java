package baguchan.frostrealm.aurorapower;

import net.minecraft.world.entity.EquipmentSlot;

public class HealthPower extends ArmorPower {
	public HealthPower(Properties properties, EquipmentSlot[] equipmentSlots) {
		super(properties, equipmentSlots);
	}

	public int getMinCost(int auroraPowerLevel) {
		return 10 + (auroraPowerLevel - 1) * 10;
	}

	public int getMaxCost(int auroraPowerLevel) {
		return this.getMinCost(auroraPowerLevel) + 30;
	}
}
