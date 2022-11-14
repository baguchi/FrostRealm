package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.IChargeMob;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

import java.util.EnumSet;

public class BeasterAngryGoal<T extends PathfinderMob & IChargeMob> extends TimeConditionGoal {
	private final T mob;
	private double speed;
	private boolean attacked;

	public BeasterAngryGoal(T frostBeaster, UniformInt cooldown, UniformInt time, double speed) {
		super(frostBeaster, cooldown, time);
		this.mob = frostBeaster;
		this.speed = speed;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean isMatchCondition() {
		return this.mob.getTarget() != null && this.mob.getMainHandItem().isEmpty();
	}

	@Override
	public boolean canContinueToUse() {
		return !this.attacked && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.mob.getNavigation().stop();
		this.attacked = false;
		this.mob.onCharge();
	}

	@Override
	public void tick() {
		super.tick();

		if (this.mob.getTarget() != null) {
			LivingEntity livingentity = this.mob.getTarget();
			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			if (this.tick >= 20) {
				this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
				this.mob.getNavigation().moveTo(livingentity, this.speed);
				this.checkAndPerformAttack(livingentity, d0);
			}
		}


	}

	protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
		double d0 = this.getAttackReachSqr(p_25557_);
		if (p_25558_ <= d0 && !this.attacked) {
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.onChargeDamage(p_25557_);
			this.attacked = true;
		}
	}

	protected double getAttackReachSqr(LivingEntity p_25556_) {
		return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_25556_.getBbWidth();
	}
}
