package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FerretModel;
import baguchan.frostrealm.client.render.layer.FerretCollarLayer;
import baguchan.frostrealm.entity.animal.Ferret;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FerretRenderer<T extends Ferret> extends MobRenderer<T, FerretModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/ferret/ferret.png");

    public FerretRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FerretModel<>(p_173952_.bakeLayer(FrostModelLayers.FERRET)), 0.5F);
        this.addLayer(new FerretCollarLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}