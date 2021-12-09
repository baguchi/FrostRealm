package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangedColdMessage {
	private final int entityId;

	private final int temperature;

	private final float temperatureSaturation;

	public ChangedColdMessage(Entity entity, int temperature, float temperatureSaturation) {
		this.entityId = entity.getId();
		this.temperature = temperature;
		this.temperatureSaturation = temperatureSaturation;
	}

	public ChangedColdMessage(int entityID, int temperature, float temperatureSaturation) {
		this.entityId = entityID;
		this.temperature = temperature;
		this.temperatureSaturation = temperatureSaturation;
	}

	public static void writeToPacket(ChangedColdMessage packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.entityId);
		buf.writeInt(packet.temperature);
		buf.writeFloat(packet.temperatureSaturation);
	}

	public static ChangedColdMessage readFromPacket(FriendlyByteBuf buf) {
		return new ChangedColdMessage(buf.readInt(), buf.readInt(), buf.readFloat());
	}

	public static void handle(ChangedColdMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
			context.enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().player.level.getEntity(message.entityId);
				if (entity != null && entity instanceof net.minecraft.world.entity.LivingEntity)
					entity.getCapability(FrostRealm.FROST_LIVING_CAPABILITY, null).ifPresent(frostLivingCapability -> {
						frostLivingCapability.setTemperatureLevel(message.temperature);
						frostLivingCapability.setSaturation(message.temperatureSaturation);
					});
			});
		ctx.get().setPacketHandled(true);
	}
}
