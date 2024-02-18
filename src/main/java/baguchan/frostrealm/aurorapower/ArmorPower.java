package baguchan.frostrealm.aurorapower;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

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
        return stack.getItem() instanceof ArmorItem && Arrays.stream(this.getSlots()).anyMatch(slot -> {
            return slot == ((ArmorItem) stack.getItem()).getEquipmentSlot();
        });
    }
}
