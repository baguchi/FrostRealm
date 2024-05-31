package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class FrostCatalystItem extends Item {
	public FrostCatalystItem(Item.Properties tab) {
		super(tab);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.SNOW_BLOCK &&
				FrostBlocks.FROST_PORTAL.get().trySpawnPortal(context.getLevel(), context.getClickedPos().above())) {
			if (!context.getPlayer().isCreative())
                context.getItemInHand().hurtAndBreak(1, (LivingEntity) context.getPlayer(), LivingEntity.getSlotForHand(context.getHand()));
			return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}

	@Override
	public boolean isFoil(ItemStack p_77636_1_) {
		return true;
	}
}
