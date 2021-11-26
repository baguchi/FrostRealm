package baguchan.frostrealm.entity.goal;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ConditionGoal extends Goal {
	protected final PathfinderMob mob;
	protected int cooldown;
	protected int maxCooldown;
	private final UniformInt timeBetween;

	public ConditionGoal(PathfinderMob mob, UniformInt cooldown) {
		this.mob = mob;
		this.timeBetween = cooldown;
	}

	@Override
	public boolean canUse() {
		if (this.maxCooldown <= 0) {
			this.maxCooldown = timeBetween.sample(this.mob.getRandom());
			return false;
		} else {
			if (cooldown > this.maxCooldown) {
				this.cooldown = 0;
				this.maxCooldown = timeBetween.sample(this.mob.getRandom());
				return this.isMatchCondition();
			} else {
				++this.cooldown;
				return false;
			}
		}
	}

	private boolean isMatchCondition() {
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		return this.maxCooldown > this.cooldown;
	}

	@Override
	public void start() {
		super.start();
		this.cooldown = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.cooldown = 0;
	}
}
