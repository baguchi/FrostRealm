package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.entity.IWarming;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class WarmingGoal<T extends PathfinderMob & IWarming> extends Goal {
	private final T mob;

	public WarmingGoal(T p_i50572_2_) {
		this.mob = p_i50572_2_;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	public boolean canUse() {
		if (!this.mob.isWarming()) {
			if (FrostLivingCapability.get(this.mob).isColdBody()) {
				return true;
			}
		}

		return false;

	}

	@Override
	public void start() {
		super.start();
		this.mob.setWarming(true);
	}

	@Override
	public void stop() {
		super.stop();
		this.mob.setWarming(false);
	}

	public void tick() {
		if (this.mob.tickCount % 20 == 0) {
			FrostLivingCapability cap = FrostLivingCapability.get(this.mob);
			cap.setTemperatureLevel(cap.getTemperatureLevel() + 1);
			cap.setSaturation(cap.getSaturationLevel() + 0.1F);
		}
	}
}
