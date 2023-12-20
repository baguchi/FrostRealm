package baguchan.frostrealm.data.builder;

import baguchan.frostrealm.recipe.CrystalSmithingRecipe;
import baguchan.frostrealm.registry.FrostRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CrystalSmithingRecipeBuilder {
	private final Ingredient template;
    private final Ingredient addition;
	private final RecipeCategory category;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<?> type;

    public CrystalSmithingRecipeBuilder(RecipeSerializer<?> p_248989_, Ingredient template, Ingredient addition, RecipeCategory p_248993_) {
        this.category = p_248993_;
        this.type = p_248989_;
        this.template = template;
        this.addition = addition;
    }

    public static CrystalSmithingRecipeBuilder smithing(Ingredient template, Ingredient addition, RecipeCategory p_248633_) {
        return new CrystalSmithingRecipeBuilder(FrostRecipes.RECIPE_CRYSTAL_SMITHING.get(), template, addition, p_248633_);
    }

    public CrystalSmithingRecipeBuilder unlocks(String p_126390_, Criterion<?> p_126391_) {
        this.advancement.addCriterion(p_126390_, p_126391_);
        return this;
    }

    public void save(RecipeOutput p_126393_, String p_126394_) {
        this.save(p_126393_, new ResourceLocation(p_126394_));
    }

    public void save(RecipeOutput p_126396_, ResourceLocation p_126397_) {
        Advancement.Builder advancement$builder = p_126396_.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126397_))
                .rewards(AdvancementRewards.Builder.recipe(p_126397_))
                .requirements(AdvancementRequirements.Strategy.OR);
        p_126396_.accept(
                p_126397_,
                new CrystalSmithingRecipe(
                        this.template,
                        this.addition
                ),
                advancement$builder.build(p_126397_.withPrefix("recipes/" + this.category.getFolderName() + "/"))
        );
	}

}