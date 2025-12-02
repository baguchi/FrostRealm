package baguchan.frostrealm.item;

import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class FrostSpearItem extends Item {

	public FrostSpearItem(Properties properties) {
		super(properties);
	}

    @Override
	public void hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
		if (p_43279_.isAttackable() && p_43279_.canFreeze()) {
			p_43279_.setTicksFrozen(Mth.clamp(p_43279_.getTicksFrozen() + 400, 0, 600));
		}
	}

	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.supportsEnchantment(stack, enchantment) || enchantment == Enchantments.BREACH;
	}
}
