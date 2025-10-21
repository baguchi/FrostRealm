package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.sounds.FrostAmbientSoundsHandler;
import baguchan.frostrealm.registry.FrostParticleTypes;
import baguchan.frostrealm.registry.FrostWeathers;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.client.renderer.state.WeatherRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
    private static final ResourceLocation AURORA_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png");
    public static final ResourceLocation ORB_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/frost_orb.png");
    private static final ResourceLocation END_SKY_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/end_sky.png");

    private final FrostAmbientSoundsHandler soundsHandler;

    private final GpuBuffer auroraBuffer;
    private final GpuBuffer orbBuffer;
    private final RenderSystem.AutoStorageIndexBuffer quadIndices = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
    @Nullable
    private AbstractTexture orbTexture;
    @Nullable
    private AbstractTexture auroraTexture;
    public static ContextKey<Float> NORMAL_WEATHER_LEVEL_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "normal_weather_level"));


    public FrostRealmRenderInfo(SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(fogType, brightenLightMap, entityLightingBottomsLit);
        soundsHandler = new FrostAmbientSoundsHandler(Minecraft.getInstance().getSoundManager());
        this.auroraBuffer = this.buildAurora();
        this.orbBuffer = this.buildOrbQuad();
        this.initTextures();
    }

    protected void initTextures() {
        this.orbTexture = this.getTexture(ORB_LOCATION);
        this.auroraTexture = this.getTexture(AURORA_LOCATION);
    }

    private AbstractTexture getTexture(ResourceLocation p_449048_) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(p_449048_);
        abstracttexture.setUseMipmaps(false);
        return abstracttexture;
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
    public boolean renderClouds(LevelRenderState levelRenderState, Vec3 camPos, CloudStatus cloudStatus, int cloudColor, float cloudHeight, Matrix4f modelViewMatrix) {
        return true;
    }

    @Override
    public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4f modelViewMatrix, Runnable setupFog) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        float f4 = Mth.sin(levelRenderState.skyRenderState.sunAngle) < 0.0F ? 180.0F : 0.0F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(f4 + 90.0F));
        renderOrb(levelRenderState.getRenderDataOrDefault(FrostRealmRenderInfo.NORMAL_WEATHER_LEVEL_KEY, 0.0F), poseStack);
        poseStack.popPose();
        setupFog.run();

        poseStack.pushPose();

        renderAurora(poseStack, levelRenderState.getRenderDataOrDefault(FrostRealmRenderInfo.NORMAL_WEATHER_LEVEL_KEY, 0.0F));

        poseStack.popPose();
        return true;
    }


    private void renderOrb(float p_362331_, PoseStack p_361665_) {
        if (this.orbTexture != null) {
            Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
            matrix4fstack.pushMatrix();
            matrix4fstack.mul(p_361665_.last().pose());
            matrix4fstack.translate(0.0F, 100.0F, 0.0F);
            matrix4fstack.scale(30.0F, 1.0F, 30.0F);
            GpuBufferSlice gpubufferslice = RenderSystem.getDynamicUniforms()
                    .writeTransform(matrix4fstack, new Vector4f(1.0F, 1.0F, 1.0F, p_362331_), new Vector3f(), new Matrix4f(), 0.0F);
            GpuTextureView gputextureview = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
            GpuTextureView gputextureview1 = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();
            GpuBuffer gpubuffer = this.quadIndices.getBuffer(6);

            try (RenderPass renderpass = RenderSystem.getDevice()
                    .createCommandEncoder()
                    .createRenderPass(() -> "Sky Orb", gputextureview, OptionalInt.empty(), gputextureview1, OptionalDouble.empty())) {
                renderpass.setPipeline(RenderPipelines.CELESTIAL);
                RenderSystem.bindDefaultUniforms(renderpass);
                renderpass.setUniform("DynamicTransforms", gpubufferslice);
                renderpass.bindSampler("Sampler0", this.orbTexture.getTextureView());
                renderpass.setVertexBuffer(0, this.orbBuffer);
                renderpass.setIndexBuffer(gpubuffer, this.quadIndices.type());
                renderpass.drawIndexed(0, 0, 6, 1);
            }

            matrix4fstack.popMatrix();
        }
    }

    public void renderAurora(PoseStack p_362809_, float weatherLevel) {
        if (this.auroraTexture != null) {
            if (!(weatherLevel <= 0.001F)) {
                float f11 = (weatherLevel);
                p_362809_.pushPose();
                Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
                matrix4fstack.pushMatrix();
                matrix4fstack.mul(p_362809_.last().pose());
                matrix4fstack.translate(0.0F, 100.0F, 0.0F);
                matrix4fstack.scale(5.0F, 1.0F, 5.0F);
                GpuBufferSlice gpubufferslice = RenderSystem.getDynamicUniforms()
                        .writeTransform(matrix4fstack, new Vector4f(1F, 1F, 1F, weatherLevel), new Vector3f(), new Matrix4f(), 0.0F);
                GpuTextureView gputextureview = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
                GpuTextureView gputextureview1 = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();
                GpuBuffer gpubuffer = this.quadIndices.getBuffer(6);

                try (RenderPass renderpass = RenderSystem.getDevice()
                        .createCommandEncoder()
                        .createRenderPass(() -> "Aurora", gputextureview, OptionalInt.empty(), gputextureview1, OptionalDouble.empty())) {
                    renderpass.setPipeline(RenderPipelines.CELESTIAL);
                    RenderSystem.bindDefaultUniforms(renderpass);
                    renderpass.setUniform("DynamicTransforms", gpubufferslice);
                    renderpass.bindSampler("Sampler0", this.auroraTexture.getTextureView());
                    renderpass.setVertexBuffer(0, this.auroraBuffer);
                    renderpass.setIndexBuffer(gpubuffer, this.quadIndices.type());
                    renderpass.drawIndexed(0, 0, 6, 1);
                }

                matrix4fstack.popMatrix();
                p_362809_.popPose();
            }
        }
    }

    private GpuBuffer buildOrbQuad() {
        GpuBuffer gpubuffer;
        try (ByteBufferBuilder bytebufferbuilder = ByteBufferBuilder.exactlySized(4 * DefaultVertexFormat.POSITION_TEX.getVertexSize())) {
            BufferBuilder bufferbuilder = new BufferBuilder(bytebufferbuilder, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f matrix4f = new Matrix4f();
            bufferbuilder.addVertex(matrix4f, -1.0F, 0.0F, -1.0F).setUv(0.0F, 0.0F);
            bufferbuilder.addVertex(matrix4f, 1.0F, 0.0F, -1.0F).setUv(1.0F, 0.0F);
            bufferbuilder.addVertex(matrix4f, 1.0F, 0.0F, 1.0F).setUv(1.0F, 1.0F);
            bufferbuilder.addVertex(matrix4f, -1.0F, 0.0F, 1.0F).setUv(0.0F, 1.0F);

            try (MeshData meshdata = bufferbuilder.buildOrThrow()) {
                gpubuffer = RenderSystem.getDevice().createBuffer(() -> "Orb quad", 40, meshdata.vertexBuffer());
            }
        }

        return gpubuffer;
    }

    private GpuBuffer buildAurora() {
        GpuBuffer gpubuffer;
        try (ByteBufferBuilder bytebufferbuilder = ByteBufferBuilder.exactlySized(4 * DefaultVertexFormat.POSITION_TEX.getVertexSize())) {
            BufferBuilder bufferbuilder = new BufferBuilder(bytebufferbuilder, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f matrix4f = new Matrix4f();

            float f12 = 160.0F;
            float f13 = (float) (100.0F);
            bufferbuilder.addVertex(matrix4f, -f12, (float) f13, -f12).setUv(0.0F, 0.0F);
            bufferbuilder.addVertex(matrix4f, f12, (float) f13, -f12).setUv(1.0F, 0.0F);
            bufferbuilder.addVertex(matrix4f,  f12, (float) f13, f12).setUv(1.0F, 1.0F);
            bufferbuilder.addVertex(matrix4f, -f12, (float) f13, f12).setUv(0.0F, 1.0F);

            try (MeshData meshdata = bufferbuilder.buildOrThrow()) {
                gpubuffer = RenderSystem.getDevice().createBuffer(() -> "Aurora quad", 40, meshdata.vertexBuffer());
            }
        }

        return gpubuffer;
    }


    @Override
    public boolean renderSnowAndRain(LevelRenderState levelRenderState, WeatherRenderState weatherRenderState, MultiBufferSource bufferSource, Vec3 camPos) {
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
                    if (level.canSeeSky(BlockPos.containing(new Vec3((float) (camera.getPosition().x - x * 36F), (float) (camera.getPosition().y + 8 + y * 16), (float) (camera.getPosition().z - z * 36))))) {
                        level.addParticle(FrostParticleTypes.SNOW.get(), camera.getPosition().x - x * 36F, camera.getPosition().y + 8 + y * 16, camera.getPosition().z - z * 36, -0.2F, -0.5F, -0.2F);
                    }
                }
            }
        }

        return true;
    }
}