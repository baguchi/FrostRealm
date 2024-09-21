package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.layer.WolfflueCollarLayer;
import baguchan.frostrealm.entity.animal.Wolfflue;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WolfflueRenderer<T extends Wolfflue> extends MobRenderer<T, WolfflueModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/wolfflue/wolfflue.png");
    private static final ResourceLocation ANGRY_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/wolfflue/wolfflue_angry.png");

    public WolfflueRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WolfflueModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFFLUE)), 0.5F);
        this.addLayer(new WolfflueCollarLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        if (p_110775_1_.isAngry()) {
            return ANGRY_TEXTURE;
        }
        return TEXTURE;
    }
}