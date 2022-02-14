package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.item.FrostCatalystItem;
import baguchan.frostrealm.item.FusionCrystalDaggerItem;
import baguchan.frostrealm.item.YetiFurArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostItems {
	public static final Item FROST_CRYSTAL = new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item GLIMMERROCK = new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item STARDUST_CRYSTAL = new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item FROZEN_FRUIT = new Item((new Item.Properties()).food(FrostFoods.FROZEN_FRUIT).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item MELTED_FRUIT = new Item((new Item.Properties()).food(FrostFoods.MELTED_FRUIT).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item SUGARBEET = new Item((new Item.Properties()).food(FrostFoods.SUGARBEET).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item SUGARBEET_SEEDS = new ItemNameBlockItem(FrostBlocks.SUGARBEET, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item BEARBERRY = new ItemNameBlockItem(FrostBlocks.BEARBERRY_BUSH, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item COOKED_BEARBERRY = new Item((new Item.Properties()).food(FrostFoods.COOKED_BEARBERRY).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item COOKED_SNOWPILE_QUAIL_EGG = new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_EGG).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item SNOWPILE_QUAIL_MEAT = new Item((new Item.Properties()).food(FrostFoods.SNOWPILE_QUAIL_MEAT).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item COOKED_SNOWPILE_QUAIL_MEAT = new Item((new Item.Properties()).food(FrostFoods.COOKED_SNOWPILE_QUAIL_MEAT).tab(FrostGroups.TAB_FROSTREALM));


	public static final Item FROST_CATALYST = new FrostCatalystItem((new Item.Properties()).stacksTo(1).durability(64).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item STRAY_NECKLACE_PART = new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));

	public static final Item YETI_FUR = new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item FROST_TORCH = new StandingAndWallBlockItem(FrostBlocks.FROST_TORCH, FrostBlocks.WALL_FROST_TORCH, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));

	public static final Item FUSION_CRYSTAL_DAGGER = new FusionCrystalDaggerItem((new Item.Properties()).durability(420).tab(FrostGroups.TAB_FROSTREALM));

	public static final Item YETI_FUR_HELMET = new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.HEAD, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item YETI_FUR_CHESTPLATE = new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.CHEST, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item YETI_FUR_LEGGINGS = new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.LEGS, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item YETI_FUR_BOOTS = new YetiFurArmorItem(FrostArmorMaterials.YETI_FUR, EquipmentSlot.FEET, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));


	public static final Item CRYSTAL_TORTOISE_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.CRYSTAL_TORTOISE, 0x3E3CAE, 0x8685E0, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item MARMOT_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.MARMOT, 0xB18346, 0x9B6B2D, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item SNOWPILE_QUAIL_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.SNOWPILE_QUAIL, 0xFFFFFF, 0xFFFFFF, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item FROST_WOLF_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.FROST_WOLF, 0xFFFFFF, 0xFFFFFF, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item YETI_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.YETI, 0xD4D7DB, 0x403656, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item FROST_WRAITH_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.FROST_WRAITH, 0x31454A, 0xA0CBD3, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item GOKKUR_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.GOKKUR, 0x968E7A, 0xD5FCF7, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item GOKKUDILLO_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.GOKKUDILLO, 0x968E7A, 0xD5FCF7, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item FROST_BEASTER_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.FROST_BEASTER, 0x7CA7A6, 0x973C3C, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));
	public static final Item CRYSTAL_FOX_SPAWNEGG = new ForgeSpawnEggItem(() -> FrostEntities.CRYSTAL_FOX, 0xF7FFFB, 0x90D3E8, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM));


	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		register(event, FROST_CRYSTAL, "frost_crystal");
		register(event, GLIMMERROCK, "glimmerrock");
		register(event, STARDUST_CRYSTAL, "stardust_crystal");
		register(event, FROZEN_FRUIT, "frozen_fruit");
		register(event, MELTED_FRUIT, "melted_fruit");
		register(event, SUGARBEET, "sugarbeet");
		register(event, SUGARBEET_SEEDS, "sugarbeet_seeds");
		register(event, BEARBERRY, "bearberry");
		register(event, COOKED_BEARBERRY, "bearberry_cooked");
		register(event, COOKED_SNOWPILE_QUAIL_EGG, "cooked_snowpile_quail_egg");
		register(event, SNOWPILE_QUAIL_MEAT, "snowpile_quail_meat");
		register(event, COOKED_SNOWPILE_QUAIL_MEAT, "cooked_snowpile_quail_meat");

		register(event, FROST_CATALYST, "frost_catalyst");
		register(event, STRAY_NECKLACE_PART, "stray_necklace_part");
		register(event, YETI_FUR, "yeti_fur");
		register(event, FROST_TORCH, "frost_torch");

		register(event, FUSION_CRYSTAL_DAGGER, "fusion_crystal_dagger");

		register(event, YETI_FUR_HELMET, "yeti_fur_helmet");
		register(event, YETI_FUR_CHESTPLATE, "yeti_fur_chestplate");
		register(event, YETI_FUR_LEGGINGS, "yeti_fur_leggings");
		register(event, YETI_FUR_BOOTS, "yeti_fur_boots");

		register(event, CRYSTAL_TORTOISE_SPAWNEGG, "crystal_tortoise_spawn_egg");
		register(event, MARMOT_SPAWNEGG, "marmot_spawn_egg");
		register(event, SNOWPILE_QUAIL_SPAWNEGG, "snowpile_quail_spawn_egg");
		register(event, FROST_WOLF_SPAWNEGG, "frost_wolf_spawn_egg");
		register(event, YETI_SPAWNEGG, "yeti_spawn_egg");
		register(event, FROST_WRAITH_SPAWNEGG, "frost_wraith_spawn_egg");
		register(event, GOKKUR_SPAWNEGG, "gokkur_spawn_egg");
		register(event, GOKKUDILLO_SPAWNEGG, "gokkudillo_spawn_egg");
		register(event, FROST_BEASTER_SPAWNEGG, "frost_beaster_spawn_egg");
		register(event, CRYSTAL_FOX_SPAWNEGG, "crystal_fox_spawn_egg");
	}

	public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
		item.setRegistryName(new ResourceLocation(FrostRealm.MODID, id));
		registry.getRegistry().register(item);
	}

	public static void register(RegistryEvent.Register<Item> registry, Item item) {
		if (item instanceof BlockItem && item.getRegistryName() == null) {
			item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());
		}
		registry.getRegistry().register(item);
	}
}
