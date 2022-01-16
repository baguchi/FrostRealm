package baguchan.frostrealm.client.model;// Made with Blockbench 4.1.2
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.FrostWolf;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FrostWolfModel<T extends FrostWolf> extends ColorableAgeableListModel<T> {
	private final ModelPart body;
	private final ModelPart mane;
	private final ModelPart head;
	private final ModelPart legR;
	private final ModelPart legR2;
	private final ModelPart legL;
	private final ModelPart legL2;
	private final ModelPart tail;

	public FrostWolfModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legR = this.body.getChild("legR");
		this.legL = this.body.getChild("legL");
		this.tail = this.body.getChild("tail");
		this.legL2 = this.body.getChild("legL2");
		this.legR2 = this.body.getChild("legR2");
		this.mane = root.getChild("mane");
		this.head = this.mane.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 11.0F, -3.0F));

		PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(0, 43).addBox(-1.5F, 0.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 5.0F, -1.5F));

		PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(24, 32).addBox(-1.5F, 0.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 5.0F, -1.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 12.0F));

		PartDefinition legL2 = body.addOrReplaceChild("legL2", CubeListBuilder.create().texOffs(0, 32).addBox(-1.5F, 0.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 5.0F, 8.5F));

		PartDefinition legR2 = body.addOrReplaceChild("legR2", CubeListBuilder.create().texOffs(12, 32).addBox(-1.5F, 0.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 5.0F, 8.5F));

		PartDefinition mane = partdefinition.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(40, 0).addBox(-5.0F, -5.5F, -4.0F, 9.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.5F, -3.0F));

		PartDefinition head = mane.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 20).addBox(-2.75F, -2.5F, -4.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -1.5F, -4.0F));

		PartDefinition mouse = head.addOrReplaceChild("mouse", CubeListBuilder.create().texOffs(12, 43).addBox(-1.5F, -1.75F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, -4.0F));

		PartDefinition earR = head.addOrReplaceChild("earR", CubeListBuilder.create().texOffs(24, 43).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.75F, -2.5F, -2.0F));

		PartDefinition earL = head.addOrReplaceChild("earL", CubeListBuilder.create().texOffs(12, 49).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -2.5F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.tail.xRot = entity.getTailAngle();
	}

	public void prepareMobModel(T p_104132_, float p_104133_, float p_104134_, float p_104135_) {
		if (p_104132_.isAngry()) {
			this.tail.yRot = 0.0F;
		} else {
			this.tail.yRot = Mth.cos(p_104133_ * 0.6662F) * 1.4F * p_104134_;
		}

		if (p_104132_.isInSittingPose()) {
			this.body.setPos(-0.5F, 5.0F, -3.0F);
			this.mane.setPos(0.0F, 6.5F, -3.0F);
			this.mane.xRot = 1.2566371F;
			this.mane.yRot = 0.0F;
			this.body.xRot = ((float) Math.PI / 4F);
			this.legR2.xRot = -((float) Math.PI / 2);
			this.legL2.xRot = -((float) Math.PI / 2);
			this.legR.xRot = ((float) Math.PI / 2);
			this.legL.xRot = ((float) Math.PI / 2);
		} else {
			this.body.setPos(-0.5F, 11.0F, -3.0F);
			this.mane.setPos(0.0F, 12.5F, -3.0F);
			this.body.xRot = 0.0F;
			this.mane.xRot = this.body.xRot;
			this.legR2.xRot = Mth.cos(p_104133_ * 0.6662F) * 1.4F * p_104134_;
			this.legL2.xRot = Mth.cos(p_104133_ * 0.6662F + (float) Math.PI) * 1.4F * p_104134_;
			this.legR.xRot = Mth.cos(p_104133_ * 0.6662F + (float) Math.PI) * 1.4F * p_104134_;
			this.legL.xRot = Mth.cos(p_104133_ * 0.6662F) * 1.4F * p_104134_;
		}

		this.head.zRot = p_104132_.getHeadRollAngle(p_104135_) + p_104132_.getBodyRollAngle(p_104135_, 0.0F);
		this.mane.zRot = p_104132_.getBodyRollAngle(p_104135_, -0.08F);
		this.body.zRot = p_104132_.getBodyRollAngle(p_104135_, -0.16F);
		this.tail.zRot = p_104132_.getBodyRollAngle(p_104135_, -0.2F);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.mane);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body);
	}
}