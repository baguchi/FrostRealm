package baguchan.frostrealm;


import baguchan.frostrealm.client.ClientRegistrar;
import baguchan.frostrealm.command.FrostWeatherCommand;
import baguchan.frostrealm.command.TemperatureCommand;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.message.ChangedColdMessage;
import baguchan.frostrealm.message.HurtMultipartMessage;
import baguchan.frostrealm.registry.*;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Map;

@Mod(FrostRealm.MODID)
public class FrostRealm {
	public static final Logger LOGGER = LogManager.getLogger("frostrealm");

	public static final String MODID = "frostrealm";

	public static final String NETWORK_PROTOCOL = "2";


	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
			.networkProtocolVersion(() -> NETWORK_PROTOCOL)
			.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
			.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
			.simpleChannel();

	public FrostRealm(IEventBus modBus) {

		IEventBus forgeBus = NeoForge.EVENT_BUS;
		FrostWeathers.FROST_WEATHER.register(modBus);
		FrostFeatures.FEATURES.register(modBus);
		FrostSounds.SOUND_EVENTS.register(modBus);
		FrostMenuTypes.MENU_TYPES.register(modBus);
		FrostBlocks.BLOCKS.register(modBus);
		FrostSensors.SENSOR_TYPES.register(modBus);
		FrostMemoryModuleType.MEMORY_MODULE_TYPES.register(modBus);
		FrostEntities.ENTITIES.register(modBus);
		FrostCreativeTabs.CREATIVE_MODE_TABS.register(modBus);
		FrostItems.ITEMS.register(modBus);
		FrostLootFunctions.LOOT_REIGSTER.register(modBus);
		FrostEffects.MOB_EFFECTS.register(modBus);
		FrostEffects.POTION.register(modBus);
		FrostRecipes.RECIPE_SERIALIZERS.register(modBus);
		FrostBlockEntitys.BLOCK_ENTITIES.register(modBus);
		FrostAttachs.ATTACHMENT_TYPES.register(modBus);
		modBus.addListener(this::setup);
		modBus.addListener(this::dataSetup);
		NeoForge.EVENT_BUS.addListener(this::registerCommands);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			modBus.addListener(ClientRegistrar::setup);
		}
	}

	private void dataSetup(final DataPackRegistryEvent.NewRegistry event) {
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostBlocks.burnables();
            this.setupMessages();
			FrostBiomes.addBiomeTypes();
			Map<ResourceLocation, MultiNoiseBiomeSourceParameterList.Preset> map = Maps.newHashMap();
			map.putAll(Map.copyOf(MultiNoiseBiomeSourceParameterList.Preset.BY_NAME));
			map.put(new ResourceLocation(FrostRealm.MODID, "frostrealm"), FrostBiomeSources.FROSTREALM_PRESET);
			MultiNoiseBiomeSourceParameterList.Preset.BY_NAME = map;
		});
	}

	public static <MSG> void sendMSGToAll(MSG message) {
		for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
			sendNonLocal(message, player);
		}
	}

	public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
		CHANNEL.sendTo(msg, player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
	}

	private void setupMessages() {
		CHANNEL.messageBuilder(ChangedColdMessage.class, 0)
				.encoder(ChangedColdMessage::writeToPacket).decoder(ChangedColdMessage::readFromPacket)
                .consumerMainThread(ChangedColdMessage::handle)
                .add();
		CHANNEL.messageBuilder(ChangeWeatherMessage.class, 1)
                .encoder(ChangeWeatherMessage::writeToPacket).decoder(ChangeWeatherMessage::readFromPacket)
                .consumerMainThread(ChangeWeatherMessage::handle)
                .add();
		CHANNEL.messageBuilder(HurtMultipartMessage.class, 2)
                .encoder(HurtMultipartMessage::write).decoder(HurtMultipartMessage::read)
				.consumerMainThread(HurtMultipartMessage::handle)
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
		TemperatureCommand.register(evt.getDispatcher());
	}

	public static RegistryAccess registryAccess() {
		if (EffectiveSide.get().isServer()) {
			return ServerLifecycleHooks.getCurrentServer().registryAccess();
		}
		return Minecraft.getInstance().getConnection().registryAccess();
	}

}
