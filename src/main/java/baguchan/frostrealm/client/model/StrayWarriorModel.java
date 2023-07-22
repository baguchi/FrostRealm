package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import bagu_chan.bagus_lib.client.layer.IArmor;
import baguchan.frostrealm.client.animation.SpearAttackAnimations;
import baguchan.frostrealm.entity.StrayWarrior;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class StrayWarriorModel<T extends StrayWarrior> extends HierarchicalModel<T> implements IArmor, ArmedModel {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart right_arm;
    private final ModelPart left_arm;

    private final ModelPart right_item;
    private final ModelPart left_item;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public StrayWarriorModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.right_item = this.right_arm.getChild("right_item");
        this.left_item = this.left_arm.getChild("left_item");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
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
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.7F * limbSwingAmount;
        this.right_leg.yRot = 0.0F;
        this.left_leg.yRot = 0.0F;
        this.right_leg.zRot = 0.0F;
        this.left_leg.zRot = 0.0F;

        if (!entity.attackAnimationState.isStarted() && !entity.guardAnimationState.isStarted()) {
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                this.applyStatic(SpearAttackAnimations.IDLE_RIGHT);
            } else {
                this.applyStatic(SpearAttackAnimations.IDLE_LEFT);
            }
        }

        if (entity.getMainArm() == HumanoidArm.RIGHT) {
            this.animate(entity.attackAnimationState, SpearAttackAnimations.SPIN_SPEAR_ATTACK_RIGHT, ageInTicks);
        } else {
            this.animate(entity.attackAnimationState, SpearAttackAnimations.SPIN_SPEAR_ATTACK_LEFT, ageInTicks);
        }

        if (entity.getMainArm() == HumanoidArm.RIGHT) {
            this.animate(entity.guardAnimationState, SpearAttackAnimations.GUARD_RIGHT, ageInTicks);
        } else {
            this.animate(entity.guardAnimationState, SpearAttackAnimations.GUARD_LEFT, ageInTicks);
        }

    }

    @Override
    public ModelPart root() {
        return root;
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
    public void translateToHand(HumanoidArm p_102108_, PoseStack p_102109_) {
        this.getArm(p_102108_).translateAndRotate(p_102109_);
        this.getArmItem(p_102108_).translateAndRotate(p_102109_);
        p_102109_.translate(0, -(8F / 16F), 0);
    }
}