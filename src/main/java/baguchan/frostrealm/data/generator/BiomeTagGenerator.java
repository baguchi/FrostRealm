package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BiomeTagGenerator extends BiomeTagsProvider {
    public BiomeTagGenerator(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_) {
        super(p_255941_, p_256600_, FrostRealm.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FrostTags.Biomes.HAS_IGLOO).add(FrostBiomes.TUNDRA);
        this.tag(FrostTags.Biomes.HAS_CASTLE).add(FrostBiomes.SHERBET_DESERT);
        this.tag(FrostTags.Biomes.GRASS_FROST_BIOME).add(FrostBiomes.TUNDRA, FrostBiomes.FRIGID_FOREST);
        this.tag(FrostTags.Biomes.HOT_BIOME).add(FrostBiomes.CRYSTAL_FALL, FrostBiomes.HOT_ROCK, FrostBiomes.SHERBET_DESERT, FrostBiomes.DEEP_UNDERGROUND);
    }
}