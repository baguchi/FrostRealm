package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.GokkudilloModel;
import baguchan.frostrealm.entity.Gokkudillo;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GokkudilloRenderer<T extends Gokkudillo> extends MobRenderer<T, GokkudilloModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/gokkur/gokkudillo.png");
	private static final RenderType EYES = RenderType.eyes(new ResourceLocation(FrostRealm.MODID, "textures/entity/gokkur/gokkudillo_glow.png"));


	public GokkudilloRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new GokkudilloModel<>(p_173952_.bakeLayer(FrostModelLayers.GOKKUDILLO)), 0.6F);
		this.addLayer(new EyesLayer<T, GokkudilloModel<T>>(this) {
			@Override
			public RenderType renderType() {
				return EYES;
			}
		});
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		float size = p_115314_.getScale();
		p_115315_.scale(1.8F, 1.8F, 1.8F);
        super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}