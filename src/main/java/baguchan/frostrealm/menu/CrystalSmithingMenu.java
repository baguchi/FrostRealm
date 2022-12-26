package baguchan.frostrealm.menu;

import baguchan.frostrealm.recipe.CrystalSmithingRecipe;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostMenuTypes;
import baguchan.frostrealm.registry.FrostRecipes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CrystalSmithingMenu extends AbstractContainerMenu {

	protected final ResultContainer resultSlots = new ResultContainer();
	protected final Container inputSlots = new SimpleContainer(3) {
		public void setChanged() {
			super.setChanged();
			CrystalSmithingMenu.this.slotsChanged(this);
		}
	};
	protected final ContainerLevelAccess access;
	protected final Player player;

	private final Level level;
	@Nullable
	private CrystalSmithingRecipe selectedRecipe;
	private final List<CrystalSmithingRecipe> recipes;

	public CrystalSmithingMenu(int p_40245_, Inventory p_40246_) {
		this(p_40245_, p_40246_, ContainerLevelAccess.NULL);
	}

	public CrystalSmithingMenu(int p_40248_, Inventory p_40249_, ContainerLevelAccess p_40250_) {
		super(FrostMenuTypes.CRYSTAL_SMITHING.get(), p_40248_);
		this.access = p_40250_;
		this.player = p_40249_.player;
		this.addSlot(new Slot(this.inputSlots, 0, 27, 47));
		this.addSlot(new Slot(this.inputSlots, 1, 76, 47));
		this.addSlot(new Slot(this.inputSlots, 2, 52, 65));
		this.addSlot(new Slot(this.resultSlots, 3, 134, 47) {
			public boolean mayPlace(ItemStack p_39818_) {
				return CrystalSmithingMenu.this.mayPickup(player, !this.hasItem());
			}

			public void onTake(Player p_150604_, ItemStack p_150605_) {
				CrystalSmithingMenu.this.onTake(p_150604_, p_150605_);
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(p_40249_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(p_40249_, k, 8 + k * 18, 142));
		}


		this.level = p_40249_.player.level;
		this.recipes = this.level.getRecipeManager().getAllRecipesFor(FrostRecipes.RECIPETYPE_CRYSTAL_SMITHING);
	}

	public void slotsChanged(Container p_39778_) {
		super.slotsChanged(p_39778_);
		if (p_39778_ == this.inputSlots) {
			this.createResult();
		}

	}

	public void removed(Player p_39790_) {
		super.removed(p_39790_);
		this.access.execute((p_39796_, p_39797_) -> {
			this.clearContainer(p_39790_, this.inputSlots);
		});
	}

	public boolean stillValid(Player p_39780_) {
		return this.access.evaluate((p_39785_, p_39786_) -> {
			return !this.isValidBlock(p_39785_.getBlockState(p_39786_)) ? false : p_39780_.distanceToSqr((double) p_39786_.getX() + 0.5D, (double) p_39786_.getY() + 0.5D, (double) p_39786_.getZ() + 0.5D) <= 64.0D;
		}, true);
	}

	protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_39787_) {
		return this.recipes.stream().anyMatch((p_40261_) -> {
			return p_40261_.isAdditionIngredient(p_39787_);
		});
	}

	public ItemStack quickMoveStack(Player p_39792_, int p_39793_) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(p_39793_);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (p_39793_ == 3) {
				if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (p_39793_ != 0 && p_39793_ != 1 && p_39793_ != 2) {
				if (p_39793_ >= 4 && p_39793_ < 40) {
					int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
					if (!this.moveItemStackTo(itemstack1, i, 3, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(p_39792_, itemstack1);
		}

		return itemstack;
	}

	protected boolean isValidBlock(BlockState p_40266_) {
		return p_40266_.is(FrostBlocks.CRYSTAL_SMITHING_TABLE.get());
	}

	public boolean mayPickup(Player p_40268_, boolean p_40269_) {
		return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
	}

	public void onTake(Player p_150663_, ItemStack p_150664_) {
		p_150664_.onCraftedBy(p_150663_.level, p_150663_, p_150664_.getCount());
		this.resultSlots.awardUsedRecipes(p_150663_);
		this.shrinkStackInSlot(0);
		this.shrinkStackInSlot(1);
		this.shrinkStackInSlot(2);
		this.access.execute((p_40263_, p_40264_) -> {
			p_40263_.levelEvent(1044, p_40264_, 0);
		});
	}

	private void shrinkStackInSlot(int p_40271_) {
		ItemStack itemstack = this.inputSlots.getItem(p_40271_);
		itemstack.shrink(1);
		this.inputSlots.setItem(p_40271_, itemstack);
	}

	public void createResult() {
		if (this.inputSlots.getItem(2).is(FrostItems.FROST_CRYSTAL.get())) {
			List<CrystalSmithingRecipe> list = this.level.getRecipeManager().getRecipesFor(FrostRecipes.RECIPETYPE_CRYSTAL_SMITHING, this.inputSlots, this.level);
			if (list.isEmpty()) {
				this.resultSlots.setItem(0, ItemStack.EMPTY);
			} else {
				CrystalSmithingRecipe upgraderecipe = list.get(0);
				ItemStack itemstack = upgraderecipe.assemble(this.inputSlots);
				if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
					this.selectedRecipe = upgraderecipe;
					this.resultSlots.setRecipeUsed(upgraderecipe);
					this.resultSlots.setItem(0, itemstack);
				}
			}
		}
	}

	public boolean canTakeItemForPickAll(ItemStack p_40257_, Slot p_40258_) {
		return p_40258_.container != this.resultSlots && super.canTakeItemForPickAll(p_40257_, p_40258_);
	}
}