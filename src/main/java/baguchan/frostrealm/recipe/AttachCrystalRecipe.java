package baguchan.frostrealm.recipe;

import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.registry.FrostDataCompnents;
import baguchan.frostrealm.registry.FrostRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.stream.Stream;

public class AttachCrystalRecipe implements SmithingRecipe {
    final Ingredient base;
    final Ingredient addition;

    public AttachCrystalRecipe(Ingredient p_266787_, Ingredient p_267292_) {
        this.base = p_266787_;
        this.addition = p_267292_;
    }

    public boolean matches(SmithingRecipeInput p_346359_, Level p_266781_) {
        return this.base.test(p_346359_.base()) && this.addition.test(p_346359_.addition());
    }

    public ItemStack assemble(SmithingRecipeInput p_345750_, HolderLookup.Provider p_335536_) {
        ItemStack itemstack = p_345750_.base();
        if (this.base.test(itemstack)) {
            Optional<Holder.Reference<AttachableCrystal>> optional1 = AttachableCrystals.getFromIngredient(p_335536_, p_345750_.template());
            if (this.addition.test(p_345750_.addition()) && optional1.isPresent()) {
                Holder<AttachableCrystal> attachCrystal = itemstack.get(FrostDataCompnents.ATTACH_CRYSTAL.get());
                if (attachCrystal != null) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack1 = itemstack.copyWithCount(1);
                itemstack1.set(FrostDataCompnents.ATTACH_CRYSTAL.get(), optional1.get());
                return itemstack1;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_335445_) {
        ItemStack itemstack = new ItemStack(Items.IRON_SWORD);
        Optional<Holder.Reference<TrimPattern>> optional = p_335445_.lookupOrThrow(Registries.TRIM_PATTERN).listElements().findFirst();
        Optional<Holder.Reference<TrimMaterial>> optional1 = p_335445_.lookupOrThrow(Registries.TRIM_MATERIAL).get(TrimMaterials.REDSTONE);
        if (optional.isPresent() && optional1.isPresent()) {
            itemstack.set(DataComponents.TRIM, new ArmorTrim(optional1.get(), optional.get()));
        }

        return itemstack;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack p_267113_) {
        return true;
    }

    @Override
    public boolean isBaseIngredient(ItemStack p_267276_) {
        return this.base.test(p_267276_);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack p_267260_) {
        return this.addition.test(p_267260_);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FrostRecipes.RECIPE_ATTACH.get();
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.base, this.addition).anyMatch(Ingredient::hasNoItems);
    }

    public static class Serializer implements RecipeSerializer<AttachCrystalRecipe> {
        private static final MapCodec<AttachCrystalRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340782_ -> p_340782_.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(p_300938_ -> p_300938_.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(p_301153_ -> p_301153_.addition)
                        )
                        .apply(p_340782_, AttachCrystalRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, AttachCrystalRecipe> STREAM_CODEC = StreamCodec.of(
                AttachCrystalRecipe.Serializer::toNetwork, AttachCrystalRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<AttachCrystalRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AttachCrystalRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AttachCrystalRecipe fromNetwork(RegistryFriendlyByteBuf p_320375_) {
            Ingredient ingredient1 = Ingredient.CONTENTS_STREAM_CODEC.decode(p_320375_);
            Ingredient ingredient2 = Ingredient.CONTENTS_STREAM_CODEC.decode(p_320375_);
            return new AttachCrystalRecipe(ingredient1, ingredient2);
        }

        private static void toNetwork(RegistryFriendlyByteBuf p_320743_, AttachCrystalRecipe p_319840_) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(p_320743_, p_319840_.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(p_320743_, p_319840_.addition);
        }
    }
}
