package baguchan.frostrealm.client.model;// Made with Blockbench 4.1.2
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.FrostWolfAnimations;
import baguchan.frostrealm.entity.FrostWolf;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrostWolfModel<T extends FrostWolf> extends ColorableHierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart mane;
	private final ModelPart head;
	private final ModelPart rightLeg;
	private final ModelPart leftLegBack;
	private final ModelPart leftLeg;
	private final ModelPart rightLegBack;
	private final ModelPart tail;

	public FrostWolfModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.rightLeg = this.body.getChild("right_leg");
		this.leftLeg = this.body.getChild("left_leg");
		this.tail = this.body.getChild("tail");
		this.rightLegBack = this.body.getChild("right_leg_back");
		this.leftLegBack = this.body.getChild("left_leg_back");
		this.mane = this.body.getChild("mane");
		this.head = this.mane.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -7.0F, 0.0F, 12.0F, 12.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 14.0F, -4.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 27).addBox(-2.5F, 0.0F, -1.25F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 5.0F, -1.5F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, 0.0F, -1.25F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 5.0F, -1.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(6, 27).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 15.0F));

		PartDefinition left_leg_back = body.addOrReplaceChild("left_leg_back", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, 0.0F, -2.25F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 5.0F, 8.5F));

		PartDefinition right_leg_back = body.addOrReplaceChild("right_leg_back", CubeListBuilder.create().texOffs(0, 27).addBox(-2.5F, 0.0F, -2.25F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 5.0F, 8.5F));

		PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(55, 0).addBox(-8.0F, -10.5F, -4.0F, 15.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.5F, 0.0F));

		PartDefinition head = mane.addOrReplaceChild("head", CubeListBuilder.create().texOffs(93, 0).addBox(-5.75F, -8.5F, -5.0F, 12.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -1.5F, -4.0F));

		PartDefinition mouse = head.addOrReplaceChild("mouse", CubeListBuilder.create().texOffs(87, 17).addBox(-2.5F, -4.75F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, -4.0F));

		PartDefinition earR = head.addOrReplaceChild("earR", CubeListBuilder.create().texOffs(111, 22).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, -8.5F, -2.0F));

		PartDefinition earL = head.addOrReplaceChild("earL", CubeListBuilder.create().texOffs(111, 17).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.25F, -8.5F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.tail.xRot = entity.getTailAngle();
		this.animateWalk(FrostWolfAnimations.WALK, limbSwing, limbSwingAmount, 1.0F, 1.5F);
		if (!this.young) {
			this.applyStatic(FrostWolfAnimations.ADULT);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}