package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.message.ChangeWeatherEvent;
import baguchan.frostrealm.message.ChangeWeatherTimeEvent;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.utils.RecipeUtils;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
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
	public static void onLevelUpdate(TickEvent.LevelTickEvent event) {
		event.level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(frostWeatherCapability -> {
			frostWeatherCapability.tick(event.level);
		});
	}


	@SubscribeEvent
	public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() != null && event.getEntity().level instanceof ServerLevel) {
			ServerLevel world = (ServerLevel) event.getEntity().level;
			MinecraftServer server = world.getServer();
			//sync weather
			for (ServerLevel serverworld : server.getAllLevels()) {
				if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(cap.getWeatherTime(), cap.getWeatherCooldown());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					});
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherEvent message = new ChangeWeatherEvent(cap.getFrostWeather());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
						cap.needWeatherChanged = true;
					});
				}
			}
		}
	}

	@SubscribeEvent
	public static void onDimensionChangeEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() != null && event.getEntity().level instanceof ServerLevel) {
			ServerLevel world = (ServerLevel) event.getEntity().level;
			MinecraftServer server = world.getServer();
			//sync weather
			for (ServerLevel serverworld : server.getAllLevels()) {
				if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(cap.getWeatherTime(), cap.getWeatherCooldown());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					});
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherEvent message = new ChangeWeatherEvent(cap.getFrostWeather());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
						cap.needWeatherChanged = true;
					});
				}
			}
		}
	}

	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		livingEntity.getCapability(FrostRealm.FROST_LIVING_CAPABILITY).ifPresent(livingCapability -> {
			livingCapability.tick(livingEntity);
		});
	}

	@SubscribeEvent
	public static void onHoeRightClick(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();

		if (stack.getItem() instanceof HoeItem) {
			if (event.getLevel().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_DIRT.get() || event.getLevel().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_GRASS_BLOCK.get()) {
				event.getLevel().setBlock(event.getPos(), FrostBlocks.FROZEN_FARMLAND.get().defaultBlockState(), 2);
				stack.hurtAndBreak(1, event.getEntity(), (p_147232_) -> {
					p_147232_.broadcastBreakEvent(event.getHand());
				});
				event.getLevel().playSound(event.getEntity(), event.getPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
				event.setUseItem(Event.Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public static void onNeighborNotified(BlockEvent.NeighborNotifyEvent event) {
		LevelAccessor levelAccessor = event.getLevel();
		BlockPos blockPos = event.getPos();
		if (levelAccessor instanceof Level) {
			if (((Level) levelAccessor).dimension() == FrostDimensions.FROSTREALM_LEVEL) {
				RecipeUtils.isBlockPlacementFreeze((Level) levelAccessor, blockPos, event.getState());
			}
		}
	}

	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		LevelAccessor levelAccessor = event.getLevel();
		BlockPos blockPos = event.getPos();
		if (levelAccessor instanceof Level) {
			if (((Level) levelAccessor).dimension() == FrostDimensions.FROSTREALM_LEVEL) {
				RecipeUtils.isBlockPlacementFreeze((Level) levelAccessor, blockPos, event.getState());
			}
		}
	}
}
