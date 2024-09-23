package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.layer.SnowGokkurLayer;
import baguchan.frostrealm.entity.hostile.Gokkur;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GokkurRenderer<T extends Gokkur> extends MobRenderer<T, GokkurModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur.png");
    private static final ResourceLocation GRASS_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_grass.png");

    public GokkurRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new GokkurModel<>(p_173952_.bakeLayer(FrostModelLayers.GOKKUR)), 0.5F);
        this.addLayer(new SnowGokkurLayer<>(this, p_173952_.getItemRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isGrass()) {
            return GRASS_TEXTURE;
        }
        return TEXTURE;
    }
}