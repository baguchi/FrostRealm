package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.frostrealm.entity.ShadeInsect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ShadeInsectModel<T extends ShadeInsect> extends EntityModel<T> {
    private final ModelPart core;
    private final ModelPart body;
    private final ModelPart leftWingBase;
    private final ModelPart leftWing;
    private final ModelPart rightWingBase;
    private final ModelPart rightWing;

    public ShadeInsectModel(ModelPart root) {
        this.core = root.getChild("core");
        this.body = this.core.getChild("body");
        this.leftWingBase = this.body.getChild("wingL");
        this.rightWingBase = this.body.getChild("wingR");
        this.leftWing = this.leftWingBase.getChild("wingL2");
        this.rightWing = this.rightWingBase.getChild("wingR2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -4.0F, 0.0F, 20.0F, 9.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -5.0F));

        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 28).addBox(-7.0F, -4.0F, 0.0F, 14.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create().texOffs(68, 31).addBox(-9.0F, -2.0F, -3.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.5F, -1.0F, 3.0F));

        PartDefinition wingR2 = wingR.addOrReplaceChild("wingR2", CubeListBuilder.create().texOffs(68, 15).addBox(-16.0F, -2.0F, -3.0F, 16.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 0.0F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create().texOffs(68, 31).mirror().addBox(0.0F, -2.0F, -3.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.5F, -1.0F, 3.0F));

        PartDefinition wingL2 = wingL.addOrReplaceChild("wingL2", CubeListBuilder.create().texOffs(68, 15).mirror().addBox(0.0F, -2.0F, -3.0F, 16.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, 0.0F, 0.0F));

        PartDefinition head = core.addOrReplaceChild("head", CubeListBuilder.create().texOffs(59, 0).addBox(-7.0F, -3.0F, -8.0F, 13.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ((float) entity.getUniqueFlapTickOffset() + ageInTicks) * 7.448451F * ((float) Math.PI / 180F);
        float f1 = 16.0F;
        this.leftWingBase.zRot = -Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
        this.leftWing.zRot = -Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
        this.rightWingBase.zRot = -this.leftWingBase.zRot;
        this.rightWing.zRot = -this.leftWing.zRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        core.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}