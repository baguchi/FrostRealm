package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.MindVineModel;
import baguchan.frostrealm.entity.hostile.MindVine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MindVineRenderer<T extends MindVine> extends MobRenderer<T, MindVineModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/mind_vine.png");

    public MindVineRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new MindVineModel<>(p_173952_.bakeLayer(FrostModelLayers.MIND_VINE)), 0.35F);
    }

    protected void setupRotations(T p_320913_, PoseStack p_115891_, float p_115892_, float p_115893_, float p_115894_, float p_319950_) {
        super.setupRotations(p_320913_, p_115891_, p_115892_, p_115893_ + 180.0F, p_115894_, p_319950_);
        p_115891_.rotateAround(p_320913_.getAttachFace().getOpposite().getRotation(), 0.0F, 0.5F, 0.0F);
    }

    protected float getFlipDegrees(T p_115337_) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}