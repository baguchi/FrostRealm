package baguchan.frostrealm.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RaftSledgeModel extends ListModel<Boat> {
    private static final String LEFT_PADDLE = "left_paddle";
    private static final String RIGHT_PADDLE = "right_paddle";
    private static final String BOTTOM = "bottom";
    private final ImmutableList<ModelPart> parts;

    public RaftSledgeModel(ModelPart p_251383_) {
        this.parts = this.createPartsBuilder(p_251383_).build();
    }

    protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart p_250773_) {
        ImmutableList.Builder<ModelPart> builder = new ImmutableList.Builder<>();
        builder.add(p_250773_.getChild("bottom"));
        return builder;
    }

    public static void createChildren(PartDefinition p_250262_) {
        p_250262_.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -11.0F, -4.0F, 28.0F, 20.0F, 4.0F).texOffs(0, 0).addBox(-14.0F, -9.0F, -8.0F, 28.0F, 16.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, 1.5708F, 0.0F, 0.0F));
        int i = 20;
        int j = 7;
        int k = 6;
        float f = -5.0F;
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        createChildren(partdefinition);
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public void setupAnim(Boat p_249733_, float p_249202_, float p_252219_, float p_249366_, float p_249759_, float p_250286_) {
    }

    public ImmutableList<ModelPart> parts() {
        return this.parts;
    }
}