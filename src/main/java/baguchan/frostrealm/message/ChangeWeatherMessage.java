package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeWeatherMessage {
	private final FrostWeather weather;

	public ChangeWeatherMessage(FrostWeather weather) {
		this.weather = weather;
	}

	public static void writeToPacket(ChangeWeatherMessage packet, FriendlyByteBuf buf) {
		buf.writeRegistryIdUnsafe(FrostWeathers.getRegistry().get(), packet.weather);
	}

	public static ChangeWeatherMessage readFromPacket(FriendlyByteBuf buf) {
		return new ChangeWeatherMessage(buf.readRegistryIdUnsafe(FrostWeathers.getRegistry().get()));
	}

	public static void handle(ChangeWeatherMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
						cap.setFrostWeather(message.weather);
					});
			});
		ctx.get().setPacketHandled(true);
	}
}
