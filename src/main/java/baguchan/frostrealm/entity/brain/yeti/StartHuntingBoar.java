package baguchan.frostrealm.entity.brain.yeti;

import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.animal.FrostBoar;
import baguchan.frostrealm.entity.brain.YetiAi;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class StartHuntingBoar {
    public static OneShot<Yeti> create() {
        return BehaviorBuilder.create((p_259791_) -> {
            return p_259791_.group(p_259791_.present(FrostMemoryModuleType.NEAREST_FROST_BOARS.get()), p_259791_.absent(MemoryModuleType.ANGRY_AT), p_259791_.absent(MemoryModuleType.HUNTED_RECENTLY), p_259791_.registered(FrostMemoryModuleType.NEAREST_YETIS.get())).apply(p_259791_, (p_259255_, p_260214_, p_259562_, p_259156_) -> {
                return (p_259918_, p_259191_, p_259772_) -> {
                    if (!p_259191_.isBaby() && !p_259791_.tryGet(p_259156_).map((p_259958_) -> {
                        return p_259958_.stream().anyMatch(StartHuntingBoar::hasHuntedRecently);
                    }).isPresent()) {
                        FrostBoar yeti = p_259791_.get(p_259255_).get(p_259191_.getRandom().nextInt(p_259791_.get(p_259255_).size()));
                        YetiAi.setAttackTarget(p_259191_, yeti);
                        YetiAi.dontKillAnyMoreBoarForAWhile(p_259191_);
                        p_259791_.tryGet(p_259156_).ifPresent((p_259760_) -> {
                            p_259760_.forEach(YetiAi::dontKillAnyMoreBoarForAWhile);
                        });
                        return true;
                    } else {
                        return false;
                    }
                };
            });
        });
    }

    private static boolean hasHuntedRecently(Yeti p_260138_) {
        return p_260138_.getBrain().hasMemoryValue(MemoryModuleType.HUNTED_RECENTLY) && p_260138_.isHunt();
    }
}