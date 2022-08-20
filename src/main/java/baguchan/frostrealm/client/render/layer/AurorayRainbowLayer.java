package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.Auroray;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class AurorayRainbowLayer<T extends Auroray, M extends EntityModel<T>> extends RenderLayer<T, M> {

	private static final ResourceLocation AURORAY_LAYER = new ResourceLocation(FrostRealm.MODID, "textures/entity/auroray/auroray_layer.png");

	public AurorayRainbowLayer(RenderLayerParent<T, M> p_116981_) {
		super(p_116981_);
	}

	public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, T p_116986_, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
		if (!p_116986_.isInvisible() && !p_116986_.isAggressive()) {
			float f;
			float f1;
			float f2;
			int i1 = 25;
			int i = p_116986_.tickCount / 25 + p_116986_.getId();
			int j = DyeColor.values().length;
			int k = i % j;
			int l = (i + 1) % j;
			float f3 = ((float) (p_116986_.tickCount % 25) + p_116989_) / 25.0F;
			float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
			float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
			f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
			f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
			f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;

			VertexConsumer vertexconsumer = p_116984_.getBuffer(RenderType.eyes(AURORAY_LAYER));
			this.getParentModel().renderToBuffer(p_116983_, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
		}
	}
}