package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SeekerModel;
import baguchan.frostrealm.client.render.state.SeekerRenderState;
import baguchan.frostrealm.entity.boss.Seeker;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class SeekerRenderer<T extends Seeker> extends MobRenderer<T, SeekerRenderState, SeekerModel<SeekerRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seeker/seeker.png");
    private static final RenderType WRAITH_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/seeker/seeker_eye.png"));

    private static final float HALF_SQRT_3 = (float) (Math.sqrt(60.0) / 2.0);

    public SeekerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SeekerModel<>(p_173952_.bakeLayer(FrostModelLayers.SEEKER)), 0.5F);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return WRAITH_GLOW;
            }
        });
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public SeekerRenderState createRenderState() {
        return new SeekerRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, SeekerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        ArmedEntityRenderState.extractArmedEntityRenderState(p_362733_, p_360515_, this.itemModelResolver);
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.preAttackAnimationState.copyFrom(p_362733_.preAttackAnimationState);
        p_360515_.stopAttackAnimationState.copyFrom(p_362733_.stopAttackAnimationState);
        p_360515_.deathAnimationState.copyFrom(p_362733_.deathAnimationState);
        p_360515_.breathAnimationState.copyFrom(p_362733_.breathAnimationState);
        p_360515_.breathPreAnimationState.copyFrom(p_362733_.breathPreAnimationState);
        p_360515_.breathStopAnimationState.copyFrom(p_362733_.breathStopAnimationState);
        p_360515_.isAgressive = p_362733_.isAggressive();
        p_360515_.state = p_362733_.getState();
    }

    public void translate(ModelPart part, PoseStack p_104300_) {
        p_104300_.translate(part.x / 16.0F, part.y / 16.0F, part.z / 16.0F);
    }


    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    @Override
    public ResourceLocation getTextureLocation(SeekerRenderState p_110775_1_) {
        return TEXTURE;
    }
}
