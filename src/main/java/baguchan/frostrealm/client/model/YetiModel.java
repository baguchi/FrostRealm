package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.3
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.Yeti;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class YetiModel<T extends Yeti> extends HumanoidModel<T> {

	public YetiModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition handR = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(4, 90).addBox(-4.1581F, 0.6404F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -5.0F, -2.0F, 0.0F, 0.0F, 0.1F));

		PartDefinition legR = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(5, 61).addBox(-4.5F, -3.0F, -3.0F, 8.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 12.0F, 0.1F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -10.5F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -9.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition horn2 = head.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(39, 0).addBox(-6.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -4.0F, -9.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -11.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(5, 24).addBox(-14.0F, -2.0F, -8.0F, 28.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition handL = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 90).addBox(-3.8419F, 0.6404F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -5.0F, -1.0F, 0.0F, 0.0F, -0.1F));

		PartDefinition legL = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(44, 61).addBox(-2.5F, -3.0F, -3.0F, 8.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 12.0F, 0.1F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;

		float f1 = attackTime;

		if (f1 > 0) {
			if (entity.getMainArm() == HumanoidArm.RIGHT) {
				this.rightArm.zRot -= Mth.sin((float) Math.PI * f1) * 0.75F;
				this.rightArm.xRot -= Mth.sin((float) Math.PI * f1) * 0.5F;
			} else {
				this.rightArm.zRot -= Mth.sin((float) Math.PI * f1) * 0.75F;
				this.rightArm.xRot += Mth.sin((float) Math.PI * f1) * 0.5F;
			}
		}
	}

	public void prepareMobModel(T p_102957_, float p_102958_, float p_102959_, float p_102960_) {

	}

	private HumanoidArm getAttackArm(T p_102857_) {

		HumanoidArm humanoidarm = p_102857_.getMainArm();
		return p_102857_.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
	}

	protected void setupAttackAnimation(T p_102858_, float p_102859_) {
		if (!(this.attackTime <= 0.0F)) {
			HumanoidArm humanoidarm = this.getAttackArm(p_102858_);
			ModelPart modelpart = this.getArm(humanoidarm);
			float f = this.attackTime;
			this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
			if (humanoidarm == HumanoidArm.LEFT) {
				this.body.yRot *= -1.0F;
			}

			this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
			this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
			this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
			this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
			this.rightArm.yRot += this.body.yRot;
			this.leftArm.yRot += this.body.yRot;
			this.leftArm.xRot += this.body.yRot;
			f = 1.0F - this.attackTime;
			f = f * f;
			f = f * f;
			f = 1.0F - f;
			float f1 = Mth.sin(f * (float) Math.PI);
			float f2 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			modelpart.xRot = (float) ((double) modelpart.xRot - ((double) f1 * 1.2D + (double) f2));
			modelpart.yRot += this.body.yRot * 2.0F;
			modelpart.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
		}
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
	}

	public ModelPart getArm(HumanoidArm p_102923_) {
		return p_102923_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
	}

	public ModelPart getHead() {
		return this.head;
	}

	public void translateToHand(HumanoidArm p_102925_, PoseStack p_102926_) {
		this.getArm(p_102925_).translateAndRotate(p_102926_);
	}
}