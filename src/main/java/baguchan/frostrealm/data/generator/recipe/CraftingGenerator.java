package baguchan.frostrealm.data.generator.recipe;

import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.data.CraftingDataHelper;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

public class CraftingGenerator extends CraftingDataHelper {
	public CraftingGenerator(HolderLookup.Provider generator, RecipeOutput p_323846_) {
		super(generator, p_323846_);
	}

	@Override
	protected void buildRecipes() {
		HolderLookup<Item> lookup = this.registries.lookupOrThrow(Registries.ITEM);
		HolderLookup<AttachableCrystal> attach = this.registries.lookupOrThrow(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY);
		makeSlab(this.output, FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE.get());
		makeStairs(this.output, FrostBlocks.FRIGID_STONE_STAIRS.get(), FrostBlocks.FRIGID_STONE.get());

		makeSlab(this.output, FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());
		makeStairs(this.output, FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.FRIGID_STONE_MOSSY.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE_MOSSY.get()).getPath(), has(FrostBlocks.FRIGID_STONE_MOSSY.get())).save(this.output);


		makeSlab(this.output, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		makeStairs(this.output, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.SHERBET_SANDSTONE.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.SHERBET_SAND.get())
				.unlockedBy("has_item", has(FrostBlocks.SHERBET_SAND.get())).save(this.output);


		makeSlab(this.output, FrostBlocks.SHERBET_SANDSTONE_SLAB.get(), FrostBlocks.SHERBET_SANDSTONE.get());
		makeStairs(this.output, FrostBlocks.SHERBET_SANDSTONE_STAIRS.get(), FrostBlocks.SHERBET_SANDSTONE.get());

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.PERMA_SLATE_BRICK.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.PERMA_SLATE.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.PERMA_SLATE.get()).getPath(), has(FrostBlocks.PERMA_SLATE.get())).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.PERMA_SLATE_SMOOTH.get(), 4)
				.pattern("BB")
				.pattern("BB")
				.define('B', FrostBlocks.PERMA_SLATE_BRICK.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.PERMA_SLATE_BRICK.get()).getPath(), has(FrostBlocks.PERMA_SLATE_BRICK.get())).save(this.output);


		makeSlab(this.output, FrostBlocks.PERMA_SLATE_BRICK_SLAB.get(), FrostBlocks.PERMA_SLATE_BRICK.get());
		makeStairs(this.output, FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get(), FrostBlocks.PERMA_SLATE_BRICK.get());
		makeWall(this.output, FrostBlocks.PERMA_SLATE_BRICK_WALL.get(), FrostBlocks.PERMA_SLATE_BRICK.get());

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_BRICK.get(), 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', FrostBlocks.FRIGID_STONE.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE.get()).getPath(), has(FrostBlocks.FRIGID_STONE.get())).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FRIGID_STONE_SMOOTH.get(), 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', FrostBlocks.FRIGID_STONE_BRICK.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE_BRICK.get()).getPath(), has(FrostBlocks.FRIGID_STONE_BRICK.get())).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get(), 1)
				.pattern("B")
				.pattern("B")
				.define('B', FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get()).getPath(), has(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())).save(this.output);


		makeSlab(this.output, FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		makeStairs(this.output, FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		makeWall(this.output, FrostBlocks.FRIGID_STONE_BRICK_WALL.get(), FrostBlocks.FRIGID_STONE_BRICK.get());

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_PLANKS.get(), 4).requires(FrostBlocks.FROSTROOT_LOG.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FROSTROOT_LOG.get()).getPath(), has(FrostBlocks.FROSTROOT_LOG.get())).save(this.output);
		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTROOT_PLANKS.get(), 4).requires(FrostBlocks.STRIPPED_FROSTROOT_LOG.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.STRIPPED_FROSTROOT_LOG.get()).getPath(), has(FrostBlocks.STRIPPED_FROSTROOT_LOG.get())).save(this.output, prefix("stripped_frostroot_to_plank"));

		makeSlab(this.output, FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeStairs(this.output, FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeWoodFence(this.output, FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeFenceGate(this.output, FrostBlocks.FROSTROOT_FENCE_GATE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeDoor(this.output, FrostBlocks.FROSTROOT_DOOR.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeTrapDoor(this.output, FrostBlocks.FROSTROOT_TRAPDOOR.get(), FrostBlocks.FROSTROOT_PLANKS.get());

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTBITE_PLANKS.get(), 4).requires(FrostBlocks.FROSTBITE_LOG.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.FROSTBITE_LOG.get()).getPath(), has(FrostBlocks.FROSTBITE_LOG.get())).save(this.output);
		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROSTBITE_PLANKS.get(), 4).requires(FrostBlocks.STRIPPED_FROSTBITE_LOG.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.STRIPPED_FROSTBITE_LOG.get()).getPath(), has(FrostBlocks.STRIPPED_FROSTBITE_LOG.get())).save(this.output, prefix("stripped_frostbite_to_plank"));

		makeSlab(this.output, FrostBlocks.FROSTBITE_PLANKS_SLAB.get(), FrostBlocks.FROSTBITE_PLANKS.get());
		makeStairs(this.output, FrostBlocks.FROSTBITE_PLANKS_STAIRS.get(), FrostBlocks.FROSTBITE_PLANKS.get());
		makeWoodFence(this.output, FrostBlocks.FROSTBITE_FENCE.get(), FrostBlocks.FROSTBITE_PLANKS.get());
		makeFenceGate(this.output, FrostBlocks.FROSTBITE_FENCE_GATE.get(), FrostBlocks.FROSTBITE_PLANKS.get());
		makeDoor(this.output, FrostBlocks.FROSTBITE_DOOR.get(), FrostBlocks.FROSTBITE_PLANKS.get());
		makeTrapDoor(this.output, FrostBlocks.FROSTBITE_TRAPDOOR.get(), FrostBlocks.FROSTBITE_PLANKS.get());


		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.ROCK_WOOD_PLANKS.get(), 4).requires(FrostBlocks.ROCK_WOOD.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.ROCK_WOOD.get()).getPath(), has(FrostBlocks.ROCK_WOOD.get())).save(this.output);

		makeSlab(this.output, FrostBlocks.ROCK_WOOD_PLANKS_SLAB.get(), FrostBlocks.ROCK_WOOD_PLANKS.get());
		makeStairs(this.output, FrostBlocks.ROCK_WOOD_PLANKS_STAIRS.get(), FrostBlocks.ROCK_WOOD_PLANKS.get());
		makeWoodFence(this.output, FrostBlocks.ROCK_WOOD_FENCE.get(), FrostBlocks.ROCK_WOOD_PLANKS.get());
		makeFenceGate(this.output, FrostBlocks.ROCK_WOOD_FENCE_GATE.get(), FrostBlocks.ROCK_WOOD_PLANKS.get());
		makeDoor(this.output, FrostBlocks.ROCK_WOOD_DOOR.get(), FrostBlocks.ROCK_WOOD_PLANKS.get());
		makeTrapDoor(this.output, FrostBlocks.ROCK_WOOD_TRAPDOOR.get(), FrostBlocks.ROCK_WOOD_PLANKS.get());
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, FrostItems.ROCK_WOOD_STICK.get(), 4)
				.pattern("A")
				.pattern("A")
				.define('A', FrostBlocks.ROCK_WOOD_PLANKS.get())
				.unlockedBy("has_item", has(FrostBlocks.ROCK_WOOD_PLANKS.get())).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, Blocks.CRAFTING_TABLE, 1)
				.pattern("AA")
				.pattern("AA")
				.define('A', FrostBlocks.ROCK_WOOD_PLANKS.get())
				.unlockedBy("has_item", has(FrostBlocks.ROCK_WOOD_PLANKS.get())).save(this.output, prefix("rock_wood_crafting_table"));



		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.DRIP_PLANKS.get(), 4).requires(FrostBlocks.DRIP_LOG.get())
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.DRIP_LOG.get()).getPath(), has(FrostBlocks.DRIP_LOG.get())).save(this.output);
/*
		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.DRIP_PLANKS.get(), 4).requires(FrostBlocks.STRIPPED_DRIP_LOG.get())
				.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(FrostBlocks.STRIPPED_DRIP_LOG.get()).getPath(), has(FrostBlocks.STRIPPED_DRIP_LOG.get())).save(this.output, prefix("stripped_frostbite_to_plank"));
*/

        makeSlab(this.output, FrostBlocks.DRIP_PLANKS_SLAB.get(), FrostBlocks.DRIP_PLANKS.get());
        makeStairs(this.output, FrostBlocks.DRIP_PLANKS_STAIRS.get(), FrostBlocks.DRIP_PLANKS.get());
        makeWoodFence(this.output, FrostBlocks.DRIP_FENCE.get(), FrostBlocks.DRIP_PLANKS.get());
        makeFenceGate(this.output, FrostBlocks.DRIP_FENCE_GATE.get(), FrostBlocks.DRIP_PLANKS.get());
        makeDoor(this.output, FrostBlocks.DRIP_DOOR.get(), FrostBlocks.DRIP_PLANKS.get());
        makeTrapDoor(this.output, FrostBlocks.DRIP_TRAPDOOR.get(), FrostBlocks.DRIP_PLANKS.get());


		foodCooking(FrostItems.FROZEN_FRUIT.get(), FrostItems.MELTED_FRUIT.get(), 0.1F, this.output);
		foodCooking(FrostItems.BEARBERRY.get(), FrostItems.COOKED_BEARBERRY.get(), 0.1F, this.output);
		foodCooking(FrostBlocks.SNOWPILE_QUAIL_EGG.get().asItem(), FrostItems.COOKED_SNOWPILE_QUAIL_EGG.get(), 0.2F, this.output);
		foodCooking(FrostItems.SNOWPILE_QUAIL_MEAT.get(), FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get(), 0.15F, this.output);
		foodCooking(FrostItems.GLACIER_BOAR_MEAT.get(), FrostItems.COOKED_GLACIER_BOAR_MEAT.get(), 0.15F, this.output);
		foodCooking(FrostItems.SILK_MOON_MEAT.get(), FrostItems.COOKED_SILK_MOON_MEAT.get(), 0.05F, this.output);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(FrostItems.CRYONITE.get()), RecipeCategory.MISC, CookingBookCategory.MISC, FrostItems.CRYONITE_CREAM.get(), 0.1F, 200).unlockedBy("has_item", has(FrostItems.CRYONITE.get())).save(this.output, prefix("smelting_" + BuiltInRegistries.ITEM.getKey(FrostItems.CRYONITE.get()).getPath()));


		helmetItem(this.output, "yeti_fur_helmet", FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR.get());
		chestplateItem(this.output, "yeti_fur_chestplate", FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR.get());
		leggingsItem(this.output, "yeti_fur_leggings", FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR.get());
		bootsItem(this.output, "yeti_fur_boots", FrostItems.YETI_FUR_BOOTS.get(), FrostItems.YETI_FUR.get());

		helmetItem(this.output, "frost_boar_fur_helmet", FrostItems.GLACIER_BOAR_FUR_HELMET.get(), FrostItems.GLACIER_BOAR_FUR.get());
		chestplateItem(this.output, "frost_boar_fur_chestplate", FrostItems.GLACIER_BOAR_FUR_CHESTPLATE.get(), FrostItems.GLACIER_BOAR_FUR.get());
		leggingsItem(this.output, "frost_boar_fur_leggings", FrostItems.GLACIER_BOAR_FUR_LEGGINGS.get(), FrostItems.GLACIER_BOAR_FUR.get());
		bootsItem(this.output, "frost_boar_fur_boots", FrostItems.GLACIER_BOAR_FUR_BOOTS.get(), FrostItems.GLACIER_BOAR_FUR.get());

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, FrostItems.WOLFFLUE_ASTRIUM_ARMOR.get(), 1)
				.pattern("AAA")
				.pattern("SAA")
				.pattern("  S")
				.define('A', FrostItems.ASTRIUM_INGOT.get())
				.define('S', Items.STRING)
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, FrostItems.WOLFFLUE_GLACIER_BOAR_ARMOR.get(), 1)
				.pattern("AAA")
				.pattern("SAA")
				.pattern("  S")
				.define('A', FrostItems.GLACIER_BOAR_FUR.get())
				.define('S', Items.STRING)
				.unlockedBy("has_item", has(FrostItems.GLACIER_BOAR_FUR.get())).save(this.output);
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, FrostItems.WOLFFLUE_SADDLE.get(), 1)
                .pattern(" A ")
                .pattern("AIA")
                .define('A', FrostItems.GLACIER_BOAR_FUR.get())
                .define('I', FrostItems.ASTRIUM_INGOT)
                .unlockedBy("has_item", has(FrostItems.GLACIER_BOAR_FUR.get())).save(this.output);


		helmetItem(this.output, "astrium_helmet", FrostItems.ASTRIUM_HELMET.get(), FrostItems.ASTRIUM_INGOT.get());
		chestplateItem(this.output, "astrium_chestplate", FrostItems.ASTRIUM_CHESTPLATE.get(), FrostItems.ASTRIUM_INGOT.get());
		leggingsItem(this.output, "astrium_leggings", FrostItems.ASTRIUM_LEGGINGS.get(), FrostItems.ASTRIUM_INGOT.get());
		bootsItem(this.output, "astrium_boots", FrostItems.ASTRIUM_BOOTS.get(), FrostItems.ASTRIUM_INGOT.get());

		swordItem(this.output, "astrium_sword", FrostItems.ASTRIUM_SWORD.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		axeItem(this.output, "astrium_axe", FrostItems.ASTRIUM_AXE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		pickaxeItem(this.output, "astrium_pickaxe", FrostItems.ASTRIUM_PICKAXE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		shovelItem(this.output, "astrium_shovel", FrostItems.ASTRIUM_SHOVEL.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		hoeItem(this.output, "astrium_hoe", FrostItems.ASTRIUM_HOE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		sickleItem(this.output, "astrium_sickle", FrostItems.ASTRIUM_SICKLE.get(), FrostItems.ASTRIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
        javelinItem(this.output, "astrium_spear", FrostItems.ASTRIUM_SPEAR.get(), FrostItems.ASTRIUM_SPEAR.get(), Tags.Items.RODS_WOODEN);

		swordItem(this.output, "glacinium_sword", FrostItems.GLACINIUM_SWORD.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		axeItem(this.output, "glacinium_axe", FrostItems.GLACINIUM_AXE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		pickaxeItem(this.output, "glacinium_pickaxe", FrostItems.GLACINIUM_PICKAXE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		shovelItem(this.output, "glacinium_shovel", FrostItems.GLACINIUM_SHOVEL.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		hoeItem(this.output, "glacinium_hoe", FrostItems.GLACINIUM_HOE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
		sickleItem(this.output, "glacinium_sickle", FrostItems.GLACINIUM_SICKLE.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);
        javelinItem(this.output, "glacinium_javelin", FrostItems.GLACINIUM_SPEAR.get(), FrostItems.GLACINIUM_INGOT.get(), Tags.Items.RODS_WOODEN);

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, Items.ARROW, 4)
				.pattern("B")
				.pattern("S")
				.pattern("F")
				.define('B', FrostItems.GLACIER_BOAR_HORN.get())
				.define('S', Items.STICK)
				.define('F', Items.FEATHER)
				.unlockedBy("has_item", has(FrostItems.GLACIER_BOAR_HORN.get())).save(this.output, prefix("horn_arrow"));


		makeFrostTorch(this.output, FrostBlocks.FROST_TORCH.get().asItem());

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROST_CAMPFIRE.get(), 1)
				.pattern(" S ")
				.pattern("SFS")
				.pattern("LLL")
				.define('S', Tags.Items.RODS_WOODEN)
				.define('F', FrostItems.FROST_CRYSTAL.get())
				.define('L', ItemTags.LOGS_THAT_BURN)
				.unlockedBy("has_item", has(ItemTags.LOGS_THAT_BURN)).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.ASTRIUM_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.ASTRIUM_INGOT.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(this.output);

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.ASTRIUM_INGOT.get(), 9)
				.requires(FrostBlocks.ASTRIUM_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(this.output, prefix("astrium_ingot_from_block"));


		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.RAW_ASTRIUM_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.ASTRIUM_RAW.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_RAW.get())).save(this.output);

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.ASTRIUM_RAW.get(), 9)
				.requires(FrostBlocks.RAW_ASTRIUM_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_RAW.get())).save(this.output, prefix("astrium_raw_from_block"));

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.GLIMMERROCK_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.GLIMMERROCK.get())
				.unlockedBy("has_item", has(FrostItems.GLIMMERROCK.get())).save(this.output);

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.GLIMMERROCK.get(), 9)
				.requires(FrostBlocks.GLIMMERROCK_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.GLIMMERROCK.get())).save(this.output, prefix("glimmer_rock_from_block"));


		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.FROST_CRYSTAL_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.FROST_CRYSTAL.get())
				.unlockedBy("has_item", has(FrostItems.FROST_CRYSTAL.get())).save(this.output);

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.FROST_CRYSTAL.get(), 9)
				.requires(FrostBlocks.FROST_CRYSTAL_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.FROST_CRYSTAL.get())).save(this.output, prefix("frost_crystal_from_block"));

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.RYE_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.RYE.get())
				.unlockedBy("has_item", has(FrostItems.RYE.get())).save(this.output);

		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.RYE.get(), 9)
				.requires(FrostBlocks.RYE_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.RYE.get())).save(this.output, prefix("rye_from_block"));


		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.GLACINIUM_INGOT.get(), 1)
				.requires(FrostItems.GLACINIUM_CRYSTAL.get())
				.requires(FrostItems.GLACINIUM_CRYSTAL.get())
				.requires(FrostItems.FROST_CRYSTAL.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_CRYSTAL.get())).save(this.output);
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.GLACINIUM_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.GLACINIUM_INGOT.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_INGOT.get())).save(this.output);
		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.GLACINIUM_INGOT.get(), 9)
				.requires(FrostBlocks.GLACINIUM_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_INGOT.get())).save(this.output, prefix("glacinium_to_ingot"));
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BUILDING_BLOCKS, FrostBlocks.RAW_GLACINIUM_BLOCK.get(), 1)
				.pattern("AAA")
				.pattern("AAA")
				.pattern("AAA")
				.define('A', FrostItems.GLACINIUM_CRYSTAL.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_CRYSTAL.get())).save(this.output);
		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BUILDING_BLOCKS, FrostItems.GLACINIUM_CRYSTAL.get(), 9)
				.requires(FrostBlocks.RAW_GLACINIUM_BLOCK.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_CRYSTAL.get())).save(this.output, prefix("raw_glacinium_to_ingot"));


		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, FrostItems.FROST_CATALYST.get(), 1)
				.pattern(" S ")
				.pattern("SBS")
				.pattern(" S ")
				.define('S', FrostItems.STRAY_NECKLACE_PART.get())
				.define('B', FrostItems.FROST_CRYSTAL)
				.unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(FrostItems.STRAY_NECKLACE_PART.get()).getPath(), has(FrostItems.STRAY_NECKLACE_PART.get())).save(this.output, prefix("catalyst_with_frost"));
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, FrostItems.FROST_CATALYST.get(), 1)
				.pattern(" S ")
				.pattern("SBS")
				.pattern(" S ")
				.define('S', FrostItems.STRAY_NECKLACE_PART.get())
				.define('B', Items.DIAMOND)
				.unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(FrostItems.STRAY_NECKLACE_PART.get()).getPath(), has(FrostItems.STRAY_NECKLACE_PART.get())).save(this.output, prefix("catalyst_with_diamond"));



		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, Blocks.SMITHING_TABLE, 1)
                .pattern("SS")
                .pattern("WW")
                .pattern("WW")
                .define('S', FrostItems.ASTRIUM_INGOT.get())
				.define('W', ItemTags.PLANKS)
				.unlockedBy("has_item", has(FrostItems.ASTRIUM_INGOT.get())).save(this.output, prefix("astrium_smithing_table"));


		smeltOre(FrostItems.ASTRIUM_RAW.get(), FrostItems.ASTRIUM_INGOT.get(), 0.2F, this.output);

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.FOOD, FrostItems.RYE_BREAD.get(), 1)
				.pattern("WWW")
				.define('W', FrostItems.RYE.get())
				.unlockedBy("has_item", has(FrostItems.RYE.get())).save(this.output);
		ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.FOOD, FrostItems.RYE_PANCAKE.get(), 1)
				.requires(FrostItems.RYE.get())
				.requires(FrostBlocks.SNOWPILE_QUAIL_EGG.get())
				.requires(Items.SUGAR)
				.unlockedBy("has_item", has(FrostItems.RYE.get())).save(this.output);


		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, FrostBlocks.AURORA_INFUSER.get(), 1)
                .pattern("ASA")
                .pattern("TST")
                .pattern("TTT")
                .define('T', FrostBlocks.FRIGID_STONE_SMOOTH.get())
                .define('S', FrostItems.STARDUST_CRYSTAL.get())
				.define('A', FrostItems.GLACINIUM_INGOT.get())
				.unlockedBy("has_item", has(FrostItems.GLACINIUM_INGOT.get())).save(this.output);

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, FrostItems.UNSTABLE_VENOM_CRYSTAL.get(), 1)
				.pattern("VVV")
				.pattern("VWV")
				.pattern("VVV")
				.define('V', FrostItems.UNSTABLE_VENOM_CRYSTAL.get())
				.define('W', FrostItems.WARPED_CRYSTAL.get())
				.unlockedBy("has_item", has(FrostItems.VENOM_CRYSTAL.get())).save(this.output);

		foodCooking(FrostItems.SUGARBEET.get(), Items.SUGAR, 0.05F, this.output, "sugar_beet");
		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, Items.BUNDLE, 1)
				.pattern("S")
				.pattern("L")
				.define('S', Items.STRING)
				.define('L', FrostItems.GLACIER_BOAR_FUR.get())
				.unlockedBy("has_item", has(FrostItems.GLACIER_BOAR_FUR.get())).save(this.output, prefix("bundle_frost_boar_fur"));

		ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, FrostItems.COATING_FUR, 1)
				.pattern("S")
				.pattern("L")
				.define('S', FrostItems.CRYONITE_CREAM)
				.define('L', FrostItems.GLACIER_BOAR_FUR.get())
				.unlockedBy("has_item", has(FrostItems.CRYONITE_CREAM.get())).save(this.output);


	}
}
