package baguchan.frostrealm.client.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.VenochemAnimation;
import baguchan.frostrealm.entity.hostile.Venochem;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class VenochemModel<T extends Venochem> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leg;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart leg5;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart tail2;

    public VenochemModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.leg = this.body.getChild("leg");
        this.leg0 = this.leg.getChild("leg0");
        this.leg1 = this.leg.getChild("leg1");
        this.leg2 = this.leg.getChild("leg2");
        this.leg3 = this.leg.getChild("leg3");
        this.leg4 = this.leg.getChild("leg4");
        this.leg5 = this.leg.getChild("leg5");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
        this.tail2 = this.tail.getChild("tail2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(37, 2).addBox(-5.0F, -7.0F, -7.0F, 10.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 2.0F));

        PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offset(4.0F, -6.0F, 0.0F));

        PartDefinition leg0 = leg.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-8.0F, 0.25F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 3.5F, 4.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition leg1 = leg.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, 0.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.75F, 4.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition leg2 = leg.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-9.0F, -0.75F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 4.0F, 0.5F, 0.0F, 0.0F, -0.48F));

        PartDefinition leg3 = leg.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.75F, -0.5F, 0.0F, 0.0F, 0.48F));

        PartDefinition leg4 = leg.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-9.0F, -0.75F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 4.0F, -4.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition leg5 = leg.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -1.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.75F, -4.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -6.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -7.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(69, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 4.0F, 0.829F, 0.0F, 0.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(83, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(82, 16).addBox(-4.0F, -4.0F, 8.0F, 8.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(Venochem entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        this.animate(entity.attackAnimationState, VenochemAnimation.attack, ageInTicks);
        this.animate(entity.shootAnimationState, VenochemAnimation.spit, ageInTicks);

        this.animateWalk(VenochemAnimation.walk, limbSwing, limbSwingAmount, 4.0F, 2.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}