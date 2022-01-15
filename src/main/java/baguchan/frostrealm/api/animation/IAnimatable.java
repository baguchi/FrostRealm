package baguchan.frostrealm.api.animation;

import net.minecraft.world.entity.Entity;

public interface IAnimatable {
	Animation NO_ANIMATION = Animation.create(0);

	int getAnimationTick();

	void setAnimationTick(int tick);

	Animation getAnimation();

	Animation[] getAnimations();

	void setAnimation(Animation animation);

	default <T extends Entity & IAnimatable> void updateAnimations(T entity) {
		if (entity.getAnimation() == null) {
			entity.setAnimation(IAnimatable.NO_ANIMATION);
		} else {
			if (entity.getAnimation() != IAnimatable.NO_ANIMATION) {
				if (entity.getAnimationTick() < entity.getAnimation().getDuration()) {
					entity.setAnimationTick(entity.getAnimationTick() + 1);
				}
				if (entity.getAnimationTick() == entity.getAnimation().getDuration()) {
					entity.setAnimationTick(0);
					entity.setAnimation(IAnimatable.NO_ANIMATION);
				}
			}
		}
	}
}
