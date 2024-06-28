package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Optional;
import java.util.function.Supplier;

import static net.minecraft.resources.ResourceKey.createRegistryKey;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FrostWeathers {
	public static final ResourceKey<Registry<FrostWeather>> WEATHER_RESOURCE_KEY = createRegistryKey(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_weather"));


	public static final DeferredRegister<FrostWeather> FROST_WEATHER = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_weather"), FrostRealm.MODID);

	public static final Supplier<FrostWeather> NOPE = FROST_WEATHER.register("nope", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(1.0F, 1.0F, 1.0F, 0.9F), Optional.empty(), Optional.empty())));
	public static final Supplier<FrostWeather> BLIZZARD = FROST_WEATHER.register("blizzard", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.9F, 0.9F, 0.9F, 0.25F), Optional.of(FrostSounds.BLIZZARD_AMBIENT.get()), Optional.of(FrostTags.Biomes.HOT_BIOME))));
	public static final Supplier<FrostWeather> PURPLE_FOG = FROST_WEATHER.register("purple_fog", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.6F, 0.0F, 0.6F, 0.385F), Optional.empty(), Optional.empty())));

    private static Registry<FrostWeather> registry;

	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
        registry = event.create(new RegistryBuilder<>(WEATHER_RESOURCE_KEY));
	}

    public static Registry<FrostWeather> getRegistry() {
		if (registry == null) {
			throw new IllegalStateException("Registry not yet initialized");
		}
		return registry;
	}
}
