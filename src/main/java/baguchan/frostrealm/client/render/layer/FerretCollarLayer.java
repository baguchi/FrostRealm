package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.FerretModel;
import baguchan.frostrealm.entity.animal.Ferret;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FerretCollarLayer<T extends Ferret> extends RenderLayer<T, FerretModel<T>> {
    private static final ResourceLocation COLLAR_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/ferret/ferret_collar.png");

    public FerretCollarLayer(RenderLayerParent<T, FerretModel<T>> p_117707_) {
        super(p_117707_);
    }

    public void render(
            PoseStack p_117720_,
            MultiBufferSource p_117721_,
            int p_117722_,
            T p_117723_,
            float p_117724_,
            float p_117725_,
            float p_117726_,
            float p_117727_,
            float p_117728_,
            float p_117729_
    ) {
        if (p_117723_.isTame() && !p_117723_.isInvisible()) {
            int i = p_117723_.getCollarColor().getTextureDiffuseColor();
            VertexConsumer vertexconsumer = p_117721_.getBuffer(RenderType.entityCutoutNoCull(COLLAR_LOCATION));
            this.getParentModel().renderToBuffer(p_117720_, vertexconsumer, p_117722_, OverlayTexture.NO_OVERLAY, i);
        }
    }
}
