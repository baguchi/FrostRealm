package baguchan.frostrealm;


import java.util.Locale;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostCarvers;
import baguchan.frostrealm.registry.FrostConfiguredFeatures;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("frostrealm")
public class FrostRealm {
	public static final Logger LOGGER = LogManager.getLogger("frostrealm");

	public static final String MODID = "frostrealm";

	public static final String NETWORK_PROTOCOL = "2";

	public FrostRealm() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::lateSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		FrostBlocks.BLOCKS.register(modbus);
		FrostItems.ITEMS.register(modbus);
		FrostCarvers.CARVERS.register(modbus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FrostCarvers.registerConfiguredCarvers();
			FrostConfiguredFeatures.init();
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
