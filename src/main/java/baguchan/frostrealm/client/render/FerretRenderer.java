package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FerretModel;
import baguchan.frostrealm.client.render.layer.FerretCollarLayer;
import baguchan.frostrealm.client.render.state.FerretRenderState;
import baguchan.frostrealm.entity.animal.Ferret;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class FerretRenderer<T extends Ferret> extends MobRenderer<T, FerretRenderState, FerretModel<FerretRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/ferret/ferret.png");

    public FerretRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FerretModel<>(p_173952_.bakeLayer(FrostModelLayers.FERRET)), 0.5F);
        this.addLayer(new FerretCollarLayer<>(this));
    }

    @Override
    protected void scale(FerretRenderState p_115314_, PoseStack p_115315_) {
        p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
        super.scale(p_115314_, p_115315_);
    }


    @Override
    public void extractRenderState(T p_362733_, FerretRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.running = p_362733_.getRunningScale(p_361157_);
        p_360515_.collarColor = p_362733_.isTame() ? p_362733_.getCollarColor() : null;
        p_360515_.isSitting = p_362733_.isInSittingPose();
    }

    @Override
    public FerretRenderState createRenderState() {
        return new FerretRenderState();
    }

    @Override
    public Identifier getTextureLocation(FerretRenderState entity) {
        return TEXTURE;
    }
}