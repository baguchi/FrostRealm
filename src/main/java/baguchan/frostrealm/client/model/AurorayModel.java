package baguchan.frostrealm.client.model;// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.Auroray;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class AurorayModel<T extends Auroray> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart wing_r;
	private final ModelPart wing_r2;
	private final ModelPart wing_l;
	private final ModelPart wing_l2;

	public AurorayModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.wing_r = this.body.getChild("wing_r");
		this.wing_r2 = this.wing_r.getChild("wing_r2");
		this.wing_l = this.body.getChild("wing_l");
		this.wing_l2 = this.wing_l.getChild("wing_l2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -17.0F));

		PartDefinition wing_r = body.addOrReplaceChild("wing_r", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-19.0F, 0.0F, -1.0F, 19.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 0.0F, 1.0F));

		PartDefinition wing_r2 = wing_r.addOrReplaceChild("wing_r2", CubeListBuilder.create().texOffs(0, 54).addBox(-19.0F, 0.0F, -1.0F, 19.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.0F, 0.0F, 0.0F));

		PartDefinition wing_l = body.addOrReplaceChild("wing_l", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 19.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, 1.0F));

		PartDefinition wing_l2 = wing_l.addOrReplaceChild("wing_l2", CubeListBuilder.create().texOffs(0, 54).mirror().addBox(0.0F, 0.0F, -1.0F, 19.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(19.0F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(110, 54).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

		PartDefinition stings = body2.addOrReplaceChild("stings", CubeListBuilder.create().texOffs(64, 54).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(63, 0).addBox(-6.0F, -2.0F, -7.0F, 12.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 176, 176);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.wing_r.zRot = -Mth.cos(ageInTicks * 0.09F) * 0.3F;
		this.wing_l.zRot = Mth.cos(ageInTicks * 0.09F) * 0.3F;
		this.wing_r2.zRot = -Mth.cos(ageInTicks * 0.09F) * 0.15F;
		this.wing_l2.zRot = Mth.cos(ageInTicks * 0.09F) * 0.15F;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}