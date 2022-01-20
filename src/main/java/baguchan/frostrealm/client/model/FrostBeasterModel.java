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
		modelAnimator.setAnimation(FrostBeaster.GROWL_ANIMATION);
		modelAnimator.startKeyframe(5);
		modelAnimator.move(this.head, 0.0F, 15.0F, -11.0F);
		modelAnimator.move(this.body, 0.0F, 16.25F, -6.75F);
		modelAnimator.rotate(this.body, 1.9635F, 0.0F, 0.0F);
		modelAnimator.move(this.leftArm, 0.0F, 13.0F, -6.0F);
		modelAnimator.rotate(this.leftArm, -0.7854F, 0.0F, 0.0F);
		modelAnimator.move(this.rightArm, 0.0F, 13.0F, -6.0F);
		modelAnimator.rotate(this.rightArm, -0.7854F, 0.0F, 0.0F);
		//modelAnimator.move(this.leftLeg, -2.0F, 13.0F, 4.0F);
		modelAnimator.rotate(this.leftLeg, 0.7854F, 0.0F, 0.0F);
		modelAnimator.rotate(this.leftLeg2, -1.2654F, 0.0F, 0.0F);
		//modelAnimator.move(this.rightLeg, 2.0F, 13.0F, 4.0F);
		modelAnimator.endKeyframe();
		modelAnimator.setStaticKeyframe(15);
		modelAnimator.resetKeyframe(5);

		modelAnimator.setAnimation(FrostBeaster.GROWL_ATTACK_ANIMATION);
		modelAnimator.startKeyframe(5);
		modelAnimator.move(this.head, 0.0F, 15.0F, -11.0F);
		modelAnimator.move(this.body, 0.0F, 16.25F, -6.75F);
		modelAnimator.rotate(this.body, 1.9635F, 0.0F, 0.0F);
		modelAnimator.move(this.leftArm, 0.0F, 13.0F, -6.0F);
		modelAnimator.rotate(this.leftArm, -0.7854F - 0.4F, 0.0F, 0.0F);
		modelAnimator.move(this.rightArm, 0.0F, 13.0F, -6.0F);
		modelAnimator.rotate(this.rightArm, -0.7854F - 0.4F, 0.0F, 0.0F);
		//modelAnimator.move(this.leftLeg, -2.0F, 13.0F, 4.0F);
		//modelAnimator.move(this.rightLeg, 2.0F, 13.0F, 4.0F);
		modelAnimator.endKeyframe();
		modelAnimator.startKeyframe(5);
		modelAnimator.move(this.head, 0.0F, 15.0F, -11.0F);
		modelAnimator.move(this.body, 0.0F, 16.25F, -6.75F);
		modelAnimator.rotate(this.body, 1.9635F, 0.0F, 0.0F);
		modelAnimator.move(this.leftArm, 0.0F, 13.0F, -6.0F);
		modelAnimator.rotate(this.leftArm, -0.7854F, 0.0F, 0.0F);
		modelAnimator.move(this.rightArm, 0.0F, 13.0F, -6.0F);
		modelAnimator.rotate(this.rightArm, -0.7854F, 0.0F, 0.0F);
		//modelAnimator.move(this.leftLeg, -2.0F, 13.0F, 4.0F);
		//modelAnimator.move(this.rightLeg, 2.0F, 13.0F, 4.0F);
		modelAnimator.endKeyframe();
		modelAnimator.resetKeyframe(5);
	}
}