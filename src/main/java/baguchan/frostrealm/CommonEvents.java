package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.message.ChangeWeatherEvent;
import baguchan.frostrealm.message.ChangeWeatherTimeEvent;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostStructures;
import baguchan.frostrealm.utils.StructureUtils;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
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
			//sync weather
			for (ServerLevel serverworld : server.getAllLevels()) {
				if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(cap.getWeatherTime(), cap.getWeatherCooldown(), cap.getWeatherLevel(1.0F));
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
		if (event.getPlayer() != null && event.getPlayer().level instanceof ServerLevel) {
			ServerLevel world = (ServerLevel) event.getPlayer().level;
			MinecraftServer server = world.getServer();
			//sync weather
			for (ServerLevel serverworld : server.getAllLevels()) {
				if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(cap.getWeatherTime(), cap.getWeatherCooldown(), cap.getWeatherLevel(1.0F));
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
			if (event.getWorld().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_DIRT.get() || event.getWorld().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_GRASS_BLOCK.get()) {
				event.getWorld().setBlock(event.getPos(), FrostBlocks.FROZEN_FARMLAND.get().defaultBlockState(), 2);
				event.getPlayer().swing(event.getHand());
				stack.hurtAndBreak(1, event.getPlayer(), (p_147232_) -> {
					p_147232_.broadcastBreakEvent(event.getHand());
				});
				event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	@SubscribeEvent
	public void onBlockBreaked(BlockEvent.BreakEvent event) {
		if (event.getPlayer() instanceof ServerPlayer) {
			LevelAccessor world = event.getPlayer().level;
			if (world instanceof ServerLevel) {
				ServerLevel serverLevel = (ServerLevel) world;
				Structure structure = serverLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY).get(FrostStructures.CRYSTAL_TEMPLE);
				if (structure != null) {
					StructureStart structureStart = serverLevel.structureManager().getStructureAt(event.getPos(), structure);
					if (structureStart.isValid()) {
						if (!StructureUtils.isBeatenDungeons(serverLevel, structureStart.getBoundingBox())) {
							ServerPlayer player = (ServerPlayer) event.getPlayer();
							if (!player.isCreative() && !(event.getState().getBlock() instanceof TorchBlock) && !(event.getState().getBlock() instanceof SpawnerBlock)) {
								player.displayClientMessage(Component.translatable("frostrealm.need_defeat_boss"), true);
								event.setCanceled(true);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
		if (event.getEntity() instanceof ServerPlayer) {
			LevelAccessor world = event.getEntity().level;
			if (world instanceof ServerLevel) {
				ServerLevel serverLevel = (ServerLevel) world;
				Structure structure = serverLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY).get(FrostStructures.CRYSTAL_TEMPLE);
				if (structure != null) {
					StructureStart structureStart = serverLevel.structureManager().getStructureAt(event.getPos(), structure);
					if (structureStart.isValid()) {
						if (!StructureUtils.isBeatenDungeons(serverLevel, structureStart.getBoundingBox())) {
							ServerPlayer player = (ServerPlayer) event.getEntity();
							Block placedBlock = event.getPlacedBlock().getBlock();
							if (!player.isCreative() && !(placedBlock instanceof TorchBlock)) {
								player.displayClientMessage(Component.translatable("frostrealm.need_defeat_boss"), true);
								event.setCanceled(true);
								ItemStack placedStack = new ItemStack(placedBlock);
								InteractionHand hand = player.getMainHandItem().getItem() == placedStack.getItem() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
								//TODO Packet
							}
						}
					}
				}
			}
		}
	}
}
