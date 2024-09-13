package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.VenomBallModel;
import baguchan.frostrealm.entity.projectile.VenomBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VenomBallRenderer<T extends VenomBall> extends EntityRenderer<T> {
    private static final ResourceLocation LLAMA_SPIT_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/venom_ball.png");
    private final VenomBallModel<T> model;

    public VenomBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new VenomBallModel<>(context.bakeLayer(FrostModelLayers.VENOM_BALL));
    }

    public void render(T llamaSpit, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.0F, -1.15F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, llamaSpit.yRotO, llamaSpit.getYRot()) - 180F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, llamaSpit.xRotO, llamaSpit.getXRot())));
        this.model.setupAnim(llamaSpit, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(LLAMA_SPIT_LOCATION));
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(llamaSpit, f, g, poseStack, multiBufferSource, i);
    }

    public ResourceLocation getTextureLocation(T llamaSpit) {
        return LLAMA_SPIT_LOCATION;
    }
}