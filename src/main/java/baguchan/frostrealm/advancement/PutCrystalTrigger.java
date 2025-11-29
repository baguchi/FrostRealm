package baguchan.frostrealm.advancement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostCriterions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class PutCrystalTrigger extends SimpleCriterionTrigger<PutCrystalTrigger.Instance> {

    public static final Identifier ID = FrostRealm.prefix("put_crystal");

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create((p_311988_) -> {
            return p_311988_.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player)).apply(p_311988_, Instance::new);
        });

        @Override
        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }

    public static Criterion<Instance> get() {
        return FrostCriterions.PUT_CRYSTAL.get().createCriterion(new Instance(Optional.empty()));
    }
}