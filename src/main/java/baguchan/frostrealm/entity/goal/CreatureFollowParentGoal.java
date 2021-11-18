package baguchan.frostrealm.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class CreatureFollowParentGoal extends Goal {
	public static final int HORIZONTAL_SCAN_RANGE = 8;
	public static final int VERTICAL_SCAN_RANGE = 4;
	public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
	private final PathfinderMob animal;
	private PathfinderMob parent;
	private final double speedModifier;
	private int timeToRecalcPath;

	public CreatureFollowParentGoal(PathfinderMob p_25319_, double p_25320_) {
		this.animal = p_25319_;
		this.speedModifier = p_25320_;
	}

	public boolean canUse() {
		if (!this.animal.isBaby()) {
			return false;
		} else {
			List<? extends PathfinderMob> list = this.animal.level.getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
			PathfinderMob animal = null;
			double d0 = Double.MAX_VALUE;

			for (PathfinderMob animal1 : list) {
				if (!animal1.isBaby()) {
					double d1 = this.animal.distanceToSqr(animal1);
					if (!(d1 > d0)) {
						d0 = d1;
						animal = animal1;
					}
				}
			}

			if (animal == null) {
				return false;
			} else if (d0 < 9.0D) {
				return false;
			} else {
				this.parent = animal;
				return true;
			}
		}
	}

	public boolean canContinueToUse() {
		if (!this.animal.isBaby()) {
			return false;
		} else if (!this.parent.isAlive()) {
			return false;
		} else {
			double d0 = this.animal.distanceToSqr(this.parent);
			return !(d0 < 12.0D) && !(d0 > 256.0D);
		}
	}

	public void start() {
		this.timeToRecalcPath = 0;
	}

	public void stop() {
		this.parent = null;
	}

	public void tick() {
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = 10;
			this.animal.getNavigation().moveTo(this.parent, this.speedModifier);
		}
	}
}