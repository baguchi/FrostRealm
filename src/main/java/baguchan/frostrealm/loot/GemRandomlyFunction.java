package baguchan.frostrealm.loot;

import baguchan.frostrealm.registry.FrostLootFunctions;
import baguchan.frostrealm.utils.ModifierUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
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

import java.util.EnumSet;
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
        return FrostLootFunctions.SET_GEM_FUNCTION;
    }

    public Set<LootContextParam<?>> getReferencedContextParams() {
        return this.modifiers.stream().flatMap((p_165234_) -> {
            return p_165234_.amount.getReferencedContextParams().stream();
        }).collect(ImmutableSet.toImmutableSet());
    }

    public ItemStack run(ItemStack p_80840_, LootContext p_80841_) {
        RandomSource randomsource = p_80841_.getRandom();

        for (GemRandomlyFunction.Modifier GemRandomlyFunction$modifier : this.modifiers) {
            UUID uuid = UUID.randomUUID();

            EquipmentSlot equipmentslot = Util.getRandom(GemRandomlyFunction$modifier.slots, randomsource);
            ModifierUtils.addAttributeModifier(p_80840_, GemRandomlyFunction$modifier.attribute, new AttributeModifier(uuid, "Tool Bounus", (double) GemRandomlyFunction$modifier.amount.getFloat(p_80841_), AttributeModifier.Operation.ADDITION), equipmentslot);
        }

        return p_80840_;
    }

    public static GemRandomlyFunction.ModifierBuilder modifier(String p_165236_, Attribute p_165237_, AttributeModifier.Operation p_165238_, NumberProvider p_165239_) {
        return new GemRandomlyFunction.ModifierBuilder(p_165237_, p_165239_);
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
        final EquipmentSlot[] slots;

        Modifier(Attribute p_165251_, NumberProvider p_165253_, EquipmentSlot[] p_165254_) {
            this.attribute = p_165251_;
            this.amount = p_165253_;
            this.slots = p_165254_;
        }

        public JsonObject serialize(JsonSerializationContext p_80866_) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("attribute", BuiltInRegistries.ATTRIBUTE.getKey(this.attribute).toString());
            jsonobject.add("amount", p_80866_.serialize(this.amount));

            if (this.slots.length == 1) {
                jsonobject.addProperty("slot", this.slots[0].getName());
            } else {
                JsonArray jsonarray = new JsonArray();

                for (EquipmentSlot equipmentslot : this.slots) {
                    jsonarray.add(new JsonPrimitive(equipmentslot.getName()));
                }

                jsonobject.add("slot", jsonarray);
            }

            return jsonobject;
        }

        public static GemRandomlyFunction.Modifier deserialize(JsonObject p_80863_, JsonDeserializationContext p_80864_) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(p_80863_, "attribute"));
            Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(resourcelocation);
            if (attribute == null) {
                throw new JsonSyntaxException("Unknown attribute: " + resourcelocation);
            } else {
                NumberProvider numberprovider = GsonHelper.getAsObject(p_80863_, "amount", p_80864_, NumberProvider.class);
                EquipmentSlot[] aequipmentslot;
                if (GsonHelper.isStringValue(p_80863_, "slot")) {
                    aequipmentslot = new EquipmentSlot[]{EquipmentSlot.byName(GsonHelper.getAsString(p_80863_, "slot"))};
                } else {
                    if (!GsonHelper.isArrayNode(p_80863_, "slot")) {
                        throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
                    }

                    JsonArray jsonarray = GsonHelper.getAsJsonArray(p_80863_, "slot");
                    aequipmentslot = new EquipmentSlot[jsonarray.size()];
                    int i = 0;

                    for (JsonElement jsonelement : jsonarray) {
                        aequipmentslot[i++] = EquipmentSlot.byName(GsonHelper.convertToString(jsonelement, "slot"));
                    }

                    if (aequipmentslot.length == 0) {
                        throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                    }
                }

                return new GemRandomlyFunction.Modifier(attribute, numberprovider, aequipmentslot);
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
        private final Set<EquipmentSlot> slots = EnumSet.noneOf(EquipmentSlot.class);

        public ModifierBuilder(Attribute p_165264_, NumberProvider p_165266_) {
            this.attribute = p_165264_;
            this.amount = p_165266_;
        }

        public GemRandomlyFunction.ModifierBuilder forSlot(EquipmentSlot p_165269_) {
            this.slots.add(p_165269_);
            return this;
        }

        public GemRandomlyFunction.Modifier build() {
            return new GemRandomlyFunction.Modifier(this.attribute, this.amount, this.slots.toArray(new EquipmentSlot[0]));
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