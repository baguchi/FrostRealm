package baguchan.frostrealm.fluidtype;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public class HotSpringFluidType extends FluidType {
    public HotSpringFluidType(FluidType.Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation TEXTURE_STILL = new ResourceLocation(FrostRealm.MODID, "block/hot_spring_still");
            private static final ResourceLocation TEXTURE_FLOW = new ResourceLocation(FrostRealm.MODID, "block/hot_spring_flow");
            private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(FrostRealm.MODID, "textures/block/hot_spring_still.png");

            @Override
            public ResourceLocation getStillTexture() {
                return TEXTURE_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return TEXTURE_FLOW;
            }

            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return TEXTURE_OVERLAY;
            }


            @Override
            public void renderOverlay(Minecraft mc, PoseStack stack) {
                ResourceLocation texture = this.getRenderOverlayTexture(mc);
                if (texture == null) return;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, texture);
                BufferBuilder buffer = Tesselator.getInstance().getBuilder();
                BlockPos playerEyePos = BlockPos.containing(mc.player.getX(), mc.player.getEyeY(), mc.player.getZ());
                float brightness = LightTexture.getBrightness(mc.player.level().dimensionType(), mc.player.level().getMaxLocalRawBrightness(playerEyePos));
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(brightness, brightness, brightness, 0.65F);
                float uOffset = -mc.player.getYRot() / 64.0F;
                float vOffset = mc.player.getXRot() / 64.0F;
                Matrix4f pose = stack.last().pose();
                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                buffer.vertex(pose, -1.0F, -1.0F, -0.5F).uv(4.0F + uOffset, 4.0F + vOffset).endVertex();
                buffer.vertex(pose, 1.0F, -1.0F, -0.5F).uv(uOffset, 4.0F + vOffset).endVertex();
                buffer.vertex(pose, 1.0F, 1.0F, -0.5F).uv(uOffset, vOffset).endVertex();
                buffer.vertex(pose, -1.0F, 1.0F, -0.5F).uv(4.0F + uOffset, vOffset).endVertex();
                BufferUploader.drawWithShader(buffer.end());
                RenderSystem.disableBlend();
            }
        });
    }
}