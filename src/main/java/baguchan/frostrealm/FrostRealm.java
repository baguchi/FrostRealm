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
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
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


	public FrostRealm(IEventBus modBus) {

		IEventBus forgeBus = NeoForge.EVENT_BUS;
		FrostFeatures.FEATURES.register(modBus);
		FrostSounds.SOUND_EVENTS.register(modBus);
		FrostMenuTypes.MENU_TYPES.register(modBus);
		FrostFluidTypes.FLUID_TYPES.register(modBus);
		FrostFluids.FLUIDS.register(modBus);
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
        FrostChunkGenerators.CHUNK_GENERATOR.register(modBus);
        FrostWeathers.FROST_WEATHER.register(modBus);
		modBus.addListener(this::setup);
		modBus.addListener(this::dataSetup);
		modBus.addListener(this::setupPackets);
		NeoForge.EVENT_BUS.addListener(this::registerCommands);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			modBus.addListener(ClientRegistrar::setup);
		}
	}

	private void dataSetup(final DataPackRegistryEvent.NewRegistry event) {
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostInteractionInformations.init();
			FrostBlocks.burnables();
			FrostBiomes.addBiomeTypes();
			Map<ResourceLocation, MultiNoiseBiomeSourceParameterList.Preset> map = Maps.newHashMap();
			map.putAll(Map.copyOf(MultiNoiseBiomeSourceParameterList.Preset.BY_NAME));
			map.put(new ResourceLocation(FrostRealm.MODID, "frostrealm"), FrostBiomeSources.FROSTREALM_PRESET);
			MultiNoiseBiomeSourceParameterList.Preset.BY_NAME = map;
		});
	}

	public static void sendMSGToAll(CustomPacketPayload message) {
		for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
			sendNonLocal(message, player);
		}
	}

	public static void sendNonLocal(CustomPacketPayload msg, ServerPlayer player) {
		PacketDistributor.PLAYER.with(player).send(msg);
	}

	public void setupPackets(RegisterPayloadHandlerEvent event) {
		IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
		registrar.play(ChangedColdMessage.ID, ChangedColdMessage::new, payload -> payload.client(ChangedColdMessage::handle));
		registrar.play(ChangeWeatherMessage.ID, ChangeWeatherMessage::new, payload -> payload.client(ChangeWeatherMessage::handle));
		registrar.play(HurtMultipartMessage.ID, HurtMultipartMessage::new, payload -> payload.server(HurtMultipartMessage::handle));
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
