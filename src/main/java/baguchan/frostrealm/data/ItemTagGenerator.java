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
        tag(ItemTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get().asItem(), FrostBlocks.FROSTBITE_LOG.get().asItem(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get().asItem(), FrostBlocks.STRIPPED_FROSTBITE_LOG.get().asItem());
        tag(ItemTags.PLANKS).add(FrostBlocks.FROSTROOT_PLANKS.get().asItem(), FrostBlocks.FROSTBITE_PLANKS.get().asItem());
        tag(Tags.Items.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.get().asItem());
        tag(ItemTags.STONE_TOOL_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
        tag(Tags.Items.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.get().asItem());
        tag(Tags.Items.EGGS).add(FrostBlocks.SNOWPILE_QUAIL_EGG.get().asItem());
        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get())
                .add(FrostItems.FROST_BOAR_FUR_HELMET.get(), FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR_BOOTS.get());
        tag(FrostTags.Items.AURORA_FUELS).add(FrostItems.STARDUST_CRYSTAL.get());
        tag(FrostTags.Items.YETI_CURRENCY).add(FrostItems.STARDUST_CRYSTAL.get());
        //tag(FrostTags.Items.YETI_BIG_CURRENCY).add(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get().asItem());
        tag(FrostTags.Items.YETI_LOVED).addTags(FrostTags.Items.YETI_CURRENCY).add(FrostItems.FROST_BOAR_MEAT.get(), FrostItems.COOKED_FROST_BOAR_MEAT.get(), FrostItems.SNOWPILE_QUAIL_MEAT.get(), FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get());
        tag(FrostTags.Items.YETI_SCARED).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get());
        tag(ItemTags.TRIMMABLE_ARMOR).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get())
                .add(FrostItems.FROST_BOAR_FUR_HELMET.get(), FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR_BOOTS.get())
                .add(FrostItems.ASTRIUM_HELMET.get(), FrostItems.ASTRIUM_CHESTPLATE.get(), FrostItems.ASTRIUM_LEGGINGS.get(), FrostItems.ASTRIUM_BOOTS.get());
    }
}