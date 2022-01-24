package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagGenerator extends ItemTagsProvider {
	public ItemTagGenerator(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper exFileHelper) {
		super(generator, blockTagsProvider, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags() {
		tag(ItemTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.asItem());
		tag(ItemTags.PLANKS).add(FrostBlocks.FROSTROOT_PLANKS.asItem());
		tag(Tags.Items.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.asItem());
		tag(ItemTags.STONE_TOOL_MATERIALS).add(FrostBlocks.FRIGID_STONE.asItem());
		tag(ItemTags.STONE_CRAFTING_MATERIALS).add(FrostBlocks.FRIGID_STONE.asItem());
		tag(Tags.Items.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.asItem());
		tag(Tags.Items.EGGS).add(FrostBlocks.SNOWPILE_QUAIL_EGG.asItem());
		tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(FrostItems.YETI_FUR_HELMET, FrostItems.YETI_FUR_CHESTPLATE, FrostItems.YETI_FUR_LEGGINGS, FrostItems.YETI_FUR_BOOTS);
	}
}