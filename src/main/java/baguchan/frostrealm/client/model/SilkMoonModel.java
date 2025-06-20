package baguchan.frostrealm.client.model;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.SilkMoonAnimations;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class SilkMoonModel<T extends LivingEntityRenderState> extends EntityModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart right_ear;
    private final ModelPart left_ear;
    private final ModelPart tail;
    private final ModelPart right_leg_front;
    private final ModelPart right_leg_mid;
    private final ModelPart right_leg_back;
    private final ModelPart left_leg_front;
    private final ModelPart left_leg_mid;
    private final ModelPart left_leg_back;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final KeyframeAnimation flyAnimationState;
    private final KeyframeAnimation flyLegAnimationState;

    public SilkMoonModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.right_ear = this.head.getChild("right_ear");
        this.left_ear = this.head.getChild("left_ear");
        this.tail = this.body.getChild("tail");
        this.right_leg_front = this.body.getChild("right_leg_front");
        this.right_leg_mid = this.body.getChild("right_leg_mid");
        this.right_leg_back = this.body.getChild("right_leg_back");
        this.left_leg_front = this.body.getChild("left_leg_front");
        this.left_leg_mid = this.body.getChild("left_leg_mid");
        this.left_leg_back = this.body.getChild("left_leg_back");
        this.right_wing = this.body.getChild("right_wing");
        this.left_wing = this.body.getChild("left_wing");
        this.flyAnimationState = SilkMoonAnimations.fly.bake(root);
        this.flyLegAnimationState = SilkMoonAnimations.fly_leg.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 0).addBox(-3.0F, -2.5F, -5.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -1.0F, -2.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, -5.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(3, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -0.75F, -1.0F, -0.4363F, 0.0F, -0.1309F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(3, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -0.75F, -1.0F, -0.4363F, 0.0F, 0.1309F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.5F, 0.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

        PartDefinition right_leg_front = body.addOrReplaceChild("right_leg_front", CubeListBuilder.create().texOffs(3, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.5F, -4.0F));

        PartDefinition right_leg_mid = body.addOrReplaceChild("right_leg_mid", CubeListBuilder.create().texOffs(3, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.5F, -2.5F));

        PartDefinition right_leg_back = body.addOrReplaceChild("right_leg_back", CubeListBuilder.create().texOffs(3, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.5F, -1.0F));

        PartDefinition left_leg_front = body.addOrReplaceChild("left_leg_front", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.5F, -4.0F));

        PartDefinition left_leg_mid = body.addOrReplaceChild("left_leg_mid", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.5F, -2.5F));

        PartDefinition left_leg_back = body.addOrReplaceChild("left_leg_back", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.5F, -1.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(19, 10).addBox(-7.0F, 0.0F, -5.0F, 8.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, -4.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(1, 10).addBox(-1.0F, 0.0F, -4.0F, 8.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.flyLegAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed, 2.0F, 2.0F);
        this.flyAnimationState.applyWalk(entity.ageInTicks, 1.0F, 1.0F, 1.0F);
    }
}