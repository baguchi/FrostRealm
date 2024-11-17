package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.animal.CrystalFox;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Objects;

public class FoxSittingGoal extends Goal {

    private final CrystalFox mob;
    private int cooldown;
    private int sitTick;

    public FoxSittingGoal(CrystalFox p_25492_) {
        this.mob = p_25492_;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasControllingPassenger()) {
            return false;
        } else {
            if (Objects.equals(this.mob.getState(), CrystalFox.State.SITTING.name())) {
                return true;
            }
            if (!Objects.equals(this.mob.getState(), CrystalFox.State.IDLING.name())) {
                return false;
            }
            if (this.cooldown > 0) {
                this.cooldown--;
            }
            if (this.cooldown <= 0 && this.mob.onGround() && !this.mob.isInWater()) {
                this.cooldown = this.mob.getRandom().nextInt(600, 1200);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.onGround() || this.sitTick <= 600;
    }

    @Override
    public void start() {
        super.start();
        this.sitTick = 0;
        this.mob.setState(CrystalFox.State.SITTING);
    }

    @Override
    public void tick() {
        super.tick();
        this.sitTick++;
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.setState(CrystalFox.State.IDLING);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
