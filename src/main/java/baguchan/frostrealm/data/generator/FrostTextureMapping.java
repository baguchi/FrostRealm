package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class FrostTextureMapping {
    public static final TextureSlot GLOW_ALL = TextureSlot.create("glow_all");

    public static final TextureSlot OVERLAY = TextureSlot.create("overlay");

    public static TextureMapping grassBlock(Block block, Block dirt) {
        ResourceLocation resourcelocation = getBlockTexture(block).withSuffix("_top");
        ResourceLocation resourcelocation2 = getBlockTexture(block).withSuffix("_side");
        ResourceLocation resourcelocation3 = getBlockTexture(block).withSuffix("_side_overlay");
        ResourceLocation resourcelocation4 = getBlockTexture(dirt);
        return grassBlock(resourcelocation, resourcelocation2, resourcelocation3, resourcelocation4);
    }

    public static TextureMapping auroraInfuser(Block p_388634_) {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(p_388634_))
                .put(TextureSlot.DOWN, getBlockTexture(FrostBlocks.FRIGID_STONE_SMOOTH.get()))
                .put(TextureSlot.UP, getBlockTexture(p_388634_, "_top"))
                .put(TextureSlot.NORTH, getBlockTexture(p_388634_))
                .put(TextureSlot.EAST, getBlockTexture(p_388634_))
                .put(TextureSlot.SOUTH, getBlockTexture(p_388634_))
                .put(TextureSlot.WEST, getBlockTexture(p_388634_));
    }

    public static TextureMapping grassBlock(ResourceLocation p_386993_, ResourceLocation side, ResourceLocation overlay, ResourceLocation dirt) {
        return new TextureMapping().put(TextureSlot.PARTICLE, dirt).put(TextureSlot.TOP, p_386993_).put(TextureSlot.SIDE, side).put(TextureSlot.BOTTOM, dirt).put(OVERLAY, overlay);
    }

    public static TextureMapping glowCube(Block p_387253_) {
        ResourceLocation resourcelocation = getBlockTexture(p_387253_);
        ResourceLocation resourcelocation2 = getBlockTexture(p_387253_, "_glow");
        return glowCube(resourcelocation, resourcelocation2);
    }

    public static TextureMapping glowCube(ResourceLocation p_386993_, ResourceLocation glow) {
        return new TextureMapping().put(TextureSlot.ALL, p_386993_).put(GLOW_ALL, glow);
    }
}
