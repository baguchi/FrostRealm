package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Crackiness;

import java.util.Map;

public class CrackingGokkurLayer<T extends GokkurRenderState> extends RenderLayer<T, GokkurModel<T>> {
    private static final Map<Crackiness.Level, Identifier> resourceLocations = ImmutableMap.of(
            Crackiness.Level.LOW,
            Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_crackiness_low.png"),
            Crackiness.Level.MEDIUM,
            Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_crackiness_medium.png"),
            Crackiness.Level.HIGH,
            Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_crackiness_high.png")
    );

    public CrackingGokkurLayer(RenderLayerParent<T, GokkurModel<T>> p_116994_) {
        super(p_116994_);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, T t, float v, float v1) {
        poseStack.pushPose();

        Crackiness.Level level = t.crackiness;
        if (!t.isInvisible) {
            if (level != Crackiness.Level.NONE) {
              submitNodeCollector.submitModel(this.getParentModel(), t, poseStack, RenderTypes.entityCutoutNoCull(resourceLocations.get(level)), i, OverlayTexture.NO_OVERLAY, -1, null, t.outlineColor, null);
            }
        }
        poseStack.popPose();
    }
}