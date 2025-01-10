package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

public class FrostRenderType extends RenderType {

    public static final RenderType AURORA_GLINT = RenderType.create(
            "frostrealm:aurora_glint",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            1536,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_GLINT_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"), TriState.DEFAULT, false))
                    .setCullState(RenderType.NO_CULL)
                    .setDepthTestState(RenderType.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderType.GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public static final RenderType AURORA_ARMOR_GLINT = RenderType.create(
            "frostrealm:aurora_armor_glint",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            1536,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"), TriState.DEFAULT, false))
                    .setCullState(RenderType.NO_CULL)
                    .setDepthTestState(RenderType.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderType.ENTITY_GLINT_TEXTURING)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .createCompositeState(false)
    );
    public static final RenderType AURORA_ENTITY_GLINT = RenderType.create(
            "frostrealm:aurora_entity_glint",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            1536,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"), TriState.DEFAULT, false))
                    .setCullState(RenderType.NO_CULL)
                    .setDepthTestState(RenderType.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderType.ENTITY_GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public FrostRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static VertexConsumer getAurora(MultiBufferSource bufferSource, RenderType renderType) {
        return VertexMultiConsumer.create(VertexMultiConsumer.create(bufferSource.getBuffer(FrostRenderType.AURORA_GLINT)), bufferSource.getBuffer(renderType));
    }
}
