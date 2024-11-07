package baguchan.frostrealm.entity.brain.behavior;

import baguchan.frostrealm.entity.HasContainerEntity;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;

import java.util.function.Predicate;

public class TakeBackFromStealer {
    public static <T extends Mob & HasContainerEntity> OneShot<T> create(Predicate<T> p_379852_) {
        return BehaviorBuilder.create(
                p_379103_ -> p_379103_.group(
                                p_379103_.registered(MemoryModuleType.LOOK_TARGET),
                                p_379103_.registered(MemoryModuleType.WALK_TARGET),
                                p_379103_.absent(FrostMemoryModuleType.TAKE_BACK_COOLDOWN.get()),
                                p_379103_.present(FrostMemoryModuleType.TAKE_BACK_TARGET.get()),
                                p_379103_.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                        )
                        .apply(
                                p_379103_,
                                (p_379118_, walk, cooldown, p_379119_, p_379121_) -> (p_379112_, p_379113_, p_379114_) -> {
                                    LivingEntity livingentity = p_379103_.get(p_379119_);
                                    if (p_379852_.test(p_379113_)
                                            && p_379113_.isWithinMeleeAttackRange(livingentity)
                                            && p_379103_.<NearestVisibleLivingEntities>get(p_379121_).contains(livingentity)) {
                                        p_379118_.set(new EntityTracker(livingentity, true));
                                        p_379113_.swing(InteractionHand.MAIN_HAND);
                                        p_379113_.getInventory().addItem(livingentity.getMainHandItem().split(1));
                                        p_379119_.erase();
                                        return true;
                                    } else {
                                        p_379118_.set(new EntityTracker(livingentity, true));

                                        walk.set(new WalkTarget(livingentity, 1.25F, 1));
                                        return true;
                                    }
                                }
                        )
        );
    }
}
