package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.RootDeerModel;
import baguchan.frostrealm.client.render.layer.CrackingRootDeerLayer;
import baguchan.frostrealm.client.render.state.RootDeerRenderState;
import baguchan.frostrealm.entity.hostile.RootDeer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

public class RootDeerRenderer<T extends RootDeer> extends MobRenderer<T, RootDeerRenderState, RootDeerModel<RootDeerRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/root_deer/root_deer.png");
    private static final RenderType GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/root_deer/root_deer_eye.png"));

    public RootDeerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new RootDeerModel<>(p_173952_.bakeLayer(FrostModelLayers.ROOT_DEER)), 0.5F);
        this.addLayer(new CrackingRootDeerLayer<>(this));
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return GLOW;
            }
        });
    }

    @Override
    public void extractRenderState(T p_362733_, RootDeerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.direction = p_362733_.getAttachFace();
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.summonAnimationState.copyFrom(p_362733_.summonAnimationState);
        p_360515_.deathAnimationState.copyFrom(p_362733_.deathAnimationState);
        p_360515_.crackiness = p_362733_.getCrackiness();
    }

    @Override
    protected void setupRotations(RootDeerRenderState p_320913_, PoseStack p_115891_, float p_115892_, float p_115893_) {


        p_115891_.rotateAround(p_320913_.direction.getOpposite().getRotation(), 0.0F, 0F, 0.0F);
        if (p_320913_.direction.getAxis() == Direction.Axis.Y) {
            p_115891_.translate(0, 0, 0.5);
        }

        if (p_320913_.direction == Direction.DOWN) {
            p_115891_.translate(0, 1, 0);
        }
        p_115891_.mulPose(Axis.XP.rotationDegrees(270.0F));
        p_115891_.mulPose(Axis.YP.rotationDegrees(180.0F));

    }

    @Override
    protected float getFlipDegrees() {
        return 0.0F;
    }

    @Override
    public RootDeerRenderState createRenderState() {
        return new RootDeerRenderState();
    }

    @Override
    public Identifier getTextureLocation(RootDeerRenderState p_110775_1_) {
        return TEXTURE;
    }
}