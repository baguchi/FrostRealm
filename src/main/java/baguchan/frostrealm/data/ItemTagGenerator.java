package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
	public ItemTagGenerator(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, CompletableFuture<TagsProvider.TagLookup<Block>> p_256467_, ExistingFileHelper exFileHelper) {
        super(p_255871_, p_256035_, p_256467_, FrostRealm.MODID, exFileHelper);
    }

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get().asItem());
        tag(ItemTags.PLANKS).add(FrostBlocks.FROSTROOT_PLANKS.get().asItem());
        tag(Tags.Items.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.get().asItem());
        tag(ItemTags.STONE_TOOL_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
        tag(Tags.Items.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.get().asItem());
        tag(Tags.Items.EGGS).add(FrostBlocks.SNOWPILE_QUAIL_EGG.get().asItem());
        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get())
                .add(FrostItems.KOLOSSUS_FUR_HELMET.get(), FrostItems.KOLOSSUS_FUR_CHESTPLATE.get(), FrostItems.KOLOSSUS_FUR_LEGGINGS.get(), FrostItems.KOLOSSUS_FUR_BOOTS.get());
        tag(FrostTags.Items.AURORA_FUELS).add(FrostItems.STARDUST_CRYSTAL.get());
        tag(FrostTags.Items.YETI_CURRENCY).add(FrostItems.STARDUST_CRYSTAL.get());
        //tag(FrostTags.Items.YETI_BIG_CURRENCY).add(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get().asItem());
        tag(FrostTags.Items.YETI_LOVED).addTags(FrostTags.Items.YETI_CURRENCY);
    }
}