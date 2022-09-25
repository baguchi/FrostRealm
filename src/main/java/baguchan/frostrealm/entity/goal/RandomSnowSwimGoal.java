package baguchan.frostrealm.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RandomSnowSwimGoal extends RandomStrollGoal {
	public RandomSnowSwimGoal(PathfinderMob p_25753_, double p_25754_, int p_25755_) {
		super(p_25753_, p_25754_, p_25755_);
	}

	@Nullable
	protected Vec3 getPosition() {
		return DefaultRandomPos.getPos(this.mob, 12, 8);
	}

}
