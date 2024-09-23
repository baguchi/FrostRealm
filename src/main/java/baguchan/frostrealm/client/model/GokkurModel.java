package baguchan.frostrealm.client.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.GokkurAnimations;
import baguchan.frostrealm.entity.hostile.Gokkur;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GokkurModel<T extends Gokkur> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body_rotation;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart body;

    public GokkurModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body_rotation = this.root.getChild("body_rotation");
        this.left_leg = this.body_rotation.getChild("left_leg");
        this.right_leg = this.body_rotation.getChild("right_leg");
        this.body = this.body_rotation.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_rotation = root.addOrReplaceChild("body_rotation", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition left_leg = body_rotation.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 8.0F, 0.0F));

        PartDefinition right_leg = body_rotation.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 8.0F, 0.0F));

        PartDefinition body = body_rotation.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Gokkur entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.rollAnimationState.isStarted() || entity.startRollAnimationState.isStarted()) {
            this.animate(entity.rollAnimationState, GokkurAnimations.roll, ageInTicks);
            this.animate(entity.startRollAnimationState, GokkurAnimations.roll_start, ageInTicks);
        } else {
            this.animateWalk(GokkurAnimations.walk, limbSwing, limbSwingAmount, 1.0F, 4.0F);
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}