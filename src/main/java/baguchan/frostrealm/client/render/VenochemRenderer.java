package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.VenochemModel;
import baguchan.frostrealm.client.render.state.VenochemRenderState;
import baguchan.frostrealm.entity.hostile.Venochem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class VenochemRenderer<T extends Venochem> extends MobRenderer<T, VenochemRenderState, VenochemModel<VenochemRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/venochem/venochem.png");
    private static final RenderType VENOCHEM_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/venochem/venochem_glow.png"));

    public VenochemRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new VenochemModel<>(p_173952_.bakeLayer(FrostModelLayers.VENOCHEM)), 0.5F);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return VENOCHEM_GLOW;
            }
        });
    }

    @Override
    public VenochemRenderState createRenderState() {
        return new VenochemRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, VenochemRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.shootAnimationState.copyFrom(p_362733_.shootAnimationState);
        p_360515_.attachFace = p_362733_.getAttachFacing();
    }

    @Override
    protected void setupRotations(VenochemRenderState p_364147_, PoseStack p_115908_, float p_115909_, float p_115910_) {
        if (p_364147_.attachFace == Direction.DOWN) {
            super.setupRotations(p_364147_, p_115908_, p_115909_, p_115910_);
        }
        p_115908_.rotateAround(p_364147_.attachFace.getOpposite().getRotation(), 0.0F, 0.5F, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(VenochemRenderState p_110775_1_) {
        return TEXTURE;
    }
}