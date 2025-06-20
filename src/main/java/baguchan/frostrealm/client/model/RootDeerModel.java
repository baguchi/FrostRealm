package baguchan.frostrealm.client.model;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.RootDeerAnimations;
import baguchan.frostrealm.client.render.state.RootDeerRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class RootDeerModel<T extends RootDeerRenderState> extends EntityModel<T> {
    private final ModelPart all;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart above_teeth;
    private final ModelPart under_teeth;
    private final ModelPart joint;
    private final ModelPart corner;
    private final KeyframeAnimation attackAnimationState;
    private final KeyframeAnimation summonAnimationState;
    private final KeyframeAnimation deathAnimationState;

    public RootDeerModel(ModelPart root) {
        super(root);
        this.all = root.getChild("all");
        this.body = this.all.getChild("body");
        this.head = this.all.getChild("head");
        this.above_teeth = this.head.getChild("above_teeth");
        this.corner = this.above_teeth.getChild("corner");
        this.under_teeth = this.head.getChild("under_teeth");
        this.joint = this.under_teeth.getChild("joint");
        this.attackAnimationState = RootDeerAnimations.attack.bake(root);
        this.summonAnimationState = RootDeerAnimations.spawn.bake(root);
        this.deathAnimationState = RootDeerAnimations.death.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 14.0F));

        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-5.0F, -12.0F, -1.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -11.0F, -11.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 18).addBox(-3.0F, -10.0F, -20.0F, 6.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-2.0F, -9.0F, -28.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = all.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition above_teeth = head.addOrReplaceChild("above_teeth", CubeListBuilder.create().texOffs(36, 14).addBox(-3.0F, 0.0F, -11.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(44, 56).addBox(-3.0F, 0.0F, -11.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(34, 54).addBox(3.0F, 0.0F, -11.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-3.0F, -6.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 46).addBox(-3.0F, -3.0F, -11.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -28.0F));

        PartDefinition corner = above_teeth.addOrReplaceChild("corner", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -2.0F));

        PartDefinition corner_r1 = corner.addOrReplaceChild("corner_r1", CubeListBuilder.create().texOffs(32, 34).addBox(-8.0F, -12.0F, -1.0F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition under_teeth = head.addOrReplaceChild("under_teeth", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -28.0F));

        PartDefinition head_r1 = under_teeth.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -2.0F, -8.0F, 6.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(56, 46).addBox(-3.0F, -4.0F, -8.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(56, 52).addBox(3.0F, -4.0F, -8.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(-3.0F, -4.0F, -8.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition joint = under_teeth.addOrReplaceChild("joint", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition joint_r1 = joint.addOrReplaceChild("joint_r1", CubeListBuilder.create().texOffs(46, 46).addBox(3.0F, -7.0F, -3.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(24, 54).addBox(8.0F, -7.0F, -3.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T p_364104_) {
        super.setupAnim(p_364104_);
        this.attackAnimationState.apply(p_364104_.attackAnimationState, p_364104_.ageInTicks);
        this.summonAnimationState.apply(p_364104_.summonAnimationState, p_364104_.ageInTicks);
        this.deathAnimationState.apply(p_364104_.deathAnimationState, p_364104_.ageInTicks);
    }
}