package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class FrostDamageSources {
    public static final ResourceKey<DamageType> VENOM = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "venom"));
    public static final ResourceKey<DamageType> VENOM_BALL = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "venom_ball"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(VENOM, new DamageType("venom", 0.1F));
        context.register(VENOM_BALL, new DamageType("venom_ball", 0.1F));
    }
}
