package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.NetworkEvent;

public class ChangeWeatherMessage {
	private final FrostWeather weather;

	public ChangeWeatherMessage(FrostWeather weather) {
		this.weather = weather;
	}

	public static void writeToPacket(ChangeWeatherMessage packet, FriendlyByteBuf buf) {
        buf.writeResourceLocation(FrostWeathers.getRegistry().getKey(packet.weather));
	}

	public static ChangeWeatherMessage readFromPacket(FriendlyByteBuf buf) {
        return new ChangeWeatherMessage(FrostWeathers.getRegistry().get(buf.readResourceLocation()));
	}

    public void handle(NetworkEvent.Context context) {
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
                        cap.setFrostWeather(weather);
					});
			});
        context.setPacketHandled(true);
	}
}
