package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.Gokkur;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class StunGoal extends Goal {
	protected final Gokkur mob;
	protected int tick;

	public StunGoal(Gokkur mob) {
		this.mob = mob;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		if (this.mob.isStun()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canContinueToUse() {
		return 100 >= this.tick;
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
		this.mob.setStun(false);
	}
}