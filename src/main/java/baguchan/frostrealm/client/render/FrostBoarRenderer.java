package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostBoarModel;
import baguchan.frostrealm.entity.animal.FrostBoar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostBoarRenderer<T extends FrostBoar> extends MobRenderer<T, FrostBoarModel<T>> {
    private static final ResourceLocation BOAR = new ResourceLocation(FrostRealm.MODID, "textures/entity/frost_boar.png");


    public FrostBoarRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FrostBoarModel<>(p_173952_.bakeLayer(FrostModelLayers.FROST_BOAR)), 0.6F);
    }

    @Override
    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        super.scale(p_115314_, p_115315_, p_115316_);
        p_115315_.scale(p_115314_.getAgeScale(), p_115314_.getAgeScale(), p_115314_.getAgeScale());
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return BOAR;
    }
}