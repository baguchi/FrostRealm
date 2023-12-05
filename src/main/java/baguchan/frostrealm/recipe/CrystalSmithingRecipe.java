package baguchan.frostrealm.recipe;

import baguchan.frostrealm.registry.FrostRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
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
    final Ingredient template;
    final Ingredient addition;

    public CrystalSmithingRecipe(Ingredient template, Ingredient addition) {
        this.template = template;
        this.addition = addition;
    }

    public boolean matches(Container p_267224_, Level p_266798_) {
        return this.template.test(p_267224_.getItem(0)) && (p_267224_.getItem(1).getItem() instanceof ArmorItem || p_267224_.getItem(1).getItem() instanceof TieredItem) && this.addition.test(p_267224_.getItem(2));
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        ItemStack itemstack = p_44001_.getItem(1).copy();
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

    public RecipeSerializer<?> getSerializer() {
        return FrostRecipes.RECIPE_CRYSTAL_SMITHING.get();
    }

	public boolean isIncomplete() {
        return Stream.of(this.template, this.addition).anyMatch(Ingredient::isEmpty);
	}

    public static class Serializer implements RecipeSerializer<CrystalSmithingRecipe> {
        private static final Codec<CrystalSmithingRecipe> CODEC = RecordCodecBuilder.create(
                p_301062_ -> p_301062_.group(
                                Ingredient.CODEC.fieldOf("template").forGetter(p_301310_ -> p_301310_.template),
                                Ingredient.CODEC.fieldOf("addition").forGetter(p_301153_ -> p_301153_.addition)
                        )
                        .apply(p_301062_, CrystalSmithingRecipe::new)
        );

        @Override
        public Codec<CrystalSmithingRecipe> codec() {
            return CODEC;
        }

        @Override
        public CrystalSmithingRecipe fromNetwork(FriendlyByteBuf p_44106_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_44106_);
            Ingredient ingredient1 = Ingredient.fromNetwork(p_44106_);
            return new CrystalSmithingRecipe(ingredient, ingredient1);
        }

        public void toNetwork(FriendlyByteBuf p_266901_, CrystalSmithingRecipe p_266893_) {
            p_266893_.template.toNetwork(p_266901_);
            p_266893_.addition.toNetwork(p_266901_);
        }
    }
}
