package baguchan.frostrealm.client.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.BabyAnimations;
import baguchan.frostrealm.client.animation.WolfflueAnimations;
import baguchan.frostrealm.entity.animal.Wolfflue;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WolfflueModel<T extends Wolfflue> extends AgeableHierarchicalModel<T> {
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

    public WolfflueModel(ModelPart root) {
        super(0.5F, 24.0F);
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
    public void prepareMobModel(T entity, float p_102615_, float p_102616_, float p_102617_) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        super.prepareMobModel(entity, p_102615_, p_102616_, p_102617_);

        this.head.zRot = entity.getHeadRollAngle(p_102617_);
    }

    @Override
    public void setupAnim(Wolfflue entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.tail.xRot = entity.getTailAngle();

        float f = ageInTicks - (float) entity.tickCount;

        if (entity.isInSittingPose()) {
            if (entity.idleSitAnimationState.isStarted() || entity.idleSit2AnimationState.isStarted()) {
                this.animate(entity.idleSitAnimationState, WolfflueAnimations.sit_idle, ageInTicks);
                this.animate(entity.idleSit2AnimationState, WolfflueAnimations.sit_idle2, ageInTicks);
            } else {
                this.applyStatic(WolfflueAnimations.sit);
            }
        } else {
            if (entity.jumpAnimationState.isStarted()) {
                this.animate(entity.jumpAnimationState, WolfflueAnimations.jump, ageInTicks);
            } else {
                this.animateWalk(WolfflueAnimations.run, limbSwing, limbSwingAmount * (entity.getRunningScale(f)), 1.0F, 2.5F);
                this.animateWalk(WolfflueAnimations.walk, limbSwing, limbSwingAmount * (1.0F - entity.getRunningScale(f)), 1.0F, 5.0F);
            }
        }

        if (this.young) {
            this.applyStatic(BabyAnimations.baby);
        }
    }

    @Override
    public ModelPart root() {
        return this.all;
    }
}