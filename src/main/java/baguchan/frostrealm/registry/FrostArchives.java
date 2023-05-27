package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.frost_archive.FrostArchive;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.Optional;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostArchives {

	public static final DeferredRegister<FrostArchive> FROST_ARCHIVE = DeferredRegister.create(new ResourceLocation(FrostRealm.MODID, "frost_archive"), FrostRealm.MODID);


	public static final RegistryObject<FrostArchive> FIRST_STEP = FROST_ARCHIVE.register("first_step", () -> new FrostArchive("frost_archive.frostrealm.first_step", 3, Optional.empty()));
	private static Supplier<IForgeRegistry<FrostArchive>> registry;

	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
		registry = event.create(new RegistryBuilder<FrostArchive>()
				.addCallback(FrostWeather.class)
				.setName(new ResourceLocation(FrostRealm.MODID, "frost_archive"))
				.setDefaultKey(new ResourceLocation(FrostRealm.MODID, "first_step")));
	}

	public static Supplier<IForgeRegistry<FrostArchive>> getRegistry() {
		if (registry == null) {
			throw new IllegalStateException("Registry not yet initialized");
		}
		return registry;
	}
}
