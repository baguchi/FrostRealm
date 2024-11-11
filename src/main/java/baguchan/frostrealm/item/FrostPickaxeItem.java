package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostToolMaterials;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;

public class FrostPickaxeItem extends FrostDiggerItem {
    public FrostPickaxeItem(FrostToolMaterials.FrostToolMaterial tofuItemTier, float p_362481_, float p_364182_, Properties properties) {
        super(tofuItemTier, BlockTags.MINEABLE_WITH_PICKAXE, p_362481_, p_364182_, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility);
    }

}
