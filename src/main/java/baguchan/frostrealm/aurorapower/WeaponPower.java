package baguchan.frostrealm.aurorapower;

import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

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
    public boolean canApplyItem(ItemStack stack) {
        return stack.is(FrostTags.Items.SMITHABLE_WEAPON);
    }
}
