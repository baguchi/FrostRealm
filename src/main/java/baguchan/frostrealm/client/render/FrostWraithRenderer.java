package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostWraithModel;
import baguchan.frostrealm.client.render.state.FrostWraithRenderState;
import baguchan.frostrealm.entity.hostile.FrostWraith;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;


public class FrostWraithRenderer extends MobRenderer<FrostWraith, FrostWraithRenderState, FrostWraithModel<FrostWraithRenderState>> {
	private static final Identifier WRAITH = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_wraith/frost_wraith.png");
	private static final RenderType WRAITH_GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_wraith/frost_wraith_glow.png"));

	public FrostWraithRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new FrostWraithModel<>(p_173952_.bakeLayer(FrostModelLayers.FROST_WRAITH)), 0.5F);
		this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return WRAITH_GLOW;
            }
        });
	}

	@Override
	public FrostWraithRenderState createRenderState() {
		return new FrostWraithRenderState();
	}

	@Override
	public Identifier getTextureLocation(FrostWraithRenderState p_368654_) {
		return WRAITH;
	}
}