package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.*;
import net.minecraft.resources.Identifier;

import java.util.function.BiFunction;

public class FrostRenderType {

    private static final RenderType AURORA_ARMOR_ENTITY_GLINT = RenderType.create(
            "frostrealm:aurora_armor_entity_glint",
            RenderSetup.builder(RenderPipelines.GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"))
                    .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                    .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                    .createRenderSetup()
    );
    private static final RenderType AURORA_GLINT_TRANSLUCENT = RenderType.create(
            "frostrealm:aurora_glint_translucent",
            RenderSetup.builder(RenderPipelines.GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"))
                    .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                    .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                    .createRenderSetup()
    );
    private static final RenderType AURORA_GLINT = RenderType.create(
            "frostrealm:aurora_glint",
            RenderSetup.builder(RenderPipelines.GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"))
                    .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                    .createRenderSetup()
    );
    private static final RenderType AURORA_ENTITY_GLINT = RenderType.create(
            "frostrealm:aurora_entity_glint",
            RenderSetup.builder(RenderPipelines.GLINT)
                    .withTexture("Sampler0", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"))
                    .setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
                    .createRenderSetup()
    );

    public static VertexConsumer getAuroraBuffer(MultiBufferSource p_115212_, RenderType p_115213_, boolean p_115214_) {
        return useTransparentGlint(p_115213_) ? VertexMultiConsumer.create(p_115212_.getBuffer(FrostRenderType.AURORA_GLINT_TRANSLUCENT), p_115212_.getBuffer(p_115213_)) : VertexMultiConsumer.create(p_115212_.getBuffer(p_115214_ ? FrostRenderType.AURORA_GLINT : FrostRenderType.AURORA_ENTITY_GLINT), p_115212_.getBuffer(p_115213_));

    }

    private static boolean useTransparentGlint(RenderType p_418495_) {
        return Minecraft.useShaderTransparency() && p_418495_ == Sheets.translucentItemSheet();
    }
}
