package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.message.ChangeWeatherTimeMessage;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostTags;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.world.FrostLevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;

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
						ChangeWeatherTimeMessage message = new ChangeWeatherTimeMessage(cap.getWeatherTime(), cap.getWeatherCooldown());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					});
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherMessage message = new ChangeWeatherMessage(cap.getFrostWeather());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
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
						ChangeWeatherTimeMessage message = new ChangeWeatherTimeMessage(cap.getWeatherTime(), cap.getWeatherCooldown());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					});
					world.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
						ChangeWeatherMessage message = new ChangeWeatherMessage(cap.getFrostWeather());
						FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
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
			if (event.getLevel().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_DIRT.get() || event.getLevel().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_GRASS_BLOCK.get() || event.getLevel().getBlockState(event.getPos()).getBlock() == FrostBlocks.ETERNITY_GRASS_BLOCK.get()) {
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
	public static void onWorldLoad(LevelEvent.Load event) {
		if (event.getLevel() instanceof ServerLevel level && level.dimension().location().equals(FrostDimensions.FROSTREALM_LEVEL.location())) {
			FrostLevelData levelData = new FrostLevelData(level.getServer().getWorldData(), level.getServer().getWorldData().overworldData());
			level.serverLevelData = levelData;
			level.levelData = levelData;
		}
	}

	@SubscribeEvent
	public static void onPreServerTick(TickEvent.LevelTickEvent event) {
		if (event.level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			if (event.level instanceof ServerLevel serverLevel) {
				event.level.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
					if (cap.isWeatherActive() && cap.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
						ChunkMap chunkManager = serverLevel.getChunkSource().chunkMap;

						if (event.level.random.nextInt(8) == 0) {
							chunkManager.getChunks().forEach(chunkHolder -> {
								Optional<LevelChunk> optionalChunk = chunkHolder.getEntityTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK).left();
								if (optionalChunk.isPresent()) {
									ChunkPos chunkPos = optionalChunk.get().getPos();
									if (!chunkManager.getPlayersCloseForSpawning(chunkPos).isEmpty()) {
										BlockPos pos = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, serverLevel.getBlockRandomPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ(), 15));
										BlockPos posDown = pos.below();

										if (serverLevel.isAreaLoaded(posDown, 1)) {
                                            BlockState snowState = serverLevel.getBlockState(pos);
                                            BlockState snowStateBelow = serverLevel.getBlockState(pos.below());
                                            if (snowState.getBlock() instanceof CropBlock) {
                                                if (!snowState.is(FrostTags.Blocks.NON_FREEZE_CROP)) {
                                                    serverLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                                                }
                                            } else if (snowState.getBlock() == Blocks.FIRE) {
                                                serverLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                                            } else if (snowStateBelow.hasProperty(BlockStateProperties.LIT) && snowStateBelow.getValue(BlockStateProperties.LIT)) {
                                                makeParticles(serverLevel, pos.below());
                                                serverLevel.setBlockAndUpdate(pos.below(), snowStateBelow.setValue(BlockStateProperties.LIT, false));
                                            } else if (snowState.getBlock() == Blocks.SNOW.defaultBlockState().getBlock()) {
                                                int layers = snowState.getValue(SnowLayerBlock.LAYERS);
                                                if (layers < 3) {
                                                    serverLevel.setBlockAndUpdate(pos, snowState.setValue(SnowLayerBlock.LAYERS, ++layers));
                                                }
                                            } else if (canPlaceSnowLayer(serverLevel, pos)) {
												serverLevel.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
											}
										}
									}
								}
							});
						}
					}
				});
			}
		}
	}

	public static void makeParticles(Level p_51252_, BlockPos p_51253_) {
		p_51252_.levelEvent(1501, p_51253_, 0);
	}

	public static boolean canPlaceSnowLayer(ServerLevel world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		BlockState stateDown = world.getBlockState(pos.below());
		return world.isEmptyBlock(pos.above())
				&& world.isEmptyBlock(pos)
				&& Block.canSupportRigidBlock(world, pos.below())
				&& !(stateDown.getBlock() instanceof SnowLayerBlock)
				&& !(state.getBlock() instanceof SnowLayerBlock)
				&& Blocks.SNOW.defaultBlockState().canSurvive(world, pos);
	}
}
