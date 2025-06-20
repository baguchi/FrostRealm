package baguchan.frostrealm.client.model;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.SeekerAnimations;
import baguchan.frostrealm.client.render.state.SeekerRenderState;
import baguchan.frostrealm.entity.boss.Seeker;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

public class SeekerModel<T extends SeekerRenderState> extends EntityModel<T> implements ArmedModel {
    public final ModelPart all;
    public final ModelPart body;
    public final ModelPart neck;
    public final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart head_upper;
    private final ModelPart hood;
    private final ModelPart bodyleggings;
    private final ModelPart necklace;
    private final ModelPart cloth;
    private final ModelPart leftArm;
    private final ModelPart leftArm2;
    private final ModelPart leftArmGlobe;
    private final ModelPart leftArmPat;
    private final ModelPart rightArm;
    private final ModelPart rightArm2;
    private final ModelPart rightArmGlobe;
    private final ModelPart righArmPat;
    private final ModelPart rightLeg;
    private final ModelPart rightLeg2;
    private final ModelPart leftLeg;
    private final ModelPart leftLeg2;
    private final KeyframeAnimation walkAnimationState;
    private final KeyframeAnimation preAttackAnimationState;
    private final KeyframeAnimation attackAnimationState;
    private final KeyframeAnimation stopAttackAnimationState;
    private final KeyframeAnimation deathAnimationState;
    private final KeyframeAnimation jumpAnimationState;
    private final KeyframeAnimation jumpStopAnimationState;

