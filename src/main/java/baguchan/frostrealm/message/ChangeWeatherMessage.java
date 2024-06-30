package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ChangeWeatherMessage implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ChangeWeatherMessage> STREAM_CODEC = CustomPacketPayload.codec(ChangeWeatherMessage::write, ChangeWeatherMessage::new);
    public static final CustomPacketPayload.Type<ChangeWeatherMessage> TYPE = new Type<>(FrostRealm.prefix("change_weather"));

	private final FrostWeather weather;

	public ChangeWeatherMessage(FrostWeather weather) {
		this.weather = weather;
	}

	@Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeResourceLocation(FrostWeathers.getRegistry().getKey(this.weather));
	}

	public ChangeWeatherMessage(FriendlyByteBuf buf) {
		this(FrostWeathers.getRegistry().get(buf.readResourceLocation()));
	}

    public static void handle(ChangeWeatherMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
			FrostWeatherManager.setFrostWeather(message.weather);
			});
	}
}
