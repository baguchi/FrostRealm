package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.ModModelLayers;
import baguchan.frostrealm.client.model.MarmotModel;
import baguchan.frostrealm.entity.Marmot;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarmotRenderer<T extends Marmot> extends MobRenderer<T, MarmotModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/marmot/marmot.png");
	private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/marmot/marmot_angry.png");


	public MarmotRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new MarmotModel<>(p_173952_.bakeLayer(ModModelLayers.MARMOT)), 0.4F);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}