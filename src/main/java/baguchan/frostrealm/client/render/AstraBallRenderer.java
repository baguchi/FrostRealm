package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.AstraBallModel;
import baguchan.frostrealm.entity.AstraBall;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AstraBallRenderer<T extends AstraBall> extends MobRenderer<T, AstraBallModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/astra_ball.png");

    public AstraBallRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new AstraBallModel<>(p_173952_.bakeLayer(FrostModelLayers.ASTRA_BALL)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}