package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
