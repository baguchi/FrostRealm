package baguchan.frostrealm.entity;

import bagu_chan.bagus_lib.register.ModSensors;
import baguchan.frostrealm.entity.brain.YetiAi;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Yeti extends AgeableMob {
	private static final EntityDataAccessor<String> DATA_STATE = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.LONG);

	protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Yeti>>> SENSOR_TYPES = ImmutableList.of(ModSensors.SMART_NEAREST_LIVING_ENTITY_SENSOR.get(), SensorType.NEAREST_ADULT, SensorType.HURT_BY
			, FrostSensors.YETI_SENSOR.get(), FrostSensors.ENEMY_SENSOR.get(), SensorType.NEAREST_ITEMS);
	protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleType.IS_PANICKING
			, FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), MemoryModuleType.AVOID_TARGET, FrostMemoryModuleType.NEAREST_YETIS.get(), FrostMemoryModuleType.YETI_COUNT.get()
			, MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleType.HOME
			, MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM
			, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS);

	private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(FrostEntities.YETI.get().getWidth(), FrostEntities.YETI.get().getHeight() - 0.35F)
			.withEyeHeight(1.4F);

	private final SimpleContainer inventory = new SimpleContainer(5);
	private int holdTime;

	public final AnimationState sitAnimationState = new AnimationState();
	public final AnimationState sitPoseAnimationState = new AnimationState();

	public final AnimationState sitUpAnimationState = new AnimationState();

	public static final Predicate<? super ItemEntity> ALLOWED_ITEMS = (p_213616_0_) -> {
		return p_213616_0_.getItem().getItem() != Items.SPIDER_EYE && p_213616_0_.getItem().getItem() != Items.PUFFERFISH || p_213616_0_.getItem().is(FrostTags.Items.YETI_CURRENCY) || p_213616_0_.getItem().is(FrostTags.Items.YETI_BIG_CURRENCY);
	};


	public Yeti(EntityType<? extends Yeti> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		this.getNavigation().setCanFloat(true);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("boarBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("boarActivityUpdate");
		YetiAi.updateActivity(this);
		this.level().getProfiler().pop();
	}

	protected Brain.Provider<Yeti> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	protected Brain<?> makeBrain(Dynamic<?> p_35064_) {
		return YetiAi.makeBrain(this, this.brainProvider().makeBrain(p_35064_));
	}

	public Brain<Yeti> getBrain() {
		return (Brain<Yeti>) super.getBrain();
	}

	@javax.annotation.Nullable
	public LivingEntity getTarget() {
		return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse((LivingEntity) null);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_STATE, State.IDLING.name());
		builder.define(LAST_POSE_CHANGE_TICK, 0L);
	}

	public boolean isYetiSitting() {
		return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
	}

	public boolean isYetiVisuallySitting() {
		return this.getPoseTime() < 0L != this.isYetiSitting();
	}

	public boolean isInPoseTransition() {
		long i = this.getPoseTime();
		return i < (long) (this.isYetiSitting() ? 40 : 52);
	}

	private boolean isVisuallySittingDown() {
		return this.isYetiSitting() && this.getPoseTime() < 40L && this.getPoseTime() >= 0L;
	}

	public void sitDown() {
		if (!this.isYetiSitting()) {
			//this.makeSound(SoundEvents.CAMEL_SIT);
			this.setPose(Pose.SITTING);
			this.gameEvent(GameEvent.ENTITY_ACTION);
			this.resetLastPoseChangeTick(-this.level().getGameTime());
		}
	}

	public void standUp() {
		if (this.isYetiSitting()) {
			//this.makeSound(SoundEvents.CAMEL_STAND);
			this.setPose(Pose.STANDING);
			this.gameEvent(GameEvent.ENTITY_ACTION);
			this.resetLastPoseChangeTick(this.level().getGameTime());
		}
	}

	public void standUpInstantly() {
		this.setPose(Pose.STANDING);
		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
	}

	@VisibleForTesting
	public void resetLastPoseChangeTick(long p_248642_) {
		this.entityData.set(LAST_POSE_CHANGE_TICK, p_248642_);
	}

	private void resetLastPoseChangeTickToFullStand(long p_265447_) {
		this.resetLastPoseChangeTick(Math.max(0L, p_265447_ - 52L - 1L));
	}

	public long getPoseTime() {
		return this.level().getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
	}


	public boolean canYetiChangePose() {
		return this.wouldNotSuffocateAtTargetPose(this.isYetiSitting() ? Pose.STANDING : Pose.SITTING) && this.getState().equals(State.IDLING.name()) && this.getTarget() == null;
	}

	public boolean refuseToMove() {
		return this.isYetiSitting() || this.isInPoseTransition();
	}


	public boolean isTrade() {
		return State.get(this.entityData.get(DATA_STATE)) == State.TRADE;
	}


	public boolean isCheer() {
		return State.get(this.entityData.get(DATA_STATE)) == State.CHEER;
	}

	public boolean isPanic() {
		return State.get(this.entityData.get(DATA_STATE)) == State.PANIC;
	}

	public void setState(State state) {
		this.standUpInstantly();
		this.entityData.set(DATA_STATE, state.name());
	}

	private void setStateName(String state) {
		this.entityData.set(DATA_STATE, state);
	}

	public String getState() {
		return this.entityData.get(DATA_STATE);
	}

	@Override
	protected PathNavigation createNavigation(Level p_33348_) {
		return new FrostPathNavigation(this, p_33348_);
	}

	public static AttributeSupplier.Builder createAttributeMap() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.26F).add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.ATTACK_DAMAGE, 6.0F);
	}

	@Override
	protected void completeUsingItem() {
		InteractionHand hand = this.getUsedItemHand();
		if (this.useItem.equals(this.getItemInHand(hand))) {
			if (!this.useItem.isEmpty() && this.isUsingItem()) {
				ItemStack copy = this.useItem.copy();

				if (copy.getFoodProperties(this) != null) {
					this.heal(copy.getFoodProperties(this).nutrition());
				}
			}
		}
		super.completeUsingItem();
	}


	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			this.setupAnimationStates();
		}

		if (this.isYetiSitting() && this.isInWater()) {
			this.standUpInstantly();
		}
	}

	private void setupAnimationStates() {

		if (this.isYetiVisuallySitting()) {
			this.sitUpAnimationState.stop();
			if (this.isVisuallySittingDown()) {
				this.sitAnimationState.startIfStopped(this.tickCount);
				this.sitPoseAnimationState.stop();
			} else {
				this.sitAnimationState.stop();
				this.sitPoseAnimationState.startIfStopped(this.tickCount);
			}
		} else {
			this.sitAnimationState.stop();
			this.sitPoseAnimationState.stop();
			;
			this.sitUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
		}
	}

	@Override
	public void aiStep() {
		this.updateSwingTime();
		if (!this.level().isClientSide && this.isAlive()) {
			ItemStack offhand = this.getItemInHand(InteractionHand.OFF_HAND);

			if (!this.isUsingItem() && offhand.isEmpty()) {
				ItemStack food = ItemStack.EMPTY;

				if (this.getHealth() < this.getMaxHealth() && this.random.nextFloat() < 0.0025F) {
					food = this.findFood();
				}

				if (!food.isEmpty()) {
					this.setItemSlot(EquipmentSlot.OFFHAND, food);
					this.startUsingItem(InteractionHand.OFF_HAND);
				}
			}

			if (!this.isBaby()) {
				if (offhand.is(FrostTags.Items.YETI_BIG_CURRENCY) || offhand.is(FrostTags.Items.YETI_CURRENCY)) {
					if (--this.holdTime <= 0) {
						YetiAi.stopHoldingOffHandItem(this, true);
					}
				}
			}
		}

		super.aiStep();
	}


	private ItemStack findFood() {
		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty() && itemstack.getFoodProperties(this) != null) {
				return itemstack.split(1);
			}
		}
		return ItemStack.EMPTY;
	}


	public boolean wantsToPickUp(ItemStack p_34777_) {
		return EventHooks.canEntityGrief(this.level(), this) && this.canPickUpLoot() && YetiAi.wantsToPickup(this, p_34777_);
	}

	@Override
	public void pickUpItem(ItemEntity p_175445_1_) {
		ItemStack itemstack = p_175445_1_.getItem();
		Item item = itemstack.getItem();
		if (itemstack.is(FrostTags.Items.YETI_CURRENCY) && this.isAdult()) {
			this.onItemPickup(p_175445_1_);
			this.take(p_175445_1_, 1);
			YetiAi.holdInOffHand(this, itemstack.split(1));
			this.setState(State.TRADE);
			if (itemstack.isEmpty()) {
				p_175445_1_.discard();
			} else {
				itemstack.setCount(itemstack.getCount());
			}
			this.holdTime = 200;
		} else if (itemstack.getFoodProperties(this) != null) {
			this.onItemPickup(p_175445_1_);
			this.take(p_175445_1_, itemstack.getCount());
			ItemStack itemstack1 = this.inventory.addItem(itemstack);
			if (itemstack1.isEmpty()) {
				p_175445_1_.discard();
			} else {
				itemstack.setCount(itemstack1.getCount());
			}
		} else {
			super.pickUpItem(p_175445_1_);
		}
	}

	public void holdInOffHand(ItemStack p_34784_) {
		this.setItemSlotAndDropWhenKilled(EquipmentSlot.OFFHAND, p_34784_);
	}

	public ItemStack addToInventory(ItemStack p_34779_) {
		return this.inventory.addItem(p_34779_);
	}

	public boolean canAddToInventory(ItemStack p_34781_) {
		return this.inventory.canAddItem(p_34781_);
	}

	protected boolean canReplaceCurrentItem(ItemStack p_34788_) {
		EquipmentSlot equipmentslot = this.getEquipmentSlotForItem(p_34788_);
		ItemStack itemstack = this.getItemBySlot(equipmentslot);
		return this.canReplaceCurrentItem(p_34788_, itemstack);
	}

	private boolean wantsFood(ItemStack p_213672_1_) {
		return p_213672_1_.getFoodProperties(this) != null;
	}


	protected void dropEquipment() {
		super.dropEquipment();
		this.inventory.removeAllItems().forEach(this::spawnAtLocation);
	}
	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public int getHoldTime() {
		return holdTime;
	}

	public void readAdditionalSaveData(CompoundTag p_29541_) {
		super.readAdditionalSaveData(p_29541_);
		ListTag listnbt = p_29541_.getList("Inventory", 10);

		for (int i = 0; i < listnbt.size(); ++i) {
			ItemStack itemstack = ItemStack.parse(this.registryAccess(), listnbt.getCompound(i)).orElse(ItemStack.EMPTY);
			if (!itemstack.isEmpty()) {
				this.inventory.addItem(itemstack);
			}
		}
		this.setHoldTime(p_29541_.getInt("HoldTime"));
		this.setStateName(p_29541_.getString("State"));
		long i = p_29541_.getLong("LastPoseTick");
		if (i < 0L) {
			this.setPose(Pose.SITTING);
		}

		this.resetLastPoseChangeTick(i);
	}

	public void addAdditionalSaveData(CompoundTag p_29548_) {
		super.addAdditionalSaveData(p_29548_);
		ListTag listnbt = new ListTag();

		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty()) {
				listnbt.add(itemstack.save(this.registryAccess(), new CompoundTag()));
			}
		}

		p_29548_.put("Inventory", listnbt);
		p_29548_.putInt("HoldTime", holdTime);
		p_29548_.putString("State", this.getState());
		p_29548_.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));

	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, MobSpawnType p_29535_, @Nullable SpawnGroupData p_29536_) {
		if (p_29536_ == null) {

			if (p_29535_ == MobSpawnType.PATROL) {
				p_29536_ = new YetiGroupData(true, 0);
			} else {
				p_29536_ = new YetiGroupData(false, 1F);
			}
		}
		this.inventory.addItem(new ItemStack(Items.SALMON, 4));
		YetiAi.initMemories(this, p_29533_.getRandom(), p_29535_);

		this.resetLastPoseChangeTickToFullStand(p_29533_.getLevel().getGameTime());
		if (p_29535_ == MobSpawnType.STRUCTURE) {
			GlobalPos globalpos = GlobalPos.of(p_29533_.getLevel().dimension(), this.blockPosition());
			this.getBrain().setMemory(MemoryModuleType.HOME, globalpos);
		}

		this.populateDefaultEquipmentSlots(p_29533_.getRandom(), p_29534_);
		this.populateDefaultEquipmentEnchantments(p_29533_, p_29533_.getRandom(), p_29534_);

		return super.finalizeSpawn(p_29533_, p_29534_, p_29535_, p_29536_);
	}

	protected void populateDefaultEquipmentSlots(RandomSource p_219165_, DifficultyInstance p_219166_) {
		if (p_219165_.nextFloat() < 0.1F) {
			this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.FROST_BOAR_FUR_HELMET.get()));
		}
	}

	@Override
	public boolean doHurtTarget(Entity p_21372_) {
		if (p_21372_ instanceof LivingEntity) {
			YetiAi.onHitTarget(this, (LivingEntity) p_21372_);
		}
		return super.doHurtTarget(p_21372_);
	}

	@Override
	public boolean hurt(DamageSource p_34503_, float p_34504_) {
		boolean flag = super.hurt(p_34503_, p_34504_);
		if (this.level().isClientSide) {
			return false;
		} else {
			this.standUpInstantly();
			YetiAi.stopHoldingOffHandItem(this, false);
			if (flag && p_34503_.getEntity() instanceof LivingEntity) {
				YetiAi.wasHurtBy(this, (LivingEntity) p_34503_.getEntity());
			}

			return flag;
		}
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_316664_) {
		return p_316664_ == Pose.SITTING ? SITTING_DIMENSIONS.scale(this.getAgeScale()) : super.getDefaultDimensions(p_316664_);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.YETI.get().create(p_146743_);
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}

	public boolean isAdult() {
		return !this.isBaby();
	}

	public boolean canAttack(LivingEntity p_186270_) {
		return p_186270_ instanceof Yeti ? false : super.canAttack(p_186270_);
	}


	public static class YetiGroupData extends AgeableMobGroupData {
		public final boolean isHunt;
		public final float child;

		public YetiGroupData(boolean p_34358_, float child) {
			super(false);
			this.isHunt = p_34358_;
			this.child = child;
		}
	}

	public static enum State {
		IDLING,
		TRADE,
		PANIC,
		CHEER;

		public static State get(String nameIn) {
			for (State role : values()) {
				if (role.name().equals(nameIn))
					return role;
			}
			return State.IDLING;
		}

	}
}
