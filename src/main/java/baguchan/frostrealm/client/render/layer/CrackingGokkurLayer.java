package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Crackiness;

import java.util.Map;

public class CrackingGokkurLayer<T extends GokkurRenderState> extends RenderLayer<T, GokkurModel<T>> {
    private static final Map<Crackiness.Level, ResourceLocation> resourceLocations = ImmutableMap.of(
            Crackiness.Level.LOW,
            ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_crackiness_low.png"),
            Crackiness.Level.MEDIUM,
            ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_crackiness_medium.png"),
            Crackiness.Level.HIGH,
            ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_crackiness_high.png")
    );

    public CrackingGokkurLayer(RenderLayerParent<T, GokkurModel<T>> p_116994_) {
        super(p_116994_);
    }

    public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, GokkurRenderState p_117010_, float p_117011_, float p_117012_) {
        p_117007_.pushPose();

        Crackiness.Level level = p_117010_.crackiness;
        if (!p_117010_.isInvisible) {
            if (level != Crackiness.Level.NONE) {
                VertexConsumer vertexconsumer = p_117008_.getBuffer(RenderType.entityCutoutNoCull(resourceLocations.get(level)));
                this.getParentModel().renderToBuffer(p_117007_, vertexconsumer, p_117009_, OverlayTexture.NO_OVERLAY);
            }
        }
        p_117007_.popPose();
    }
}