package baguchan.frostrealm.registry;

import baguchan.frostrealm.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static baguchan.frostrealm.FrostRealm.MODID;

public class FrostItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);

    public static final DeferredHolder<Item, Item> FROST_CRYSTAL = ITEMS.register("frost_crystal", () -> new FrostCrystalItem((new Item.Properties())));

    public static final DeferredHolder<Item, Item> CRYONITE = ITEMS.register("cryonite", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> CRYONITE_CREAM = ITEMS.register("cryonite_cream", () -> new GlimmerRockItem((new Item.Properties())));

    public static final DeferredHolder<Item, Item> WARPED_CRYSTAL = ITEMS.register("warped_crystal", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> VENOM_CRYSTAL = ITEMS.register("venom_crystal", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> UNSTABLE_VENOM_CRYSTAL = ITEMS.register("unstable_venom_crystal", () -> new SmithableCrystalItem((new Item.Properties())));
    public static final DeferredHolder<Item, Item> GLIMMERROCK = ITEMS.register("glimmerrock", () -> new GlimmerRockItem((new Item.Properties())));
    public static final DeferredHolder<Item, Item> ASTRIUM_RAW = ITEMS.register("astrium_raw", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> ASTRIUM_INGOT = ITEMS.register("astrium_ingot", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> STARDUST_CRYSTAL = ITEMS.register("stardust_crystal", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> GLACINIUM_CRYSTAL = ITEMS.register("glacinium_crystal", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> GLACINIUM_INGOT = ITEMS.register("glacinium_ingot", () -> new Item((new Item.Properties())));


    public static final DeferredHolder<Item, Item> FROZEN_FRUIT = ITEMS.register("frozen_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.FROZEN_FRUIT)));
    public static final DeferredHolder<Item, Item> MELTED_FRUIT = ITEMS.register("melted_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.MELTED_FRUIT)));
    public static final DeferredHolder<Item, Item> SUGARBEET = ITEMS.register("sugarbeet", () -> new Item((new Item.Properties()).food(FrostFoods.SUGARBEET)));
    public static final DeferredHolder<Item, Item> SUGARBEET_SEEDS = ITEMS.register("sugarbeet_seeds", () -> new ItemNameBlockItem(FrostBlocks.SUGARBEET.get(), (new Item.Properties())));
    public static final DeferredHolder<Item, Item> RYE = ITEMS.register("rye", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> RYE_BREAD = ITEMS.register("rye_bread", () -> new Item((new Item.Properties().food(FrostFoods.RYE_BREAD))));
    public static final DeferredHolder<Item, Item> RYE_PANCAKE = ITEMS.register("rye_pancake", () -> new Item((new Item.Properties().food(FrostFoods.RYE_PANCAKE))));
    public static final DeferredHolder<Item, Item> RYE_SEEDS = ITEMS.register("rye_seeds", () -> new ItemNameBlockItem(FrostBlocks.RYE.get(), (new Item.Properties())));
    public static final DeferredHolder<Item, Item> BEARBERRY = ITEMS.register("bearberry", () -> new ItemNameBlockItem(FrostBlocks.BEARBERRY_BUSH.get(), (new Item.Properties())));
    public static final DeferredHolder<Item, Item> COOKED_BEARBERRY = ITEMS.register("bearberry_cooked", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_BEARBERRY)));
    public static final DeferredHolder<Item, Item> COOKED_SNOWPILE_QUAIL_EGG = ITEMS.register("cooked_snowpile_quail_egg", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_EGG)));
    public static final DeferredHolder<Item, Item> SNOWPILE_QUAIL_MEAT = ITEMS.register("snowpile_quail_meat", () -> new Item((new Item.Properties()).food(FrostFoods.SNOWPILE_QUAIL_MEAT)));
    public static final DeferredHolder<Item, Item> COOKED_SNOWPILE_QUAIL_MEAT = ITEMS.register("cooked_snowpile_quail_meat", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_MEAT)));
    public static final DeferredHolder<Item, Item> FROST_BOAR_MEAT = ITEMS.register("frost_boar_meat", () -> new Item((new Item.Properties()).food(FrostFoods.FROST_BOAR_MEAT)));
    public static final DeferredHolder<Item, Item> COOKED_FROST_BOAR_MEAT = ITEMS.register("cooked_frost_boar_meat", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_FROST_BOAR_MEAT)));

    public static final DeferredHolder<Item, Item> FROST_CATALYST = ITEMS.register("frost_catalyst", () -> new FrostCatalystItem((new Item.Properties()).stacksTo(1).durability(64)));
    public static final DeferredHolder<Item, Item> STRAY_NECKLACE_PART = ITEMS.register("stray_necklace_part", () -> new Item((new Item.Properties())));

    public static final DeferredHolder<Item, Item> YETI_FUR = ITEMS.register("yeti_fur", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> FROST_BOAR_FUR = ITEMS.register("frost_boar_fur", () -> new Item((new Item.Properties())));
    public static final DeferredHolder<Item, Item> FROST_SPEAR = ITEMS.register("frost_spear", () -> new FrostSpearItem((new Item.Properties().attributes(FrostSpearItem.createAttributes()).durability(1021).rarity(Rarity.UNCOMMON))));
    public static final DeferredHolder<Item, Item> SILVER_MOON = ITEMS.register("silver_moon", () -> new SilverMoonSwordItem(FrostItemTier.SILVER_MOON, (new Item.Properties().attributes(SwordItem.createAttributes(FrostItemTier.SILVER_MOON, 3, -2.2F)).rarity(Rarity.RARE))));

    public static final DeferredHolder<Item, Item> ASTRIUM_SWORD = ITEMS.register("astrium_sword", () -> new SwordItem(FrostItemTier.ASTRIUM, (new Item.Properties().attributes(SwordItem.createAttributes(FrostItemTier.ASTRIUM, 3, -2.3F)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_AXE = ITEMS.register("astrium_axe", () -> new AxeItem(FrostItemTier.ASTRIUM, (new Item.Properties().attributes(AxeItem.createAttributes(FrostItemTier.ASTRIUM, 6F, -3.0F)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_PICKAXE = ITEMS.register("astrium_pickaxe", () -> new PickaxeItem(FrostItemTier.ASTRIUM, (new Item.Properties().attributes(PickaxeItem.createAttributes(FrostItemTier.ASTRIUM, 1, -2.7F)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_SHOVEL = ITEMS.register("astrium_shovel", () -> new ShovelItem(FrostItemTier.ASTRIUM, (new Item.Properties().attributes(ShovelItem.createAttributes(FrostItemTier.ASTRIUM, 1.5F, -2.9F)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_HOE = ITEMS.register("astrium_hoe", () -> new HoeItem(FrostItemTier.ASTRIUM, (new Item.Properties().attributes(HoeItem.createAttributes(FrostItemTier.ASTRIUM, -2, -1.0F)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_SICKLE = ITEMS.register("astrium_sickle", () -> new SickleItem(FrostItemTier.ASTRIUM, (new Item.Properties().attributes(SickleItem.createAttributes(FrostItemTier.ASTRIUM, 8.0F, -3.3F)))));

    public static final DeferredHolder<Item, Item> GLACINIUM_SWORD = ITEMS.register("glacinium_sword", () -> new SwordItem(FrostItemTier.GLACINIUM, (new Item.Properties().attributes(SwordItem.createAttributes(FrostItemTier.GLACINIUM, 3, -2.6F)))));
    public static final DeferredHolder<Item, Item> GLACINIUM_AXE = ITEMS.register("glacinium_axe", () -> new AxeItem(FrostItemTier.GLACINIUM, (new Item.Properties().attributes(AxeItem.createAttributes(FrostItemTier.GLACINIUM, 4F, -3.1F)))));
    public static final DeferredHolder<Item, Item> GLACINIUM_PICKAXE = ITEMS.register("glacinium_pickaxe", () -> new PickaxeItem(FrostItemTier.GLACINIUM, (new Item.Properties().attributes(PickaxeItem.createAttributes(FrostItemTier.GLACINIUM, 1, -2.9F)))));
    public static final DeferredHolder<Item, Item> GLACINIUM_SHOVEL = ITEMS.register("glacinium_shovel", () -> new ShovelItem(FrostItemTier.GLACINIUM, (new Item.Properties().attributes(ShovelItem.createAttributes(FrostItemTier.GLACINIUM, 1.5F, -3.0F)))));
    public static final DeferredHolder<Item, Item> GLACINIUM_HOE = ITEMS.register("glacinium_hoe", () -> new HoeItem(FrostItemTier.GLACINIUM, (new Item.Properties().attributes(HoeItem.createAttributes(FrostItemTier.GLACINIUM, -2, -1.0F)))));
    public static final DeferredHolder<Item, Item> GLACINIUM_SICKLE = ITEMS.register("glacinium_sickle", () -> new SickleItem(FrostItemTier.GLACINIUM, (new Item.Properties().attributes(SickleItem.createAttributes(FrostItemTier.GLACINIUM, 6.0F, -3.4F)))));


    public static final DeferredHolder<Item, Item> YETI_FUR_HELMET = ITEMS.register("yeti_fur_helmet", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.HELMET, (new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20)))));
    public static final DeferredHolder<Item, Item> YETI_FUR_CHESTPLATE = ITEMS.register("yeti_fur_chestplate", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.CHESTPLATE, (new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20)))));
    public static final DeferredHolder<Item, Item> YETI_FUR_LEGGINGS = ITEMS.register("yeti_fur_leggings", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.LEGGINGS, (new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20)))));
    public static final DeferredHolder<Item, Item> YETI_FUR_BOOTS = ITEMS.register("yeti_fur_boots", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.BOOTS, (new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20)))));

    public static final DeferredHolder<Item, Item> FROST_BOAR_FUR_HELMET = ITEMS.register("frost_boar_fur_helmet", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.HELMET, (new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20)))));
    public static final DeferredHolder<Item, Item> FROST_BOAR_FUR_CHESTPLATE = ITEMS.register("frost_boar_fur_chestplate", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.CHESTPLATE, (new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20)))));
    public static final DeferredHolder<Item, Item> FROST_BOAR_FUR_LEGGINGS = ITEMS.register("frost_boar_fur_leggings", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.LEGGINGS, (new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20)))));
    public static final DeferredHolder<Item, Item> FROST_BOAR_FUR_BOOTS = ITEMS.register("frost_boar_fur_boots", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.BOOTS, (new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20)))));


    public static final DeferredHolder<Item, Item> ASTRIUM_HELMET = ITEMS.register("astrium_helmet", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.HELMET, (new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(22)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_CHESTPLATE = ITEMS.register("astrium_chestplate", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.CHESTPLATE, (new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(22)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_LEGGINGS = ITEMS.register("astrium_leggings", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.LEGGINGS, (new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(22)))));
    public static final DeferredHolder<Item, Item> ASTRIUM_BOOTS = ITEMS.register("astrium_boots", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.BOOTS, (new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(22)))));
    public static final DeferredHolder<Item, Item> WOLFFLUE_ASTRIUM_ARMOR = ITEMS.register("wolfflue_astrium_armor", () -> new WolfflueArmorItem(FrostArmorMaterials.ASTRIUM, false, (new Item.Properties().durability(ArmorItem.Type.BODY.getDurability(22)))));


    public static final DeferredHolder<Item, Item> MARMOT_SPAWNEGG = ITEMS.register("marmot_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.MARMOT, 0xB18346, 0x9B6B2D, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> SNOWPILE_QUAIL_SPAWNEGG = ITEMS.register("snowpile_quail_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.SNOWPILE_QUAIL, 0xFFFFFF, 0xFFFFFF, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> YETI_SPAWNEGG = ITEMS.register("yeti_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.YETI, 0xD4D7DB, 0x403656, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> FROST_WRAITH_SPAWNEGG = ITEMS.register("frost_wraith_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.FROST_WRAITH, 0x895D7B, 0xD15EBE, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> CRYSTAL_FOX_SPAWNEGG = ITEMS.register("crystal_fox_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.CRYSTAL_FOX, 0xF7FFFB, 0x90D3E8, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> SNOW_MOLE_SPAWNEGG = ITEMS.register("snow_mole_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.SNOW_MOLE, 0xE4E5E6, 0xB6A7A7, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> ASTRA_BALL_SPAWNEGG = ITEMS.register("astra_ball_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.ASTRA_BALL, 0x9352CC, 0xE3A6FF, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> FROST_BOAR_SPAWNEGG = ITEMS.register("frost_boar_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.FROST_BOAR, 0x031822, 0x296B89, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> WOLFFLUE_SPAWNEGG = ITEMS.register("wolfflue_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.WOLFFLUE, 0x9CAAB1, 0xAFA58A, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> FERRET_SPAWNEGG = ITEMS.register("ferret_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.FERRET, 0x795C5A, 0x41312D, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> SEAL_SPAWNEGG = ITEMS.register("seal_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.SEAL, 0xFFFFFF, 0xFFFFFF, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> STRAY_WARRIOR_SPAWNEGG = ITEMS.register("seeker_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.SEEKER, 6387319, 14543594, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> MIND_VINE_SPAWNEGG = ITEMS.register("mind_vine_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.MIND_VINE, 0x495E27, 0x6C8031, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> VENOCHEM_SPAWNEGG = ITEMS.register("venochem_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.VENOCHEM, 0x400659, 0xCE5487, (new Item.Properties())));
    public static final DeferredHolder<Item, Item> GOKKUR_SPAWNEGG = ITEMS.register("gokkur_spawn_egg", () -> new DeferredSpawnEggItem(FrostEntities.GOKKUR, 0xA09D96, 0x6F6965, (new Item.Properties())));
}
