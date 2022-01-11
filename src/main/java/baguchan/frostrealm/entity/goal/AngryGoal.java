package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.SnowPileQuail;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class AngryGoal extends Goal {
	private final SnowPileQuail mob;

	public AngryGoal(SnowPileQuail snowPileQuail) {
		this.mob = snowPileQuail;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.mob.isAngry() && this.mob.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return this.mob.isAngry() && this.mob.getTarget() != null && this.mob.hasLineOfSight(this.mob.getTarget()) && this.mob.distanceToSqr(this.mob.getTarget()) <= 32D;
	}

	@Override
	public void stop() {
		super.stop();
		this.mob.setAngry(false);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.mob.getTarget() != null) {
			this.mob.getLookControl().setLookAt(this.mob.getTarget(), 20F, 20F);

			if (!(this.mob.distanceToSqr(this.mob.getTarget()) <= 8D)) {
				this.mob.getNavigation().moveTo(this.mob.getTarget(), 0.6F);
			} else {
				this.mob.getNavigation().stop();
			}
		}
	}
}
