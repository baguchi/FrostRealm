package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.CorruptedWalkerFootModel;
import baguchan.frostrealm.client.model.CorruptedWalkerModel;
import baguchan.frostrealm.client.model.CorruptedWalkerPartModel;
import baguchan.frostrealm.client.render.state.CorruptedWalkerRenderState;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalker;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalkerPart;
import baguchan.frostrealm.entity.hostile.part.CorruptedWalkerPartContainer;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class CorruptedWalkerRenderer extends MobRenderer<CorruptedWalker, CorruptedWalkerRenderState, CorruptedWalkerModel<CorruptedWalkerRenderState>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_walker.png");
    private static final ResourceLocation EYE_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_walker_eye.png");
    private static final ResourceLocation FOOT_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_walker_foot.png");
    private static final ResourceLocation PART_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/corrupted_walker/corrupted_walker_part.png");

    private final CorruptedWalkerFootModel footModel;
    private final CorruptedWalkerPartModel partModel;

    public CorruptedWalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new CorruptedWalkerModel<>(context.bakeLayer(FrostModelLayers.CORRUPTED_WALKER)), 0.5F);
        this.footModel = new CorruptedWalkerFootModel(context.bakeLayer(FrostModelLayers.CORRUPTED_WALKER_FOOT));
        this.partModel = new CorruptedWalkerPartModel(context.bakeLayer(FrostModelLayers.CORRUPTED_WALKER_PART));
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return RenderType.eyes(EYE_LOCATION);
            }
        });
    }

    @Override
    public void render(CorruptedWalkerRenderState entity, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        super.render(entity, poseStack, multiBufferSource, light);
        for (CorruptedWalkerPartContainer container : entity.container) {
            for (CorruptedWalkerPart part : container.getParts()) {
                renderMultiPart(entity, part, poseStack, entity.partialTick, multiBufferSource, light);
            }

            renderFootMultiPart(entity, container.getParentPart(), poseStack, entity.partialTick, multiBufferSource, light);
        }
    }

    @Override
    public void extractRenderState(CorruptedWalker p_362733_, CorruptedWalkerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.container = Arrays.stream(p_362733_.ec).toList();
    }

    @Override
    public CorruptedWalkerRenderState createRenderState() {
        return new CorruptedWalkerRenderState();
    }

    public void renderMultiPart(CorruptedWalkerRenderState state, CorruptedWalkerPart entity, PoseStack poseStack, float partialTick, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        double x = Mth.lerp(partialTick, entity.parentMob.xo - entity.xo, entity.parentMob.getX() - entity.getX());
        double y = Mth.lerp(partialTick, entity.parentMob.yo - entity.yo, entity.parentMob.getY() - entity.getY());
        double z = Mth.lerp(partialTick, entity.parentMob.zo - entity.zo, entity.parentMob.getZ() - entity.getZ());

        float f = entity.getXRot();
        float f8 = entity.parentMob.getScale();
        poseStack.translate(-x, -y, -z);

        poseStack.scale(f8, f8, f8);
        this.setupPartRotations(state, entity, poseStack, 0.0F, f, partialTick, f8);

        poseStack.scale(-1.0F, -1.0F, 1.0F);

        this.scale(state, poseStack);
        poseStack.translate(0.0F, -1.501F, 0.0F);

        boolean flag = this.isBodyVisible(state);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.partModel.renderType(PART_LOCATION);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(rendertype);
            int i = getOverlayCoords(state, 0.0F);
            this.partModel.renderToBuffer(poseStack, vertexconsumer, light, i, flag1 ? 654311423 : -1);
        }
        poseStack.popPose();
    }

    public void renderFootMultiPart(CorruptedWalkerRenderState state, CorruptedWalkerPart entity, PoseStack poseStack, float partialTick, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        double x = Mth.lerp(partialTick, entity.parentMob.xo - entity.xo, entity.parentMob.getX() - entity.getX());
        double y = Mth.lerp(partialTick, entity.parentMob.yo - entity.yo, entity.parentMob.getY() - entity.getY());
        double z = Mth.lerp(partialTick, entity.parentMob.zo - entity.zo, entity.parentMob.getZ() - entity.getZ());

        float f = entity.getXRot();
        float f8 = entity.parentMob.getScale();
        poseStack.translate(-x, -y, -z);

        poseStack.scale(f8, f8, f8);
        this.setupPartRotations(state, entity, poseStack, 0.0F, f, partialTick, f8);

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(state, poseStack);
        poseStack.translate(0.0F, -1.501F, 0.0F);

        boolean flag = this.isBodyVisible(state);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.footModel.renderType(FOOT_LOCATION);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(rendertype);
            int i = getOverlayCoords(state, 0.0F);
            this.footModel.renderToBuffer(poseStack, vertexconsumer, light, i, flag1 ? 654311423 : -1);
        }
        poseStack.popPose();
    }

    @Override
    protected void extractAdditionalHitboxes(CorruptedWalker p_412673_, ImmutableList.Builder<HitboxRenderState> p_412323_, float p_412176_) {
        super.extractAdditionalHitboxes(p_412673_, p_412323_, p_412176_);
        double d0 = -Mth.lerp((double) p_412176_, p_412673_.xOld, p_412673_.getX());
        double d1 = -Mth.lerp((double) p_412176_, p_412673_.yOld, p_412673_.getY());
        double d2 = -Mth.lerp((double) p_412176_, p_412673_.zOld, p_412673_.getZ());

        for (@Nullable PartEntity<?> enderdragonpart : p_412673_.getParts()) {
            AABB aabb = enderdragonpart.getBoundingBox();
            HitboxRenderState hitboxrenderstate = new HitboxRenderState(aabb.minX - enderdragonpart.getX(), aabb.minY - enderdragonpart.getY(), aabb.minZ - enderdragonpart.getZ(), aabb.maxX - enderdragonpart.getX(), aabb.maxY - enderdragonpart.getY(), aabb.maxZ - enderdragonpart.getZ(), (float) (d0 + Mth.lerp((double) p_412176_, enderdragonpart.xOld, enderdragonpart.getX())), (float) (d1 + Mth.lerp((double) p_412176_, enderdragonpart.yOld, enderdragonpart.getY())), (float) (d2 + Mth.lerp((double) p_412176_, enderdragonpart.zOld, enderdragonpart.getZ())), 0.25F, 1.0F, 0.0F);
            p_412323_.add(hitboxrenderstate);
        }

    }

    protected void setupPartRotations(CorruptedWalkerRenderState state, CorruptedWalkerPart p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_, float p_320045_) {
        if (this.isShaking(state)) {
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
    public ResourceLocation getTextureLocation(CorruptedWalkerRenderState corrupedWalker) {
        return LOCATION;
    }
}
