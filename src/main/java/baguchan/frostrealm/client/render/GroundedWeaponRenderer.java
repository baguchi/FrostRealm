package baguchan.frostrealm.client.render;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.client.render.state.GroundedWeaponRenderState;
import baguchan.frostrealm.entity.projectile.GroundedWeapon;
import baguchan.frostrealm.registry.FrostItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;

public class GroundedWeaponRenderer<T extends GroundedWeapon> extends EntityRenderer<T, GroundedWeaponRenderState> {
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
        ItemStackRenderState.LayerRenderState itemstackrenderstate$layerrenderstate = state.item.newLayer();
        itemstackrenderstate$layerrenderstate.setRenderType(FrostRenderType.AURORA_ENTITY_GLINT);

        state.xRot = entity.getXRot(partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.direction = entity.getAttachFace();
        state.animationScale = entity.getAnimationScale(partialTicks);
    }

    @Override
    public void submit(GroundedWeaponRenderState state, PoseStack poseStack, SubmitNodeCollector bufferSource, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.scale(2.5f, 2.5f, 2.5f);
        poseStack.mulPose(state.direction.getOpposite().getRotation());
        poseStack.translate(0.25F, -1.501F * (1F - state.animationScale), 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(45.0f));


        state.item.submit(poseStack, bufferSource, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();

        super.submit(state, poseStack, bufferSource, cameraRenderState);
    }
}