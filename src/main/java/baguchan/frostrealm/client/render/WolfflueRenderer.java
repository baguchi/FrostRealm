package baguchan.frostrealm.client.render;

import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.layer.WolfflueArmorLayer;
import baguchan.frostrealm.client.render.layer.WolfflueCollarLayer;
import baguchan.frostrealm.client.render.layer.WolfflueHeldItemLayer;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import baguchan.frostrealm.entity.animal.Wolfflue;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;

public class WolfflueRenderer<T extends Wolfflue> extends AgeableMobRenderer<T, WolfflueRenderState, WolfflueModel<WolfflueRenderState>> {
    public WolfflueRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WolfflueModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFFLUE)), new WolfflueModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFFLUE_BABY)), 0.5F);
        this.addLayer(new WolfflueCollarLayer<>(this));
        this.addLayer(new WolfflueArmorLayer<>(this, p_173952_.getModelSet(), p_173952_.getEquipmentRenderer()));
        this.addLayer(new SimpleEquipmentLayer<>(this, p_173952_.getEquipmentRenderer(), EquipmentClientInfo.LayerType.valueOf("FROSTREALM_WOLFFLUE_SADDLE"), (p_397421_) -> p_397421_.saddle, new WolfflueModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFFLUE_SADDLE)), (EntityModel) null));

        this.addLayer(new WolfflueHeldItemLayer<>(this));
    }


    @Override
    public void extractRenderState(T p_363274_, WolfflueRenderState p_363549_, float p_362105_) {
        super.extractRenderState(p_363274_, p_363549_, p_362105_);
        HoldingEntityRenderState.extractHoldingEntityRenderState(p_363274_, p_363549_, this.itemModelResolver);

        p_363549_.isAngry = p_363274_.isAngry();
        p_363549_.isSitting = p_363274_.isInSittingPose();
        p_363549_.saddle = p_363274_.getItemBySlot(EquipmentSlot.SADDLE);
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
    public Identifier getTextureLocation(WolfflueRenderState entity) {
        return entity.texture;
    }
}