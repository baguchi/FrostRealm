package baguchan.frostrealm.recipe;

import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.registry.FrostDataCompnents;
import baguchan.frostrealm.registry.FrostRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class AttachCrystalRecipe implements SmithingRecipe {
    final Ingredient base;
    final Ingredient template;
    final Ingredient addition;
    @Nullable
    private PlacementInfo placementInfo;

    public AttachCrystalRecipe
            (Ingredient p_266787_, Ingredient template, Ingredient p_267292_) {
        this.base = p_266787_;
        this.template = template;
        this.addition = p_267292_;
    }

    public boolean matches(SmithingRecipeInput p_346359_, Level p_266781_) {
        return this.base.test(p_346359_.base()) && this.template.test(p_346359_.template()) && this.addition.test(p_346359_.addition());
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.of(this.template);
    }

    @Override
    public Optional<Ingredient> baseIngredient() {
        return Optional.of(this.base);
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return Optional.of(this.addition);
    }

    public ItemStack assemble(SmithingRecipeInput p_345750_, HolderLookup.Provider p_335536_) {
        ItemStack itemstack = p_345750_.base();
        if (this.base.test(itemstack)) {
            Optional<Holder.Reference<AttachableCrystal>> optional1 = AttachableCrystals.getFromIngredient(p_335536_, p_345750_.addition());
            if (this.addition.test(p_345750_.addition()) && optional1.isPresent()) {
                Holder<AttachableCrystal> attachCrystal = itemstack.get(FrostDataCompnents.ATTACH_CRYSTAL.get());

                ItemStack itemstack1 = itemstack.copyWithCount(1);
                itemstack1.set(FrostDataCompnents.ATTACH_CRYSTAL.get(), optional1.get());
                return itemstack1;
            }
        }

        return ItemStack.EMPTY;
    }


    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return FrostRecipes.RECIPE_ATTACH.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(List.of(this.base, this.addition, this.template));
        }

        return this.placementInfo;
    }

    public static class Serializer implements RecipeSerializer<AttachCrystalRecipe> {
        private static final MapCodec<AttachCrystalRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340782_ -> p_340782_.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(p_300938_ -> p_300938_.base),
                                Ingredient.CODEC.fieldOf("template").forGetter(p_300938_ -> p_300938_.template),
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
            Ingredient ingredient3 = Ingredient.CONTENTS_STREAM_CODEC.decode(p_320375_);
            return new AttachCrystalRecipe(ingredient1, ingredient2, ingredient3);
        }

        private static void toNetwork(RegistryFriendlyByteBuf p_320743_, AttachCrystalRecipe p_319840_) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(p_320743_, p_319840_.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(p_320743_, p_319840_.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(p_320743_, p_319840_.addition);
        }
    }
}
