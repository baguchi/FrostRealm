package baguchan.frostrealm.aurorapower;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

import java.util.Arrays;

public class ArmorPower extends AuroraPower {
    public ArmorPower(Properties properties, EquipmentSlot[] equipmentSlots) {
        super(properties, equipmentSlots);
    }

    public int getMinCost(int auroraPowerLevel) {
        return 1 + (auroraPowerLevel - 1) * 10;
    }

    public int getMaxCost(int auroraPowerLevel) {
        return this.getMinCost(auroraPowerLevel) + 20;
    }

    @Override
    public boolean canApplyItem(ItemStack stack) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        return equippable != null && Arrays.stream(getSlots()).anyMatch(equipmentSlot -> equipmentSlot == equippable.slot());
    }
}
