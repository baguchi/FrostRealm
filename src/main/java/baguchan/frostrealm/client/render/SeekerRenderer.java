package baguchan.frostrealm.client.render;

import bagu_chan.bagus_lib.client.layer.CustomArmorLayer;
import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.SeekerModel;
import baguchan.frostrealm.entity.Seeker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class SeekerRenderer<T extends Seeker> extends MobRenderer<T, SeekerModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FrostRealm.MODID, "textures/entity/seeker/seeker.png");


    private static final float HALF_SQRT_3 = (float) (Math.sqrt(30.0) / 2.0);

    public SeekerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new SeekerModel<>(p_173952_.bakeLayer(FrostModelLayers.SEEKER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
        this.addLayer(new CustomArmorLayer<>(this, p_173952_));
    }

    public boolean shouldRender(T p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        if (!p_114491_.shouldRender(p_114493_, p_114494_, p_114495_)) {
            return false;
        } else if (p_114491_.noCulling) {
            return true;
        } else {

            Vec3 vec31 = p_114491_.getLookAngle();
            double range = p_114491_.getAttribute(Attributes.FOLLOW_RANGE) != null ? p_114491_.getAttributeValue(Attributes.FOLLOW_RANGE) : 16F;
            AABB aabb = p_114491_.getBoundingBoxForCulling().inflate(0.5);
            if (aabb.hasNaN() || aabb.getSize() == 0.0) {
                aabb = new AABB(
                        p_114491_.getX() - 2.0,
                        p_114491_.getY() - 2.0,
                        p_114491_.getZ() - 2.0,
                        p_114491_.getX() + 2.0,
                        p_114491_.getY() + 2.0,
                        p_114491_.getZ() + 2.0
                );
            }

            return p_114492_.isVisible(aabb.expandTowards(vec31.x() * (range), vec31.y() * (range), vec31.z() * (range)));
        }
    }

    @Override
    public void render(T p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);

        float f3 = 16.0F;
        float f4 = 2.0F + 1.0F;
        int j = (int) (255.0F * (1F));
        p_115458_.pushPose();
        VertexConsumer vertexconsumer2 = p_115459_.getBuffer(RenderType.lightning());

        float f1 = (float) p_115455_.getYHeadRot();
        p_115458_.translate(0, 2.1F * 0.85F, 0);

        p_115458_.mulPose(Axis.YP.rotationDegrees(-f1 - 90.0F));
        p_115458_.mulPose(Axis.ZP.rotationDegrees((float) -p_115455_.getXRot() + 90.0F));


        p_115458_.scale(-1.0F, -1.0F, 1.0F);
        this.getModel().root.translateAndRotate(p_115458_);
        this.getModel().head.translateAndRotate(p_115458_);

        Matrix4f matrix4f = p_115458_.last().pose();
        vertex01(vertexconsumer2, matrix4f, j);
        vertex2(vertexconsumer2, matrix4f, f3, f4);
        vertex3(vertexconsumer2, matrix4f, f3, f4);
        vertex01(vertexconsumer2, matrix4f, j);
        vertex3(vertexconsumer2, matrix4f, f3, f4);
        vertex4(vertexconsumer2, matrix4f, f3, f4);
        vertex01(vertexconsumer2, matrix4f, j);
        vertex4(vertexconsumer2, matrix4f, f3, f4);
        vertex2(vertexconsumer2, matrix4f, f3, f4);
        p_115458_.popPose();
    }

    private static void vertex01(VertexConsumer p_254498_, Matrix4f p_253891_, int p_254278_) {
        p_254498_.vertex(p_253891_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_254278_).endVertex();
    }

    private static void vertex2(VertexConsumer p_253956_, Matrix4f p_254053_, float p_253704_, float p_253701_) {
        p_253956_.vertex(p_254053_, -HALF_SQRT_3 * p_253701_, p_253704_, -0.5F * p_253701_).color(0, 0, 255, 0).endVertex();
    }

    private static void vertex3(VertexConsumer p_253850_, Matrix4f p_254379_, float p_253729_, float p_254030_) {
        p_253850_.vertex(p_254379_, HALF_SQRT_3 * p_254030_, p_253729_, -0.5F * p_254030_).color(0, 0, 255, 0).endVertex();
    }

    private static void vertex4(VertexConsumer p_254184_, Matrix4f p_254082_, float p_253649_, float p_253694_) {
        p_254184_.vertex(p_254082_, 0.0F, p_253649_, 0.5F * p_253694_).color(0, 0, 255, 0).endVertex();
    }



    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}