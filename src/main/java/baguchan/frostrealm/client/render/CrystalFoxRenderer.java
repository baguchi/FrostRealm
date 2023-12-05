package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.CrystalFoxModel;
import baguchan.frostrealm.client.render.layer.CrystalFoxHeldItemLayer;
import baguchan.frostrealm.entity.CrystalFox;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalFoxRenderer extends MobRenderer<CrystalFox, CrystalFoxModel<CrystalFox>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox.png");
	private static final ResourceLocation SHEARED_TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_sheared.png");

	private static final RenderType FOX_GLOW = RenderType.eyes(new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_glow.png"));
	private static final RenderType FOX_EYES_GLOW = RenderType.eyes(new ResourceLocation(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_eyes_glow.png"));

	public CrystalFoxRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new CrystalFoxModel<>(p_173952_.bakeLayer(FrostModelLayers.CRYSTAL_FOX)), 0.5F);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, CrystalFox p_116986_, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
				if (p_116986_.isShearableWithoutConditions()) {
					super.render(p_116983_, p_116984_, p_116985_, p_116986_, p_116987_, p_116988_, p_116989_, p_116990_, p_116991_, p_116992_);
				}
			}

			@Override
			public RenderType renderType() {
				return FOX_GLOW;
			}
		});
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public RenderType renderType() {
				return FOX_EYES_GLOW;
			}
		});
		this.addLayer(new CrystalFoxHeldItemLayer(this, p_173952_.getItemInHandRenderer()));
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