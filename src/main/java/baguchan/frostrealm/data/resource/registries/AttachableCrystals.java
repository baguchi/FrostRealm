package baguchan.frostrealm.data.resource.registries;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.registry.FrostEffects;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class AttachableCrystals {
    public static final ResourceKey<Registry<AttachableCrystal>> ATTACHABLE_CRYSTAL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "attachable_crystal"));

    public static final ResourceKey<AttachableCrystal> VENOM = createKey("venom");
    public static final ResourceKey<AttachableCrystal> FROST = createKey("frost");
    private static ResourceKey<AttachableCrystal> createKey(String name) {
        return ResourceKey.create(ATTACHABLE_CRYSTAL_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    public static Optional<Holder.Reference<AttachableCrystal>> getFromIngredient(HolderLookup.Provider p_335701_, ItemStack p_267327_) {
        return p_335701_.lookupOrThrow(ATTACHABLE_CRYSTAL_REGISTRY_KEY).listElements().filter(p_266876_ -> p_267327_.is(p_266876_.value().getItem())).findFirst();
    }

    public static Optional<Holder.Reference<AttachableCrystal>> getIngredient(HolderLookup.Provider p_335701_, Holder<AttachableCrystal> attachableCrystal) {
        return p_335701_.lookupOrThrow(ATTACHABLE_CRYSTAL_REGISTRY_KEY).listElements().filter(p_266876_ -> attachableCrystal.is(p_266876_)).findFirst();
    }

    static void register(BootstrapContext<AttachableCrystal> context, ResourceKey<AttachableCrystal> key, Holder<Item> itemHolder, float damage, int usage) {
        context.register(key, new AttachableCrystal(itemHolder, damage, usage, Optional.empty()));
    }

    static void register(BootstrapContext<AttachableCrystal> context, ResourceKey<AttachableCrystal> key, Holder<Item> itemHolder, float damage, int usage, MobEffectInstance mobEffectInstance) {
        context.register(key, new AttachableCrystal(itemHolder, damage, usage, Optional.of(mobEffectInstance)));
    }


    public static void bootstrap(BootstrapContext<AttachableCrystal> context) {
        register(context, VENOM, FrostItems.UNSTABLE_VENOM_CRYSTAL, 1.0F, 64, new MobEffectInstance(MobEffects.POISON, 200));
        register(context, FROST, FrostItems.FROST_CRYSTAL, 1.0F, 64, new MobEffectInstance(FrostEffects.COLD_SENSITIVITY, 200));
    }
}
