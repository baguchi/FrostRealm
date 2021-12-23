package baguchan.frostrealm.data;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class CraftingGenerator extends CraftingDataHelper {
	public CraftingGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(FrostBlocks.FROSTROOT_PLANKS, 4).requires(FrostBlocks.FROSTROOT_LOG)
				.unlockedBy("has_" + FrostBlocks.FROSTROOT_LOG.getRegistryName().getPath(), has(FrostBlocks.FROSTROOT_LOG)).save(consumer);

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_SLAB, FrostBlocks.FRIGID_STONE);
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_STAIRS, FrostBlocks.FRIGID_STONE);

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_MOSSY_SLAB, FrostBlocks.FRIGID_STONE_MOSSY);
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_MOSSY_STAIRS, FrostBlocks.FRIGID_STONE_MOSSY);

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_BRICK_SLAB, FrostBlocks.FRIGID_STONE_BRICK);
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_BRICK_STAIRS, FrostBlocks.FRIGID_STONE_BRICK);

		makeSlab(consumer, FrostBlocks.FROSTROOT_PLANKS_SLAB, FrostBlocks.FROSTROOT_PLANKS);
		makeStairs(consumer, FrostBlocks.FROSTROOT_PLANKS_STAIRS, FrostBlocks.FROSTROOT_PLANKS);
		makeWoodFence(consumer, FrostBlocks.FROSTROOT_FENCE, FrostBlocks.FROSTROOT_PLANKS);
		makeFenceGate(consumer, FrostBlocks.FROSTROOT_FENCE_GATE, FrostBlocks.FROSTROOT_PLANKS);
		makeDoor(consumer, FrostBlocks.FROSTROOT_DOOR, FrostBlocks.FROSTROOT_PLANKS);

		foodCooking(FrostItems.FROZEN_FRUIT, FrostItems.MELTED_FRUIT, 0.1F, consumer);
		foodCooking(FrostItems.BEARBERRY, FrostItems.COOKED_BEARBERRY, 0.1F, consumer);

		makeFrostTorch(consumer, FrostItems.FROST_TORCH);

		ShapedRecipeBuilder.shaped(FrostItems.FROST_CATALYST, 1)
				.pattern(" S ")
				.pattern("SBS")
				.pattern(" S ")
				.define('S', FrostItems.STRAY_NECKLACE_PART)
				.define('B', Items.SNOWBALL)
				.unlockedBy("has_" + FrostItems.STRAY_NECKLACE_PART.getRegistryName().getPath(), has(FrostItems.STRAY_NECKLACE_PART)).save(consumer);
	}
}
