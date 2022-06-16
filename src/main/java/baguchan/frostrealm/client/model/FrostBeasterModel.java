package baguchan.frostrealm.client.model;

import baguchan.frostrealm.entity.FrostBeaster;
import net.minecraft.client.model.geom.ModelPart;

public class FrostBeasterModel<T extends FrostBeaster> extends WolfesterModel<T> {

	public FrostBeasterModel(ModelPart root) {
		super(root);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}