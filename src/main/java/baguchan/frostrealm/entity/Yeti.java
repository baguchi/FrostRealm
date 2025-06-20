package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.brain.YetiAi;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;

public class Yeti extends AgeableMob implements HasContainerEntity, SnowChargeMob, InventoryCarrier {
	private static final EntityDataAccessor<String> DATA_STATE = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.LONG);

	protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Yeti>>> SENSOR_TYPES = ImmutableList.of(baguchi.bagus_lib.register.ModSensors.SMART_NEAREST_LIVING_ENTITY_SENSOR.get(), SensorType.NEAREST_ADULT, SensorType.HURT_BY
			, FrostSensors.YETI_SENSOR.get(), FrostSensors.ENEMY_SENSOR.get(), SensorType.NEAREST_ITEMS);
	protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleType.IS_PANICKING
			, FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), MemoryModuleType.AVOID_TARGET, FrostMemoryModuleType.NEAREST_YETIS.get(), FrostMemoryModuleType.YETI_COUNT.get()
			, MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleType.HOME
			, MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM
			, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, FrostMemoryModuleType.TAKE_BACK_TARGET.get(), FrostMemoryModuleType.TAKE_BACK_COOLDOWN.get());

	private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(FrostEntities.YETI.get().getWidth(), FrostEntities.YETI.get().getHeight() - 0.35F)
			.withEyeHeight(1.4F);

	private final SimpleContainer inventory = new SimpleContainer(5);
	private int holdTime;

	public final AnimationState sitAnimationState = new AnimationState();
	public final AnimationState sitPoseAnimationState = new AnimationState();

	public final AnimationState sitUpAnimationState = new AnimationState();
	public final AnimationState noticedStealerAnimationState = new AnimationState();
	public final AnimationState snowChargeAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();
	private int ticksIdle = 1200;
	public Yeti(EntityType<? extends Yeti> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		this.getNavigation().setCanFloat(true);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void customServerAiStep(ServerLevel serverLevel) {
		ProfilerFiller profiler = Profiler.get();
		profiler.push("yetiBrain");
		this.getBrain().tick(serverLevel, this);
		profiler.pop();
		profiler.push("yetiActivityUpdate");
		YetiAi.updateActivity(this);
		profiler.pop();

		if (this.isAlive()) {
			ItemStack offhand = this.getItemInHand(InteractionHand.MAIN_HAND);

			if (!this.isUsingItem() && offhand.isEmpty()) {
				ItemStack food = ItemStack.EMPTY;

				if (this.getHealth() < this.getMaxHealth() && this.random.nextFloat() < 0.0025F) {
					food = this.findFood();
				}

				if (!food.isEmpty()) {
					this.setItemSlot(EquipmentSlot.MAINHAND, food);
					this.startUsingItem(InteractionHand.MAIN_HAND);
				}
			}

			if (!this.isBaby()) {
				if (--this.holdTime <= 0) {
					YetiAi.stopHoldingOffHandItem(serverLevel, this, true);
				}
			}

			if (this.isYetiSitting()) {
				if (this.ticksIdle > 0) {
					--this.ticksIdle;
				} else {
					serverLevel.broadcastEntityEvent(this, (byte) 5);
					this.spawnAtLocation(serverLevel, new ItemStack(FrostItems.YETI_FUR.asItem(), 1));

					this.ticksIdle = 1200 + random.nextInt(600);
				}
			}
		}

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

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_312373_) {
		if (this.level().isClientSide() && DATA_STATE.equals(p_312373_)) {
			if (this.isSameStatue(State.CHASING)) {
				this.noticedStealerAnimationState.start(this.tickCount);
			}
			if (this.isSameStatue(State.SNOWBALL_MAKING)) {
				this.snowChargeAnimationState.start(this.tickCount);
			} else {
				this.snowChargeAnimationState.stop();
			}

		}

		super.onSyncedDataUpdated(p_312373_);
	}


	@org.jetbrains.annotations.Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return FrostSounds.YETI_IDLE.get();
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
		return this.wouldNotSuffocateAtTargetPose(this.isYetiSitting() ? Pose.STANDING : Pose.SITTING) && State.get(this.getState()) == State.IDLING && this.getTarget() == null;
	}

	public boolean refuseToMove() {
		return this.isYetiSitting() || this.isInPoseTransition();
	}


	public boolean isTrade() {
		return State.get(this.entityData.get(DATA_STATE)) == State.TRADE;
	}

	public boolean isSameStatue(State state) {
		return State.get(this.entityData.get(DATA_STATE)) == state;
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
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.26F).add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ATTACK_DAMAGE, 6.0F);
	}

	@Override
	protected void completeUsingItem() {
		InteractionHand hand = this.getUsedItemHand();
		if (this.useItem.equals(this.getItemInHand(hand))) {
			if (!this.useItem.isEmpty() && this.isUsingItem()) {
				ItemStack copy = this.useItem.copy();

				if (copy.get(DataComponents.FOOD) != null) {
					FoodProperties foodproperties = copy.get(DataComponents.FOOD);
					float f = foodproperties != null ? (float) foodproperties.nutrition() : 1.0F;
					this.heal(f);
				}
			}
		}
		super.completeUsingItem();
	}

	@Override
	public InteractionResult mobInteract(Player p_34745_, InteractionHand p_34746_) {
		InteractionResult interactionresult = super.mobInteract(p_34745_, p_34746_);
		if (interactionresult.consumesAction()) {
			return interactionresult;
		} else if (this.level() instanceof ServerLevel serverlevel) {
			return YetiAi.mobInteract(serverlevel, this, p_34745_, p_34746_);
		} else {
			boolean flag = YetiAi.canAdmire(this, p_34745_.getItemInHand(p_34746_)) && State.get(this.getState()) != State.TRADE;
			boolean flag2 = this.isTrade();
			return (InteractionResult) (flag || flag2 ? InteractionResult.SUCCESS : InteractionResult.PASS);
		}
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


	@Override
	public void handleEntityEvent(byte p_21375_) {
		super.handleEntityEvent(p_21375_);
		if (p_21375_ == 5) {
			this.idleAnimationState.start(this.tickCount);
		} else {
			super.handleEntityEvent(p_21375_);
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
			this.sitUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
		}
	}

	@Override
	public void aiStep() {
		this.updateSwingTime();

		super.aiStep();
	}

	public ItemStack hasFood() {
		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty() && itemstack.get(DataComponents.FOOD) != null) {
				return itemstack;
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack findFood() {
		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty() && itemstack.get(DataComponents.FOOD) != null) {
				return itemstack.split(1);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean wantsToPickUp(ServerLevel serverLevel, ItemStack p_34777_) {
		return EventHooks.canEntityGrief(serverLevel, this) && this.canPickUpLoot() && YetiAi.wantsToPickup(this, p_34777_);
	}

	@Override
	public void pickUpItem(ServerLevel serverLevel, ItemEntity p_175445_1_) {
		ItemStack itemstack = p_175445_1_.getItem();
		Item item = itemstack.getItem();
		if (itemstack.get(DataComponents.FOOD) != null) {
			this.onItemPickup(p_175445_1_);
			this.take(p_175445_1_, itemstack.getCount());
			ItemStack itemstack1 = this.inventory.addItem(itemstack);
			if (itemstack1.isEmpty()) {
				p_175445_1_.discard();
			} else {
				itemstack.setCount(itemstack1.getCount());
			}
		} else {
			super.pickUpItem(serverLevel, p_175445_1_);
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

	@Override
	public SimpleContainer getInventory() {
		return inventory;
	}

	@Override
	protected void dropEquipment(ServerLevel serverLevel) {
		super.dropEquipment(serverLevel);
		this.inventory.removeAllItems().forEach(item -> spawnAtLocation(serverLevel, item));
	}
	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public int getHoldTime() {
		return holdTime;
	}

    @Override
	public void readAdditionalSaveData(ValueInput p_29541_) {
		super.readAdditionalSaveData(p_29541_);

		this.setHoldTime(p_29541_.getIntOr("HoldTime", 0));
		this.setStateName(p_29541_.getStringOr("State", "IDLING"));
		long i = p_29541_.getLongOr("LastPoseTick", 0L);
		if (i < 0L) {
			this.setPose(Pose.SITTING);
		}
		this.ticksIdle = p_29541_.getIntOr("IdleTime", 0);

		this.resetLastPoseChangeTick(i);
	}

    @Override
	public void addAdditionalSaveData(ValueOutput p_29548_) {
		super.addAdditionalSaveData(p_29548_);

		p_29548_.putInt("HoldTime", holdTime);
		p_29548_.putInt("IdleTime", this.ticksIdle);
		p_29548_.putString("State", this.getState());
		p_29548_.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
	}

    @Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, EntitySpawnReason p_29535_, @Nullable SpawnGroupData p_29536_) {
		if (p_29536_ == null) {

			if (p_29535_ == EntitySpawnReason.PATROL) {
				p_29536_ = new YetiGroupData(true, 0);
			} else {
				p_29536_ = new YetiGroupData(false, 1F);
			}
		}
		this.inventory.addItem(new ItemStack(Items.SALMON, 4));
		YetiAi.initMemories(this, p_29533_.getRandom(), p_29535_);

		this.resetLastPoseChangeTickToFullStand(p_29533_.getLevel().getGameTime());
		if (p_29535_ == EntitySpawnReason.STRUCTURE) {
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
	public boolean doHurtTarget(ServerLevel serverLevel, Entity p_21372_) {
		if (p_21372_ instanceof LivingEntity) {
			YetiAi.onHitTarget(this, (LivingEntity) p_21372_);
		}
		return super.doHurtTarget(serverLevel, p_21372_);
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource p_34503_, float p_34504_) {
		boolean flag = super.hurtServer(serverLevel, p_34503_, p_34504_);
		if (this.level().isClientSide) {
			return false;
		} else {
			this.standUpInstantly();
			YetiAi.stopHoldingOffHandItem(serverLevel, this, false);
			if (flag && p_34503_.getEntity() instanceof LivingEntity) {
				YetiAi.wasHurtBy(serverLevel, this, (LivingEntity) p_34503_.getEntity());
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
		return FrostEntities.YETI.get().create(p_146743_, EntitySpawnReason.BREEDING);
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}

	public boolean isAdult() {
		return !this.isBaby();
	}

	public boolean isMeleeAttack() {
		return this.isAdult() && !this.isSnowCharge();
	}

	public boolean isSnowAttack() {
		return this.isAdult() && this.isSnowCharge();
	}

	public boolean canAttack(LivingEntity p_186270_) {
		return p_186270_ instanceof Yeti ? false : super.canAttack(p_186270_);
	}

	@Override
	protected boolean considersEntityAsAlly(Entity p_360600_) {
		if (super.considersEntityAsAlly(p_360600_)) {
			return true;
		} else {
			return p_360600_.getType() != FrostEntities.YETI.get() ? false : this.getTeam() == null && p_360600_.getTeam() == null;
		}
	}

	@Override
	public void setSnowCharge(boolean b) {
		if (b) {
			this.setState(State.SNOWBALL_MAKING);
		} else {
			this.setState(State.IDLING);
		}
	}

	@Override
	public boolean isSnowCharge() {
		return State.get(this.getState()) == State.SNOWBALL_MAKING;
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
		SNOWBALL_MAKING,
		CHASING,
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
