package baguchan.frostrealm.client.render;

import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.layer.WolfflueArmorLayer;
import baguchan.frostrealm.client.render.layer.WolfflueCollarLayer;
import baguchan.frostrealm.entity.animal.Wolfflue;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WolfflueRenderer<T extends Wolfflue> extends MobRenderer<T, WolfflueModel<T>> {
    public WolfflueRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WolfflueModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFFLUE)), 0.5F);
        this.addLayer(new WolfflueCollarLayer<>(this));
        this.addLayer(new WolfflueArmorLayer<>(this, p_173952_.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.getTexture();
    }
}