package baguchan.frostrealm.registry;

import baguchan.frostrealm.item.FrostCatalystItem;
import baguchan.frostrealm.item.FusionCrystalDaggerItem;
import baguchan.frostrealm.item.YetiFurArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static baguchan.frostrealm.FrostRealm.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> FROST_CRYSTAL = ITEMS.register("frost_crystal", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> GLIMMERROCK = ITEMS.register("glimmerrock", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> STARDUST_CRYSTAL = ITEMS.register("stardust_crystal", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROZEN_FRUIT = ITEMS.register("frozen_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.FROZEN_FRUIT).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> MELTED_FRUIT = ITEMS.register("melted_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.MELTED_FRUIT).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> SUGARBEET = ITEMS.register("sugarbeet", () -> new Item((new Item.Properties()).food(FrostFoods.SUGARBEET).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> SUGARBEET_SEEDS = ITEMS.register("sugarbeet_seeds", () -> new ItemNameBlockItem(FrostBlocks.SUGARBEET.get(), (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> BEARBERRY = ITEMS.register("bearberry", () -> new ItemNameBlockItem(FrostBlocks.BEARBERRY_BUSH.get(), (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> COOKED_BEARBERRY = ITEMS.register("bearberry_cooked", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_BEARBERRY).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> COOKED_SNOWPILE_QUAIL_EGG = ITEMS.register("cooked_snowpile_quail_egg", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_EGG).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> SNOWPILE_QUAIL_MEAT = ITEMS.register("snowpile_quail_meat", () -> new Item((new Item.Properties()).food(FrostFoods.SNOWPILE_QUAIL_MEAT).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> COOKED_SNOWPILE_QUAIL_MEAT = ITEMS.register("cooked_snowpile_quail_meat", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_MEAT).tab(FrostGroups.TAB_FROSTREALM)));


	public static final RegistryObject<Item> FROST_CATALYST = ITEMS.register("frost_catalyst", () -> new FrostCatalystItem((new Item.Properties()).stacksTo(1).durability(64).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> STRAY_NECKLACE_PART = ITEMS.register("stray_necklace_part", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

	public static final RegistryObject<Item> YETI_FUR = ITEMS.register("yeti_fur", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

	public static final RegistryObject<Item> FUSION_CRYSTAL_DAGGER = ITEMS.register("fusion_crystal_dagger", () -> new FusionCrystalDaggerItem((new Item.Properties()).durability(420).tab(FrostGroups.TAB_FROSTREALM)));

	public static final RegistryObject<Item> YETI_FUR_HELMET = ITEMS.register("yeti_fur_helmet", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.HEAD, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> YETI_FUR_CHESTPLATE = ITEMS.register("yeti_fur_chestplate", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.CHEST, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> YETI_FUR_LEGGINGS = ITEMS.register("yeti_fur_leggings", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.LEGS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> YETI_FUR_BOOTS = ITEMS.register("yeti_fur_boots", () -> new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.FEET, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));


	public static final RegistryObject<Item> CRYSTAL_TORTOISE_SPAWNEGG = ITEMS.register("crystal_tortoise_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.CRYSTAL_TORTOISE, 0x3E3CAE, 0x8685E0, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> MARMOT_SPAWNEGG = ITEMS.register("marmot_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.MARMOT, 0xB18346, 0x9B6B2D, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> SNOWPILE_QUAIL_SPAWNEGG = ITEMS.register("ssnowpile_quail_pawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.SNOWPILE_QUAIL, 0xFFFFFF, 0xFFFFFF, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROST_WOLF_SPAWNEGG = ITEMS.register("frost_wolf_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.FROST_WOLF, 0xFFFFFF, 0xFFFFFF, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> YETI_SPAWNEGG = ITEMS.register("yeti_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.YETI, 0xD4D7DB, 0x403656, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROST_WRAITH_SPAWNEGG = ITEMS.register("frost_wraith_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.FROST_WRAITH, 0x31454A, 0xA0CBD3, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> GOKKUR_SPAWNEGG = ITEMS.register("gokkur_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.GOKKUR, 0x968E7A, 0xD5FCF7, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> GOKKUDILLO_SPAWNEGG = ITEMS.register("gokkudillo_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.GOKKUDILLO, 0x968E7A, 0xD5FCF7, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROST_BEASTER_SPAWNEGG = ITEMS.register("frost_beaster_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.FROST_BEASTER, 0x7CA7A6, 0x973C3C, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> CRYSTAL_FOX_SPAWNEGG = ITEMS.register("crystal_fox_spawn_egg", () -> new ForgeSpawnEggItem(FrostEntities.CRYSTAL_FOX, 0xF7FFFB, 0x90D3E8, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

	public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
		item.setRegistryName(new ResourceLocation(MODID, id));
		registry.getRegistry().register(item);
	}

	public static void register(RegistryEvent.Register<Item> registry, Item item) {
		if (item instanceof BlockItem && item.getRegistryName() == null) {
			item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());
		}
		registry.getRegistry().register(item);
	}
}
