package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.resource.FrostPaintingVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;

import java.util.concurrent.CompletableFuture;

public class FrostPaintingTagsProvider extends PaintingVariantTagsProvider {
    public FrostPaintingTagsProvider(PackOutput p_255750_, CompletableFuture<HolderLookup.Provider> p_256184_) {
        super(p_255750_, p_256184_, FrostRealm.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256017_) {
        this.tag(PaintingVariantTags.PLACEABLE).add(FrostPaintingVariants.WOLFFLUE);
    }
}