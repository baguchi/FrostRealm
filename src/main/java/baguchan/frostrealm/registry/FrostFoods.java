package baguchan.frostrealm.registry;

import net.minecraft.world.food.FoodProperties;

public class FrostFoods {
    public static final FoodProperties FROZEN_FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties MELTED_FRUIT = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.45F).build();
    public static final FoodProperties SUGARBEET = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.3F).build();
    public static final FoodProperties RYE_BREAD = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F).build();


    public static final FoodProperties COOKED_BEARBERRY = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties COOKED_SNOWPILE_QUAIL_EGG = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.8F).build();

    public static final FoodProperties FROST_BOAR_MEAT = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties COOKED_FROST_BOAR_MEAT = (new FoodProperties.Builder()).nutrition(9).saturationModifier(0.8F).build();

    public static final FoodProperties SNOWPILE_QUAIL_MEAT = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties COOKED_SNOWPILE_QUAIL_MEAT = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F).build();
}
