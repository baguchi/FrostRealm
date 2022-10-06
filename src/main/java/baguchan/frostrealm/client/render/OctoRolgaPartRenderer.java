package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.OctorolgaPartModel;
import baguchan.frostrealm.client.render.layer.OctorolgaPartMagmaLayer;
import baguchan.frostrealm.entity.OctorolgaPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OctoRolgaPartRenderer<T extends OctorolgaPart> extends MobRenderer<T, OctorolgaPartModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/octorolga.png");

	public OctoRolgaPartRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new OctorolgaPartModel<>(p_173952_.bakeLayer(FrostModelLayers.OCTOROLGA_PART)), 0.5F);
		this.addLayer(new OctorolgaPartMagmaLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}