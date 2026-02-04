package baguchan.frostrealm.data.resource.registries;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.entity.WolfflueVariant;
import baguchan.frostrealm.registry.FrostBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class WolfflueVariants {
    public static final ResourceKey<Registry<WolfflueVariant>> WOLFFLUE_VARIANT_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "wolfflue_variant"));

    public static final ResourceKey<WolfflueVariant> FROST = createKey("frost");
    public static final ResourceKey<WolfflueVariant> SNOW = createKey("snow");
    public static final ResourceKey<WolfflueVariant> YUZUKI = createKey("yuzuki");
    public static final ResourceKey<WolfflueVariant> DEFAULT = FROST;

    private static ResourceKey<WolfflueVariant> createKey(String name) {
        return ResourceKey.create(WOLFFLUE_VARIANT_REGISTRY_KEY, Identifier.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    static void register(BootstrapContext<WolfflueVariant> context, ResourceKey<WolfflueVariant> key, String name, ResourceKey<Biome> biomeResourceKey) {
        register(context, key, name, HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biomeResourceKey)));
    }

    static void register(BootstrapContext<WolfflueVariant> context, ResourceKey<WolfflueVariant> key, String name, TagKey<Biome> biomeTag) {
        register(context, key, name, context.lookup(Registries.BIOME).getOrThrow(biomeTag));
    }

    static void register(BootstrapContext<WolfflueVariant> context, ResourceKey<WolfflueVariant> key, String name, HolderSet<Biome> biomeHolderSet) {
        Identifier resourcelocation = FrostRealm.prefix("entity/wolfflue/" + name);
        Identifier resourcelocation2 = FrostRealm.prefix("entity/wolfflue/" + name + "_angry");
        Identifier resourcelocation3 = FrostRealm.prefix("entity/wolfflue/" + name + "_baby");
        Identifier resourcelocation4 = FrostRealm.prefix("entity/wolfflue/" + name + "_baby_angry");

        context.register(key, new WolfflueVariant(resourcelocation, resourcelocation2, resourcelocation3, resourcelocation4, biomeHolderSet));
    }

    public static Holder<WolfflueVariant> getSpawnVariant(RegistryAccess p_332694_, Holder<Biome> p_332773_) {
        Registry<WolfflueVariant> registry = p_332694_.lookupOrThrow(WOLFFLUE_VARIANT_REGISTRY_KEY);
        return registry.listElements()
                .filter(p_332674_ -> p_332674_.value().biomes().contains(p_332773_))
                .findFirst()
                .or(() -> registry.get(DEFAULT))
                .or(registry::getAny)
                .orElseThrow();
    }

    public static void bootstrap(BootstrapContext<WolfflueVariant> context) {
        register(context, FROST, "wolfflue", FrostBiomes.FRIGID_FOREST);
        register(context, SNOW, "wolfflue_snow", FrostBiomes.TUNDRA);
        register(context, YUZUKI, "wolfflue_yuzuki", HolderSet.empty());
    }
}
