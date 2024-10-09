package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
	public ItemTagGenerator(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, CompletableFuture<TagsProvider.TagLookup<Block>> p_256467_, ExistingFileHelper exFileHelper) {
        super(p_255871_, p_256035_, p_256467_, FrostRealm.MODID, exFileHelper);
    }

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get().asItem(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get().asItem()).add(FrostBlocks.FROSTBITE_LOG.get().asItem(), FrostBlocks.STRIPPED_FROSTBITE_LOG.get().asItem());
        tag(ItemTags.PLANKS).add(FrostBlocks.FROSTROOT_PLANKS.get().asItem()).add(FrostBlocks.FROSTBITE_PLANKS.get().asItem());
        tag(ItemTags.WOODEN_FENCES).add(FrostBlocks.FROSTROOT_FENCE.get().asItem()).add(FrostBlocks.FROSTBITE_FENCE.get().asItem());
        tag(ItemTags.FENCE_GATES).add(FrostBlocks.FROSTROOT_FENCE_GATE.get().asItem()).add(FrostBlocks.FROSTBITE_FENCE_GATE.get().asItem());
        tag(ItemTags.WOODEN_DOORS).add(FrostBlocks.FROSTROOT_DOOR.get().asItem());
        tag(ItemTags.WOODEN_TRAPDOORS).add(FrostBlocks.FROSTROOT_TRAPDOOR.get().asItem());
        tag(ItemTags.WOODEN_SLABS).add(FrostBlocks.FROSTROOT_PLANKS_SLAB.get().asItem()).add(FrostBlocks.FROSTBITE_PLANKS_SLAB.get().asItem());
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(FrostBlocks.FROSTROOT_PRESSURE_PLATE.get().asItem()).add(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get().asItem());
        tag(ItemTags.BUTTONS).add(FrostBlocks.FROSTROOT_PRESSURE_PLATE.get().asItem()).add(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get().asItem());


        tag(ItemTags.STONE_TOOL_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem(), FrostBlocks.PERMA_SLATE.get().asItem());
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(FrostBlocks.FRIGID_STONE.get().asItem());
        tag(Tags.Items.EGGS).add(FrostBlocks.SNOWPILE_QUAIL_EGG.get().asItem());
        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get())
                .add(FrostItems.FROST_BOAR_FUR_HELMET.get(), FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR_BOOTS.get());
        tag(FrostTags.Items.AURORA_FUELS).add(FrostItems.WARPED_CRYSTAL.get());
        tag(FrostTags.Items.YETI_CURRENCY).add(FrostItems.STARDUST_CRYSTAL.get());
        //tag(FrostTags.Items.YETI_BIG_CURRENCY).add(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get().asItem());
        tag(FrostTags.Items.YETI_LOVED).addTags(FrostTags.Items.YETI_CURRENCY).add(FrostItems.FROST_BOAR_MEAT.get(), FrostItems.COOKED_FROST_BOAR_MEAT.get(), FrostItems.SNOWPILE_QUAIL_MEAT.get(), FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get());
        tag(FrostTags.Items.YETI_SCARED).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get());
        tag(ItemTags.TRIMMABLE_ARMOR).add(FrostItems.YETI_FUR_HELMET.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_BOOTS.get())
                .add(FrostItems.FROST_BOAR_FUR_HELMET.get(), FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR_BOOTS.get())
                .add(FrostItems.ASTRIUM_HELMET.get(), FrostItems.ASTRIUM_CHESTPLATE.get(), FrostItems.ASTRIUM_LEGGINGS.get(), FrostItems.ASTRIUM_BOOTS.get());
        tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(FrostItems.RYE_SEEDS.get());
        tag(Tags.Items.SEEDS).add(FrostItems.RYE_SEEDS.get());

        this.tag(ItemTags.FOOT_ARMOR).add(FrostItems.FROST_BOAR_FUR_BOOTS.get()).add(FrostItems.YETI_FUR_BOOTS.get()).add(FrostItems.ASTRIUM_BOOTS.get());

        this.tag(ItemTags.LEG_ARMOR).add(FrostItems.FROST_BOAR_FUR_LEGGINGS.get()).add(FrostItems.YETI_FUR_LEGGINGS.get()).add(FrostItems.ASTRIUM_LEGGINGS.get());
        this.tag(ItemTags.CHEST_ARMOR).add(FrostItems.FROST_BOAR_FUR_CHESTPLATE.get()).add(FrostItems.YETI_FUR_CHESTPLATE.get()).add(FrostItems.ASTRIUM_CHESTPLATE.get())
                .add(FrostItems.WOLFFLUE_ASTRIUM_ARMOR.get());
        this.tag(ItemTags.HEAD_ARMOR).add(FrostItems.FROST_BOAR_FUR_HELMET.get()).add(FrostItems.YETI_FUR_HELMET.get()).add(FrostItems.ASTRIUM_HELMET.get());

        this.tag(ItemTags.MEAT).add(FrostItems.FROST_BOAR_MEAT.get()).add(FrostItems.COOKED_FROST_BOAR_MEAT.get()).add(FrostItems.SNOWPILE_QUAIL_MEAT.get()).add(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get());

        this.tag(FrostTags.Items.CRYSTAL_FOX_FOODS).add(FrostItems.BEARBERRY.get());
        this.tag(FrostTags.Items.FROST_BOAR_FOODS).add(FrostItems.RYE.get());
        this.tag(FrostTags.Items.SNOWPILE_FOODS).add(FrostItems.SUGARBEET_SEEDS.get()).add(FrostItems.RYE_SEEDS.get());
        this.tag(FrostTags.Items.WOLFFLUE_FOODS).addTag(ItemTags.WOLF_FOOD);
        this.tag(FrostTags.Items.FERRET_FOODS).add(Items.CHICKEN).add(Items.COOKED_CHICKEN).add(FrostItems.SNOWPILE_QUAIL_MEAT.get()).add(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get());
        this.tag(ItemTags.SWORDS).add(FrostItems.ASTRIUM_SWORD.get()).add(FrostItems.GLACINIUM_SWORD.get()).add(FrostItems.SILVER_MOON.get());
        this.tag(ItemTags.PICKAXES).add(FrostItems.ASTRIUM_PICKAXE.get()).add(FrostItems.GLACINIUM_PICKAXE.get());
        this.tag(ItemTags.AXES).add(FrostItems.ASTRIUM_AXE.get()).add(FrostItems.GLACINIUM_AXE.get());
        this.tag(ItemTags.SHOVELS).add(FrostItems.ASTRIUM_SHOVEL.get()).add(FrostItems.ASTRIUM_SHOVEL.get());
        this.tag(ItemTags.HOES).add(FrostItems.ASTRIUM_HOE.get()).add(FrostItems.GLACINIUM_HOE.get());
        this.tag(FrostTags.Items.SICKLE).add(FrostItems.ASTRIUM_SICKLE.get()).add(FrostItems.GLACINIUM_SICKLE.get());
        this.tag(FrostTags.Items.SMITHABLE_WEAPON).addTag(ItemTags.SWORDS).addTag(ItemTags.AXES).addTag(FrostTags.Items.SICKLE);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(FrostItems.FROST_SPEAR.get()).addTag(FrostTags.Items.SICKLE);
        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(FrostItems.FROST_SPEAR.get()).addTag(FrostTags.Items.SICKLE);
        this.tag(ItemTags.MEAT).add(FrostItems.COOKED_FROST_BOAR_MEAT.get()).add(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get())
                .add(FrostItems.FROST_BOAR_MEAT.get()).add(FrostItems.SNOWPILE_QUAIL_MEAT.get());
    }
}