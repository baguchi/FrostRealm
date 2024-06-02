package baguchan.frostrealm.client.model;// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.client.animation.MindVineAnimations;
import baguchan.frostrealm.entity.hostile.MindVine;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MindVineModel<T extends MindVine> extends HierarchicalModel<T> {
    private final ModelPart root;

    public MindVineModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -15.0F, -4.0F, 8.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition root2 = root.addOrReplaceChild("root2", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition root3 = root2.addOrReplaceChild("root3", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.attackAnimationState, MindVineAnimations.ATTACK, ageInTicks);

        this.animate(entity.summonAnimationState, MindVineAnimations.SUMMONED, ageInTicks);
        this.animate(entity.deathAnimationState, MindVineAnimations.DEATH, ageInTicks);

    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}