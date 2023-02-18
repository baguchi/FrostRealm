package baguchan.frostrealm;


import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.client.ClientRegistrar;
import baguchan.frostrealm.command.FrostWeatherCommand;
import baguchan.frostrealm.message.*;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.world.gen.FrostTreeFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
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
		FrostFeatures.FEATURES.register(modBus);
		FrostSounds.SOUND_EVENTS.register(modBus);
		FrostMenuTypes.MENU_TYPES.register(modBus);
		FrostBlocks.BLOCKS.register(modBus);
		FrostEntities.ENTITIES.register(modBus);
		AuroraPowers.AURORA_POWER.register(modBus);
		FrostItems.ITEMS.register(modBus);
		FrostEffects.MOB_EFFECTS.register(modBus);
		FrostEffects.POTION.register(modBus);
		FrostRecipes.RECIPE_SERIALIZERS.register(modBus);
		FrostBlockEntitys.BLOCK_ENTITIES.register(modBus);
		modBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostBlocks.burnables();
			FrostTreeFeatures.init();
			this.setupMessages();
		});
		FrostBiomes.addBiomeTypes();
	}

	public static <MSG> void sendMSGToAll(MSG message) {
		for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
			sendNonLocal(message, player);
		}
	}

	public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
		CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	private void setupMessages() {
		CHANNEL.messageBuilder(ChangedColdMessage.class, 0)
				.encoder(ChangedColdMessage::writeToPacket).decoder(ChangedColdMessage::readFromPacket)
				.consumerMainThread(ChangedColdMessage::handle)
				.add();
		CHANNEL.messageBuilder(ChangeWeatherTimeMessage.class, 1)
				.encoder(ChangeWeatherTimeMessage::writeToPacket).decoder(ChangeWeatherTimeMessage::readFromPacket)
				.consumerMainThread(ChangeWeatherTimeMessage::handle)
				.add();
		CHANNEL.messageBuilder(ChangeWeatherMessage.class, 2)
				.encoder(ChangeWeatherMessage::writeToPacket).decoder(ChangeWeatherMessage::readFromPacket)
				.consumerMainThread(ChangeWeatherMessage::handle)
				.add();
		CHANNEL.messageBuilder(MessageHurtMultipart.class, 4)
				.encoder(MessageHurtMultipart::write).decoder(MessageHurtMultipart::read)
				.consumerMainThread(MessageHurtMultipart::handle)
				.add();
		CHANNEL.messageBuilder(AuroraLevelMessage.class, 5)
				.encoder(AuroraLevelMessage::writeToPacket).decoder(AuroraLevelMessage::readFromPacket)
				.consumerMainThread(AuroraLevelMessage::handle)
				.add();
		CHANNEL.messageBuilder(AuroraPowerMessage.class, 6)
				.encoder(AuroraPowerMessage::writeToPacket).decoder(AuroraPowerMessage::readFromPacket)
				.consumerMainThread(AuroraPowerMessage::handle)
				.add();
	}

	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(FrostRealm.MODID, name.toLowerCase(Locale.ROOT));
	}

	public static String prefixOnString(String name) {
		return FrostRealm.MODID + ":" + name.toLowerCase(Locale.ROOT);
	}

	private void registerCommands(RegisterCommandsEvent evt) {
		FrostWeatherCommand.register(evt.getDispatcher());
	}
}
