package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;

public class WolfflueArmorItem extends Item {

    public WolfflueArmorItem(ArmorMaterial armorMaterial, Item.Properties p_316341_) {
        super(p_316341_.durability(ArmorType.BODY.getDurability(armorMaterial.durability())).attributes(armorMaterial.createAttributes(ArmorType.BODY)).repairable(armorMaterial.repairIngredient()).component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.BODY).setEquipSound(armorMaterial.equipSound()).setAsset(armorMaterial.assetId()).setAllowedEntities(HolderSet.direct(FrostEntities.WOLFFLUE))     .setCanBeSheared(true)
                .setShearingSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARMOR_UNEQUIP_WOLF))
                .build()).component(DataComponents.BREAK_SOUND, SoundEvents.WOLF_ARMOR_BREAK).stacksTo(1));
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return stack.is(FrostItems.WOLFFLUE_GLACIER_BOAR_ARMOR);
    }
}
