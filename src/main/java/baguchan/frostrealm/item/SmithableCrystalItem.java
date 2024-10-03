package baguchan.frostrealm.item;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SmithableCrystalItem extends Item {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component SMITHABLE = Component.translatable(
                    Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "smithing_template.smithable"))
            )
            .withStyle(DESCRIPTION_FORMAT);

    public SmithableCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack p_267313_, TooltipContext p_339591_, List<Component> p_266820_, TooltipFlag p_266857_) {
        super.appendHoverText(p_267313_, p_339591_, p_266820_, p_266857_);
        p_266820_.add(CommonComponents.EMPTY);
        p_266820_.add(CommonComponents.space().append(SMITHABLE));
    }
}
