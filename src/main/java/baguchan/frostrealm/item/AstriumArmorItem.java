package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;

public class AstriumArmorItem extends Item {
    public AstriumArmorItem(Properties p_41383_) {
        super(p_41383_);
    }

    public static Properties astriumArmor(Properties properties, ArmorMaterial p_394389_, ArmorType p_393823_) {
        return properties.durability(p_393823_.getDurability(p_394389_.durability())).attributes(createAstriumAttributes(p_394389_.createAttributes(p_393823_), p_393823_)).enchantable(p_394389_.enchantmentValue()).component(DataComponents.EQUIPPABLE, Equippable.builder(p_393823_.getSlot()).setEquipSound(p_394389_.equipSound()).setAsset(p_394389_.assetId()).build()).repairable(p_394389_.repairIngredient());
    }

    public static Properties astriumWolfArmor(Properties properties, ArmorMaterial p_399934_) {
        return properties.durability(ArmorType.BODY.getDurability(p_399934_.durability())).attributes(createAstriumAttributes(p_399934_.createAttributes(ArmorType.BODY), ArmorType.BODY)).repairable(p_399934_.repairIngredient()).component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.BODY).setEquipSound(p_399934_.equipSound()).setAsset(p_399934_.assetId()).setAllowedEntities(HolderSet.direct(FrostEntities.WOLFFLUE)).setCanBeSheared(true).setShearingSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARMOR_UNEQUIP_WOLF)).build()).component(DataComponents.BREAK_SOUND, SoundEvents.WOLF_ARMOR_BREAK).stacksTo(1);
    }

    public static ItemAttributeModifiers createAstriumAttributes(ItemAttributeModifiers itemAttributeModifiers, ArmorType p_371239_) {
        ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(p_371239_.getSlot());

        itemAttributeModifiers.forEach(equipmentslotgroup, (attributeHolder, attributeModifier, s) -> {
            itemattributemodifiers$builder.add(attributeHolder, attributeModifier, equipmentslotgroup);
        });
        Identifier resourcelocation = Identifier.withDefaultNamespace("armor." + p_371239_.getName());

        if(p_371239_.getSlot() == EquipmentSlot.FEET || p_371239_.getSlot() == EquipmentSlot.BODY) {
            itemattributemodifiers$builder.add(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(resourcelocation, 2.0F, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        }
        itemattributemodifiers$builder.add(Attributes.BURNING_TIME, new AttributeModifier(resourcelocation, -0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), equipmentslotgroup);

        return itemattributemodifiers$builder.build();
    }
}
