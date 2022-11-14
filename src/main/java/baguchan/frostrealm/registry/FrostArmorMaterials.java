package baguchan.frostrealm.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum FrostArmorMaterials implements ArmorMaterial {
	YETI_FUR("frostrealm:yeti_fur", 15, new int[]{2, 3, 5, 2}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.2F, 0.0F, () -> {
		return Ingredient.of(FrostItems.YETI_FUR.get());
	}),
	KOLOSSUS_FUR("frostrealm:kolossus_fur", 18, new int[]{2, 4, 6, 2}, 13, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5F, 0.0F, () -> {
		return Ingredient.of(FrostItems.KOLOSSUS_FUR.get());
	}),
	ASTRIUM("frostrealm:astrium", 20, new int[]{2, 5, 6, 2}, 11, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> {
		return Ingredient.of(FrostItems.ASTRIUM_INGOT.get());
	});

	private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] slotProtections;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	FrostArmorMaterials(String p_40474_, int p_40475_, int[] p_40476_, int p_40477_, SoundEvent p_40478_, float p_40479_, float p_40480_, Supplier<Ingredient> p_40481_) {
		this.name = p_40474_;
		this.durabilityMultiplier = p_40475_;
		this.slotProtections = p_40476_;
		this.enchantmentValue = p_40477_;
		this.sound = p_40478_;
		this.toughness = p_40479_;
		this.knockbackResistance = p_40480_;
		this.repairIngredient = new LazyLoadedValue<>(p_40481_);
	}

	public int getDurabilityForSlot(EquipmentSlot p_40484_) {
		return HEALTH_PER_SLOT[p_40484_.getIndex()] * this.durabilityMultiplier;
	}

	public int getDefenseForSlot(EquipmentSlot p_40487_) {
		return this.slotProtections[p_40487_.getIndex()];
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
}