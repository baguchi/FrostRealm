package baguchan.frostrealm.loot;

import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.RandomSource;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import java.util.List;
import java.util.Set;

public class AuroraWithLevelsFunction extends LootItemConditionalFunction {
    public static final MapCodec<AuroraWithLevelsFunction> CODEC = RecordCodecBuilder.mapCodec(
            p_344692_ -> commonFields(p_344692_)
                    .and(
                            NumberProviders.CODEC.fieldOf("levels").forGetter(p_298844_ -> p_298844_.levels)
                    )
                    .apply(p_344692_, AuroraWithLevelsFunction::new)
    );
    private final NumberProvider levels;

    AuroraWithLevelsFunction(List<LootItemCondition> condtions, NumberProvider levels) {
        super(condtions);
        this.levels = levels;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return this.levels.getReferencedContextParams();
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return CODEC;
    }

    /**
     * Called to perform the actual action of this function, after conditions have been checked.
     */
    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        RandomSource randomsource = context.getRandom();
        return AuroraPowerUtils.auroraInfusionItem(randomsource, stack, this.levels.getInt(context), true);
    }

    public static AuroraWithLevelsFunction.Builder enchantWithLevels(HolderLookup.Provider registries, NumberProvider levels) {
        return new AuroraWithLevelsFunction.Builder(levels);
    }

    public static class Builder extends LootItemConditionalFunction.Builder<AuroraWithLevelsFunction.Builder> {
        private final NumberProvider levels;

        public Builder(NumberProvider levels) {
            this.levels = levels;
        }

        protected AuroraWithLevelsFunction.Builder getThis() {
            return this;
        }


        @Override
        public LootItemFunction build() {
            return new AuroraWithLevelsFunction(this.getConditions(), this.levels);
        }
    }
}