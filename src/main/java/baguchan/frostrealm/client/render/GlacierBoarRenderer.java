package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.GlacierBoarModel;
import baguchan.frostrealm.client.render.state.GlacierBoarRenderState;
import baguchan.frostrealm.entity.animal.GlacierBoar;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class GlacierBoarRenderer<T extends GlacierBoar> extends MobRenderer<T, GlacierBoarRenderState, GlacierBoarModel<GlacierBoarRenderState>> {
    private static final ResourceLocation BOAR = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/glacier_boar.png");


    public GlacierBoarRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new GlacierBoarModel<>(p_173952_.bakeLayer(FrostModelLayers.GLACIER_BOAR)), 0.6F);
    }

    @Override
    public GlacierBoarRenderState createRenderState() {
        return new GlacierBoarRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, GlacierBoarRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.attackAnimation = p_362733_.attackAnimation;
        p_360515_.runningScale = p_362733_.getRunningScale();
    }

    @Override
    public ResourceLocation getTextureLocation(GlacierBoarRenderState p_110775_1_) {
        return BOAR;
    }
}