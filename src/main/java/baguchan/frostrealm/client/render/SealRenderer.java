package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SealModel;
import baguchan.frostrealm.client.render.state.SealRenderState;
import baguchan.frostrealm.entity.animal.Seal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;


public class SealRenderer<T extends Seal> extends MobRenderer<T, SealRenderState, SealModel<SealRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal.png");
    private static final Identifier TEXTURE_BABY = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal_baby.png");

    private static final Identifier TEXTURE_CLOSE_EYE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal_close_eye.png");
    private static final Identifier TEXTURE_BABY_CLOSE_EYE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal_baby_close_eye.png");

    public SealRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SealModel<>(p_173952_.bakeLayer(FrostModelLayers.SEAL)), 0.5F);
    }

    @Override
    public void extractRenderState(T p_362733_, SealRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.fartAnimationState.copyFrom(p_362733_.fartAnimationState);
    }

    @Override
    public SealRenderState createRenderState() {
        return new SealRenderState();
    }

    @Override
    public Identifier getTextureLocation(SealRenderState p_110775_1_) {
        if (p_110775_1_.fartAnimationState.isStarted()) {
            return p_110775_1_.isBaby ? TEXTURE_BABY_CLOSE_EYE : TEXTURE_CLOSE_EYE;
        }
        return p_110775_1_.isBaby ? TEXTURE_BABY : TEXTURE;
    }
}