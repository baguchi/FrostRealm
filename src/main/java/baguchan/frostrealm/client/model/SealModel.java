package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.SealAnimations;
import baguchan.frostrealm.entity.animal.Seal;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SealModel<T extends Seal> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head;

    public SealModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 0).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -6.0F));

        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 10.0F));

        PartDefinition tail = body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(92, 0).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(112, 0).mirror().addBox(1.0F, 3.0F, 6.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 0).addBox(-5.0F, 3.0F, 6.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -4.25F));

        PartDefinition right_hand = body.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(0, 24).addBox(-8.0F, -1.0F, -2.0F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -1.0F, -1.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition right_hand2 = right_hand.addOrReplaceChild("right_hand2", CubeListBuilder.create().texOffs(0, 31).addBox(-6.0F, -1.0F, 0.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition left_hand = body.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-1.0F, -1.0F, -2.0F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition left_hand2 = left_hand.addOrReplaceChild("left_hand2", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(0.0F, -1.0F, 0.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.2618F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float) Math.PI / 180F) * 0.9F;
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F) * 0.9F;
        if (entity.isBaby()) {
            this.applyStatic(SealAnimations.BABY);
        }
        if (entity.isInWater()) {
            this.animateWalk(SealAnimations.SWIM, limbSwing, limbSwingAmount, 1.0F, 1.5F);
        } else {
            this.animateWalk(SealAnimations.WALK, limbSwing, limbSwingAmount, 1.0F, 4.0F);
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}