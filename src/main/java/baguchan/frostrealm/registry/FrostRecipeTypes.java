package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.recipe.recipes.BlockPlacementFreezeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostRecipeTypes {
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, FrostRealm.MODID);

	public static RegistryObject<RecipeType<BlockPlacementFreezeRecipe>> BLOCK_PLACEMENT_FREEZE = RECIPE_TYPES.register("block_placement_freeze", () -> RecipeType.simple(new ResourceLocation(FrostRealm.MODID, "block_placement_freeze")));

}
