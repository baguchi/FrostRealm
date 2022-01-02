package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeWeatherTimeEvent {
	private final int weatherTime;

	private final int weatherTimeCooldown;
	private final float weatherStrength;

	public ChangeWeatherTimeEvent(int weatherTime, int weatherTimeCooldown, float weatherStrength) {
		this.weatherTime = weatherTime;
		this.weatherTimeCooldown = weatherTimeCooldown;
		this.weatherStrength = weatherStrength;
	}

	public static void writeToPacket(ChangeWeatherTimeEvent packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.weatherTime);
		buf.writeInt(packet.weatherTimeCooldown);
		buf.writeFloat(packet.weatherStrength);
	}

	public static ChangeWeatherTimeEvent readFromPacket(FriendlyByteBuf buf) {
		return new ChangeWeatherTimeEvent(buf.readInt(), buf.readInt(), buf.readFloat());
	}

	public static void handle(ChangeWeatherTimeEvent message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
						cap.setWetherTime(message.weatherTime);
						cap.setWeatherCooldown(message.weatherTimeCooldown);
						cap.setWeatherLevel(message.weatherStrength);
					});
			});
		ctx.get().setPacketHandled(true);
	}
}
