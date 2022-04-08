package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostWeathers {

	public static final DeferredRegister<FrostWeather> FROST_WEATHER = DeferredRegister.create(new ResourceLocation(FrostRealm.MODID, "frost_weather"), FrostRealm.MODID);


	public static final RegistryObject<FrostWeather> BLIZZARD = FROST_WEATHER.register("blizzard", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(1.0F, 1.0F, 1.0F, 0.2F))));
	public static final RegistryObject<FrostWeather> PURPLE_WEATHER = FROST_WEATHER.register("tough", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.9F, 0.1F, 0.9F, 0.4F))));

	private static Supplier<IForgeRegistry<FrostWeather>> registry;

	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
		registry = event.create(new RegistryBuilder<FrostWeather>()
				.setType(FrostWeather.class)
				.setName(new ResourceLocation(FrostRealm.MODID, "frost_weather"))
				.setDefaultKey(new ResourceLocation(FrostRealm.MODID, "blizzard")));
	}

	public static Supplier<IForgeRegistry<FrostWeather>> getRegistry() {
		if (registry == null) {
			throw new IllegalStateException("Registry not yet initialized");
		}
		return registry;
	}
}
