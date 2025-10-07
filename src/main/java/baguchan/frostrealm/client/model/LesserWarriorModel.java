package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.SpearAttackAnimations;
import baguchan.frostrealm.client.render.state.LesserWarriorRenderState;
import baguchi.bagus_lib.client.layer.IArmor;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class LesserWarriorModel<T extends LesserWarriorRenderState> extends EntityModel<T> implements IArmor, ArmedModel {
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;
    private final ModelPart right_arm;
    private final ModelPart left_arm;

    private final ModelPart right_item;
    private final ModelPart left_item;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final KeyframeAnimation idleRightAnimationState;
    private final KeyframeAnimation idleLeftAnimationState;
    private final KeyframeAnimation attackRightAnimationState;
    private final KeyframeAnimation attackLeftAnimationState;
    private final KeyframeAnimation counterRightAnimationState;
    private final KeyframeAnimation counterLeftAnimationState;
    private final KeyframeAnimation guardRightAnimationState;
    private final KeyframeAnimation guardLeftAnimationState;

    public LesserWarriorModel(ModelPart root) {
        super(root);
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.right_item = this.right_arm.getChild("right_item");
        this.left_item = this.left_arm.getChild("left_item");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
        this.idleRightAnimationState = SpearAttackAnimations.idle_right.bake(root);
        this.idleLeftAnimationState = SpearAttackAnimations.idle_left.bake(root);
        this.attackRightAnimationState = SpearAttackAnimations.spear_attack_right.bake(root);
        this.attackLeftAnimationState = SpearAttackAnimations.spear_attack_left.bake(root);
        this.counterRightAnimationState = SpearAttackAnimations.counter_right.bake(root);
        this.counterLeftAnimationState = SpearAttackAnimations.counter_left.bake(root);
        this.guardRightAnimationState = SpearAttackAnimations.guard_right.bake(root);
        this.guardLeftAnimationState = SpearAttackAnimations.guard_left.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition right_item = right_arm.addOrReplaceChild("right_item", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition left_item = left_arm.addOrReplaceChild("left_item", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 1.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.yRot = entity.yRot * ((float) Math.PI / 180F);
        this.head.xRot = entity.xRot * ((float) Math.PI / 180F);

        float f1 = entity.walkAnimationPos;
        float f2 = entity.walkAnimationSpeed;
        this.right_leg.xRot = Mth.cos(f1 * 0.6662F) * 1.4F * f2 / entity.speedValue;
        this.left_leg.xRot = Mth.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2 / entity.speedValue;
        this.right_leg.yRot = 0.0F;
        this.left_leg.yRot = 0.0F;
        this.right_leg.zRot = 0.0F;
        this.left_leg.zRot = 0.0F;

        float f = entity.partialTick;

        if (entity.isHoldingBow && !entity.isAggressive) {
            this.right_arm.xRot = Mth.cos(f1 * 0.6662F + (float) Math.PI) * 2.0F * f2 * 0.5F / entity.speedValue;
            this.left_arm.xRot = Mth.cos(f1 * 0.6662F) * 2.0F * f2 * 0.5F / entity.speedValue;
        } else {
            if (!entity.attackAnimationState.isStarted() && !entity.counterAnimationState.isStarted() && entity.guardAnimationScale.getAnimationScale(f) <= 0) {
                if (entity.mainArm == HumanoidArm.RIGHT) {
                    this.idleRightAnimationState.applyStatic();
                } else {
                    this.idleLeftAnimationState.applyStatic();
                }
            }


            if (entity.mainArm == HumanoidArm.RIGHT) {
                this.attackRightAnimationState.apply(entity.attackAnimationState, entity.ageInTicks);
            } else {
                this.attackLeftAnimationState.apply(entity.attackAnimationState, entity.ageInTicks);
            }

            if (entity.mainArm == HumanoidArm.RIGHT) {
                this.counterRightAnimationState.apply(entity.counterAnimationState, entity.ageInTicks);
            } else {
                this.counterLeftAnimationState.apply(entity.counterAnimationState, entity.ageInTicks);
            }
            if (!entity.counterAnimationState.isStarted()) {
                if (entity.mainArm == HumanoidArm.RIGHT) {
                    this.guardRightAnimationState.applyWalk(0.0F, entity.guardAnimationScale.getAnimationScale(f), 1.0F, 1.0F);
                } else {
                    this.guardLeftAnimationState.applyWalk(0.0F, entity.guardAnimationScale.getAnimationScale(f), 1.0F, 1.0F);
                }
            }
        }
        if (entity.isHoldingBow && entity.isAggressive) {
            this.right_arm.yRot = -0.1F + this.head.yRot;
            this.left_arm.yRot = 0.1F + this.head.yRot + 0.4F;
            this.right_arm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
            this.left_arm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
        }
    }

    @Override
    public void translateToHead(ModelPart modelPart, PoseStack poseStack) {
        modelPart.translateAndRotate(poseStack);
    }

    @Override
    public void translateToChest(ModelPart modelPart, PoseStack poseStack) {
        modelPart.translateAndRotate(poseStack);
    }

    @Override
    public void translateToLeg(ModelPart modelPart, PoseStack poseStack) {
        modelPart.translateAndRotate(poseStack);
    }

    @Override
    public void translateToChestPat(ModelPart modelPart, PoseStack poseStack) {
        modelPart.translateAndRotate(poseStack);
    }

    @Override
    public Iterable<ModelPart> headPartArmors() {
        return ImmutableList.of(this.head);
    }

    @Override
    public Iterable<ModelPart> bodyPartArmors() {
        return ImmutableList.of(this.body);
    }

    @Override
    public Iterable<ModelPart> leftLegPartArmors() {
        return ImmutableList.of(this.left_leg);
    }

    @Override
    public Iterable<ModelPart> rightLegPartArmors() {
        return ImmutableList.of(this.right_leg);
    }

    @Override
    public Iterable<ModelPart> rightHandArmors() {
        return ImmutableList.of(this.right_arm);
    }

    @Override
    public Iterable<ModelPart> leftHandArmors() {
        return ImmutableList.of(this.left_arm);
    }

    private ModelPart getArm(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
    }

    private ModelPart getArmItem(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.left_item : this.right_item;
    }


    @Override
    public void translateToHand(EntityRenderState entityRenderState, HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
        this.getArmItem(humanoidArm).translateAndRotate(poseStack);
        poseStack.translate(0, -(8F / 16F), 0);
    }
}