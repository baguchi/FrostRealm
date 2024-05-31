package baguchan.frostrealm.utils.aurorapower;

import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.item.component.ItemAuroraPower;
import baguchan.frostrealm.registry.AuroraPowers;
import baguchan.frostrealm.registry.FrostDataCompnents;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class AuroraPowerUtils {
    private static final String TAG_ENCH_ID = "id";
    private static final String TAG_ENCH_LEVEL = "lvl";
    private static final float SWIFT_SNEAK_EXTRA_FACTOR = 0.15F;

    @Nullable
    public static int getAuroraPowerLevel(ItemAuroraPower itemAuroraPower, AuroraPower auroraPower) {
        return itemAuroraPower.getLevel(auroraPower);
    }

    /**
     * @deprecated forge: use {@link #getTagAuroraPowerLevel(AuroraPower, ItemStack)} or {@link AuroraPowerUtils#getAuroraPowersRaw(ItemStack)}
     */
    @Deprecated
    public static int getItemAuroraPowerLevel(AuroraPower p_44844_, ItemStack p_44845_) {
        return getTagAuroraPowerLevel(p_44844_, p_44845_);
    }

    /**
     * Gets the auroraPower level from NBT. Generally should use {@link AuroraPowerUtils#getAuroraPowersRaw(ItemStack)} for gameplay logic
     */
    public static int getTagAuroraPowerLevel(AuroraPower p_44844_, ItemStack p_44845_) {
        if (p_44845_.isEmpty()) {
            return 0;
        } else {


            ItemAuroraPower auroraPower = getAuroraPowersRaw(p_44845_);

            for (int i = 0; i < auroraPower.size(); ++i) {
                return getAuroraPowerLevel(auroraPower, p_44844_);
            }

            return 0;
        }
    }

    public static ItemAuroraPower getAuroraPowersRaw(ItemStack p_41164_) {
        ItemAuroraPower auroraPower = p_41164_.getOrDefault(FrostDataCompnents.AURORA_POWER, ItemAuroraPower.EMPTY);
        return auroraPower;
    }

    public static ItemAuroraPower getAuroraPowers(ItemStack p_44832_) {
        ItemAuroraPower auroraPower = getAuroraPowersRaw(p_44832_);
        return auroraPower;
    }

    private static void runIterationOnItem(AuroraPowerVisitor p_44851_, ItemStack p_44852_) {
        if (!p_44852_.isEmpty()) {
            if (true) { // forge: redirect auroraPower logic to allow non-NBT enchants
                for (Object2IntMap.Entry<Holder<AuroraPower>> entry : getAuroraPowers(p_44852_).entrySet()) {
                    p_44851_.accept(entry.getKey().value(), entry.getValue());
                }
                return;
            }

            ItemAuroraPower itemAuroraPower = getAuroraPowersRaw(p_44852_);

            for (Object2IntMap.Entry<Holder<AuroraPower>> entry : itemAuroraPower.entrySet()) {
                AuroraPower auroraPower = entry.getKey().value();

                if (auroraPower != null) {
                    p_44851_.accept(auroraPower, entry.getIntValue());
                }
            }

        }
    }

    private static void runIterationOnInventory(AuroraPowerVisitor p_44854_, Iterable<ItemStack> p_44855_) {
        for (ItemStack itemstack : p_44855_) {
            runIterationOnItem(p_44854_, itemstack);
        }

    }

    public static int getAuroraPowerLevel(AuroraPower p_44837_, LivingEntity p_44838_) {
        Iterable<ItemStack> iterable = p_44837_.getSlotItems(p_44838_).values();
        if (iterable == null) {
            return 0;
        } else {
            int i = 0;

            for (ItemStack itemstack : iterable) {
                int j = getItemAuroraPowerLevel(p_44837_, itemstack);
                if (j > i) {
                    i = j;
                }
            }

            return i;
        }
    }

    @Nullable
    public static Map.Entry<EquipmentSlot, ItemStack> getRandomItemWith(AuroraPower p_44907_, LivingEntity p_44908_) {
        return getRandomItemWith(p_44907_, p_44908_, (p_44941_) -> {
            return true;
        });
    }

    @Nullable
    public static Map.Entry<EquipmentSlot, ItemStack> getRandomItemWith(AuroraPower p_44840_, LivingEntity p_44841_, Predicate<ItemStack> p_44842_) {
        Map<EquipmentSlot, ItemStack> map = p_44840_.getSlotItems(p_44841_);
        if (map.isEmpty()) {
            return null;
        } else {
            List<Map.Entry<EquipmentSlot, ItemStack>> list = Lists.newArrayList();

            for (Map.Entry<EquipmentSlot, ItemStack> entry : map.entrySet()) {
                ItemStack itemstack = entry.getValue();
                if (!itemstack.isEmpty() && getItemAuroraPowerLevel(p_44840_, itemstack) > 0 && p_44842_.test(itemstack)) {
                    list.add(entry);
                }
            }

            return list.isEmpty() ? null : list.get(p_44841_.getRandom().nextInt(list.size()));
        }
    }

    public static int getAuroraPowerCost(RandomSource p_220288_, int p_220289_, int p_220290_, ItemStack p_220291_) {
        Item item = p_220291_.getItem();
        //TODO Crystal Valve
        int i = 1;
        if (i <= 0) {
            return 0;
        } else {
            if (p_220290_ > 15) {
                p_220290_ = 15;
            }

            int j = p_220288_.nextInt(8) + 1 + (p_220290_ >> 1) + p_220288_.nextInt(p_220290_ + 1);
            if (p_220289_ == 0) {
                return Math.max(j / 3, 1);
            } else {
                return p_220289_ == 1 ? j * 2 / 3 + 1 : Math.max(j, p_220290_ * 2);
            }
        }
    }

    public static ItemStack auroraInfusionItem(RandomSource p_220293_, ItemStack p_220294_, int p_220295_, boolean p_220296_) {
        List<AuroraPowerInstance> list = selectAuroraPower(p_220293_, p_220294_, p_220295_, p_220296_);

        for (AuroraPowerInstance auroraPowerinstance : list) {
            auroraInfusion(p_220294_, auroraPowerinstance.auroraPower, auroraPowerinstance.level);
        }

        return p_220294_;
    }

    public static void auroraInfusion(ItemStack stack, AuroraPower p_41664_, int p_41665_) {
        ItemAuroraPower itemAuroraPower = stack.getOrDefault(FrostDataCompnents.AURORA_POWER, ItemAuroraPower.EMPTY);

        ItemAuroraPower.Mutable mutable = new ItemAuroraPower.Mutable(itemAuroraPower);

        mutable.upgrade(p_41664_, (byte) p_41665_);
        stack.set(FrostDataCompnents.AURORA_POWER, mutable.toImmutable());
    }

    private static ResourceLocation getAuroraPowerId(AuroraPower p41664) {
        return AuroraPowers.getRegistry().getKey(p41664);
    }

    public static List<AuroraPowerInstance> selectAuroraPower(RandomSource p_220298_, ItemStack p_220299_, int p_220300_, boolean p_220301_) {
        List<AuroraPowerInstance> list = Lists.newArrayList();
        Item item = p_220299_.getItem();
        //TODO crystal valve
        int i = 1;
        if (i <= 0) {
            return list;
        } else {
            p_220300_ += 1 + p_220298_.nextInt(i / 4 + 1) + p_220298_.nextInt(i / 4 + 1);
            float f = (p_220298_.nextFloat() + p_220298_.nextFloat() - 1.0F) * 0.15F;
            p_220300_ = Mth.clamp(Math.round((float) p_220300_ + (float) p_220300_ * f), 1, Integer.MAX_VALUE);
            List<AuroraPowerInstance> list1 = getAvailableAuroraPowerResults(p_220300_, p_220299_, p_220301_);
            if (!list1.isEmpty()) {
                WeightedRandom.getRandomItem(p_220298_, list1).ifPresent(list::add);

                while (p_220298_.nextInt(50) <= p_220300_) {
                    if (!list.isEmpty()) {
                        filterCompatibleAuroraPowers(p_220299_, list1, Util.lastOf(list));
                    }

                    if (list1.isEmpty()) {
                        break;
                    }

                    WeightedRandom.getRandomItem(p_220298_, list1).ifPresent(list::add);
                    p_220300_ /= 2;
                }
            }

            return list;
        }
    }

    public static void filterCompatibleAuroraPowers(ItemStack stack, List<AuroraPowerInstance> p_44863_, AuroraPowerInstance p_44864_) {
        Iterator<AuroraPowerInstance> iterator = p_44863_.iterator();

        while (iterator.hasNext()) {
            if (!p_44864_.auroraPower.isCompatibleWith(stack, (iterator.next()).auroraPower) && !p_44864_.auroraPower.canApplyItem(stack)) {
                iterator.remove();
            }
        }

    }

    public static List<AuroraPowerInstance> getAvailableAuroraPowerResults(int p_44818_, ItemStack p_44819_, boolean p_44820_) {
        List<AuroraPowerInstance> list = Lists.newArrayList();
        Item item = p_44819_.getItem();

        for (AuroraPower auroraPower : AuroraPowers.getRegistry()) {
            if ((!auroraPower.isTresureEnchant() || p_44820_) && !auroraPower.isOnlyChest() && auroraPower.isCompatibleWith(p_44819_) && auroraPower.canApplyItem(p_44819_)) {
                for (int i = auroraPower.getMaxLevel(); i > auroraPower.getMinLevel() - 1; --i) {
                    if (p_44818_ >= auroraPower.getMinCost(i) && p_44818_ <= auroraPower.getMaxCost(i)) {
                        list.add(new AuroraPowerInstance(auroraPower, i));
                        break;
                    }
                }
            }
        }

        return list;
    }

    public static Component auroraPowerNameWithLevel(AuroraPower auroraPower, int integer) {
        ChatFormatting[] textformatting = new ChatFormatting[]{ChatFormatting.GREEN};
        return Component.translatable("aurora_power." + AuroraPowers.getRegistry().getKey(auroraPower).getNamespace() + "." + AuroraPowers.getRegistry().getKey(auroraPower).getPath()).withStyle(textformatting).append(" ").append(Component.translatable("enchantment.level." + integer).withStyle(textformatting));
    }

    public static Component auroraPowerName(AuroraPower auroraPower) {
        ChatFormatting[] textformatting = new ChatFormatting[]{ChatFormatting.GREEN};
        return Component.translatable("aurora_power." + AuroraPowers.getRegistry().getKey(auroraPower).getNamespace() + "." + AuroraPowers.getRegistry().getKey(auroraPower).getPath()).withStyle(textformatting);
    }

    @FunctionalInterface
    interface AuroraPowerVisitor {
        void accept(AuroraPower p_44945_, int p_44946_);
    }
}
