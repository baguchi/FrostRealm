package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SeekerModel;
import baguchan.frostrealm.client.render.state.SeekerRenderState;
import baguchan.frostrealm.entity.boss.Seeker;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class SeekerRenderer<T extends Seeker> extends MobRenderer<T, SeekerRenderState, SeekerModel<SeekerRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seeker/seeker.png");

    public SeekerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SeekerModel<>(p_173952_.bakeLayer(FrostModelLayers.SEEKER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public SeekerRenderState createRenderState() {
        return new SeekerRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, SeekerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        ArmedEntityRenderState.extractArmedEntityRenderState(p_362733_, p_360515_, this.itemModelResolver);
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.preAttackAnimationState.copyFrom(p_362733_.preAttackAnimationState);
        p_360515_.stopAttackAnimationState.copyFrom(p_362733_.stopAttackAnimationState);
        p_360515_.deathAnimationState.copyFrom(p_362733_.deathAnimationState);
        p_360515_.isAgressive = p_362733_.isAggressive();
    }

    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    @Override
    public ResourceLocation getTextureLocation(SeekerRenderState p_110775_1_) {
        return TEXTURE;
    }
}
