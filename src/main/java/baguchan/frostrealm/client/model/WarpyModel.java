package baguchan.frostrealm.client.model;// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.WarpyAnimation;
import baguchan.frostrealm.entity.hostile.Warpy;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WarpyModel<T extends Warpy> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart mouth;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart right_wing2;
    private final ModelPart left_wing;
    private final ModelPart left_wing2;

    public WarpyModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.mouth = this.body.getChild("mouth");
        this.right_leg = this.mouth.getChild("right_leg");
        this.left_leg = this.mouth.getChild("left_leg");
        this.right_wing = this.body.getChild("right_wing");
        this.right_wing2 = this.right_wing.getChild("right_wing2");
        this.left_wing = this.body.getChild("left_wing");
        this.left_wing2 = this.left_wing.getChild("left_wing2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

        PartDefinition mouth = body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 28).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 48).addBox(-8.0F, -1.75F, -16.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -4.0F, 8.0F));

        PartDefinition right_leg = mouth.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 66).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(-5.0F, 4.0F, -8.0F));

        PartDefinition left_leg = mouth.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 66).mirror().addBox(-2.0F, -2.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(5.0F, 4.0F, -8.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 67).addBox(-12.0F, 0.0F, -6.0F, 13.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -11.0F, 0.0F));

        PartDefinition right_wing2 = right_wing.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(16, 80).addBox(-13.0F, 0.0F, -6.0F, 13.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 0.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(16, 67).mirror().addBox(0.0F, 0.0F, -6.0F, 13.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -11.0F, 0.0F));

        PartDefinition left_wing2 = left_wing.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(16, 80).mirror().addBox(0.0F, 0.0F, -6.0F, 13.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(13.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 128);
    }

    @Override
    public void setupAnim(Warpy entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.shoot, WarpyAnimation.shoot, ageInTicks, 1.0F);
        this.animate(entity.flying, WarpyAnimation.flying, ageInTicks, 1.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}