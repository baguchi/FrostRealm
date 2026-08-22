package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import static net.minecraft.client.renderer.RenderPipelines.*;

public class FrostRenderPipelines {
    public static final RenderPipeline.Snippet GLOW_OUTLINE_SNIPPET = RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
            .withVertexShader(FrostRealm.prefix("core/glow_outline"))
            .withFragmentShader(FrostRealm.prefix("core/glow_outline"))
            .withSampler("Sampler0")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withDepthStencilState(DepthStencilState.DEFAULT).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .buildSnippet();
    public static final RenderPipeline GLOW_OUTLINE_CULL = RenderPipeline.builder(GLOW_OUTLINE_SNIPPET).withLocation(FrostRealm.prefix("pipeline/glow_outline_cull")).build();
    public static final RenderPipeline GLOW_OUTLINE_NO_CULL = RenderPipeline.builder(GLOW_OUTLINE_SNIPPET).withLocation(FrostRealm.prefix("pipeline/glow_outline_no_cull")).withCull(false).build();

    public static final RenderPipeline ENTITY_GLOW_SHADOW =
            RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                    .withLocation(FrostRealm.prefix("pipeline/entity_glow_shadow"))
                    .withVertexShader("core/rendertype_entity_shadow")
                    .withFragmentShader("core/rendertype_entity_shadow")
                    .withShaderDefine("EMISSIVE")
                    .withSampler("Sampler0")
                    .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withVertexFormat(DefaultVertexFormat.ENTITY, VertexFormat.Mode.QUADS)
                    .withDepthStencilState(DepthStencilState.DEFAULT)
                    .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
                    .build();

    public static final RenderPipeline AURORA_GLINT =
            RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                    .withLocation(FrostRealm.prefix("pipeline/aurora_glint"))
                    .withVertexShader("core/glint")
                    .withFragmentShader("core/glint")
                    .withSampler("Sampler0")
                    .withCull(false)
                    .withColorTargetState(new ColorTargetState(BlendFunction.GLINT))
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
                    .withDepthStencilState(new DepthStencilState(CompareOp.EQUAL, false))
                    .build();

}
