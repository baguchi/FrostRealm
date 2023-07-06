package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.3
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import bagu_chan.bagus_lib.client.layer.IArmor;
import baguchan.frostrealm.client.animation.YetiAnimations;
import baguchan.frostrealm.entity.Yeti;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class YetiModel<T extends Yeti> extends HierarchicalModel<T> implements HeadedModel, ArmedModel, IArmor {
    private final ModelPart realRoot;
    private final ModelPart root;

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

	public YetiModel(ModelPart part) {
		super();
		this.realRoot = part;
		this.root = part.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		this.rightArm = this.root.getChild("right_arm");
		this.leftArm = this.root.getChild("left_arm");
		this.rightLeg = this.root.getChild("right_leg");
		this.leftLeg = this.root.getChild("left_leg");
	}

	@Override
	public ModelPart root() {
		return this.realRoot;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(5, 24).addBox(-14.0F, -2.0F, -8.0F, 28.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(44, 61).addBox(-2.5F, -3.0F, -3.0F, 8.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -13.0F, 0.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(5, 61).addBox(-5.5F, -3.0F, -3.0F, 8.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -13.0F, 0.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 90).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(18.0F, -27.0F, -1.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(4, 90).addBox(-4.0F, -0.5F, -5.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-18.0F, -27.0F, -1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -10.5F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(-1.0F, 2.0F, -12.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 7).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, -3.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(40, 0).addBox(4.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-6.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -9.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);

		float f = 1.0F;

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.7F * limbSwingAmount / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;

		float f1 = attackTime;

		float f2 = ageInTicks - entity.tickCount;

		if (entity.isTrade()) {
			this.head.xRot = 30F * ((float) Math.PI / 180F);
			this.head.yRot = 0.0F;
			if (entity.getMainArm() == HumanoidArm.LEFT) {
                this.rightArm.xRot = -42.5f * ((float) Math.PI / 180F);
                this.rightArm.yRot = -52.5F * ((float) Math.PI / 180F);
            } else {
                this.leftArm.xRot = -42.5f * ((float) Math.PI / 180F);
                this.leftArm.yRot = 52.5F * ((float) Math.PI / 180F);
            }
		}

		if (f1 > 0) {
			if (entity.getMainArm() == HumanoidArm.RIGHT) {
				this.rightArm.zRot -= Mth.sin((float) Math.PI * f1) * 0.75F;
				this.rightArm.xRot -= Mth.sin((float) Math.PI * f1) * 0.5F;
			} else {
				this.leftArm.zRot += Mth.sin((float) Math.PI * f1) * 0.75F;
				this.leftArm.xRot -= Mth.sin((float) Math.PI * f1) * 0.5F;
			}
		}


		this.animateWalk(YetiAnimations.IDLE, ageInTicks, 1.0F, 0.1F, 0.1F);
		this.animate(entity.warmingAnimation, YetiAnimations.WARMING, ageInTicks);
	}

	private HumanoidArm getAttackArm(T p_102857_) {

		HumanoidArm humanoidarm = p_102857_.getMainArm();
		return p_102857_.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
	}

	public ModelPart getArm(HumanoidArm p_102923_) {
		return p_102923_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
	}

	public ModelPart getHead() {
		return this.head;
	}

    public void translateToHand(HumanoidArm p_102925_, PoseStack p_102926_) {
		this.root.translateAndRotate(p_102926_);
		this.getArm(p_102925_).translateAndRotate(p_102926_);
		p_102926_.translate(0, 0.8D, 0);
		if (this.young) {
			p_102926_.scale(1.5F, 1.5F, 1.5F);
			p_102926_.translate(-0.75F, -0.4F, 0.0F);
			p_102926_.scale(1.4F, 1.4F, 1.4F);
		}
	}

	@Override
	public Iterable<ModelPart> rightHandArmors() {
		return ImmutableList.of(this.rightArm);
	}

	@Override
	public Iterable<ModelPart> leftHandArmors() {
		return ImmutableList.of(this.leftArm);
	}

	@Override
	public Iterable<ModelPart> rightLegPartArmors() {
		return ImmutableList.of(this.rightLeg);
	}

	@Override
	public Iterable<ModelPart> leftLegPartArmors() {
		return ImmutableList.of(this.leftLeg);
	}

	@Override
	public Iterable<ModelPart> bodyPartArmors() {
		return ImmutableList.of(this.body);
	}

	@Override
	public Iterable<ModelPart> headPartArmors() {
		return ImmutableList.of(this.head);
	}

	@Override
	public void translateToHead(ModelPart modelPart, PoseStack poseStack) {
		this.root.translateAndRotate(poseStack);
		modelPart.translateAndRotate(poseStack);
		poseStack.translate(0, 0.25F, -0.325F);
		poseStack.scale(1.1F, 1.1F, 1.1F);
	}

	@Override
	public void translateToChest(ModelPart modelPart, PoseStack poseStack) {
		this.root.translateAndRotate(poseStack);
		modelPart.translateAndRotate(poseStack);
		poseStack.translate(0, -0.1F, -0.1F);
		poseStack.scale(2.825F, 2.0F, 2.825F);
	}

	@Override
	public void translateToLeg(ModelPart modelPart, PoseStack poseStack) {
		this.root.translateAndRotate(poseStack);
		modelPart.translateAndRotate(poseStack);
		poseStack.scale(2F, 1.01F, 2F);
	}

	@Override
	public void translateToChestPat(ModelPart modelPart, PoseStack poseStack) {
		this.root.translateAndRotate(poseStack);
		modelPart.translateAndRotate(poseStack);
		if (modelPart == rightArm) {
			poseStack.translate(0.195F, 0F, 0F);
		} else {
			poseStack.translate(-0.195F, 0F, 0F);
		}
		poseStack.scale(1.85F, 1.65F, 1.85F);
	}
}