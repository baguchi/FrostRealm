package baguchan.frostrealm.client.render.dimension;

import baguchan.frostrealm.client.FrostRealmTextureManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.util.ARGB;
import net.neoforged.neoforge.client.CustomSkyboxRenderer;
import org.joml.Matrix4fc;

public class FrostRealmSkySpecialRender implements CustomSkyboxRenderer {

    @Override
    public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4fc modelViewMatrix, Runnable setupFog) {
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
