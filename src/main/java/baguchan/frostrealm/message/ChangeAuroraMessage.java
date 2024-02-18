package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ChangeAuroraMessage implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(FrostRealm.MODID, "changed_aurora");

    private final float auroraLevel;

    public ChangeAuroraMessage(float auroraLevel) {
        this.auroraLevel = auroraLevel;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.auroraLevel);
    }

    public ChangeAuroraMessage(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    public static void handle(ChangeAuroraMessage message, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            FrostWeatherManager.setAuroraLevel(message.auroraLevel);
        });
    }
}
