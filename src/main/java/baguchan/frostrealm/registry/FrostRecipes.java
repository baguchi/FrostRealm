package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.recipe.AttachCrystalRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FrostRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, FrostRealm.MODID);
	public static final DeferredHolder<RecipeSerializer<?>, AttachCrystalRecipe.Serializer> RECIPE_ATTACH = RECIPE_SERIALIZERS.register("attach_crystal", AttachCrystalRecipe.Serializer::new);

	static <T extends Recipe<?>> RecipeType<T> register(final String p_44120_) {
		return new RecipeType<T>() {
			public String toString() {
				return p_44120_;
			}
		};
	}
}