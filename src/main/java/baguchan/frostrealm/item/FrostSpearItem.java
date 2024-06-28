package baguchan.frostrealm.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FrostSpearItem extends Item {
	public static final ResourceLocation BASE_ENTITY_RANGE = ResourceLocation.withDefaultNamespace("base_entity_range");
	public static final ResourceLocation BASE_BLOCK_RANGE = ResourceLocation.withDefaultNamespace("base_block_range");

	public FrostSpearItem(Properties properties) {
		super(properties);
	}

	public static ItemAttributeModifiers createAttributes() {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 5.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
				)
				.add(
						Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.6F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
				)
				.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_RANGE, 1F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_RANGE, 1F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.build();
	}

	public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
		return !p_43294_.isCreative();
	}

	public float getDestroySpeed(ItemStack p_43288_, BlockState p_43289_) {
		if (p_43289_.is(Blocks.COBWEB)) {
			return 0.1F;
		} else {
			return super.getDestroySpeed(p_43288_, p_43289_);
		}
	}

	public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
		p_43278_.hurtAndBreak(1, p_43280_, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
		if (p_43279_.isAttackable()) {
			p_43279_.setTicksFrozen(Mth.clamp(p_43279_.getTicksFrozen() + 100, 0, 600));
		}
		return true;
	}

	public boolean mineBlock(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
		if (p_43284_.getDestroySpeed(p_43283_, p_43285_) != 0.0F) {
			p_43282_.hurtAndBreak(2, p_43286_, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
		}

		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack p_336002_, BlockState p_41450_) {
		return false;
	}
	@Override
	public int getEnchantmentValue() {
		return 20;
	}
}
