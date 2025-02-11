package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostCrawlerModel;
import baguchan.frostrealm.client.render.state.FrostCrawlerRenderState;
import baguchan.frostrealm.entity.hostile.FrostCrawler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class FrostCrawlerRenderer<T extends FrostCrawler> extends MobRenderer<T, FrostCrawlerRenderState, FrostCrawlerModel<FrostCrawlerRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_crawler.png");

    public FrostCrawlerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FrostCrawlerModel<>(p_173952_.bakeLayer(FrostModelLayers.FROST_CRAWLER)), 0.5F);
    }

    @Override
    public FrostCrawlerRenderState createRenderState() {
        return new FrostCrawlerRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, FrostCrawlerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.attachFace = p_362733_.getAttachFacing();

    }


    @Override
    protected void setupRotations(FrostCrawlerRenderState entity, PoseStack poseStack, float p_115909_, float p_115910_) {

        float trans = 6.5F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            if (entity.attachFace == Direction.DOWN) {
                super.setupRotations(entity, poseStack, p_115909_, p_115910_);
            } else if (entity.attachFace == Direction.UP) {
                poseStack.translate(0.0D, trans, 0.0D);

                poseStack.mulPose(Axis.YP.rotationDegrees(-p_115909_));
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                //poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(0.0D, -trans, 0.0D);
                poseStack.translate(0.0D, -8F / 16F, 0.0D);
            } else {
                poseStack.translate(0.0D, trans, 0.0D);
                poseStack.mulPose(Axis.YP.rotationDegrees(180 - entity.attachFace.toYRot()));
                poseStack.translate(0.0D, -trans, 0.0D);
                poseStack.translate(0.0D, -8F / 16F, 0.0D);
            }
        } else {
            super.setupRotations(entity, poseStack, p_115909_, p_115910_);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(FrostCrawlerRenderState p_110775_1_) {
        return TEXTURE;
    }
}