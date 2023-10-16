package baguchan.frostrealm.entity.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class GuardHandler {
    protected final int hurtTriggerCount;
    protected int hurtCount;
    protected int hurtTick;
    protected boolean trigger;

    public GuardHandler(int hurtTriggerCount) {
        this.hurtTriggerCount = hurtTriggerCount;
    }

    public void tick(Mob entity) {
        if (!entity.level().isClientSide) {
            if (this.hurtCount > this.hurtTriggerCount) {
                this.trigger = true;
            }

            if (this.hurtTick > 0) {
                --this.hurtTick;
            } else {
                resetTrigger(true);
            }
        }
    }

    public void addHurtCount(float damage) {
        this.hurtCount += 1;
        this.hurtTick = (int) Mth.clamp(damage * 4 + this.hurtTick, 0, 400);
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public void resetTrigger(boolean trigger) {
        this.hurtCount = 0;
        this.trigger = false;
    }
}
