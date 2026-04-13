package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
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

}
