package baguchan.frostrealm.entity.brain;

import baguchan.frostrealm.entity.brain.behavior.SnowBallAttack;
import baguchan.frostrealm.entity.brain.behavior.StartAdmiringItemIfSeen;
import baguchan.frostrealm.entity.brain.behavior.StopAdmiringIfItemTooFarAway;
import baguchan.frostrealm.entity.brain.behavior.TakeBackFromStealer;
import baguchan.frostrealm.entity.npc.Yeti;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.piglin.StopAdmiringIfTiredOfTryingToReachItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class YetiAi<E extends Yeti> {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(6, 16);
    public static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(10, 30);
    protected static final UniformInt TIME_BETWEEN_HUNTS = TimeUtil.rangeOfSeconds(30, 120);
    private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_LINE_OF_SIGHT = TargetingConditions.forCombat().range(24.0D).ignoreLineOfSight();
    private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_AND_LINE_OF_SIGHT = TargetingConditions.forCombat().range(24.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

    public static List<ActivityData<Yeti>> getActivities() {
        return List.of(initCoreActivity(), initIdleActivity(), initFightActivity(), initTakeBack(), initAdmireItemActivity(), initRetreatActivity());
    }


    public static void updateActivity(Yeti yeti) {
        Brain<Yeti> brain = yeti.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity) null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, FrostActivity.TAKE_BACK.get(), Activity.AVOID, Activity.IDLE));
        Activity activity1 = brain.getActiveNonCoreActivity().orElse((Activity) null);
        /*if (activity != activity1) {
            getSoundForCurrentActivity(boar).ifPresent(boar::playSound);
        }*/

        if (yeti.isSameStatue(Yeti.State.CHASING) && !brain.hasMemoryValue(FrostMemoryModuleType.TAKE_BACK_TARGET.get())) {
            yeti.setState(Yeti.State.IDLING);
        }
        yeti.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    private static ActivityData<Yeti> initFightActivity() {
        return ActivityData.create(Activity.FIGHT, 0, ImmutableList.of(StopAttackingIfTargetInvalid.create(), SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(YetiAi::getSpeedModifierChasing), new SnowBallAttack<>(), BehaviorBuilder.triggerIf(Yeti::isMeleeAttack, MeleeAttack.create(25)), EraseMemoryIf.<Mob>create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
    }

    private static ActivityData<Yeti> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(StartAttacking.create((p_381522_, p_381523_) -> p_381523_.isAdult(), YetiAi::findNearestValidAttackTarget), new Swim<>(0.8F), new LookAtTargetSink(45, 90), StopBeingAngryIfTargetDead.create(), StartAdmiringItemIfSeen.create(120), new MoveToTargetSink()));
    }

    private static ActivityData<Yeti> initIdleActivity() {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(Pair.of(3, createIdleMovementBehaviors()), Pair.of(0, createLookBehaviors()), Pair.of(1, new RandomSitting(20)), Pair.of(0, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.85F)), Pair.of(2, StrollToPoi.create(MemoryModuleType.HOME, 0.85F, 3, 600))), ImmutableSet.of());
    }

    private static ActivityData<Yeti> initTakeBack() {
        return ActivityData.create(FrostActivity.TAKE_BACK.get(), 10, ImmutableList.of(TakeBackFromStealer.create(living -> true)), FrostMemoryModuleType.TAKE_BACK_TARGET.get());
    }

    private static ActivityData<Yeti> initAdmireItemActivity() {
        return ActivityData.create(Activity.ADMIRE_ITEM, 10, ImmutableList.of(GoToWantedItem.create(1.2F, true, 9), StopAdmiringIfItemTooFarAway.create(9), StopAdmiringIfTiredOfTryingToReachItem.create(200, 200)), MemoryModuleType.ADMIRING_ITEM);
    }

    private static ActivityData<Yeti> initRetreatActivity() {
        return ActivityData.create(Activity.AVOID, 10, ImmutableList.of(SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.35F, 15, false), createIdleMovementBehaviors(), SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)), EraseMemoryIf.create(YetiAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<LivingEntity> createLookBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60)), 2), Pair.of(SetEntityLookTargetSometimes.create(6.0F, UniformInt.of(30, 60)), 2)));
    }

    private static RunOne<Yeti> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(StartAdmiringItemIfSeen.create(119), 0), Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Yeti::refuseToMove), RandomStroll.stroll(0.8F)), 2), Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Yeti::refuseToMove), SetWalkTargetFromLookTarget.create(0.8F, 3)), 2), Pair.of(new DoNothing(30, 60), 1)));
    }

    private static boolean wantsToStopFleeing(Yeti p_34618_) {
        return p_34618_.isAdult() && isEnoughYeti(p_34618_);
    }

    private static boolean isNoEnoughYeti(Yeti yeti) {
        if (yeti.isBaby()) {
            return false;
        } else {
            int j = yeti.getBrain().getMemory(FrostMemoryModuleType.YETI_COUNT.get()).orElse(0) + 1;
            int i = yeti.getBrain().getMemory(FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get()).orElse(0);

            return j + 5 < i || yeti.getHealth() < yeti.getMaxHealth() / 4;
        }
    }

    private static boolean isEnoughYeti(Yeti p_34623_) {
        if (p_34623_.isBaby()) {
            return false;
        } else {
            int j = p_34623_.getBrain().getMemory(FrostMemoryModuleType.YETI_COUNT.get()).orElse(0) + 1;
            int i = p_34623_.getBrain().getMemory(FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get()).orElse(0);

            return j > i - 2;
        }
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel p_376590_, Yeti p_34611_) {
        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(p_34611_, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && isEntityAttackableIgnoringLineOfSight(p_376590_, p_34611_, optional.get())) {
            return optional;
        } else {
            Optional<List<LivingEntity>> listOptional = p_34611_.getBrain().getMemory(FrostMemoryModuleType.NEAREST_ENEMYS.get());
            if (listOptional.isPresent() && !listOptional.get().isEmpty()) {
                return Optional.of(listOptional.get().get(p_34611_.getRandom().nextInt(listOptional.get().size())));
            }
        }

        return p_34611_.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static InteractionResult mobInteract(ServerLevel p_376885_, Yeti yeti, Player p_34848_, InteractionHand p_34849_) {
        ItemStack itemstack = p_34848_.getItemInHand(p_34849_);
        ItemStack itemstack2 = yeti.getItemInHand(InteractionHand.OFF_HAND);

        if ((itemstack.is(FrostTags.Items.SMITHABLE_WEAPON) || itemstack.is(ItemTags.ARMOR_ENCHANTABLE)) && !itemstack.has(FrostDataCompnents.AURORA_POWER) && !(itemstack2.is(FrostTags.Items.SMITHABLE_WEAPON) || itemstack2.is(ItemTags.ARMOR_ENCHANTABLE)) && yeti.isTrade()) {
            yeti.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            ItemStack itemstack1 = itemstack.consumeAndReturn(1, p_34848_);
            holdInOffHand(p_376885_, yeti, itemstack1);
            admireGoldItem(yeti);
            stopWalking(yeti);
            yeti.setHoldTime(80);
            if (p_34848_ instanceof ServerPlayer serverPlayer) {
                FrostCriterions.STARDUST_TRADE.get().trigger(serverPlayer, itemstack);
            }
            yeti.setState(Yeti.State.TRADE);
            return InteractionResult.SUCCESS;
        } else
        if (canAdmire(yeti, itemstack)) {
            ItemStack itemstack1 = itemstack.consumeAndReturn(1, p_34848_);
            holdInOffHand(p_376885_, yeti, itemstack1);
            admireGoldItem(yeti);
            stopWalking(yeti);
            yeti.setHoldTime(60);
            if (p_34848_ instanceof ServerPlayer serverPlayer) {
                FrostCriterions.STARDUST_TRADE.get().trigger(serverPlayer, itemstack);
            }
            yeti.setState(Yeti.State.TRADE);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public static boolean canAdmire(Yeti yeti, ItemStack stack) {
        return !isAdmiringDisabled(yeti) && !isAdmiringItem(yeti) && yeti.isAdult() && stack.is(FrostTags.Items.YETI_CURRENCY);
    }

    private static boolean isAdmiringItem(Yeti yeti) {
        return yeti.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM);
    }

    private static void admireGoldItem(LivingEntity p_34939_) {
        p_34939_.getBrain().setMemoryWithExpiry(MemoryModuleType.ADMIRING_ITEM, true, 119L);
    }

    private static void stopWalking(Yeti p_35007_) {
        p_35007_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        p_35007_.getNavigation().stop();
    }

    private static float getSpeedModifier(LivingEntity livingEntity) {
        return 1.0F;
    }

    private static float getSpeedModifierChasing(LivingEntity p_149289_) {
        if (p_149289_ instanceof Yeti yeti && yeti.isSnowAttack()) {
            return 0.1F;
        }
        return 1.2F;
    }

    private static void retreatFromNearestTarget(Yeti p_34613_, LivingEntity p_34614_) {
        Brain<Yeti> brain = p_34613_.getBrain();
        LivingEntity $$2 = BehaviorUtils.getNearestTarget(p_34613_, brain.getMemory(MemoryModuleType.AVOID_TARGET), p_34614_);
        $$2 = BehaviorUtils.getNearestTarget(p_34613_, brain.getMemory(MemoryModuleType.ATTACK_TARGET), $$2);
        setAvoidTarget(p_34613_, $$2);
    }

    private static void setAvoidTarget(Yeti p_34620_, LivingEntity p_34621_) {
        p_34620_.standUpInstantly();
        p_34620_.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        p_34620_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        p_34620_.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, p_34621_, (long) RETREAT_DURATION.sample(p_34620_.getRandom()));
    }


    public static void wasHurtBy(ServerLevel serverLevel, Yeti p_34596_, LivingEntity p_34597_) {
        Brain<Yeti> brain = p_34596_.getBrain();
        if (!(p_34597_ instanceof Yeti)) {
            brain.eraseMemory(MemoryModuleType.ADMIRING_ITEM);
            if (p_34597_ instanceof Player && !((Player) p_34597_).isCreative()) {
                brain.setMemoryWithExpiry(MemoryModuleType.ADMIRING_DISABLED, true, 400L);
            }

            if (p_34596_.isBaby() || isNoEnoughYeti(p_34596_)) {
                retreatFromNearestTarget(p_34596_, p_34597_);
                if (YetiAi.isEntityAttackableIgnoringLineOfSight(serverLevel, p_34596_, p_34597_)) {
                    broadcastAngerTarget(serverLevel, p_34596_, p_34597_);
                }
            } else if (isNoEnoughYeti(p_34596_)) {
                retreatFromNearestTarget(p_34596_, p_34597_);
            } else {
                maybeRetaliate(serverLevel, p_34596_, p_34597_);
            }
        }
    }

    private static void maybeRetaliate(ServerLevel p_376847_, Yeti p_34625_, LivingEntity p_34626_) {
        if (!p_34625_.getBrain().isActive(Activity.AVOID) || p_34626_.getType() != FrostEntities.YETI.get()) {
            if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(p_34625_, p_34626_, 4.0D)) {
                if (Sensor.isEntityAttackable(p_376847_, p_34625_, p_34626_)) {
                        setAttackTarget(p_34625_, p_34626_);
                        broadcastAttackTarget(p_34625_, p_34626_);
                    }
                }

        }
    }

    public static void setAttackTarget(Yeti p_34630_, LivingEntity p_34631_) {
        Brain<Yeti> brain = p_34630_.getBrain();
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, p_34631_, 200L);
        p_34630_.standUpInstantly();
    }

    public static void onHitTarget(Yeti p_34580_, LivingEntity p_34581_) {
        if (!p_34580_.isBaby()) {
            broadcastAttackTarget(p_34580_, p_34581_);
        }
    }

    public static void angerNearbyYeti(ServerLevel serverLevel, Player player, boolean see) {
        List<Yeti> list = player.level().getEntitiesOfClass(Yeti.class, player.getBoundingBox().inflate(16.0D));
        list.stream().filter(YetiAi::isIdle).filter((p_34881_) -> {
            return !see || BehaviorUtils.canSee(p_34881_, player);
        }).forEach((p_269951_) -> {
            setAngerTarget(serverLevel, p_269951_, player);
        });
    }


    public static void stealFromYeti(LivingEntity living, Yeti yeti) {
        Brain<Yeti> brain = yeti.getBrain();
        ItemStack stack = yeti.findFood();
        if (!stack.isEmpty()) {
            living.setItemInHand(InteractionHand.MAIN_HAND, stack);
            brain.setMemory(FrostMemoryModuleType.TAKE_BACK_TARGET.get(), living);
            brain.setMemoryWithExpiry(FrostMemoryModuleType.TAKE_BACK_COOLDOWN.get(), Unit.INSTANCE, 40L);
            yeti.setState(Yeti.State.CHASING);
            living.playSound(SoundEvents.ITEM_PICKUP);
        }
    }

    protected static boolean isIdle(Yeti yeti) {
        return yeti.getBrain().isActive(Activity.IDLE);
    }

    protected static void broadcastAngerTarget(ServerLevel serverLevel, Yeti yeti, LivingEntity p_34897_) {
        getVisibleAdultYetis(yeti).forEach((p_275125_) -> {
            setAngerTargetIfCloserThanCurrent(serverLevel, p_275125_, p_34897_);
        });
    }

    private static Optional<LivingEntity> getAngerTarget(Yeti p_34976_) {
        return BehaviorUtils.getLivingEntityFromUUIDMemory(p_34976_, MemoryModuleType.ANGRY_AT);
    }

    private static void setAngerTargetIfCloserThanCurrent(ServerLevel serverLevel, Yeti yeti, LivingEntity target) {
        Optional<LivingEntity> optional = getAngerTarget(yeti);
        LivingEntity livingentity = BehaviorUtils.getNearestTarget(yeti, optional, target);
        if (!optional.isPresent() || optional.get() != livingentity) {
            setAngerTarget(serverLevel, yeti, livingentity);
        }
    }

    protected static void setAngerTarget(ServerLevel p_376847_, Yeti yeti, LivingEntity target) {
        if (YetiAi.isEntityAttackableIgnoringLineOfSight(p_376847_, yeti, target)) {
            yeti.standUpInstantly();
            yeti.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            yeti.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

            if (target.getType() == EntityType.PLAYER && p_376847_.getGameRules().get(GameRules.UNIVERSAL_ANGER)) {
                yeti.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }

        }
    }

    public static boolean isEntityAttackableIgnoringLineOfSight(ServerLevel serverLevel, LivingEntity p_182378_, LivingEntity p_182379_) {
        return p_182378_.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, p_182379_) ? ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_AND_LINE_OF_SIGHT.test(serverLevel, p_182378_, p_182379_) : ATTACK_TARGET_CONDITIONS_IGNORE_LINE_OF_SIGHT.test(serverLevel, p_182378_, p_182379_);
    }

    public static void initMemories(Yeti p_219206_, RandomSource p_219207_, EntitySpawnReason p_29535_) {
        int i = TIME_BETWEEN_HUNTS.sample(p_219207_);
        p_219206_.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, true, (long) i);
        if (p_29535_ == EntitySpawnReason.STRUCTURE) {
            GlobalPos globalpos = GlobalPos.of(p_219206_.level().dimension(), p_219206_.blockPosition());
            p_219206_.getBrain().setMemory(MemoryModuleType.HOME, globalpos);
        }
    }

    private static void broadcastAttackTarget(Yeti yeti, LivingEntity target) {
        getVisibleAdultYetis(yeti).forEach((p_34574_) -> {
            setAttackTargetIfCloserThanCurrent(p_34574_, target);
        });
    }

    public static List<Yeti> getVisibleAdultYetis(LivingEntity living) {
        return living.getBrain().getMemory(FrostMemoryModuleType.NEAREST_YETIS.get()).orElse(ImmutableList.of());
    }

    private static void setAttackTargetIfCloserThanCurrent(Yeti yeti, LivingEntity target) {
        Optional<LivingEntity> optional = yeti.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        LivingEntity livingentity = BehaviorUtils.getNearestTarget(yeti, optional, target);
        setAttackTarget(yeti, livingentity);
    }

    public static Optional<SoundEvent> getSoundForCurrentActivity(Yeti boar) {
        return boar.getBrain().getActiveNonCoreActivity().map((activity) -> {
            return getSoundForActivity(boar, activity);
        });
    }

    private static SoundEvent getSoundForActivity(Yeti p_34583_, Activity p_34584_) {
        if (p_34584_ != Activity.AVOID) {
            if (p_34584_ == Activity.FIGHT) {
                return FrostSounds.YETI_IDLE.get();
            } else {
                return FrostSounds.YETI_IDLE.get();
            }
        } else {
            return FrostSounds.YETI_IDLE.get();
        }
    }

    public static void stopHoldingOffHandItem(ServerLevel serverLevel, Yeti yeti, boolean thrown) {
        ItemStack itemstack = yeti.getItemInHand(InteractionHand.OFF_HAND);
        if (!itemstack.isEmpty()) {
            if (!yeti.isBaby()) {
                boolean flag = itemstack.is(FrostTags.Items.YETI_CURRENCY);
                //boolean flag2 = itemstack.is(FrostTags.Items.YETI_BIG_CURRENCY);
                if (thrown && flag) {
                    itemstack.shrink(1);
                    throwItems(yeti, getBarterResponseItems(yeti));
                    if (itemstack.getCount() <= 0) {
                        yeti.setState(Yeti.State.IDLING);

                        yeti.setHoldTime(40);
                    }
                } else if (thrown && (itemstack.is(FrostTags.Items.SMITHABLE_WEAPON) || itemstack.is(ItemTags.ARMOR_ENCHANTABLE))) {

                    ItemStack itemstack1 = yeti.getOffhandItem();
                    AuroraPowerUtils.auroraInfusionItem(yeti.getRandom(), itemstack1, 11, false);
                    throwItems(yeti, Collections.singletonList(itemstack1));
                    yeti.setState(Yeti.State.IDLING);
                    yeti.holdInOffHand(ItemStack.EMPTY);
                } else if (!flag) {
                    putInInventory(yeti, itemstack);

                    yeti.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                }
            } else {

                    ItemStack itemstack1 = yeti.getOffhandItem();
                if (isFood(itemstack1)) {
                        putInInventory(yeti, itemstack1);
                    } else {
                        throwItems(yeti, Collections.singletonList(itemstack1));
                    }

                    yeti.holdInOffHand(itemstack);

                yeti.setState(Yeti.State.IDLING);
            }
        }
    }

    public static boolean wantsToPickup(Yeti p_34858_, ItemStack p_34859_) {
        if (p_34859_.is(FrostTags.Items.YETI_SCARED)) {
            return false;
        } else if (isAdmiringDisabled(p_34858_) && p_34858_.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return false;
        } else {
            boolean flag = p_34858_.canAddToInventory(p_34859_);
            if (isFood(p_34859_)) {
                return flag;
            } else {
                return false;
            }
        }
    }

    private static boolean isAdmiringDisabled(Yeti p_35025_) {
        return p_35025_.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_DISABLED);
    }

    public static boolean isLovedItem(ItemStack p_149966_) {
        return p_149966_.is(FrostTags.Items.YETI_LOVED);
    }

    public static boolean isFood(ItemStack p_149966_) {
        return p_149966_.get(DataComponents.FOOD) != null;
    }


    private static List<ItemStack> getBarterResponseItems(Yeti p_34997_) {
        LootTable loottable = p_34997_.level().getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FrostLoots.YETI_BARTERING));
        List<ItemStack> list = loottable.getRandomItems((new LootParams.Builder((ServerLevel) p_34997_.level())).withParameter(LootContextParams.THIS_ENTITY, p_34997_).create(LootContextParamSets.PIGLIN_BARTER));
        return list;
    }

    private static void putInInventory(Yeti p_34953_, ItemStack p_34954_) {
        ItemStack itemstack = p_34953_.addToInventory(p_34954_);
        throwItemsTowardRandomPos(p_34953_, Collections.singletonList(itemstack));
    }

    private static void throwItems(Yeti p_34861_, List<ItemStack> p_34862_) {
        Player player = p_34861_.level().getNearestPlayer(p_34861_, 10.0D);
        if (player != null) {
            throwItemsTowardPlayer(p_34861_, player, p_34862_);
        } else {
            throwItemsTowardRandomPos(p_34861_, p_34862_);
        }

    }

    private static void throwItemsTowardRandomPos(Yeti p_34913_, List<ItemStack> p_34914_) {
        throwItemsTowardPos(p_34913_, p_34914_, getRandomNearbyPos(p_34913_));
    }

    private static void throwItemsTowardPlayer(Yeti p_34851_, Player p_34852_, List<ItemStack> p_34853_) {
        throwItemsTowardPos(p_34851_, p_34853_, p_34852_.position());
    }

    private static void throwItemsTowardPos(Yeti p_34864_, List<ItemStack> p_34865_, Vec3 p_34866_) {
        if (!p_34865_.isEmpty()) {
            p_34864_.swing(InteractionHand.OFF_HAND);

            for (ItemStack itemstack : p_34865_) {
                BehaviorUtils.throwItem(p_34864_, itemstack, p_34866_.add(0.0D, 1.0D, 0.0D));
            }
        }

    }

    public static void holdInOffHand(ServerLevel serverLevel, Yeti p_34933_, ItemStack p_34934_) {
        if (isHoldingItemInOffHand(p_34933_)) {
            p_34933_.spawnAtLocation(serverLevel, p_34933_.getItemInHand(InteractionHand.OFF_HAND));
        }

        p_34933_.holdInOffHand(p_34934_);
    }


    private static boolean isHoldingItemInOffHand(Yeti p_35027_) {
        return !p_35027_.getOffhandItem().isEmpty();
    }

    private static Vec3 getRandomNearbyPos(Yeti p_35017_) {
        Vec3 vec3 = LandRandomPos.getPos(p_35017_, 4, 2);
        return vec3 == null ? p_35017_.position() : vec3;
    }

    public static boolean isWearingFear(LivingEntity p_34809_) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemstack = p_34809_.getItemBySlot(equipmentSlot);
            Item item = itemstack.getItem();
            if (itemstack.is(FrostItems.YETI_FUR_HELMET.get()) || itemstack.is(FrostItems.YETI_FUR_CHESTPLATE.get()) || itemstack.is(FrostItems.YETI_FUR_LEGGINGS.get()) || itemstack.is(FrostItems.YETI_FUR_BOOTS.get())) {
                return true;
            }
        }

        return false;
    }

    public static class RandomSitting extends Behavior<Yeti> {
        private final int minimalPoseTicks;

        public RandomSitting(int p_251207_) {
            super(ImmutableMap.of());
            this.minimalPoseTicks = p_251207_ * 20;
        }

        protected boolean checkExtraStartConditions(ServerLevel p_249520_, Yeti p_250322_) {
            return !p_250322_.isInWater()
                    && p_250322_.getPoseTime() >= (long) this.minimalPoseTicks
                    && !p_250322_.isLeashed()
                    && p_250322_.onGround()
                    && !p_250322_.hasControllingPassenger()
                    && p_250322_.canYetiChangePose();
        }

        protected void start(ServerLevel p_250901_, Yeti p_250345_, long p_248515_) {
            p_250345_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            p_250345_.getNavigation().stop();
            if (p_250345_.isYetiSitting()) {
                p_250345_.standUp();
            } else if (!p_250345_.isPanicking()) {
                p_250345_.sitDown();
            }
        }
    }
}
