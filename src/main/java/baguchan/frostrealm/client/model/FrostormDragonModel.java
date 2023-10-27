package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.FrostormDragon;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrostormDragonModel<T extends FrostormDragon> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head2;


    public FrostormDragonModel(ModelPart root) {
        this.root = root;
        this.head2 = root.getChild("head2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head2 = partdefinition.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, -1.0F, -30.0F, 12.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(34, 64).addBox(3.0F, -12.0F, -10.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 74).addBox(3.0F, -3.0F, -28.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 9.0F));

        PartDefinition mirror = head2.addOrReplaceChild("mirror", CubeListBuilder.create().texOffs(18, 64).addBox(-5.0F, -18.0F, -68.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-5.0F, -9.0F, -86.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 58.0F));

        PartDefinition jaw2 = head2.addOrReplaceChild("jaw2", CubeListBuilder.create().texOffs(64, 0).addBox(-7.0F, 0.0F, -17.0F, 14.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -14.0F));

        PartDefinition horn = head2.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(-8.0F, -11.0F, 6.5F));

        PartDefinition group = horn.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(2.0F, 4.0922F, 6.8757F));

        PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0F, -5.3422F, 1.6243F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -2.5F, -6.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = group.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(66, 64).addBox(-1.0F, -8.7703F, -0.7647F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -5.0719F, 2.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r3 = group.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 53).addBox(-2.0F, -3.4142F, -2.9868F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -3.4281F, -8.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r4 = group.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 53).addBox(-2.5F, -2.6464F, -2.3891F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -0.9458F, -12.9866F, -0.7854F, 0.0F, 0.0F));

        PartDefinition group3 = horn.addOrReplaceChild("group3", CubeListBuilder.create(), PartPose.offset(1.0F, 4.0922F, 6.8757F));

        PartDefinition cube_r5 = group3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0F, -5.3422F, 1.6243F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -2.5F, -6.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r6 = group3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(50, 64).addBox(-1.0F, -8.7703F, -0.7647F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -5.0719F, 2.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r7 = group3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(20, 53).addBox(-2.0F, -3.4142F, -2.9868F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -3.4281F, -8.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r8 = group3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 53).addBox(-2.5F, -2.6464F, -2.3891F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -0.9458F, -12.9866F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ((float) entity.getUniqueFlapTickOffset() + ageInTicks) * 7.448451F * ((float) Math.PI / 180F);
        float f1 = 16.0F;
    }
    @Override
    public ModelPart root() {
        return this.root;
    }
}