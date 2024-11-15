package baguchan.frostrealm.data.generator.recipe.builder;

import baguchan.frostrealm.recipe.AttachCrystalRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.Map;

public class AttachCrystalRecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient template;
    private final Ingredient addition;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public AttachCrystalRecipeBuilder(RecipeCategory p_267007_, Ingredient p_267018_, Ingredient template, Ingredient p_267264_) {
        this.category = p_267007_;
        this.base = p_267018_;
        this.template = template;
        this.addition = p_267264_;
    }

    public static AttachCrystalRecipeBuilder smithingTrim(Ingredient p_266812_, Ingredient template, Ingredient p_266843_, RecipeCategory p_267269_) {
        return new AttachCrystalRecipeBuilder(p_267269_, p_266812_, template, p_266843_);
    }

    public AttachCrystalRecipeBuilder unlocks(String p_266882_, Criterion<?> p_301261_) {
        this.criteria.put(p_266882_, p_301261_);
        return this;
    }

    public void save(RecipeOutput p_301110_, ResourceKey<Recipe<?>> p_379691_) {
        this.ensureValid(p_379691_);
        Advancement.Builder advancement$builder = p_301110_.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_379691_))
                .rewards(AdvancementRewards.Builder.recipe(p_379691_))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        AttachCrystalRecipe smithingtrimrecipe = new AttachCrystalRecipe(this.base, this.template, this.addition);
        p_301110_.accept(
                p_379691_, smithingtrimrecipe, advancement$builder.build(p_379691_.location().withPrefix("recipes/" + this.category.getFolderName() + "/"))
        );
    }

    private void ensureValid(ResourceKey<Recipe<?>> p_379384_) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_379384_.location());
        }
    }
}
