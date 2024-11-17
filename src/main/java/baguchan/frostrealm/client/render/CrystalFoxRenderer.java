package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.CrystalFoxModel;
import baguchan.frostrealm.client.render.layer.CrystalFoxHeldItemLayer;
import baguchan.frostrealm.client.render.state.CrystalFoxRenderState;
import baguchan.frostrealm.entity.animal.CrystalFox;
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
public class CrystalFoxRenderer extends MobRenderer<CrystalFox, CrystalFoxRenderState, CrystalFoxModel<CrystalFoxRenderState>> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox.png");
	private static final ResourceLocation SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_sheared.png");

	private static final RenderType FOX_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_glow.png"));
	private static final RenderType FOX_EYES_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_eyes_glow.png"));

	public CrystalFoxRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new CrystalFoxModel<>(p_173952_.bakeLayer(FrostModelLayers.CRYSTAL_FOX)), 0.5F);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, CrystalFoxRenderState p_363277_, float p_116987_, float p_116988_) {
				if (p_363277_.shearable) {
					super.render(p_116983_, p_116984_, p_116985_, p_363277_, p_116987_, p_116988_);
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
		this.addLayer(new CrystalFoxHeldItemLayer(this, p_173952_.getItemRenderer()));
	}

	@Override
	public void extractRenderState(CrystalFox p_364137_, CrystalFoxRenderState p_365146_, float p_361192_) {
		super.extractRenderState(p_364137_, p_365146_, p_361192_);
		p_365146_.eatAnimationState.copyFrom(p_364137_.eatAnimationState);
		p_365146_.shearable = p_364137_.isShearableWithoutConditions();
		p_365146_.state = CrystalFox.State.get(p_364137_.getState());
	}

	@Override
	public CrystalFoxRenderState createRenderState() {
		return new CrystalFoxRenderState();
	}

	@Override
	protected void scale(CrystalFoxRenderState p_115314_, PoseStack p_115315_) {
		p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
		super.scale(p_115314_, p_115315_);
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalFoxRenderState p_110775_1_) {
		return p_110775_1_.shearable ? TEXTURE : SHEARED_TEXTURE;
	}
}