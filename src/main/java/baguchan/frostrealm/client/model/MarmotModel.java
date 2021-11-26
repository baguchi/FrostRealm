package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.Marmot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MarmotModel<T extends Marmot> extends EntityModel<T> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart handR;
	private final ModelPart handL;
	private final ModelPart legR;
	private final ModelPart legL;

	public MarmotModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.handR = root.getChild("handR");
		this.handL = root.getChild("handL");
		this.legR = root.getChild("legR");
		this.legL = root.getChild("legL");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -6.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -3.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 11).addBox(-3.5F, -3.0F, -9.0F, 7.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 6.0F));

		PartDefinition handR = partdefinition.addOrReplaceChild("handR", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 21.0F, -1.0F));

		PartDefinition handL = partdefinition.addOrReplaceChild("handL", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 21.0F, -1.0F));

		PartDefinition legR = partdefinition.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 21.0F, 5.0F));

		PartDefinition legL = partdefinition.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 21.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);

		this.legR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.legL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.legR.zRot = 0.0F;
		this.legL.zRot = 0.0F;
		this.handR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.handL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.handR.zRot = 0.0F;
		this.handL.zRot = 0.0F;

		this.body.xRot = 0.0F;
		this.body.y = 18.0F;
		this.body.z = 6.0F;

		this.handR.y = 21.0F;
		this.handL.y = 21.0F;
		this.handR.z = -2.0F;
		this.handL.z = -2.0F;

		this.head.y = 17.0F;
		this.head.z = -3.0F;
		this.legR.z = 5.0F;
		this.legL.z = 5.0F;

		if (entity.isStanding()) {
			this.body.xRot = (float) -(Math.PI / 2F);
			this.body.y = 21.0F;
			this.body.z = 3.0F;
			this.handR.y = 14.0F;
			this.handL.y = 14.0F;
			this.handR.z = -1.0F;
			this.handL.z = -1.0F;
			this.head.y = 9.5F;
			this.head.z = 3.0F;
			this.legR.z = 2.0F;
			this.legL.z = 2.0F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		handR.render(poseStack, buffer, packedLight, packedOverlay);
		handL.render(poseStack, buffer, packedLight, packedOverlay);
		legR.render(poseStack, buffer, packedLight, packedOverlay);
		legL.render(poseStack, buffer, packedLight, packedOverlay);
	}
}