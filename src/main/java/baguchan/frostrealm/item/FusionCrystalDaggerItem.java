package baguchan.frostrealm.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.material.Material;

public class FusionCrystalDaggerItem extends Item implements Vanishable {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	private Multimap<Attribute, AttributeModifier> extraModifiers;
	private float extraAttackDamage;
	private int damageTick;
	private int cooldownTick;

	public FusionCrystalDaggerItem(Properties properties) {
		super(properties);
		this.attackDamage = 4.0F;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -2.2F, AttributeModifier.Operation.ADDITION));
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
			return 14.0F;
		} else {
			Material material = p_43289_.getMaterial();
			return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !p_43289_.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		damageTick = 600;

		if (cooldownTick <= 0 && player.getRandom().nextFloat() < 0.05F) {
			if (extraAttackDamage == 4.0F) {
				extraAttackDamage = 0.0F;
				player.heal(2);
			}

			extraAttackDamage = Mth.clamp(extraAttackDamage + 0.5F, 0.0F, 4.0F);

			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamage + extraAttackDamage, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -2.2F, AttributeModifier.Operation.ADDITION));
			this.extraModifiers = builder.build();
			this.cooldownTick = 10;
		}

		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
		super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);

		if (damageTick > 0) {
			--this.damageTick;
		}

		if (cooldownTick > 0) {
			--this.cooldownTick;
		}

		if (damageTick == 0) {
			extraModifiers = null;
			this.extraAttackDamage = 0.0F;
			this.damageTick = -1;
		}
	}

	public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
		p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
			p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
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
		return p_43298_.is(Blocks.COBWEB);
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {
		if (p_43274_ == EquipmentSlot.MAINHAND && extraModifiers != null) {
			return extraModifiers;
		}

		return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
		return net.minecraftforge.common.ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
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
