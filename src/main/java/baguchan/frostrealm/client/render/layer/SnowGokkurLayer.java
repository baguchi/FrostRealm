package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowGokkurLayer<T extends GokkurRenderState> extends RenderLayer<T, GokkurModel<T>> {
    private final BlockRenderDispatcher blockRenderer;

    public SnowGokkurLayer(RenderLayerParent<T, GokkurModel<T>> p_234850_, BlockRenderDispatcher p_234851_) {
        super(p_234850_);
        this.blockRenderer = p_234851_;
    }

    public void render(PoseStack p_117256_, MultiBufferSource p_117257_, int p_117258_, T p_361786_, float p_117260_, float p_117261_) {
        if (!p_361786_.isBaby) {
            boolean flag = p_361786_.appearsGlowing && p_361786_.isInvisible;
            if (!p_361786_.isInvisible || flag) {
                BlockState blockstate = Blocks.SNOW_BLOCK.defaultBlockState();
                int i = LivingEntityRenderer.getOverlayCoords(p_361786_, 0.0F);
                BakedModel bakedmodel = this.blockRenderer.getBlockModel(blockstate);
                p_117256_.pushPose();
                float f = p_361786_.snowProgress;
                this.getParentModel().root.translateAndRotate(p_117256_);
                this.getParentModel().body_rotation.translateAndRotate(p_117256_);
                p_117256_.scale(f, f, f);

                p_117256_.translate(-0.5F, -0.5F, -0.5F);
                this.renderBlock(p_117256_, p_117257_, p_117258_, flag, blockstate, i, bakedmodel);
                p_117256_.popPose();
            }
        }
    }

    private void renderBlock(
            PoseStack p_234853_, MultiBufferSource p_234854_, int p_234855_, boolean p_234856_, BlockState p_234857_, int p_234858_, BakedModel p_234859_
    ) {
        if (p_234856_) {
            this.blockRenderer
                    .getModelRenderer()
                    .renderModel(
                            p_234853_.last(),
                            p_234854_.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)),
                            p_234857_,
                            p_234859_,
                            0.0F,
                            0.0F,
                            0.0F,
                            p_234855_,
                            p_234858_
                    );
        } else {
            this.blockRenderer.renderSingleBlock(p_234857_, p_234853_, p_234854_, p_234855_, p_234858_);
        }
    }
}
