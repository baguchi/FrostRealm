package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.item.FrostCatalystItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FrostItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrostRealm.MODID);

	public static final RegistryObject<Item> FROST_CRYSTAL = ITEMS.register("frost_crystal", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> GLIMMERROCK = ITEMS.register("glimmerrock", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> STARDUST_CRYSTAL = ITEMS.register("stardust_crystal", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROZEN_FRUIT = ITEMS.register("frozen_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.FROZEN_FRUIT).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> MELTED_FRUIT = ITEMS.register("melted_fruit", () -> new Item((new Item.Properties()).food(FrostFoods.MELTED_FRUIT).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> SUGARBEET = ITEMS.register("sugarbeet", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	//public static final RegistryObject<Item> SUGARBEET_SEEDS = ITEMS.register("sugarbeet_seeds", () -> new ItemNameBlockItem((Block) FrostBlocks.SUGARBEETS.get(), (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> BEARBERRY = ITEMS.register("bearberry", () -> new ItemNameBlockItem(FrostBlocks.BEARBERRY_BUSH.get(), (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> COOKED_BEARBERRY = ITEMS.register("cooked_bearberry", () -> new Item((new Item.Properties()).food(FrostFoods.COOKED_BEARBERRY).tab(FrostGroups.TAB_FROSTREALM)));


	public static final RegistryObject<Item> FROST_CATALYST = ITEMS.register("frost_catalyst", () -> new FrostCatalystItem((new Item.Properties()).stacksTo(1).durability(64).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> STRAY_NECKLACE_PART = ITEMS.register("stray_necklace_part", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

	public static final RegistryObject<Item> YETI_FUR = ITEMS.register("yeti_fur", () -> new Item((new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROST_TORCH = ITEMS.register("frost_torch", () -> new StandingAndWallBlockItem((Block) FrostBlocks.FROST_TORCH.get(), (Block) FrostBlocks.WALL_FROST_TORCH.get(), (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));

	public static final RegistryObject<Item> CRYSTAL_TORTOISE_SPAWNEGG = ITEMS.register("crystal_tortoise_spawn_egg", () -> new ForgeSpawnEggItem(() -> FrostEntities.CRYSTAL_TORTOISE_TYPE, 0x3E3CAE, 0x8685E0, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> MARMOT_SPAWNEGG = ITEMS.register("marmot_spawn_egg", () -> new ForgeSpawnEggItem(() -> FrostEntities.MARMOT_TYPE, 0xB18346, 0x9B6B2D, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> YETI_SPAWNEGG = ITEMS.register("yeti_spawn_egg", () -> new ForgeSpawnEggItem(() -> FrostEntities.YETI_TYPE, 0xD4D7DB, 0x403656, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));
	public static final RegistryObject<Item> FROST_WRAITH_SPAWNEGG = ITEMS.register("frost_wraith_spawn_egg", () -> new ForgeSpawnEggItem(() -> FrostEntities.FROST_WRAITH_TYPE, 0x31454A, 0xA0CBD3, (new Item.Properties()).tab(FrostGroups.TAB_FROSTREALM)));


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
