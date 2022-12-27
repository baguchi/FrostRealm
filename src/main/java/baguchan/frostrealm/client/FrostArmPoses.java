package baguchan.frostrealm.client;

import baguchan.frostrealm.client.armpose.RubHandArmPose;
import net.minecraft.client.model.HumanoidModel;

public class FrostArmPoses {
	public static final HumanoidModel.ArmPose RUB_HAND = HumanoidModel.ArmPose.create("frostrealm:rub_hand", true, new RubHandArmPose());

	public static void init() {

	}
}
