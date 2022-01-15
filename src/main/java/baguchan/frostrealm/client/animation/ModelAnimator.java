package baguchan.frostrealm.client.animation;

import baguchan.frostrealm.api.animation.Animation;
import baguchan.frostrealm.api.animation.IAnimatable;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ModelAnimator {
	private int tempTick;
	private int prevTempTick;

	private IAnimatable entity;
	private HashMap<ModelPart, ModelPartPos> transformMap;
	private HashMap<ModelPart, ModelPartPos> prevModelPartPosMap;
	private Animation animation;
	private boolean correctAnimation;

	public ModelAnimator() {
		this.correctAnimation = false;
		this.tempTick = 0;
		this.transformMap = new HashMap<>();
		this.prevModelPartPosMap = new HashMap<>();
	}

	public static ModelAnimator create() {
		return new ModelAnimator();
	}

	public IAnimatable getEntity() {
		return entity;
	}

	public void update(IAnimatable animatable) {
		this.correctAnimation = false;
		this.tempTick = this.prevTempTick = 0;
		this.entity = animatable;
		this.transformMap.clear();
		this.prevModelPartPosMap.clear();
	}

	public boolean setAnimation(Animation animation) {
		this.tempTick = this.prevTempTick = 0;
		this.correctAnimation = this.entity.getAnimation() == animation;
		return this.correctAnimation;
	}

	public void startKeyframe(int duration) {
		if (!this.correctAnimation) {
			return;
		}
		this.prevTempTick = this.tempTick;
		this.tempTick += duration;
	}

	/**
	 * Add a static keyframe with a specific duration to the model.
	 *
	 * @param duration the keyframe duration
	 */
	public void setStaticKeyframe(int duration) {
		this.startKeyframe(duration);
		this.endKeyframe(true);
	}

	/**
	 * Reset this keyframe to its original state
	 *
	 * @param duration the keyframe duration
	 */
	public void resetKeyframe(int duration) {
		this.startKeyframe(duration);
		this.endKeyframe();
	}

	/**
	 * Rotate a box in the current keyframe. All the values are relative.
	 *
	 * @param box the box to rotate
	 * @param x   the x rotation
	 * @param y   the y rotation
	 * @param z   the z rotation
	 */
	public void rotate(ModelPart box, float x, float y, float z) {
		if (!this.correctAnimation) {
			return;
		}
		this.getModelPartPos(box).addRotation(new Vector3f(x, y, z));
	}

	/**
	 * Move a box in the current keyframe. All the values are relative.
	 *
	 * @param box the box to move
	 * @param x   the x offset
	 * @param y   the y offset
	 * @param z   the z offset
	 */
	public void move(ModelPart box, float x, float y, float z) {
		if (!this.correctAnimation) {
			return;
		}
		this.getModelPartPos(box).addOffsetRotation(new Vector3f(x, y, z));
	}

	private ModelPartPos getModelPartPos(ModelPart box) {
		return this.transformMap.computeIfAbsent(box, b -> new ModelPartPos());
	}

	/**
	 * End the current keyframe. this will reset all box transformations to their original state.
	 */
	public void endKeyframe() {
		this.endKeyframe(false);
	}

	private void endKeyframe(boolean stationary) {
		if (!this.correctAnimation) {
			return;
		}
		int animationTick = this.entity.getAnimationTick();

		if (animationTick >= this.prevTempTick && animationTick < this.tempTick) {
			if (stationary) {
				for (ModelPart box : this.prevModelPartPosMap.keySet()) {
					ModelPartPos transform = this.prevModelPartPosMap.get(box);
					box.xRot += transform.getRotation().x();
					box.yRot += transform.getRotation().y();
					box.zRot += transform.getRotation().z();
					box.x += transform.getOffsetRotation().x();
					box.y += transform.getOffsetRotation().y();
					box.z += transform.getOffsetRotation().z();
				}
			} else {
				float tick = (animationTick - this.prevTempTick + Minecraft.getInstance().getFrameTime()) / (this.tempTick - this.prevTempTick);
				float inc = Mth.sin((float) (tick * Math.PI / 2.0F)), dec = 1.0F - inc;
				for (ModelPart box : this.prevModelPartPosMap.keySet()) {
					ModelPartPos transform = this.prevModelPartPosMap.get(box);
					box.xRot += dec * transform.getRotation().x();
					box.yRot += dec * transform.getRotation().y();
					box.zRot += dec * transform.getRotation().z();
					box.x += dec * transform.getOffsetRotation().x();
					box.y += dec * transform.getOffsetRotation().y();
					box.z += dec * transform.getOffsetRotation().z();
				}
				for (ModelPart box : this.transformMap.keySet()) {
					ModelPartPos transform = this.transformMap.get(box);
					box.xRot += inc * transform.getRotation().x();
					box.yRot += inc * transform.getRotation().y();
					box.zRot += inc * transform.getRotation().z();
					box.x += inc * transform.getOffsetRotation().x();
					box.y += inc * transform.getOffsetRotation().y();
					box.z += inc * transform.getOffsetRotation().z();
				}
			}
		}

		if (!stationary) {
			this.prevModelPartPosMap.clear();
			this.prevModelPartPosMap.putAll(this.transformMap);
			this.transformMap.clear();
		}
	}
}
