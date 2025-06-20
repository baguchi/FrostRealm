package baguchan.frostrealm.client.model;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.SealAnimations;
import baguchan.frostrealm.client.render.state.SealRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SealModel<T extends SealRenderState> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final KeyframeAnimation babyAnimationState;
    private final KeyframeAnimation swimAnimationState;
    private final KeyframeAnimation walkAnimationState;

    public SealModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.head = this.root.getChild("body").getChild("head");
        this.babyAnimationState = SealAnimations.BABY.bake(root);
        this.swimAnimationState = SealAnimations.SWIM.bake(root);
        this.walkAnimationState = SealAnimations.WALK.bake(root);
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
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.yRot = entity.yRot * ((float) Math.PI / 180F);
        this.head.xRot = entity.xRot * ((float) Math.PI / 180F);

        if (entity.isBaby) {
            this.babyAnimationState.applyStatic();
        }
        if (entity.isInWater) {
            this.swimAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed, 1.0F, 1.5F);
        } else {
            this.walkAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed, 1.0F, 4.0F);
        }
    }
}