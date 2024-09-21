package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.entity.animal.Wolfflue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WolfflueHeldItemLayer<T extends Wolfflue> extends RenderLayer<T, WolfflueModel<T>> {
    public WolfflueHeldItemLayer(RenderLayerParent<T, WolfflueModel<T>> p_116994_) {
        super(p_116994_);
    }

    public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, T p_117010_, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
        boolean flag1 = p_117010_.isBaby();
        p_117007_.pushPose();
        if (flag1) {
            float f = 0.75F;
            p_117007_.scale(0.75F, 0.75F, 0.75F);
            p_117007_.translate(0.0D, 0.65D, 0.0D);
        }

        this.getParentModel().all.translateAndRotate(p_117007_);
        this.getParentModel().head.translateAndRotate(p_117007_);
        p_117007_.translate(0.059D, 0.15D, -0.42D);
        p_117007_.mulPose(Axis.XP.rotationDegrees(90.0F));

        ItemStack itemstack = p_117010_.getItemBySlot(EquipmentSlot.MAINHAND);
        Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(p_117010_, itemstack, ItemDisplayContext.GROUND, false, p_117007_, p_117008_, p_117009_);
        p_117007_.popPose();
    }
}