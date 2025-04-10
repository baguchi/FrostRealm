package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostAnimations;
import baguchi.bagus_lib.util.client.AnimationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

public class SpearItem extends Item {
    public static final ResourceLocation BASE_ENTITY_RANGE = ResourceLocation.withDefaultNamespace("base_entity_range");
    public static final ResourceLocation BASE_BLOCK_RANGE = ResourceLocation.withDefaultNamespace("base_block_range");


    public SpearItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        if (!entity.swinging && entity instanceof Player player && !player.level().isClientSide()) {
            AnimationUtil.sendAnimation(entity, FrostAnimations.ATTACK);
        }
        return super.onEntitySwing(stack, entity, hand);
    }

    @Override
    public boolean mineBlock(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
        if (p_43284_.getDestroySpeed(p_43283_, p_43285_) != 0.0F) {
            p_43282_.hurtAndBreak(2, p_43286_, EquipmentSlot.MAINHAND);
        }

        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return super.canPerformAction(stack, itemAbility);
    }
}
