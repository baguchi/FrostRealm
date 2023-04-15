package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.utils.ai.YetiAi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class LookAtPlayerAndPanicGoal extends LookAtPlayerGoal {
    protected final Yeti yeti;

    public LookAtPlayerAndPanicGoal(Yeti p_25520_, Class<? extends LivingEntity> p_25521_, float p_25522_) {
        super(p_25520_, p_25521_, p_25522_);
        this.yeti = p_25520_;
    }

    public void tick() {
        if (this.lookAt instanceof LivingEntity livingEntity) {
            if (YetiAi.isWearingFear(livingEntity)) {
                this.yeti.setPanic(true);
            }
        }
        super.tick();
    }
}
