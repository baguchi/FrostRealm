package baguchan.frostrealm.client.model;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.FrostCrawlerAnimations;
import baguchan.frostrealm.client.render.state.FrostCrawlerRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrostCrawlerModel<T extends FrostCrawlerRenderState> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart head;
    private final ModelPart jaw;

    public FrostCrawlerModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.left_leg = this.root.getChild("left_leg");
        this.right_leg = this.root.getChild("right_leg");
        this.left_arm = this.root.getChild("left_arm");
        this.right_arm = this.root.getChild("right_arm");
        this.head = this.root.getChild("head");
        this.jaw = this.head.getChild("jaw");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(58, 0).addBox(-11.0F, -13.0F, -8.0F, 22.0F, 24.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -31.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(82, 38).mirror().addBox(-2.5F, -3.0F, -3.0F, 8.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(108, 37).mirror().addBox(-2.0F, 6.0F, -3.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -17.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(82, 38).addBox(-5.5F, -3.0F, -3.0F, 8.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(108, 37).addBox(-3.0F, 6.0F, -3.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -17.0F, 0.0F));

        PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -0.5F, -3.0F, 6.0F, 26.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -42.0F, -1.0F));

        PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, -0.5F, -4.0F, 6.0F, 26.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -42.0F, -1.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -9.5F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(34, 9).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -43.0F, -4.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, 1.0F, -7.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, -2.0F, -2.5F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        if (!entity.jumpAnimationState.isStarted()) {
            if (entity.attackAnimationState.isStarted()) {
                this.animateWalk(FrostCrawlerAnimations.walk_without_hand, entity.walkAnimationPos, entity.walkAnimationSpeed, 2.0F, 2.5F);
            } else {
                this.animateWalk(FrostCrawlerAnimations.walk, entity.walkAnimationPos, entity.walkAnimationSpeed, 2.0F, 2.5F);
            }
        }
        this.animate(entity.attackAnimationState, FrostCrawlerAnimations.attack, entity.ageInTicks);
        this.animate(entity.jumpAnimationState, FrostCrawlerAnimations.jump, entity.ageInTicks);
    }
}