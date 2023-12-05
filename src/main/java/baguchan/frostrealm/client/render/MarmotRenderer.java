package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.MarmotModel;
import baguchan.frostrealm.entity.Marmot;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarmotRenderer<T extends Marmot> extends MobRenderer<T, MarmotModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/marmot/marmot.png");
	private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/marmot/marmot_angry.png");


	public MarmotRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new MarmotModel<>(p_173952_.bakeLayer(FrostModelLayers.MARMOT)), 0.4F);
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		float size = p_115314_.getScale();
		p_115315_.scale(size, size, size);
		super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}