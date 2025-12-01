package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.FerretModel;
import baguchan.frostrealm.client.render.state.FerretRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class FerretCollarLayer<T extends FerretRenderState> extends RenderLayer<T, FerretModel<T>> {
    private static final Identifier COLLAR_LOCATION = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/ferret/ferret_collar.png");

    public FerretCollarLayer(RenderLayerParent<T, FerretModel<T>> p_117707_) {
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
                            RenderTypes.entityCutoutNoCull(COLLAR_LOCATION),
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
