package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.BushBugModel;
import baguchan.frostrealm.entity.animal.BushBug;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

public class BushBugBushLayer<T extends BushBug> extends RenderLayer<T, BushBugModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/bush_bug/bush_bug_overlay.png");

    public BushBugBushLayer(RenderLayerParent<T, BushBugModel<T>> p_117707_) {
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
        if (p_117723_.isShearableWithoutConditions()) {
            this.getParentModel().setColor(FastColor.ARGB32.color(1, 1, 1, 1));
            renderColoredCutoutModel(this.getParentModel(), TEXTURE, p_117720_, p_117721_, p_117722_, p_117723_, FastColor.ARGB32.colorFromFloat(1, 1, 1, 1));
        }
    }
}
