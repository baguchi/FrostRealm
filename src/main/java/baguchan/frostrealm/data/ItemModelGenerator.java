package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static baguchan.frostrealm.FrostRealm.prefix;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrostRealm.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.singleTex(FrostItems.FROST_CRYSTAL);
		this.singleTex(FrostItems.GLIMMERROCK);
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

		this.singleTexTool(FrostItems.FUSION_CRYSTAL_DAGGER);

		this.singleTex(FrostItems.YETI_FUR_HELMET);
		this.singleTex(FrostItems.YETI_FUR_CHESTPLATE);
		this.singleTex(FrostItems.YETI_FUR_LEGGINGS);
		this.singleTex(FrostItems.YETI_FUR_BOOTS);

		this.egg(FrostItems.CRYSTAL_TORTOISE_SPAWNEGG);
		this.egg(FrostItems.MARMOT_SPAWNEGG);
		this.egg(FrostItems.SNOWPILE_QUAIL_SPAWNEGG);
		this.egg(FrostItems.FROST_WOLF_SPAWNEGG);
		this.egg(FrostItems.YETI_SPAWNEGG);
		this.egg(FrostItems.FROST_WRAITH_SPAWNEGG);
		this.egg(FrostItems.GOKKUR_SPAWNEGG);
		this.egg(FrostItems.GOKKUDILLO_SPAWNEGG);
		this.egg(FrostItems.FROST_BEASTER_SPAWNEGG);

		this.toBlock(FrostBlocks.FROZEN_DIRT);
		this.toBlock(FrostBlocks.FROZEN_GRASS_BLOCK);
		this.toBlock(FrostBlocks.FROZEN_FARMLAND);

		this.toBlock(FrostBlocks.FRIGID_STONE);
		this.toBlock(FrostBlocks.FRIGID_STONE_SLAB);
		this.toBlock(FrostBlocks.FRIGID_STONE_STAIRS);
		this.toBlock(FrostBlocks.FRIGID_STONE_BRICK);
		this.toBlock(FrostBlocks.FRIGID_STONE_SMOOTH);
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
		this.itemBlockFlat(FrostBlocks.VIGOROSHROOM);
		this.itemBlockFlat(FrostBlocks.ARCTIC_POPPY);
		this.itemBlockFlat(FrostBlocks.ARCTIC_WILLOW);

		this.itemBlockFlat(FrostBlocks.COLD_GRASS);
		this.itemBlockFlat(FrostBlocks.COLD_TALL_GRASS, "cold_tall_grass_top");

		this.toBlock(FrostBlocks.FROST_CRYSTAL_ORE);
		this.toBlock(FrostBlocks.GLIMMERROCK_ORE);
		this.toBlock(FrostBlocks.STARDUST_CRYSTAL_ORE);
		this.toBlock(FrostBlocks.STARDUST_CRYSTAL_CLUSTER);
		this.itemBlockFlat(FrostBlocks.FROST_TORCH);

		this.toBlock(FrostBlocks.FRIGID_STOVE);
	}

	public ItemModelBuilder torchItem(Block item) {
		return withExistingParent(item.getRegistryName().getPath(), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + item.getRegistryName().getPath()));
	}

	private ItemModelBuilder generated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/generated");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTexTool(Item item) {
		return tool(item.getRegistryName().getPath(), prefix("item/" + item.getRegistryName().getPath()));
	}

	private ItemModelBuilder tool(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/handheld");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTex(Item item) {
		return generated(item.getRegistryName().getPath(), prefix("item/" + item.getRegistryName().getPath()));
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
		getBuilder(button.getRegistryName().getPath())
				.parent(getExistingFile(mcLoc("block/button_inventory")))
				.texture("texture", "block/wood/planks_" + variant + "_0");
	}

	private void woodenFence(Block fence, Block block) {
		getBuilder(fence.getRegistryName().getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + block.getRegistryName().getPath());
	}

	private void woodenFence(Block fence, String texture) {
		getBuilder(fence.getRegistryName().getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + texture);
	}

	private void toBlock(Block b) {
		toBlockModel(b, b.getRegistryName().getPath());
	}

	private void toBlockModel(Block b, String model) {
		toBlockModel(b, prefix("block/" + model));
	}

	private void toBlockModel(Block b, ResourceLocation model) {
		withExistingParent(b.getRegistryName().getPath(), model);
	}

	public ItemModelBuilder itemBlockFlat(Block block) {
		return itemBlockFlat(block, blockName(block));
	}

	public ItemModelBuilder itemBlockFlat(Block block, String name) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + name));
	}

	public ItemModelBuilder egg(Item item) {
		return withExistingParent(item.getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
	}

	public String blockName(Block block) {
		return block.getRegistryName().getPath();
	}

	@Override
	public String getName() {
		return "FrostRealm item and itemblock models";
	}
}