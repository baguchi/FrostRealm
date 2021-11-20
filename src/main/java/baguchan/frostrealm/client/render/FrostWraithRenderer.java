package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.ModModelLayers;
import baguchan.frostrealm.client.model.FrostWraithModel;
import baguchan.frostrealm.client.render.layer.TransparentLayer;
import baguchan.frostrealm.entity.FrostWraith;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostWraithRenderer<T extends FrostWraith> extends MobRenderer<T, FrostWraithModel<T>> {
	private static final ResourceLocation WRAITH = new ResourceLocation(FrostRealm.MODID, "textures/entity/frost_wraith/frost_wraith.png");
	private static final ResourceLocation WRAITH_LAYER = new ResourceLocation(FrostRealm.MODID, "textures/entity/frost_wraith/frost_wraith_layer.png");


	public FrostWraithRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new FrostWraithModel<>(p_173952_.bakeLayer(ModModelLayers.FROST_WRAITH)), 0.5F);
		this.addLayer(new TransparentLayer<>(this, new FrostWraithModel<>(p_173952_.bakeLayer(ModModelLayers.FROST_WRAITH)), WRAITH_LAYER));
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return WRAITH;
	}
}