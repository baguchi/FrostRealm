package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.GlacierBoarAnimations;
import baguchan.frostrealm.client.render.state.GlacierBoarRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.Set;

public class GlacierBoarModel<T extends GlacierBoarRenderState> extends EntityModel<T> {

    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(Set.of());

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart right_hind_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart hair_front;
    private final ModelPart hair_right;
    private final ModelPart hair_left;
    private final ModelPart ear_left;
    private final ModelPart ear_right;
    private final KeyframeAnimation walkAnimationState;
    private final KeyframeAnimation attackAnimationState;

    public GlacierBoarModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.right_hind_leg = root.getChild("right_hind_leg");
        this.right_front_leg = root.getChild("right_front_leg");
        this.left_front_leg = root.getChild("left_front_leg");
        this.left_hind_leg = root.getChild("left_hind_leg");
        this.head = root.getChild("head");
        this.nose = this.head.getChild("nose");
        this.hair_front = this.head.getChild("hair_front");
        this.hair_right = this.head.getChild("hair_right");
        this.hair_left = this.head.getChild("hair_left");
        this.ear_left = this.head.getChild("ear_left");
        this.ear_right = this.head.getChild("ear_right");
        this.walkAnimationState = GlacierBoarAnimations.walk.bake(root);
        this.attackAnimationState = GlacierBoarAnimations.attack.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -31.0F, -17.0F, 26.0F, 28.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(-10, 13).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 23.0F));

        PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 79).addBox(-2.0F, 0.05F, -3.0F, 6.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-3.0F, -0.95F, -3.75F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 13.0F, 14.0F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 79).addBox(-2.0F, 0.05F, -3.0F, 6.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-3.0F, -0.95F, -3.75F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 13.0F, -10.0F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 79).mirror().addBox(-2.0F, 0.05F, -3.0F, 6.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 68).mirror().addBox(-3.0F, -0.95F, -3.75F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.0F, 13.0F, -10.0F));

        PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 79).mirror().addBox(-2.0F, 0.05F, -3.0F, 6.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 68).mirror().addBox(-3.0F, -0.95F, -3.75F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.0F, 13.0F, 14.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(93, 0).addBox(-10.0F, -10.0F, -8.0F, 20.0F, 20.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -16.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -4.0F, 10.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -8.0F));

        PartDefinition horn = nose.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(1, 23).addBox(-7.0F, -6.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(5.0F, -6.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition hair_front = head.addOrReplaceChild("hair_front", CubeListBuilder.create().texOffs(148, 0).addBox(-10.0F, 0.0F, 0.0F, 20.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -8.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition hair_right = head.addOrReplaceChild("hair_right", CubeListBuilder.create().texOffs(202, -7).addBox(0.0F, 0.0F, -3.5F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -10.0F, -4.5F, 0.0F, 0.0F, 0.0436F));

        PartDefinition hair_left = head.addOrReplaceChild("hair_left", CubeListBuilder.create().texOffs(188, -7).addBox(0.0F, 0.0F, -3.5F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -10.0F, -4.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(148, 9).addBox(0.0F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -4.0F, -4.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(148, 23).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.0F, -4.5F, 0.0F, 0.0F, 0.0436F));

        return LayerDefinition.create(meshdefinition, 256, 128);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.yRot = entity.yRot * ((float) Math.PI / 180F);
        this.head.xRot = entity.xRot * ((float) Math.PI / 180F);
        this.walkAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed, 2.0F, 2.0F);
        this.attackAnimationState.apply(entity.attackAnimation, entity.ageInTicks);
    }
}