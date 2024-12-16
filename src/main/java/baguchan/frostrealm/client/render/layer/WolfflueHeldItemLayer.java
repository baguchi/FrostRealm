package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class WolfflueHeldItemLayer<T extends WolfflueRenderState> extends RenderLayer<T, WolfflueModel<T>> {
    public WolfflueHeldItemLayer(RenderLayerParent<T, WolfflueModel<T>> p_116994_) {
        super(p_116994_);
    }

    @Override
    public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, T p_117010_, float p_117011_, float p_117012_) {
        boolean flag1 = p_117010_.isBaby;
        if (!p_117010_.heldItem.isEmpty()) {

            p_117007_.pushPose();
            if (flag1) {
                float f = 0.75F;
                p_117007_.scale(0.75F, 0.75F, 0.75F);
                p_117007_.translate(0.0D, 0.65D, 0.0D);
            }

            this.getParentModel().all.translateAndRotate(p_117007_);
            this.getParentModel().head.translateAndRotate(p_117007_);
            p_117007_.translate(0.0D, 0.05D, -0.8D);
            p_117007_.mulPose(Axis.XP.rotationDegrees(90.0F));
            p_117007_.mulPose(Axis.ZP.rotationDegrees(-60.0F));

            p_117010_.heldItem.render(p_117007_, p_117008_, p_117009_, OverlayTexture.NO_OVERLAY);
            p_117007_.popPose();
        }
    }
}