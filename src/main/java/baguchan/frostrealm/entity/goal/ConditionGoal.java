package baguchan.frostrealm.entity.goal;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ConditionGoal extends Goal {
	protected final PathfinderMob mob;
	protected int tick;
	private int cooldown;
	private int maxCooldown;
	private int maxActiveTime;
	private final UniformInt timeBetweenCooldown;
	private final UniformInt timeBetween;

	public ConditionGoal(PathfinderMob mob, UniformInt cooldown) {
		this(mob, cooldown, cooldown);
	}

	public ConditionGoal(PathfinderMob mob, UniformInt cooldown, UniformInt time) {
		this.mob = mob;
		this.timeBetweenCooldown = cooldown;
		this.timeBetween = time;
	}

	@Override
	public boolean canUse() {
		if (this.maxCooldown <= 0) {
			this.maxCooldown = timeBetweenCooldown.sample(this.mob.getRandom());
			return false;
		} else {
			if (cooldown > this.maxCooldown) {
				this.cooldown = 0;
				this.maxCooldown = timeBetweenCooldown.sample(this.mob.getRandom());
				this.maxActiveTime = timeBetween.sample(this.mob.getRandom());
				return this.isMatchCondition();
			} else {
				++this.cooldown;
				return false;
			}
		}
	}

	public boolean isMatchCondition() {
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		return this.maxActiveTime >= this.tick;
	}

	@Override
	public void tick() {
		super.tick();
		++this.tick;
	}

	@Override
	public void start() {
		super.start();
		this.tick = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.tick = 0;
	}
}
