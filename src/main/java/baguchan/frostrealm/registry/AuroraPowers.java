package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.aurorapower.ArmorPower;
import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.aurorapower.HealthPower;
import baguchan.frostrealm.aurorapower.LightSlasherPower;
import baguchan.frostrealm.aurorapower.WeaponPower;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AuroraPowers {
	public static final ResourceKey<Registry<AuroraPower>> AURORAP_POWER_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FrostRealm.MODID, "aurora_power"));

	public static final IForgeRegistry<AuroraPower> AURORA_POWER_REGISTRY = RegistryManager.ACTIVE.getRegistry(AURORAP_POWER_KEY);

	public static final DeferredRegister<AuroraPower> AURORA_POWER = DeferredRegister.create(new ResourceLocation(FrostRealm.MODID, "aurora_power"), FrostRealm.MODID);

	public static final RegistryObject<AuroraPower> AURORA_HEALTH = AURORA_POWER.register("aurora_health", () -> new HealthPower(new AuroraPower.Properties(AuroraPower.Rarity.VERY_RARE, 4), new EquipmentSlot[]{EquipmentSlot.CHEST}).addAttributesModifier(Attributes.MAX_HEALTH, "0606cc03-9eae-4155-891f-fa8b963eeef5", 2.0D, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<AuroraPower> AURORA_PROTECTION = AURORA_POWER.register("aurora_protection", () -> new ArmorPower(new AuroraPower.Properties(AuroraPower.Rarity.COMMON, 3), new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}));
	public static final RegistryObject<AuroraPower> AURORA_SHAPER = AURORA_POWER.register("aurora_shaper", () -> new WeaponPower(new AuroraPower.Properties(AuroraPower.Rarity.COMMON, 5), new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
	public static final RegistryObject<AuroraPower> LIGHT_SLASHER = AURORA_POWER.register("light_slasher", () -> new LightSlasherPower(new AuroraPower.Properties(AuroraPower.Rarity.RARE, 3), new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

	private static Supplier<IForgeRegistry<AuroraPower>> registry;

	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
		registry = event.create(new RegistryBuilder<AuroraPower>()
				.addCallback(AuroraPower.class)
				.setName(new ResourceLocation(FrostRealm.MODID, "aurora_power"))
				.setDefaultKey(new ResourceLocation(FrostRealm.MODID, "aurora_health")));
	}

	public static Supplier<IForgeRegistry<AuroraPower>> getRegistry() {
		if (registry == null) {
			throw new IllegalStateException("Registry not yet initialized");
		}
		return registry;
	}
}