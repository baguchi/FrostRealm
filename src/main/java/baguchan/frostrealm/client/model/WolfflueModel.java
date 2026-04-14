package baguchan.frostrealm.client.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.WolfflueAnimations;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WolfflueModel<T extends WolfflueRenderState> extends EntityModel<T> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5F);
    public final ModelPart all;
    public final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart rightLeg;
    private final ModelPart rightLeg2;
    private final ModelPart rightLeg3;
    private final ModelPart leftLeg;
    private final ModelPart leftLeg2;
    private final ModelPart leftLeg3;

    private final KeyframeAnimation idleSitAnimationState;
    private final KeyframeAnimation idleSit2AnimationState;
    private final KeyframeAnimation runAnimationState;
    private final KeyframeAnimation walkAnimationState;
    private final KeyframeAnimation sitAnimationState;
    private final KeyframeAnimation babySitAnimationState;
    private final KeyframeAnimation jumpAnimationState;


    public WolfflueModel(ModelPart root) {
        super(root);
        this.all = root.getChild("all");
        this.head = this.all.getChild("head");
        this.body = this.all.getChild("body");
        this.tail = this.body.getChild("tail");
        this.rightLeg = this.all.getChild("rightLeg");
        this.rightLeg2 = this.rightLeg.getChild("rightLeg2");
        this.rightLeg3 = this.rightLeg.getChild("rightLeg3");
        this.leftLeg = this.all.getChild("leftLeg");
        this.leftLeg2 = this.leftLeg.getChild("leftLeg2");
        this.leftLeg3 = this.leftLeg.getChild("leftLeg3");
        this.idleSitAnimationState = WolfflueAnimations.sit_idle.bake(root);
        this.idleSit2AnimationState = WolfflueAnimations.sit_idle2.bake(root);
        this.walkAnimationState = WolfflueAnimations.walk.bake(root);
        this.runAnimationState = WolfflueAnimations.run.bake(root);
        this.sitAnimationState = WolfflueAnimations.sit.bake(root);
        this.babySitAnimationState = WolfflueAnimations.baby_sit.bake(root);
        this.jumpAnimationState = WolfflueAnimations.jump.bake(root);

    }

    public static LayerDefinition createBabyBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 0).addBox(1.0F, -7.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-3.0F, -7.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -6.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-2.0F, -4.0F, -13.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, 6.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -7.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 3.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 12).addBox(-3.5F, -13.0F, 0.5F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-3.5F, -14.0F, -6.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(46, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 5).addBox(-1.0F, 0.0F, 4.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.5F, 6.0F));

        PartDefinition rightLeg = all.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg2 = rightLeg.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 24).addBox(-1.5F, 1.75F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, -8.0F, -5.0F));

        PartDefinition rightLeg3 = rightLeg.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(24, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 24).addBox(-1.5F, 1.75F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, -8.0F, 4.75F));

        PartDefinition leftLeg = all.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftLeg3 = leftLeg.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(36, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 24).addBox(-1.5F, 1.75F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -8.0F, 4.75F));

        PartDefinition leftLeg2 = leftLeg.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(12, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-1.5F, 1.75F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -8.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 27).addBox(1.0F, -11.0F, -3.0F, 3.0F, 5.0F, 2.0F, cubeDeformation)
                .texOffs(40, 27).addBox(-4.0F, -11.0F, -3.0F, 3.0F, 5.0F, 2.0F, cubeDeformation), PartPose.offset(0.0F, -20.0F, -13.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(52, 39).addBox(-2.0F, -5.0F, -15.0F, 4.0F, 4.0F, 5.0F, cubeDeformation)
                .texOffs(41, 0).addBox(-4.0F, -8.0F, -10.0F, 8.0F, 7.0F, 8.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, 3.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -22.0F, -5.0F, 12.0F, 10.0F, 17.0F, cubeDeformation)
                .texOffs(0, 27).addBox(-7.0F, -24.0F, -16.0F, 14.0F, 14.0F, 12.0F, cubeDeformation), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 27).addBox(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 11.0F, cubeDeformation)
                .texOffs(58, 15).addBox(-2.0F, -1.5F, 12.0F, 4.0F, 2.0F, 5.0F, cubeDeformation), PartPose.offset(0.0F, -19.0F, 11.0F));

        PartDefinition rightLeg = all.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg2 = rightLeg.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(40, 58).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 12.0F, 3.0F, cubeDeformation)
                .texOffs(53, 22).addBox(-2.0F, 4.0F, -2.0F, 5.0F, 5.0F, 5.0F, cubeDeformation), PartPose.offset(-5.0F, -12.0F, -10.0F));

        PartDefinition rightLeg3 = rightLeg.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 12.0F, 3.0F, cubeDeformation)
                .texOffs(0, 53).addBox(-2.0F, 4.0F, -3.0F, 5.0F, 5.0F, 5.0F, cubeDeformation), PartPose.offset(-5.0F, -12.0F, 10.0F));

        PartDefinition leftLeg = all.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftLeg2 = leftLeg.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(0, 63).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 12.0F, 3.0F, cubeDeformation)
                .texOffs(47, 48).addBox(-3.0F, 4.0F, -2.0F, 5.0F, 5.0F, 5.0F, cubeDeformation), PartPose.offset(5.0F, -12.0F, -10.0F));

        PartDefinition leftLeg3 = leftLeg.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(52, 58).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 12.0F, 3.0F, cubeDeformation)
                .texOffs(20, 53).addBox(-3.0F, 4.0F, -3.0F, 5.0F, 5.0F, 5.0F, cubeDeformation), PartPose.offset(5.0F, -12.0F, 10.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.yRot = entity.yRot * ((float) Math.PI / 180F);
        this.head.xRot = entity.xRot * ((float) Math.PI / 180F);

        this.tail.xRot = entity.tailAngle;
        this.head.zRot = entity.headRollAngle;
        float f = entity.partialTick;

        if (entity.isSitting) {
            if (entity.isBaby) {
                this.babySitAnimationState.applyStatic();
            } else {
                if (entity.idleSitAnimationState.isStarted() || entity.idleSit2AnimationState.isStarted()) {
                    this.idleSitAnimationState.apply(entity.idleSitAnimationState, entity.ageInTicks);
                    this.idleSit2AnimationState.apply(entity.idleSit2AnimationState, entity.ageInTicks);
                } else {
                    this.sitAnimationState.applyStatic();
                }
            }
        } else {
            if (entity.jumpAnimationState.isStarted()) {
                this.jumpAnimationState.apply(entity.jumpAnimationState, entity.ageInTicks);
            } else {
                this.runAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed * (entity.running), 1.0F, 2.5F);
                this.walkAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed * (1.0F - entity.running), 1.0F, 5.0F);
            }
        }


    }
}