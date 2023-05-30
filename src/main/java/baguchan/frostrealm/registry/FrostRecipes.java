package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.recipe.CrystalSmithingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FrostRealm.MODID);

	public static final RegistryObject<RecipeSerializer<?>> RECIPE_CRYSTAL_SMITHING = RECIPE_SERIALIZERS.register("crystal_smithing", CrystalSmithingRecipe.Serializer::new);

	static <T extends Recipe<?>> RecipeType<T> register(final String p_44120_) {
		return new RecipeType<T>() {
			public String toString() {
				return p_44120_;
			}
		};
	}
}