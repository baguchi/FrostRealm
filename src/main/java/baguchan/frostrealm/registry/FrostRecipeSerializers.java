package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.recipe.recipes.BlockPlacementFreezeRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FrostRealm.MODID);

	public static final RegistryObject<BlockPlacementFreezeRecipe.Serializer> BLOCK_PLACEMENT_FREEZE = RECIPE_SERIALIZERS.register("block_placement_freeze", BlockPlacementFreezeRecipe.Serializer::new);

}
