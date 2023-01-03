package baguchan.frostrealm.aurorapower;

import baguchan.frostrealm.registry.AuroraPowers;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;

public class AuroraPower {
	private final Map<Attribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
	protected final Rarity rarity;

	private final EquipmentSlot[] slots;
	private final int level;
	private int minlevel = 1;

	public AuroraPower(Properties properties, EquipmentSlot[] equipmentSlots) {
		this.rarity = properties.rarity;
		this.level = properties.level;
		this.slots = equipmentSlots;
	}

	public EquipmentSlot[] getSlots() {
		return slots;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public AuroraPower setMinLevel(int level) {
		this.minlevel = level;

		return this;
	}


	/**
	 * Returns the minimum level that the AuroraPower can have.
	 */
	public int getMinLevel() {
		return minlevel;
	}

	/**
	 * Returns the maximum level that the AuroraPower can have.
	 */
	public int getMaxLevel() {
		return level;
	}

	public int getMinCost(int auroraPowerLevel) {
		return 1 + (auroraPowerLevel - 1) * 10;
	}

	public int getMaxCost(int auroraPowerLevel) {
		return this.getMinCost(auroraPowerLevel) + 5;
	}


	public Map<EquipmentSlot, ItemStack> getSlotItems(LivingEntity p_44685_) {
		Map<EquipmentSlot, ItemStack> map = Maps.newEnumMap(EquipmentSlot.class);

		for (EquipmentSlot equipmentslot : this.slots) {
			ItemStack itemstack = p_44685_.getItemBySlot(equipmentslot);
			if (!itemstack.isEmpty()) {
				map.put(equipmentslot, itemstack);
			}
		}

		return map;
	}

	public void tick(LivingEntity entity, int level) {

	}

	public final boolean isCompatibleWith(ItemStack itemStack, AuroraPower auroraPower) {
		return this.canApplyTogether(auroraPower) && auroraPower.canApplyTogether(this) && this.canApplyItem(itemStack);
	}

	public final boolean isCompatibleWith(ItemStack itemStack) {
		for (AuroraPower auroraPower : AuroraPowerUtils.getAuroraPowers(itemStack).keySet()) {
			if (!this.canApplyTogether(auroraPower) || !auroraPower.canApplyTogether(this) || !this.canApplyItem(itemStack)) {
				return false;
			}
		}
		return this.canApplyItem(itemStack);
	}

	public boolean isTresureEnchant() {
		return false;
	}

	public boolean isOnlyChest() {
		return false;
	}

	/**
	 * Determines if the AuroraPower passed can be applyied together with this AuroraPower.
	 */
	protected boolean canApplyTogether(AuroraPower ench) {
		return this != ench;
	}

	protected boolean canApplyItem(ItemStack stack) {
		return true;
	}

	public AuroraPower addAttributesModifier(Attribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), Util.makeDescriptionId("aurora_power", AuroraPowers.getRegistry().get().getKey(this)), amount, operation);
		this.attributeModifierMap.put(attributeIn, attributemodifier);
		return this;
	}

	public Map<Attribute, AttributeModifier> getAttributeModifierMap() {
		return this.attributeModifierMap;
	}

	public void removeAttributesModifiersFromEntity(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn) {
		for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			AttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
			if (modifiableattributeinstance != null) {
				modifiableattributeinstance.removeModifier(entry.getValue());
			}
		}

	}

	public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
		for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			AttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
			if (modifiableattributeinstance != null) {
				AttributeModifier attributemodifier = entry.getValue();
				modifiableattributeinstance.removeModifier(attributemodifier);
				modifiableattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), AuroraPowers.getRegistry().get().getKey(this).toString() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
			}
		}
	}

	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
		return modifier.getAmount() * (double) (amplifier);
	}

	public static class Properties {
		private final Rarity rarity;
		private final int level;

		public Properties(Rarity rarity, int level) {
			this.rarity = rarity;
			this.level = level;
		}
	}

	public static enum Rarity {
		COMMON(10),
		UNCOMMON(5),
		RARE(2),
		VERY_RARE(1);

		private final int weight;

		private Rarity(int rarityWeight) {
			this.weight = rarityWeight;
		}

		/**
		 * Retrieves the weight of Rarity.
		 */
		public int getWeight() {
			return this.weight;
		}
	}
}