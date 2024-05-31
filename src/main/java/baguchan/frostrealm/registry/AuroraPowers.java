package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.aurorapower.ArmorPower;
import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.aurorapower.LightSlasherPower;
import baguchan.frostrealm.aurorapower.WeaponPower;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AuroraPowers {
    public static final ResourceKey<Registry<AuroraPower>> AURORA_POWER_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FrostRealm.MODID, "aurora_power"));

    public static final DeferredRegister<AuroraPower> AURORA_POWER = DeferredRegister.create(new ResourceLocation(FrostRealm.MODID, "aurora_power"), FrostRealm.MODID);

    public static final Supplier<AuroraPower> AURORA_PROTECTION = AURORA_POWER.register("aurora_protection", () -> new ArmorPower(new AuroraPower.Properties(AuroraPower.Rarity.COMMON, 3), new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}));

    public static final Supplier<AuroraPower> AURORA_SHAPER = AURORA_POWER.register("aurora_shaper", () -> new WeaponPower(new AuroraPower.Properties(AuroraPower.Rarity.COMMON, 5), new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final Supplier<AuroraPower> CRYSTAL_SLASHER = AURORA_POWER.register("crystal_slasher", () -> new LightSlasherPower(new AuroraPower.Properties(AuroraPower.Rarity.RARE, 3), new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

    private static Registry<AuroraPower> registry;

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        registry = event.create(new RegistryBuilder<>(AURORA_POWER_KEY));
    }

    public static Registry<AuroraPower> getRegistry() {
        if (registry == null) {
            throw new IllegalStateException("Registry not yet initialized");
        }
        return registry;
    }
}