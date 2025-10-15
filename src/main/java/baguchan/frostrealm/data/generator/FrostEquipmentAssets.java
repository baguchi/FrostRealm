package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public interface FrostEquipmentAssets {
    ResourceKey<EquipmentAsset> ASTRIUM = createId("astrium");
    ResourceKey<EquipmentAsset> FROST_BOAR_FUR = createId("frost_boar_fur");
    ResourceKey<EquipmentAsset> YETI_FUR = createId("yeti_fur");
    ResourceKey<EquipmentAsset> WOLFFLUE_SADDLE = createId("wolfflue_saddle");

    static ResourceKey<EquipmentAsset> createId(String p_386630_) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, p_386630_));
    }
}
