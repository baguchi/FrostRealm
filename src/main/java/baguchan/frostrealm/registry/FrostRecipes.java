package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.recipe.CrystalSmithingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, FrostRealm.MODID);

    public static final Supplier<RecipeSerializer<?>> RECIPE_CRYSTAL_SMITHING = RECIPE_SERIALIZERS.register("crystal_smithing", CrystalSmithingRecipe.Serializer::new);

	static <T extends Recipe<?>> RecipeType<T> register(final String p_44120_) {
		return new RecipeType<T>() {
			public String toString() {
				return p_44120_;
			}
		};
	}
}