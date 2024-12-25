package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public class FrItemModelGenerator extends ModelProvider {
	public FrItemModelGenerator(PackOutput generator) {
		super(generator, FrostRealm.MODID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateFlatItem(FrostItems.FROST_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.CRYONITE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.CRYONITE_CREAM.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.WARPED_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.VENOM_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.UNSTABLE_VENOM_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLIMMERROCK.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_RAW.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_INGOT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.STARDUST_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_INGOT.asItem(), ModelTemplates.FLAT_ITEM);


		itemModels.generateFlatItem(FrostItems.FROZEN_FRUIT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.MELTED_FRUIT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.SUGARBEET.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.SUGARBEET_SEEDS.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.RYE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.RYE_BREAD.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.RYE_PANCAKE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.RYE_SEEDS.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.BEARBERRY.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.COOKED_BEARBERRY.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.COOKED_SNOWPILE_QUAIL_EGG.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.SNOWPILE_QUAIL_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.FROST_BOAR_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.COOKED_FROST_BOAR_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.SILK_MOON_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.COOKED_SILK_MOON_MEAT.asItem(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(FrostItems.FROST_CATALYST.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.STRAY_NECKLACE_PART.asItem(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(FrostItems.YETI_FUR.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.FROST_BOAR_FUR.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.FROST_BOAR_HORN.asItem(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(FrostItems.SILVER_MOON.asItem(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(FrostItems.ASTRIUM_SWORD.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_AXE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_PICKAXE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_SHOVEL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_HOE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_SICKLE.asItem(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(FrostItems.GLACINIUM_SWORD.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_AXE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_PICKAXE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_SHOVEL.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_HOE.asItem(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(FrostItems.GLACINIUM_SICKLE.asItem(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(FrostItems.YETI_FUR_HELMET.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.YETI_FUR_CHESTPLATE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.YETI_FUR_LEGGINGS.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.YETI_FUR_BOOTS.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);

		itemModels.generateFlatItem(FrostItems.FROST_BOAR_FUR_HELMET.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.FROST_BOAR_FUR_CHESTPLATE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.FROST_BOAR_FUR_LEGGINGS.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.FROST_BOAR_FUR_BOOTS.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);

		itemModels.generateFlatItem(FrostItems.ASTRIUM_HELMET.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_CHESTPLATE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_LEGGINGS.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.ASTRIUM_BOOTS.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.WOLFFLUE_ASTRIUM_ARMOR.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(FrostItems.HOT_SPRING_BUCKET.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);

		itemModels.generateSpawnEgg(FrostItems.MARMOT_SPAWNEGG.get(), 11633478, 10185517);
		itemModels.generateSpawnEgg(FrostItems.SNOWPILE_QUAIL_SPAWNEGG.get(), 16777215, 16777215);
		itemModels.generateSpawnEgg(FrostItems.YETI_SPAWNEGG.get(), 13948891, 4208214);
		itemModels.generateSpawnEgg(FrostItems.FROST_WRAITH_SPAWNEGG.get(), 9002363, 13721278);
		itemModels.generateSpawnEgg(FrostItems.CRYSTAL_FOX_SPAWNEGG.get(), 16252923, 9491432);
		itemModels.generateSpawnEgg(FrostItems.SNOW_MOLE_SPAWNEGG.get(), 15001062, 11970471);
		itemModels.generateSpawnEgg(FrostItems.ASTRA_BALL_SPAWNEGG.get(), 9654988, 14919423);
		itemModels.generateSpawnEgg(FrostItems.FROST_BOAR_SPAWNEGG.get(), 202786, 2714505);
		itemModels.generateSpawnEgg(FrostItems.WOLFFLUE_SPAWNEGG.get(), 8689054, 11511178);
		itemModels.generateSpawnEgg(FrostItems.FERRET_SPAWNEGG.get(), 7953498, 4272429);
		itemModels.generateSpawnEgg(FrostItems.SEAL_SPAWNEGG.get(), 16777215, 16777215);
		itemModels.generateSpawnEgg(FrostItems.STRAY_WARRIOR_SPAWNEGG.get(), 6387319, 14543594);
		itemModels.generateSpawnEgg(FrostItems.VENOCHEM_SPAWNEGG.get(), 4195929, 13522055);
		itemModels.generateSpawnEgg(FrostItems.GOKKUR_SPAWNEGG.get(), 10526102, 7301477);
		itemModels.generateSpawnEgg(FrostItems.UNDER_GOKKUR_SPAWNEGG.get(), 7572368, 15098896);
		itemModels.generateSpawnEgg(FrostItems.ROOT_DEER_SPAWNEGG.get(), 6390687, 11213654);
		itemModels.generateSpawnEgg(FrostItems.SILK_MOON_SPAWNEGG.get(), 14679020, 7716503);
	}

	@Override
	public Stream<? extends Holder<Block>> getKnownBlocks() {
		return Stream.of();
	}

	@Override
	protected Stream<? extends Holder<Item>> getKnownItems() {
		return Stream.of();
	}

	@Override
	public String getName() {
		return this.modId + " item and itemblock models";
	}
}