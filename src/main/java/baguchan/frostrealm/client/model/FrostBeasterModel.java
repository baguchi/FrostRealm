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

	@Override
	public void animate(T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
		this.head.zRot = 0.0F;
		modelAnimator.update(entity);
		modelAnimator.setAnimation(FrostBeaster.NOT_BEG_ANIMATION);
		modelAnimator.startKeyframe(10);
		modelAnimator.rotate(this.head, 0.0F, 0.0F, 0.4F);
		modelAnimator.endKeyframe();
		modelAnimator.startKeyframe(60);
		modelAnimator.rotate(this.head, 0.0F, 0.0F, 0.4F);
		modelAnimator.endKeyframe();
		modelAnimator.resetKeyframe(10);
	}
}