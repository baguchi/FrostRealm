package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeWeatherEvent {
	private final FrostWeather weather;

	public ChangeWeatherEvent(FrostWeather weather) {
		this.weather = weather;
	}

	public static void writeToPacket(ChangeWeatherEvent packet, FriendlyByteBuf buf) {
		buf.writeRegistryId(FrostWeathers.getRegistry().get(), packet.weather);
	}

	public static ChangeWeatherEvent readFromPacket(FriendlyByteBuf buf) {
		return new ChangeWeatherEvent(buf.readRegistryId());
	}

	public static void handle(ChangeWeatherEvent message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null) {
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
						cap.setFrostWeather(message.weather);
					});
				}
			});
		ctx.get().setPacketHandled(true);
	}
}
