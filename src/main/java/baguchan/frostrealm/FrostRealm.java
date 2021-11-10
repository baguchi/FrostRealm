package baguchan.frostrealm;


import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.client.ClientRegistrar;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.world.FrostBiomeSource;
import baguchan.frostrealm.world.FrostChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
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

	public FrostRealm() {
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		FrostBlocks.BLOCKS.register(modbus);
		FrostItems.ITEMS.register(modbus);
		FrostCarvers.CARVERS.register(modbus);
		FrostFeatures.FEATURES.register(modbus);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::lateSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostCarvers.registerConfiguredCarvers();
			FrostConfiguredFeatures.init();
			FrostSurfaceBuilders.init();
			Registry.register(Registry.CHUNK_GENERATOR, FrostRealm.prefix("chunk_generator"), FrostChunkGenerator.CODEC);
			Registry.register(Registry.BIOME_SOURCE, FrostRealm.prefix("biome_provider"), FrostBiomeSource.CODEC);
		});
	}

	public void lateSetup(FMLLoadCompleteEvent event) {
	}

	private void doClientStuff(FMLClientSetupEvent event) {
		//MinecraftForge.EVENT_BUS.register(new ClientColdHUDEvent());
	}

	private void enqueueIMC(InterModEnqueueEvent event) {
	}

	private void processIMC(InterModProcessEvent event) {
	}

	@SubscribeEvent
	public void onCommandRegister(RegisterCommandsEvent event) {
	}

	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(FrostRealm.MODID, name.toLowerCase(Locale.ROOT));
	}

	public void gatherData(GatherDataEvent event) {
	}
}
