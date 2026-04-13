package baguchan.frostrealm.client.render;

import baguchan.frostrealm.client.render.state.FlyBlockRenderState;
import baguchan.frostrealm.entity.projectile.FlyingBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

/**
 * <p>Revamped Falling Block Renderer.</p>
 * <p>Structure based on <a href=https://github.com/TeamTwilight/twilightforest/blob/1.19.x/src/main/java/twilightforest/client/renderer/entity/ThrownBlockRenderer.java>ThrownBlockRenderer</a></p>
 *
 * @author bagu_chan
 */

public class FlyingBlockRenderer extends EntityRenderer<FlyingBlockEntity, FlyBlockRenderState> {

    public FlyingBlockRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.shadowRadius = 0.5F;
    }

    public boolean shouldRender(FlyingBlockEntity p_362415_, Frustum p_364047_, double p_362218_, double p_363427_, double p_361722_) {
        return super.shouldRender(p_362415_, p_364047_, p_362218_, p_363427_, p_361722_);
    }

    public void submit(FlyBlockRenderState p_450955_, PoseStack poseStack, SubmitNodeCollector p_433266_, net.minecraft.client.renderer.state.level.CameraRenderState p_451470_) {
        BlockState blockstate = p_450955_.movingBlockRenderState.blockState;
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            poseStack.pushPose();
            poseStack.translate(0, -0.5, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(-p_450955_.yRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(p_450955_.xRot));

            poseStack.translate(-0.5, -0.5, -0.5);
            p_433266_.submitMovingBlock(poseStack, p_450955_.movingBlockRenderState);
            poseStack.popPose();
            super.submit(p_450955_, poseStack, p_433266_, p_451470_);
        }
    }

    public FlyBlockRenderState createRenderState() {
        return new FlyBlockRenderState();
    }

    public void extractRenderState(FlyingBlockEntity entity, FlyBlockRenderState renderState, float p_361019_) {
        super.extractRenderState(entity, renderState, p_361019_);
        BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
        //p_360509_.movingBlockRenderState.randomSeedPos = p_364559_.getStartPos();
        renderState.movingBlockRenderState.blockPos = blockpos;
        renderState.movingBlockRenderState.blockState = entity.getBlockState();
        if (entity.level() instanceof ClientLevel clientLevel) {
            BlockPos pos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());

            renderState.movingBlockRenderState.biome = clientLevel.getBiome(pos);
            renderState.movingBlockRenderState.cardinalLighting = clientLevel.cardinalLighting();
            renderState.movingBlockRenderState.lightEngine = clientLevel.getLightEngine();
        }
        renderState.xRot = entity.getXRot(p_361019_);
        renderState.yRot = entity.getYRot(p_361019_);
    }
}