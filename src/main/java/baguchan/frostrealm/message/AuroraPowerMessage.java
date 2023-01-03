package baguchan.frostrealm.message;

import baguchan.frostrealm.client.AuroraPowerClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AuroraPowerMessage {
	private final ResourceLocation[] auroraIDs;

	public AuroraPowerMessage(ResourceLocation[] power) {
		this.auroraIDs = power;
	}

	public static void writeToPacket(AuroraPowerMessage packet, FriendlyByteBuf buf) {
		buf.writeResourceLocation(packet.auroraIDs[0]);
		buf.writeResourceLocation(packet.auroraIDs[1]);
		buf.writeResourceLocation(packet.auroraIDs[2]);
	}

	public static AuroraPowerMessage readFromPacket(FriendlyByteBuf buf) {
		return new AuroraPowerMessage(new ResourceLocation[]{buf.readResourceLocation(), buf.readResourceLocation(), buf.readResourceLocation()});
	}

	public static void handle(AuroraPowerMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					AuroraPowerClientHandler.setAuroraIDs(message.auroraIDs);
			});
		ctx.get().setPacketHandled(true);
	}
}
