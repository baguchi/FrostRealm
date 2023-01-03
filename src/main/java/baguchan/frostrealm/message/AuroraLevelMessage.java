package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AuroraLevelMessage {
	private final float auroraLevel;


	public AuroraLevelMessage(float auroraLevel) {
		this.auroraLevel = auroraLevel;
	}

	public static void writeToPacket(AuroraLevelMessage packet, FriendlyByteBuf buf) {
		buf.writeFloat(packet.auroraLevel);
	}

	public static AuroraLevelMessage readFromPacket(FriendlyByteBuf buf) {
		return new AuroraLevelMessage(buf.readFloat());
	}

	public static void handle(AuroraLevelMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY, null).ifPresent(cap -> {
						cap.setAuroraLevel(message.auroraLevel);
					});
			});
		ctx.get().setPacketHandled(true);
	}
}
