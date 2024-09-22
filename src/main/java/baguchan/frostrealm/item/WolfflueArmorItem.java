package baguchan.frostrealm.item;

import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WolfflueArmorItem extends AnimalArmorItem {
    private final Supplier<ItemAttributeModifiers> defaultModifiers;

    private final ResourceLocation textureLocation;
    @Nullable
    private final ResourceLocation overlayTextureLocation;

    public WolfflueArmorItem(Holder<ArmorMaterial> armorMaterial, boolean hasOverlay, Item.Properties p_316341_) {
        super(armorMaterial, AnimalArmorItem.BodyType.CANINE, hasOverlay, p_316341_);
        ResourceLocation resourcelocation = armorMaterial.unwrapKey().orElseThrow().location().withPath(p_323717_ -> "textures/entity/wolfflue/wolfflue_armor_" + p_323717_);
        this.textureLocation = resourcelocation.withSuffix(".png");
        if (hasOverlay) {
            this.overlayTextureLocation = resourcelocation.withSuffix("_overlay.png");
        } else {
            this.overlayTextureLocation = null;
        }


        ResourceLocation resourcelocation2 = ResourceLocation.withDefaultNamespace("armor." + Type.BODY.getName());


        this.defaultModifiers = Suppliers.memoize(
                () -> {
                    int i = armorMaterial.value().getDefense(Type.BODY) * 2;
                    float f = armorMaterial.value().toughness() * 2;
                    ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
                    EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(Type.BODY.getSlot());
                    itemattributemodifiers$builder.add(
                            Attributes.ARMOR, new AttributeModifier(resourcelocation2, (double) i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup
                    );
                    itemattributemodifiers$builder.add(
                            Attributes.ARMOR_TOUGHNESS,
                            new AttributeModifier(resourcelocation2, (double) f, AttributeModifier.Operation.ADD_VALUE),
                            equipmentslotgroup
                    );
                    float f1 = armorMaterial.value().knockbackResistance() * 2;
                    if (f1 > 0.0F) {
                        itemattributemodifiers$builder.add(
                                Attributes.KNOCKBACK_RESISTANCE,
                                new AttributeModifier(resourcelocation2, (double) f1, AttributeModifier.Operation.ADD_VALUE),
                                equipmentslotgroup
                        );
                    }

                    return itemattributemodifiers$builder.build();
                }
        );
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }

    @Override
    public int getDefense() {
        return super.getDefense() * 2;
    }

    @Override
    public float getToughness() {
        return super.getToughness() * 2;
    }

    public ResourceLocation getTexture() {
        return this.textureLocation;
    }

    @Nullable
    public ResourceLocation getOverlayTexture() {
        return this.overlayTextureLocation;
    }

    @Override
    public SoundEvent getBreakingSound() {
        return SoundEvents.WOLF_ARMOR_BREAK;
    }

    @Override
    public boolean isEnchantable(ItemStack p_341697_) {
        return true;
    }
}
