package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class WolfflueHeldItemLayer<T extends WolfflueRenderState> extends RenderLayer<T, WolfflueModel<T>> {
    public WolfflueHeldItemLayer(RenderLayerParent<T, WolfflueModel<T>> p_116994_) {
        super(p_116994_);
    }


    @Override
    public void submit(PoseStack p_117007_, SubmitNodeCollector p_434965_, int p_117009_, T p_360609_, float p_117011_, float p_117012_) {
        boolean flag1 = p_360609_.isBaby;

        ItemStackRenderState itemstackrenderstate = p_360609_.heldItem;
        if (!itemstackrenderstate.isEmpty()) {

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
            itemstackrenderstate.submit(p_117007_, p_434965_, p_117009_, OverlayTexture.NO_OVERLAY, p_360609_.outlineColor);
            p_117007_.popPose();
        }
    }
}