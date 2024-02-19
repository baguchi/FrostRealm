package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WarpedInsectModel;
import baguchan.frostrealm.entity.WarpedInsect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WarpedInsectRenderer<T extends WarpedInsect> extends MobRenderer<T, WarpedInsectModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/warped_insect.png");


    public WarpedInsectRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WarpedInsectModel<>(p_173952_.bakeLayer(FrostModelLayers.WARPED_INSECT)), 0.5F);
    }

    protected void setupRotations(T p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
        super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
        p_115686_.mulPose(Axis.XP.rotationDegrees(p_115685_.getXRot()));
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}