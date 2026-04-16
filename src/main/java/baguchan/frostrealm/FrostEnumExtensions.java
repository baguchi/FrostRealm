package baguchan.frostrealm;

import baguchan.frostrealm.client.animation.SickleAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

@SuppressWarnings("unused")
public class FrostEnumExtensions {
    public static final EnumProxy<HumanoidModel.ArmPose> SICKLE_ARM_POSE = new EnumProxy<>(HumanoidModel.ArmPose.class, true, true, (IArmPoseTransformer) SickleAnimation::thirdPersonAttackHand);
}
