package baguchan.frostrealm.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class WolfflueArmorItem extends AnimalArmorItem {
    private final ResourceLocation textureLocation;

    public WolfflueArmorItem(ArmorMaterial armorMaterial, Item.Properties p_316341_) {
        super(armorMaterial, AnimalArmorItem.BodyType.CANINE, p_316341_);
        ResourceLocation resourcelocation = armorMaterial.assetId().location().withPath(p_323717_ -> "textures/entity/wolfflue/armor/" + p_323717_);
        this.textureLocation = resourcelocation.withSuffix(".png");

    }

    public ResourceLocation getTexture() {
        return this.textureLocation;
    }
    @Override
    public SoundEvent getBreakingSound() {
        return SoundEvents.WOLF_ARMOR_BREAK;
    }
}
