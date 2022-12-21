package baguchan.frostrealm.client.model;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.WarpedIceSoul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

public class WarpedIceModel<T extends WarpedIceSoul> extends HierarchicalModel<T> implements ArmedModel {
	private final ModelPart realRoot;
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart shoulderR;
	private final ModelPart handR;
	private final ModelPart shoulderL;
	private final ModelPart handL;

	public WarpedIceModel(ModelPart root) {
		this.realRoot = root;
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.shoulderR = this.body.getChild("shoulderR");
		this.handR = this.shoulderR.getChild("handR");
		this.shoulderL = this.body.getChild("shoulderL");
		this.handL = this.shoulderR.getChild("handL");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 16).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition sholderR = body.addOrReplaceChild("sholderR", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, 0.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition handR = sholderR.addOrReplaceChild("handR", CubeListBuilder.create().texOffs(64, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition sholderL = body.addOrReplaceChild("sholderL", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, 0.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition handL = sholderL.addOrReplaceChild("handL", CubeListBuilder.create().texOffs(64, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	public void translateToHand(HumanoidArm p_233322_, PoseStack p_233323_) {
		float f = -1.5F;
		float f1 = 1.5F;
		this.root.translateAndRotate(p_233323_);
		this.body.translateAndRotate(p_233323_);
		if (p_233322_ == HumanoidArm.RIGHT) {
			this.shoulderR.translateAndRotate(p_233323_);
			this.handR.translateAndRotate(p_233323_);
		} else {
			this.shoulderL.translateAndRotate(p_233323_);
			this.handL.translateAndRotate(p_233323_);
		}
	}

	@Override
	public ModelPart root() {
		return this.realRoot;
	}
}