package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowGokkurLayer<T extends GokkurRenderState> extends RenderLayer<T, GokkurModel<T>> {
    private final BlockRenderDispatcher blockRenderer;

    public SnowGokkurLayer(RenderLayerParent<T, GokkurModel<T>> p_234850_, BlockRenderDispatcher p_234851_) {
        super(p_234850_);
        this.blockRenderer = p_234851_;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector p_435807_, int p_433104_, T p_434616_, float p_433223_, float p_433380_) {

            if (!p_434616_.isInvisible || p_434616_.appearsGlowing()) {
                poseStack.pushPose();
                float f = p_434616_.snowProgress;
                this.getParentModel().root.translateAndRotate(poseStack);
                this.getParentModel().body_rotation.translateAndRotate(poseStack);
                poseStack.scale(f, f, f);
                BlockState blockstate = Blocks.SNOW_BLOCK.defaultBlockState();
                BlockStateModel blockstatemodel = this.blockRenderer.getBlockModel(blockstate);
                int i = LivingEntityRenderer.getOverlayCoords(p_434616_, 0.0F);
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                RenderType rendertype = p_434616_.appearsGlowing() && p_434616_.isInvisible
                        ? RenderType.outline(TextureAtlas.LOCATION_BLOCKS)
                        : ItemBlockRenderTypes.getRenderType(blockstate);
                p_435807_.submitBlockModel(poseStack, rendertype, blockstatemodel, 0.0F, 0.0F, 0.0F, p_433104_, i, p_434616_.outlineColor);
                poseStack.popPose();
            }
    }
}
