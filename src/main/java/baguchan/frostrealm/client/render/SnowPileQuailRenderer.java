package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SnowPileQuailModel;
import baguchan.frostrealm.client.render.layer.QuailHeldItemLayer;
import baguchan.frostrealm.client.render.state.SnowPileQuailRenderState;
import baguchan.frostrealm.entity.animal.SnowPileQuail;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class SnowPileQuailRenderer<T extends SnowPileQuail> extends MobRenderer<T, SnowPileQuailRenderState, SnowPileQuailModel<SnowPileQuailRenderState>> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/snowpile_quail.png");

	public SnowPileQuailRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new SnowPileQuailModel<>(p_173952_.bakeLayer(FrostModelLayers.SNOWPILE_QUAIL)), 0.4F);
		this.addLayer(new QuailHeldItemLayer<>(this));
	}

	@Override
    public SnowPileQuailRenderState createRenderState() {
        return new SnowPileQuailRenderState();
	}

	@Override
    protected void scale(SnowPileQuailRenderState p_115314_, PoseStack p_115315_) {
        p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
        super.scale(p_115314_, p_115315_);
    }

	@Override
	public void extractRenderState(T p_362733_, SnowPileQuailRenderState p_360515_, float p_361157_) {
		super.extractRenderState(p_362733_, p_360515_, p_361157_);
		HoldingEntityRenderState.extractHoldingEntityRenderState(p_362733_, p_360515_, this.itemModelResolver);

		p_360515_.popEggAnimationState.copyFrom(p_362733_.popEggAnimationState);
		p_360515_.shakeAnimationState.copyFrom(p_362733_.shakeAnimationState);
		p_360515_.flap = Mth.lerp(p_361157_, p_362733_.oFlap, p_362733_.flap);
		p_360515_.flapSpeed = Mth.lerp(p_361157_, p_362733_.oFlapSpeed, p_362733_.flapSpeed);
	}

	@Override
    public ResourceLocation getTextureLocation(SnowPileQuailRenderState p_110775_1_) {
		return TEXTURE;
	}
}