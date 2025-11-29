package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.layer.CrackingGokkurLayer;
import baguchan.frostrealm.client.render.state.CrystalFoxRenderState;
import baguchan.frostrealm.client.render.state.UnderGokkurRenderState;
import baguchan.frostrealm.entity.animal.CrystalFox;
import baguchan.frostrealm.entity.hostile.UnderGokkur;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class UnderGokkurRenderer<T extends UnderGokkur> extends MobRenderer<T, UnderGokkurRenderState, GokkurModel<UnderGokkurRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/under_gokkur.png");
    private static final RenderType GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/under_gokkur_hot.png"), false);

    public UnderGokkurRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new GokkurModel<>(p_173952_.bakeLayer(FrostModelLayers.GOKKUR)), 0.5F);
        this.addLayer(new CrackingGokkurLayer<>(this));
        this.addLayer(new EyesLayer<>(this) {

            @Override
            public void submit(PoseStack p_433452_, SubmitNodeCollector p_433171_, int p_434650_, UnderGokkurRenderState p_435883_, float p_433542_, float p_435619_)
            {
                if (p_435883_.magma) {
                    super.submit(p_433452_, p_433171_, p_434650_, p_435883_, p_433542_, p_435619_);
                }
            }

            @Override
            public RenderType renderType() {
                return GLOW;
            }
        });
    }

    @Override
    public void extractRenderState(T p_362733_, UnderGokkurRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);

        p_360515_.rollAnimationState.copyFrom(p_362733_.rollAnimationState);
        p_360515_.startRollAnimationState.copyFrom(p_362733_.startRollAnimationState);
        p_360515_.crackiness = p_362733_.getCrackiness();
        p_360515_.magma = p_362733_.isMagma();
    }

    @Override
    public UnderGokkurRenderState createRenderState() {
        return new UnderGokkurRenderState();
    }

    @Override
    public Identifier getTextureLocation(UnderGokkurRenderState entity) {
        return TEXTURE;
    }
}