package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SilkMoonWormModel;
import baguchan.frostrealm.entity.animal.SilkMoonWorm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SilkMoonWormRenderer<T extends SilkMoonWorm> extends MobRenderer<T, LivingEntityRenderState, SilkMoonWormModel<LivingEntityRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/silk_moon_worm.png");


    public SilkMoonWormRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SilkMoonWormModel<>(p_173952_.bakeLayer(FrostModelLayers.SILK_MOON_WORM)), 0.3F);
    }


    @Override
    protected void scale(LivingEntityRenderState p_362272_, PoseStack p_115315_) {
        super.scale(p_362272_, p_115315_);
        p_115315_.scale(0.5F, 0.5F, 0.5F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }


    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState p_110775_1_) {
        return TEXTURE;
    }
}