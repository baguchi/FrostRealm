package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.VenochemModel;
import baguchan.frostrealm.client.render.state.VenochemRenderState;
import baguchan.frostrealm.entity.hostile.Venochem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class VenochemRenderer<T extends Venochem> extends MobRenderer<T, VenochemRenderState, VenochemModel<VenochemRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/venochem/venochem.png");
    private static final RenderType VENOCHEM_GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/venochem/venochem_glow.png"));

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
        p_360515_.rotations = p_362733_.getCellRotation();
        p_360515_.prevRotations = p_362733_.prevRotation;
        p_360515_.attachChangeProgress = p_362733_.getAttachAmount(p_361157_);
    }

    @Override
    protected void setupRotations(VenochemRenderState entity, PoseStack poseStack, float rotationYaw, float p_115910_) {

        float trans = 6.5F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            if (entity.attachFace == Direction.DOWN) {
                super.setupRotations(entity, poseStack, rotationYaw, p_115910_);
            } else {

                float yaw = (float) Math.toDegrees(Mth.atan2(entity.rotations.x(), entity.rotations.z()));
                float pitch = (float) -Math.toDegrees(Mth.atan2(entity.rotations.y(), Math.sqrt(entity.rotations.x() * entity.rotations.x() + entity.rotations.z() * entity.rotations.z())));
                float prevYaw = (float) Math.toDegrees(Mth.atan2(entity.prevRotations.x(), entity.prevRotations.z()));
                float prevPitch = (float) -Math.toDegrees(Mth.atan2(entity.prevRotations.y(), Math.sqrt(entity.prevRotations.x() * entity.prevRotations.x() + entity.prevRotations.z() * entity.prevRotations.z())));
                float realYaw = prevYaw * (1 - entity.attachChangeProgress) - yaw * entity.attachChangeProgress;
                float realPitch = prevPitch * (1 - entity.attachChangeProgress) - pitch * entity.attachChangeProgress;
                poseStack.translate(0.0F, trans, 0.0F);

                poseStack.mulPose(Axis.YP.rotationDegrees(realYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(-90 + realPitch));

                poseStack.translate(0.0F, -trans, 0.0F);
                //poseStack.translate(0.0D, -8F / 16F, 0.0D);

                super.setupRotations(entity, poseStack, 0.0F, p_115910_);
            }
        } else {
            super.setupRotations(entity, poseStack, rotationYaw, p_115910_);
        }
    }

    @Override
    public Identifier getTextureLocation(VenochemRenderState p_110775_1_) {
        return TEXTURE;
    }
}