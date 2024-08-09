package baguchan.frostrealm.item.component;

import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.registry.AuroraPowers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ItemAuroraPower implements TooltipProvider {
    public static final ItemAuroraPower EMPTY = new ItemAuroraPower(new Object2IntOpenHashMap(), true);
    public static final int MAX_LEVEL = 255;
    private static final Codec<Integer> LEVEL_CODEC = Codec.intRange(0, 255);
    private static final Codec<Object2IntOpenHashMap<Holder<AuroraPower>>> LEVELS_CODEC;
    private static final Codec<ItemAuroraPower> FULL_CODEC;
    public static final Codec<ItemAuroraPower> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemAuroraPower> STREAM_CODEC;
    final Object2IntOpenHashMap<Holder<AuroraPower>> enchantments;
    final boolean showInTooltip;

    ItemAuroraPower(Object2IntOpenHashMap<Holder<AuroraPower>> p_341287_, boolean p_330219_) {
        this.enchantments = p_341287_;
        this.showInTooltip = p_330219_;
        ObjectIterator var3 = p_341287_.object2IntEntrySet().iterator();

        Object2IntMap.Entry entry;
        int i;
        do {
            if (!var3.hasNext()) {
                return;
            }

            entry = (Object2IntMap.Entry) var3.next();
            i = entry.getIntValue();
        } while (i >= 0 && i <= 255);

        String var10002 = String.valueOf(entry.getKey());
        throw new IllegalArgumentException("Enchantment " + var10002 + " has invalid level " + i);
    }

    public int getLevel(AuroraPower p_330552_) {
        return this.enchantments.getInt(getHolder(p_330552_));
    }

    public void addToTooltip(Item.TooltipContext p_341290_, Consumer<Component> p_331119_, TooltipFlag p_330400_) {
        if (this.showInTooltip) {
            HolderLookup.Provider holderlookup$provider = p_341290_.registries();
            Registry<AuroraPower> holderset = AuroraPowers.getRegistry();
            Iterator<AuroraPower> var6 = holderset.iterator();

            while (var6.hasNext()) {
                AuroraPower holder = var6.next();
                int i = this.enchantments.getInt(holder);
                if (i > 0) {
                    p_331119_.accept(holder.getFullname(i));
                }
            }

            ObjectIterator var9 = this.enchantments.object2IntEntrySet().iterator();

            while (var9.hasNext()) {
                Object2IntMap.Entry<Holder<AuroraPower>> entry = (Object2IntMap.Entry) var9.next();
                Holder<AuroraPower> holder1 = entry.getKey();
                if (holderset.containsValue(holder1.value())) {
                    p_331119_.accept(holder1.value().getFullname(entry.getIntValue()));
                }
            }
        }

    }

    private static <T> HolderSet<T> getTagOrEmpty(@Nullable HolderLookup.Provider p_341186_, ResourceKey<Registry<T>> p_341113_, TagKey<T> p_341409_) {
        if (p_341186_ != null) {
            Optional<HolderSet.Named<T>> optional = p_341186_.lookupOrThrow(p_341113_).get(p_341409_);
            if (optional.isPresent()) {
                return optional.get();
            }
        }

        return HolderSet.direct(new Holder[0]);
    }

    public ItemAuroraPower withTooltip(boolean p_335616_) {
        return new ItemAuroraPower(this.enchantments, p_335616_);
    }

    public Set<Holder<AuroraPower>> keySet() {
        return Collections.unmodifiableSet(this.enchantments.keySet());
    }

    public Set<Object2IntMap.Entry<Holder<AuroraPower>>> entrySet() {
        return Collections.unmodifiableSet(this.enchantments.object2IntEntrySet());
    }

    public int size() {
        return this.enchantments.size();
    }

    public boolean isEmpty() {
        return this.enchantments.isEmpty();
    }

    public boolean equals(Object p_331697_) {
        if (this == p_331697_) {
            return true;
        } else {
            boolean var10000;
            if (p_331697_ instanceof ItemAuroraPower itemenchantments) {
                var10000 = this.showInTooltip == itemenchantments.showInTooltip && this.enchantments.equals(itemenchantments.enchantments);
            } else {
                var10000 = false;
            }

            return var10000;
        }
    }

    public int hashCode() {
        int i = this.enchantments.hashCode();
        return 31 * i + (this.showInTooltip ? 1 : 0);
    }

    public String toString() {
        String var10000 = String.valueOf(this.enchantments);
        return "ItemAuroraPower{enchantments=" + var10000 + ", showInTooltip=" + this.showInTooltip + "}";
    }

    static {
        LEVELS_CODEC = Codec.unboundedMap(AuroraPowers.getRegistry().holderByNameCodec(), LEVEL_CODEC).xmap(Object2IntOpenHashMap::new, Function.identity());
        FULL_CODEC = RecordCodecBuilder.create((p_337961_) -> {
            return p_337961_.group(LEVELS_CODEC.fieldOf("levels").forGetter((p_340785_) -> {
                return p_340785_.enchantments;
            }), Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter((p_331891_) -> {
                return p_331891_.showInTooltip;
            })).apply(p_337961_, ItemAuroraPower::new);
        });
        CODEC = Codec.withAlternative(FULL_CODEC, LEVELS_CODEC, (p_340783_) -> {
            return new ItemAuroraPower(p_340783_, true);
        });
        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.map(Object2IntOpenHashMap::new, ByteBufCodecs.holderRegistry(AuroraPowers.getRegistry().key()), ByteBufCodecs.VAR_INT), (p_340784_) -> {
            return p_340784_.enchantments;
        }, ByteBufCodecs.BOOL, (p_330450_) -> {
            return p_330450_.showInTooltip;
        }, ItemAuroraPower::new);
    }

    public static Holder<AuroraPower> getHolder(AuroraPower mobEnchant) {
        return AuroraPowers.getRegistry().wrapAsHolder(mobEnchant);
    }

    public static class Mutable {
        private final Object2IntOpenHashMap<Holder<AuroraPower>> enchantments = new Object2IntOpenHashMap();
        private final boolean showInTooltip;

        public Mutable(ItemAuroraPower p_330722_) {
            this.enchantments.putAll(p_330722_.enchantments);
            this.showInTooltip = p_330722_.showInTooltip;
        }

        public void set(AuroraPower p_331872_, int p_330832_) {
            if (p_330832_ <= 0) {
                this.enchantments.removeInt(getHolder(p_331872_));
            } else {
                this.enchantments.put(getHolder(p_331872_), Math.min(p_330832_, 255));
            }

        }

        public void upgrade(AuroraPower p_330536_, int p_331153_) {
            if (p_331153_ > 0) {
                this.enchantments.merge(getHolder(p_330536_), Math.min(p_331153_, 255), Integer::max);
            }

        }

        public void removeIf(Predicate<Holder<AuroraPower>> p_332079_) {
            this.enchantments.keySet().removeIf(p_332079_);
        }

        public int getLevel(AuroraPower p_331330_) {
            return this.enchantments.getOrDefault(getHolder(p_331330_), 0);
        }

        public Set<Holder<AuroraPower>> keySet() {
            return this.enchantments.keySet();
        }

        public ItemAuroraPower toImmutable() {
            return new ItemAuroraPower(this.enchantments, this.showInTooltip);
        }
    }
}