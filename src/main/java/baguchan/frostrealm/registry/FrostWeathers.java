package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static net.minecraft.resources.ResourceKey.createRegistryKey;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostWeathers {
	public static final ResourceKey<Registry<FrostWeather>> WEATHER_RESOURCE_KEY = createRegistryKey(new ResourceLocation(FrostRealm.MODID, "frost_weather"));


	public static final DeferredRegister<FrostWeather> FROST_WEATHER = DeferredRegister.create(new ResourceLocation(FrostRealm.MODID, "frost_weather"), FrostRealm.MODID);


    public static final Supplier<FrostWeather> BLIZZARD = FROST_WEATHER.register("blizzard", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.9F, 0.9F, 0.9F, 0.25F))));
    public static final Supplier<FrostWeather> PURPLE_FOG = FROST_WEATHER.register("purple_fog", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.6F, 0.0F, 0.6F, 0.385F))));

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
