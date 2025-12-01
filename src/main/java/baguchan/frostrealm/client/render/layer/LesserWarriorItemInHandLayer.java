package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.LesserWarriorModel;
import baguchan.frostrealm.client.render.LesserWarriorRenderer;
import baguchan.frostrealm.client.render.state.LesserWarriorRenderState;
import baguchan.frostrealm.entity.hostile.LesserWarrior;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;

public class LesserWarriorItemInHandLayer<S extends LesserWarriorRenderState, M extends EntityModel<S> & ArmedModel<S>> extends ItemInHandLayer<S, M> {
    public  LesserWarriorItemInHandLayer(RenderLayerParent<S, M> p_234846_) {
        super(p_234846_);
    }

    protected void submitArmWithItem(
            S p_433403_,
            ItemStackRenderState p_434808_,
            ItemStack p_454825_,
            HumanoidArm p_433781_,
            PoseStack p_435302_,
            SubmitNodeCollector p_435985_,
            int p_434421_
    ) {
        if (!p_434808_.isEmpty()) {
            p_435302_.pushPose();
            this.getParentModel().translateToHand(p_433403_, p_433781_, p_435302_);
            p_435302_.mulPose(Axis.XP.rotationDegrees(-90.0F));
            p_435302_.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean flag = p_433781_ == HumanoidArm.LEFT;
            p_435302_.translate((flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);


            if (p_433403_.attackTime > 0.0F && p_433403_.mainArm == p_433781_ && p_433403_.isHoldingSpear) {
                SpearAnimations.thirdPersonAttackItem(p_433403_, p_435302_);
            }

            float f = p_433403_.ticksUsingItem(p_433781_);
            if (f != 0.0F&& p_433403_.isHoldingSpear) {
                SpearAnimations.thirdPersonUseItem(p_433403_, p_435302_, f, p_433781_, p_454825_);
            }

            p_434808_.submit(p_435302_, p_435985_, p_434421_, OverlayTexture.NO_OVERLAY, p_433403_.outlineColor);
            p_435302_.popPose();
        }
    }
}
