package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.utils.AttackUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SickleAttackPacket() implements CustomPacketPayload {

    public static final Type<SickleAttackPacket> TYPE = new Type<>(FrostRealm.prefix("sickle_attack"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SickleAttackPacket> STREAM_CODEC = CustomPacketPayload.codec(SickleAttackPacket::write, SickleAttackPacket::new);

    public SickleAttackPacket(RegistryFriendlyByteBuf buf) {
        this();
    }

    public SickleAttackPacket() {
    }

    public void write(RegistryFriendlyByteBuf buf) {
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SickleAttackPacket message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player ent = ctx.player();
            if (ent != null) {
                AttackUtils.sickleAttack(ctx.player(), ent.getWeaponItem());
            }
        });
    }
}
