package baguchan.frostrealm.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.UUID;

public class FrostSpearItem extends Item implements Vanishable {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

	public FrostSpearItem(Properties properties) {
		super(properties);
		this.attackDamage = 5.0F;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.6F, AttributeModifier.Operation.ADDITION));
		builder.put(NeoForgeMod.ENTITY_REACH.value(), new AttributeModifier(UUID.fromString("358accd2-f9a8-912b-2397-6356e2043b68"), "Weapon modifier", 1.0F, AttributeModifier.Operation.ADDITION));
		builder.put(NeoForgeMod.BLOCK_REACH.value(), new AttributeModifier(UUID.fromString("2460a516-b2ef-b8a4-a6bc-33c4566d8a90"), "Weapon modifier", 1.0F, AttributeModifier.Operation.ADDITION));

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
		p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
			p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		if (p_43279_.isAttackable()) {
			p_43279_.setTicksFrozen(Mth.clamp(p_43279_.getTicksFrozen() + 100, 0, 600));
		}
		return true;
	}

	public boolean mineBlock(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
		if (p_43284_.getDestroySpeed(p_43283_, p_43285_) != 0.0F) {
			p_43282_.hurtAndBreak(2, p_43286_, (p_43276_) -> {
				p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}

		return true;
	}

	public boolean isCorrectToolForDrops(BlockState p_43298_) {
		return false;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {

		return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment.category == EnchantmentCategory.WEAPON || super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public int getEnchantmentValue() {
		return 20;
	}
}
