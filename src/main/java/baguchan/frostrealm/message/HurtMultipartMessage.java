package baguchan.frostrealm.message;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.IHurtableMultipart;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/*
 * https://github.com/AlexModGuy/AlexsMobs/blob/1.19.4/src/main/java/com/github/alexthe666/alexsmobs/message/MessageHurtMultipart.java
 * Thanks Alex!
 */
public class HurtMultipartMessage implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(FrostRealm.MODID, "hurt_multipart");


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


    @Override
    public ResourceLocation id() {
        return ID;
    }

    public HurtMultipartMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readFloat(), buf.readUtf());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.part);
        buf.writeInt(this.parent);
        buf.writeFloat(this.damage);
        buf.writeUtf(this.damageType);
    }


    public static void handle(HurtMultipartMessage message, PlayPayloadContext context) {
        Player player = context.player().get();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            player = Minecraft.getInstance().player;
        }

        if (player != null) {
            if (player.level() != null) {
                Entity part2 = player.level().getEntity(message.part);
                Entity parent2 = player.level().getEntity(message.parent);
                Registry<DamageType> registry = player.level().registryAccess().registry(Registries.DAMAGE_TYPE).get();
                DamageType dmg = registry.get(new ResourceLocation(message.damageType));
                if (dmg != null) {
                    Holder<DamageType> holder = registry.getHolder(registry.getId(dmg)).orElseGet(null);
                    if (holder != null) {
                        DamageSource source = new DamageSource(registry.getHolder(registry.getId(dmg)).get());
                        if (part2 instanceof IHurtableMultipart && parent2 instanceof LivingEntity) {
                            ((IHurtableMultipart) part2).onAttackedFromServer((LivingEntity) parent2, message.damage, source);
                        }
                        if (part2 == null && parent2 != null && parent2.isMultipartEntity()) {
                            parent2.hurt(source, message.damage);
                        }

                    }
                }

            }
        }
    }
}