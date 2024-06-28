package baguchan.frostrealm.client;

import baguchan.frostrealm.client.armpose.RubHandArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class FrostArmPoses {
	@SuppressWarnings("unused")
	public static final EnumProxy<HumanoidModel.ArmPose> RUB_HAND = new EnumProxy<>(
			HumanoidModel.ArmPose.class, true, new RubHandArmPose());
}
