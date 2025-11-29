package baguchan.frostrealm.item;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class AttachableCrystalItem extends Item {
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component SMITHABLE = Component.translatable(
                    Util.makeDescriptionId("item", Identifier.fromNamespaceAndPath(FrostRealm.MODID, "attachable"))
            )
            .withStyle(DESCRIPTION_FORMAT);

    public AttachableCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, TooltipDisplay p_399753_, Consumer<Component> p_399884_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_339594_, p_399753_, p_399884_, p_41424_);
        p_399884_.accept(CommonComponents.EMPTY);
        p_399884_.accept(CommonComponents.space().append(SMITHABLE));
    }
}
