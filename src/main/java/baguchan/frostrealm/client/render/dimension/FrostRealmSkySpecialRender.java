package baguchan.frostrealm.client.render.dimension;

import baguchan.frostrealm.client.FrostRealmTextureManager;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.util.ARGB;
import net.neoforged.neoforge.client.CustomSkyboxRenderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class FrostRealmSkySpecialRender implements CustomSkyboxRenderer {
    @Override
    public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4f modelViewMatrix, Runnable setupFog) {
        FrostRealmRenderer frostrealmRenderer = FrostRealmTextureManager.INSTANCE.getFrostrealmRenderer();

        PoseStack poseStack = new PoseStack();
        setupFog.run();

        float f = ARGB.redFloat(skyRenderState.skyColor);
        float f1 = ARGB.greenFloat(skyRenderState.skyColor);
        float f2 = ARGB.blueFloat(skyRenderState.skyColor);
        if (frostrealmRenderer != null) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(skyRenderState.sunAngle));
            frostrealmRenderer.renderOrb(levelRenderState.getRenderDataOrDefault(FrostRealmRenderer.NORMAL_WEATHER_LEVEL_KEY, 0.0F), poseStack);
            poseStack.popPose();

            poseStack.pushPose();
            frostrealmRenderer.renderAurora(poseStack, levelRenderState.getRenderDataOrDefault(FrostRealmRenderer.NORMAL_WEATHER_LEVEL_KEY, 0.0F));
            poseStack.popPose();
        }
        return true;
    }
}
