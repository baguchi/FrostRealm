package baguchan.frostrealm.registry;

import net.minecraft.world.food.FoodProperties;

public class FrostFoods {
	public static final FoodProperties FROZEN_FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
	public static final FoodProperties MELTED_FRUIT = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.45F).build();
}
