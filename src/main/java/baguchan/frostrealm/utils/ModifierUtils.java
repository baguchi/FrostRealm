package baguchan.frostrealm.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class ModifierUtils {
    public static final String TAG_MODIFIER_LIST = "CrystalModifierList";

    /*
     * Set Modifier Scale when take the item with has more material
     */

    public static Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot p_41639_) {
        CompoundTag compoundTag = stack.getTag();
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (stack.hasTag() && compoundTag.contains(TAG_MODIFIER_LIST, 9)) {

            ListTag listtag = compoundTag.getList(TAG_MODIFIER_LIST, 10);

            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                if (!compoundtag.contains("Slot", 8) || compoundtag.getString("Slot").equals(p_41639_.getName())) {
                    Optional<Attribute> optional = BuiltInRegistries.ATTRIBUTE.getOptional(ResourceLocation.tryParse(compoundtag.getString("AttributeName")));
                    if (optional.isPresent()) {
                        AttributeModifier attributemodifier = AttributeModifier.load(compoundtag);
                        if (attributemodifier != null && attributemodifier.getId().getLeastSignificantBits() != 0L && attributemodifier.getId().getMostSignificantBits() != 0L) {
                            multimap.put(optional.get(), attributemodifier);
                        }
                    }
                }
            }
        }
        return multimap;
    }

    public static void addAttributeModifier(ItemStack stack, Attribute p_41644_, AttributeModifier p_41645_, @Nullable EquipmentSlot p_41646_) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        if (!compoundTag.contains(TAG_MODIFIER_LIST, 9)) {
            compoundTag.put(TAG_MODIFIER_LIST, new ListTag());
        }

        ListTag listtag = compoundTag.getList(TAG_MODIFIER_LIST, 10);
        CompoundTag compoundtag = p_41645_.save();
        compoundtag.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(p_41644_).toString());
        if (p_41646_ != null) {
            compoundtag.putString("Slot", p_41646_.getName());
        }

        listtag.add(compoundtag);
    }

}
