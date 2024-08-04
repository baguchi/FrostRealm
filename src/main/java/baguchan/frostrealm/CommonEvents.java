package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.entity.FrostPart;
import baguchan.frostrealm.entity.animal.Seal;
import baguchan.frostrealm.message.ChangeAuroraMessage;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.utils.aurorapower.AuroraCombatRules;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import baguchan.frostrealm.world.FrostLevelData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ChunkResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = FrostRealm.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() != null && event.getEntity().level() instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) event.getEntity().level();
            MinecraftServer server = world.getServer();
            //sync weather
            for (ServerLevel serverworld : server.getAllLevels()) {
                if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
                    FrostWeatherSavedData cap = FrostWeatherSavedData.get(serverworld);
                    ChangeWeatherMessage message = new ChangeWeatherMessage(cap.getFrostWeather());
                    PacketDistributor.sendToAllPlayers(message);
                    ChangeAuroraMessage message2 = new ChangeAuroraMessage(cap.getAuroraLevel());
                    PacketDistributor.sendToAllPlayers(message2);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMusicPlayed(SelectMusicEvent event) {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            Holder<Biome> biome = Minecraft.getInstance().player.level().getBiome(Minecraft.getInstance().player.blockPosition());
            if (Minecraft.getInstance().level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
                event.setMusic(biome.value().getBackgroundMusic().orElse(Musics.GAME));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity().isMultipartEntity()) {
            for (PartEntity<?> partEntity : event.getEntity().getParts()) {
                if (partEntity instanceof FrostPart<?> part) {
                    part.moveTo(event.getEntity().position());
                }
            }
        }
        if (event.getEntity() instanceof final PolarBear bear) {
            bear.targetSelector.addGoal(1,
                    new NearestAttackableTargetGoal<>(bear, Seal.class, 45, true, true, null));
        }
    }

    @SubscribeEvent
    public static void onLevelUpdate(LevelTickEvent.Pre event) {
        if (FrostWeatherSavedData.get(event.getLevel()) != null) {
            FrostWeatherSavedData.get(event.getLevel()).tick(event.getLevel());
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

                    FrostWeatherSavedData cap = FrostWeatherSavedData.get(serverworld);
                    ChangeWeatherMessage message = new ChangeWeatherMessage(cap.getFrostWeather());
                    PacketDistributor.sendToAllPlayers(message);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onUpdate(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            FrostLivingCapability capability = livingEntity.getData(FrostAttachs.FROST_LIVING);
            capability.tick(livingEntity);
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
    public static void onPreServerTick(LevelTickEvent.Pre event) {
        if (event.getLevel().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                FrostWeatherSavedData frostWeatherSavedData = FrostWeatherSavedData.get(serverLevel);
                if (frostWeatherSavedData.isWeatherActive() && frostWeatherSavedData.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
                        ChunkMap chunkManager = serverLevel.getChunkSource().chunkMap;

                    if (event.getLevel().random.nextInt(8) == 0) {
                            chunkManager.getChunks().forEach(chunkHolder -> {
                                ChunkResult<LevelChunk> optionalChunk = chunkHolder.getEntityTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK);
                                if (optionalChunk.isSuccess()) {
                                    optionalChunk.ifSuccess(chunkHolder2 -> {
                                        ChunkPos chunkPos = chunkHolder2.getPos();
                                    if (!chunkManager.getPlayersCloseForSpawning(chunkPos).isEmpty()) {
                                        BlockPos pos = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, serverLevel.getBlockRandomPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ(), 15));
                                        BlockPos posDown = pos.below();
                                        if (!event.getLevel().getBiome(pos).is(FrostTags.Biomes.HOT_BIOME)) {
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
                                                    if (layers < 2) {
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
                            });
                        }
                    }
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
    public static void blockToolInteractions(BlockEvent.BlockToolModificationEvent event) {
        ItemAbility action = event.getItemAbility();
        BlockState state = event.getState();
        UseOnContext context = event.getContext();
        if (!event.isSimulated()) {
            if (action == ItemAbilities.AXE_STRIP) {
                if (state.is(FrostBlocks.FROSTROOT_LOG.get())) {
                    event.setFinalState(FrostBlocks.STRIPPED_FROSTROOT_LOG.get().withPropertiesOf(state));
                }
            }
            if (action == ItemAbilities.HOE_TILL && (context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir())) {
                if (state.is(FrostBlocks.FROZEN_DIRT.get()) || state.is(FrostBlocks.FROZEN_GRASS_BLOCK.get())) {
                    event.setFinalState(FrostBlocks.FROZEN_FARMLAND.get().defaultBlockState());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingIncomingDamageEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

            int auroraShaper = AuroraPowerUtils.getAuroraPowerLevel(AuroraPowers.AURORA_SHAPER.get(), attacker);

            if (event.getAmount() > 0 && auroraShaper > 0) {
                event.setAmount(AuroraCombatRules.getDamageAddition(event.getAmount(), auroraShaper));
            }

            int crystalSlasher = AuroraPowerUtils.getAuroraPowerLevel(AuroraPowers.CRYSTAL_SLASHER.get(), attacker);

            float armor = livingEntity.getArmorValue();

            if (event.getAmount() > 0 && armor > 0 && crystalSlasher > 0) {
                event.setAmount(AuroraCombatRules.getDamageAdditionWithExtra(event.getAmount(), crystalSlasher, armor));
            }
        }
        int auroraProtection = AuroraPowerUtils.getAuroraPowerLevel(AuroraPowers.AURORA_PROTECTION.get(), livingEntity);

        if (event.getAmount() > 0) {
            event.setAmount(AuroraCombatRules.getDamageReduction(event.getAmount(), auroraProtection));
        }
    }
}
