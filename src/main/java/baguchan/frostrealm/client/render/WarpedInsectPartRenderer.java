package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WarpedInsectPartModel;
import baguchan.frostrealm.entity.WarpedInsectPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WarpedInsectPartRenderer<T extends WarpedInsectPart> extends LivingEntityRenderer<T, WarpedInsectPartModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/warped_insect.png");


    public WarpedInsectPartRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new WarpedInsectPartModel<>(p_173952_.bakeLayer(FrostModelLayers.WARPED_INSECT_PART)), 0.5F);
    }

    protected void setupRotations(T p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
        super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
        p_115686_.mulPose(Axis.XP.rotationDegrees(p_115685_.getXRot()));
    }

    protected boolean shouldShowName(T p_115506_) {
        return super.shouldShowName(p_115506_) && (p_115506_.shouldShowName() || p_115506_.hasCustomName() && p_115506_ == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}