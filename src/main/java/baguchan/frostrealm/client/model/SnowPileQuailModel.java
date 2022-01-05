package baguchan.frostrealm.client.model;// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.SnowPileQuail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SnowPileQuailModel<T extends SnowPileQuail> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart legR;
	private final ModelPart legL;
	private final ModelPart head;
	private final ModelPart wingR;
	private final ModelPart wingL;

	public SnowPileQuailModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legR = this.body.getChild("legR");
		this.legL = this.body.getChild("legL");
		this.head = this.body.getChild("head");
		this.wingR = this.body.getChild("wingR");
		this.wingL = this.body.getChild("wingL");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 1.5F));

		PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(18, 16).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(18, 16).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-2.5F, -2.0F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-1.5F, 0.0F, -7.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -4.0F));

		PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -4.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -6.0F, 0.0F));

		PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, 0.0F, -4.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -6.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.legR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.wingR.zRot = 0.0F;
		this.wingL.zRot = -0.0F;

		if (!entity.isOnGround()) {
			this.wingR.zRot = 0.6F + 0.8F * Mth.sin(2.4F * ageInTicks);
			this.wingL.zRot = -0.6F + -0.8F * Mth.sin(2.4F * ageInTicks);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
	}
}