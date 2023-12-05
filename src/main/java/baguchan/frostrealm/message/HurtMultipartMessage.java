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
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.NetworkEvent;

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


    public void handle(NetworkEvent.Context context) {
        context.setPacketHandled(true);
        Player player = context.getSender();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            player = Minecraft.getInstance().player;
        }

        if (player != null) {
            if (player.level() != null) {
                Entity part2 = player.level().getEntity(part);
                Entity parent2 = player.level().getEntity(parent);
                Registry<DamageType> registry = player.level().registryAccess().registry(Registries.DAMAGE_TYPE).get();
                DamageType dmg = registry.get(new ResourceLocation(damageType));
                if (dmg != null) {
                    Holder<DamageType> holder = registry.getHolder(registry.getId(dmg)).orElseGet(null);
                    if (holder != null) {
                        DamageSource source = new DamageSource(registry.getHolder(registry.getId(dmg)).get());
                        if (part2 instanceof IHurtableMultipart && parent2 instanceof LivingEntity) {
                            ((IHurtableMultipart) part2).onAttackedFromServer((LivingEntity) parent2, damage, source);
                        }
                        if (part2 == null && parent2 != null && parent2.isMultipartEntity()) {
                            parent2.hurt(source, damage);
                        }

                    }
                }

            }
        }
    }
}