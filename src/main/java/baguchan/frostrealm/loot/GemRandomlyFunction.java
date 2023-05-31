package baguchan.frostrealm.loot;

import baguchan.frostrealm.registry.FrostLootFunctions;
import baguchan.frostrealm.utils.ModifierUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GemRandomlyFunction extends LootItemConditionalFunction {
    final List<GemRandomlyFunction.Modifier> modifiers;

    GemRandomlyFunction(LootItemCondition[] p_80833_, List<GemRandomlyFunction.Modifier> p_80834_) {
        super(p_80833_);
        this.modifiers = ImmutableList.copyOf(p_80834_);
    }

    public LootItemFunctionType getType() {
        return FrostLootFunctions.RANDOM_GEM_FUNCTION;
    }

    public Set<LootContextParam<?>> getReferencedContextParams() {
        return this.modifiers.stream().flatMap((p_165234_) -> {
            return p_165234_.amount.getReferencedContextParams().stream();
        }).collect(ImmutableSet.toImmutableSet());
    }

    public ItemStack run(ItemStack p_80840_, LootContext p_80841_) {
        RandomSource randomsource = p_80841_.getRandom();
        GemRandomlyFunction.Modifier gemRandomlyFunction$modifier = this.modifiers.get(randomsource.nextInt(this.modifiers.size()));

        UUID uuid = UUID.randomUUID();
        ModifierUtils.addAttributeModifier(p_80840_, gemRandomlyFunction$modifier.attribute, new AttributeModifier(uuid, "Tool Bounus", (double) gemRandomlyFunction$modifier.amount.getFloat(p_80841_), AttributeModifier.Operation.ADDITION), gemRandomlyFunction$modifier.armor);


        return p_80840_;
    }

    public static GemRandomlyFunction.ModifierBuilder modifier(Attribute p_165237_, NumberProvider p_165239_, boolean armor) {
        return new GemRandomlyFunction.ModifierBuilder(p_165237_, p_165239_, armor);
    }

    public static GemRandomlyFunction.Builder setAttributes() {
        return new GemRandomlyFunction.Builder();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<GemRandomlyFunction.Builder> {
        private final List<GemRandomlyFunction.Modifier> modifiers = Lists.newArrayList();

        protected GemRandomlyFunction.Builder getThis() {
            return this;
        }

        public GemRandomlyFunction.Builder withModifier(GemRandomlyFunction.ModifierBuilder p_165246_) {
            this.modifiers.add(p_165246_.build());
            return this;
        }

        public LootItemFunction build() {
            return new GemRandomlyFunction(this.getConditions(), this.modifiers);
        }
    }

    static class Modifier {
        final Attribute attribute;
        final NumberProvider amount;
        final boolean armor;

        Modifier(Attribute p_165251_, NumberProvider p_165253_, boolean armor) {
            this.attribute = p_165251_;
            this.amount = p_165253_;
            this.armor = armor;
        }

        public JsonObject serialize(JsonSerializationContext p_80866_) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("attribute", BuiltInRegistries.ATTRIBUTE.getKey(this.attribute).toString());
            jsonobject.add("amount", p_80866_.serialize(this.amount));

            jsonobject.add("armor", p_80866_.serialize(this.armor));


            return jsonobject;
        }

        public static GemRandomlyFunction.Modifier deserialize(JsonObject p_80863_, JsonDeserializationContext p_80864_) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(p_80863_, "attribute"));
            Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(resourcelocation);
            if (attribute == null) {
                throw new JsonSyntaxException("Unknown attribute: " + resourcelocation);
            } else {
                NumberProvider numberprovider = GsonHelper.getAsObject(p_80863_, "amount", p_80864_, NumberProvider.class);
                boolean armor = false;
                if (GsonHelper.isBooleanValue(p_80863_, "armor")) {
                    armor = GsonHelper.getAsBoolean(p_80863_, "armor");
                }

                return new GemRandomlyFunction.Modifier(attribute, numberprovider, armor);
            }
        }

        private static String operationToString(AttributeModifier.Operation p_80861_) {
            switch (p_80861_) {
                case ADDITION:
                    return "addition";
                case MULTIPLY_BASE:
                    return "multiply_base";
                case MULTIPLY_TOTAL:
                    return "multiply_total";
                default:
                    throw new IllegalArgumentException("Unknown operation " + p_80861_);
            }
        }

        private static AttributeModifier.Operation operationFromString(String p_80870_) {
            switch (p_80870_) {
                case "addition":
                    return AttributeModifier.Operation.ADDITION;
                case "multiply_base":
                    return AttributeModifier.Operation.MULTIPLY_BASE;
                case "multiply_total":
                    return AttributeModifier.Operation.MULTIPLY_TOTAL;
                default:
                    throw new JsonSyntaxException("Unknown attribute modifier operation " + p_80870_);
            }
        }
    }

    public static class ModifierBuilder {
        private final Attribute attribute;
        private final NumberProvider amount;
        private final boolean armor;

        public ModifierBuilder(Attribute p_165264_, NumberProvider p_165266_, boolean armor) {
            this.attribute = p_165264_;
            this.amount = p_165266_;
            this.armor = armor;
        }

        public GemRandomlyFunction.Modifier build() {
            return new GemRandomlyFunction.Modifier(this.attribute, this.amount, this.armor);
        }
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<GemRandomlyFunction> {
        public void serialize(JsonObject p_80891_, GemRandomlyFunction p_80892_, JsonSerializationContext p_80893_) {
            super.serialize(p_80891_, p_80892_, p_80893_);
            JsonArray jsonarray = new JsonArray();

            for (GemRandomlyFunction.Modifier GemRandomlyFunction$modifier : p_80892_.modifiers) {
                jsonarray.add(GemRandomlyFunction$modifier.serialize(p_80893_));
            }

            p_80891_.add("modifiers", jsonarray);
        }

        public GemRandomlyFunction deserialize(JsonObject p_80883_, JsonDeserializationContext p_80884_, LootItemCondition[] p_80885_) {
            JsonArray jsonarray = GsonHelper.getAsJsonArray(p_80883_, "modifiers");
            List<GemRandomlyFunction.Modifier> list = Lists.newArrayListWithExpectedSize(jsonarray.size());

            for (JsonElement jsonelement : jsonarray) {
                list.add(GemRandomlyFunction.Modifier.deserialize(GsonHelper.convertToJsonObject(jsonelement, "modifier"), p_80884_));
            }

            if (list.isEmpty()) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            } else {
                return new GemRandomlyFunction(p_80885_, list);
            }
        }
    }
}