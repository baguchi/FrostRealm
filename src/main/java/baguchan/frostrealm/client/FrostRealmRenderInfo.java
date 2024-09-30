package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.sounds.FrostAmbientSoundsHandler;
import baguchan.frostrealm.registry.FrostParticleTypes;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
    private static final ResourceLocation AURORA_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png");
    private final FrostAmbientSoundsHandler soundsHandler;

    public FrostRealmRenderInfo(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
        soundsHandler = new FrostAmbientSoundsHandler(Minecraft.getInstance().getSoundManager());
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
        return biomeFogColor;
    }

    @Override
    public float[] getSunriseColor(float p_108872_, float p_108873_) {
        return null;
    }

    @Override
    public boolean isFoggyAt(int p_108874_, int p_108875_) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.mulPose(modelViewMatrix);
        renderAurora(poseStack, FrostWeatherManager.getWeatherLevel(partialTick));
        poseStack.popPose();
        return false;
    }

    private void renderAurora(PoseStack p_109781_, float weatherLevel) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        p_109781_.pushPose();
        float f11 = (1.0F - weatherLevel);
        FogRenderer.levelFogColor();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);

        Matrix4f matrix4f1 = p_109781_.last().pose();
        float f12 = 160.0F;
        float f13 = (float) (100.0F);
        float u1 = 0;
        float v1 = 0;
        float u2 = 1F;
        float v2 = 1F;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, AURORA_LOCATION);
        bufferbuilder.addVertex(matrix4f1, -f12, (float) f13, -f12).setUv(u1, v1);
        bufferbuilder.addVertex(matrix4f1, f12, (float) f13, -f12).setUv(u2, v1);
        bufferbuilder.addVertex(matrix4f1, f12, (float) f13, f12).setUv(u2, v2);
        bufferbuilder.addVertex(matrix4f1, -f12, (float) f13, f12).setUv(u1, v2);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        p_109781_.popPose();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
        return true;
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        float f = FrostWeatherManager.getWeatherLevel(partialTick);
        if (!(f <= 0.0F)) {
            if (level.random.nextFloat() < 0.1F) {
                float x = level.getRandom().nextFloat() * 0.5F - level.getRandom().nextFloat();
                float y = level.getRandom().nextFloat();
                float z = level.getRandom().nextFloat() * 0.5F - level.getRandom().nextFloat();
                level.addParticle(FrostParticleTypes.SNOW.get(), camX + x * 16F, camY + y * 16, camZ + z * 16, 0F, -0.01F, 0F);
            }
        }
        return false;
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        soundsHandler.tick();


        return true;
    }
}