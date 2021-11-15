package baguchan.frostrealm.event;

import baguchan.frostrealm.FrostRealm;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID)
public class LootEvents {
	private static final Set<ResourceLocation> STRAY_LOOT = Sets.newHashSet(new ResourceLocation("entities/stray"));

	@SubscribeEvent
	public static void onInjectLoot(LootTableLoadEvent event) {
		if (STRAY_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(FrostRealm.MODID, "injections/stray_injections")).setWeight(1).setQuality(1)).name("tofustick_temple").build();
			event.getTable().addPool(pool);
		}
	}
}