package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

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

    public static final BiFunction<ResourceLocation, Boolean, RenderType> DARK_OUTLINE = Util.memoize(
            (p_414959_, p_414960_) -> RenderType.create(
                    "frostrealm:dark_outline",
                    1536,
                    p_414960_ ? FrostRenderPipelines.DARK_OUTLINE_CULL : FrostRenderPipelines.DARK_OUTLINE_NO_CULL,
                    RenderType.CompositeState.builder()
                            .setTextureState(new RenderStateShard.TextureStateShard(p_414959_, false))
                            .createCompositeState(false)
            )
    );

    public static final BiFunction<ResourceLocation, Boolean, RenderType> MYSTIC = Util.memoize(
            (p_414959_, p_414960_) -> RenderType.create(
                    "frostrealm:mystic",
                    1536,
                    p_414960_ ? FrostRenderPipelines.MYSTIC_CULL : FrostRenderPipelines.MYSTIC_NO_CULL,
                    RenderType.CompositeState.builder()
                            .setTextureState(new RenderStateShard.TextureStateShard(p_414959_, false))
                            .createCompositeState(false)
            )
    );

    public static final RenderType DARK_ITEM_RENDER_TYPE = FrostRenderType.DARK_OUTLINE.apply(TextureAtlas.LOCATION_BLOCKS, false);

    public FrostRenderType(String p_173178_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static VertexConsumer getAurora(MultiBufferSource bufferSource, RenderType renderType) {
        return VertexMultiConsumer.create(VertexMultiConsumer.create(bufferSource.getBuffer(FrostRenderType.AURORA_GLINT)), bufferSource.getBuffer(renderType));
    }

    public static VertexConsumer getDark(MultiBufferSource bufferSource, RenderType renderType) {
        return VertexMultiConsumer.create(VertexMultiConsumer.create(bufferSource.getBuffer(DARK_ITEM_RENDER_TYPE)), bufferSource.getBuffer(renderType));
    }
}
