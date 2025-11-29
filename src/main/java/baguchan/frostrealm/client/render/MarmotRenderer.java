package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.MarmotModel;
import baguchan.frostrealm.client.render.state.MarmotRenderState;
import baguchan.frostrealm.entity.animal.Marmot;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;


public class MarmotRenderer<T extends Marmot> extends MobRenderer<T, MarmotRenderState, MarmotModel<MarmotRenderState>> {
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/marmot/marmot.png");
	private static final Identifier ANGRY_TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/marmot/marmot_angry.png");


	public MarmotRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new MarmotModel<>(p_173952_.bakeLayer(FrostModelLayers.MARMOT)), 0.4F);
	}

	@Override
	public void extractRenderState(T p_362733_, MarmotRenderState p_360515_, float p_361157_) {
		super.extractRenderState(p_362733_, p_360515_, p_361157_);
		p_360515_.scream = p_362733_.isStanding();
	}

	@Override
	public MarmotRenderState createRenderState() {
		return new MarmotRenderState();
	}

	@Override
	protected void scale(MarmotRenderState p_115314_, PoseStack p_115315_) {
		p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
		super.scale(p_115314_, p_115315_);
	}

	@Override
	public Identifier getTextureLocation(MarmotRenderState p_110775_1_) {
		return TEXTURE;
	}
}