package baguchan.frostrealm.data;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.function.Consumer;

public class CraftingGenerator extends CraftingDataHelper {
	public CraftingGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void saveAdvancement(HashCache p_208310_1_, JsonObject p_208310_2_, Path p_208310_3_) {
		//Silence. This just makes it so that we don't gen advancements
		//TODO Recipe advancements control the unlock of a recipe, so if we ever consider actually making them, recipes should unlock based on also possible prerequisite conditions, instead of ONLY obtaining the item itself
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(FrostBlocks.FROSTROOT_PLANKS.get()).requires(FrostBlocks.FROSTROOT_LOG.get())
				.unlockedBy("has_" + FrostBlocks.FROSTROOT_LOG.get().getRegistryName().getPath(), has(FrostBlocks.FROSTROOT_LOG.get())).save(consumer);

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_STAIRS.get(), FrostBlocks.FRIGID_STONE.get());

		makeSlab(consumer, FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		makeStairs(consumer, FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());

		makeSlab(consumer, FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeStairs(consumer, FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeWoodFence(consumer, FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		makeFenceGate(consumer, FrostBlocks.FROSTROOT_FENCE_GATE.get(), FrostBlocks.FROSTROOT_PLANKS.get());

		foodCooking(FrostItems.FROZEN_FRUIT.get(), FrostItems.MELTED_FRUIT.get(), 0.1F, consumer);

		makeFrostTorch(consumer, FrostItems.FROST_TORCH.get());

		ShapedRecipeBuilder.shaped(FrostItems.FROST_CATALYST.get(), 1)
				.pattern(" S ")
				.pattern("SBS")
				.pattern(" S ")
				.define('S', FrostItems.STRAY_NECKLACE_PART.get())
				.define('B', Items.SNOWBALL)
				.unlockedBy("has_" + FrostItems.STRAY_NECKLACE_PART.get().getRegistryName().getPath(), has(FrostItems.STRAY_NECKLACE_PART.get())).save(consumer);
	}
}
