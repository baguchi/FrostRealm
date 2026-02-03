package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import static net.minecraft.client.renderer.RenderPipelines.*;

public class FrostRenderPipelines {
    public static final RenderPipeline.Snippet DARK_OUTLINE_SNIPPET = RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
            .withVertexShader(FrostRealm.prefix("core/dark_outline"))
            .withFragmentShader(FrostRealm.prefix("core/dark_outline"))
            .withSampler("Sampler0")
            .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .buildSnippet();
    public static final RenderPipeline DARK_OUTLINE_CULL = RenderPipeline.builder(new RenderPipeline.Snippet[]{DARK_OUTLINE_SNIPPET}).withLocation(FrostRealm.prefix("pipeline/dark_outline_cull")).build();
    public static final RenderPipeline DARK_OUTLINE_NO_CULL = RenderPipeline.builder(new RenderPipeline.Snippet[]{DARK_OUTLINE_SNIPPET}).withLocation(FrostRealm.prefix("pipeline/dark_outline_no_cull")).withCull(false).build();

    public static final RenderPipeline ENTITY_GLOW_SHADOW =
            RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                    .withLocation(FrostRealm.prefix("pipeline/entity_glow_shadow"))
                    .withVertexShader("core/rendertype_entity_shadow")
                    .withFragmentShader("core/rendertype_entity_shadow")
                    .withShaderDefine("EMISSIVE")
                    .withSampler("Sampler0")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withDepthWrite(false)
                    .withVertexFormat(DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS)
                    .build();

    public static final RenderPipeline AURORA_GLINT =
            RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                    .withLocation(FrostRealm.prefix("pipeline/aurora_glint"))
                    .withVertexShader("core/glint")
                    .withFragmentShader("core/glint")
            .withSampler("Sampler0")
            .withDepthWrite(false)
                    .withCull(false)
                    .withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST)
                    .withBlend(BlendFunction.GLINT)
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
                    .build();

}
