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
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Random;

;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
    private static final ResourceLocation AURORA_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/environment/aurora.png");
	private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
    private final float[] rainxs = new float[1024];
    private final float[] rainzs = new float[1024];
    private int rendererUpdateCount;
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
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
		renderAurora(poseStack, FrostWeatherManager.getWeatherLevel(partialTick), partialTick, ticks, Minecraft.getInstance());

		return true;
	}

	private void renderAurora(PoseStack p_109781_, float weatherLevel, float partialTick, int ticks, Minecraft minecraft) {
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
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

        RenderSystem.setShader(FrostShaders::getRenderTypeAuroraShader);
		RenderSystem.setShaderTexture(0, AURORA_LOCATION);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix4f1, -f12, (float) f13, -f12).uv(u1, v1).endVertex();
		bufferbuilder.vertex(matrix4f1, f12, (float) f13, -f12).uv(u2, v1).endVertex();
		bufferbuilder.vertex(matrix4f1, f12, (float) f13, f12).uv(u2, v2).endVertex();
		bufferbuilder.vertex(matrix4f1, -f12, (float) f13, f12).uv(u1, v2).endVertex();
		BufferUploader.drawWithShader(bufferbuilder.end());
		p_109781_.popPose();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.enableCull();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
		return true;
	}

	@Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
		if (FrostWeatherManager.getPrevFrostWeather() == FrostWeathers.BLIZZARD.get()) {
			this.renderBrizzardWeather(lightTexture, level, Minecraft.getInstance(), partialTick, camX, camY, camZ);
		}

        return true;
    }

	private void renderBrizzardWeather(LightTexture lightmap, ClientLevel world, Minecraft mc, float ticks, double x, double y, double z) {
        ++this.rendererUpdateCount;
		float f = FrostWeatherManager.getWeatherLevel(ticks);
        if (!(f <= 0.0F)) {
            lightmap.turnOnLightLayer();
            Level level = Minecraft.getInstance().level;
            int i = Mth.floor(x);
            int j = Mth.floor(y);
            int k = Mth.floor(z);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			int l = 5;
			if (Minecraft.useFancyGraphics()) {
				l = 10;
			}

			RenderSystem.depthMask(Minecraft.useShaderTransparency());
			int i1 = -1;
			float f1 = (float) rendererUpdateCount + ticks;
			RenderSystem.setShader(GameRenderer::getParticleShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int j1 = k - l; j1 <= k + l; ++j1) {
				for (int k1 = i - l; k1 <= i + l; ++k1) {
					int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
					double d0 = (double) this.rainxs[l1] * 0.5D;
					double d1 = (double) this.rainzs[l1] * 0.5D;
					blockpos$mutableblockpos.set(k1, 0, j1);

					int i2 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos).getY();
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
						Random random = new Random((long) k1 * k1 * 3121 + k1 * 45238971L ^ (long) j1 * j1 * 418711 + j1 * 13761L);
						blockpos$mutableblockpos.set(k1, j2, j1);
						if (i1 != 1) {
							if (i1 >= 0) {
								tesselator.end();
							}

							i1 = 1;
							RenderSystem.setShaderTexture(0, SNOW_TEXTURES);
							bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
						}

						float f6 = -((float) (rendererUpdateCount & 511) + ticks) / 324.0F;
						float f7 = (float) (random.nextDouble() + (double) f1 * 0.01D * (double) ((float) random.nextGaussian()));
						float f8 = (float) (random.nextDouble() + (double) (f1 * (float) random.nextGaussian()) * 0.001D);
						double d3 = (double) k1 + 0.5D - x;
						double d5 = (double) j1 + 0.5D - z;
						float f9 = (float) Math.sqrt(d3 * d3 + d5 * d5) / (float) l;
						float f10 = ((1.0F - f9 * f9) * 0.3F + 0.5F) * f;
						blockpos$mutableblockpos.set(k1, l2, j1);
						int k3 = LevelRenderer.getLightColor(level, blockpos$mutableblockpos);
						int l3 = k3 >> 16 & '\uffff';
						int i4 = k3 & '\uffff';
						int j4 = (l3 * 3 + 240) / 4;
						int k4 = (i4 * 3 + 240) / 4;
						bufferbuilder.vertex((double) k1 - x - d0 + 0.5D, (double) k2 - y, (double) j1 - z - d1 + 0.5D).uv(0.0F + f7, (float) j2 * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
						bufferbuilder.vertex((double) k1 - x + d0 + 0.5D, (double) k2 - y, (double) j1 - z + d1 + 0.5D).uv(1.0F + f7, (float) j2 * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
						bufferbuilder.vertex((double) k1 - x + d0 + 0.5D, (double) j2 - y, (double) j1 - z + d1 + 0.5D).uv(1.0F + f7, (float) k2 * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
						bufferbuilder.vertex((double) k1 - x - d0 + 0.5D, (double) j2 - y, (double) j1 - z - d1 + 0.5D).uv(0.0F + f7, (float) k2 * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
					}
				}
			}

			if (i1 >= 0) {
				tesselator.end();
			}

			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			lightmap.turnOffLightLayer();
		}
	}

	@Override
	public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
		soundsHandler.tick();
		return true;
	}
}