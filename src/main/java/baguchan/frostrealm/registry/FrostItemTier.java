package baguchan.frostrealm.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum FrostItemTier implements Tier {
	ASTRIUM(2, 320, 6.0F, 2.0F, 16, () -> Ingredient.of(FrostItems.ASTRIUM_INGOT.get()));
	private final int level;

	private final int uses;

	private final float speed;

	private final float damage;

	private final int enchantmentValue;

	private final LazyLoadedValue<Ingredient> repairIngredient;

	FrostItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
		this.level = p_i48458_3_;
		this.uses = p_i48458_4_;
		this.speed = p_i48458_5_;
		this.damage = p_i48458_6_;
		this.enchantmentValue = p_i48458_7_;
		this.repairIngredient = new LazyLoadedValue(p_i48458_8_);
	}

	public int getUses() {
		return this.uses;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getAttackDamageBonus() {
		return this.damage;
	}

	public int getLevel() {
		return this.level;
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}