    public SeekerModel(ModelPart root) {
        super(root);
        this.all = root.getChild("all");
        this.body = this.all.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.head_upper = this.head.getChild("head_upper");
        this.hood = this.head_upper.getChild("hood");
        this.bodyleggings = this.body.getChild("bodyleggings");
        this.necklace = this.body.getChild("necklace");
        this.cloth = this.body.getChild("cloth");
        this.leftArm = this.body.getChild("leftArm");
        this.leftArm2 = this.leftArm.getChild("leftArm2");
        this.leftArmGlobe = this.leftArm2.getChild("leftArmGlobe");
        this.leftArmPat = this.leftArm.getChild("leftArmPat");
        this.rightArm = this.body.getChild("rightArm");
        this.rightArm2 = this.rightArm.getChild("rightArm2");
        this.rightArmGlobe = this.rightArm2.getChild("rightArmGlobe");
        this.righArmPat = this.rightArm.getChild("righArmPat");
        this.rightLeg = this.all.getChild("rightLeg");
        this.rightLeg2 = this.rightLeg.getChild("rightLeg2");
        this.leftLeg = this.all.getChild("leftLeg");
        this.leftLeg2 = this.leftLeg.getChild("leftLeg2");
        this.walkAnimationState = SeekerAnimations.walk.bake(root);
        this.preAttackAnimationState = SeekerAnimations.attack_pre.bake(root);
        this.attackAnimationState = SeekerAnimations.attack.bake(root);
        this.stopAttackAnimationState = SeekerAnimations.attack_stop.bake(root);
        this.deathAnimationState = SeekerAnimations.death.bake(root);
        this.jumpAnimationState = SeekerAnimations.jump_attack.bake(root);
        this.jumpStopAnimationState = SeekerAnimations.finish_jump_attack.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 46).addBox(-1.8039F, -34.2163F, 0.8921F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.6961F, -34.2163F, -4.2843F, 12.0F, 15.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(68, 12).addBox(-1.8039F, -11.2924F, -2.0177F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(42, 10).addBox(-3.8039F, -6.2924F, -3.0177F, 8.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -3.7837F, 0.1961F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(40, 68).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1961F, -18.2163F, 2.8921F, -0.3927F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.1961F, -34.1821F, 3.37F));

        PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(70, 0).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.0342F, -0.4779F, 0.1309F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-0.1961F, -6.0342F, -4.0661F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.1079F, -2.6279F, 3.3527F));

        PartDefinition teeth_r1 = jaw.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(76, 31).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 38).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6279F, -3.1027F, 0.2182F, 0.0F, 0.0F));

        PartDefinition head_upper = head.addOrReplaceChild("head_upper", CubeListBuilder.create().texOffs(32, 24).addBox(-4.0F, -10.0F, -8.3333F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(76, 27).addBox(-2.0F, -4.0F, -8.3333F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 0).addBox(-4.0F, -4.0F, -6.3333F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1079F, -3.0F, 3.8333F));

        PartDefinition hood = head_upper.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 84).addBox(-4.0F, -10.0F, -8.3333F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition orb = head.addOrReplaceChild("orb", CubeListBuilder.create().texOffs(76, 35).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition bodyleggings = body.addOrReplaceChild("bodyleggings", CubeListBuilder.create().texOffs(0, 24).addBox(-4.6079F, 0.0F, -2.6079F, 10.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1961F, -11.2163F, -1.0F));

        PartDefinition necklace = body.addOrReplaceChild("necklace", CubeListBuilder.create(), PartPose.offset(0.1961F, -31.2063F, -3.9556F));

        PartDefinition necklace_r1 = necklace.addOrReplaceChild("necklace_r1", CubeListBuilder.create().texOffs(70, 74).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 74).addBox(6.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -0.01F, -0.1523F, -0.1309F, 0.0F, 0.0F));

        PartDefinition necklace_r2 = necklace.addOrReplaceChild("necklace_r2", CubeListBuilder.create().texOffs(56, 74).addBox(-1.0F, -3.0F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.49F, -0.1523F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cloth = body.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(32, 86).addBox(-5.6961F, 0.7837F, -4.2843F, 12.0F, 15.0F, 9.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -35.0F, 0.0F));

        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(16, 66).addBox(1.5F, -2.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -30.7163F, 0.3039F));

        PartDefinition leftArm2 = leftArm.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(16, 46).addBox(-1.5F, 0.5F, -0.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 12.0F, -1.0F));

        PartDefinition leftArmGlobe = leftArm2.addOrReplaceChild("leftArmGlobe", CubeListBuilder.create().texOffs(64, 59).addBox(-2.0F, 0.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition leftArmPat = leftArm.addOrReplaceChild("leftArmPat", CubeListBuilder.create().texOffs(65, 84).mirror().addBox(-4.0F, -4.0F, -3.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(28, 68).addBox(-2.5F, -2.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -30.7163F, 0.3039F));

        PartDefinition rightArm2 = rightArm.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(28, 48).addBox(-1.5F, 0.5F, -0.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 12.0F, -1.0F));

        PartDefinition rightArmGlobe = rightArm2.addOrReplaceChild("rightArmGlobe", CubeListBuilder.create().texOffs(0, 66).addBox(-2.0F, 0.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition righArmPat = rightArm.addOrReplaceChild("righArmPat", CubeListBuilder.create().texOffs(65, 84).addBox(-4.0F, -4.0F, -3.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg = all.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(64, 21).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -4.0F, -0.5F));

        PartDefinition rightLeg2 = rightLeg.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(40, 48).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, -1.0F));

        PartDefinition leftLeg = all.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(64, 40).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -4.0F, -0.5F));

        PartDefinition leftLeg2 = leftLeg.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(52, 48).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, -1.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.yRot = entity.yRot * ((float) Math.PI / 180F) * (2F / 3F);
        this.head.xRot = entity.xRot * ((float) Math.PI / 180F) * (2F / 3F);
        this.neck.yRot = entity.yRot * ((float) Math.PI / 180F) * (1F / 3F);
        this.neck.xRot = entity.xRot * ((float) Math.PI / 180F) * (1F / 3F);

        this.walkAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed, 2, 2.5F);

        if (entity.state != Seeker.SeekerState.IDLE) {
            this.leftArm.resetPose();
            this.leftArm2.resetPose();
            this.leftArmGlobe.resetPose();
            this.rightArm.resetPose();
            this.rightArm2.resetPose();
            this.rightArmGlobe.resetPose();
        }
        this.preAttackAnimationState.apply(entity.preAttackAnimationState, entity.ageInTicks);
        this.attackAnimationState.apply(entity.attackAnimationState, entity.ageInTicks);
        this.stopAttackAnimationState.apply(entity.stopAttackAnimationState, entity.ageInTicks);
        this.deathAnimationState.apply(entity.deathAnimationState, entity.ageInTicks);
        this.jumpAnimationState.apply(entity.jumpAnimationState, entity.ageInTicks);
        this.jumpStopAnimationState.apply(entity.jumpStopAnimationState, entity.ageInTicks);
    }

    private ModelPart getArm(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    private ModelPart getArm2(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.leftArm2 : this.rightArm2;
    }


    @Override
    public void translateToHand(HumanoidArm p_102108_, PoseStack p_102109_) {
        this.all.translateAndRotate(p_102109_);
        this.body.translateAndRotate(p_102109_);
        this.getArm(p_102108_).translateAndRotate(p_102109_);
        this.getArm2(p_102108_).translateAndRotate(p_102109_);
        p_102109_.translate(0, (4F / 16F), 0);
    }

}