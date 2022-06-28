package baguchan.frostrealm.client.sky;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ISkyRenderHandler;
import org.jetbrains.annotations.NotNull;

public class FrostrealmSkyRenderer implements ISkyRenderHandler {
	private static final ResourceLocation AURORA_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/environment/aurora.png");

	@Override
	public void render(int ticks, float partialTick, PoseStack poseStack, ClientLevel level, Minecraft minecraft) {

		level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(frostWeatherCapability -> {
			renderSky(poseStack, frostWeatherCapability, partialTick, minecraft);
		});
	}

	private void renderSky(PoseStack p_109781_, @NotNull FrostWeatherCapability frostWeatherCapability, float partialTick, Minecraft minecraft) {
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		RenderSystem.depthMask(false);
		RenderSystem.disableCull();
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.enableTexture();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		p_109781_.pushPose();
		float f11 = 1.0F - frostWeatherCapability.getWeatherLevel(partialTick);
		FogRenderer.levelFogColor();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
		Matrix4f matrix4f1 = p_109781_.last().pose();
		float f12 = 160.0F;
		float f13 = (float) (110.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, AURORA_LOCATION);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix4f1, -f12, (float) f13, -f12).uv(0.0F, 0.0F).endVertex();
		bufferbuilder.vertex(matrix4f1, f12, (float) f13, -f12).uv(1.0F, 0.0F).endVertex();
		bufferbuilder.vertex(matrix4f1, f12, (float) f13, f12).uv(1.0F, 1.0F).endVertex();
		bufferbuilder.vertex(matrix4f1, -f12, (float) f13, f12).uv(0.0F, 1.0F).endVertex();
		BufferUploader.drawWithShader(bufferbuilder.end());
		p_109781_.popPose();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableCull();
		RenderSystem.disableBlend();
		RenderSystem.disableTexture();
		RenderSystem.depthMask(true);
	}
}
