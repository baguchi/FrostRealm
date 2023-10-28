package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.FrostormDragonAnimations;
import baguchan.frostrealm.entity.FrostormDragon;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

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

        PartDefinition tail13 = partdefinition.addOrReplaceChild("tail13", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -64.0F));

        PartDefinition tail14 = tail13.addOrReplaceChild("tail14", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail15 = tail14.addOrReplaceChild("tail15", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail16 = tail15.addOrReplaceChild("tail16", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail17 = tail16.addOrReplaceChild("tail17", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail18 = tail17.addOrReplaceChild("tail18", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail19 = tail18.addOrReplaceChild("tail19", CubeListBuilder.create().texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail20 = tail19.addOrReplaceChild("tail20", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail21 = tail20.addOrReplaceChild("tail21", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail22 = tail21.addOrReplaceChild("tail22", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail23 = tail22.addOrReplaceChild("tail23", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail24 = tail23.addOrReplaceChild("tail24", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail1 = tail24.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail8 = tail7.addOrReplaceChild("tail8", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail9 = tail8.addOrReplaceChild("tail9", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail10 = tail9.addOrReplaceChild("tail10", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail11 = tail10.addOrReplaceChild("tail11", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail12 = tail11.addOrReplaceChild("tail12", CubeListBuilder.create().texOffs(76, 21).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(76, 42).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition back_right_leg = tail24.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(192, 0).addBox(-5.0F, -4.0F, -8.0F, 13.0F, 28.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, 1.0F, 4.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition back_right_shin = back_right_leg.addOrReplaceChild("back_right_shin", CubeListBuilder.create().texOffs(211, 51).addBox(-2.0F, 0.0F, -3.0F, 8.0F, 32.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.0F, 7.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition back_right_foot = back_right_shin.addOrReplaceChild("back_right_foot", CubeListBuilder.create().texOffs(172, 92).addBox(-3.0F, 0.0F, -20.0F, 12.0F, 10.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.0F, 4.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition back_left_leg = tail24.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(192, 0).addBox(-8.0F, -4.0F, -8.0F, 13.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 1.0F, 4.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition back_left_shin = back_left_leg.addOrReplaceChild("back_left_shin", CubeListBuilder.create().texOffs(208, 48).addBox(-6.0F, 0.0F, -6.0F, 8.0F, 32.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.0F, 7.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition back_left_foot = back_left_shin.addOrReplaceChild("back_left_foot", CubeListBuilder.create().texOffs(172, 92).addBox(-9.0F, 0.0F, -20.0F, 12.0F, 10.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.0F, 4.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition front_left_leg = tail18.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(200, 126).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 2.0F, 8.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition front_left_shin = front_left_leg.addOrReplaceChild("front_left_shin", CubeListBuilder.create().texOffs(232, 126).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition front_left_foot = front_left_shin.addOrReplaceChild("front_left_foot", CubeListBuilder.create().texOffs(208, 159).addBox(-3.0F, 0.0F, -13.0F, 8.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 20.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition front_right_leg = tail18.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(200, 126).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 2.0F, 8.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition front_right_shin = front_right_leg.addOrReplaceChild("front_right_shin", CubeListBuilder.create().texOffs(232, 126).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition front_right_foot = front_right_shin.addOrReplaceChild("front_right_foot", CubeListBuilder.create().texOffs(208, 159).addBox(-4.0F, 0.0F, -13.0F, 8.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition head2 = partdefinition.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, -1.0F, -30.0F, 12.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(76, 41).addBox(3.0F, -12.0F, -10.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(13, 74).addBox(3.0F, -3.0F, -28.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, -64.0F));

        PartDefinition mirror = head2.addOrReplaceChild("mirror", CubeListBuilder.create().texOffs(76, 41).addBox(-5.0F, -18.0F, -68.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-5.0F, -9.0F, -86.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 58.0F));

        PartDefinition jaw2 = head2.addOrReplaceChild("jaw2", CubeListBuilder.create().texOffs(64, 0).addBox(-7.0F, 0.0F, -17.0F, 14.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -14.0F));

        PartDefinition horn = head2.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(-8.0F, -11.0F, 6.5F));

        PartDefinition group = horn.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(2.0F, 4.0922F, 6.8757F));

        PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0F, -5.3422F, 1.6243F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -2.5F, -6.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = group.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 64).addBox(-1.0F, -8.7703F, -0.7647F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -5.0719F, 2.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r3 = group.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 53).addBox(-2.0F, -3.4142F, -2.9868F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -3.4281F, -8.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r4 = group.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(56, 32).addBox(-2.5F, -2.6464F, -2.3891F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -0.9458F, -12.9866F, -0.7854F, 0.0F, 0.0F));

        PartDefinition group3 = horn.addOrReplaceChild("group3", CubeListBuilder.create(), PartPose.offset(1.0F, 4.0922F, 6.8757F));

        PartDefinition cube_r5 = group3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0F, -5.3422F, 1.6243F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -2.5F, -6.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r6 = group3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(66, 64).addBox(-1.0F, -8.7703F, -0.7647F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -5.0719F, 2.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r7 = group3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 53).addBox(-2.0F, -3.4142F, -2.9868F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -3.4281F, -8.389F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r8 = group3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(56, 32).addBox(-2.5F, -2.6464F, -2.3891F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -0.9458F, -12.9866F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isFlying()) {
            this.head2.yRot = netHeadYaw * ((float) Math.PI / 180F);
            this.head2.xRot = headPitch * ((float) Math.PI / 180F);
        }
        float f = (ageInTicks) * 1.448451F * ((float) Math.PI / 180F);
        if (entity.isFlying()) {
            this.applyStatic(FrostormDragonAnimations.FLIGHT);

            this.getAnyDescendantWithName("tail13").get().xRot = -Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail14").get().xRot = -Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail15").get().xRot = -Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail16").get().xRot = -Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail17").get().xRot = Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail18").get().xRot = Mth.cos(f) * 24F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail19").get().xRot = Mth.cos(f) * 24F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail20").get().xRot = Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail21").get().xRot = -Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail22").get().xRot = -Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail23").get().xRot = -Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail24").get().xRot = -Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);

            this.getAnyDescendantWithName("tail1").get().xRot = Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail2").get().xRot = Mth.cos(f) * 24F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail3").get().xRot = Mth.cos(f) * 24F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail4").get().xRot = Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail5").get().xRot = -Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail6").get().xRot = -Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail7").get().xRot = -Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail8").get().xRot = -Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail9").get().xRot = Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail10").get().xRot = Mth.cos(f) * 24F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail11").get().xRot = Mth.cos(f) * 24F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail12").get().xRot = Mth.cos(f) * 8F * ((float) Math.PI / 180F);

        } else {
            this.getAnyDescendantWithName("tail1").get().xRot = Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail2").get().xRot = Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail3").get().xRot = Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail4").get().xRot = Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail5").get().xRot = -Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail6").get().xRot = -Mth.cos(f) * 12F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail7").get().xRot = -Mth.cos(f) * 12F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail8").get().xRot = -Mth.cos(f) * 8F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail9").get().xRot = Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail10").get().xRot = Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail11").get().xRot = Mth.cos(f) * 12.0F * ((float) Math.PI / 180F);
            this.getAnyDescendantWithName("tail12").get().xRot = Mth.cos(f) * 8.0F * ((float) Math.PI / 180F);

            this.animateWalk(FrostormDragonAnimations.WALKING, limbSwing, limbSwingAmount, 0.75F, 2.5F);
        }
    }
    @Override
    public ModelPart root() {
        return this.root;
    }
}