package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SnowMoleModel;
import baguchan.frostrealm.entity.animal.SnowMole;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;


public class SnowMoleRenderer<T extends SnowMole> extends MobRenderer<T, LivingEntityRenderState, SnowMoleModel<LivingEntityRenderState>> {
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/snow_mole.png");

	public SnowMoleRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new SnowMoleModel<>(p_173952_.bakeLayer(FrostModelLayers.SNOW_MOLE)), 0.4F);
	}

	@Override
	protected void scale(LivingEntityRenderState p_115314_, PoseStack p_115315_) {
		p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
		super.scale(p_115314_, p_115315_);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState p_110775_1_) {
		return TEXTURE;
	}
}