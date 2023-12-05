package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.message.ChangeWeatherTimeMessage;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.utils.ModifierUtils;
import baguchan.frostrealm.world.FrostLevelData;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.common.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AttachCapabilitiesEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
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
        if (event.getEntity() != null && event.getEntity().level() instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) event.getEntity().level();
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
        if (event.getEntity() != null && event.getEntity().level() instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) event.getEntity().level();
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

    @SubscribeEvent
    public static void modifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty() && !stack.is(FrostItems.AURORA_GEM.get())) {

            if (event.getSlotType() == EquipmentSlot.MAINHAND || (stack.getItem() instanceof ArmorItem) && ((ArmorItem) stack.getItem()).getEquipmentSlot() == event.getSlotType()) {
                Multimap<Attribute, AttributeModifier> multimap = ModifierUtils.getAttributeModifiers(stack, event.getSlotType());
                if (!multimap.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                        event.addModifier(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockToolInteractions(BlockEvent.BlockToolModificationEvent event) {
        ToolAction action = event.getToolAction();
        BlockState state = event.getState();
        UseOnContext context = event.getContext();
        if (!event.isSimulated()) {
            if (action == ToolActions.AXE_STRIP) {
                if (state.is(FrostBlocks.FROSTROOT_LOG.get())) {
                    event.setFinalState(FrostBlocks.STRIPPED_FROSTROOT_LOG.get().withPropertiesOf(state));
                }
            }
            if (action == ToolActions.HOE_TILL && (context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir())) {
                if (state.is(FrostBlocks.FROZEN_DIRT.get()) || state.is(FrostBlocks.FROZEN_GRASS_BLOCK.get())) {
                    event.setFinalState(FrostBlocks.FROZEN_FARMLAND.get().defaultBlockState());
                }
            }
        }
    }
}
