package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static baguchan.frostrealm.FrostRealm.prefix;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrostRealm.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.singleTexFullbright(FrostItems.FROST_CRYSTAL);
		this.singleTexFullbright(FrostItems.CRYONITE);
		this.singleTexFullbright(FrostItems.CRYONITE_CREAM);
		this.singleTex(FrostItems.WARPED_CRYSTAL);
		this.singleTex(FrostItems.VENOM_CRYSTAL);
		this.singleTex(FrostItems.UNSTABLE_VENOM_CRYSTAL);
		this.singleTexFullbright(FrostItems.GLIMMERROCK);
		this.singleTex(FrostItems.ASTRIUM_RAW);
		this.singleTex(FrostItems.ASTRIUM_INGOT);
		this.singleTexFullbright(FrostItems.STARDUST_CRYSTAL);
		this.singleTex(FrostItems.GLACINIUM_CRYSTAL);
		this.singleTex(FrostItems.GLACINIUM_INGOT);


        this.singleTex(FrostItems.FROZEN_FRUIT);
        this.singleTex(FrostItems.MELTED_FRUIT);
        this.singleTex(FrostItems.SUGARBEET);
        this.singleTex(FrostItems.SUGARBEET_SEEDS);
		this.singleTex(FrostItems.RYE);
		this.singleTex(FrostItems.RYE_BREAD);
		this.singleTex(FrostItems.RYE_PANCAKE);
		this.singleTex(FrostItems.RYE_SEEDS);
        this.singleTex(FrostItems.BEARBERRY);
        this.singleTex(FrostItems.COOKED_BEARBERRY);
        this.singleTex(FrostItems.COOKED_SNOWPILE_QUAIL_EGG);
        this.singleTex(FrostItems.SNOWPILE_QUAIL_MEAT);
        this.singleTex(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT);
        this.singleTex(FrostItems.FROST_BOAR_MEAT);
        this.singleTex(FrostItems.COOKED_FROST_BOAR_MEAT);

        this.singleTex(FrostItems.FROST_CATALYST);
        this.singleTex(FrostItems.STRAY_NECKLACE_PART);

        this.singleTex(FrostItems.YETI_FUR);
		this.singleTex(FrostItems.FROST_BOAR_FUR);

		this.singleTexTool(FrostItems.SILVER_MOON);

        this.singleTexTool(FrostItems.ASTRIUM_SWORD);
		this.singleTexTool(FrostItems.ASTRIUM_AXE);
		this.singleTexTool(FrostItems.ASTRIUM_PICKAXE);
		this.singleTexTool(FrostItems.ASTRIUM_SHOVEL);
		this.singleTexTool(FrostItems.ASTRIUM_HOE);
		this.singleTexTool(FrostItems.ASTRIUM_SICKLE);

		this.singleTexTool(FrostItems.GLACINIUM_SWORD);
		this.singleTexTool(FrostItems.GLACINIUM_AXE);
		this.singleTexTool(FrostItems.GLACINIUM_PICKAXE);
		this.singleTexTool(FrostItems.GLACINIUM_SHOVEL);
		this.singleTexTool(FrostItems.GLACINIUM_HOE);
		this.singleTexTool(FrostItems.GLACINIUM_SICKLE);

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
		this.singleTex(FrostItems.WOLFFLUE_ASTRIUM_ARMOR);

		this.egg(FrostItems.MARMOT_SPAWNEGG);
		this.egg(FrostItems.SNOWPILE_QUAIL_SPAWNEGG);
		this.egg(FrostItems.YETI_SPAWNEGG);
		this.egg(FrostItems.FROST_WRAITH_SPAWNEGG);
		this.egg(FrostItems.CRYSTAL_FOX_SPAWNEGG);
		this.egg(FrostItems.SNOW_MOLE_SPAWNEGG);
        this.egg(FrostItems.ASTRA_BALL_SPAWNEGG);
        this.egg(FrostItems.FROST_BOAR_SPAWNEGG);
		this.egg(FrostItems.WOLFFLUE_SPAWNEGG);
		this.egg(FrostItems.SEAL_SPAWNEGG);
		this.egg(FrostItems.STRAY_WARRIOR_SPAWNEGG);
        this.egg(FrostItems.MIND_VINE_SPAWNEGG);
		this.egg(FrostItems.VENOCHEM_SPAWNEGG);
		this.egg(FrostItems.GOKKUR_SPAWNEGG);

		this.toBlock(FrostBlocks.FROZEN_DIRT);
		this.toBlock(FrostBlocks.FROZEN_GRASS_BLOCK);
		this.toBlock(FrostBlocks.FRIGID_GRASS_BLOCK);
		this.toBlock(FrostBlocks.FROZEN_FARMLAND);

		this.toBlock(FrostBlocks.PERMA_SLATE);
		this.toBlock(FrostBlocks.PERMA_SLATE_BRICK);
		this.toBlock(FrostBlocks.PERMA_SLATE_SMOOTH);
		this.toBlock(FrostBlocks.PERMA_SLATE_BRICK_SLAB);
		this.toBlock(FrostBlocks.PERMA_SLATE_BRICK_STAIRS);
		this.toBlock(FrostBlocks.PERMA_MAGMA);

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

		this.toBlock(FrostBlocks.SHERBET_SAND);
		this.toBlock(FrostBlocks.SHERBET_SANDSTONE);
		this.toBlock(FrostBlocks.SHERBET_SANDSTONE_STAIRS);
		this.toBlock(FrostBlocks.SHERBET_SANDSTONE_SLAB);

		this.toBlock(FrostBlocks.GLACINIUM_ORE);
		this.toBlock(FrostBlocks.GLACINIUM_BLOCK);

		this.toBlock(FrostBlocks.FROSTROOT_LOG);
		this.toBlock(FrostBlocks.STRIPPED_FROSTROOT_LOG);
		this.itemBlockFlat(FrostBlocks.FROSTROOT_SAPLING);
		this.toBlock(FrostBlocks.FROSTROOT_LEAVES);
		this.toBlock(FrostBlocks.FROSTROOT_PLANKS);
		this.toBlock(FrostBlocks.FROSTROOT_PLANKS_SLAB);
		this.toBlock(FrostBlocks.FROSTROOT_PLANKS_STAIRS);
		this.woodenFence(FrostBlocks.FROSTROOT_FENCE, FrostBlocks.FROSTROOT_PLANKS);
		this.toBlock(FrostBlocks.FROSTROOT_FENCE_GATE);
		this.singleTex(FrostBlocks.FROSTROOT_DOOR);
		this.trapdoor(FrostBlocks.FROSTROOT_TRAPDOOR);

		this.toBlock(FrostBlocks.FROSTBITE_LOG);
		this.toBlock(FrostBlocks.STRIPPED_FROSTBITE_LOG);
		this.itemBlockFlat(FrostBlocks.FROSTBITE_SAPLING);
		this.toBlock(FrostBlocks.FROSTBITE_LEAVES);
		this.toBlock(FrostBlocks.FROSTBITE_PLANKS);
		this.toBlock(FrostBlocks.FROSTBITE_PLANKS_SLAB);
		this.toBlock(FrostBlocks.FROSTBITE_PLANKS_STAIRS);
		this.woodenFence(FrostBlocks.FROSTBITE_FENCE, FrostBlocks.FROSTBITE_PLANKS);
		this.toBlock(FrostBlocks.FROSTBITE_FENCE_GATE);

		this.itemBlockFlat(FrostBlocks.VIGOROSHROOM);
		this.itemBlockFlat(FrostBlocks.ARCTIC_POPPY);
		this.itemBlockFlat(FrostBlocks.ARCTIC_WILLOW);

		this.itemBlockFlat(FrostBlocks.COLD_GRASS);
		this.itemBlockFlat(FrostBlocks.COLD_TALL_GRASS.get(), "cold_tall_grass_top");

		this.toBlock(FrostBlocks.RYE_BLOCK);

		this.toBlock(FrostBlocks.FROST_CRYSTAL_ORE);
		this.toBlock(FrostBlocks.GLIMMERROCK_ORE);
		this.toBlock(FrostBlocks.ASTRIUM_ORE);

		this.toBlock(FrostBlocks.FROST_CRYSTAL_SLATE_ORE);
		this.toBlock(FrostBlocks.GLIMMERROCK_SLATE_ORE);
		this.toBlock(FrostBlocks.ASTRIUM_SLATE_ORE);


		this.toBlock(FrostBlocks.GLIMMERROCK_BLOCK);
		this.toBlock(FrostBlocks.FROST_CRYSTAL_BLOCK);
		this.toBlock(FrostBlocks.ASTRIUM_BLOCK);
		this.toBlock(FrostBlocks.RAW_ASTRIUM_BLOCK);

		this.toBlock(FrostBlocks.STARDUST_CRYSTAL_ORE);
		this.toBlock(FrostBlocks.STARDUST_CRYSTAL_CLUSTER);
		this.toBlock(FrostBlocks.WARPED_CRYSTAL_BLOCK);
        this.toBlock(FrostBlocks.AURORA_INFUSER);
		this.itemBlockFlat(FrostBlocks.FROST_TORCH);
	}


	public void trapdoor(Supplier<? extends TrapDoorBlock> trapdoor) {
		withExistingParent(BuiltInRegistries.BLOCK.getKey(trapdoor.get()).getPath(), ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "block/" + blockName(trapdoor.get()) + "_bottom"));
	}

	private ItemModelBuilder singleTexFullbright(DeferredHolder<Item, ? extends Item> item) {
		return fullbright(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
	}

	private ItemModelBuilder singleTexFullbrightTool(DeferredHolder<Item, ? extends Item> item) {
		return fullbrightTool(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
	}

	private ItemModelBuilder fullbright(String name, ResourceLocation... layers) {
		return buildItem(name, "item/generated", 15, layers);
	}

	private ItemModelBuilder fullbrightTool(String name, ResourceLocation... layers) {
		return buildItem(name, "item/handheld", 15, layers);
	}

	private ItemModelBuilder singleTexTool(DeferredHolder<Item, ? extends Item> item) {
		return tool(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
	}

	private ItemModelBuilder singleTex(DeferredHolder<?, ?> item) {
		return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
	}

	public ItemModelBuilder torchItem(Block item) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(item).getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + BuiltInRegistries.BLOCK.getKey(item).getPath()));
	}

	private ItemModelBuilder buildItem(String name, String parent, int emissivity, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, parent);
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		if (emissivity > 0)
			builder = builder.customLoader(ItemLayerModelBuilder::begin).emissive(emissivity, emissivity, 0).renderType("minecraft:translucent", 0).end();
		return builder;
	}

	private ItemModelBuilder generated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/generated");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder tool(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/handheld");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/bow");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private void woodenButton(Block button, String variant) {
        getBuilder(BuiltInRegistries.BLOCK.getKey(button).getPath())
				.parent(getExistingFile(mcLoc("block/button_inventory")))
				.texture("texture", "block/wood/planks_" + variant + "_0");
	}

    private void woodenFence(Supplier<? extends Block> fence, Supplier<? extends Block> block) {
        getBuilder(BuiltInRegistries.BLOCK.getKey(fence.get()).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", "block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath());
	}

	private void woodenFence(Block fence, String texture) {
        getBuilder(BuiltInRegistries.BLOCK.getKey(fence).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + texture);
	}

    private void toBlock(Supplier<? extends Block> b) {
        toBlockModel(b.get(), BuiltInRegistries.BLOCK.getKey(b.get()).getPath());
	}

	private void toBlockModel(Block b, String model) {
		toBlockModel(b, prefix("block/" + model));
	}

	private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath(), model);
	}

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block) {
		return itemBlockFlat(block.get(), blockName(block.get()));
	}

	public ItemModelBuilder itemBlockFlat(Block block, String name) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + name));
	}

    public ItemModelBuilder egg(Supplier<Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
	}

	public String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
	}

	@Override
	public String getName() {
		return "FrostRealm item and itemblock models";
	}
}