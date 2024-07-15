package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalkerPart;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public record UpdateMultipartPacket(int entityId, @Nullable Entity entity,
                                    @Nullable Map<Integer, PartDataHolder> data) implements CustomPacketPayload {

    public static final Type<UpdateMultipartPacket> TYPE = new Type<>(FrostRealm.prefix("update_multipart_entity"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateMultipartPacket> STREAM_CODEC = CustomPacketPayload.codec(UpdateMultipartPacket::write, UpdateMultipartPacket::new);

    public UpdateMultipartPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), null, new HashMap<>());
        int id;
        while ((id = buf.readInt()) > 0) {
            this.data.put(id, PartDataHolder.decode(buf));
        }
    }

    public UpdateMultipartPacket(Entity entity) {
        this(-1, entity, Arrays.stream(entity.getParts()).filter(part -> part instanceof CorruptedWalkerPart).map(part -> (CorruptedWalkerPart<?>) part).collect(Collectors.toMap(CorruptedWalkerPart::getId, CorruptedWalkerPart::writeData)));
    }

    public void write(RegistryFriendlyByteBuf buf) {
        if (this.entity == null)
            throw new IllegalStateException("Null Entity while encoding UpdateMultipartPacket");
        if (this.data == null)
            throw new IllegalStateException("Null Data while encoding UpdateMultipartPacket");
        buf.writeInt(this.entity.getId());
        this.data.forEach((id, data) -> {
            buf.writeInt(id);
            data.encode(buf);
        });
        buf.writeInt(-1);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateMultipartPacket message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            int eId = message.entity != null && message.entityId <= 0 ? message.entity.getId() : message.entityId; // Account for Singleplayer
            Entity ent = ctx.player().level().getEntity(eId);
            if (ent != null && ent.isMultipartEntity()) {
                PartEntity<?>[] parts = ent.getParts();
                if (parts == null)
                    return;
                for (PartEntity<?> part : parts) {
                    if (part instanceof CorruptedWalkerPart<?> tfPart) {
                        if (message.data == null && message.entity != null) // Account for Singleplayer
                            Arrays.stream(message.entity.getParts())
                                    .filter(p -> p instanceof CorruptedWalkerPart<?> && p.getId() == part.getId())
                                    .map(p -> (CorruptedWalkerPart<?>) p)
                                    .findFirst().ifPresent(p -> tfPart.readData(p.writeData()));
                        else if (message.data != null) {
                            PartDataHolder data = message.data.get(tfPart.getId());
                            if (data != null)
                                tfPart.readData(data);
                        }
                    }
                }
            }
        });
    }


    public record PartDataHolder(double x, double y, double z,
                                 float yRot, float xRot,
                                 float width, float height,
                                 boolean fixed,
                                 @Nullable List<SynchedEntityData.DataValue<?>> data) {


        public void encode(RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(this.x());
            buffer.writeDouble(this.y());
            buffer.writeDouble(this.z());
            buffer.writeFloat(this.yRot());
            buffer.writeFloat(this.xRot());
            buffer.writeFloat(this.width());
            buffer.writeFloat(this.height());
            buffer.writeBoolean(this.fixed());
            if (this.data() != null) {
                for (SynchedEntityData.DataValue<?> datavalue : this.data()) {
                    datavalue.write(buffer);
                }
            }
            buffer.writeByte(255);
        }

        static PartDataHolder decode(RegistryFriendlyByteBuf buffer) {
            return new PartDataHolder(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(),
                    buffer.readFloat(), buffer.readFloat(),
                    buffer.readFloat(), buffer.readFloat(),
                    buffer.readBoolean(),
                    unpack(buffer)
            );
        }

        private static List<SynchedEntityData.DataValue<?>> unpack(RegistryFriendlyByteBuf buf) {
            List<SynchedEntityData.DataValue<?>> list = new ArrayList<>();

            int i;
            while ((i = buf.readUnsignedByte()) != 255) {
                list.add(SynchedEntityData.DataValue.read(buf, i));
            }

            return list;
        }

    }
}
