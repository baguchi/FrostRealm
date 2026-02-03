package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.rendertype.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class FrostRenderType {

    private static final Function<Identifier, RenderType> ENTITY_GLOW_SHADOW = Util.memoize(
            texture -> {
                RenderSetup state = RenderSetup.builder(FrostRenderPipelines.ENTITY_GLOW_SHADOW)
                        .withTexture("Sampler0", texture)
                        .useLightmap()
                        .useOverlay()
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                        .createRenderSetup();
                return RenderType.create("frostrealm:entity_glow_shadow", state);
            }
    );

    public static final RenderType AURORA_ARMOR_ENTITY_GLINT = RenderType.create(
            "frostrealm:aurora_armor_entity_glint",
            RenderSetup.builder(FrostRenderPipelines.AURORA_GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/misc/aurora_glint.png"))
                    .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                    .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                    .createRenderSetup()
    );
    public static final RenderType AURORA_GLINT_TRANSLUCENT = RenderType.create(
            "frostrealm:aurora_glint_translucent",
            RenderSetup.builder(FrostRenderPipelines.AURORA_GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/misc/aurora_glint.png"))
                    .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                    .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                    .createRenderSetup()
    );
    public static final RenderType AURORA_GLINT = RenderType.create(
            "frostrealm:aurora_glint",
            RenderSetup.builder(FrostRenderPipelines.AURORA_GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/misc/aurora_glint.png"))
                    .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                    .createRenderSetup()
    );
    public static final RenderType AURORA_ENTITY_GLINT = RenderType.create(
            "frostrealm:aurora_entity_glint",
            RenderSetup.builder(FrostRenderPipelines.AURORA_GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/misc/aurora_glint.png"))
                    .setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
                    .createRenderSetup()
    );

    public static VertexConsumer getAuroraBuffer(MultiBufferSource p_115212_, RenderType p_115213_, boolean p_115214_) {
        return useTransparentGlint(p_115213_) ? VertexMultiConsumer.create(p_115212_.getBuffer(FrostRenderType.AURORA_GLINT_TRANSLUCENT), p_115212_.getBuffer(p_115213_)) : VertexMultiConsumer.create(p_115212_.getBuffer(p_115214_ ? FrostRenderType.AURORA_GLINT : FrostRenderType.AURORA_ENTITY_GLINT), p_115212_.getBuffer(p_115213_));

    }

    private static boolean useTransparentGlint(RenderType p_418495_) {
        return Minecraft.useShaderTransparency() && p_418495_ == Sheets.translucentItemSheet();
    }

    public static RenderType entityGlowShadow(Identifier texture) {
        return ENTITY_GLOW_SHADOW.apply(texture);
    }
}
