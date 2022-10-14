package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.PurifiedStrayModel;
import baguchan.frostrealm.client.render.layer.PurifiedStrayClothingLayer;
import baguchan.frostrealm.entity.PurifiedStray;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class PurifiedStrayRenderer<T extends PurifiedStray> extends HumanoidMobRenderer<T, PurifiedStrayModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/gazener/purified_stray.png");

	public PurifiedStrayRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new PurifiedStrayModel<>(p_173952_.bakeLayer(ModelLayers.STRAY)), 0.5F);
		this.addLayer(new HumanoidArmorLayer<>(this, new PurifiedStrayModel<>(p_173952_.bakeLayer(ModelLayers.STRAY_INNER_ARMOR)), new PurifiedStrayModel<>(p_173952_.bakeLayer(ModelLayers.STRAY_OUTER_ARMOR))));
		this.addLayer(new PurifiedStrayClothingLayer<>(this, p_173952_.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}