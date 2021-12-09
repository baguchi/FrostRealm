package baguchan.frostrealm;


import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.client.ClientRegistrar;
import baguchan.frostrealm.client.event.ClientColdHUDEvent;
import baguchan.frostrealm.message.ChangedColdMessage;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostCarvers;
import baguchan.frostrealm.registry.FrostNoiseGeneratorSettings;
import baguchan.frostrealm.world.FrostChunkGenerator;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import baguchan.frostrealm.world.gen.FrostTreeFeatures;
import baguchan.frostrealm.world.placement.FrostOrePlacements;
import baguchan.frostrealm.world.placement.FrostPlacements;
import net.minecraft.core.Registry;
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

	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
			.networkProtocolVersion(() -> NETWORK_PROTOCOL)
			.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
			.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
			.simpleChannel();

	public FrostRealm() {
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

		modbus.addListener(this::setup);

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
		MinecraftForge.EVENT_BUS.register(this);
		this.setupMessages();
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostBlocks.burnables();
			FrostTreeFeatures.init();
			FrostConfiguredFeatures.init();
			FrostPlacements.init();
			FrostOrePlacements.init();
			FrostCarvers.registerConfiguredCarvers();
			FrostNoiseGeneratorSettings.init();
			Registry.register(Registry.CHUNK_GENERATOR, FrostRealm.prefix("chunk_generator"), FrostChunkGenerator.CODEC);
		});
		MinecraftForge.EVENT_BUS.register(new ClientColdHUDEvent());
	}

	private void setupMessages() {
		CHANNEL.messageBuilder(ChangedColdMessage.class, 0)
				.encoder(ChangedColdMessage::writeToPacket).decoder(ChangedColdMessage::readFromPacket)
				.consumer(ChangedColdMessage::handle)
				.add();
	}

	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(FrostRealm.MODID, name.toLowerCase(Locale.ROOT));
	}

}
