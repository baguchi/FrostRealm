package baguchan.frostrealm.client.model;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.CrystalFoxAnimations;
import baguchan.frostrealm.entity.CrystalFox;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CrystalFoxModel<T extends CrystalFox> extends HierarchicalModel<T> {
	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart leg_left_front;
	private final ModelPart leg_left_hind;
	private final ModelPart leg_right_front;
	private final ModelPart leg_right_hind;

	public CrystalFoxModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
		this.leg_left_front = this.body.getChild("leg_left_front");
		this.leg_left_hind = this.body.getChild("leg_left_hind");
		this.leg_right_front = this.body.getChild("leg_right_front");
		this.leg_right_hind = this.body.getChild("leg_right_hind");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -6.4F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(50, 19).addBox(-1.0F, -8.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(33, 23).addBox(-1.0F, -7.0F, 6.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(23, 0).addBox(-2.0F, 1.0F, -9.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(-3.0F, -6.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(1.0F, -6.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(36, 14).addBox(2.0F, -1.0F, 4.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(36, 14).addBox(-3.0F, -1.0F, 4.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(44, 14).addBox(-1.0F, -3.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 14).addBox(-1.0F, 3.0F, 2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 11.0F));

		PartDefinition leg_left_front = body.addOrReplaceChild("leg_left_front", CubeListBuilder.create().texOffs(28, 17).addBox(-0.999F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(40, 23).addBox(-0.5F, 3.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, 3.0F));

		PartDefinition leg_left_hind = body.addOrReplaceChild("leg_left_hind", CubeListBuilder.create().texOffs(28, 17).addBox(-0.999F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(40, 23).addBox(-0.5F, 3.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, 10.0F));

		PartDefinition leg_right_front = body.addOrReplaceChild("leg_right_front", CubeListBuilder.create().texOffs(28, 17).addBox(-0.001F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(40, 23).addBox(0.999F, 3.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, 3.0F));

		PartDefinition leg_right_hind = body.addOrReplaceChild("leg_right_hind", CubeListBuilder.create().texOffs(28, 17).addBox(-0.001F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(40, 23).addBox(0.999F, 3.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.leg_right_hind.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg_left_hind.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_right_front.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_left_front.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.animate(entity.eatAnimationState, CrystalFoxAnimations.CRYSTAL_FOX_EATING, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return this.main;
	}
}