package baguchan.frostrealm.client.model;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ClustWraithModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructorprivate final ModelPart main;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart leg_left_front;
	private final ModelPart leg_right_front;
	private final ModelPart leg_left_hind;
	private final ModelPart leg_right_hind;


	public ClustWraithModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = this.main.getChild("head");
		this.leg_left_front = this.main.getChild("leg_left_front");
		this.leg_right_front = this.main.getChild("leg_right_front");
		this.leg_left_hind = this.main.getChild("leg_left_hind");
		this.leg_right_hind = this.main.getChild("leg_right_hind");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition leg_left_front = main.addOrReplaceChild("leg_left_front", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.0F, -2.0F));

		PartDefinition leg_right_front = main.addOrReplaceChild("leg_right_front", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, -2.0F));

		PartDefinition leg_left_hind = main.addOrReplaceChild("leg_left_hind", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.0F, 2.0F));

		PartDefinition leg_right_hind = main.addOrReplaceChild("leg_right_hind", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 2.0F));

		return LayerDefinition.create(meshdefinition, 96, 48);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);

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
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}