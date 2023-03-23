package baguchan.frostrealm.data;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class CraftingGenerator extends CraftingDataHelper {
	public CraftingGenerator(PackOutput generator) {
		super(generator);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_PLANKS.get(), 4).requires(FrostBlocks.FROSTROOT_LOG.get())
				.unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(FrostBlocks.FROSTROOT_LOG.get()).getPath(), has(FrostBlocks.FROSTROOT_LOG.get())).save(consumer);

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_STAIRS.get(), FrostBlocks.FRIGID_STONE.get());

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.FRIGID_STONE_MOSSY.get())
				.unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(FrostBlocks.FRIGID_STONE_MOSSY.get()).getPath(), has(FrostBlocks.FRIGID_STONE_MOSSY.get())).save(consumer);


		makeSlab(consumer, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_BRICK.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.FRIGID_STONE.get())
				.unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(FrostBlocks.FRIGID_STONE.get()).getPath(), has(FrostBlocks.FRIGID_STONE.get())).save(consumer);

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());

		makeSlab(consumer, FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeStairs(consumer, FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeWoodFence(consumer, FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeFenceGate(consumer, FrostBlocks.FROSTROOT_FENCE_GATE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeDoor(consumer, FrostBlocks.FROSTROOT_DOOR.get(), FrostBlocks.FROSTROOT_PLANKS.get());

		foodCooking(FrostItems.FROZEN_FRUIT.get(), FrostItems.MELTED_FRUIT.get(), 0.1F, consumer);
		foodCooking(FrostItems.BEARBERRY.get(), FrostItems.COOKED_BEARBERRY.get(), 0.1F, consumer);
		foodCooking(FrostBlocks.SNOWPILE_QUAIL_EGG.get().asItem(), FrostItems.COOKED_SNOWPILE_QUAIL_EGG.get(), 0.2F, consumer);
		foodCooking(FrostItems.SNOWPILE_QUAIL_MEAT.get(), FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get(), 0.15F, consumer);

		helmetItem(consumer, "yeti_fur_helmet", FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR.get());
		chestplateItem(consumer, "yeti_fur_chestplate", FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR.get());
		leggingsItem(consumer, "yeti_fur_leggings", FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR.get());
		bootsItem(consumer, "yeti_fur_boots", FrostItems.YETI_FUR_BOOTS.get(), FrostItems.YETI_FUR.get());

		helmetItem(consumer, "kolossus_fur_helmet", FrostItems.KOLOSSUS_FUR_HELMET.get(), FrostItems.KOLOSSUS_FUR.get());
		chestplateItem(consumer, "kolossus_fur_chestplate", FrostItems.KOLOSSUS_FUR_CHESTPLATE.get(), FrostItems.KOLOSSUS_FUR.get());
		leggingsItem(consumer, "kolossus_fur_leggings", FrostItems.KOLOSSUS_FUR_LEGGINGS.get(), FrostItems.KOLOSSUS_FUR.get());
		bootsItem(consumer, "kolossus_fur_boots", FrostItems.KOLOSSUS_FUR_BOOTS.get(), FrostItems.KOLOSSUS_FUR.get());


		helmetItem(consumer, "astrium_helmet", FrostItems.ASTRIUM_HELMET.get(), FrostItems.ASTRIUM_INGOT.get());
		chestplateItem(consumer, "astrium_chestplate", FrostItems.ASTRIUM_CHESTPLATE.get(), FrostItems.ASTRIUM_INGOT.get());
		leggingsItem(consumer, "astrium_leggings", FrostItems.ASTRIUM_LEGGINGS.get(), FrostItems.ASTRIUM_INGOT.get());
		bootsItem(consumer, "astrium_boots", FrostItems.ASTRIUM_BOOTS.get(), FrostItems.ASTRIUM_INGOT.get());

		swordItem(consumer, "astrium_sword", FrostItems.ASTRIUM_SWORD.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		axeItem(consumer, "astrium_axe", FrostItems.ASTRIUM_AXE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		pickaxeItem(consumer, "astrium_pickaxe", FrostItems.ASTRIUM_PICKAXE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		shovelItem(consumer, "astrium_shovel", FrostItems.ASTRIUM_SHOVEL.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);

		swordItem(consumer, "rolga_sword", FrostItems.ROLGA_SWORD.get(), FrostItems.ROLGA_CRYSTAL.get(), Tags.Items.RODS_WOODEN);


        makeFrostTorch(consumer, FrostBlocks.FROST_TORCH.get().asItem());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_CHEST.get(), 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .define('S', FrostBlocks.FROSTROOT_PLANKS.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(FrostBlocks.FROSTROOT_PLANKS.get()).getPath(), has(FrostBlocks.FROSTROOT_PLANKS.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_CRAFTING_TABLE.get(), 1)
                .pattern("WW")
                .pattern("WW")
                .define('W', FrostBlocks.FROSTROOT_PLANKS.get())
                .unlockedBy("has_item", has(FrostBlocks.FROSTROOT_PLANKS.get())).save(consumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROST_CAMPFIRE.get(), 1)
                .pattern(" S ")
                .pattern("SFS")
                .pattern("LLL")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FrostItems.FROST_CRYSTAL.get())
                .define('L', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("has_item", has(ItemTags.LOGS_THAT_BURN)).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FrostItems.ROLGA_CRYSTAL.get(), 1)
				.pattern("CCC")
				.pattern("CFC")
				.pattern("CCC")
				.define('F', FrostItems.FROST_CRYSTAL.get())
				.define('C', FrostItems.ROLGA_SHARD.get())
				.unlockedBy("has_item", has(FrostItems.ROLGA_SHARD.get())).save(consumer);


		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FrostItems.FROST_CATALYST.get(), 1)
				.pattern(" S ")
				.pattern("SBS")
				.pattern(" S ")
				.define('S', FrostItems.STRAY_NECKLACE_PART.get())
				.define('B', Items.SNOWBALL)
				.unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(FrostItems.STRAY_NECKLACE_PART.get()).getPath(), has(FrostItems.STRAY_NECKLACE_PART.get())).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FrostBlocks.CRYSTAL_SMITHING_TABLE.get(), 1)
				.pattern("SS")
				.pattern("WA")
				.pattern("WW")
				.define('S', FrostBlocks.FRIGID_STONE_SMOOTH.get())
				.define('W', ItemTags.PLANKS)
				.define('A', FrostItems.ASTRIUM_INGOT.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(consumer);


		smeltOre(FrostItems.ASTRIUM_RAW.get(), FrostItems.ASTRIUM_INGOT.get(), 0.2F, consumer);
	}
}
