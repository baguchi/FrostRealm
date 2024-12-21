package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.sounds.FrostAmbientSoundsHandler;
import baguchan.frostrealm.registry.FrostParticleTypes;
import baguchan.frostrealm.registry.FrostWeathers;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
    private static final ResourceLocation AURORA_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png");
    private static final ResourceLocation ORB_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/frost_orb.png");
    private static final ResourceLocation END_SKY_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/end_sky.png");

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
    public boolean isFoggyAt(int p_108874_, int p_108875_) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, Runnable setupFog) {
        setupFog.run();
        RenderStateShard.MAIN_TARGET.setupRenderState();
        float f = level.getSunAngle(partialTick);
        float f1 = level.getTimeOfDay(partialTick);
        float f2 = 1.0F - level.getRainLevel(partialTick);
        float f3 = level.getStarBrightness(partialTick) * f2;
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.pushPose();
        //poseStack.mulPose(modelViewMatrix);

        renderAurora(poseStack, FrostWeatherManager.getWeatherLevel(partialTick));
        float f5 = FrostWeatherManager.getWeatherLevel(1.0F);
        if (!(f5 <= 0.0F) && FrostWeatherManager.getFrostWeather() == FrostWeathers.PURPLE_FOG.get()) {
            renderEndSky(poseStack, f5);
        }
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(f1 * 360.0F));
        renderOrb(1.0F - FrostWeatherManager.getWeatherLevel(partialTick), Tesselator.getInstance(), poseStack);
        poseStack.popPose();
        poseStack.popPose();
        return true;
    }

    private void renderOrb(float p_362331_, Tesselator p_361695_, PoseStack p_361665_) {
        float f = 30.0F;
        float f1 = 100.0F;
        int i = ARGB.white(p_362331_);
        Matrix4f matrix4f = p_361665_.last().pose();
        VertexConsumer vertexconsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.celestial(ORB_LOCATION));
        vertexconsumer.addVertex(matrix4f, -30.0F, 100.0F, -30.0F).setUv(0.0F, 0.0F).setColor(i);
        vertexconsumer.addVertex(matrix4f, 30.0F, 100.0F, -30.0F).setUv(1.0F, 0.0F).setColor(i);
        vertexconsumer.addVertex(matrix4f, 30.0F, 100.0F, 30.0F).setUv(1.0F, 1.0F).setColor(i);
        vertexconsumer.addVertex(matrix4f, -30.0F, 100.0F, 30.0F).setUv(0.0F, 1.0F).setColor(i);
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
    }

    private void renderAurora(PoseStack p_109781_, float weatherLevel) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        p_109781_.pushPose();
        float f11 = (1.0F - weatherLevel);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);

        Matrix4f matrix4f1 = p_109781_.last().pose();
        float f12 = 160.0F;
        float f13 = (float) (100.0F);
        float u1 = 0;
        float v1 = 0;
        float u2 = 1F;
        float v2 = 1F;

        RenderSystem.setShader(CoreShaders.POSITION_TEX);
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

    public void renderEndSky(PoseStack p_361681_, float weatherLevel) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        //RenderSystem.overlayBlendFunc();
        RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, weatherLevel);
        Tesselator tesselator = Tesselator.getInstance();

        for (int i = 0; i < 6; i++) {
            p_361681_.pushPose();
            if (i == 1) {
                p_361681_.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                p_361681_.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                p_361681_.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                p_361681_.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                p_361681_.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = p_361681_.last().pose();
            BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(-14145496);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(-14145496);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(-14145496);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(-14145496);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            p_361681_.popPose();
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
        return true;
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        soundsHandler.tick();
        float f = FrostWeatherManager.getWeatherLevel(1.0F);
        if (!(f <= 0.0F) && FrostWeatherManager.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
            for (int i = 0; i < 2; i++) {
                if (level.random.nextInt(2) != 0) {
                    float x = level.getRandom().nextFloat() * 0.5F - level.getRandom().nextFloat();
                    float y = level.getRandom().nextFloat();
                    float z = level.getRandom().nextFloat() * 0.5F - level.getRandom().nextFloat();
                    level.addParticle(FrostParticleTypes.SNOW.get(), camera.getPosition().x - x * 36F, camera.getPosition().y + 8 + y * 16, camera.getPosition().z - z * 36, -0.2F, -0.5F, -0.2F);
                }
            }
        }

        return true;
    }
}