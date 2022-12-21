package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WarpedIceModel;
import baguchan.frostrealm.entity.WarpedIceSoul;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WarpedIceRender<T extends WarpedIceSoul> extends MobRenderer<T, WarpedIceModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/warped_ice/warped_ice.png");

	public WarpedIceRender(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new WarpedIceModel<>(p_173952_.bakeLayer(FrostModelLayers.WARPED_ICE)), 0.4F);
		this.addLayer(new ItemInHandLayer<>(this, p_173952_.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}

	protected int getBlockLightLevel(T p_234560_, BlockPos p_234561_) {
		return 15;
	}
}