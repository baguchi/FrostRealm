package baguchan.frostrealm.entity.goal;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RandomFlyingAndHoverGoal extends RandomStrollGoal {
	public RandomFlyingAndHoverGoal(PathfinderMob p_25981_, double p_25982_) {
		super(p_25981_, p_25982_, 10);
	}

	@Nullable
	protected Vec3 getPosition() {
		Vec3 vec3 = this.mob.getViewVector(0.0F);
		int i = 8;

		RandomSource randomsource = this.mob.getRandom();
		double d0 = this.mob.getX() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
		double d1 = this.mob.getY() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
		double d2 = this.mob.getZ() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);

		if (this.mob.getY() < 80) {
			d1 = this.mob.getY() + (double) ((randomsource.nextFloat() * 1.2F) * 16.0F);
		}

		return new Vec3(d0, d1, d2);
	}
}