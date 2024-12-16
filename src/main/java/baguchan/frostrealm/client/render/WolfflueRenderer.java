package baguchan.frostrealm.client.render;

import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.layer.WolfflueArmorLayer;
import baguchan.frostrealm.client.render.layer.WolfflueCollarLayer;
import baguchan.frostrealm.client.render.layer.WolfflueHeldItemLayer;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import baguchan.frostrealm.entity.animal.Wolfflue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class WolfflueRenderer<T extends Wolfflue> extends MobRenderer<T, WolfflueRenderState, WolfflueModel<WolfflueRenderState>> {
    public WolfflueRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WolfflueModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFFLUE)), 0.5F);
        this.addLayer(new WolfflueCollarLayer<>(this));
        this.addLayer(new WolfflueArmorLayer<>(this, p_173952_.getModelSet()));
        this.addLayer(new WolfflueHeldItemLayer<>(this));
    }

    @Override
    protected void scale(WolfflueRenderState p_115314_, PoseStack p_115315_) {
        p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
        super.scale(p_115314_, p_115315_);
    }


    @Override
    public void extractRenderState(T p_363274_, WolfflueRenderState p_363549_, float p_362105_) {
        super.extractRenderState(p_363274_, p_363549_, p_362105_);
        HoldingEntityRenderState.extractHoldingEntityRenderState(p_363274_, p_363549_, this.itemModelResolver);

        p_363549_.isAngry = p_363274_.isAngry();
        p_363549_.isSitting = p_363274_.isInSittingPose();
        p_363549_.tailAngle = p_363274_.getTailAngle();
        p_363549_.headRollAngle = p_363274_.getHeadRollAngle(p_362105_);
        p_363549_.texture = p_363274_.getTexture();
        p_363549_.collarColor = p_363274_.isTame() ? p_363274_.getCollarColor() : null;
        p_363549_.bodyArmorItem = p_363274_.getBodyArmorItem().copy();
        p_363549_.idleSitAnimationState.copyFrom(p_363274_.idleSitAnimationState);
        p_363549_.idleSit2AnimationState.copyFrom(p_363274_.idleSit2AnimationState);
        p_363549_.jumpAnimationState.copyFrom(p_363274_.jumpAnimationState);
        p_363549_.running = p_363274_.getRunningScale(p_362105_);
    }

    @Override
    public WolfflueRenderState createRenderState() {
        return new WolfflueRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(WolfflueRenderState entity) {
        return entity.texture;
    }
}