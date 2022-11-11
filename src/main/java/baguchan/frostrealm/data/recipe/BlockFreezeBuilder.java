package baguchan.frostrealm.data.recipe;

import baguchan.frostrealm.recipe.BlockPropertyPair;
import baguchan.frostrealm.recipe.BlockStateIngredient;
import baguchan.frostrealm.recipe.recipes.BlockPlacementFreezeRecipe;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BlockFreezeBuilder extends PlacementFreezeBuilder {
	private final BlockStateIngredient ingredient;

	public BlockFreezeBuilder(BlockStateIngredient ingredient, @Nullable BlockStateIngredient bypassBlock, BlockPropertyPair result, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPlacementFreezeRecipe.Serializer serializer) {
		super(bypassBlock, result, biomeKey, biomeTag, serializer);
		this.ingredient = ingredient;
	}

	public static PlacementFreezeBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, BlockPropertyPair result, @Nullable TagKey<Biome> biomeTag, BlockPlacementFreezeRecipe.Serializer serializer) {
		return recipe(ingredient, bypassBlock, result, null, biomeTag, serializer);
	}

	public static PlacementFreezeBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, BlockPropertyPair result, @Nullable ResourceKey<Biome> biomeKey, BlockPlacementFreezeRecipe.Serializer serializer) {
		return recipe(ingredient, bypassBlock, result, biomeKey, null, serializer);
	}

	public static PlacementFreezeBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, BlockPropertyPair result, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPlacementFreezeRecipe.Serializer serializer) {
		return new BlockFreezeBuilder(ingredient, bypassBlock, result, biomeKey, biomeTag, serializer);
	}

	@Override
	public void save(Consumer<FinishedRecipe> p_176499_) {
		this.save(p_176499_, getDefaultRecipeId(this.getResultBlock().block()));
	}

	@Override
	public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
		finishedRecipeConsumer.accept(new BlockFreezeBuilder.Result(recipeId, this.getBiomeKey(), this.getBiomeTag(), this.getResultBlock(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
	}

	static ResourceLocation getDefaultRecipeId(Block p_176494_) {
		return Registry.BLOCK.getKey(p_176494_);
	}

	public static class Result extends PlacementFreezeBuilder.Result {
		private final BlockStateIngredient ingredient;

		public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPropertyPair result, @Nullable BlockStateIngredient bypassBlock, BlockStateIngredient ingredient, RecipeSerializer<?> serializer) {
			super(id, biomeKey, biomeTag, result, bypassBlock, serializer);
			this.ingredient = ingredient;
		}

		@Override
		public void serializeRecipeData(@Nonnull JsonObject json) {
			super.serializeRecipeData(json);
			json.add("ingredient", this.ingredient.toJson());
		}
	}
}