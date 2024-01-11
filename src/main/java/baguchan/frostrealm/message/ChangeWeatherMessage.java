package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ChangeWeatherMessage implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(FrostRealm.MODID, "changed_weather");

	private final FrostWeather weather;

	public ChangeWeatherMessage(FrostWeather weather) {
		this.weather = weather;
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeResourceLocation(FrostWeathers.getRegistry().getKey(this.weather));
	}

	public ChangeWeatherMessage(FriendlyByteBuf buf) {
		this(FrostWeathers.getRegistry().get(buf.readResourceLocation()));
	}

	public static void handle(ChangeWeatherMessage message, PlayPayloadContext context) {
		context.workHandler().execute(() -> {
			FrostWeatherManager.setFrostWeather(message.weather);
			});
	}
}
