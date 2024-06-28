package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SealModel;
import baguchan.frostrealm.entity.animal.Seal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SealRenderer<T extends Seal> extends MobRenderer<T, SealModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal.png");
    private static final ResourceLocation TEXTURE_BABY = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal_baby.png");

    private static final ResourceLocation TEXTURE_CLOSE_EYE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal_close_eye.png");
    private static final ResourceLocation TEXTURE_BABY_CLOSE_EYE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seal/seal_baby_close_eye.png");

    public SealRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SealModel<>(p_173952_.bakeLayer(FrostModelLayers.SEAL)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        /*if(p_110775_1_.fartAnimationState.isStarted()){
            return p_110775_1_.isBaby() ? TEXTURE_BABY_CLOSE_EYE : TEXTURE_CLOSE_EYE;
        }*/
        return p_110775_1_.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}