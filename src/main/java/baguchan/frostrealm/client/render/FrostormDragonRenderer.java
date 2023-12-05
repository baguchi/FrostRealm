package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostormDragonModel;
import baguchan.frostrealm.entity.FrostormDragon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostormDragonRenderer<T extends FrostormDragon> extends MobRenderer<T, FrostormDragonModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/frostorm_dragon/frostorm_dragon.png");


    public FrostormDragonRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FrostormDragonModel<>(p_173952_.bakeLayer(FrostModelLayers.FROSTROM_DRAGON)), 1.0F);
    }

    protected void setupRotations(T p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
        super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
        if (p_115685_.isFlying()) {
            p_115686_.mulPose(Axis.XP.rotationDegrees(p_115685_.getXRot()));
        }
    }

    @Override
    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        float size = p_115314_.getScale();
        p_115315_.scale(size, size, size);
        super.scale(p_115314_, p_115315_, p_115316_);
    }
    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}