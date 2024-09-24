package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.VenochemModel;
import baguchan.frostrealm.entity.hostile.Venochem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class VenochemRenderer<T extends Venochem> extends MobRenderer<T, VenochemModel<T>> {
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
    protected void setupRotations(T entity, PoseStack poseStack, float ageInTick, float rotationYaw, float partialTicks, float p_320045_) {
        float trans = /*entity.isBaby() ? 0.25F : */6.5F / 16F;
        if (entity.getPose() != Pose.SLEEPING) {
            float progresso = 1F - (entity.prevAttachChangeProgress + (entity.attachChangeProgress - entity.prevAttachChangeProgress) * partialTicks);

            if (entity.getAttachFacing() == Direction.DOWN) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
                poseStack.translate(0.0D, trans, 0.0D);
                if (entity.yo < entity.getY()) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(90 * (1 - progresso)));
                } else {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90 * (1 - progresso)));
                }
                poseStack.translate(0.0D, -trans, 0.0D);

            } else if (entity.getAttachFacing() == Direction.UP) {
                poseStack.translate(0.0D, trans, 0.0D);

                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(0.0D, -trans, 0.0D);

            } else {
                poseStack.translate(0.0D, trans, 0.0D);
                switch (entity.getAttachFacing()) {
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
                if (entity.getDeltaMovement().y <= -0.001F) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                }
                poseStack.translate(0.0D, -trans, 0.0D);
            }
        } else {
            super.setupRotations(entity, poseStack, ageInTick, rotationYaw, partialTicks, p_320045_);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}