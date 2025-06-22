package baguchan.frostrealm.data.generator.models;

import baguchan.frostrealm.data.generator.FrostModelTemplates;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class FrostItemModels extends ItemModelGenerators {
    public FrostItemModels(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }


    @Override
    public void run() {
        this.generateFlatItem(FrostItems.FROST_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.CRYONITE.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.CRYONITE_CREAM.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.COATING_FUR.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.ROCK_WOOD_STICK.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.WARPED_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.VENOM_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.UNSTABLE_VENOM_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.GLIMMERROCK.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_RAW.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_INGOT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.STARDUST_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_CRYSTAL.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_INGOT.asItem(), ModelTemplates.FLAT_ITEM);


        this.generateFlatItem(FrostItems.FROZEN_FRUIT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.MELTED_FRUIT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.SUGARBEET.asItem(), ModelTemplates.FLAT_ITEM);
        //this.generateFlatItem(FrostItems.SUGARBEET_SEEDS.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.RYE.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.RYE_BREAD.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.RYE_PANCAKE.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.COOKED_BEARBERRY.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.COOKED_SNOWPILE_QUAIL_EGG.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.SNOWPILE_QUAIL_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.FROST_BOAR_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.COOKED_FROST_BOAR_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.SILK_MOON_MEAT.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.COOKED_SILK_MOON_MEAT.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.FROST_CATALYST.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.STRAY_NECKLACE_PART.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.YETI_FUR.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.FROST_BOAR_FUR.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.FROST_BOAR_HORN.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.SILVER_MOON.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.ASTRIUM_SWORD.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_AXE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_PICKAXE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_SHOVEL.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_HOE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_SICKLE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);

        this.generateFlatItem(FrostItems.GLACINIUM_SWORD.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_AXE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_PICKAXE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_SHOVEL.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_HOE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_SICKLE.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(FrostItems.GLACINIUM_JAVELIN.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);


        this.generateFlatItem(FrostItems.YETI_FUR_HELMET.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.YETI_FUR_CHESTPLATE.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.YETI_FUR_LEGGINGS.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.YETI_FUR_BOOTS.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.FROST_BOAR_FUR_HELMET.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.FROST_BOAR_FUR_CHESTPLATE.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.FROST_BOAR_FUR_LEGGINGS.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.FROST_BOAR_FUR_BOOTS.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.ASTRIUM_HELMET.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_CHESTPLATE.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_LEGGINGS.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.ASTRIUM_BOOTS.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.WOLFFLUE_ASTRIUM_ARMOR.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.WOLFFLUE_FROST_BOAR_ARMOR.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.HOT_SPRING_BUCKET.asItem(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(FrostItems.MARMOT_SPAWNEGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateSpawnEgg(FrostItems.SNOWPILE_QUAIL_SPAWNEGG.get(), 16777215, 16777215);
        this.generateSpawnEgg(FrostItems.YETI_SPAWNEGG.get(), 13948891, 4208214);
        this.generateSpawnEgg(FrostItems.FROST_WRAITH_SPAWNEGG.get(), 9002363, 13721278);
        this.generateSpawnEgg(FrostItems.CRYSTAL_FOX_SPAWNEGG.get(), 16252923, 9491432);
        this.generateSpawnEgg(FrostItems.SNOW_MOLE_SPAWNEGG.get(), 15001062, 11970471);
        this.generateSpawnEgg(FrostItems.ASTRA_BALL_SPAWNEGG.get(), 9654988, 14919423);
        this.generateSpawnEgg(FrostItems.FROST_BOAR_SPAWNEGG.get(), 202786, 2714505);
        this.generateFlatItem(FrostItems.WOLFFLUE_SPAWNEGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateSpawnEgg(FrostItems.FERRET_SPAWNEGG.get(), 7953498, 4272429);
        this.generateSpawnEgg(FrostItems.SEAL_SPAWNEGG.get(), 16777215, 16777215);
        this.generateSpawnEgg(FrostItems.LESSER_WARRIOR_SPAWNEGG.get(), 6387319, 14543594);
        this.generateSpawnEgg(FrostItems.VENOCHEM_SPAWNEGG.get(), 4195929, 13522055);
        this.generateFlatItem(FrostItems.GOKKUR_SPAWNEGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(FrostItems.UNDER_GOKKUR_SPAWNEGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateSpawnEgg(FrostItems.ROOT_DEER_SPAWNEGG.get(), 6390687, 11213654);
        this.generateSpawnEgg(FrostItems.SILK_MOON_SPAWNEGG.get(), 14679020, 7716503);
        this.generateSpawnEgg(FrostItems.SEEKER_SPAWNEGG.get(), 0xFFFFFF, 0xFFFFFF);
        this.generateFlatItem(FrostItems.FROST_SPEAR.asItem(), FrostModelTemplates.BIG_HANDHELD);
    }

    public void generateSpawnEgg(Item p_387114_, int p_387737_, int p_387138_) {
        ResourceLocation resourcelocation = ModelLocationUtils.decorateItemModelLocation("bagus_lib:template_spawn_egg");
        this.itemModelOutput.accept(p_387114_, ItemModelUtils.tintedModel(resourcelocation, new ItemTintSource[]{ItemModelUtils.constantTint(p_387737_), ItemModelUtils.constantTint(p_387138_)}));
    }
}