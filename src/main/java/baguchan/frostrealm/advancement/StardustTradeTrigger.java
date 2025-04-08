package baguchan.frostrealm.advancement;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostCriterions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public class StardustTradeTrigger extends SimpleCriterionTrigger<StardustTradeTrigger.Instance> {

    public static final ResourceLocation ID = FrostRealm.prefix("stardust_trade");

    public void trigger(ServerPlayer player, ItemStack itemStack) {
        this.trigger(player, p_74436_ -> p_74436_.matches(itemStack));
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player,
                           Optional<ItemPredicate> item) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create((p_311988_) -> {
            return p_311988_.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                    ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Instance::item)
            ).apply(p_311988_, Instance::new);
        });

        @Override
        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }

        public boolean matches(ItemStack p_74451_) {
            return this.item.isEmpty() || ((ItemPredicate) this.item.get()).test(p_74451_);
        }

        public static Criterion<Instance> usedTradeItem(ItemPredicate p_163725_) {
            return FrostCriterions.STARDUST_TRADE.get().createCriterion(new Instance(Optional.empty(), Optional.of(p_163725_)));
        }

        public static Criterion<Instance> usedTradeItem(HolderGetter<Item> p_364731_, ItemLike p_74453_) {
            return FrostCriterions.STARDUST_TRADE.get()
                    .createCriterion(
                            new Instance(Optional.empty(), Optional.of(ItemPredicate.Builder.item().of(p_364731_, p_74453_).build()))
                    );
        }
    }
}