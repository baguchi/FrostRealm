package baguchan.frostrealm.item;

import baguchan.frostrealm.client.screen.FrostArchiveScreen;
import baguchan.frostrealm.registry.FrostArchives;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FrostArchiveItem extends Item {
    public FrostArchiveItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (p_41432_.isClientSide()) {
            if (p_41433_ == Minecraft.getInstance().player) {
                Minecraft.getInstance().setScreen(new FrostArchiveScreen(FrostArchives.FIRST_STEP.get()));
            }
        }
        p_41433_.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, p_41432_.isClientSide());
    }
}
