package baguchan.frostrealm.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class AstriumWolfflueArmorItem extends Item {
    public AstriumWolfflueArmorItem(ArmorMaterial armorMaterial, Properties p_41383_) {
        super(AstriumArmorItem.astriumWolfArmor(p_41383_, armorMaterial));
    }
}
