package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.registry.FrostAttachs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.GameTestClearMarkersDebugPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ChangedColdMessage implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ChangedColdMessage> STREAM_CODEC = CustomPacketPayload.codec(ChangedColdMessage::write, ChangedColdMessage::new);
    public static final CustomPacketPayload.Type<ChangedColdMessage> TYPE = CustomPacketPayload.createType("frostrealm:changed_cold");
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

	public void write(FriendlyByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.temperature);
		buf.writeFloat(this.temperatureSaturation);
	}

	public ChangedColdMessage(FriendlyByteBuf buf) {
		this(buf.readInt(), buf.readInt(), buf.readFloat());
	}

    public static void handle(ChangedColdMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
			Entity entity = Minecraft.getInstance().level.getEntity(message.entityId);
				if (entity != null && entity instanceof net.minecraft.world.entity.LivingEntity) {
					FrostLivingCapability frostLivingCapability = entity.getData(FrostAttachs.FROST_LIVING);
					frostLivingCapability.setTemperatureLevel(message.temperature);
					frostLivingCapability.setSaturation(message.temperatureSaturation);
				}
			});
	}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
