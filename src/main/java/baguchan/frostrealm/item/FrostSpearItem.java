package baguchan.frostrealm.item;

import bagu_chan.bagus_lib.util.client.AnimationUtil;
import baguchan.frostrealm.registry.FrostAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
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
				.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_RANGE, 0.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_RANGE, 0.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.build();
	}

	public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
		return !p_43294_.isCreative();
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if (!player.level().isClientSide()) {
			AnimationUtil.sendAnimation(player, FrostAnimations.ATTACK);
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
		if (p_43279_.isAttackable() && p_43279_.canFreeze()) {
			p_43279_.setTicksFrozen(Mth.clamp(p_43279_.getTicksFrozen() + 100, 0, 600));
		}
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
	public int getEnchantmentValue() {
		return 20;
	}


	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.supportsEnchantment(stack, enchantment) || enchantment == Enchantments.BREACH;
	}
}
