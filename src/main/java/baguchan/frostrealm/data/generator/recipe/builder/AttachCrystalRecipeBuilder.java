package baguchan.frostrealm.data.generator.recipe.builder;

import baguchan.frostrealm.recipe.AttachCrystalRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedHashMap;
import java.util.Map;

public class AttachCrystalRecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public AttachCrystalRecipeBuilder(RecipeCategory p_267007_, Ingredient p_267018_, Ingredient p_267264_) {
        this.category = p_267007_;
        this.base = p_267018_;
        this.addition = p_267264_;
    }

    public static AttachCrystalRecipeBuilder smithingTrim(Ingredient base, Ingredient addition, RecipeCategory p_267269_) {
        return new AttachCrystalRecipeBuilder(p_267269_, base, addition);
    }

    public AttachCrystalRecipeBuilder unlocks(String p_266882_, Criterion<?> p_301261_) {
        this.criteria.put(p_266882_, p_301261_);
        return this;
    }

    public void save(RecipeOutput p_301110_, ResourceLocation p_266718_) {
        this.ensureValid(p_266718_);
        Advancement.Builder advancement$builder = p_301110_.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_266718_))
                .rewards(AdvancementRewards.Builder.recipe(p_266718_))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        AttachCrystalRecipe smithingtrimrecipe = new AttachCrystalRecipe(this.base, this.addition);
        p_301110_.accept(p_266718_, smithingtrimrecipe, advancement$builder.build(p_266718_.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation p_267040_) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_267040_);
        }
    }
}
