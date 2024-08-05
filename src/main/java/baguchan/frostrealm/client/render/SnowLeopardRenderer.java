package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SnowLeopardModel;
import baguchan.frostrealm.entity.animal.SnowLeopard;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowLeopardRenderer<T extends SnowLeopard> extends MobRenderer<T, SnowLeopardModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/snow_leopard.png");

    public SnowLeopardRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SnowLeopardModel<>(p_173952_.bakeLayer(FrostModelLayers.SNOW_LEOPARD)), 0.7F);
        this.addLayer(new SnowLeopardHeldItemLayer<>(this));
    }

    @Override
    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        p_115315_.scale(p_115314_.getAgeScale(), p_115314_.getAgeScale(), p_115314_.getAgeScale());
        super.scale(p_115314_, p_115315_, p_115316_);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}
