package baguchan.frostrealm.api.entity;

import baguchan.frostrealm.data.resource.registries.WolfflueVariants;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;

public class WolfflueVariant {
    public static final Codec<WolfflueVariant> DIRECT_CODEC = RecordCodecBuilder.create(
            p_332779_ -> p_332779_.group(
                            ResourceLocation.CODEC.fieldOf("wild_texture").forGetter(p_335261_ -> p_335261_.wildTexture),
                            ResourceLocation.CODEC.fieldOf("angry_texture").forGetter(p_335264_ -> p_335264_.angryTexture),
                            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(WolfflueVariant::biomes)
                    )
                    .apply(p_332779_, WolfflueVariant::new)
    );
    public static final Codec<Holder<WolfflueVariant>> CODEC = RegistryFileCodec.create(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<WolfflueVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY);
    private final ResourceLocation wildTexture;
    private final ResourceLocation angryTexture;
    private final ResourceLocation wildTextureFull;
    private final ResourceLocation angryTextureFull;
    private final HolderSet<Biome> biomes;

    public WolfflueVariant(ResourceLocation p_332712_, ResourceLocation p_332788_, HolderSet<Biome> p_332717_) {
        this.wildTexture = p_332712_;
        this.wildTextureFull = fullTextureId(p_332712_);
        this.angryTexture = p_332788_;
        this.angryTextureFull = fullTextureId(p_332788_);
        this.biomes = p_332717_;
    }

    private static ResourceLocation fullTextureId(ResourceLocation p_336042_) {
        return p_336042_.withPath(p_335262_ -> "textures/" + p_335262_ + ".png");
    }

    public ResourceLocation wildTexture() {
        return this.wildTextureFull;
    }

    public ResourceLocation angryTexture() {
        return this.angryTextureFull;
    }

    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    @Override
    public boolean equals(Object p_332811_) {
        if (p_332811_ == this) {
            return true;
        } else {
            return !(p_332811_ instanceof WolfflueVariant wolfvariant)
                    ? false
                    : Objects.equals(this.wildTexture, wolfvariant.wildTexture)
                    && Objects.equals(this.angryTexture, wolfvariant.angryTexture)
                    && Objects.equals(this.biomes, wolfvariant.biomes);
        }
    }

    @Override
    public int hashCode() {
        int i = 1;
        i = 31 * i + this.wildTexture.hashCode();
        i = 31 * i + this.angryTexture.hashCode();
        return 31 * i + this.biomes.hashCode();
    }
}
