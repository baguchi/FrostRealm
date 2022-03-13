package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Constructor;
import java.util.function.Consumer;

public abstract class CraftingDataHelper extends RecipeProvider {
	public CraftingDataHelper(DataGenerator generator) {
		super(generator);
	}

	protected final Ingredient itemWithNBT(RegistryObject<? extends ItemLike> item, Consumer<CompoundTag> nbtSetter) {
		return itemWithNBT(item, nbtSetter);
	}

	protected final Ingredient itemWithNBT(ItemLike item, Consumer<CompoundTag> nbtSetter) {
		ItemStack stack = new ItemStack(item);

		CompoundTag nbt = new CompoundTag();
		nbtSetter.accept(nbt);
		stack.setTag(nbt);

		try {
			Constructor<NBTIngredient> constructor = NBTIngredient.class.getDeclaredConstructor(ItemStack.class);

			constructor.setAccessible(true);

			return constructor.newInstance(stack);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// This will just defer to the regular Ingredient method instead of some overridden thing, but whatever.
		// Forge PRs are too slow to even feel motivated about fixing it on the Forge end.
		return Ingredient.of(stack);
	}

	protected final void foodCooking(Item material, Item result, float xp, Consumer<FinishedRecipe> consumer) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), result, xp, 200).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smelting_" + result.getRegistryName().getPath()));
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(material), result, xp, 100).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smoking_" + result.getRegistryName().getPath()));
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(material), result, xp, 600).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("campfire_cooking_" + result.getRegistryName().getPath()));
	}

	protected final void foodCooking(Item material, Item result, float xp, Consumer<FinishedRecipe> consumer, String recipeName) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), result, xp, 200).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smelting_" + recipeName));
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(material), result, xp, 100).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smoking_" + recipeName));
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(material), result, xp, 600).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("campfire_cooking_" + recipeName));
	}

	protected final void helmetItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("###")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void chestplateItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("# #")
				.pattern("###")
				.pattern("###")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void leggingsItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("###")
				.pattern("# #")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void bootsItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("# #")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void pickaxeItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("###")
				.pattern(" X ")
				.pattern(" X ")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void swordItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("#")
				.pattern("#")
				.pattern("X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void axeItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("##")
				.pattern("#X")
				.pattern(" X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void shovelItem(Consumer<FinishedRecipe> consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(result)
				.pattern("#")
				.pattern("X")
				.pattern("X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	public void makeStairs(Consumer<FinishedRecipe> consumer, Block stairsOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(stairsOut, 4)
				.pattern("M  ")
				.pattern("MM ")
				.pattern("MMM")
				.define('M', blockIn)
				.unlockedBy("has_" + blockIn.getRegistryName().getPath(), has(blockIn)).save(consumer);
	}

	public void makeSlab(Consumer<FinishedRecipe> consumer, Block slabOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(slabOut, 6)
				.pattern("MMM")
				.define('M', blockIn)
				.unlockedBy("has_" + blockIn.getRegistryName().getPath(), has(blockIn)).save(consumer);
	}

	public void makeWoodFence(Consumer<FinishedRecipe> consumer, Block fenceOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(fenceOut, 3)
				.pattern("MSM")
				.pattern("MSM")
				.define('M', blockIn)
				.define('S', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_" + blockIn.getRegistryName().getPath(), has(blockIn)).save(consumer);
	}

	public void makeFenceGate(Consumer<FinishedRecipe> consumer, Block fenceOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(fenceOut)
				.pattern("SMS")
				.pattern("SMS")
				.define('M', blockIn)
				.define('S', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_" + blockIn.getRegistryName().getPath(), has(blockIn)).save(consumer);
	}

	public void makeDoor(Consumer<FinishedRecipe> consumer, Block doorOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(doorOut, 3)
				.pattern("DD")
				.pattern("DD")
				.define('D', blockIn)
				.unlockedBy("has_" + blockIn.getRegistryName().getPath(), has(blockIn)).save(consumer);
	}

	public void makeFrostTorch(Consumer<FinishedRecipe> consumer, Item torchOut) {
		ShapedRecipeBuilder.shaped(torchOut, 4)
				.pattern("C")
				.pattern("M")
				.define('C', FrostItems.FROST_CRYSTAL.get())
				.define('M', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_" + FrostItems.FROST_CRYSTAL.get().getRegistryName().getPath(), has(FrostItems.FROST_CRYSTAL.get())).save(consumer);
	}


	protected final ResourceLocation locEquip(String name) {
		return FrostRealm.prefix("equipment/" + name);
	}
}