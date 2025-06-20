package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class FrostRenderType extends RenderType {

    public static final RenderType AURORA_GLINT = create(
            "frostrealm:aurora_glint",
            1536,
            RenderPipelines.GLINT,
            RenderType.CompositeState.builder()
                    .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"), false))
                    .setTexturingState(GLINT_TEXTURING)
                    .createCompositeState(false)
    );


    public static final RenderType AURORA_ARMOR_ENTITY_GLINT = create(
            "frostrealm:aurora_armor_entity_glint",
            1536,
            RenderPipelines.GLINT,
            RenderType.CompositeState.builder()
                    .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"), false))
                    .setTexturingState(ARMOR_ENTITY_GLINT_TEXTURING)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .createCompositeState(false)
    );
    public static final RenderType AURORA_ENTITY_GLINT = create(
            "frostrealm:aurora_entity_glint",
            1536,
            RenderPipelines.GLINT,
            RenderType.CompositeState.builder()
                    .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png"), false))
                    .setTexturingState(ENTITY_GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public FrostRenderType(String p_173178_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static VertexConsumer getAurora(MultiBufferSource bufferSource, RenderType renderType) {
        return VertexMultiConsumer.create(VertexMultiConsumer.create(bufferSource.getBuffer(FrostRenderType.AURORA_GLINT)), bufferSource.getBuffer(renderType));
    }
}
