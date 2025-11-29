package baguchan.frostrealm.item;

import baguchan.frostrealm.api.IItemAnimation;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.registry.FrostAnimations;
import baguchan.frostrealm.registry.FrostAttachs;
import baguchi.bagus_lib.util.client.BagusAnimationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class SpearItem extends Item implements IItemAnimation {
    public static final Identifier BASE_ENTITY_RANGE = Identifier.withDefaultNamespace("base_entity_range");
    public static final Identifier BASE_BLOCK_RANGE = Identifier.withDefaultNamespace("base_block_range");


    public SpearItem(Properties properties) {
        super(properties);
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2, false);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof Player player && !player.level().isClientSide()) {
            if (!entity.swinging) {
                FrostLivingCapability capability = player.getData(FrostAttachs.FROST_LIVING);
                BagusAnimationUtil.sendAnimation(player, FrostAnimations.SPEAR_ATTACK);
                capability.usingItem = player.getItemBySlot(EquipmentSlot.MAINHAND).copy();

            }
        }
        return super.onEntitySwing(stack, entity, hand);
    }

    @Override
    public void onEntityStopAnimation(LivingEntity entity) {
        BagusAnimationUtil.sendStopAnimation(entity, FrostAnimations.SPEAR_ATTACK);
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
