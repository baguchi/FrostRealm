package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.ModModelLayers;
import baguchan.frostrealm.client.model.YetiModel;
import baguchan.frostrealm.client.render.layer.YetiItemInHandLayer;
import baguchan.frostrealm.entity.Yeti;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YetiRenderer<T extends Yeti> extends MobRenderer<T, YetiModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/yeti/yeti.png");

	public YetiRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new YetiModel<>(p_173952_.bakeLayer(ModModelLayers.YETI)), 0.75F);
		this.addLayer(new CustomHeadLayer<>(this, p_173952_.getModelSet(), 1.0F, 1.0F, 1.0F));
		this.addLayer(new YetiItemInHandLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}