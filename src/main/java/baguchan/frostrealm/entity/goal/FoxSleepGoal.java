package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.entity.animal.CrystalFox;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Objects;

public class FoxSleepGoal extends Goal {

    private final CrystalFox mob;
    private int cooldown;

    public FoxSleepGoal(CrystalFox p_25492_) {
        this.mob = p_25492_;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasControllingPassenger()) {
            return false;
        } else {
            if (Objects.equals(this.mob.getState(), CrystalFox.State.SLEEPING.name()) && this.mob.level().isNight()) {
                return true;
            }
            if (!Objects.equals(this.mob.getState(), CrystalFox.State.IDLING.name())) {
                return false;
            }
            if (this.cooldown > 0) {
                this.cooldown--;
            }
            if (this.cooldown <= 0 && this.mob.onGround() && !this.mob.isInWater() && (this.mob.level().isNight() || FrostWeatherSavedData.get(this.mob.level()) != null && FrostWeatherSavedData.get(this.mob.level()).isWeatherActive()) && !this.mob.level().canSeeSky(this.mob.blockPosition())) {
                this.cooldown = this.mob.getRandom().nextInt(600, 1800);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.onGround() || FrostWeatherSavedData.get(this.mob.level()) != null && FrostWeatherSavedData.get(this.mob.level()).isWeatherActive() || this.mob.level().isNight();
    }

    @Override
    public void start() {
        super.start();
        this.mob.setState(CrystalFox.State.SLEEPING);
    }

    @Override
    public void tick() {
        super.tick();
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
