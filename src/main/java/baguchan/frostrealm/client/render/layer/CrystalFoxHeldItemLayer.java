package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.CrystalFoxModel;
import baguchan.frostrealm.client.render.state.CrystalFoxRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrystalFoxHeldItemLayer extends RenderLayer<CrystalFoxRenderState, CrystalFoxModel<CrystalFoxRenderState>> {
	private final ItemRenderer itemRenderer;

	public CrystalFoxHeldItemLayer(RenderLayerParent<CrystalFoxRenderState, CrystalFoxModel<CrystalFoxRenderState>> p_234838_, ItemRenderer p_362296_) {
		super(p_234838_);
		this.itemRenderer = p_362296_;
	}

	public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, CrystalFoxRenderState p_360609_, float p_117011_, float p_117012_) {
		BakedModel bakedmodel = p_360609_.getMainHandItemModel();
		ItemStack itemstack = p_360609_.getMainHandItem();
		if (bakedmodel != null && !itemstack.isEmpty()) {
			boolean flag1 = p_360609_.isBaby;
			p_117007_.pushPose();
			this.getParentModel().main.translateAndRotate(p_117007_);
			this.getParentModel().body.translateAndRotate(p_117007_);
			p_117007_.translate(this.getParentModel().head.x / 16.0F, this.getParentModel().head.y / 16.0F, this.getParentModel().head.z / 16.0F);
			if (flag1) {
				float f = 0.75F;
				p_117007_.scale(0.75F, 0.75F, 0.75F);
			}

			p_117007_.mulPose(Axis.YP.rotationDegrees(p_117011_));
			p_117007_.mulPose(Axis.XP.rotationDegrees(p_117012_));

			if (p_360609_.isBaby) {
				p_117007_.translate(0.06F, 0.175F, -0.8F);
			} else {
				p_117007_.translate(0.06F, 0.115F, -0.8F);
			}

			p_117007_.mulPose(Axis.XP.rotationDegrees(90.0F));

			this.itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, p_117007_, p_117008_, p_117009_, OverlayTexture.NO_OVERLAY, bakedmodel);
			p_117007_.popPose();
		}
	}
}
