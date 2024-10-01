package baguchan.frostrealm.data.resource.registries;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.registry.FrostDamageType;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class AttachableCrystals {
    public static final ResourceKey<Registry<AttachableCrystal>> ATTACHABLE_CRYSTAL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "attachable_crystal"));

    public static final ResourceKey<AttachableCrystal> VENOM = createKey("venom");

    private static ResourceKey<AttachableCrystal> createKey(String name) {
        return ResourceKey.create(ATTACHABLE_CRYSTAL_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    public static Optional<Holder.Reference<AttachableCrystal>> getFromIngredient(HolderLookup.Provider p_335701_, ItemStack p_267327_) {
        return p_335701_.lookupOrThrow(ATTACHABLE_CRYSTAL_REGISTRY_KEY).listElements().filter(p_266876_ -> p_267327_.is(p_266876_.value().getItem())).findFirst();
    }

    static void register(BootstrapContext<AttachableCrystal> context, ResourceKey<AttachableCrystal> key, TagKey<Item> tagKey, float damage, int usage, ResourceKey<DamageType> damageTypeResourceKey) {
        context.register(key, new AttachableCrystal(context.lookup(Registries.ITEM).getOrThrow(tagKey), damage, usage, context.lookup(Registries.DAMAGE_TYPE).getOrThrow(damageTypeResourceKey)));
    }

    static void register(BootstrapContext<AttachableCrystal> context, ResourceKey<AttachableCrystal> key, ResourceKey<Item> item, float damage, int usage, ResourceKey<DamageType> damageTypeResourceKey) {
        context.register(key, new AttachableCrystal(HolderSet.direct(context.lookup(Registries.ITEM).getOrThrow(item)), damage, usage, context.lookup(Registries.DAMAGE_TYPE).getOrThrow(damageTypeResourceKey)));
    }

    public static void bootstrap(BootstrapContext<AttachableCrystal> context) {
        register(context, VENOM, FrostItems.UNSTABLE_VENOM_CRYSTAL.getKey(), 2.0F, 30, FrostDamageType.VENOM);
    }
}
