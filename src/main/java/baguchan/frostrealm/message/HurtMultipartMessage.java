package baguchan.frostrealm.message;

import baguchan.frostrealm.entity.IHurtableMultipart;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/*
 * https://github.com/AlexModGuy/AlexsMobs/blob/1.19.4/src/main/java/com/github/alexthe666/alexsmobs/message/MessageHurtMultipart.java
 * Thanks Alex!
 */
public class HurtMultipartMessage {

    public int part;
    public int parent;
    public float damage;
    public String damageType;

    public HurtMultipartMessage(int part, int parent, float damage) {
        this.part = part;
        this.parent = parent;
        this.damage = damage;
        this.damageType = "";
    }

    public HurtMultipartMessage(int part, int parent, float damage, String damageType) {
        this.part = part;
        this.parent = parent;
        this.damage = damage;
        this.damageType = damageType;
    }

    public HurtMultipartMessage() {
    }

    public static HurtMultipartMessage read(FriendlyByteBuf buf) {
        return new HurtMultipartMessage(buf.readInt(), buf.readInt(), buf.readFloat(), buf.readUtf());
    }

    public static void write(HurtMultipartMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.part);
        buf.writeInt(message.parent);
        buf.writeFloat(message.damage);
        buf.writeUtf(message.damageType);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(HurtMultipartMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
            Player player = context.get().getSender();
            if (context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                player = Minecraft.getInstance().player;
            }

            if (player != null) {
                if (player.level != null) {
                    Entity part = player.level.getEntity(message.part);
                    Entity parent = player.level.getEntity(message.parent);
                    Registry<DamageType> registry = player.level.registryAccess().registry(Registries.DAMAGE_TYPE).get();
                    DamageType dmg = registry.get(new ResourceLocation(message.damageType));
                    if (dmg != null) {
                        Holder<DamageType> holder = registry.getHolder(registry.getId(dmg)).orElseGet(null);
                        if (holder != null) {
                            DamageSource source = new DamageSource(registry.getHolder(registry.getId(dmg)).get());
                            if (part instanceof IHurtableMultipart && parent instanceof LivingEntity) {
                                ((IHurtableMultipart) part).onAttackedFromServer((LivingEntity) parent, message.damage, source);
                            }
                            if (part == null && parent != null && parent.isMultipartEntity()) {
                                parent.hurt(source, message.damage);
                            }

                        }
                    }

                }
            }
        }
    }
}