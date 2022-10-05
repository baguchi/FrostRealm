package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.OctorolgaModel;
import baguchan.frostrealm.client.render.layer.OctorolgaMagmaLayer;
import baguchan.frostrealm.entity.Octorolga;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OctoRolgaRenderer<T extends Octorolga> extends MobRenderer<T, OctorolgaModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/octorolga.png");

	public OctoRolgaRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new OctorolgaModel<>(p_173952_.bakeLayer(FrostModelLayers.OCTOROLGA)), 0.65F);
		this.addLayer(new OctorolgaMagmaLayer<>(this));
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		super.scale(p_115314_, p_115315_, p_115316_);
		p_115315_.scale(p_115314_.getScale(), p_115314_.getScale(), p_115314_.getScale());
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}