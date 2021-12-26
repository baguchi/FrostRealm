package baguchan.frostrealm.client.model;// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.Gokkudillo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GokkudilloModel<T extends Gokkudillo> extends EntityModel<T> {

	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart tail;
	private final ModelPart legR;
	private final ModelPart legL;
	private final ModelPart legBackR;
	private final ModelPart legBackL;
	private final ModelPart head;
	private final ModelPart head2;

	public GokkudilloModel(ModelPart root) {
		this.body = root.getChild("body");
		this.body2 = this.body.getChild("body2");
		this.tail = this.body2.getChild("tail");
		this.legR = this.body.getChild("legR");
		this.legL = this.body.getChild("legL");
		this.legBackR = this.body.getChild("legBackR");
		this.legBackL = this.body.getChild("legBackL");
		this.head = this.body.getChild("head");
		this.head2 = this.head.getChild("head2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -4.5F));
		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 14).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 7.0F));
		PartDefinition tail = body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 3.0F));
		PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(20, 14).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, 2.0F));
		PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(20, 14).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 2.0F));
		PartDefinition legBackR = body.addOrReplaceChild("legBackR", CubeListBuilder.create().texOffs(20, 14).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, 7.0F));
		PartDefinition legBackL = body.addOrReplaceChild("legBackL", CubeListBuilder.create().texOffs(20, 14).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 7.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));
		PartDefinition head2 = head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(40, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -3.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = (netHeadYaw * ((float) Math.PI / 180F)) / 2;
		this.head.xRot = (headPitch * ((float) Math.PI / 180F)) / 2;
		this.head2.yRot = (netHeadYaw * ((float) Math.PI / 180F)) / 2;
		this.head2.xRot = (headPitch * ((float) Math.PI / 180F)) / 2;

		this.tail.yRot = Mth.cos(ageInTicks * 0.1F) * 0.25F;

		this.legR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.legL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.legR.zRot = 0.0F;
		this.legL.zRot = 0.0F;
		this.legBackR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.legBackL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.legBackR.zRot = 0.0F;
		this.legBackL.zRot = 0.0F;

		this.legR.visible = !entity.isRolling();
		this.legL.visible = !entity.isRolling();
		this.legBackR.visible = !entity.isRolling();
		this.legBackL.visible = !entity.isRolling();
		this.head2.visible = !entity.isRolling();
		this.tail.visible = !entity.isRolling();
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
	}
}