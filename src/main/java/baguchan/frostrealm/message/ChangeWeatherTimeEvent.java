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

	public ChangeWeatherTimeEvent(int weatherTime, int weatherTimeCooldown) {
		this.weatherTime = weatherTime;
		this.weatherTimeCooldown = weatherTimeCooldown;
	}

	public static void writeToPacket(ChangeWeatherTimeEvent packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.weatherTime);
		buf.writeInt(packet.weatherTimeCooldown);
	}

	public static ChangeWeatherTimeEvent readFromPacket(FriendlyByteBuf buf) {
		return new ChangeWeatherTimeEvent(buf.readInt(), buf.readInt());
	}

	public static void handle(ChangeWeatherTimeEvent message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
						cap.setWetherTime(message.weatherTime);
						cap.setWeatherCooldown(message.weatherTimeCooldown);
					});
			});
		ctx.get().setPacketHandled(true);
	}
}
