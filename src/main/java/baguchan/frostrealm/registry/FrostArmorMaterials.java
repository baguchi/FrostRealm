package baguchan.frostrealm.registry;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum FrostArmorMaterials implements StringRepresentable, ArmorMaterial {
	YETI_FUR("frostrealm:yeti_fur", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 4);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 5);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
	}), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5F, 0.0F, () -> {
		return Ingredient.of(FrostItems.YETI_FUR.get());
	}),
	KOLOSSUS_FUR("frostrealm:kolossus_fur", 18, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 2);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266652_.put(ArmorItem.Type.HELMET, 2);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, () -> {
		return Ingredient.of(FrostItems.KOLOSSUS_FUR.get());
	}),
	ASTRIUM("frostrealm:astrium", 20, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266654_) -> {
		p_266654_.put(ArmorItem.Type.BOOTS, 2);
		p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266654_.put(ArmorItem.Type.HELMET, 2);
	}), 12, SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.0F, () -> {
		return Ingredient.of(FrostItems.ASTRIUM_INGOT.get());
	});

	private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
		p_266653_.put(ArmorItem.Type.BOOTS, 13);
		p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
		p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
		p_266653_.put(ArmorItem.Type.HELMET, 11);
	});
	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	private FrostArmorMaterials(String p_268171_, int p_268303_, EnumMap<ArmorItem.Type, Integer> p_267941_, int p_268086_, SoundEvent p_268145_, float p_268058_, float p_268180_, Supplier<Ingredient> p_268256_) {
		this.name = p_268171_;
		this.durabilityMultiplier = p_268303_;
		this.protectionFunctionForType = p_267941_;
		this.enchantmentValue = p_268086_;
		this.sound = p_268145_;
		this.toughness = p_268058_;
		this.knockbackResistance = p_268180_;
		this.repairIngredient = new LazyLoadedValue<>(p_268256_);
	}

	public int getDurabilityForType(ArmorItem.Type p_266745_) {
		return HEALTH_FUNCTION_FOR_TYPE.get(p_266745_) * this.durabilityMultiplier;
	}

	public int getDefenseForType(ArmorItem.Type p_266752_) {
		return this.protectionFunctionForType.get(p_266752_);
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public SoundEvent getEquipSound() {
		return this.sound;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

	public String getSerializedName() {
		return this.name;
	}
}