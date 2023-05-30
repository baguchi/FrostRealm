package baguchan.frostrealm.recipe;

import baguchan.frostrealm.registry.FrostRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;

import java.util.stream.Stream;

public class CrystalSmithingRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    final Ingredient template;
    final Ingredient addition;

    public CrystalSmithingRecipe(ResourceLocation p_44523_, Ingredient template, Ingredient addition) {
        this.id = p_44523_;
        this.template = template;
        this.addition = addition;
    }

    public boolean matches(Container p_267224_, Level p_266798_) {
        return this.template.test(p_267224_.getItem(0)) && p_267224_.getItem(1).getItem() instanceof ArmorItem || p_267224_.getItem(1).getItem() instanceof TieredItem && this.addition.test(p_267224_.getItem(2));
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        ItemStack itemstack = p_44001_.getItem(0).copy();
        CompoundTag compoundtag = p_44001_.getItem(1).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        CompoundTag compoundtag2 = p_44001_.getItem(2).getTag();
        if (compoundtag2 != null) {
            itemstack.setTag(compoundtag2.copy());
        }

        return itemstack;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        ItemStack itemstack = new ItemStack(Items.IRON_CHESTPLATE);

        return itemstack;
    }

    public boolean isTemplateIngredient(ItemStack p_266762_) {
        return this.template.test(p_266762_);
    }

    public boolean isBaseIngredient(ItemStack p_266795_) {
        return p_266795_.getItem() instanceof ArmorItem || p_266795_.getItem() instanceof TieredItem;
    }

    public boolean isAdditionIngredient(ItemStack p_266922_) {
        return this.addition.test(p_266922_);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeSerializer<?> getSerializer() {
        return FrostRecipes.RECIPE_CRYSTAL_SMITHING.get();
    }

	public boolean isIncomplete() {
        return Stream.of(this.template, this.addition).anyMatch(Ingredient::isEmpty);
	}

    public static class Serializer implements RecipeSerializer<CrystalSmithingRecipe> {
        public CrystalSmithingRecipe fromJson(ResourceLocation p_267037_, JsonObject p_267004_) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_267004_, "template"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_267004_, "addition"));

            return new CrystalSmithingRecipe(p_267037_, ingredient, ingredient2);
        }

        public CrystalSmithingRecipe fromNetwork(ResourceLocation p_267169_, FriendlyByteBuf p_267251_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_267251_);
            Ingredient ingredient1 = Ingredient.fromNetwork(p_267251_);
            return new CrystalSmithingRecipe(p_267169_, ingredient, ingredient1);
        }

        public void toNetwork(FriendlyByteBuf p_266901_, CrystalSmithingRecipe p_266893_) {
            p_266893_.template.toNetwork(p_266901_);
            p_266893_.addition.toNetwork(p_266901_);
        }
    }
}
