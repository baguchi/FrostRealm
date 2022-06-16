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


	public static final RegistryObject<FrostWeather> BLIZZARD = FROST_WEATHER.register("blizzard", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.9F, 0.9F, 0.9F, 0.25F))));
	public static final RegistryObject<FrostWeather> PURPLE_FOG = FROST_WEATHER.register("purple_fog", () -> new FrostWeather(new FrostWeather.Properties(new FrostWeather.FogProperties(0.6F, 0.0F, 0.6F, 0.385F))));

	private static Supplier<IForgeRegistry<FrostWeather>> registry;

	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
		registry = event.create(new RegistryBuilder<FrostWeather>()
				.addCallback(FrostWeather.class)
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
