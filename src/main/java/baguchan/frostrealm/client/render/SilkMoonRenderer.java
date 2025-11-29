package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SilkMoonModel;
import baguchan.frostrealm.entity.animal.SilkMoon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;


public class SilkMoonRenderer<T extends SilkMoon> extends MobRenderer<T, LivingEntityRenderState, SilkMoonModel<LivingEntityRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/silk_moon.png");


    public SilkMoonRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SilkMoonModel<>(p_173952_.bakeLayer(FrostModelLayers.SILK_MOON)), 0.35F);
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