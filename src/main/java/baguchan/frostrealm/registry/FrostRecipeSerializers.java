package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.recipe.BlockStateIngredient;
import baguchan.frostrealm.recipe.recipes.BlockPlacementFreezeRecipe;
import baguchan.frostrealm.recipe.recipes.PlacementFreezeRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FrostRealm.MODID);

	public static final RegistryObject<PlacementFreezeRecipeSerializer<BlockState, BlockStateIngredient, BlockPlacementFreezeRecipe>> BLOCK_PLACEMENT_FREEZE = RECIPE_SERIALIZERS.register("block_placement_freeze", BlockPlacementFreezeRecipe.Serializer::new);

}
