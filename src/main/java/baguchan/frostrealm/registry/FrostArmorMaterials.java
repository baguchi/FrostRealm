package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class FrostArmorMaterials {
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, FrostRealm.MODID);

	public static final Holder<ArmorMaterial> YETI_FUR = register("yeti_fur", Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
		p_266652_.put(ArmorItem.Type.BODY, 5);
	}), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, () -> Ingredient.of(FrostItems.YETI_FUR.get()));
	public static final Holder<ArmorMaterial> FROST_BOAR_FUR = register("frost_boar_fur", Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
		p_266652_.put(ArmorItem.Type.BODY, 5);
	}), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, () -> Ingredient.of(FrostItems.FROST_BOAR_FUR.get()));
	public static final Holder<ArmorMaterial> ASTRIUM = register("astrium", Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266654_) -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266654_.put(ArmorItem.Type.HELMET, 2);
		p_266654_.put(ArmorItem.Type.BODY, 5);
	}), 12, SoundEvents.ARMOR_EQUIP_IRON, 1.5F, 0.0F, () -> Ingredient.of(FrostItems.ASTRIUM_INGOT.get()));

	private static Holder<ArmorMaterial> register(
			String p_323589_,
			EnumMap<ArmorItem.Type, Integer> p_323819_,
			int p_323636_,
			Holder<SoundEvent> p_323958_,
			float p_323937_,
			float p_323731_,
			Supplier<Ingredient> p_323970_
	) {
		List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(FrostRealm.prefix(p_323589_)));
		return register(p_323589_, p_323819_, p_323636_, p_323958_, p_323937_, p_323731_, p_323970_, list);
	}

	private static Holder<ArmorMaterial> register(
			String p_323865_,
			EnumMap<ArmorItem.Type, Integer> p_324599_,
			int p_324319_,
			Holder<SoundEvent> p_324145_,
			float p_323494_,
			float p_324549_,
			Supplier<Ingredient> p_323845_,
			List<ArmorMaterial.Layer> p_323990_
	) {
		EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

		for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
			enummap.put(armoritem$type, p_324599_.get(armoritem$type));
		}

		return ARMOR_MATERIALS.register(p_323865_,
				() -> new ArmorMaterial(enummap, p_324319_, p_324145_, p_323845_, p_323990_, p_323494_, p_324549_)
		);
	}
}