package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.sounds.FrostAmbientSoundsHandler;
import baguchan.frostrealm.registry.FrostParticleTypes;
import baguchan.frostrealm.registry.FrostWeathers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
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
        VertexConsumer vertexconsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.celestial(AURORA_LOCATION));
        p_109781_.pushPose();
        float f11 = (1.0F - weatherLevel);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
        p_109781_.pushPose();
        Matrix4f matrix4f1 = p_109781_.last().pose();
        float f12 = 160.0F;
        float f13 = (float) (100.0F);
        float u1 = 0;
        float v1 = 0;
        float u2 = 1F;
        float v2 = 1F;
        vertexconsumer.addVertex(matrix4f1, -f12, (float) f13, -f12).setUv(u1, v1);
        vertexconsumer.addVertex(matrix4f1, f12, (float) f13, -f12).setUv(u2, v1);
        vertexconsumer.addVertex(matrix4f1, f12, (float) f13, f12).setUv(u2, v2);
        vertexconsumer.addVertex(matrix4f1, -f12, (float) f13, f12).setUv(u1, v2);
        p_109781_.popPose();
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
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