package baguchan.frostrealm.registry;

import baguchan.frostrealm.data.generator.FrostEquipmentAssets;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public class FrostEquippable {
    public static Equippable saddle() {
        HolderGetter<EntityType<?>> holdergetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ENTITY_TYPE);
        return Equippable.builder(EquipmentSlot.SADDLE)
                .setEquipSound(SoundEvents.HORSE_SADDLE)
                .setAsset(FrostEquipmentAssets.WOLFFLUE_SADDLE)
                .setAllowedEntities(holdergetter.getOrThrow(FrostTags.EntityTypes.EQUIPPABLE_FROST_SADDLE))
                .setEquipOnInteract(true)
                .setCanBeSheared(true)
                .setShearingSound(SoundEvents.SADDLE_UNEQUIP)
                .build();
    }
}
