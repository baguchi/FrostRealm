package baguchan.frostrealm.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.UUID;

public class FrostSpearItem extends Item {
	private final float attackDamage;
	private final ItemAttributeModifiers defaultModifiers;

	public FrostSpearItem(Properties properties) {
		super(properties);
		this.attackDamage = 5.0F;
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
		builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.6F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(UUID.fromString("358accd2-f9a8-912b-2397-6356e2043b68"), "Weapon modifier", 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		builder.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(UUID.fromString("2460a516-b2ef-b8a4-a6bc-33c4566d8a90"), "Weapon modifier", 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

		this.defaultModifiers = builder.build();
	}

	public float getDamage() {
		return this.attackDamage;
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

	public boolean isCorrectToolForDrops(BlockState p_43298_) {
		return false;
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return this.defaultModifiers;
	}

	@Override
	public int getEnchantmentValue() {
		return 20;
	}
}
