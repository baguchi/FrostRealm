package baguchan.frostrealm.client.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FrostBoarModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leg_R;
    private final ModelPart leg_L;
    private final ModelPart leg_back_R;
    private final ModelPart leg_back_L;
    private final ModelPart body;

    public FrostBoarModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.leg_R = root.getChild("leg_R");
        this.leg_L = root.getChild("leg_L");
        this.leg_back_R = root.getChild("leg_back_R");
        this.leg_back_L = root.getChild("leg_back_L");
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(52, 63).addBox(-10.0F, -11.0F, -5.5F, 20.0F, 22.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(53, 92).addBox(-6.0F, -2.0F, -11.25F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.5F, -17.5F));

        PartDefinition tusk = head.addOrReplaceChild("tusk", CubeListBuilder.create().texOffs(86, 0).addBox(6.0F, -1.0F, -6.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(86, 0).addBox(-9.0F, -1.0F, -6.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -9.0F, -0.9599F, 0.0F, 0.0F));

        PartDefinition leg_R = partdefinition.addOrReplaceChild("leg_R", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.5F, 14.0F, -8.5F));

        PartDefinition leg_L = partdefinition.addOrReplaceChild("leg_L", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 14.0F, -8.5F));

        PartDefinition leg_back_R = partdefinition.addOrReplaceChild("leg_back_R", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.5F, 14.0F, 12.5F));

        PartDefinition leg_back_L = partdefinition.addOrReplaceChild("leg_back_L", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 1.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 14.0F, 12.5F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -46.0F, -16.0F, 25.0F, 28.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 33.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(12, 25).addBox(-3.0F, 0.0F, 6.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -38.0F, 19.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        this.leg_R.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leg_L.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.leg_R.zRot = 0.0F;
        this.leg_L.zRot = 0.0F;
        this.leg_back_R.xRot = Mth.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.leg_back_L.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.7F * limbSwingAmount;
        this.leg_back_R.yRot = 0.0F;
        this.leg_back_L.yRot = 0.0F;
        this.leg_back_R.zRot = 0.0F;
        this.leg_back_L.zRot = 0.0F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_R.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_L.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_back_R.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_back_L.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}