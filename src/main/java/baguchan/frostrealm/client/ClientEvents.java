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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        boolean flag = false;
        if (stack.is(FrostItems.AURORA_GEM.get())) {
            List<Component> list = Lists.newArrayList();
            Attribute prevAttribute = null;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                Multimap<Attribute, AttributeModifier> multimap = ModifierUtils.getAttributeModifiers(stack, equipmentSlot);
                if (!multimap.isEmpty()) {
                    list.add(CommonComponents.EMPTY);
                    list.add(Component.translatable("frostrealm.item.modifiers.has_attribute").withStyle(ChatFormatting.DARK_GREEN));
                    flag = true;
                    return;
                }
            }

            if (!flag) {
                list.add(CommonComponents.EMPTY);
                list.add(Component.translatable("frostrealm.item.modifiers.empty").withStyle(ChatFormatting.GRAY));

            }

            event.getToolTip().addAll(list);
        }
    }
}
