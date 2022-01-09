package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.message.ChangeWeatherTimeEvent;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID)
public class CommonEvents {
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(FrostLivingCapability.class);
		event.register(FrostWeatherCapability.class);
	}

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(new ResourceLocation(FrostRealm.MODID, "frost_living"), new FrostLivingCapability());
		}
	}

	@SubscribeEvent
	public static void onAttachLevelCapabilities(AttachCapabilitiesEvent<Level> event) {
		event.addCapability(new ResourceLocation(FrostRealm.MODID, "frost_weather"), new FrostWeatherCapability());
	}

	@SubscribeEvent
	public static void onWorldUpdate(TickEvent.WorldTickEvent event) {
		event.world.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(frostWeatherCapability -> {
			frostWeatherCapability.tick(event.world);
		});
	}

	@SubscribeEvent
	public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getPlayer() != null && event.getPlayer().level instanceof ServerLevel) {
			ServerLevel world = (ServerLevel) event.getPlayer().level;
			MinecraftServer server = world.getServer();
			for (ServerLevel serverworld : server.getAllLevels()) {
				if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(cap.getWeatherTime(), cap.getWeatherCooldown(), cap.getWeatherLevel(1.0F));
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					});
				}
			}
		}
	}

	@SubscribeEvent
	public static void onDimensionChangeEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getPlayer() != null && event.getPlayer().level instanceof ServerLevel) {
			ServerLevel world = (ServerLevel) event.getPlayer().level;
			MinecraftServer server = world.getServer();
			for (ServerLevel serverworld : server.getAllLevels()) {
				if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(cap.getWeatherTime(), cap.getWeatherCooldown(), cap.getWeatherLevel(1.0F));
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					});
				}
			}
		}
	}

	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
		LivingEntity livingEntity = event.getEntityLiving();
		livingEntity.getCapability(FrostRealm.FROST_LIVING_CAPABILITY).ifPresent(livingCapability -> {
			livingCapability.tick(livingEntity);
		});
	}

	@SubscribeEvent
	public static void onHoeRightClick(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();

		if (stack.getItem() instanceof HoeItem) {
			if (event.getWorld().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_DIRT || event.getWorld().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_GRASS_BLOCK) {
				event.getWorld().setBlock(event.getPos(), FrostBlocks.FROZEN_FARMLAND.defaultBlockState(), 2);
				event.getPlayer().swing(event.getHand());
				stack.hurtAndBreak(1, event.getPlayer(), (p_147232_) -> {
					p_147232_.broadcastBreakEvent(event.getHand());
				});
				event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
		}
	}
}
