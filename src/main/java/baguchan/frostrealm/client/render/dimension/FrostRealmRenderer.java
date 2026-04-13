package baguchan.frostrealm.client.render.dimension;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.sounds.FrostAmbientSoundsHandler;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class FrostRealmRenderer  {
    public static final Identifier ORB_LOCATION = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "frost_orb");
    private static final Identifier AURORA_LOCATION = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "aurora");

    private final FrostAmbientSoundsHandler soundsHandler;
    private final GpuBuffer auroraBuffer;
    private final GpuBuffer orbBuffer;
    private final RenderSystem.AutoStorageIndexBuffer quadIndices;
    private final TextureAtlas celestialsAtlas;

    public static ContextKey<Float> NORMAL_WEATHER_LEVEL_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "normal_weather_level"));


    public FrostRealmRenderer(AtlasManager p_455011_) {
        soundsHandler = new FrostAmbientSoundsHandler(Minecraft.getInstance().getSoundManager());
        this.quadIndices = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
        this.celestialsAtlas = p_455011_.getAtlasOrThrow(AtlasIds.CELESTIALS);
        this.orbBuffer = buildOrbQuad(this.celestialsAtlas);
        this.auroraBuffer = buildAuroraQuad(this.celestialsAtlas);
    }

    private static GpuBuffer buildCelestialQuad(String p_454894_, TextureAtlasSprite p_456200_) {
        VertexFormat vertexformat = DefaultVertexFormat.POSITION_TEX;

        GpuBuffer gpubuffer;
        try (ByteBufferBuilder bytebufferbuilder = ByteBufferBuilder.exactlySized(4 * vertexformat.getVertexSize())) {
            BufferBuilder bufferbuilder = new BufferBuilder(bytebufferbuilder, VertexFormat.Mode.QUADS, vertexformat);
            bufferbuilder.addVertex(-1.0F, 0.0F, -1.0F).setUv(p_456200_.getU0(), p_456200_.getV0());
            bufferbuilder.addVertex(1.0F, 0.0F, -1.0F).setUv(p_456200_.getU1(), p_456200_.getV0());
            bufferbuilder.addVertex(1.0F, 0.0F, 1.0F).setUv(p_456200_.getU1(), p_456200_.getV1());
            bufferbuilder.addVertex(-1.0F, 0.0F, 1.0F).setUv(p_456200_.getU0(), p_456200_.getV1());

            try (MeshData meshdata = bufferbuilder.buildOrThrow()) {
                gpubuffer = RenderSystem.getDevice().createBuffer(() -> p_454894_, 32, meshdata.vertexBuffer());
            }
        }

        return gpubuffer;
    }

    private static GpuBuffer buildOrbQuad(TextureAtlas p_455519_) {
        return buildCelestialQuad("Orb quad", p_455519_.getSprite(ORB_LOCATION));
    }

    private static GpuBuffer buildAuroraQuad(TextureAtlas p_455519_) {
        return buildCelestialQuad("Aurora quad", p_455519_.getSprite(AURORA_LOCATION));
    }

    public FrostAmbientSoundsHandler getSoundsHandler() {
        return soundsHandler;
    }

    /*@Override
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
    }*/



    public void renderOrb(float p_362331_, PoseStack p_361665_) {
        Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
        matrix4fstack.pushMatrix();
        matrix4fstack.mul(p_361665_.last().pose());
        matrix4fstack.translate(0.0F, 100.0F, 0.0F);
        matrix4fstack.scale(30.0F, 1.0F, 30.0F);
        GpuBufferSlice gpubufferslice = RenderSystem.getDynamicUniforms()
                .writeTransform(matrix4fstack, new Vector4f(1.0F, 1.0F, 1.0F, p_362331_), new Vector3f(), new Matrix4f());
        GpuTextureView gputextureview = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
        GpuTextureView gputextureview1 = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();
        GpuBuffer gpubuffer = this.quadIndices.getBuffer(6);

        try (RenderPass renderpass = RenderSystem.getDevice()
                .createCommandEncoder()
                .createRenderPass(() -> "Sky Orb", gputextureview, OptionalInt.empty(), gputextureview1, OptionalDouble.empty())) {
            renderpass.setPipeline(RenderPipelines.CELESTIAL);
            RenderSystem.bindDefaultUniforms(renderpass);
            renderpass.setUniform("DynamicTransforms", gpubufferslice);
            renderpass.bindTexture("Sampler0", this.celestialsAtlas.getTextureView(), this.celestialsAtlas.getSampler());
            renderpass.setVertexBuffer(0, this.orbBuffer);
            renderpass.setIndexBuffer(gpubuffer, this.quadIndices.type());
            renderpass.drawIndexed(0, 0, 6, 1);
        }

        matrix4fstack.popMatrix();
    }

    public void renderAurora(PoseStack p_362809_, float weatherLevel) {
            if (!(weatherLevel <= 0.001F)) {
                float f11 = (weatherLevel);
                p_362809_.pushPose();
                Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
                matrix4fstack.pushMatrix();
                matrix4fstack.mul(p_362809_.last().pose());
                matrix4fstack.translate(0.0F, 100.0F, 0.0F);
                matrix4fstack.scale(5.0F, 1.0F, 5.0F);
                GpuBufferSlice gpubufferslice = RenderSystem.getDynamicUniforms()
                        .writeTransform(matrix4fstack, new Vector4f(1F, 1F, 1F, weatherLevel), new Vector3f(), new Matrix4f());
                GpuTextureView gputextureview = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
                GpuTextureView gputextureview1 = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();
                GpuBuffer gpubuffer = this.quadIndices.getBuffer(6);

                try (RenderPass renderpass = RenderSystem.getDevice()
                        .createCommandEncoder()
                        .createRenderPass(() -> "Aurora", gputextureview, OptionalInt.empty(), gputextureview1, OptionalDouble.empty())) {
                    renderpass.setPipeline(RenderPipelines.CELESTIAL);
                    RenderSystem.bindDefaultUniforms(renderpass);
                    renderpass.setUniform("DynamicTransforms", gpubufferslice);
                    renderpass.bindTexture("Sampler0", this.celestialsAtlas.getTextureView(), this.celestialsAtlas.getSampler());
                    renderpass.setVertexBuffer(0, this.auroraBuffer);
                    renderpass.setIndexBuffer(gpubuffer, this.quadIndices.type());
                    renderpass.drawIndexed(0, 0, 6, 1);
                }

                matrix4fstack.popMatrix();
                p_362809_.popPose();
            }
    }
}