package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.CrystalTortoiseModel;
import baguchan.frostrealm.entity.CrystalTortoise;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalTortoiseRenderer<T extends CrystalTortoise> extends MobRenderer<T, CrystalTortoiseModel<T>> {
	private static final ResourceLocation CRYSTAL_TORTOISE = new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_tortoise.png");

	public CrystalTortoiseRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new CrystalTortoiseModel<>(p_173952_.bakeLayer(FrostModelLayers.CRYSTAL_TORTOISE)), 0.55F);
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		float size = p_115314_.getScale();
		p_115315_.scale(size, size, size);
		super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return CRYSTAL_TORTOISE;
	}
}