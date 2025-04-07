package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class WolfflueArmorItem extends Item {
    private final ResourceLocation textureLocation;

    public WolfflueArmorItem(ArmorMaterial armorMaterial, Item.Properties p_316341_) {
        super(p_316341_.wolfArmor(armorMaterial));
        ResourceLocation resourcelocation = armorMaterial.assetId().location().withPath(p_323717_ -> "textures/entity/wolfflue/armor/" + p_323717_);
        this.textureLocation = resourcelocation.withSuffix(".png");

    }

    public ResourceLocation getTexture() {
        return this.textureLocation;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return stack.is(FrostItems.WOLFFLUE_FROST_BOAR_ARMOR);
    }
}
