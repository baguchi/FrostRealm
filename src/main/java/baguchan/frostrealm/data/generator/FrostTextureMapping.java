package baguchan.frostrealm.data.generator;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class FrostTextureMapping {
    public static final TextureSlot GLOW_ALL = TextureSlot.create("glow_all");

    public static TextureMapping glowCube(Block p_387253_) {
        ResourceLocation resourcelocation = getBlockTexture(p_387253_);
        ResourceLocation resourcelocation2 = getBlockTexture(p_387253_).withSuffix("_glow");
        return glowCube(resourcelocation, resourcelocation2);
    }

    public static TextureMapping glowCube(ResourceLocation p_386993_, ResourceLocation glow) {
        return new TextureMapping().put(TextureSlot.ALL, p_386993_).put(GLOW_ALL, glow);
    }
}
