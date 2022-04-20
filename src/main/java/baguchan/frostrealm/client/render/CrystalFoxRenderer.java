package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.CrystalFoxModel;
import baguchan.frostrealm.entity.CrystalFox;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalFoxRenderer extends MobRenderer<CrystalFox, CrystalFoxModel<CrystalFox>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox.png");
	private static final ResourceLocation SHEARED_TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_sheared.png");


	public CrystalFoxRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new CrystalFoxModel<>(p_173952_.bakeLayer(FrostModelLayers.CRYSTAL_FOX)), 0.5F);
	}

	@Override
	protected void scale(CrystalFox p_115314_, PoseStack p_115315_, float p_115316_) {
		float size = p_115314_.getScale();
		p_115315_.scale(size, size, size);
		super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalFox p_110775_1_) {
		return p_110775_1_.isShearableWithoutConditions() ? TEXTURE : SHEARED_TEXTURE;
	}
}