package baguchan.frostrealm.data.builder;

import baguchan.frostrealm.registry.FrostRecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

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

    public CrystalSmithingRecipeBuilder unlocks(String p_126390_, CriterionTriggerInstance p_126391_) {
        this.advancement.addCriterion(p_126390_, p_126391_);
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_126393_, String p_126394_) {
        this.save(p_126393_, new ResourceLocation(p_126394_));
    }

	public void save(Consumer<FinishedRecipe> p_126396_, ResourceLocation p_126397_) {
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126397_)).rewards(AdvancementRewards.Builder.recipe(p_126397_)).requirements(RequirementsStrategy.OR);
        p_126396_.accept(new CrystalSmithingRecipeBuilder.Result(p_126397_, this.type, this.template, this.addition, this.advancement, p_126397_.withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

	public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient template;
        private final Ingredient addition;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

        public Result(ResourceLocation p_126408_, RecipeSerializer<?> p_126409_, Ingredient template, Ingredient addition, Advancement.Builder p_126413_, ResourceLocation p_126414_) {
            this.id = p_126408_;
            this.type = p_126409_;
            this.template = template;
            this.addition = addition;
            this.advancement = p_126413_;
            this.advancementId = p_126414_;
        }

		public void serializeRecipeData(JsonObject p_126416_) {
            p_126416_.add("template", this.template.toJson());
            p_126416_.add("addition", this.addition.toJson());
		}

		public ResourceLocation getId() {
			return this.id;
		}

		public RecipeSerializer<?> getType() {
			return this.type;
		}

		@Nullable
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}