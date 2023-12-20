package baguchan.frostrealm.client.event;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.registry.FrostAttachs;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ClientColdHUDEvent {
	protected final Random random = new Random();

	protected int tickCount;

	public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/gui/icons.png");

	public static void renderPortalOverlay(RenderGuiOverlayEvent.Post event, Minecraft mc, Window window, Entity livingEntity) {
		FrostLivingCapability cap = livingEntity.getData(FrostAttachs.FROST_LIVING);
			float timeInPortal = Mth.lerp(event.getPartialTick(), cap.getPrevPortalAnimTime(), cap.getPortalAnimTime());

			if (timeInPortal > 0.0F) {
				if (timeInPortal < 1.0F) {
					timeInPortal = timeInPortal * timeInPortal;
					timeInPortal = timeInPortal * timeInPortal;
					timeInPortal = timeInPortal * 0.8F + 0.2F;
				}
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, timeInPortal);
				RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				TextureAtlasSprite textureatlassprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(FrostBlocks.FROST_PORTAL.get().defaultBlockState());
				float f = textureatlassprite.getU0();
				float f1 = textureatlassprite.getV0();
				float f2 = textureatlassprite.getU1();
				float f3 = textureatlassprite.getV1();
				Tesselator tesselator = Tesselator.getInstance();
				BufferBuilder bufferbuilder = tesselator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(f, f3).endVertex();
				bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(f2, f3).endVertex();
				bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(f2, f1).endVertex();
				bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
				tesselator.end();
				RenderSystem.disableBlend();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			}
	}

	@SubscribeEvent
	public void renderHudEvent(RenderGuiOverlayEvent.Post event) {
		GuiGraphics guiGraphics = event.getGuiGraphics();
		Minecraft mc = Minecraft.getInstance();
		Entity entity = mc.getCameraEntity();
		int screenWidth = mc.getWindow().getGuiScaledWidth();
		int screenHeight = mc.getWindow().getGuiScaledHeight() - ((ExtendedGui) mc.gui).rightHeight;
		if (entity != null && entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL && event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
			this.random.setSeed((this.tickCount * 312871));
			RenderSystem.enableBlend();
			FrostLivingCapability cap = entity.getData(FrostAttachs.FROST_LIVING);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
				int l = cap.getTemperatureLevel();
				int j1 = screenWidth / 2 + 91;
				int k1 = screenHeight;
				for (int k6 = 0; k6 < 10; k6++) {
					int i7 = k1;
					int k7 = 16;
					int i8 = 0;
					if (cap.getSaturationLevel() <= 0.0F && this.tickCount % (l * 3 + 1) == 0) {
						i7 = k1 + this.random.nextInt(3) - 1;
					}
					int k8 = j1 - k6 * 8 - 9;
					guiGraphics.blit(GUI_ICONS_LOCATION, k8, i7, 16 + i8 * 9, 27, 9, 9);
					if (k6 * 2 + 1 < l) {
						guiGraphics.blit(GUI_ICONS_LOCATION, k8, i7, k7 + 36, 27, 9, 9);
					}
					if (k6 * 2 + 1 == l) {
						guiGraphics.blit(GUI_ICONS_LOCATION, k8, i7, k7 + 45, 27, 9, 9);
					}
				}
			RenderSystem.disableBlend();
			((ExtendedGui) mc.gui).rightHeight += 10;
			this.tickCount++;
		}

		if (event.getOverlay() == VanillaGuiOverlay.PORTAL.type()) {
			Entity entity2 = mc.getCameraEntity();
			renderPortalOverlay(event, mc, mc.getWindow(), entity2);
		}
	}
}
