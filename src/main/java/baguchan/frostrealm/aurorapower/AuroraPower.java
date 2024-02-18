package baguchan.frostrealm.aurorapower;

import baguchan.frostrealm.registry.AuroraPowers;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;

public class AuroraPower {
    @Nullable
    protected String descriptionId;
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
        return this.canApplyTogether(auroraPower) && auroraPower.canApplyTogether(this);
    }

    public final boolean isCompatibleWith(ItemStack itemStack) {
        for (AuroraPower auroraPower : AuroraPowerUtils.getAuroraPowers(itemStack).keySet()) {
            if (!this.canApplyTogether(auroraPower) || !auroraPower.canApplyTogether(this)) {
                return false;
            }
        }
        return true;
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

    public boolean canApplyItem(ItemStack stack) {
        return true;
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("aurora_power", AuroraPowers.getRegistry().getKey(this));
        }

        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getFullname(int p_44701_) {
        MutableComponent mutablecomponent = Component.translatable(this.getDescriptionId());
        mutablecomponent.withStyle(ChatFormatting.GREEN);


        if (p_44701_ != 1 || this.getMaxLevel() != 1) {
            mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + p_44701_));
        }

        return mutablecomponent;
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