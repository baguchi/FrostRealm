package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.4
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.hostile.FrostWraith;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FrostWraithModel<T extends FrostWraith> extends EntityModel<T> implements HeadedModel {
	private final ModelPart head;
	public final ModelPart body;
	private final ModelPart arm_left;
	private final ModelPart arm_right;
	private final ModelPart leg_left_front;
	private final ModelPart leg_right_front;
	private final ModelPart leg_left_hind;
	private final ModelPart leg_right_hind;

	public final ModelPart main;

	public FrostWraithModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.head = this.body.getChild("head");
		this.arm_left = this.body.getChild("arm_left");
		this.arm_right = this.body.getChild("arm_right");
		this.leg_left_front = this.body.getChild("leg_left_front");
		this.leg_right_front = this.body.getChild("leg_right_front");
		this.leg_left_hind = this.body.getChild("leg_left_hind");
		this.leg_right_hind = this.body.getChild("leg_right_hind");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -14.0F, -2.0F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(64, 15).addBox(5.0F, -14.0F, -2.0F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 20).addBox(-17.0F, -2.0F, -2.0F, 17.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(42, 22).addBox(-21.0F, -1.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -13.0F, 0.0F));

		PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 29).addBox(0.0F, -2.0F, -2.0F, 17.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(42, 27).addBox(17.0F, -1.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -13.0F, 0.0F));

		PartDefinition leg_right_hind = body.addOrReplaceChild("leg_right_hind", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, 2.0F));

		PartDefinition leg_left_hind2 = body.addOrReplaceChild("leg_left_hind", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -4.0F, 2.0F));

		PartDefinition leg_right_front = body.addOrReplaceChild("leg_right_front", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, -2.0F));

		PartDefinition leg_left_front = body.addOrReplaceChild("leg_left_front", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -4.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 96, 48);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);

		this.body.xRot = (float) ((Math.PI / 6F) * limbSwingAmount);

		this.arm_right.xRot = Mth.cos(ageInTicks * 0.05F) * 0.2F;
		this.arm_left.xRot = Mth.cos(ageInTicks * 0.05F) * 0.2F;

		this.arm_right.zRot = (float) -(Math.PI / 6) - Mth.cos(ageInTicks * 0.05F) * 0.2F;
		this.arm_left.zRot = (float) (Math.PI / 6) + Mth.cos(ageInTicks * 0.05F) * 0.2F;

		this.arm_right.xRot += 0.45F * limbSwingAmount;
		this.arm_left.xRot += 0.45F * limbSwingAmount;

		this.leg_right_front.xRot = Mth.clamp(-0.45F + 0.45F * limbSwingAmount, -0.45F, 0.0F);
		this.leg_right_front.zRot = Mth.clamp(0.45F - 0.45F * limbSwingAmount, 0.0F, 0.45F);

		this.leg_left_front.xRot = Mth.clamp(-0.45F + 0.45F * limbSwingAmount, -0.45F, 0.0F);
		this.leg_left_front.zRot = Mth.clamp(-0.45F + 0.45F * limbSwingAmount, -0.45F, 0.0F);

		this.leg_right_hind.xRot = Mth.clamp(0.45F - 0.45F * limbSwingAmount, 0.0F, 0.45F);
		this.leg_right_hind.zRot = Mth.clamp(0.45F - 0.45F * limbSwingAmount, 0.0F, 0.45F);

		this.leg_left_hind.xRot = Mth.clamp(0.45F - 0.45F * limbSwingAmount, 0.0F, 0.45F);
		this.leg_left_hind.zRot = Mth.clamp(-0.45F + 0.45F * limbSwingAmount, -0.45F, 0.0F);

		this.head.xRot -= (float) ((Math.PI / 6F) * limbSwingAmount);

		this.leg_right_front.xRot += 0.45F * limbSwingAmount;
		this.leg_left_front.xRot += 0.45F * limbSwingAmount;
		this.leg_right_hind.xRot += 0.45F * limbSwingAmount;
		this.leg_left_hind.xRot += 0.45F * limbSwingAmount;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}
}