package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.entity.hostile.Gokkur;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SnowGokkurLayer<T extends Gokkur> extends RenderLayer<T, GokkurModel<T>> {
    private final ItemRenderer itemRenderer;

    public SnowGokkurLayer(RenderLayerParent<T, GokkurModel<T>> p_116994_, ItemRenderer p_234872_) {
        super(p_116994_);
        this.itemRenderer = p_234872_;
    }

    public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, T p_117010_, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
        p_117007_.pushPose();

        float f = p_117010_.getSnowProgress();

        this.getParentModel().root.translateAndRotate(p_117007_);
        //p_117007_.translate(0.0F, -0.34375F, 0.0F);

        this.getParentModel().body_rotation.translateAndRotate(p_117007_);
        p_117007_.scale(f, -f, -f);

        //p_117007_.translate(-0.0F, -0.0F, -0.0F);
        this.itemRenderer
                .renderStatic(
                        p_117010_,
                        new ItemStack(Items.SNOW_BLOCK),
                        ItemDisplayContext.HEAD,
                        false,
                        p_117007_,
                        p_117008_,
                        p_117010_.level(),
                        p_117009_,
                        LivingEntityRenderer.getOverlayCoords(p_117010_, 0.0F),
                        p_117010_.getId()
                );
        p_117007_.popPose();
    }
}