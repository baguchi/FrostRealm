package baguchan.frostrealm.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum FrostItemTier implements Tier {
	ASTRIUM(BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 320, 6.0F, 2.0F, 16, () -> Ingredient.of(FrostItems.ASTRIUM_INGOT.get())),
    SILVER_MOON(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 3, 560, 7.0F, 2.0F, 20, () -> Ingredient.of());
	private final TagKey<Block> incorrectBlocksForDrops;
	private final int level;

	private final int uses;

	private final float speed;

	private final float damage;

	private final int enchantmentValue;

	private final LazyLoadedValue<Ingredient> repairIngredient;

	FrostItemTier(TagKey<Block> p_336171_, int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
		this.incorrectBlocksForDrops = p_336171_;
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

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return this.incorrectBlocksForDrops;
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