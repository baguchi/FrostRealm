package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class SnowGokkurLayer<T extends GokkurRenderState> extends RenderLayer<T, GokkurModel<T>> {


    public SnowGokkurLayer(RenderLayerParent<T, GokkurModel<T>> p_234850_) {
        super(p_234850_);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, T renderState, float p_433223_, float p_433380_) {

        if (!renderState.headBlock.isEmpty()) {
            if (!renderState.isInvisible || renderState.appearsGlowing()) {
                poseStack.pushPose();
                float f = renderState.snowProgress;
                this.getParentModel().root.translateAndRotate(poseStack);
                this.getParentModel().body_rotation.translateAndRotate(poseStack);
                poseStack.scale(f, f, f);
                int overlayCoords = LivingEntityRenderer.getOverlayCoords(renderState, 0.0F);
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                if (renderState.appearsGlowing() && renderState.isInvisible) {
                    renderState.headBlock.submitOnlyOutline(poseStack, submitNodeCollector, lightCoords, overlayCoords, renderState.outlineColor);
                } else {
                    renderState.headBlock.submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, renderState.outlineColor);
                }
                poseStack.popPose();
            }
        }
    }
}
