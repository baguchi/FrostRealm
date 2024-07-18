package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostWraithModel;
import baguchan.frostrealm.entity.hostile.FrostWraith;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class FrostWraithRenderer<T extends FrostWraith> extends MobRenderer<T, FrostWraithModel<T>> {
	private static final ResourceLocation WRAITH = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_wraith/frost_wraith.png");
	private static final RenderType WRAITH_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_wraith/frost_wraith_glow.png"));

	private static final float HALF_SQRT_3 = (float) (Math.sqrt(30.0) / 2.0);

	public FrostWraithRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new FrostWraithModel<>(p_173952_.bakeLayer(FrostModelLayers.FROST_WRAITH)), 0.5F);
		this.addLayer(new EyesLayer<T, FrostWraithModel<T>>(this) {
			@Override
			public RenderType renderType() {
				return WRAITH_GLOW;
			}
		});
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
	public void render(T p_115455_, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource p_115459_, int p_115460_) {
		super.render(p_115455_, p_115456_, p_115457_, poseStack, p_115459_, p_115460_);

		float length = 16.0F;
		float range = 2.0F + 1.0F;
		int j = (int) (255.0F * (1F));
		poseStack.pushPose();
		VertexConsumer vertexconsumer2 = p_115459_.getBuffer(RenderType.lightning());

		float f1 = (float) p_115455_.getYHeadRot();
		poseStack.translate(0, 2.1F * 0.85F, 0);

		poseStack.mulPose(Axis.YP.rotationDegrees(-f1 - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees((float) -p_115455_.getXRot() + 90.0F));


		poseStack.scale(-1.0F, -1.0F, 1.0F);
		this.getModel().main.translateAndRotate(poseStack);
		this.getModel().body.translateAndRotate(poseStack);
		this.getModel().getHead().translateAndRotate(poseStack);

		for (int i = 0; i < 4; ++i) {

			poseStack.pushPose();
			poseStack.mulPose(Axis.YP.rotationDegrees((float) -90.0F * i));

			poseStack.mulPose(Axis.XP.rotationDegrees(i % 2 == 0 ? -range * 360.0F : range * 360.0F));

			Matrix4f matrix4f = poseStack.last().pose();
			PoseStack.Pose pose = poseStack.last();

			originVertex(vertexconsumer2, matrix4f, pose, 255);
			leftVertex(vertexconsumer2, matrix4f, pose, length, range);
			rightVertex(vertexconsumer2, matrix4f, pose, length, range);
			leftVertex(vertexconsumer2, matrix4f, pose, length, range);
			poseStack.popPose();
		}
		poseStack.popPose();
	}

	private static void originVertex(VertexConsumer p_254498_, Matrix4f p_253891_, PoseStack.Pose p_114092_, int p_254278_) {
		p_254498_.addVertex(p_253891_, 0.0F, 0.0F, 0.0F).setColor(255, 255, 255, p_254278_).setUv(0 + 0.5F, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(p_114092_, 0.0F, 1.0F, 0.0F);
	}

	private static void leftVertex(VertexConsumer p_253956_, Matrix4f p_254053_, PoseStack.Pose p_114092_, float p_253704_, float p_253701_) {
		p_253956_.addVertex(p_254053_, -HALF_SQRT_3 * p_253701_, p_253704_, 0).setColor(0, 0, 255, 0).setUv(0, 0 + 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(p_114092_, 0.0F, -1.0F, 0.0F);
	}

	private static void rightVertex(VertexConsumer p_253850_, Matrix4f p_254379_, PoseStack.Pose p_114092_, float p_253729_, float p_254030_) {
		p_253850_.addVertex(p_254379_, HALF_SQRT_3 * p_254030_, p_253729_, 0).setColor(0, 0, 255, 0).setUv(0 + 1, 0 + 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(p_114092_, 0.0F, -1.0F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return WRAITH;
	}
}