package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public abstract class CraftingDataHelper extends RecipeProvider {
    public CraftingDataHelper(PackOutput generator, CompletableFuture<HolderLookup.Provider> p_323846_) {
        super(generator, p_323846_);
    }

    protected final void foodCooking(Item material, Item result, float xp, RecipeOutput consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), RecipeCategory.FOOD, result, xp, 200).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smelting_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(material), RecipeCategory.FOOD, result, xp, 100).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smoking_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(material), RecipeCategory.FOOD, result, xp, 600).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("campfire_cooking_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
	}

    protected final void foodCooking(Item material, Item result, float xp, RecipeOutput consumer, String recipeName) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), RecipeCategory.FOOD, result, xp, 200).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smelting_" + recipeName));
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(material), RecipeCategory.FOOD, result, xp, 100).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smoking_" + recipeName));
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(material), RecipeCategory.FOOD, result, xp, 600).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("campfire_cooking_" + recipeName));
	}

    protected final void smeltOre(Item material, Item result, float xp, RecipeOutput consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), RecipeCategory.MISC, result, xp, 200).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smelting_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(material), RecipeCategory.MISC, result, xp, 100).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("blasting_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
	}

    protected final void smeltOre(Item material, Item result, float xp, RecipeOutput consumer, String recipeName) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), RecipeCategory.MISC, result, xp, 200).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("smelting_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(material), RecipeCategory.MISC, result, xp, 100).unlockedBy("has_item", has(material)).save(consumer, FrostRealm.prefix("blasting_" + BuiltInRegistries.ITEM.getKey(result).getPath()));
	}

    protected final void helmetItem(RecipeOutput consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
				.pattern("###")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void chestplateItem(RecipeOutput consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
				.pattern("# #")
				.pattern("###")
				.pattern("###")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void leggingsItem(RecipeOutput consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
				.pattern("###")
				.pattern("# #")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void bootsItem(RecipeOutput consumer, String name, Item result, Item material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
				.pattern("# #")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void pickaxeItem(RecipeOutput consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
				.pattern("###")
				.pattern(" X ")
				.pattern(" X ")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void swordItem(RecipeOutput consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
				.pattern("#")
				.pattern("#")
				.pattern("X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void axeItem(RecipeOutput consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
				.pattern("##")
				.pattern("#X")
				.pattern(" X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    protected final void shovelItem(RecipeOutput consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
				.pattern("#")
				.pattern("X")
				.pattern("X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void hoeItem(RecipeOutput consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
				.pattern("##")
				.pattern("X ")
				.pattern("X ")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

	protected final void sickleItem(RecipeOutput consumer, String name, Item result, Item material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
				.pattern("###")
				.pattern("X  ")
				.pattern("X  ")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(consumer, locEquip(name));
	}

    public void makeStairs(RecipeOutput consumer, Block stairsOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairsOut, 4)
				.pattern("M  ")
				.pattern("MM ")
				.pattern("MMM")
				.define('M', blockIn)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn).getPath(), has(blockIn)).save(consumer);
	}

    public void makeSlab(RecipeOutput consumer, Block slabOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slabOut, 6)
				.pattern("MMM")
				.define('M', blockIn)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn).getPath(), has(blockIn)).save(consumer);
	}

    public void makeWoodFence(RecipeOutput consumer, Block fenceOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceOut, 3)
				.pattern("MSM")
				.pattern("MSM")
				.define('M', blockIn)
				.define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn).getPath(), has(blockIn)).save(consumer);
	}

    public void makeFenceGate(RecipeOutput consumer, Block fenceOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceOut)
				.pattern("SMS")
				.pattern("SMS")
				.define('M', blockIn)
				.define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn).getPath(), has(blockIn)).save(consumer);
	}

    public void makeDoor(RecipeOutput consumer, Block doorOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, doorOut, 3)
				.pattern("DD")
				.pattern("DD")
				.pattern("DD")
				.define('D', blockIn)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn).getPath(), has(blockIn)).save(consumer);
	}

	public void makeTrapDoor(RecipeOutput consumer, Block doorOut, Block blockIn) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, doorOut, 3)
				.pattern("DDD")
				.pattern("DDD")
				.define('D', blockIn)
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn).getPath(), has(blockIn)).save(consumer);
	}

    public void makeFrostTorch(RecipeOutput consumer, Item torchOut) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, torchOut, 4)
                .pattern("C")
                .pattern("M")
                .define('C', FrostItems.FROST_CRYSTAL.get())
                .define('M', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(FrostItems.FROST_CRYSTAL.get()).getPath(), has(FrostItems.FROST_CRYSTAL.get())).save(consumer);
    }

    protected final ResourceLocation locEquip(String name) {
        return FrostRealm.prefix("equipment/" + name);
    }
}