package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.ModModelLayers;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.entity.Gokkur;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GokkurRenderer<T extends Gokkur> extends MobRenderer<T, GokkurModel<T>> {
	private static final ResourceLocation GOKKUR = new ResourceLocation(FrostRealm.MODID, "textures/entity/gokkur/gokkur.png");

	public GokkurRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new GokkurModel<>(p_173952_.bakeLayer(ModModelLayers.GOKKUR)), 0.25F);
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		float size = p_115314_.getScale();
		p_115315_.scale(size, size, size);
		super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	protected void setupRotations(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
		super.setupRotations(p_115317_, p_115318_, p_115319_, p_115320_, p_115321_);
		if (p_115317_.isRolling()) {
			p_115318_.translate(0.0D, (double) p_115317_.getBbHeight() / 2, 0.0D);
			p_115318_.mulPose(Vector3f.XP.rotationDegrees((float) (-((float) p_115317_.tickCount + p_115321_) * 40.0F + (20.0F * p_115317_.getDeltaMovement().horizontalDistanceSqr()))));
			p_115318_.translate(0.0D, -(double) p_115317_.getBbHeight() / 2, 0.0D);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return GOKKUR;
	}
}