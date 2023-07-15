package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SealModel;
import baguchan.frostrealm.entity.Seal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SealRenderer<T extends Seal> extends MobRenderer<T, SealModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/seal/seal.png");
    private static final ResourceLocation TEXTURE_BABY = new ResourceLocation(FrostRealm.MODID, "textures/entity/seal/seal_baby.png");

    public SealRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SealModel<>(p_173952_.bakeLayer(FrostModelLayers.SEAL)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return p_110775_1_.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}