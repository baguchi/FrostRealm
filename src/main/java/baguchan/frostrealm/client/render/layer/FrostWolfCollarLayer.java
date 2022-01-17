package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.FrostWolfModel;
import baguchan.frostrealm.entity.FrostWolf;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostWolfCollarLayer extends RenderLayer<FrostWolf, FrostWolfModel<FrostWolf>> {
	private static final ResourceLocation WOLF_COLLAR_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/entity/frost_wolf/frost_wolf_collar.png");

	public FrostWolfCollarLayer(RenderLayerParent<FrostWolf, FrostWolfModel<FrostWolf>> p_117707_) {
		super(p_117707_);
	}

	public void render(PoseStack p_117720_, MultiBufferSource p_117721_, int p_117722_, FrostWolf p_117723_, float p_117724_, float p_117725_, float p_117726_, float p_117727_, float p_117728_, float p_117729_) {
		if (p_117723_.isTame() && !p_117723_.isInvisible()) {
			float[] afloat = p_117723_.getCollarColor().getTextureDiffuseColors();
			renderColoredCutoutModel(this.getParentModel(), WOLF_COLLAR_LOCATION, p_117720_, p_117721_, p_117722_, p_117723_, afloat[0], afloat[1], afloat[2]);
		}
	}
}