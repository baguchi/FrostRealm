package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.CrystalFoxModel;
import baguchan.frostrealm.client.render.state.CrystalFoxRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class CrystalFoxHeldItemLayer extends RenderLayer<CrystalFoxRenderState, CrystalFoxModel<CrystalFoxRenderState>> {

	public CrystalFoxHeldItemLayer(RenderLayerParent<CrystalFoxRenderState, CrystalFoxModel<CrystalFoxRenderState>> p_234838_) {
		super(p_234838_);
	}

	public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, CrystalFoxRenderState p_360609_, float p_117011_, float p_117012_) {
		if (!p_360609_.heldItem.isEmpty()) {
			boolean flag1 = p_360609_.isBaby;
			p_117007_.pushPose();
			this.getParentModel().main.translateAndRotate(p_117007_);
			this.getParentModel().body.translateAndRotate(p_117007_);
			this.getParentModel().head.translateAndRotate(p_117007_);

			//p_117007_.mulPose(Axis.YP.rotationDegrees(p_117011_));
			//p_117007_.mulPose(Axis.XP.rotationDegrees(p_117012_));

			if (p_360609_.isBaby) {
				p_117007_.translate(0.06F, 0.175F, -0.7F);
			} else {
				p_117007_.translate(0.06F, 0.115F, -0.7F);
			}

			p_117007_.mulPose(Axis.XP.rotationDegrees(90.0F));

			p_360609_.heldItem.render(p_117007_, p_117008_, p_117009_, OverlayTexture.NO_OVERLAY);
			p_117007_.popPose();
		}
	}
}
