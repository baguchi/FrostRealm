package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.RootDeerModel;
import baguchan.frostrealm.client.render.state.RootDeerRenderState;
import baguchan.frostrealm.entity.hostile.RootDeer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RootDeerRenderer<T extends RootDeer> extends MobRenderer<T, RootDeerRenderState, RootDeerModel<RootDeerRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/root_deer.png");

    public RootDeerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new RootDeerModel<>(p_173952_.bakeLayer(FrostModelLayers.ROOT_DEER)), 0.5F);
    }

    @Override
    public void extractRenderState(T p_362733_, RootDeerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.direction = p_362733_.getAttachFace();
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.summonAnimationState.copyFrom(p_362733_.summonAnimationState);
        p_360515_.deathAnimationState.copyFrom(p_362733_.deathAnimationState);
    }

    @Override
    protected void setupRotations(RootDeerRenderState p_320913_, PoseStack p_115891_, float p_115892_, float p_115893_) {
        super.setupRotations(p_320913_, p_115891_, p_115892_, p_115893_ /*+ 180.0F*/);
        p_115891_.rotateAround(p_320913_.direction.getOpposite().getRotation(), 0.0F, 0.5F, 0.0F);
    }

    @Override
    protected float getFlipDegrees() {
        return 0.0F;
    }

    @Override
    public RootDeerRenderState createRenderState() {
        return new RootDeerRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(RootDeerRenderState p_110775_1_) {
        return TEXTURE;
    }
}