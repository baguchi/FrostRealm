package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostBoarModel;
import baguchan.frostrealm.client.render.state.FrostBoarRenderState;
import baguchan.frostrealm.entity.animal.FrostBoar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class FrostBoarRenderer<T extends FrostBoar> extends MobRenderer<T, FrostBoarRenderState, FrostBoarModel<FrostBoarRenderState>> {
    private static final ResourceLocation BOAR = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_boar.png");


    public FrostBoarRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FrostBoarModel<>(p_173952_.bakeLayer(FrostModelLayers.FROST_BOAR)), 0.6F);
    }

    @Override
    protected void scale(FrostBoarRenderState p_115314_, PoseStack p_115315_) {
        super.scale(p_115314_, p_115315_);
        p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
    }

    @Override
    public FrostBoarRenderState createRenderState() {
        return new FrostBoarRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, FrostBoarRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.attackAnimation = p_362733_.attackAnimation;
        p_360515_.runningScale = p_362733_.getRunningScale();
    }

    @Override
    public ResourceLocation getTextureLocation(FrostBoarRenderState p_110775_1_) {
        return BOAR;
    }
}