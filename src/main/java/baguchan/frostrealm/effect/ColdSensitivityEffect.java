package baguchan.frostrealm.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ColdSensitivityEffect extends MobEffect {
    public ColdSensitivityEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public boolean applyEffectTick(ServerLevel p_376587_, LivingEntity p_19467_, int p_19468_) {
        p_19467_.setTicksFrozen(Mth.clamp(p_19467_.getTicksFrozen() + 4, 0, 200));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
        return true;
    }
}
