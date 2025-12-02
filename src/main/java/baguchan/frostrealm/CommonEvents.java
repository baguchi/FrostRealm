package baguchan.frostrealm;

import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.data.resource.FrostDimensions;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.entity.FrostPart;
import baguchan.frostrealm.entity.animal.Seal;
import baguchan.frostrealm.entity.animal.SnowPileQuail;
import baguchan.frostrealm.message.ChangeAuroraMessage;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.utils.AttackUtils;
import baguchan.frostrealm.utils.aurorapower.AuroraCombatRules;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import baguchan.frostrealm.world.FrostLevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@EventBusSubscriber(modid = FrostRealm.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onStackOther(ItemStackedOnOtherEvent event) {
        ItemStack stack = event.getStackedOnItem();
        ItemStack carriedStack = event.getCarriedItem();
        if (event.getClickAction() == ClickAction.PRIMARY) {
            if (!stack.has(FrostDataCompnents.ATTACH_CRYSTAL.get()) && (stack.is(FrostItems.COATING_FUR))) {
                Optional<Holder.Reference<AttachableCrystal>> optional1 = AttachableCrystals.getFromIngredient(event.getPlayer().registryAccess(), carriedStack);
                if (optional1.isPresent() && carriedStack.getCount() == 1) {
                    stack.set(FrostDataCompnents.ATTACH_CRYSTAL.get(), optional1.get());
                    event.getPlayer().playSound(SoundEvents.BUNDLE_INSERT);
                    if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                        FrostCriterions.PUT_CRYSTAL.get().trigger(serverPlayer);
                    }
                    carriedStack.shrink(1);
                    event.getCarriedSlotAccess().set(stack.split(1));
                    broadcastChangesOnContainerMenu(event.getPlayer());
                    event.setCanceled(true);
                }
            }


            if (!stack.has(FrostDataCompnents.ATTACH_CRYSTAL.get()) && (stack.has(DataComponents.WEAPON) || stack.getItem() instanceof ArrowItem)
                    && carriedStack.has(FrostDataCompnents.ATTACH_CRYSTAL.get()) && carriedStack.is(FrostItems.COATING_FUR)) {
                Holder<AttachableCrystal> optional1 = carriedStack.get(FrostDataCompnents.ATTACH_CRYSTAL.get());
                stack.set(FrostDataCompnents.ATTACH_CRYSTAL.get(), optional1);
                event.getPlayer().playSound(SoundEvents.HONEYCOMB_WAX_ON);
                if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                    FrostCriterions.PUT_CRYSTAL.get().trigger(serverPlayer);
                }
                carriedStack.shrink(1);
                event.getCarriedSlotAccess().set(stack.copyAndClear());
                broadcastChangesOnContainerMenu(event.getPlayer());
                event.setCanceled(true);
            }
        }

        if (event.getClickAction() == ClickAction.SECONDARY) {

            if (carriedStack.isEmpty()) {
                if (stack.has(FrostDataCompnents.ATTACH_CRYSTAL.get()) && stack.is(FrostItems.COATING_FUR)) {
                    Holder<AttachableCrystal> crystal = stack.copy().get(FrostDataCompnents.ATTACH_CRYSTAL.get());
                    if (crystal != null) {
                        event.getPlayer().playSound(SoundEvents.HONEYCOMB_WAX_ON);
                        ItemStack stack1 = new ItemStack(crystal.value().getItem().value(), stack.getCount());
                        if (!event.getPlayer().addItem(stack1)) {
                            event.getPlayer().drop(stack1, true);
                        }
                        stack.remove(FrostDataCompnents.ATTACH_CRYSTAL.get());
                        event.getCarriedSlotAccess().set(stack.copyAndClear());
                        broadcastChangesOnContainerMenu(event.getPlayer());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private static void broadcastChangesOnContainerMenu(Player p_376384_) {
        AbstractContainerMenu abstractcontainermenu = p_376384_.containerMenu;
        if (abstractcontainermenu != null) {
            abstractcontainermenu.slotsChanged(p_376384_.getInventory());
        }
    }

    @SubscribeEvent
    public static void onSweep(SweepAttackEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack itemstack = player.getWeaponItem();
        AttackUtils.sickleAttack(player, target, itemstack);
    }

    @SubscribeEvent
    public static void onDecreaseFollowRange(LivingEvent.LivingVisibilityEvent event) {
        Entity target = event.getLookingEntity();

        if (target instanceof SnowPileQuail snowPileQuail) {
            if (snowPileQuail.getBlockStateOn().is(Blocks.SNOW) || snowPileQuail.getBlockStateOn().is(Blocks.SNOW_BLOCK)) {
                event.modifyVisibility(0.4F);
            }
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() != null && event.getEntity().level() instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) event.getEntity().level();
            MinecraftServer server = world.getServer();
            //sync weather
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                for (ServerLevel serverworld : server.getAllLevels()) {
                    if (serverworld.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
                        FrostWeatherSavedData cap = FrostWeatherSavedData.get(serverworld);
                        ChangeWeatherMessage message = new ChangeWeatherMessage(cap.getFrostWeather());
                        PacketDistributor.sendToPlayer(serverPlayer, message);
                        ChangeAuroraMessage message2 = new ChangeAuroraMessage(cap.getAuroraLevel());
                        PacketDistributor.sendToPlayer(serverPlayer, message2);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {

        if (event.getEntity().isMultipartEntity()) {
            for (PartEntity<?> partEntity : event.getEntity().getParts()) {
                if (partEntity instanceof FrostPart<?> part) {
                    part.snapTo(event.getEntity().position());
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

            /*if(event.getEntity() instanceof Player && !event.getEntity().level().isClientSide()){
                if(((Player) event.getEntity()).swingTime == 1) {
                    AnimationUtil.sendAnimation(event.getEntity(), FrostAnimations.BURGER);
                }
            }*/
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level && level.dimension().identifier().equals(FrostDimensions.FROSTREALM_LEVEL.identifier())) {
            FrostLevelData levelData = new FrostLevelData(level.getServer().getWorldData(), level.getServer().getWorldData().overworldData());
            level.serverLevelData = levelData;
            level.levelData = levelData;
        }
    }

    public static void makeParticles(Level p_51252_, BlockPos p_51253_) {
        p_51252_.levelEvent(1501, p_51253_, 0);
    }

    public static boolean canPlaceSnowLayer(Level world, BlockPos pos) {
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
                if (state.is(FrostBlocks.FROSTBITE_LOG.get())) {
                    event.setFinalState(FrostBlocks.STRIPPED_FROSTBITE_LOG.get().withPropertiesOf(state));
                }
            }
            if (action == ItemAbilities.HOE_TILL && (context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir())) {
                if (state.is(FrostBlocks.FROZEN_DIRT.get()) || state.is(FrostBlocks.FROZEN_GRASS_BLOCK.get())) {
                    event.setFinalState(FrostBlocks.FROZEN_FARMLAND.get().defaultBlockState());
                }
            }
        }
    }

    /*
      handle the Attached Crystal
    */
    @SubscribeEvent
    public static void onEntityHurtPost(LivingDamageEvent.Post event) {
        LivingEntity livingEntity = event.getEntity();
        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && event.getSource().isDirect()) {
            ItemStack stack = event.getSource().getWeaponItem();
            if (stack != null) {
                int damage = stack.getOrDefault(FrostDataCompnents.CRYSTAL_USED, 0);
                @Nullable Holder<AttachableCrystal> attachableCrystal = stack.get(FrostDataCompnents.ATTACH_CRYSTAL);
                if (attachableCrystal != null) {

                    if (attachableCrystal.value().getMobEffectInstance().isPresent()) {
                        livingEntity.addEffect(new MobEffectInstance(attachableCrystal.value().getMobEffectInstance().get()));
                    }
                    if (!(attacker instanceof Player player) || !player.isCreative()) {
                        if (damage - 1 >= attachableCrystal.value().getUse()) {
                            stack.remove(FrostDataCompnents.ATTACH_CRYSTAL);
                            stack.remove(FrostDataCompnents.CRYSTAL_USED);
                            attacker.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 0.4F / (attacker.getRandom().nextFloat() * 0.4F + 0.8F));
                        } else {
                            stack.set(FrostDataCompnents.CRYSTAL_USED, damage + 1);
                        }
                    }
                }
            }
        }

        if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow) {
            ItemStack stack2 = arrow.getPickupItemStackOrigin();
            if (event.getBlockedDamage() <= 0 && stack2 != null) {
                @Nullable Holder<AttachableCrystal> attachableCrystal = stack2.get(FrostDataCompnents.ATTACH_CRYSTAL);
                if (attachableCrystal != null && attachableCrystal.value().getMobEffectInstance().isPresent()) {
                    livingEntity.addEffect(new MobEffectInstance(attachableCrystal.value().getMobEffectInstance().get()));
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

        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && event.getSource().isDirect()) {
            ItemStack stack = event.getSource().getWeaponItem();
            if (stack != null && event.getAmount() > 0) {
                @Nullable Holder<AttachableCrystal> attachableCrystal = stack.get(FrostDataCompnents.ATTACH_CRYSTAL);
                if (attachableCrystal != null) {
                    event.setAmount(event.getAmount() + attachableCrystal.value().getDamage());
                }
            }
        }

        if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow) {
            ItemStack stack2 = arrow.getPickupItemStackOrigin();
            if (stack2 != null && event.getAmount() > 0) {
                @Nullable Holder<AttachableCrystal> attachableCrystal = stack2.get(FrostDataCompnents.ATTACH_CRYSTAL);
                if (attachableCrystal != null) {
                    event.setAmount(event.getAmount() + attachableCrystal.value().getDamage());
                }
            }
        }
    }
}
