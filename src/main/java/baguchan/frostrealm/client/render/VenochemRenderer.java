package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.VenochemModel;
import baguchan.frostrealm.client.render.state.VenochemRenderState;
import baguchan.frostrealm.entity.hostile.Venochem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
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

    }

    /*@Override
    protected void setupRotations(VenochemRenderState entity, PoseStack poseStack, float p_115909_, float p_115910_) {
        float progresso = 1.0F - entity.attachProgress;

        float trans = 6.5F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            if (entity.attachFace == Direction.DOWN) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - p_115909_));
                poseStack.translate(0.0D, trans, 0.0D);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90 * (1 - progresso)));

                poseStack.translate(0.0D, -trans, 0.0D);

            } else if (entity.attachFace == Direction.UP) {
                poseStack.translate(0.0D, trans, 0.0D);

                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - p_115909_));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(0.0D, -trans, 0.0D);

            } else {
                poseStack.translate(0.0D, trans, 0.0D);
                switch (entity.attachFace) {
                    case NORTH:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F * progresso));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                        break;
                    case SOUTH:
                        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F * progresso));
                        break;
                    case WEST:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(90F - 90.0F * progresso));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                        break;
                    case EAST:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F * progresso - 90F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                        break;
                }
                poseStack.translate(0.0D, -trans, 0.0D);
            }
        } else {
            super.setupRotations(entity, poseStack, p_115909_, p_115910_);
        }
    }*/

    @Override
    public ResourceLocation getTextureLocation(VenochemRenderState p_110775_1_) {
        return TEXTURE;
    }
}