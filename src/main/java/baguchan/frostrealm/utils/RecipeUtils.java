package baguchan.frostrealm.utils;

import baguchan.frostrealm.recipe.recipes.BlockPlacementFreezeRecipe;
import baguchan.frostrealm.registry.FrostRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RecipeUtils {

	public static boolean isBlockPlacementFreeze(Level level, BlockPos pos, BlockState state) {
		if (!level.isClientSide()) {
			for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(FrostRecipeTypes.BLOCK_PLACEMENT_FREEZE.get())) {
				if (recipe instanceof BlockPlacementFreezeRecipe freezeRecipe) {
					if (freezeRecipe.freezeBlock(level, pos, state)) {
						BlockState state2 = freezeRecipe.getResultState(state);

						//check is using block same as result
						if (state.getBlock() != state2.getBlock()) {
							level.setBlock(pos, state2, 3);
							return true;
						}


					}
				}
			}
		}
		return false;
	}
}
