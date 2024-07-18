package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.sounds.FrostAmbientSoundsHandler;
import baguchan.frostrealm.registry.FrostWeathers;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import static net.minecraft.client.renderer.LevelRenderer.getLightColor;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
    private static final ResourceLocation AURORA_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/environment/aurora.png");
    private static final ResourceLocation SNOW_TEXTURES = ResourceLocation.withDefaultNamespace("textures/environment/snow.png");
    private final float[] rainxs = new float[1024];
    private final float[] rainzs = new float[1024];
    private final FrostAmbientSoundsHandler soundsHandler;

    public FrostRealmRenderInfo(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
        soundsHandler = new FrostAmbientSoundsHandler(Minecraft.getInstance().getSoundManager());
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                float f = j - 16;
                float f1 = i - 16;
                float f2 = Mth.sqrt(f * f + f1 * f1);
                this.rainxs[i << 5 | j] = -f1 / f2;
                this.rainzs[i << 5 | j] = f / f2;
            }
        }
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
        if (FrostWeatherManager.getPrevFrostWeather() == FrostWeathers.BLIZZARD.get() && FrostWeatherManager.getFrostWeather() == FrostWeathers.NOPE.get() || FrostWeatherManager.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
            this.renderBrizzardWeather(lightTexture, level, ticks, partialTick, camX, camY, camZ);
        }


        return false;
    }

    private void renderBrizzardWeather(LightTexture lightTexture, ClientLevel level, int ticks, float partialTick, double camX, double camY, double camZ) {
        float f = FrostWeatherManager.getWeatherLevel(partialTick);
        if (!(f <= 0.0F)) {
            lightTexture.turnOnLightLayer();
            int i = Mth.floor(camX);
            int j = Mth.floor(camY);
            int k = Mth.floor(camZ);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = null;
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (Minecraft.useFancyGraphics()) {
                l = 10;
            }

            RenderSystem.depthMask(Minecraft.useShaderTransparency());
            int i1 = -1;
            float combinedTicks = (float) ticks + partialTick;
            RenderSystem.setShader(GameRenderer::getParticleShader);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int j1 = k - l; j1 <= k + l; j1++) {
                for (int k1 = i - l; k1 <= i + l; k1++) {
                    int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
                    double d0 = (double) this.rainxs[l1] * 0.5;
                    double d1 = (double) this.rainzs[l1] * 0.5;
                    blockpos$mutableblockpos.set((double) k1, camY, (double) j1);
                    Holder<Biome> biome = level.getBiome(blockpos$mutableblockpos);
                    if (FrostWeatherManager.getFrostWeather().getNonAffectableBiome().isEmpty() || !biome.is(FrostWeatherManager.getFrostWeather().getNonAffectableBiome().get())) {
                        int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
                        int j2 = j - l;
                        int k2 = j + l;
                        if (j2 < i2) {
                            j2 = i2;
                        }

                        if (k2 < i2) {
                            k2 = i2;
                        }

                        int l2 = i2;
                        if (i2 < j) {
                            l2 = j;
                        }

                        if (j2 != k2) {
                            RandomSource randomsource = RandomSource.create((long) (k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
                            blockpos$mutableblockpos.set(k1, j2, j1);

                            if (i1 != 0) {
                                if (i1 >= 0) {
                                    BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                                }

                                i1 = 0;
                                RenderSystem.setShaderTexture(0, SNOW_TEXTURES);
                                bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                            }

                            float speed = 5F;
                            float f8 = ((float) (ticks & 511) + partialTick) / 512.0F;
                            float uFactor = randomsource.nextFloat() + combinedTicks * 0.1F * (float) randomsource.nextGaussian();
                            float vFactor = randomsource.nextFloat() * speed + combinedTicks * 0.005F * (float) randomsource.nextGaussian();
                            double d4 = (double) k1 + 0.5 - camX;
                            double d5 = (double) j1 + 0.5 - camZ;
                            float f11 = (float) Math.sqrt(d4 * d4 + d5 * d5) / (float) l;
                            float f5 = ((1.0F - f11 * f11) * 0.3F + 0.5F) * f;
                            blockpos$mutableblockpos.set(k1, l2, j1);
                            int j4 = getLightColor(level, blockpos$mutableblockpos);
                            int k4 = j4 >> 16 & 65535;
                            int l4 = j4 & 65535;
                            int l3 = (k4 * 3 + 240) / 4;
                            int i4 = (l4 * 3 + 240) / 4;
                            bufferbuilder.addVertex(
                                            (float) ((double) k1 - camX - d0 + 0.5), (float) ((double) k2 - camY), (float) ((double) j1 - camZ - d1 + 0.5)
                                    )
                                    .setUv(0.0F + uFactor, (float) j2 * 0.25F + f8 + vFactor)
                                    .setColor(1.0F, 1.0F, 1.0F, f5)
                                    .setUv2(i4, l3);
                            bufferbuilder.addVertex(
                                            (float) ((double) k1 - camX + d0 + 0.5), (float) ((double) k2 - camY), (float) ((double) j1 - camZ + d1 + 0.5)
                                    )
                                    .setUv(1.0F + uFactor, (float) j2 * 0.25F + f8 + vFactor)
                                    .setColor(1.0F, 1.0F, 1.0F, f5)
                                    .setUv2(i4, l3);
                            bufferbuilder.addVertex(
                                            (float) ((double) k1 - camX + d0 + 0.5), (float) ((double) j2 - camY), (float) ((double) j1 - camZ + d1 + 0.5)
                                    )
                                    .setUv(1.0F + uFactor, (float) k2 * 0.25F + f8 + vFactor)
                                    .setColor(1.0F, 1.0F, 1.0F, f5)
                                    .setUv2(i4, l3);
                            bufferbuilder.addVertex(
                                            (float) ((double) k1 - camX - d0 + 0.5), (float) ((double) j2 - camY), (float) ((double) j1 - camZ - d1 + 0.5)
                                    )
                                    .setUv(0.0F + uFactor, (float) k2 * 0.25F + f8 + vFactor)
                                    .setColor(1.0F, 1.0F, 1.0F, f5)
                                    .setUv2(i4, l3);

                        }
                    }
                }
            }

            if (i1 >= 0) {
                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            lightTexture.turnOffLightLayer();
        }
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        soundsHandler.tick();
        return true;
    }
}