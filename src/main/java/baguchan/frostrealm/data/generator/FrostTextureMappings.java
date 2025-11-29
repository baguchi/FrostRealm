package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class FrostTextureMappings {
    public static final TextureSlot GLOW_ALL = TextureSlot.create("glow_all");

    public static final TextureSlot OVERLAY = TextureSlot.create("overlay");

    public static TextureMapping doorTop(Block block) {
        return new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top")).copySlot(TextureSlot.TOP, TextureSlot.PARTICLE);
    }

    public static TextureMapping doorBottom(Block block) {
        return new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block)).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom")).copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE);
    }

    public static TextureMapping particle(TextureMapping textureMapping) {
        return textureMapping.copyForced(TextureSlot.ALL, TextureSlot.PARTICLE);
    }

    public static TextureMapping grassBlock(Block block, Block dirt) {
        Identifier resourcelocation = getBlockTexture(block).withSuffix("_top");
        Identifier resourcelocation2 = getBlockTexture(block).withSuffix("_side");
        Identifier resourcelocation3 = getBlockTexture(block).withSuffix("_side_overlay");
        Identifier resourcelocation4 = getBlockTexture(dirt);
        return grassBlock(resourcelocation, resourcelocation2, resourcelocation3, resourcelocation4);
    }

    public static TextureMapping wolfflue(Block p_388634_) {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(p_388634_, "_front"))
                .put(TextureSlot.DOWN, getBlockTexture(p_388634_, "_side"))
                .put(TextureSlot.UP, getBlockTexture(p_388634_, "_top"))
                .put(TextureSlot.NORTH, getBlockTexture(p_388634_, "_front"))
                .put(TextureSlot.EAST, getBlockTexture(p_388634_, "_side"))
                .put(TextureSlot.SOUTH, getBlockTexture(p_388634_, "_back"))
                .put(TextureSlot.WEST, getBlockTexture(p_388634_, "_side"));
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

    public static TextureMapping grassBlock(Identifier p_386993_, Identifier side, Identifier overlay, Identifier dirt) {
        return new TextureMapping().put(TextureSlot.PARTICLE, dirt).put(TextureSlot.TOP, p_386993_).put(TextureSlot.SIDE, side).put(TextureSlot.BOTTOM, dirt).put(OVERLAY, overlay);
    }

    public static TextureMapping glowCube(Block p_387253_) {
        Identifier resourcelocation = getBlockTexture(p_387253_);
        Identifier resourcelocation2 = getBlockTexture(p_387253_, "_glow");
        return glowCube(resourcelocation, resourcelocation2);
    }

    public static TextureMapping glowCube(Identifier p_386993_, Identifier glow) {
        return new TextureMapping().put(TextureSlot.ALL, p_386993_).put(GLOW_ALL, glow);
    }
}
