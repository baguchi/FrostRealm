package baguchan.frostrealm.registry;

import baguchan.frostrealm.data.generator.FrostEquipmentAssets;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public class FrostArmorMaterials {
	public static final ArmorMaterial YETI_FUR = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), (p_266652_) -> {
		p_266652_.put(ArmorType.BOOTS, 2);
		p_266652_.put(ArmorType.LEGGINGS, 5);
		p_266652_.put(ArmorType.CHESTPLATE, 6);
		p_266652_.put(ArmorType.HELMET, 2);
		p_266652_.put(ArmorType.BODY, 10);
	}), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, FrostTags.Items.YETI_FUR_MATERIAL, FrostEquipmentAssets.YETI_FUR);
	public static final ArmorMaterial FROST_BOAR_FUR = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), (p_266652_) -> {
		p_266652_.put(ArmorType.BOOTS, 2);
		p_266652_.put(ArmorType.LEGGINGS, 5);
		p_266652_.put(ArmorType.CHESTPLATE, 6);
		p_266652_.put(ArmorType.HELMET, 2);
		p_266652_.put(ArmorType.BODY, 10);
	}), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 1.5F, 0.0F, FrostTags.Items.BOAR_FUR_MATERIAL, FrostEquipmentAssets.FROST_BOAR_FUR);
	public static final ArmorMaterial ASTRIUM = new ArmorMaterial(16, Util.make(new EnumMap<>(ArmorType.class), (p_266654_) -> {
		p_266654_.put(ArmorType.BOOTS, 2);
		p_266654_.put(ArmorType.LEGGINGS, 5);
		p_266654_.put(ArmorType.CHESTPLATE, 6);
		p_266654_.put(ArmorType.HELMET, 2);
		p_266654_.put(ArmorType.BODY, 16);
	}), 12, SoundEvents.ARMOR_EQUIP_IRON, 2F, 0.0F, FrostTags.Items.ASTRIUM_TOOL_MATERIAL, FrostEquipmentAssets.ASTRIUM);

}