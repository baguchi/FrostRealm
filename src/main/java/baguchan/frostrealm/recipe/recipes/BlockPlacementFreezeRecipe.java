package baguchan.frostrealm.recipe.recipes;

import baguchan.frostrealm.recipe.BlockPropertyPair;
import baguchan.frostrealm.recipe.BlockStateIngredient;
import baguchan.frostrealm.registry.FrostRecipeSerializers;
import baguchan.frostrealm.registry.FrostRecipeTypes;
import baguchan.frostrealm.utils.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPlacementFreezeRecipe extends AbstractPlacementFreezeRecipe<BlockState, BlockStateIngredient> {
	public BlockPlacementFreezeRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPropertyPair result, BlockStateIngredient bypassBlock, BlockStateIngredient ingredient) {
		super(FrostRecipeTypes.BLOCK_PLACEMENT_FREEZE.get(), id, biomeKey, biomeTag, result, bypassBlock, ingredient);
	}

	public boolean freezeBlock(Level level, BlockPos pos, BlockState state) {
		if (!this.matches(level, pos.below(), state)) {
			level.levelEvent(1501, pos, 0);
			level.setBlock(pos, this.getResultState(state), 3);
			return true;
		}
		return false;
	}

	@Nonnull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return FrostRecipeSerializers.BLOCK_PLACEMENT_FREEZE.get();
	}

	public static class Serializer implements RecipeSerializer<BlockPlacementFreezeRecipe> {
		public Serializer() {
		}

		@Nonnull
		@Override
		public BlockPlacementFreezeRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
			Pair<ResourceKey<Biome>, TagKey<Biome>> biomeRecipeData = BlockStateRecipeUtil.biomeRecipeDataFromJson(serializedRecipe);
			ResourceKey<Biome> biomeKey = biomeRecipeData.getLeft();
			TagKey<Biome> biomeTag = biomeRecipeData.getRight();
			if (!serializedRecipe.has("result"))
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			BlockPropertyPair result;
			if (serializedRecipe.get("result").isJsonObject()) {
				JsonObject resultObject = serializedRecipe.getAsJsonObject("result");
				result = BlockStateRecipeUtil.pairFromJson(resultObject);
			} else {
				throw new JsonSyntaxException("Expected result to be object");
			}

			BlockStateIngredient bypassBlock = BlockStateIngredient.EMPTY;
			if (serializedRecipe.has("bypass")) {
				boolean isBypassArray = GsonHelper.isArrayNode(serializedRecipe, "bypass");
				JsonElement bypassElement = isBypassArray ? GsonHelper.getAsJsonArray(serializedRecipe, "bypass") : GsonHelper.getAsJsonObject(serializedRecipe, "bypass");
				bypassBlock = BlockStateIngredient.fromJson(bypassElement);
			}
			if (!serializedRecipe.has("ingredient"))
				throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
			JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
			BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);
			return new BlockPlacementFreezeRecipe(recipeId, biomeKey, biomeTag, result, bypassBlock, ingredient);
		}

		@Nullable
		@Override
		public BlockPlacementFreezeRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
			ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
			TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
			BlockPropertyPair pair = BlockStateRecipeUtil.readPair(buf);
			BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buf);
			BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
			return new BlockPlacementFreezeRecipe(recipeId, biomeKey, biomeTag, pair, bypassBlock, ingredient);
		}

		@Override
		public void toNetwork(@Nonnull FriendlyByteBuf buf, @Nonnull BlockPlacementFreezeRecipe recipe) {
			BlockStateRecipeUtil.writeBiomeKey(buf, recipe.getIgnoreBiomeKey());
			BlockStateRecipeUtil.writeBiomeTag(buf, recipe.getIgnoreBiomeTag());
			if (recipe.getResult() != null) {
				BlockStateRecipeUtil.writePair(buf, recipe.getResult());
			}
			recipe.getBypassBlock().toNetwork(buf);
			recipe.getIngredient().toNetwork(buf);
		}
	}
}