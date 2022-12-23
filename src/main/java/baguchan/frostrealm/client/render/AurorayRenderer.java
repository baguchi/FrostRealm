package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.AurorayModel;
import baguchan.frostrealm.client.render.layer.AurorayRainbowLayer;
import baguchan.frostrealm.entity.Auroray;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AurorayRenderer<T extends Auroray> extends MobRenderer<T, AurorayModel<T>> {
	private static final ResourceLocation AURORAY = new ResourceLocation(FrostRealm.MODID, "textures/entity/auroray/auroray.png");

	public AurorayRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new AurorayModel<>(p_173952_.bakeLayer(FrostModelLayers.AURORAY)), 0.5F);
		this.addLayer(new AurorayRainbowLayer<>(this));
	}

	protected void setupRotations(T p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
		super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
		p_115686_.mulPose(Axis.XP.rotationDegrees(p_115685_.getXRot()));
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return AURORAY;
	}
}