package baguchan.frostrealm.registry;

import baguchan.frostrealm.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static baguchan.frostrealm.FrostRealm.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> FROST_CRYSTAL = ITEMS.register("frost_crystal", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> CRYONITE = ITEMS.register("cryonite", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> WARPED_CRYSTAL = ITEMS.register("warped_crystal", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> GLIMMERROCK = ITEMS.register("glimmerrock", () -> new GlimmerRockItem((new Item.Properties())));
    public static final RegistryObject<Item> ASTRIUM_RAW = ITEMS.register("astrium_raw", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> ASTRIUM_INGOT = ITEMS.register("astrium_ingot", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> STARDUST_CRYSTAL = ITEMS.register("stardust_crystal", () -> new Item((new Item.Properties())));
	public static final RegistryObject<Item> AURORA_GEM = ITEMS.register("aurora_gem", () -> new Item((new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> FROZEN_FRUIT = ITEMS.register("frozen_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.FROZEN_FRUIT)));
    public static final RegistryObject<Item> MELTED_FRUIT = ITEMS.register("melted_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.MELTED_FRUIT)));
    public static final RegistryObject<Item> SUGARBEET = ITEMS.register("sugarbeet", () -> new Item((new Item.Properties()).food(FrostFoods.SUGARBEET)));
    public static final RegistryObject<Item> SUGARBEET_SEEDS = ITEMS.register("sugarbeet_seeds", () -> new ItemNameBlockItem(FrostBlocks.SUGARBEET.get(), (new Item.Properties())));
	public static final RegistryObject<Item> RYE = ITEMS.register("rye", () -> new Item((new Item.Properties())));
	public static final RegistryObject<Item> RYE_BREAD = ITEMS.register("rye_bread", () -> new Item((new Item.Properties().food(FrostFoods.RYE_BREAD))));
	public static final RegistryObject<Item> RYE_SEEDS = ITEMS.register("rye_seeds", () -> new ItemNameBlockItem(FrostBlocks.RYE.get(), (new Item.Properties())));
	public static final RegistryObject<Item> BEARBERRY = ITEMS.register("bearberry", () -> new ItemNameBlockItem(FrostBlocks.BEARBERRY_BUSH.get(), (new Item.Properties())));
    public static final RegistryObject<Item> COOKED_BEARBERRY = ITEMS.register("bearberry_cooked", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_BEARBERRY)));
    public static final RegistryObject<Item> COOKED_SNOWPILE_QUAIL_EGG = ITEMS.register("cooked_snowpile_quail_egg", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_EGG)));
    public static final RegistryObject<Item> SNOWPILE_QUAIL_MEAT = ITEMS.register("snowpile_quail_meat", () -> new Item((new Item.Properties()).food(FrostFoods.SNOWPILE_QUAIL_MEAT)));
    public static final RegistryObject<Item> COOKED_SNOWPILE_QUAIL_MEAT = ITEMS.register("cooked_snowpile_quail_meat", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_MEAT)));
    public static final RegistryObject<Item> FROST_BOAR_MEAT = ITEMS.register("frost_boar_meat", () -> new Item((new Item.Properties()).food(FrostFoods.FROST_BOAR_MEAT)));
    public static final RegistryObject<Item> COOKED_FROST_BOAR_MEAT = ITEMS.register("cooked_frost_boar_meat", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_FROST_BOAR_MEAT)));

    public static final RegistryObject<Item> FROST_CATALYST = ITEMS.register("frost_catalyst", () -> new FrostCatalystItem((new Item.Properties()).stacksTo(1).durability(64)));
    public static final RegistryObject<Item> STRAY_NECKLACE_PART = ITEMS.register("stray_necklace_part", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> YETI_FUR = ITEMS.register("yeti_fur", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> FROST_BOAR_FUR = ITEMS.register("frost_boar_fur", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> FUSION_CRYSTAL_DAGGER = ITEMS.register("fusion_crystal_dagger", () -> new FusionCrystalDaggerItem((new Item.Properties()).durability(420)));
	public static final RegistryObject<Item> FROST_SPEAR = ITEMS.register("frost_spear", () -> new FrostSpearItem((new Item.Properties().durability(521))));

	public static final RegistryObject<Item> ASTRIUM_SWORD = ITEMS.register("astrium_sword", () -> new SwordItem(FrostItemTier.ASTRIUM, 3, -2.3F, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_AXE = ITEMS.register("astrium_axe", () -> new AxeItem(FrostItemTier.ASTRIUM, 5.5F, -3.0F, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_PICKAXE = ITEMS.register("astrium_pickaxe", () -> new PickaxeItem(FrostItemTier.ASTRIUM, 1, -2.7F, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_SHOVEL = ITEMS.register("astrium_shovel", () -> new ShovelItem(FrostItemTier.ASTRIUM, 1.5F, -2.9F, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_HOE = ITEMS.register("astrium_hoe", () -> new HoeItem(FrostItemTier.ASTRIUM, -2, -1.0F, (new Item.Properties())));


	public static final RegistryObject<Item> YETI_FUR_HELMET = ITEMS.register("yeti_fur_helmet", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> YETI_FUR_CHESTPLATE = ITEMS.register("yeti_fur_chestplate", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> YETI_FUR_LEGGINGS = ITEMS.register("yeti_fur_leggings", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> YETI_FUR_BOOTS = ITEMS.register("yeti_fur_boots", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, ArmorItem.Type.BOOTS, (new Item.Properties())));

	public static final RegistryObject<Item> FROST_BOAR_FUR_HELMET = ITEMS.register("frost_boar_fur_helmet", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> FROST_BOAR_FUR_CHESTPLATE = ITEMS.register("frost_boar_fur_chestplate", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> FROST_BOAR_FUR_LEGGINGS = ITEMS.register("frost_boar_fur_leggings", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> FROST_BOAR_FUR_BOOTS = ITEMS.register("frost_boar_fur_boots", () -> new YetiFurArmorItem(FrostArmorMaterials.FROST_BOAR_FUR, ArmorItem.Type.BOOTS, (new Item.Properties())));


	public static final RegistryObject<Item> ASTRIUM_HELMET = ITEMS.register("astrium_helmet", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_CHESTPLATE = ITEMS.register("astrium_chestplate", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_LEGGINGS = ITEMS.register("astrium_leggings", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRIUM_BOOTS = ITEMS.register("astrium_boots", () -> new ArmorItem(FrostArmorMaterials.ASTRIUM, ArmorItem.Type.BOOTS, (new Item.Properties())));


	public static final RegistryObject<Item> MARMOT_SPAWNEGG = ITEMS.register("marmot_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.MARMOT, 0xB18346, 0x9B6B2D, (new Item.Properties())));
	public static final RegistryObject<Item> SNOWPILE_QUAIL_SPAWNEGG = ITEMS.register("snowpile_quail_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.SNOWPILE_QUAIL, 0xFFFFFF, 0xFFFFFF, (new Item.Properties())));
	public static final RegistryObject<Item> YETI_SPAWNEGG = ITEMS.register("yeti_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.YETI, 0xD4D7DB, 0x403656, (new Item.Properties())));
	public static final RegistryObject<Item> FROST_WRAITH_SPAWNEGG = ITEMS.register("frost_wraith_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.FROST_WRAITH, 0x895D7B, 0xD15EBE, (new Item.Properties())));
	public static final RegistryObject<Item> CLUST_WRAITH_SPAWNEGG = ITEMS.register("clust_wraith_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.CLUST_WRAITH, 0x895D7B, 0xD15EBE, (new Item.Properties())));

	public static final RegistryObject<Item> CRYSTAL_FOX_SPAWNEGG = ITEMS.register("crystal_fox_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.CRYSTAL_FOX, 0xF7FFFB, 0x90D3E8, (new Item.Properties())));
	public static final RegistryObject<Item> SNOW_MOLE_SPAWNEGG = ITEMS.register("snow_mole_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.SNOW_MOLE, 0xE4E5E6, 0xB6A7A7, (new Item.Properties())));
	public static final RegistryObject<Item> ASTRA_BALL_SPAWNEGG = ITEMS.register("astra_ball_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.ASTRA_BALL, 0x9352CC, 0xE3A6FF, (new Item.Properties())));
    public static final RegistryObject<Item> FROST_BOAR_SPAWNEGG = ITEMS.register("frost_boar_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.FROST_BOAR, 0x031822, 0x296B89, (new Item.Properties())));
	public static final RegistryObject<Item> FROSTORM_DRAGON_SPAWNEGG = ITEMS.register("frostorm_dragon_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.FROSTORM_DRAGON, 0x031822, 0x296B89, (new Item.Properties())));
	public static final RegistryObject<Item> SEAL_SPAWNEGG = ITEMS.register("seal_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.SEAL, 0xFFFFFF, 0xFFFFFF, (new Item.Properties())));
	public static final RegistryObject<Item> STRAY_WARRIOR_SPAWNEGG = ITEMS.register("stray_warrior_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.STRAY_WARRIOR, 6387319, 14543594, (new Item.Properties())));

}
