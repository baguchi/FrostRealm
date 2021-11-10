package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagGenerator extends ItemTagsProvider {
	public ItemTagGenerator(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper exFileHelper) {
		super(generator, blockTagsProvider, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags() {
		tag(ItemTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get().asItem());
		tag(ItemTags.PLANKS).add(FrostBlocks.FROSTROOT_PLANKS.get().asItem());
		tag(ItemTags.STONE_TOOL_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
		tag(ItemTags.STONE_CRAFTING_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
	}
}