package baguchan.frostrealm.item;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class SickleItem extends TieredItem {
    public static final ResourceLocation SWEEP_SPEED_ID = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "sweep_speed");

    public SickleItem(Tier tier, Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.SWEEPING_DAMAGE_RATIO, new AttributeModifier(SWEEP_SPEED_ID, 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return !p_43294_.isCreative();
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 20.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 2.0F)), 1.0F, 2);
    }

    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack p_346200_, LivingEntity p_345855_, LivingEntity p_346191_) {
        p_346200_.hurtAndBreak(1, p_346191_, EquipmentSlot.MAINHAND);
    }

    public boolean mineBlock(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
        if (p_43284_.getDestroySpeed(p_43283_, p_43285_) != 0.0F) {
            p_43282_.hurtAndBreak(2, p_43286_, EquipmentSlot.MAINHAND);
        }

        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return super.canPerformAction(stack, itemAbility) || itemAbility == ItemAbilities.SWORD_SWEEP;
    }
}
