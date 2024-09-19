package baguchan.frostrealm.client.overlay;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.registry.FrostAttachs;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.Random;

public class FrostOverlay implements LayeredDraw.Layer {
    public static final ResourceLocation GUI_ICONS_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/gui/icons.png");

    protected final Random random = new Random();

    protected int tickCount;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker p_316643_) {
        Minecraft mc = Minecraft.getInstance();
        Entity entity = mc.getCameraEntity();
        Options options = mc.options;

        if (!options.hideGui) {
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight() - (mc.gui).rightHeight;
            if (entity != null && entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
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
                (mc.gui).rightHeight += 10;
                this.tickCount++;
            }
        }
    }
}
