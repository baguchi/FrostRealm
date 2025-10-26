package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.FrostBoarAnimations;
import baguchan.frostrealm.client.render.state.GlacierBoarRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrostBoarModel<T extends GlacierBoarRenderState> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leg_R;
    private final ModelPart leg_L;
    private final ModelPart leg_back_R;
    private final ModelPart leg_back_L;
    private final ModelPart body;
    private final KeyframeAnimation runAnimationState;
    private final KeyframeAnimation walkAnimationState;
    private final KeyframeAnimation attackAnimationState;
    public FrostBoarModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.leg_R = this.root.getChild("leg_R");
        this.leg_L = this.root.getChild("leg_L");
        this.leg_back_R = this.root.getChild("leg_back_R");
        this.leg_back_L = this.root.getChild("leg_back_L");
        this.body = this.root.getChild("body");
        this.runAnimationState = FrostBoarAnimations.RUN.bake(root);
        this.walkAnimationState = FrostBoarAnimations.WALK.bake(root);
        this.attackAnimationState = FrostBoarAnimations.ATTACK.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.5F, -15.5F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -46.0F, -16.0F, 25.0F, 28.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 32.5F, 15.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(12, 25).addBox(-3.0F, 0.0F, 6.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -38.0F, 19.0F));

        PartDefinition leg_back_L = root.addOrReplaceChild("leg_back_L", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 13.5F, 28.0F));

        PartDefinition leg_back_R = root.addOrReplaceChild("leg_back_R", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 13.5F, 28.0F));

        PartDefinition leg_L = root.addOrReplaceChild("leg_L", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 13.5F, 7.0F));

        PartDefinition leg_R = root.addOrReplaceChild("leg_R", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 13.5F, 7.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(52, 63).addBox(-10.0F, -11.0F, -5.5F, 20.0F, 22.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(53, 92).addBox(-6.0F, -2.0F, -11.25F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tusk = head.addOrReplaceChild("tusk", CubeListBuilder.create().texOffs(86, 0).addBox(6.0F, -1.0F, -6.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(86, 0).addBox(-9.0F, -1.0F, -6.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -9.0F, -0.9599F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.yRot = entity.yRot * ((float) Math.PI / 180F);
        this.head.xRot = entity.xRot * ((float) Math.PI / 180F);

        this.runAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed * (entity.runningScale), 3.0F, 8.0F);
        this.walkAnimationState.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed * (1.0F - entity.runningScale), 1.0F, 2.0F);
        this.attackAnimationState.apply(entity.attackAnimation, entity.ageInTicks);
    }
}