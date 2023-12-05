package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.utils.ModifierUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientEvents {
    protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event) {

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        boolean flag = true;
        if (stack.is(FrostItems.AURORA_GEM.get())) {
            List<Component> list = Lists.newArrayList();
            Attribute prevAttribute = null;
            list.add(CommonComponents.EMPTY);
            list.add(Component.translatable("frostrealm.item.modifiers.total").withStyle(ChatFormatting.GRAY));

            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                Multimap<Attribute, AttributeModifier> multimap = ModifierUtils.getAttributeModifiers(stack, equipmentSlot);
                if (!multimap.isEmpty()) {

                    for (Attribute attribute : multimap.keys()) {
                        Collection<AttributeModifier> attributemodifiers = multimap.get(attribute);
                        double d0 = 0;

                        double d2 = 0;
                        double d3 = 0;
                        for (AttributeModifier attributemodifier : attributemodifiers) {

                            d0 = attributemodifier.getAmount();

                            double d1 = 0;
                            if (attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                                if (attribute == Attributes.KNOCKBACK_RESISTANCE) {
                                    d1 += d0 * 10.0D;
                                } else {
                                    d1 += d0;
                                }
                            } else {
                                d1 *= d0 * 100.0D;
                            }
                            d3 += d1;
                            d2 += d0;
                        }
                        if (prevAttribute != attribute) {
                            if (d2 > 0.0D) {
                                list.add(Component.translatable("attribute.modifier.plus." + 0, ATTRIBUTE_MODIFIER_FORMAT.format(d3), Component.translatable(attribute.getDescriptionId())).withStyle(ChatFormatting.BLUE));
                            } else if (d2 < 0.0D) {
                                d3 *= -1.0D;
                                list.add(Component.translatable("attribute.modifier.take." + 0, ATTRIBUTE_MODIFIER_FORMAT.format(d3), Component.translatable(attribute.getDescriptionId())).withStyle(ChatFormatting.RED));
                            }
                        }
                        prevAttribute = attribute;
                    }
                }
            }
            event.getToolTip().addAll(list);
        }
    }
}
