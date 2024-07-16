package baguchan.frostrealm.client.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.hostile.part.CorruptedWalker;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CorruptedWalkerModel<T extends CorruptedWalker> extends HierarchicalModel<T> {
    private final ModelPart realroot;

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart plate;
    private final ModelPart plate2;
    private final ModelPart plate3;
    private final ModelPart plate4;
    private final ModelPart body2;
    private final ModelPart head;

    public CorruptedWalkerModel(ModelPart root) {
        this.realroot = root;
        this.root = root.getChild("root");
        this.body = root.getChild("body");
        this.plate = root.getChild("plate");
        this.plate2 = root.getChild("plate2");
        this.plate3 = root.getChild("plate3");
        this.plate4 = root.getChild("plate4");
        this.body2 = root.getChild("body2");
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition plate = body.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(49, 0).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -8.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition plate2 = body.addOrReplaceChild("plate2", CubeListBuilder.create().texOffs(49, 0).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -6.0F, 0.0F, -0.6981F, -1.5708F, 0.0F));

        PartDefinition plate3 = body.addOrReplaceChild("plate3", CubeListBuilder.create().texOffs(49, 0).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 8.0F, -0.6981F, 3.1416F, 0.0F));

        PartDefinition plate4 = body.addOrReplaceChild("plate4", CubeListBuilder.create().texOffs(49, 0).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -6.0F, 0.0F, -0.6981F, 1.5708F, 0.0F));

        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(80, 16).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = body2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(78, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(CorruptedWalker entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
    }

    public ModelPart root() {
        return realroot;
    }
}