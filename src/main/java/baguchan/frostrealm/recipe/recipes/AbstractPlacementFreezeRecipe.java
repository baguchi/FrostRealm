package baguchan.frostrealm.recipe.recipes;

import baguchan.frostrealm.recipe.BlockPropertyPair;
import baguchan.frostrealm.recipe.BlockStateIngredient;
import baguchan.frostrealm.utils.BlockStateRecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public abstract class AbstractPlacementFreezeRecipe<T, S extends Predicate<T>> implements Recipe<Container> {
	protected final RecipeType<?> type;
	protected final ResourceLocation id;
	private final ResourceKey<Biome> ignoreBiomeKey;
	private final TagKey<Biome> ignoreBiomeTag;

	protected final BlockPropertyPair result;
	protected final BlockStateIngredient bypassBlock;
	protected final S ingredient;

	public AbstractPlacementFreezeRecipe(RecipeType<?> type, ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPropertyPair result, BlockStateIngredient bypassBlock, S ingredient) {
		this.type = type;
		this.id = id;
		this.ignoreBiomeKey = biomeKey;
		this.ignoreBiomeTag = biomeTag;
		this.result = result;
		this.bypassBlock = bypassBlock;
		this.ingredient = ingredient;
	}

	public boolean matches(Level level, BlockPos pos, T object) {
		if (this.bypassBlock.isEmpty() || !this.bypassBlock.test(level.getBlockState(pos))) {
			if (this.ignoreBiomeKey != null) {
				return !level.getBiome(pos).is(this.ignoreBiomeKey) && this.getIngredient().test(object);
			} else if (this.ignoreBiomeTag != null) {
				return !level.getBiome(pos).is(this.ignoreBiomeTag) && this.getIngredient().test(object);
			} else {
				return this.getIngredient().test(object);
			}
		}
		return false;
	}

	@Nullable
	public ResourceKey<Biome> getIgnoreBiomeKey() {
		return this.ignoreBiomeKey;
	}

	@Nullable
	public TagKey<Biome> getIgnoreBiomeTag() {
		return this.ignoreBiomeTag;
	}

	public BlockStateIngredient getBypassBlock() {
		return this.bypassBlock;
	}

	public S getIngredient() {
		return this.ingredient;
	}

	public BlockState getResultState(BlockState originalState) {
		BlockState resultState = this.getResult().block().withPropertiesOf(originalState);
		for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : this.getResult().properties().entrySet()) {
			resultState = BlockStateRecipeUtil.setHelper(propertyEntry, resultState);
		}
		return resultState;
	}

	@Nonnull
	public BlockPropertyPair getResult() {
		return this.result;
	}

	@Nonnull
	@Override
	public RecipeType<?> getType() {
		return this.type;
	}

	@Nonnull
	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public boolean matches(@Nonnull Container container, @Nonnull Level level) {
		return false;
	}

	@Nonnull
	@Override
	public ItemStack assemble(@Nonnull Container container) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return false;
	}

	@Nonnull
	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}
}