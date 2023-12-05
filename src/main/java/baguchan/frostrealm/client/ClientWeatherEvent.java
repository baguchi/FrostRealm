package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientWeatherEvent {

	@SubscribeEvent
	public static void onClientLevelUpdate(TickEvent.RenderTickEvent event) {
		if (Minecraft.getInstance().level != null) {
			Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(frostWeatherCapability -> {
				frostWeatherCapability.clientTick(Minecraft.getInstance().level);
			});
		}
	}
}
