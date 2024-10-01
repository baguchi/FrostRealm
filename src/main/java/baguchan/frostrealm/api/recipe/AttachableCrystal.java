package baguchan.frostrealm.api.recipe;

import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;

import java.util.Objects;

public class AttachableCrystal {
    public static final Codec<AttachableCrystal> DIRECT_CODEC = RecordCodecBuilder.create(
            p_332779_ -> p_332779_.group(
                            RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("items").forGetter(AttachableCrystal::getItem),
                            Codec.FLOAT.fieldOf("damage").forGetter(AttachableCrystal::getDamage),
                            ExtraCodecs.POSITIVE_INT.fieldOf("usage").forGetter(AttachableCrystal::getUse),
                            DamageType.CODEC.fieldOf("damage_types").forGetter(AttachableCrystal::getDamageType)
                    )
                    .apply(p_332779_, AttachableCrystal::new)
    );
    public static final Codec<Holder<AttachableCrystal>> CODEC = RegistryFileCodec.create(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<AttachableCrystal>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY);
    private final HolderSet<Item> item;
    private final float damage;
    private final int use;
    private final Holder<DamageType> damageType;

    public AttachableCrystal(HolderSet<Item> item, float damage, int use, Holder<DamageType> damageType) {
        this.item = item;
        this.damage = damage;
        this.use = use;
        this.damageType = damageType;
    }

    public HolderSet<Item> getItem() {
        return item;
    }

    public float getDamage() {
        return damage;
    }

    public int getUse() {
        return use;
    }

    public Holder<DamageType> getDamageType() {
        return damageType;
    }

    @Override
    public boolean equals(Object p_332811_) {
        if (p_332811_ == this) {
            return true;
        } else {
            return !(p_332811_ instanceof AttachableCrystal attachableCrystal)
                    ? false
                    : Objects.equals(this.getItem(), attachableCrystal.getItem())
                    && Objects.equals(this.damage, attachableCrystal.damage)
                    && Objects.equals(this.use, attachableCrystal.use)
                    && Objects.equals(this.damageType, attachableCrystal.damageType);
        }
    }
}
