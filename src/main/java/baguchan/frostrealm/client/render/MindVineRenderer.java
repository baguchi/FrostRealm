package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.MindVineModel;
import baguchan.frostrealm.entity.MindVine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MindVineRenderer<T extends MindVine> extends MobRenderer<T, MindVineModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/mind_vine.png");

    public MindVineRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new MindVineModel<>(p_173952_.bakeLayer(FrostModelLayers.MIND_VINE)), 0.35F);
    }

    protected void setupRotations(T p_115907_, PoseStack p_115908_, float p_115909_, float p_115910_, float p_115911_) {
        super.setupRotations(p_115907_, p_115908_, p_115909_, p_115910_ + 180.0F, p_115911_);

        p_115908_.mulPose(Axis.YP.rotationDegrees(p_115910_));
        p_115908_.translate(0.0, 0.5, 0.0);
        p_115908_.mulPose(p_115907_.getAttachFace().getOpposite().getRotation());
        p_115908_.translate(0.0, -0.5, 0.0);

    }

    protected float getFlipDegrees(T p_115337_) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}