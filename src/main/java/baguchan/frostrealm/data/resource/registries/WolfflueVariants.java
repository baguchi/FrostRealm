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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class WolfflueVariants {
    public static final ResourceKey<Registry<WolfflueVariant>> WOLFFLUE_VARIANT_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "wolfflue_variant"));

    public static final ResourceKey<WolfflueVariant> FROST = createKey("frost");
    public static final ResourceKey<WolfflueVariant> SNOW = createKey("snow");
    public static final ResourceKey<WolfflueVariant> DEFAULT = FROST;

    private static ResourceKey<WolfflueVariant> createKey(String name) {
        return ResourceKey.create(WOLFFLUE_VARIANT_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    static void register(BootstrapContext<WolfflueVariant> context, ResourceKey<WolfflueVariant> key, String name, ResourceKey<Biome> biomeResourceKey) {
        register(context, key, name, HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biomeResourceKey)));
    }

    static void register(BootstrapContext<WolfflueVariant> context, ResourceKey<WolfflueVariant> key, String name, TagKey<Biome> biomeTag) {
        register(context, key, name, context.lookup(Registries.BIOME).getOrThrow(biomeTag));
    }

    static void register(BootstrapContext<WolfflueVariant> context, ResourceKey<WolfflueVariant> key, String name, HolderSet<Biome> biomeHolderSet) {
        ResourceLocation resourcelocation = FrostRealm.prefix("entity/wolfflue/" + name);
        ResourceLocation resourcelocation2 = FrostRealm.prefix("entity/wolfflue/" + name + "_angry");
        context.register(key, new WolfflueVariant(resourcelocation, resourcelocation2, biomeHolderSet));
    }

    public static Holder<WolfflueVariant> getSpawnVariant(RegistryAccess access, Holder<Biome> biomeHolder) {
        Registry<WolfflueVariant> registry = access.registryOrThrow(WOLFFLUE_VARIANT_REGISTRY_KEY);
        return registry.holders()
                .filter(p_332674_ -> p_332674_.value().biomes().contains(biomeHolder))
                .findFirst()
                .or(() -> registry.getHolder(DEFAULT))
                .or(registry::getAny)
                .orElseThrow();
    }

    public static void bootstrap(BootstrapContext<WolfflueVariant> context) {
        register(context, FROST, "wolfflue", FrostBiomes.FRIGID_FOREST);
        register(context, SNOW, "wolfflue_snow", FrostBiomes.TUNDRA);
    }
}
