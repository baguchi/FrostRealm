package baguchan.frostrealm.client.sky;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.registry.FrostWeathers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.IWeatherRenderHandler;

import java.util.Random;

public class FrostrealmWeatherRenderer implements IWeatherRenderHandler {

	private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
	private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
	private final float[] rainxs = new float[1024];
	private final float[] rainzs = new float[1024];

	private int rendererUpdateCount;
	public FrostrealmWeatherRenderer() {
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

	public void tick() {
		++this.rendererUpdateCount;
	}

	@Override
	public void render(int ticks, float partialTicks, ClientLevel world, Minecraft mc, LightTexture lightmap, double xIn, double yIn, double zIn) {
		tick();
		world.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(frostWeatherCapability -> {
			if (frostWeatherCapability.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
				this.renderBrizzardWeather(frostWeatherCapability, lightmap, world, mc, partialTicks, xIn, yIn, zIn);
			}
		});
	}

	private void renderBrizzardWeather(FrostWeatherCapability cap, LightTexture lightmap, ClientLevel world, Minecraft mc, float ticks, double x, double y, double z) {

		float f = cap.getWeatherLevel(ticks);
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
}