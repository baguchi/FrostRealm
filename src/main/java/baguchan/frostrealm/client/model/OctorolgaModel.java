package baguchan.frostrealm.client.model;// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.Octorolga;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class OctorolgaModel<T extends Octorolga> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	public final ModelPart rock_eye_L;
	public final ModelPart rock_eye_R;
	public final ModelPart rock_tube;
	public final ModelPart rocks;

	public OctorolgaModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.rock_eye_L = this.body.getChild("rock_eye_L");
		this.rock_eye_R = this.body.getChild("rock_eye_R");
		this.rock_tube = this.body.getChild("rock_tube");
		this.rocks = this.body.getChild("rocks");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -10.0F, -10.0F, 20.0F, 20.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rock_eye_L = body.addOrReplaceChild("rock_eye_L", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -2.5F, -10.5F));

		PartDefinition rock_eye_R = body.addOrReplaceChild("rock_eye_R", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -2.5F, -10.5F));

		PartDefinition rock_tube = body.addOrReplaceChild("rock_tube", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -10.0F));

		PartDefinition rocks = body.addOrReplaceChild("rocks", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -4.0F, 6.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(10.0F, -4.0F, 6.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-12.0F, -2.0F, 4.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-12.0F, -4.0F, -10.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(10.0F, -4.0F, -10.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-12.0F, -5.0F, -5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(10.0F, -5.0F, -5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(10.0F, -2.0F, -5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(10.0F, -2.0F, 3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-12.0F, -2.0F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-10.0F, -4.0F, -12.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(5.0F, -4.0F, -12.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-10.0F, -6.0F, 10.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-6.0F, -3.0F, 10.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(6.0F, -3.0F, 10.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}