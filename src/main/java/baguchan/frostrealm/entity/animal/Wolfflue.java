package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.api.entity.WolfflueVariant;
import baguchan.frostrealm.data.resource.registries.WolfflueVariants;
import baguchan.frostrealm.entity.goal.LeapAtTargetWolfflueGoal;
import baguchan.frostrealm.entity.goal.WolfflueBegGoal;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostEntityDatas;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariant;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class Wolfflue extends AbstractWolfflue {
    private static final EntityDataAccessor<Holder<WolfflueVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Wolfflue.class, FrostEntityDatas.WOLFFLUE_VARIANT.get());
    private static final int EAT_COOLDOWN = 20 * 60 * 2;

    private int ticksSinceEaten;
    private int eatCooldown;

    public static final Predicate<LivingEntity> PREY_SELECTOR = p_348295_ -> {
        EntityType<?> entitytype = p_348295_.getType();
        return entitytype == FrostEntities.CRYSTAL_FOX.get() || entitytype == FrostEntities.SNOWPILE_QUAIL.get() || entitytype == EntityType.FOX || entitytype == EntityType.SHEEP;
    };
    public Wolfflue(EntityType<? extends Wolfflue> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.ON_TOP_OF_POWDER_SNOW, -1.0F);
    }

    public static boolean checkWolfSpawnRules(
            EntityType<? extends Animal> p_218105_, LevelAccessor p_218106_, EntitySpawnReason p_360742_, BlockPos p_218108_, RandomSource p_218109_
    ) {
        boolean flag = EntitySpawnReason.ignoresLightRequirements(p_360742_) || isBrightEnoughToSpawn(p_218106_, p_218108_);
        return p_218106_.getBlockState(p_218108_.below()).is(FrostTags.Blocks.ANIMAL_SPAWNABLE) && flag;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetWolfflueGoal(this, 2.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.5F, 12.0F, 6.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 0.8F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.goalSelector.addGoal(9, new WolfflueBegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, (living, serverLevel) -> PREY_SELECTOR.test(living) && this.eatCooldown <= 0).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.SAFE_FALL_DISTANCE, 8.0).add(Attributes.FOLLOW_RANGE, 18.0F).add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326027_) {
        super.defineSynchedData(p_326027_);
        Registry<WolfSoundVariant> registry = this.registryAccess().lookupOrThrow(Registries.WOLF_SOUND_VARIANT);
        p_326027_.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), WolfflueVariants.DEFAULT));
    }

    @Override
    public void setCustomName(@org.jetbrains.annotations.Nullable Component p_20053_) {
        super.setCustomName(p_20053_);
        if (!this.getVariant().is(WolfflueVariants.YUZUKI) && p_20053_ != null && (p_20053_.getString().equals("Yuzuki") || p_20053_.getString().equals("YuzukiYukari") || p_20053_.getString().equals("Yukari")
                || p_20053_.getString().equals("結月ゆかり") || p_20053_.getString().equals("結月") || p_20053_.getString().equals("ゆかり"))) {
            Holder<WolfflueVariant> holder = this.registryAccess().lookupOrThrow(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY).getOrThrow(WolfflueVariants.YUZUKI);
            this.setVariant(holder);
        }
    }

    public Identifier getTexture() {
        WolfflueVariant wolfvariant = this.getVariant().value();
        if (this.isBaby()) {
            if (this.isTame()) {
                return wolfvariant.wildBabyTexture();
            } else {
                return this.isAngry() ? wolfvariant.angryBabyTexture() : wolfvariant.wildBabyTexture();
            }
        }

        if (this.isTame()) {
            return wolfvariant.wildTexture();
        } else {
            return this.isAngry() ? wolfvariant.angryTexture() : wolfvariant.wildTexture();
        }
    }

    public Holder<WolfflueVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<WolfflueVariant> p_332777_) {
        this.entityData.set(DATA_VARIANT_ID, p_332777_);
    }


    @Override
    public void addAdditionalSaveData(ValueOutput p_30418_) {
        super.addAdditionalSaveData(p_30418_);
        this.getVariant().unwrapKey().ifPresent(p_344339_ -> p_30418_.putString("variant", p_344339_.identifier().toString()));
        p_30418_.putInt("eat_cooldown", this.eatCooldown);

    }

    @Override
    public void readAdditionalSaveData(ValueInput p_30402_) {
        super.readAdditionalSaveData(p_30402_);
        Optional.ofNullable(Identifier.tryParse(p_30402_.getString("variant").orElseThrow()))
                    .map(p_332608_ -> ResourceKey.create(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY, p_332608_))
                    .flatMap(p_352803_ -> this.registryAccess().lookupOrThrow(WolfflueVariants.WOLFFLUE_VARIANT_REGISTRY_KEY).get((ResourceKey<WolfflueVariant>) p_352803_))
                    .ifPresent(this::setVariant);

        this.eatCooldown = p_30402_.getIntOr("eat_cooldown", 0);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {

        Holder<Biome> holder = serverLevelAccessor.getBiome(this.blockPosition());
        Holder<WolfflueVariant> holder1;
        if (spawnGroupData instanceof WolffluePackData wolf$wolfpackdata) {
            holder1 = wolf$wolfpackdata.type;
        } else {
            holder1 = WolfflueVariants.getSpawnVariant(this.registryAccess(), holder);
            spawnGroupData = new WolffluePackData(holder1);
        }

        this.setVariant(holder1);
        this.setSoundVariant(WolfSoundVariants.pickRandomSoundVariant(this.registryAccess(), this.random));

        this.populateDefaultEquipmentSlots(random, difficultyInstance);
        this.populateDefaultEquipmentEnchantments(serverLevelAccessor, random, difficultyInstance);

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance p_217056_) {
        if (randomSource.nextFloat() < 0.05F) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.SILVER_MOON.get()));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if(this.eatCooldown > 0) {
                this.eatCooldown--;
            }
        }
    }

    @Override
    public boolean killedEntity(ServerLevel p_216988_, LivingEntity p_216989_, DamageSource p_432749_) {
        this.eatCooldown = EAT_COOLDOWN;
        return super.killedEntity(p_216988_, p_216989_, p_432749_);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide() && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.isFood(itemstack)) {
                if (this.ticksSinceEaten > 600) {
                    FoodProperties foodproperties = itemstack.get(DataComponents.FOOD);
                    float f = foodproperties != null ? (float) foodproperties.nutrition() : 1.0F;
                    this.heal(f);
                    ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);

                    if (!itemstack1.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
                    }

                    this.ticksSinceEaten = 0;
                    this.eatCooldown = EAT_COOLDOWN;
                } else if (this.ticksSinceEaten > 560 && this.ticksSinceEaten % 5 == 0) {
                    this.playSound(SoundEvents.GENERIC_EAT.value(), 1.0F, 1.0F);
                    this.level().broadcastEntityEvent(this, (byte) 45);
                }
            }
        }

        super.aiStep();
    }

    @Override
    protected void pickUpItem(ServerLevel serverLevel, ItemEntity p_28514_) {
        ItemStack itemstack = p_28514_.getItem();
        if (this.canHoldItem(itemstack)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.dropItemStack(itemstack.split(i - 1));
            }

            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(p_28514_);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(p_28514_, itemstack.getCount());
            p_28514_.discard();
            this.ticksSinceEaten = 0;
        }

    }

    private void spitOutItem(ItemStack p_28602_) {
        if (!p_28602_.isEmpty() && !this.level().isClientSide()) {
            ItemEntity itementity = new ItemEntity(this.level(), this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, p_28602_);
            itementity.setPickUpDelay(40);
            itementity.setThrower(this);
            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level().addFreshEntity(itementity);
        }
    }

    private void dropItemStack(ItemStack p_28606_) {
        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), p_28606_);
        this.level().addFreshEntity(itementity);
    }

    @Override
    public boolean canHoldItem(ItemStack p_28578_) {
        Item item = p_28578_.getItem();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemstack.isEmpty() && this.isFood(p_28578_);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand p_30413_) {
        ItemStack itemstack = player.getItemInHand(p_30413_);
        Item item = itemstack.getItem();
        if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    FoodProperties foodproperties = itemstack.get(DataComponents.FOOD);
                    float f = foodproperties != null ? (float) foodproperties.nutrition() : 1.0F;
                    this.heal(4.0F * f);
                    itemstack.consume(1, player);
                    this.gameEvent(GameEvent.EAT); // Neo: add EAT game event
                    return InteractionResult.SUCCESS_SERVER;
                } else {
                    if (itemstack.is(ItemTags.WOLF_COLLAR_DYES) && this.isOwnedBy(player)) {
                        DyeColor color = itemstack.get(DataComponents.DYE);
                        if (color != null && color != this.getCollarColor()) {
                            this.setCollarColor(color);
                            itemstack.consume(1, player);
                            return InteractionResult.SUCCESS;
                        }

                        return super.mobInteract(player, p_30413_);
                    }


                    if (itemstack.is(FrostItems.SILVER_MOON) && this.isOwnedBy(player) && this.getMainHandItem().isEmpty() && !this.isBaby()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.copyWithCount(1));
                        itemstack.consume(1, player);
                        this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                        return InteractionResult.SUCCESS;
                    }

                    if (itemstack.isEmpty() && player.isSecondaryUseActive() && player.getMainHandItem().isEmpty() && this.isOwnedBy(player) && !this.getMainHandItem().isEmpty()) {
                        ItemStack itemstack1 = this.getMainHandItem();
                        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            this.spawnAtLocation(serverLevel, itemstack1);
                        }
                        return InteractionResult.SUCCESS;
                    }

                    if (this.isEquippableInSlot(itemstack, EquipmentSlot.SADDLE) && this.isOwnedBy(player) && !this.isBaby()) {
                        return itemstack.interactLivingEntity(player, this, p_30413_);
                    } else if (this.isEquippableInSlot(itemstack, EquipmentSlot.BODY) && this.isOwnedBy(player) && this.getBodyArmorItem().isEmpty() && !this.isBaby()) {
                        this.setBodyArmorItem(itemstack.copyWithCount(1));
                        this.setGuaranteedDrop(EquipmentSlot.BODY);
                        itemstack.consume(1, player);
                        return InteractionResult.SUCCESS;
                    } /*else if (itemstack.canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHEARS_REMOVE_ARMOR)
                            && this.isOwnedBy(p_30412_)) {
                        itemstack.hurtAndBreak(1, p_30412_, getSlotForHand(p_30413_));
                        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);
                        this.setItemSlot(EquipmentSlot.SADDLE, ItemStack.EMPTY);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            this.spawnAtLocation(serverLevel, Items.SADDLE);
                        }
                        return InteractionResult.SUCCESS;
                    } else if (itemstack.canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHEARS_REMOVE_ARMOR)
                            && this.isOwnedBy(p_30412_)
                            && this.hasArmor()
                            && (!EnchantmentHelper.has(this.getBodyArmorItem(), EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) || p_30412_.isCreative())) {
                        itemstack.hurtAndBreak(1, p_30412_, getSlotForHand(p_30413_));
                        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);
                        ItemStack itemstack1 = this.getBodyArmorItem();
                        this.setBodyArmorItem(ItemStack.EMPTY);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            this.spawnAtLocation(serverLevel, itemstack1);
                        }
                        return InteractionResult.SUCCESS;
                    }*/ else if (!this.getItemBySlot(EquipmentSlot.SADDLE).isEmpty() && !player.isSecondaryUseActive() && this.isOwnedBy(player)) {
                        this.doPlayerRide(player);
                        if (this.isInSittingPose()) {
                            this.setInSittingPose(false);
                        }
                        return InteractionResult.SUCCESS.withoutItem();
                    }

                    InteractionResult interactionresult = super.mobInteract(player, p_30413_);
                    if (!interactionresult.consumesAction() && this.isOwnedBy(player)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return InteractionResult.SUCCESS.withoutItem();
                    }

                    return interactionresult;
                }
            } else if (!this.level().isClientSide() && this.isFood(itemstack) && !this.isAngry()) {
            itemstack.consume(1, player);
            this.tryToTame(player);
                return InteractionResult.SUCCESS_SERVER;
            } else {
            return super.mobInteract(player, p_30413_);
            }
    }

    @Override
    protected boolean canDispenserEquipIntoSlot(EquipmentSlot p_371599_) {
        return (p_371599_ == EquipmentSlot.BODY || p_371599_ == EquipmentSlot.SADDLE) && this.isTame() || super.canDispenserEquipIntoSlot(p_371599_);
    }

    @Override
    public boolean canUseSlot(EquipmentSlot p_397737_) {
        return p_397737_ != EquipmentSlot.SADDLE ? super.canUseSlot(p_397737_) : this.isAlive() && !this.isBaby() && this.isTame();
    }

    public void tryToTame(Player p_333736_) {
        if (this.random.nextInt(3) == 0 && !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, p_333736_)) {
            this.tame(p_333736_);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }
    }


    @Override
    public boolean isFood(ItemStack p_30440_) {
        return p_30440_.is(FrostTags.Items.WOLFFLUE_FOODS);
    }

    @Override
    public boolean hasArmor() {
        return !this.getBodyArmorItem().isEmpty();
    }


    @Nullable
    public Wolfflue getBreedOffspring(ServerLevel p_149088_, AgeableMob p_149089_) {
        Wolfflue wolf = FrostEntities.WOLFFLUE.get().create(p_149088_, EntitySpawnReason.BREEDING);
        if (wolf != null && p_149089_ instanceof Wolfflue wolf1) {
            if (this.random.nextBoolean()) {
                wolf.setVariant(this.getVariant());
            } else {
                wolf.setVariant(wolf1.getVariant());
            }
            if (this.isTame()) {
                wolf.setOwnerReference(this.getOwnerReference());
                wolf.setTame(true, true);
                if (this.random.nextBoolean()) {
                    wolf.setCollarColor(this.getCollarColor());
                } else {
                    wolf.setCollarColor(wolf1.getCollarColor());
                }
            }
            wolf.setSoundVariant(WolfSoundVariants.pickRandomSoundVariant(this.registryAccess(), this.random));

        }

        return wolf;
    }


    @Override
    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    private class WolffluePackData extends AgeableMobGroupData {
        public final Holder<WolfflueVariant> type;

        public WolffluePackData(Holder<WolfflueVariant> p_332792_) {
            super(false);
            this.type = p_332792_;
        }
    }
}
