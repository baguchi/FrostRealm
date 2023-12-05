package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.NetworkEvent;

public class ChangeWeatherTimeMessage {
	private final int weatherTime;

	private final int weatherTimeCooldown;

	public ChangeWeatherTimeMessage(int weatherTime, int weatherTimeCooldown) {
		this.weatherTime = weatherTime;
		this.weatherTimeCooldown = weatherTimeCooldown;
	}

	public static void writeToPacket(ChangeWeatherTimeMessage packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.weatherTime);
		buf.writeInt(packet.weatherTimeCooldown);
	}

	public static ChangeWeatherTimeMessage readFromPacket(FriendlyByteBuf buf) {
		return new ChangeWeatherTimeMessage(buf.readInt(), buf.readInt());
	}

    public void handle(NetworkEvent.Context context) {
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
                        cap.setWetherTime(weatherTime);
                        cap.setWeatherCooldown(weatherTimeCooldown);
					});
			});
        context.setPacketHandled(true);
	}
}
