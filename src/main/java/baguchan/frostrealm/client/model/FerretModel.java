package baguchan.frostrealm.client.model;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.BabyAnimations;
import baguchan.frostrealm.client.animation.FerretAnimations;
import baguchan.frostrealm.entity.animal.Ferret;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FerretModel<T extends Ferret> extends AgeableHierarchicalModel<T> {
    private final ModelPart realroot;
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart body2;
    private final ModelPart back_leg_r;
    private final ModelPart back_leg_l;
    private final ModelPart tail;
    private final ModelPart front_leg_r;
    private final ModelPart front_leg_l;
    private final ModelPart head;
    private final ModelPart mouth;
    private final ModelPart ear_r;
    private final ModelPart ear_l;

    public FerretModel(ModelPart root) {
        super(0.5F, 24.0F);
        this.realroot = root;
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.body2 = this.body.getChild("body2");
        this.back_leg_r = this.body2.getChild("back_leg_r");
        this.back_leg_l = this.body2.getChild("back_leg_l");
        this.tail = this.body2.getChild("tail");
        this.front_leg_r = this.body.getChild("front_leg_r");
        this.front_leg_l = this.body.getChild("front_leg_l");
        this.head = this.root.getChild("head");
        this.mouth = this.head.getChild("mouth");
        this.ear_r = this.head.getChild("ear_r");
        this.ear_l = this.head.getChild("ear_l");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -7.0F));

        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 9.0F));

        PartDefinition back_leg_r = body2.addOrReplaceChild("back_leg_r", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.0F, 6.0F));

        PartDefinition back_leg_l = body2.addOrReplaceChild("back_leg_l", CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 2.0F, 6.0F));

        PartDefinition tail = body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 10.0F));

        PartDefinition front_leg_r = body.addOrReplaceChild("front_leg_r", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.0F, 2.0F));

        PartDefinition front_leg_l = body.addOrReplaceChild("front_leg_l", CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 2.0F, 2.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -7.0F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(17, 11).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition ear_r = head.addOrReplaceChild("ear_r", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -2.0F, -2.0F));

        PartDefinition ear_l = head.addOrReplaceChild("ear_l", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -2.0F, -2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        float f = ageInTicks - (float) entity.tickCount;

        if (entity.isInSittingPose()) {
            this.applyStatic(FerretAnimations.sit);

        } else {
            this.animateWalk(FerretAnimations.run, limbSwing, limbSwingAmount * (entity.getRunningScale(f)), 1.0F, 2.5F);
            this.animateWalk(FerretAnimations.walk, limbSwing, limbSwingAmount * (1.0F - entity.getRunningScale(f)), 1.0F, 5.0F);
        }

        if (this.young) {
            this.applyStatic(BabyAnimations.baby);
        }
    }

    @Override
    public ModelPart root() {
        return this.realroot;
    }
}