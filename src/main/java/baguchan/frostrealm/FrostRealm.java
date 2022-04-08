package baguchan.frostrealm;


import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.client.ClientRegistrar;
import baguchan.frostrealm.message.ChangeWeatherTimeEvent;
import baguchan.frostrealm.message.ChangedColdMessage;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.world.caver.FrostConfiguredWorldCarvers;
import baguchan.frostrealm.world.gen.FrostTreeFeatures;
import baguchan.frostrealm.world.placement.FrostOrePlacements;
import baguchan.frostrealm.world.placement.FrostPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

@Mod("frostrealm")
public class FrostRealm {
	public static final Logger LOGGER = LogManager.getLogger("frostrealm");

	public static final String MODID = "frostrealm";

	public static final String NETWORK_PROTOCOL = "2";

	public static final Capability<FrostLivingCapability> FROST_LIVING_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<FrostWeatherCapability> FROST_WEATHER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
			.networkProtocolVersion(() -> NETWORK_PROTOCOL)
			.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
			.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
			.simpleChannel();

	public FrostRealm() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		FrostWeathers.FROST_WEATHER.register(modBus);
		FrostBiomes.BIOMES.register(modBus);
		FrostCarvers.WORLD_CARVER.register(modBus);
		FrostBlocks.BLOCKS.register(modBus);
		FrostEntities.ENTITIES.register(modBus);

		FrostItems.ITEMS.register(modBus);
		FrostBlockEntitys.BLOCK_ENTITIES.register(modBus);

		modBus.addListener(this::setup);


		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
		MinecraftForge.EVENT_BUS.register(this);
		this.setupMessages();
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostBlocks.burnables();
			FrostTreeFeatures.init();
			FrostPlacements.init();
			FrostOrePlacements.init();
			FrostNoiseGeneratorSettings.init();
			FrostConfiguredWorldCarvers.init();
		});
		FrostBiomes.addBiomeTypes();
	}

	private void setupMessages() {
		CHANNEL.messageBuilder(ChangedColdMessage.class, 0)
				.encoder(ChangedColdMessage::writeToPacket).decoder(ChangedColdMessage::readFromPacket)
				.consumer(ChangedColdMessage::handle)
				.add();
		CHANNEL.messageBuilder(ChangeWeatherTimeEvent.class, 1)
				.encoder(ChangeWeatherTimeEvent::writeToPacket).decoder(ChangeWeatherTimeEvent::readFromPacket)
				.consumer(ChangeWeatherTimeEvent::handle)
				.add();
	}

	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(FrostRealm.MODID, name.toLowerCase(Locale.ROOT));
	}

	public static String prefixOnString(String name) {
		return FrostRealm.MODID + ":" + name.toLowerCase(Locale.ROOT);
	}

}
