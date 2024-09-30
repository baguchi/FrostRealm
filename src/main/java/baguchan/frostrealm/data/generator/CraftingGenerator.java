package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.CraftingDataHelper;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

import static baguchan.frostrealm.FrostRealm.prefix;

public class CraftingGenerator extends CraftingDataHelper {
    public CraftingGenerator(PackOutput generator, CompletableFuture<HolderLookup.Provider> p_323846_) {
        super(generator, p_323846_);
	}

	@Override
    protected void buildRecipes(RecipeOutput consumer) {
		makeSlab(consumer, FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_STAIRS.get(), FrostBlocks.FRIGID_STONE.get());

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.FRIGID_STONE_MOSSY.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE_MOSSY.get()).getPath(), has(FrostBlocks.FRIGID_STONE_MOSSY.get())).save(consumer);


        makeSlab(consumer, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
        makeStairs(consumer, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.SHERBET_SANDSTONE.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.SHERBET_SAND.get())
				.unlockedBy("has_item", has(FrostBlocks.SHERBET_SAND.get())).save(consumer);


		makeSlab(consumer, FrostBlocks.SHERBET_SANDSTONE_SLAB.get(), FrostBlocks.SHERBET_SANDSTONE.get());
		makeStairs(consumer, FrostBlocks.SHERBET_SANDSTONE_STAIRS.get(), FrostBlocks.SHERBET_SANDSTONE.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.PERMA_SLATE_BRICK.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.PERMA_SLATE.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.PERMA_SLATE.get()).getPath(), has(FrostBlocks.PERMA_SLATE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.PERMA_SLATE_SMOOTH.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.PERMA_SLATE_BRICK.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.PERMA_SLATE_BRICK.get()).getPath(), has(FrostBlocks.PERMA_SLATE_BRICK.get())).save(consumer);


		makeSlab(consumer, FrostBlocks.PERMA_SLATE_BRICK_SLAB.get(), FrostBlocks.PERMA_SLATE_BRICK.get());
		makeStairs(consumer, FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get(), FrostBlocks.PERMA_SLATE_BRICK.get());


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_BRICK.get(), 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', FrostBlocks.FRIGID_STONE.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE.get()).getPath(), has(FrostBlocks.FRIGID_STONE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_SMOOTH.get(), 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', FrostBlocks.FRIGID_STONE_BRICK.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE_BRICK.get()).getPath(), has(FrostBlocks.FRIGID_STONE_BRICK.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get(), 1)
				.pattern("B")
				.pattern("B")
				.define('B', FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get()).getPath(), has(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())).save(consumer);


		makeSlab(consumer, FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());


		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_PLANKS.get(), 4).requires(FrostBlocks.FROSTROOT_LOG.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FROSTROOT_LOG.get()).getPath(), has(FrostBlocks.FROSTROOT_LOG.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_PLANKS.get(), 4).requires(FrostBlocks.STRIPPED_FROSTROOT_LOG.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.STRIPPED_FROSTROOT_LOG.get()).getPath(), has(FrostBlocks.STRIPPED_FROSTROOT_LOG.get())).save(consumer, prefix("stripped_frostroot_to_plank"));

		makeSlab(consumer, FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeStairs(consumer, FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeWoodFence(consumer, FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeFenceGate(consumer, FrostBlocks.FROSTROOT_FENCE_GATE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeDoor(consumer, FrostBlocks.FROSTROOT_DOOR.get(), FrostBlocks.FROSTROOT_PLANKS.get());


		foodCooking(FrostItems.FROZEN_FRUIT.get(), FrostItems.MELTED_FRUIT.get(), 0.1F, consumer);
		foodCooking(FrostItems.BEARBERRY.get(), FrostItems.COOKED_BEARBERRY.get(), 0.1F, consumer);
		foodCooking(FrostBlocks.SNOWPILE_QUAIL_EGG.get().asItem(), FrostItems.COOKED_SNOWPILE_QUAIL_EGG.get(), 0.2F, consumer);
		foodCooking(FrostItems.SNOWPILE_QUAIL_MEAT.get(), FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get(), 0.15F, consumer);
		foodCooking(FrostItems.FROST_BOAR_MEAT.get(), FrostItems.COOKED_FROST_BOAR_MEAT.get(), 0.15F, consumer);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(FrostItems.CRYONITE.get()), RecipeCategory.MISC, FrostItems.CRYONITE_CREAM.get(), 0.1F, 200).unlockedBy("has_item", has(FrostItems.CRYONITE.get())).save(consumer, FrostRealm.prefix("smelting_" + BuiltInRegistries.ITEM.getKey(FrostItems.CRYONITE.get()).getPath()));


		helmetItem(consumer, "yeti_fur_helmet", FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR.get());
		chestplateItem(consumer, "yeti_fur_chestplate", FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR.get());
		leggingsItem(consumer, "yeti_fur_leggings", FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR.get());
		bootsItem(consumer, "yeti_fur_boots", FrostItems.YETI_FUR_BOOTS.get(), FrostItems.YETI_FUR.get());

		helmetItem(consumer, "frost_boar_fur_helmet", FrostItems.FROST_BOAR_FUR_HELMET.get(), FrostItems.FROST_BOAR_FUR.get());
		chestplateItem(consumer, "frost_boar_fur_chestplate", FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR.get());
		leggingsItem(consumer, "frost_boar_fur_leggings", FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR.get());
		bootsItem(consumer, "frost_boar_fur_boots", FrostItems.FROST_BOAR_FUR_BOOTS.get(), FrostItems.FROST_BOAR_FUR.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, FrostItems.WOLFFLUE_ASTRIUM_ARMOR.get(), 1)
				.pattern("AAA")
				.pattern("SAA")
				.pattern("  S")
				.define('A', FrostItems.ASTRIUM_INGOT.get())
				.define('S', Items.STRING)
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(consumer);


        helmetItem(consumer, "astrium_helmet", FrostItems.ASTRIUM_HELMET.get(), FrostItems.ASTRIUM_INGOT.get());
        chestplateItem(consumer, "astrium_chestplate", FrostItems.ASTRIUM_CHESTPLATE.get(), FrostItems.ASTRIUM_INGOT.get());
        leggingsItem(consumer, "astrium_leggings", FrostItems.ASTRIUM_LEGGINGS.get(), FrostItems.ASTRIUM_INGOT.get());
        bootsItem(consumer, "astrium_boots", FrostItems.ASTRIUM_BOOTS.get(), FrostItems.ASTRIUM_INGOT.get());

        swordItem(consumer, "astrium_sword", FrostItems.ASTRIUM_SWORD.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
        axeItem(consumer, "astrium_axe", FrostItems.ASTRIUM_AXE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
        pickaxeItem(consumer, "astrium_pickaxe", FrostItems.ASTRIUM_PICKAXE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
        shovelItem(consumer, "astrium_shovel", FrostItems.ASTRIUM_SHOVEL.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		hoeItem(consumer, "astrium_hoe", FrostItems.ASTRIUM_HOE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		sickleItem(consumer, "astrium_sickle", FrostItems.ASTRIUM_SICKLE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);

		swordItem(consumer, "glacinium_sword", FrostItems.GLACINIUM_SWORD.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		axeItem(consumer, "glacinium_axe", FrostItems.GLACINIUM_AXE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		pickaxeItem(consumer, "glacinium_pickaxe", FrostItems.GLACINIUM_PICKAXE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		shovelItem(consumer, "glacinium_shovel", FrostItems.GLACINIUM_SHOVEL.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		hoeItem(consumer, "glacinium_hoe", FrostItems.GLACINIUM_HOE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		sickleItem(consumer, "glacinium_sickle", FrostItems.GLACINIUM_SICKLE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);


		makeFrostTorch(consumer, FrostBlocks.FROST_TORCH.get().asItem());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_CHEST.get(), 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .define('S', FrostBlocks.FROSTROOT_PLANKS.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FROSTROOT_PLANKS.get()).getPath(), has(FrostBlocks.FROSTROOT_PLANKS.get())).save(consumer);

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
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.ASTRIUM_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.ASTRIUM_INGOT.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(consumer);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostItems.ASTRIUM_INGOT.get(), 9)
				.requires(FrostBlocks.ASTRIUM_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(consumer, prefix("astrium_ingot_from_block"));


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.RAW_ASTRIUM_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.ASTRIUM_RAW.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_RAW.get())).save(consumer);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostItems.ASTRIUM_RAW.get(), 9)
				.requires(FrostBlocks.RAW_ASTRIUM_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_RAW.get())).save(consumer, prefix("astrium_raw_from_block"));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.GLIMMERROCK_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.GLIMMERROCK.get())
				.unlockedBy("has_item", has(FrostItems.GLIMMERROCK.get())).save(consumer);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostItems.GLIMMERROCK.get(), 9)
				.requires(FrostBlocks.GLIMMERROCK_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.GLIMMERROCK.get())).save(consumer, prefix("glimmer_rock_from_block"));


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROST_CRYSTAL_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.FROST_CRYSTAL.get())
				.unlockedBy("has_item", has(FrostItems.FROST_CRYSTAL.get())).save(consumer);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostItems.FROST_CRYSTAL.get(), 9)
				.requires(FrostBlocks.FROST_CRYSTAL_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.FROST_CRYSTAL.get())).save(consumer, prefix("frost_crystal_from_block"));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, FrostItems.GLACINIUM_INGOT.get(), 1)
				.requires(FrostItems.GLACINIUM_CRYSTAL.get())
				.requires(FrostItems.GLACINIUM_CRYSTAL.get())
				.requires(FrostItems.FROST_CRYSTAL.get())
				.requires(FrostItems.FROST_CRYSTAL.get())
				.requires(FrostItems.ASTRIUM_INGOT.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_CRYSTAL.get())).save(consumer);



		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FrostItems.FROST_CATALYST.get(), 1)
				.pattern(" S ")
				.pattern("SBS")
				.pattern(" S ")
				.define('S', FrostItems.STRAY_NECKLACE_PART.get())
				.define('B', Items.SNOWBALL)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(FrostItems.STRAY_NECKLACE_PART.get()).getPath(), has(FrostItems.STRAY_NECKLACE_PART.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SMITHING_TABLE, 1)
                .pattern("SS")
                .pattern("WW")
                .pattern("WW")
                .define('S', FrostItems.ASTRIUM_INGOT.get())
				.define('W', ItemTags.PLANKS)
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(consumer);


		smeltOre(FrostItems.ASTRIUM_RAW.get(), FrostItems.ASTRIUM_INGOT.get(), 0.2F, consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FrostItems.RYE_BREAD.get(), 1)
				.pattern("WWW")
				.define('W', FrostItems.RYE.get())
				.unlockedBy("has_item", has(FrostItems.RYE.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FrostBlocks.AURORA_INFUSER.get(), 1)
                .pattern("ASA")
                .pattern("TST")
                .pattern("TTT")
                .define('T', FrostBlocks.FRIGID_STONE_SMOOTH.get())
                .define('S', FrostItems.STARDUST_CRYSTAL.get())
                .define('A', FrostItems.ASTRIUM_INGOT.get())
                .unlockedBy("has_item", has(FrostItems.STARDUST_CRYSTAL.get())).save(consumer);

	}
}
