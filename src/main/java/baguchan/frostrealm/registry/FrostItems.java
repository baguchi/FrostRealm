package baguchan.frostrealm.registry;

import baguchan.frostrealm.item.*;
import baguchan.frostrealm.item.block.DeferredBlockItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static baguchan.frostrealm.FrostRealm.MODID;

public class FrostItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> FROST_CRYSTAL = ITEMS.registerItem("frost_crystal", AttachableCrystalItem::new);

    public static final DeferredItem<Item> CRYONITE = ITEMS.registerItem("cryonite", Item::new);
    public static final DeferredItem<Item> CRYONITE_CREAM = ITEMS.registerItem("cryonite_cream", (properties) -> new CryoniteCreamItem(properties));
    public static final DeferredItem<Item> COATING_FUR = ITEMS.registerItem("coating_fur", (properties) -> new Item(properties.stacksTo(1)));
    public static final DeferredItem<Item> ROCK_WOOD_STICK = ITEMS.registerItem("rock_wood_stick", (properties) -> new Item(properties));

    public static final DeferredItem<Item> WARPED_CRYSTAL = ITEMS.registerItem("warped_crystal", (properties) -> new Item(properties));
    public static final DeferredItem<Item> VENOM_CRYSTAL = ITEMS.registerItem("venom_crystal", (properties) -> new Item(properties));
    public static final DeferredItem<Item> UNSTABLE_VENOM_CRYSTAL = ITEMS.registerItem("unstable_venom_crystal", (properties) -> new AttachableCrystalItem(properties));
    public static final DeferredItem<Item> GLIMMERROCK = ITEMS.registerItem("glimmerrock", GlimmerRockItem::new);
    public static final DeferredItem<Item> ASTRIUM_RAW = ITEMS.registerItem("astrium_raw", (properties) -> new Item(properties));
    public static final DeferredItem<Item> ASTRIUM_INGOT = ITEMS.registerItem("astrium_ingot", (properties) -> new Item(properties));
    public static final DeferredItem<Item> STARDUST_CRYSTAL = ITEMS.registerItem("stardust_crystal", (properties) -> new Item(properties));
    public static final DeferredItem<Item> GLACINIUM_CRYSTAL = ITEMS.registerItem("glacinium_crystal", (properties) -> new Item(properties));
    public static final DeferredItem<Item> GLACINIUM_INGOT = ITEMS.registerItem("glacinium_ingot", (properties) -> new Item(properties));


    public static final DeferredItem<Item> FROZEN_FRUIT = ITEMS.registerItem("frozen_fruit", (properties) -> new Item(properties.food(FrostFoods.FROZEN_FRUIT)));
    public static final DeferredItem<Item> MELTED_FRUIT = ITEMS.registerItem("melted_fruit", (properties) -> new Item(properties.food(FrostFoods.MELTED_FRUIT)));
    public static final DeferredItem<Item> SUGARBEET = ITEMS.registerItem("sugarbeet", (properties) -> new Item(properties.food(FrostFoods.SUGARBEET)));
    public static final DeferredItem<Item> SUGARBEET_SEEDS = ITEMS.registerItem("sugarbeet_seeds", (properties) -> new DeferredBlockItem(FrostBlocks.SUGARBEET, properties));
    public static final DeferredItem<Item> RYE = ITEMS.registerItem("rye", (properties) -> new Item(properties));
    public static final DeferredItem<Item> RYE_BREAD = ITEMS.registerItem("rye_bread", (properties) -> new Item((properties.food(FrostFoods.RYE_BREAD))));
    public static final DeferredItem<Item> RYE_PANCAKE = ITEMS.registerItem("rye_pancake", (properties) -> new Item((properties.food(FrostFoods.RYE_PANCAKE))));
    public static final DeferredItem<Item> RYE_SEEDS = ITEMS.registerItem("rye_seeds", (properties) -> new DeferredBlockItem(FrostBlocks.RYE, properties));
    public static final DeferredItem<Item> BEARBERRY = ITEMS.registerItem("bearberry", (properties) -> new DeferredBlockItem(FrostBlocks.BEARBERRY_BUSH, properties));
    public static final DeferredItem<Item> COOKED_BEARBERRY = ITEMS.registerItem("bearberry_cooked", (properties) -> new Item(properties.food(FrostFoods.COOKED_BEARBERRY)));
    public static final DeferredItem<Item> COOKED_SNOWPILE_QUAIL_EGG = ITEMS.registerItem("cooked_snowpile_quail_egg", (properties) -> new Item(properties.food(FrostFoods.COOKED_SNOWPILE_QUAIL_EGG)));
    public static final DeferredItem<Item> SNOWPILE_QUAIL_MEAT = ITEMS.registerItem("snowpile_quail_meat", (properties) -> new Item(properties.food(FrostFoods.SNOWPILE_QUAIL_MEAT)));
    public static final DeferredItem<Item> COOKED_SNOWPILE_QUAIL_MEAT = ITEMS.registerItem("cooked_snowpile_quail_meat", (properties) -> new Item(properties.food(FrostFoods.COOKED_SNOWPILE_QUAIL_MEAT)));
    public static final DeferredItem<Item> GLACIER_BOAR_MEAT = ITEMS.registerItem("glacier_boar_meat", (properties) -> new Item(properties.food(FrostFoods.GLACIER_BOAR_MEAT)));
    public static final DeferredItem<Item> COOKED_GLACIER_BOAR_MEAT = ITEMS.registerItem("cooked_glacier_boar_meat", (properties) -> new Item(properties.food(FrostFoods.COOKED_GLACIER_BOAR_MEAT)));
    public static final DeferredItem<Item> SILK_MOON_MEAT = ITEMS.registerItem("silk_moon_meat", (properties) -> new Item(properties.food(FrostFoods.SILK_MOON_MEAT, Consumable.builder().consumeSeconds(0.6F).build())));
    public static final DeferredItem<Item> COOKED_SILK_MOON_MEAT = ITEMS.registerItem("cooked_silk_moon_meat", (properties) -> new Item(properties.food(FrostFoods.COOKED_SILK_MOON_MEAT, Consumable.builder().consumeSeconds(0.6F).build())));


    public static final DeferredItem<Item> FROST_CATALYST = ITEMS.registerItem("frost_catalyst", (properties) -> new FrostCatalystItem(properties.stacksTo(1).durability(64)));
    public static final DeferredItem<Item> STRAY_NECKLACE_PART = ITEMS.registerItem("stray_necklace_part", (properties) -> new Item(properties));

    public static final DeferredItem<Item> YETI_FUR = ITEMS.registerItem("yeti_fur", (properties) -> new Item(properties));
    public static final DeferredItem<Item> GLACIER_BOAR_FUR = ITEMS.registerItem("glacier_boar_fur", (properties) -> new Item(properties));
    public static final DeferredItem<Item> GLACIER_BOAR_HORN = ITEMS.registerItem("glacier_boar_horn", (properties) -> new Item(properties));
    public static final DeferredItem<Item> FROST_SPEAR = ITEMS.registerItem("frost_spear", (properties) -> new FrostSpearItem((properties.spear(FrostToolMaterials.FROST_SPEAR, 0.85F, 0.95F, 0.6F, 2.5F, 8.0F, 4.5F, 5.1F, 11.25F, 4.6F).rarity(Rarity.UNCOMMON))));
    public static final DeferredItem<Item> SILVER_MOON = ITEMS.registerItem("silver_moon", (properties) -> new SilverMoonSwordItem(FrostToolMaterials.SILVER_MOON, 3, -2.2F, (properties.rarity(Rarity.RARE))));

    public static final DeferredItem<Item> ASTRIUM_SWORD = ITEMS.registerItem("astrium_sword", (properties) -> new Item(properties.sword(FrostToolMaterials.ASTRIUM, 3, -2.3F)));
    public static final DeferredItem<Item> ASTRIUM_AXE = ITEMS.registerItem("astrium_axe", (properties) -> new AxeItem(FrostToolMaterials.ASTRIUM, 6F, -3.0F, properties));
    public static final DeferredItem<Item> ASTRIUM_PICKAXE = ITEMS.registerItem("astrium_pickaxe", (properties) -> new Item(properties.pickaxe(FrostToolMaterials.ASTRIUM, 1, -2.7F)));
    public static final DeferredItem<Item> ASTRIUM_SHOVEL = ITEMS.registerItem("astrium_shovel", (properties) -> new ShovelItem(FrostToolMaterials.ASTRIUM, 1.5F, -2.9F, properties));
    public static final DeferredItem<Item> ASTRIUM_HOE = ITEMS.registerItem("astrium_hoe", (properties) -> new HoeItem(FrostToolMaterials.ASTRIUM, -2, -1.0F, properties));
    public static final DeferredItem<Item> ASTRIUM_SICKLE = ITEMS.registerItem("astrium_sickle", (properties) -> new SickleItem(FrostToolMaterials.ASTRIUM, 5.0F, -3.2F, properties));

    public static final DeferredItem<Item> GLACINIUM_SWORD = ITEMS.registerItem("glacinium_sword", (properties) -> new Item(properties.sword(FrostToolMaterials.GLACINIUM, 3, -2.6F)));
    public static final DeferredItem<Item> GLACINIUM_AXE = ITEMS.registerItem("glacinium_axe", (properties) -> new AxeItem(FrostToolMaterials.GLACINIUM, 4F, -3.1F, properties));
    public static final DeferredItem<Item> GLACINIUM_PICKAXE = ITEMS.registerItem("glacinium_pickaxe", (properties) -> new Item(properties.pickaxe(FrostToolMaterials.GLACINIUM, 1, -2.9F)));
    public static final DeferredItem<Item> GLACINIUM_SHOVEL = ITEMS.registerItem("glacinium_shovel", (properties) -> new ShovelItem(FrostToolMaterials.GLACINIUM, 1.5F, -3.0F, properties));
    public static final DeferredItem<Item> GLACINIUM_HOE = ITEMS.registerItem("glacinium_hoe", (properties) -> new HoeItem(FrostToolMaterials.GLACINIUM, -2, -1.0F, properties));
    public static final DeferredItem<Item> GLACINIUM_SICKLE = ITEMS.registerItem("glacinium_sickle", (properties) -> new SickleItem(FrostToolMaterials.GLACINIUM, 3.0F, -3.3F, properties));
    public static final DeferredItem<Item> GLACINIUM_SPEAR = ITEMS.registerItem("glacinium_spear", (properties) -> new Item(properties.spear(FrostToolMaterials.GLACINIUM, 1.1F, 1.2F, 0.4F, 2.5F, 7.0F, 3.5F, 5.1F, 8.75F, 4.6F)));


    public static final DeferredItem<YetiFurArmorItem> YETI_FUR_HELMET = ITEMS.registerItem("yeti_fur_helmet", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorType.HELMET, (properties.durability(ArmorType.HELMET.getDurability(20)))));
    public static final DeferredItem<YetiFurArmorItem> YETI_FUR_CHESTPLATE = ITEMS.registerItem("yeti_fur_chestplate", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorType.CHESTPLATE, (properties.durability(ArmorType.CHESTPLATE.getDurability(20)))));
    public static final DeferredItem<YetiFurArmorItem> YETI_FUR_LEGGINGS = ITEMS.registerItem("yeti_fur_leggings", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorType.LEGGINGS, (properties.durability(ArmorType.LEGGINGS.getDurability(20)))));
    public static final DeferredItem<YetiFurArmorItem> YETI_FUR_BOOTS = ITEMS.registerItem("yeti_fur_boots", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorType.BOOTS, (properties.durability(ArmorType.BOOTS.getDurability(20)))));

    public static final DeferredItem<YetiFurArmorItem> GLACIER_BOAR_FUR_HELMET = ITEMS.registerItem("glacier_boar_fur_helmet", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.GLACIER_BOAR_FUR, ArmorType.HELMET, (properties.durability(ArmorType.HELMET.getDurability(20)))));
    public static final DeferredItem<YetiFurArmorItem> GLACIER_BOAR_FUR_CHESTPLATE = ITEMS.registerItem("glacier_boar_fur_chestplate", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.GLACIER_BOAR_FUR, ArmorType.CHESTPLATE, (properties.durability(ArmorType.CHESTPLATE.getDurability(20)))));
    public static final DeferredItem<YetiFurArmorItem> GLACIER_BOAR_FUR_LEGGINGS = ITEMS.registerItem("glacier_boar_fur_leggings", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.GLACIER_BOAR_FUR, ArmorType.LEGGINGS, (properties.durability(ArmorType.LEGGINGS.getDurability(20)))));
    public static final DeferredItem<YetiFurArmorItem> GLACIER_BOAR_FUR_BOOTS = ITEMS.registerItem("glacier_boar_fur_boots", (properties) -> new YetiFurArmorItem(FrostArmorMaterials.GLACIER_BOAR_FUR, ArmorType.BOOTS, (properties.durability(ArmorType.BOOTS.getDurability(20)))));


    public static final DeferredItem<Item> ASTRIUM_HELMET = ITEMS.registerItem("astrium_helmet", (properties) -> new Item((AstriumArmorItem.astriumArmor(properties, FrostArmorMaterials.ASTRIUM, ArmorType.HELMET))));
    public static final DeferredItem<Item> ASTRIUM_CHESTPLATE = ITEMS.registerItem("astrium_chestplate", (properties) -> new Item((AstriumArmorItem.astriumArmor(properties, FrostArmorMaterials.ASTRIUM, ArmorType.CHESTPLATE))));
    public static final DeferredItem<Item> ASTRIUM_LEGGINGS = ITEMS.registerItem("astrium_leggings", (properties) -> new Item(AstriumArmorItem.astriumArmor(properties, FrostArmorMaterials.ASTRIUM, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> ASTRIUM_BOOTS = ITEMS.registerItem("astrium_boots", (properties) -> new Item(AstriumArmorItem.astriumArmor(properties, FrostArmorMaterials.ASTRIUM, ArmorType.BOOTS)));
    public static final DeferredItem<Item> WOLFFLUE_ASTRIUM_ARMOR = ITEMS.registerItem("wolfflue_astrium_armor", (properties) -> new AstriumWolfflueArmorItem(FrostArmorMaterials.ASTRIUM, (properties.durability(ArmorType.BODY.getDurability(22)))));
    public static final DeferredItem<Item> WOLFFLUE_GLACIER_BOAR_ARMOR = ITEMS.registerItem("wolfflue_glacier_boar_armor", (properties) -> new WolfflueArmorItem(FrostArmorMaterials.GLACIER_BOAR_FUR, (properties.durability(ArmorType.BODY.getDurability(20)))));
    public static final DeferredItem<Item> WOLFFLUE_SADDLE = ITEMS.registerItem("wolfflue_saddle", (properties) -> new Item((properties.stacksTo(1).component(DataComponents.EQUIPPABLE, FrostEquippable.saddle()))));
    public static final DeferredItem<Item> HOT_SPRING_BUCKET = ITEMS.registerItem("hot_spring_bucket", (properties) -> new BucketItem(FrostFluids.HOT_SPRING.get(), (properties)));


    public static final DeferredItem<Item> MARMOT_SPAWNEGG = ITEMS.registerItem("marmot_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.MARMOT.get())));
    public static final DeferredItem<Item> SNOWPILE_QUAIL_SPAWNEGG = ITEMS.registerItem("snowpile_quail_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.SNOWPILE_QUAIL.get())));
    public static final DeferredItem<Item> YETI_SPAWNEGG = ITEMS.registerItem("yeti_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.YETI.get())));
    public static final DeferredItem<Item> FROST_WRAITH_SPAWNEGG = ITEMS.registerItem("frost_wraith_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.FROST_WRAITH.get())));
    public static final DeferredItem<Item> CRYSTAL_FOX_SPAWNEGG = ITEMS.registerItem("crystal_fox_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.CRYSTAL_FOX.get())));
    public static final DeferredItem<Item> SNOW_MOLE_SPAWNEGG = ITEMS.registerItem("snow_mole_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.SNOW_MOLE.get())));
    public static final DeferredItem<Item> ASTRA_BALL_SPAWNEGG = ITEMS.registerItem("astra_ball_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.ASTRA_BALL.get())));
    public static final DeferredItem<Item> GLACIER_BOAR_SPAWNEGG = ITEMS.registerItem("glacier_boar_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.GLACIER_BOAR.get())));
    public static final DeferredItem<Item> WOLFFLUE_SPAWNEGG = ITEMS.registerItem("wolfflue_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.WOLFFLUE.get())));
    public static final DeferredItem<Item> FERRET_SPAWNEGG = ITEMS.registerItem("ferret_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.FERRET.get())));
    public static final DeferredItem<Item> SEAL_SPAWNEGG = ITEMS.registerItem("seal_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.SEAL.get())));
    public static final DeferredItem<Item> LESSER_WARRIOR_SPAWNEGG = ITEMS.registerItem("lesser_warrior_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.LESSER_WARRIOR.get())));
    public static final DeferredItem<Item> VENOCHEM_SPAWNEGG = ITEMS.registerItem("venochem_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.VENOCHEM.get())));
    public static final DeferredItem<Item> GOKKUR_SPAWNEGG = ITEMS.registerItem("gokkur_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.GOKKUR.get())));
    public static final DeferredItem<Item> UNDER_GOKKUR_SPAWNEGG = ITEMS.registerItem("under_gokkur_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.UNDER_GOKKUR.get())));
    public static final DeferredItem<Item> ROOT_DEER_SPAWNEGG = ITEMS.registerItem("root_deer_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.ROOT_DEER.get())));
    public static final DeferredItem<Item> SILK_MOON_SPAWNEGG = ITEMS.registerItem("silk_moon_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.SILK_MOON.get())));
    public static final DeferredItem<Item> SEEKER_SPAWNEGG = ITEMS.registerItem("seeker_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(FrostEntities.SEEKER.get())));


}
