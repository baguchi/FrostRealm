package baguchan.frostrealm.item;

import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class FrostSpearItem extends SpearItem {

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
				.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_RANGE, 0.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_RANGE, 0.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.build();
	}


	/*@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if (!player.level().isClientSide()) {
			AnimationUtil.sendAnimation(player, FrostAnimations.ATTACK);
		}
		return super.onLeftClickEntity(stack, player, entity);
	}*/


    @Override
	public void hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
		if (p_43279_.isAttackable() && p_43279_.canFreeze()) {
			p_43279_.setTicksFrozen(Mth.clamp(p_43279_.getTicksFrozen() + 100, 0, 600));
		}
	}

	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.supportsEnchantment(stack, enchantment) || enchantment == Enchantments.BREACH;
	}
}
