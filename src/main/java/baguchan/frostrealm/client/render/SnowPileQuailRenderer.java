package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SnowPileQuailModel;
import baguchan.frostrealm.client.render.layer.QuailHeldItemLayer;
import baguchan.frostrealm.entity.animal.SnowPileQuail;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowPileQuailRenderer<T extends SnowPileQuail> extends MobRenderer<T, SnowPileQuailModel<T>> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/snowpile_quail.png");

	public SnowPileQuailRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new SnowPileQuailModel<>(p_173952_.bakeLayer(FrostModelLayers.SNOWPILE_QUAIL)), 0.4F);
		this.addLayer(new QuailHeldItemLayer<>(this));
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		p_115315_.scale(p_115314_.getAgeScale(), p_115314_.getAgeScale(), p_115314_.getAgeScale());
		super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}