package baguchan.frostrealm.client.event;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ClientColdHUDEvent {
	protected final Random random = new Random();

	protected int tickCount;

	public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/gui/icons.png");

	public static void renderPortalOverlay(RenderGameOverlayEvent.Post event, Minecraft mc, Window window, Entity livingEntity) {
		livingEntity.getCapability(FrostRealm.FROST_LIVING_CAPABILITY).ifPresent(cap -> {
			float timeInPortal = Mth.lerp(event.getPartialTick(), cap.getPrevPortalAnimTime(), cap.getPortalAnimTime());

			if (timeInPortal > 0.0F) {
				if (timeInPortal < 1.0F) {
					timeInPortal = timeInPortal * timeInPortal;
					timeInPortal = timeInPortal * timeInPortal;
					timeInPortal = timeInPortal * 0.8F + 0.2F;
				}
				RenderSystem.enableTexture();
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
				RenderSystem.disableTexture();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			}
		});
	}

	@SubscribeEvent
	public void renderHudEvent(RenderGameOverlayEvent.Post event) {
		PoseStack stack = event.getPoseStack();
		Minecraft mc = Minecraft.getInstance();
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			Entity entity = mc.getCameraEntity();
			int screenWidth = mc.getWindow().getGuiScaledWidth();
			int screenHeight = mc.getWindow().getGuiScaledHeight() - ((ForgeIngameGui) mc.gui).right_height;
			this.random.setSeed((this.tickCount * 312871));
			stack.pushPose();
			RenderSystem.enableBlend();
			entity.getCapability(FrostRealm.FROST_LIVING_CAPABILITY).ifPresent(cap -> {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
				int l = cap.getTemperatureLevel();
				int j1 = screenWidth / 2 + 91;
				int k1 = screenHeight;
				for (int k6 = 0; k6 < 10; k6++) {
					int i7 = k1;
					int k7 = 16;
					int i8 = 0;
					if (cap.getSaturationLevel() <= 0.0F && this.tickCount % (l * 3 + 1) == 0)
						i7 = k1 + this.random.nextInt(3) - 1;
					int k8 = j1 - k6 * 8 - 9;
					mc.gui.blit(stack, k8, i7, 16 + i8 * 9, 27, 9, 9);
					if (k6 * 2 + 1 < l)
						mc.gui.blit(stack, k8, i7, k7 + 36, 27, 9, 9);
					if (k6 * 2 + 1 == l)
						mc.gui.blit(stack, k8, i7, k7 + 45, 27, 9, 9);
				}
			});
			RenderSystem.disableBlend();
			((ForgeIngameGui) mc.gui).right_height += 10;
			stack.popPose();
			this.tickCount++;
		}
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			Entity entity = mc.getCameraEntity();
			stack.pushPose();
			renderPortalOverlay(event, mc, mc.getWindow(), entity);
			stack.popPose();
		}
	}
}
