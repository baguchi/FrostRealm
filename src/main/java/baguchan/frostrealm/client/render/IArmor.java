package baguchan.frostrealm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;

public interface IArmor {

    void translateToHead(PoseStack poseStack);

    void translateToChest(PoseStack poseStack);

    void translateToChestPat(HumanoidArm arm, PoseStack poseStack);

    ModelPart rightHand();

    ModelPart leftHand();

    ModelPart rightLeg();

    ModelPart leftLeg();
}
