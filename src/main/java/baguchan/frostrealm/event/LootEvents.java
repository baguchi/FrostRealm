package baguchan.frostrealm.event;

import baguchan.frostrealm.FrostRealm;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.Set;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID)
public class LootEvents {
	private static final Set<ResourceLocation> ANCIENT_CITY_LOOT = Sets.newHashSet(BuiltInLootTables.ANCIENT_CITY_ICE_BOX);
	private static final Set<ResourceLocation> ICE_IGROO_LOOT = Sets.newHashSet(BuiltInLootTables.IGLOO_CHEST);

	@SubscribeEvent
	public static void onInjectLoot(LootTableLoadEvent event) {
		if (ANCIENT_CITY_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(FrostRealm.MODID, "injections/catalyst_injections")).setWeight(1)).name("catalyst_injection_ancient").build();
			event.getTable().addPool(pool);
		}
		if (ICE_IGROO_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(FrostRealm.MODID, "injections/catalyst_injections")).setWeight(1)).name("igloo_injection_ancient").build();
			event.getTable().addPool(pool);
		}
	}
}