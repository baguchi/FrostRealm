package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.CorruptedWalkerFootModel;
import baguchan.frostrealm.client.model.CorruptedWalkerModel;
import baguchan.frostrealm.client.model.CorruptedWalkerPartModel;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalker;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalkerPart;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalkerPartContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class CorruptedWalkerRenderer extends MobRenderer<CorruptedWalker, CorruptedWalkerModel<CorruptedWalker>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_walker.png");
    private static final ResourceLocation FOOT_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_foot.png");
    private static final ResourceLocation PART_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_part.png");

    private final CorruptedWalkerFootModel footModel;
    private final CorruptedWalkerPartModel partModel;

    public CorruptedWalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new CorruptedWalkerModel<>(context.bakeLayer(FrostModelLayers.CORRUPTED_WALKER)), 0.5F);
        this.footModel = new CorruptedWalkerFootModel(context.bakeLayer(FrostModelLayers.CORRUPTED_WALKER_FOOT));
        this.partModel = new CorruptedWalkerPartModel(context.bakeLayer(FrostModelLayers.CORRUPTED_WALKER_PART));
    }

    @Override
    public void render(CorruptedWalker entity, float p_115309_, float particalTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        super.render(entity, p_115309_, particalTick, poseStack, multiBufferSource, light);
        for (CorruptedWalkerPartContainer container : entity.ec) {
            for (CorruptedWalkerPart part : container.getParts()) {
                renderMultiPart(part, poseStack, particalTick, multiBufferSource, light);
            }

            renderFootMultiPart(container.getParentPart(), poseStack, particalTick, multiBufferSource, light);
        }
    }

    public void renderMultiPart(CorruptedWalkerPart entity, PoseStack poseStack, float partialTick, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        double x = Mth.lerp(partialTick, entity.parentMob.xo - entity.xo, entity.parentMob.getX() - entity.getX());
        double y = Mth.lerp(partialTick, entity.parentMob.yo - entity.yo, entity.parentMob.getY() - entity.getY());
        double z = Mth.lerp(partialTick, entity.parentMob.zo - entity.zo, entity.parentMob.getZ() - entity.getZ());

        float f = entity.getXRot();
        float f8 = entity.parentMob.getScale();
        poseStack.scale(f8, f8, f8);
        this.setupPartRotations(entity, poseStack, this.getBob(entity.parentMob, partialTick), f, partialTick, f8);
        poseStack.translate(x, -y, z);

        poseStack.scale(-1.0F, -1.0F, 1.0F);

        this.scale(entity.parentMob, poseStack, partialTick);
        poseStack.translate(0.0F, -1.501F, 0.0F);

        boolean flag = this.isBodyVisible(entity.parentMob);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.partModel.renderType(PART_LOCATION);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(rendertype);
            int i = getOverlayCoords(entity.parentMob, 0.0F);
            this.partModel.renderToBuffer(poseStack, vertexconsumer, light, i, flag1 ? 654311423 : -1);
        }
        poseStack.popPose();
    }

    public void renderFootMultiPart(CorruptedWalkerPart entity, PoseStack poseStack, float partialTick, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        double x = Mth.lerp(partialTick, entity.parentMob.xo - entity.xo, entity.parentMob.getX() - entity.getX());
        double y = Mth.lerp(partialTick, entity.parentMob.yo - entity.yo, entity.parentMob.getY() - entity.getY());
        double z = Mth.lerp(partialTick, entity.parentMob.zo - entity.zo, entity.parentMob.getZ() - entity.getZ());

        float f = entity.getXRot();
        float f8 = entity.parentMob.getScale();

        poseStack.scale(f8, f8, f8);
        this.setupPartRotations(entity, poseStack, this.getBob(entity.parentMob, partialTick), f, partialTick, f8);
        poseStack.translate(x, -y, z);

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity.parentMob, poseStack, partialTick);
        poseStack.translate(0.0F, -1.501F, 0.0F);

        boolean flag = this.isBodyVisible(entity.parentMob);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.footModel.renderType(FOOT_LOCATION);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(rendertype);
            int i = getOverlayCoords(entity.parentMob, 0.0F);
            this.footModel.renderToBuffer(poseStack, vertexconsumer, light, i, flag1 ? 654311423 : -1);
        }
        poseStack.popPose();
    }

    protected void setupPartRotations(CorruptedWalkerPart p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_, float p_320045_) {
        if (this.isShaking(p_115317_.parentMob)) {
            p_115320_ += (float) (Math.cos((double) p_115317_.tickCount * 3.25) * Math.PI * 0.4F);
        }

        if (!p_115317_.hasPose(Pose.SLEEPING)) {
            p_115318_.mulPose(Axis.YP.rotationDegrees(180.0F - p_115320_));
        }

        /*if (p_115317_.deathTime > 0) {
            float f = ((float)p_115317_.deathTime + p_115321_ - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            p_115318_.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(p_115317_)));
        } else if (p_115317_.isAutoSpinAttack()) {
            p_115318_.mulPose(Axis.XP.rotationDegrees(-90.0F - p_115317_.getXRot()));
            p_115318_.mulPose(Axis.YP.rotationDegrees(((float)p_115317_.tickCount + p_115321_) * -75.0F));
        } else if (p_115317_.hasPose(Pose.SLEEPING)) {
            Direction direction = p_115317_.getBedOrientation();
            float f1 = direction != null ? sleepDirectionToRotation(direction) : p_115320_;
            p_115318_.mulPose(Axis.YP.rotationDegrees(f1));
            p_115318_.mulPose(Axis.ZP.rotationDegrees(this.getFlipDegrees(p_115317_)));
            p_115318_.mulPose(Axis.YP.rotationDegrees(270.0F));
        } else if (isEntityUpsideDown(p_115317_)) {
            p_115318_.translate(0.0F, (p_115317_.getBbHeight() + 0.1F) / p_320045_, 0.0F);
            p_115318_.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }*/
    }

    @Override
    public ResourceLocation getTextureLocation(CorruptedWalker corrupedWalker) {
        return LOCATION;
    }
}
