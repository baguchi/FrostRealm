package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.ClustWraithModel;
import baguchan.frostrealm.entity.ClustWraith;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClustWraithRenderer<T extends ClustWraith> extends MobRenderer<T, ClustWraithModel<T>> {
	private static final ResourceLocation WRAITH = new ResourceLocation(FrostRealm.MODID, "textures/entity/clust_wraith/clust_wraith.png");
	private static final RenderType WRAITH_GLOW = RenderType.eyes(new ResourceLocation(FrostRealm.MODID, "textures/entity/clust_wraith/clust_wraith_glow.png"));


	public ClustWraithRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new ClustWraithModel<>(p_173952_.bakeLayer(FrostModelLayers.CLUST_WRAITH)), 0.5F);
		this.addLayer(new EyesLayer<T, ClustWraithModel<T>>(this) {
			@Override
			public RenderType renderType() {
				return WRAITH_GLOW;
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return WRAITH;
	}
}