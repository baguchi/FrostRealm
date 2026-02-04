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
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;

public class WolfflueVariant {
    public static final Codec<WolfflueVariant> DIRECT_CODEC = RecordCodecBuilder.create(
            p_332779_ -> p_332779_.group(
                            Identifier.CODEC.fieldOf("wild_texture").forGetter(p_335261_ -> p_335261_.wildTexture),
                            Identifier.CODEC.fieldOf("angry_texture").forGetter(p_335264_ -> p_335264_.angryTexture),
                            Identifier.CODEC.fieldOf("wild_baby_texture").forGetter(p_335261_ -> p_335261_.wildBabyTexture),
                            Identifier.CODEC.fieldOf("angry_baby_texture").forGetter(p_335264_ -> p_335264_.angryBabyTexture),
                            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(WolfflueVariant::biomes)
                    )
                    .apply(p_332779_, WolfflueVariant::new)
    );
    public static final Codec<Holder<WolfflueVariant>> CODEC = RegistryFileCodec.create(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<WolfflueVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY);
    private final Identifier wildTexture;
    private final Identifier angryTexture;
    private final Identifier wildBabyTexture;
    private final Identifier angryBabyTexture;
    private final Identifier wildTextureFull;
    private final Identifier angryTextureFull;
    private final Identifier wildBabyTextureFull;
    private final Identifier angryBabyTextureFull;
    private final HolderSet<Biome> biomes;

    public WolfflueVariant(Identifier wildTexture, Identifier angryTexture, Identifier wildBabyTexture, Identifier angryBabyTexture, HolderSet<Biome> p_332717_) {
        this.wildTexture = wildTexture;
        this.wildTextureFull = fullTextureId(wildTexture);
        this.angryTexture = angryTexture;
        this.angryTextureFull = fullTextureId(angryTexture);
        this.wildBabyTexture = wildBabyTexture;
        this.wildBabyTextureFull = fullTextureId(wildBabyTexture);
        this.angryBabyTexture = angryBabyTexture;
        this.angryBabyTextureFull = fullTextureId(angryBabyTexture);
        this.biomes = p_332717_;
    }

    private static Identifier fullTextureId(Identifier p_336042_) {
        return p_336042_.withPath(p_335262_ -> "textures/" + p_335262_ + ".png");
    }

    public Identifier wildTexture() {
        return this.wildTextureFull;
    }

    public Identifier angryTexture() {
        return this.angryTextureFull;
    }

    public Identifier wildBabyTexture() {
        return this.wildBabyTextureFull;
    }

    public Identifier angryBabyTexture() {
        return this.angryBabyTextureFull;
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
                    && Objects.equals(this.wildBabyTexture, wolfvariant.wildBabyTexture)
                    && Objects.equals(this.angryBabyTexture, wolfvariant.angryBabyTexture)
                    && Objects.equals(this.biomes, wolfvariant.biomes);
        }
    }

    @Override
    public int hashCode() {
        int i = 1;
        i = 31 * i + this.wildTexture.hashCode();
        i = 31 * i + this.angryTexture.hashCode();
        i = 31 * i + this.wildBabyTexture.hashCode();
        i = 31 * i + this.angryBabyTexture.hashCode();
        return 31 * i + this.biomes.hashCode();
    }
}
