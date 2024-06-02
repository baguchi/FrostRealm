package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WarpyModel;
import baguchan.frostrealm.entity.hostile.Warpy;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WarpyRenderer<T extends Warpy> extends MobRenderer<T, WarpyModel<T>> {
    private static final ResourceLocation WRAITH = new ResourceLocation(FrostRealm.MODID, "textures/entity/warpy/warpy.png");
    private static final RenderType WRAITH_GLOW = RenderType.eyes(new ResourceLocation(FrostRealm.MODID, "textures/entity/warpy/warpy_glow.png"));

    public WarpyRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WarpyModel<>(p_173952_.bakeLayer(FrostModelLayers.WARPY)), 0.5F);
        this.addLayer(new EyesLayer<T, WarpyModel<T>>(this) {
            @Override
            public RenderType renderType() {
                return WRAITH_GLOW;
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return WRAITH;
    }
}