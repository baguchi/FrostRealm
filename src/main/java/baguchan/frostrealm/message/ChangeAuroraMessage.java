package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ChangeAuroraMessage implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ChangeAuroraMessage> STREAM_CODEC = CustomPacketPayload.codec(ChangeAuroraMessage::write, ChangeAuroraMessage::new);
    public static final CustomPacketPayload.Type<ChangeAuroraMessage> TYPE = CustomPacketPayload.createType("frostrealm:change_aurora");

    private final float auroraLevel;

    public ChangeAuroraMessage(float auroraLevel) {
        this.auroraLevel = auroraLevel;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.auroraLevel);
    }

    public ChangeAuroraMessage(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    public static void handle(ChangeAuroraMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            FrostWeatherManager.setAuroraLevel(message.auroraLevel);
        });
    }
}
