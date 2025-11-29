package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class WolfflueCollarLayer<T extends WolfflueRenderState> extends RenderLayer<T, WolfflueModel<T>> {
    private static final Identifier WOLF_COLLAR_LOCATION = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/wolfflue/wolfflue_collar.png");

    public WolfflueCollarLayer(RenderLayerParent<T, WolfflueModel<T>> p_117707_) {
        super(p_117707_);
    }

    @Override
    public void submit(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int p_117722_,
            T renderState,
            float p_117724_,
            float p_117725_
    ) {
        if (renderState.collarColor != null && !renderState.isInvisible) {
            int i = renderState.collarColor.getTextureDiffuseColor();
            submitNodeCollector.order(1)
                    .submitModel(
                            this.getParentModel(),
                            renderState,
                            poseStack,
                            RenderType.entityCutoutNoCull(WOLF_COLLAR_LOCATION),
                            p_117722_,
                            OverlayTexture.NO_OVERLAY,
                            i,
                            null,
                            renderState.outlineColor,
                            null
                    );
        }
    }
}
