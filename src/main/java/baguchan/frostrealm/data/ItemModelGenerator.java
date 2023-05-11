package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static baguchan.frostrealm.FrostRealm.prefix;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrostRealm.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.singleTex(FrostItems.FROST_CRYSTAL);
		this.singleTex(FrostItems.CRYONITE);
		this.singleTex(FrostItems.WARPED_CRYSTAL);
		this.singleTex(FrostItems.GLIMMERROCK);
		this.singleTex(FrostItems.ASTRIUM_RAW);
		this.singleTex(FrostItems.ASTRIUM_INGOT);
		this.singleTex(FrostItems.STARDUST_CRYSTAL);
		this.singleTex(FrostItems.FROZEN_FRUIT);
		this.singleTex(FrostItems.MELTED_FRUIT);
		this.singleTex(FrostItems.SUGARBEET);
		this.singleTex(FrostItems.SUGARBEET_SEEDS);
		this.singleTex(FrostItems.BEARBERRY);
        this.singleTex(FrostItems.COOKED_BEARBERRY);
        this.singleTex(FrostItems.COOKED_SNOWPILE_QUAIL_EGG);
        this.singleTex(FrostItems.SNOWPILE_QUAIL_MEAT);
        this.singleTex(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT);

        this.singleTex(FrostItems.FROST_CATALYST);
        this.singleTex(FrostItems.STRAY_NECKLACE_PART);

        this.singleTex(FrostItems.YETI_FUR);
		this.singleTex(FrostItems.FROST_BOAR_FUR);
		this.singleTexTool(FrostItems.OAK_SLEDGE);
        this.singleTexTool(FrostItems.OAK_CHEST_SLEDGE);
        this.singleTexTool(FrostItems.FUSION_CRYSTAL_DAGGER);
        this.singleTexTool(FrostItems.ASTRIUM_SWORD);
		this.singleTexTool(FrostItems.ASTRIUM_AXE);
		this.singleTexTool(FrostItems.ASTRIUM_PICKAXE);
		this.singleTexTool(FrostItems.ASTRIUM_SHOVEL);
		this.singleTexTool(FrostItems.ASTRIUM_HOE);

		this.singleTex(FrostItems.YETI_FUR_HELMET);
		this.singleTex(FrostItems.YETI_FUR_CHESTPLATE);
		this.singleTex(FrostItems.YETI_FUR_LEGGINGS);
		this.singleTex(FrostItems.YETI_FUR_BOOTS);

		this.singleTex(FrostItems.FROST_BOAR_FUR_HELMET);
		this.singleTex(FrostItems.FROST_BOAR_FUR_CHESTPLATE);
		this.singleTex(FrostItems.FROST_BOAR_FUR_LEGGINGS);
		this.singleTex(FrostItems.FROST_BOAR_FUR_BOOTS);

		this.singleTex(FrostItems.ASTRIUM_HELMET);
		this.singleTex(FrostItems.ASTRIUM_CHESTPLATE);
		this.singleTex(FrostItems.ASTRIUM_LEGGINGS);
		this.singleTex(FrostItems.ASTRIUM_BOOTS);

		this.egg(FrostItems.MARMOT_SPAWNEGG);
		this.egg(FrostItems.SNOWPILE_QUAIL_SPAWNEGG);
		this.egg(FrostItems.FROST_WOLF_SPAWNEGG);
		this.egg(FrostItems.YETI_SPAWNEGG);
		this.egg(FrostItems.FROST_WRAITH_SPAWNEGG);
		this.egg(FrostItems.CLUST_WRAITH_SPAWNEGG);
		this.egg(FrostItems.GOKKUDILLO_SPAWNEGG);
		this.egg(FrostItems.FROST_BEASTER_SPAWNEGG);
		this.egg(FrostItems.CRYSTAL_FOX_SPAWNEGG);
		this.egg(FrostItems.SNOW_MOLE_SPAWNEGG);
        this.egg(FrostItems.ASTRA_BALL_SPAWNEGG);
        this.egg(FrostItems.FROST_BOAR_SPAWNEGG);
        this.egg(FrostItems.SHADE_INSECT_SPAWNEGG);

		this.toBlock(FrostBlocks.FROZEN_DIRT);
		this.toBlock(FrostBlocks.FROZEN_GRASS_BLOCK);
		this.toBlock(FrostBlocks.ETERNITY_GRASS_BLOCK);
		this.toBlock(FrostBlocks.FROZEN_FARMLAND);

		this.toBlock(FrostBlocks.FRIGID_STONE);
		this.toBlock(FrostBlocks.FRIGID_STONE_SLAB);
		this.toBlock(FrostBlocks.FRIGID_STONE_STAIRS);
		this.toBlock(FrostBlocks.FRIGID_STONE_BRICK);
		this.toBlock(FrostBlocks.FRIGID_STONE_SMOOTH);
        this.toBlock(FrostBlocks.CHISELED_FRIGID_STONE_BRICK);
        this.toBlock(FrostBlocks.FRIGID_STONE_BRICK_SLAB);
		this.toBlock(FrostBlocks.FRIGID_STONE_BRICK_STAIRS);

		this.toBlock(FrostBlocks.FRIGID_STONE_MOSSY);
		this.toBlock(FrostBlocks.FRIGID_STONE_MOSSY_SLAB);
		this.toBlock(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS);

		this.toBlock(FrostBlocks.FRIGID_STONE_BRICK_MOSSY);
		this.toBlock(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB);
		this.toBlock(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS);

		this.toBlock(FrostBlocks.FROSTROOT_LOG);
		this.itemBlockFlat(FrostBlocks.FROSTROOT_SAPLING);
		this.toBlock(FrostBlocks.FROSTROOT_LEAVES);
		this.toBlock(FrostBlocks.FROSTROOT_PLANKS);
		this.toBlock(FrostBlocks.FROSTROOT_PLANKS_SLAB);
		this.toBlock(FrostBlocks.FROSTROOT_PLANKS_STAIRS);
		this.woodenFence(FrostBlocks.FROSTROOT_FENCE, FrostBlocks.FROSTROOT_PLANKS);
		this.toBlock(FrostBlocks.FROSTROOT_FENCE_GATE);
		this.toBlock(FrostBlocks.FROSTROOT_CRAFTING_TABLE);

		this.toBlock(FrostBlocks.FROSTBITE_LOG);
		this.itemBlockFlat(FrostBlocks.FROSTBITE_SAPLING);
		this.toBlock(FrostBlocks.FROSTBITE_LEAVES);
		this.toBlock(FrostBlocks.FROSTBITE_PLANKS);
		this.toBlock(FrostBlocks.FROSTBITE_PLANKS_SLAB);
		this.toBlock(FrostBlocks.FROSTBITE_PLANKS_STAIRS);
		this.woodenFence(FrostBlocks.FROSTBITE_FENCE, FrostBlocks.FROSTBITE_PLANKS);
		this.toBlock(FrostBlocks.FROSTBITE_FENCE_GATE);
		//this.toBlock(FrostBlocks.FROSTBITE_CRAFTING_TABLE);

		this.itemBlockFlat(FrostBlocks.VIGOROSHROOM);
		this.itemBlockFlat(FrostBlocks.ARCTIC_POPPY);
		this.itemBlockFlat(FrostBlocks.ARCTIC_WILLOW);

		this.itemBlockFlat(FrostBlocks.COLD_GRASS);
		this.itemBlockFlat(FrostBlocks.COLD_TALL_GRASS.get(), "cold_tall_grass_top");

		this.toBlock(FrostBlocks.FROST_CRYSTAL_ORE);
		this.toBlock(FrostBlocks.GLIMMERROCK_ORE);
		this.toBlock(FrostBlocks.ASTRIUM_ORE);
		this.toBlock(FrostBlocks.ASTRIUM_BLOCK);
		this.toBlock(FrostBlocks.STARDUST_CRYSTAL_ORE);
		this.toBlock(FrostBlocks.STARDUST_CRYSTAL_CLUSTER);
		this.toBlock(FrostBlocks.WARPED_CRYSTAL_BLOCK);
		this.itemBlockFlat(FrostBlocks.FROST_TORCH);
		this.toBlock(FrostBlocks.CRYSTAL_SMITHING_TABLE);
	}

	public ItemModelBuilder torchItem(Block item) {
		return withExistingParent(ForgeRegistries.BLOCKS.getKey(item).getPath(), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + ForgeRegistries.BLOCKS.getKey(item).getPath()));
	}

	private ItemModelBuilder generated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/generated");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTexTool(RegistryObject<Item> item) {
		return tool(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), prefix("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
	}

	private ItemModelBuilder tool(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/handheld");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTex(RegistryObject<Item> item) {
		return generated(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), prefix("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
	}

	private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/bow");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder bowTex(RegistryObject<Item> item, ModelFile pull0, ModelFile pull1, ModelFile pull2) {
		return bowItem(item.getId().getPath(), prefix("item/" + item.getId().getPath()))
				.override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.65).model(pull1).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.9).model(pull2).end();
	}

	private void woodenButton(Block button, String variant) {
		getBuilder(ForgeRegistries.BLOCKS.getKey(button).getPath())
				.parent(getExistingFile(mcLoc("block/button_inventory")))
				.texture("texture", "block/wood/planks_" + variant + "_0");
	}

	private void woodenFence(RegistryObject<? extends Block> fence, RegistryObject<? extends Block> block) {
		getBuilder(ForgeRegistries.BLOCKS.getKey(fence.get()).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath());
	}

	private void woodenFence(Block fence, String texture) {
		getBuilder(ForgeRegistries.BLOCKS.getKey(fence).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + texture);
	}

	private void toBlock(RegistryObject<? extends Block> b) {
		toBlockModel(b.get(), ForgeRegistries.BLOCKS.getKey(b.get()).getPath());
	}

	private void toBlockModel(Block b, String model) {
		toBlockModel(b, prefix("block/" + model));
	}

	private void toBlockModel(Block b, ResourceLocation model) {
		withExistingParent(ForgeRegistries.BLOCKS.getKey(b).getPath(), model);
	}

	public ItemModelBuilder itemBlockFlat(RegistryObject<? extends Block> block) {
		return itemBlockFlat(block.get(), blockName(block.get()));
	}

	public ItemModelBuilder itemBlockFlat(Block block, String name) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + name));
	}

	public ItemModelBuilder egg(RegistryObject<Item> item) {
		return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
	}

	public String blockName(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	@Override
	public String getName() {
		return "FrostRealm item and itemblock models";
	}
}