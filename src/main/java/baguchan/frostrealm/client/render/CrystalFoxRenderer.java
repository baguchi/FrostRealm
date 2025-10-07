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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.resources.ResourceLocation;


public class CrystalFoxRenderer extends MobRenderer<CrystalFox, CrystalFoxRenderState, CrystalFoxModel<CrystalFoxRenderState>> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox.png");
	private static final ResourceLocation SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_sheared.png");
	private static final ResourceLocation SLEEP_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_sleep.png");
	private static final ResourceLocation SLEEP_SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_sleep_sheared.png");

	private static final RenderType FOX_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_glow.png"));
	private static final RenderType FOX_EYES_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/crystal_fox/crystal_fox_eyes_glow.png"));

	public CrystalFoxRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new CrystalFoxModel<>(p_173952_.bakeLayer(FrostModelLayers.CRYSTAL_FOX)), 0.5F);
		this.addLayer(new EyesLayer<>(this) {

            @Override
            public void submit(PoseStack p_433452_, SubmitNodeCollector p_433171_, int p_434650_, CrystalFoxRenderState p_435883_, float p_433542_, float p_435619_)
            {
                if (p_435883_.shearable) {
                    super.submit(p_433452_, p_433171_, p_434650_, p_435883_, p_433542_, p_435619_);
                }
            }

            @Override
			public RenderType renderType() {
				return FOX_GLOW;
			}
		});
		this.addLayer(new EyesLayer<>(this) {
            @Override
            public void submit(PoseStack p_433452_, SubmitNodeCollector p_433171_, int p_434650_, CrystalFoxRenderState p_435883_, float p_433542_, float p_435619_)
            {
                if (p_435883_.state != CrystalFox.State.SLEEPING) {
                    super.submit(p_433452_, p_433171_, p_434650_, p_435883_, p_433542_, p_435619_);
                }
            }

			@Override
			public RenderType renderType() {
				return FOX_EYES_GLOW;
			}
		});
		this.addLayer(new CrystalFoxHeldItemLayer(this));
	}

	@Override
	public void extractRenderState(CrystalFox p_364137_, CrystalFoxRenderState p_365146_, float p_361192_) {
		super.extractRenderState(p_364137_, p_365146_, p_361192_);
		HoldingEntityRenderState.extractHoldingEntityRenderState(p_364137_, p_365146_, this.itemModelResolver);

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
		if (p_110775_1_.state == CrystalFox.State.SLEEPING) {
			return p_110775_1_.shearable ? SLEEP_TEXTURE : SLEEP_SHEARED_TEXTURE;
		}
		return p_110775_1_.shearable ? TEXTURE : SHEARED_TEXTURE;
	}
}