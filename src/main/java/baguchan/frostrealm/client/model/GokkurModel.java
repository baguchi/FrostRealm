package baguchan.frostrealm.client.model;// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.Gokkur;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GokkurModel<T extends Gokkur> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart legR;
	private final ModelPart legL;

	public GokkurModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legR = root.getChild("legR");
		this.legL = root.getChild("legL");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition crystal = body.addOrReplaceChild("crystal", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition crystal2 = body.addOrReplaceChild("crystal2", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, 0.43F, 0.0F, 0.0F));

		PartDefinition crystal3 = body.addOrReplaceChild("crystal3", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, -0.43F, 0.0F, 0.0F));

		PartDefinition crystal4 = body.addOrReplaceChild("crystal4", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition legR = partdefinition.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 22.0F, 0.0F));

		PartDefinition legL = partdefinition.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 22.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		this.legR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.legL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.legR.zRot = 0.0F;
		this.legL.zRot = 0.0F;

		this.legR.visible = !entity.isRolling();
		this.legL.visible = !entity.isRolling();
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
		legR.render(poseStack, buffer, packedLight, packedOverlay);
		legL.render(poseStack, buffer, packedLight, packedOverlay);
	}
}