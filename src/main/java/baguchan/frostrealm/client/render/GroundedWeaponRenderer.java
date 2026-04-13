package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.client.render.state.GroundedWeaponRenderState;
import baguchan.frostrealm.entity.projectile.GroundedWeapon;
import baguchan.frostrealm.registry.FrostItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GroundedWeaponRenderer<T extends GroundedWeapon> extends EntityRenderer<T, GroundedWeaponRenderState> {

    private static final RenderType SHADOW_RENDER_TYPE = FrostRenderType.entityGlowShadow(FrostRealm.prefix("textures/misc/glow_shadow.png"));

    private final ItemModelResolver itemModelResolver;

    public GroundedWeaponRenderer(EntityRendererProvider.Context context) {
        super(context);
        itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public GroundedWeaponRenderState createRenderState() {
        return new GroundedWeaponRenderState();
    }

    @Override
    public void extractRenderState(T entity, GroundedWeaponRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        itemModelResolver.updateForNonLiving(state.item, FrostItems.FROST_SPEAR.toStack(), ItemDisplayContext.GROUND, entity);

        state.xRot = entity.getXRot(partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.direction = entity.getAttachFace();
        state.animationScale = entity.getAnimationScale(partialTicks);
        state.glowScale = entity.getGlowAnimationScale(partialTicks);
        float relativeX = (float) (entity.getX() - state.x);
        float relativeY = (float) (entity.getY() - state.y);
        float relativeZ = (float) (entity.getZ() - state.z);
        state.glowPiece = new GlowPiece(relativeX, relativeY, relativeZ, 1.0F);

    }

    @Override
    public void submit(GroundedWeaponRenderState state, PoseStack poseStack, SubmitNodeCollector bufferSource, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.scale(1.5f, 1.5f, 1.5f);
        poseStack.mulPose(state.direction.getOpposite().getRotation());
        poseStack.translate(0.25F, -1.501F * (1F - state.animationScale), 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(45.0f));


        state.item.submit(poseStack, bufferSource, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        if (state.glowPiece != null) {
            poseStack.pushPose();
            poseStack.mulPose(state.direction.getOpposite().getRotation());
            //poseStack.translate(0.25F, -1.501F * (1F - state.animationScale), 0);

            glowShadowRender(state, poseStack, bufferSource);
            poseStack.popPose();
        }
        super.submit(state, poseStack, bufferSource, cameraRenderState);
    }

    public void glowShadowRender(GroundedWeaponRenderState entity, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {

        submitNodeCollector.submitCustomGeometry(poseStack, SHADOW_RENDER_TYPE, (pose, buffer) -> {
            GlowPiece piece = entity.glowPiece;
            float x01 = piece.relativeX() + (float) -1;
            float x11 = piece.relativeX() + (float) 1;
            float y01 = piece.relativeY();
            float z01 = piece.relativeZ() + (float) -1;
            float z11 = piece.relativeZ() + (float) 1;
            float radius = 0.6F;
            float u0 = -x01 / 2.0F / radius + 0.5F;
            float u1 = -x11 / 2.0F / radius + 0.5F;
            float v0 = -z01 / 2.0F / radius + 0.5F;
            float v1 = -z11 / 2.0F / radius + 0.5F;
            int color = ARGB.white(piece.alpha() * entity.glowScale);
            shadowVertex(pose.pose(), buffer, color, x01, y01, z01, u0, v0);
            shadowVertex(pose.pose(), buffer, color, x01, y01, z11, u0, v1);
            shadowVertex(pose.pose(), buffer, color, x11, y01, z11, u1, v1);
            shadowVertex(pose.pose(), buffer, color, x11, y01, z01, u1, v0);
        });

    }

    private static void shadowVertex(Matrix4f pose, VertexConsumer buffer, int color, float x, float y, float z, float u, float v) {
        Vector3f position = pose.transformPosition(x, y, z, new Vector3f());
        buffer.addVertex(position.x(), position.y(), position.z(), color, u, v, OverlayTexture.NO_OVERLAY, 15728880, 0.0F, 1.0F, 0.0F);
    }

    public record GlowPiece(float relativeX, float relativeY, float relativeZ, float alpha) {
    }
}