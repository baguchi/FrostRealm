package baguchan.frostrealm.client.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class KolossusModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart root;

	private final ModelPart head;
	private final ModelPart leg_L;
	private final ModelPart leg_back_L;
	private final ModelPart leg_R;
	private final ModelPart leg_back_R;

	public KolossusModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.leg_back_L = this.root.getChild("leg_back_L");
		this.leg_back_R = this.root.getChild("leg_back_R");
		this.leg_L = this.root.getChild("leg_L");
		this.leg_R = this.root.getChild("leg_R");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(7.5F, 12.5F, 12.5F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -46.0F, -16.0F, 25.0F, 28.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(0, 63).addBox(-10.0F, -41.0F, 19.0F, 17.0F, 23.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 11.5F, -12.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(12, 25).addBox(-3.0F, 0.0F, 6.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -31.0F, 29.0F));

		PartDefinition leg_back_L = root.addOrReplaceChild("leg_back_L", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 0.0F));

		PartDefinition leg_back_R = root.addOrReplaceChild("leg_back_R", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, -6.5F, 0.0F));

		PartDefinition leg_L = root.addOrReplaceChild("leg_L", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, -21.0F));

		PartDefinition leg_R = root.addOrReplaceChild("leg_R", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, -6.5F, -21.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(52, 63).addBox(-10.0F, -11.0F, -5.5F, 20.0F, 22.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(53, 92).addBox(-6.0F, -2.0F, -11.25F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -22.0F, -30.0F));

		PartDefinition tusk = head.addOrReplaceChild("tusk", CubeListBuilder.create().texOffs(86, 0).addBox(6.0F, -1.0F, -6.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(86, 0).addBox(-9.0F, -1.0F, -6.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -9.0F, -0.9599F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.leg_back_R.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg_back_L.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_R.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_L.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}