package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientWeatherEvent {

	@SubscribeEvent
    public static void onClientLevelUpdate(ClientTickEvent.Pre event) {
		if (Minecraft.getInstance().level != null) {
			FrostWeatherManager.clientTick(Minecraft.getInstance().level);
		}
	}
}
