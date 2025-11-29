package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.layer.CrackingGokkurLayer;
import baguchan.frostrealm.client.render.layer.SnowGokkurLayer;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import baguchan.frostrealm.entity.hostile.Gokkur;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class GokkurRenderer<T extends Gokkur> extends MobRenderer<T, GokkurRenderState, GokkurModel<GokkurRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur.png");
    private static final Identifier GRASS_TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_grass.png");
    private static final RenderType GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_eye.png"));

    public GokkurRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new GokkurModel<>(p_173952_.bakeLayer(FrostModelLayers.GOKKUR)), 0.5F);
        this.addLayer(new SnowGokkurLayer<>(this, p_173952_.getBlockRenderDispatcher()));
        this.addLayer(new CrackingGokkurLayer<>(this));
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return GLOW;
            }
        });
    }

    @Override
    public void extractRenderState(T p_362733_, GokkurRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.grass = p_362733_.isGrass();
        p_360515_.snowProgress = p_362733_.getSnowProgress();
        p_360515_.rollAnimationState.copyFrom(p_362733_.rollAnimationState);
        p_360515_.startRollAnimationState.copyFrom(p_362733_.startRollAnimationState);
        p_360515_.crackiness = p_362733_.getCrackiness();
    }

    @Override
    public GokkurRenderState createRenderState() {
        return new GokkurRenderState();
    }

    @Override
    public Identifier getTextureLocation(GokkurRenderState entity) {
        if (entity.grass) {
            return GRASS_TEXTURE;
        }
        return TEXTURE;
    }
}