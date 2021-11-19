package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.4
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.FrostWraith;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FrostWraithModel<T extends FrostWraith> extends EntityModel<T> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart tail;
	private final ModelPart handL;
	private final ModelPart handR;

	public FrostWraithModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.body2 = root.getChild("body2");
		this.tail = root.getChild("tail");
		this.handL = root.getChild("handL");
		this.handR = root.getChild("handR");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 33).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition body2 = partdefinition.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 47).addBox(-4.0F, 0.0F, -2.5F, 7.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 18.0F, -0.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition handL = partdefinition.addOrReplaceChild("handL", CubeListBuilder.create().texOffs(26, 19).addBox(0.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 12.0F, 0.0F));

		PartDefinition handR = partdefinition.addOrReplaceChild("handR", CubeListBuilder.create().texOffs(26, 19).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);

		float f = Mth.sin(this.attackTime * (float) Math.PI);
		float f1 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
		this.handR.zRot = 0.0F;
		this.handL.zRot = 0.0F;
		this.handR.yRot = -(0.1F - f * 0.6F);
		this.handL.yRot = 0.1F - f * 0.6F;
		this.handR.xRot = (-(float) Math.PI / 2F) + Mth.cos(ageInTicks * 0.05F) * 0.15F;
		this.handL.xRot = (-(float) Math.PI / 2F) + Mth.cos(ageInTicks * 0.05F) * 0.15F;

		this.tail.xRot = 0.5236F - Mth.cos(ageInTicks * 0.05F) * 0.35F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		body2.render(poseStack, buffer, packedLight, packedOverlay);
		tail.render(poseStack, buffer, packedLight, packedOverlay);
		handL.render(poseStack, buffer, packedLight, packedOverlay);
		handR.render(poseStack, buffer, packedLight, packedOverlay);
	}
}