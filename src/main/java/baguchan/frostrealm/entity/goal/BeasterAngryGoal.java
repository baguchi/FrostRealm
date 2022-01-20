package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.api.animation.IAnimatable;
import baguchan.frostrealm.entity.FrostBeaster;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.EnumSet;

public class BeasterAngryGoal extends TimeConditionGoal {
	private final FrostBeaster mob;
	private boolean attacked;

	public BeasterAngryGoal(FrostBeaster frostBeaster, UniformInt cooldown, UniformInt time) {
		super(frostBeaster, cooldown, time);
		this.mob = frostBeaster;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean isMatchCondition() {
		return this.mob.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return !this.attacked && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.mob.setAnimation(FrostBeaster.GROWL_ANIMATION);

		this.attacked = false;
	}

	@Override
	public void tick() {
		super.tick();
		if ((this.mob.getAnimation() == IAnimatable.NO_ANIMATION || this.mob.getAnimation() == FrostBeaster.GROWL_ANIMATION) && this.mob.getAnimationTick() >= 15) {
			this.mob.setAnimationTick(5);
			this.mob.setAnimation(FrostBeaster.GROWL_ANIMATION);
		}

		if (this.mob.getTarget() != null) {
			LivingEntity livingentity = this.mob.getTarget();
			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			if (this.tick >= 20) {
				this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
				this.mob.getNavigation().moveTo(livingentity, 1.25D);
				this.checkAndPerformAttack(livingentity, d0);
			}
		}


	}

	protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
		double d0 = this.getAttackReachSqr(p_25557_);
		if (p_25558_ <= d0 && !this.attacked) {
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.setAnimation(FrostBeaster.GROWL_ATTACK_ANIMATION);
			p_25557_.hurt(DamageSource.mobAttack(this.mob), Mth.floor((float) (this.mob.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.25F)));
			this.attacked = true;
		}
	}

	protected double getAttackReachSqr(LivingEntity p_25556_) {
		return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_25556_.getBbWidth());
	}
}
