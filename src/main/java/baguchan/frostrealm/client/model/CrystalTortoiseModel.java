package baguchan.frostrealm.client.model;
// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.CrystalTortoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CrystalTortoiseModel<T extends CrystalTortoise> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart body;
	private final ModelPart legR;
	private final ModelPart head;
	private final ModelPart legBackR;
	private final ModelPart legL;
	private final ModelPart legBackL;
	private final ModelPart crystal;
	private final ModelPart crystal2;
	private final ModelPart crystal3;
	private final ModelPart crystal4;

	public CrystalTortoiseModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legR = root.getChild("legR");
		this.head = root.getChild("head");
		this.legBackR = root.getChild("legBackR");
		this.legL = root.getChild("legL");
		this.legBackL = root.getChild("legBackL");
		this.crystal = root.getChild("crystal");
		this.crystal2 = root.getChild("crystal2");
		this.crystal3 = root.getChild("crystal3");
		this.crystal4 = root.getChild("crystal4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(27, 0).addBox(-7.0F, 4.5F, 4.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(27, 21).addBox(-5.5F, 3.0F, 6.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -10.0F));

		PartDefinition legR = partdefinition.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 20.0F, -3.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(3, 0).addBox(-3.0F, -2.5F, -6.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, -5.0F));

		PartDefinition legBackR = partdefinition.addOrReplaceChild("legBackR", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 20.0F, 5.6F));

		PartDefinition legL = partdefinition.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 20.0F, -3.0F));

		PartDefinition legBackL = partdefinition.addOrReplaceChild("legBackL", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 20.0F, 5.6F));

		PartDefinition crystal = partdefinition.addOrReplaceChild("crystal", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.5F, -1.0F, 0.4304F, 0.3128F, -0.2346F));

		PartDefinition crystal2 = partdefinition.addOrReplaceChild("crystal2", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.4691F));

		PartDefinition crystal3 = partdefinition.addOrReplaceChild("crystal3", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 14.5F, 3.0F, 0.0F, 0.7819F, -0.5475F));

		PartDefinition crystal4 = partdefinition.addOrReplaceChild("crystal4", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 4.0F, -0.7037F, 0.0F, 0.0782F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
		legR.render(poseStack, buffer, packedLight, packedOverlay);
		head.render(poseStack, buffer, packedLight, packedOverlay);
		legBackR.render(poseStack, buffer, packedLight, packedOverlay);
		legL.render(poseStack, buffer, packedLight, packedOverlay);
		legBackL.render(poseStack, buffer, packedLight, packedOverlay);
		crystal.render(poseStack, buffer, packedLight, packedOverlay);
		crystal2.render(poseStack, buffer, packedLight, packedOverlay);
		crystal3.render(poseStack, buffer, packedLight, packedOverlay);
		crystal4.render(poseStack, buffer, packedLight, packedOverlay);
	}
}