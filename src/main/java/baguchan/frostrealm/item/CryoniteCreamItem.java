package baguchan.frostrealm.item;

import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CryoniteCreamItem extends GlimmerRockItem {
    public CryoniteCreamItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack p_267313_, TooltipContext p_339591_, List<Component> p_266820_, TooltipFlag p_266857_) {
        super.appendHoverText(p_267313_, p_339591_, p_266820_, p_266857_);
        p_266820_.add(CommonComponents.EMPTY);
        p_266820_.add(Component.translatable("item.frostrealm.cryonite_cream.tooltip"));
    }
}
