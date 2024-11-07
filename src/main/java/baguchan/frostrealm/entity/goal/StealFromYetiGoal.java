package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.animal.SnowPileQuail;
import baguchan.frostrealm.entity.brain.YetiAi;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class StealFromYetiGoal extends Goal {
    private final SnowPileQuail animal;
    private Yeti target;
    private final double speedModifier;
    private int cooldown;

    public StealFromYetiGoal(SnowPileQuail p_25319_, double p_25320_) {
        this.animal = p_25319_;
        this.speedModifier = p_25320_;
    }

    public boolean canUse() {
		if (this.animal.isBaby() || this.animal.getStealTarget() != null || !this.animal.getMainHandItem().isEmpty() || this.animal.hasEgg() || cooldown > 0) {
            return false;
        } else {
            List<? extends Yeti> list = this.animal.level().getEntitiesOfClass(Yeti.class, this.animal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            Yeti animal = null;
            double d0 = Double.MAX_VALUE;

            for (Yeti animal1 : list) {
                if (!animal1.isBaby()) {
                    double d1 = this.animal.distanceToSqr(animal1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        animal = animal1;
                    }
                }
            }

            if (animal == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else if (animal.hasFood().isEmpty()) {
                return false;
            } else {
                this.cooldown = 600 + this.animal.getRandom().nextIntBetweenInclusive(300, 600);
                this.target = animal;
                return true;
            }
        }
    }

    public boolean canContinueToUse() {
        if (!this.animal.isBaby()) {
            return false;
        } else if (!this.target.isAlive()) {
            return false;
        } else {
            double d0 = this.animal.distanceToSqr(this.target);
            return !(d0 < 12.0D) && !(d0 > 256.0D);
        }
    }

    public void start() {
    }

    public void stop() {

        double d0 = this.animal.distanceToSqr(this.target);
        if ((d0 < 14.0D)) {
            YetiAi.stealFromYeti(this.animal, this.target);
        }

        this.animal.setStealTarget(this.target);
    }

    public void tick() {
        this.animal.getNavigation().moveTo(this.target, this.speedModifier);
    }
}