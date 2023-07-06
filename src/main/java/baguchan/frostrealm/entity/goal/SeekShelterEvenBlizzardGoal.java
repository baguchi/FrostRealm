package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.capability.FrostWeatherCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class SeekShelterEvenBlizzardGoal extends FleeSunGoal {
	private int interval = reducedTickDelay(60);
	private final boolean seekDays;

	public SeekShelterEvenBlizzardGoal(PathfinderMob mob, double p_28777_) {
		this(mob, p_28777_, false);
	}

	public SeekShelterEvenBlizzardGoal(PathfinderMob mob, double p_28777_, boolean seekDays) {
		super(mob, p_28777_);
		this.seekDays = seekDays;
	}

	public boolean canUse() {
		if (!this.mob.isSleeping() && this.mob.getTarget() == null) {
			if (this.mob.level().isThundering()) {
				return true;
			} else if (this.interval > 0) {
				--this.interval;
				return false;
			} else {
				this.interval = 60;
				BlockPos blockpos = this.mob.blockPosition();
				return (this.seekDays && this.mob.level().isDay() || FrostWeatherCapability.isBadWeatherActive(this.mob.level())) && this.mob.level().canSeeSky(blockpos) && this.setWantedPos();
			}
		} else {
			return false;
		}
	}

	public void start() {
		super.start();
	}
}