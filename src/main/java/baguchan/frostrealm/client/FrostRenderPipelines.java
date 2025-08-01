package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
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


    public static final RenderPipeline.Snippet MYSTIC_SNIPPET = RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
            .withVertexShader(FrostRealm.prefix("core/mystic"))
            .withFragmentShader(FrostRealm.prefix("core/mystic"))
            .withSampler("Sampler0")
            .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .buildSnippet();
    public static final RenderPipeline MYSTIC_CULL = RenderPipeline.builder(new RenderPipeline.Snippet[]{MYSTIC_SNIPPET}).withLocation(FrostRealm.prefix("pipeline/mystic_cull")).build();
    public static final RenderPipeline MYSTIC_NO_CULL = RenderPipeline.builder(new RenderPipeline.Snippet[]{MYSTIC_SNIPPET}).withLocation(FrostRealm.prefix("pipeline/mystic_no_cull")).withCull(false).build();

}
