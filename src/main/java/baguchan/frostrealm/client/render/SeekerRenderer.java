package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SeekerModel;
import baguchan.frostrealm.client.render.state.SeekerRenderState;
import baguchan.frostrealm.entity.boss.Seeker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
        p_360515_.isAgressive = p_362733_.isAggressive();
        p_360515_.state = p_362733_.getState();
    }

    @Override
    public void render(SeekerRenderState p_361886_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_361886_, p_115311_, p_115312_, p_115313_);
        if (p_361886_.breathAnimationState.isStarted()) {
            p_115311_.pushPose();
            p_115311_.scale(-1.0F, -1.0F, 1.0F);
            p_115311_.translate(0.0F, -1.501F - (2 / 16F), -(2 / 16F));
            this.getModel().all.translateAndRotate(p_115311_);
            this.getModel().body.translateAndRotate(p_115311_);
            this.getModel().neck.translateAndRotate(p_115311_);
            this.getModel().head.translateAndRotate(p_115311_);
            renderEyeRays(p_361886_, p_115311_, 0.8F, p_115312_.getBuffer(RenderType.dragonRays()));
            renderEyeRays(p_361886_, p_115311_, 0.8F, p_115312_.getBuffer(RenderType.dragonRaysDepth()));
            p_115311_.popPose();
        }

    }

    public void translate(ModelPart part, PoseStack p_104300_) {
        p_104300_.translate(part.x / 16.0F, part.y / 16.0F, part.z / 16.0F);
    }


    private static void renderEyeRays(SeekerRenderState seekerRenderState, PoseStack p_352922_, float p_352903_, VertexConsumer p_352894_) {
        p_352922_.pushPose();
        int i = ARGB.colorFromFloat(1.0F - p_352903_, 1.0F, 1.0F, 1.0F);
        Vector3f vector3f = new Vector3f();
        Vector3f vector3f1 = new Vector3f();
        Vector3f vector3f2 = new Vector3f();
        Vector3f vector3f3 = new Vector3f();
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationYXZ((float) ((seekerRenderState.bodyRot - 180.0F) * (Math.PI / 180F)), (float) ((-90) * (Math.PI / 180F)), 0);
        p_352922_.mulPose(quaternionf);
        float f1 = 16F;
        float f2 = p_352903_;
        vector3f1.set(-HALF_SQRT_3 * f2, f1, -f2);
        vector3f2.set(HALF_SQRT_3 * f2, f1, -f2);
        vector3f3.set(0.0F, f1, f2);
        PoseStack.Pose posestack$pose = p_352922_.last();
        p_352894_.addVertex(posestack$pose, vector3f).setColor(i);
        p_352894_.addVertex(posestack$pose, vector3f1).setColor(16711935);
        p_352894_.addVertex(posestack$pose, vector3f2).setColor(16711935);
        p_352894_.addVertex(posestack$pose, vector3f).setColor(i);
        p_352894_.addVertex(posestack$pose, vector3f2).setColor(16711935);
        p_352894_.addVertex(posestack$pose, vector3f3).setColor(16711935);
        p_352894_.addVertex(posestack$pose, vector3f).setColor(i);
        p_352894_.addVertex(posestack$pose, vector3f3).setColor(16711935);
        p_352894_.addVertex(posestack$pose, vector3f1).setColor(16711935);


        p_352922_.popPose();
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
