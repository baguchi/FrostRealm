package baguchan.frostrealm.entity;

import bagu_chan.bagus_lib.register.ModSensors;
import baguchan.frostrealm.entity.brain.YetiAi;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.*;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Yeti extends AgeableMob implements HuntMob {

	private static final EntityDataAccessor<Boolean> TRADE_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PANIC_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HUNT_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HUNT_LEADER_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);

	protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Yeti>>> SENSOR_TYPES = ImmutableList.of(ModSensors.SMART_NEAREST_LIVING_ENTITY_SENSOR.get(), SensorType.NEAREST_ADULT, SensorType.HURT_BY
			, FrostSensors.YETI_SENSOR.get(), FrostSensors.ENEMY_SENSOR.get(), SensorType.NEAREST_ITEMS);
	protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleType.IS_PANICKING
			, FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), MemoryModuleType.AVOID_TARGET, FrostMemoryModuleType.NEAREST_FROST_BOARS.get(), FrostMemoryModuleType.FROST_BOAR_COUNT.get(), FrostMemoryModuleType.NEAREST_YETIS.get(), FrostMemoryModuleType.YETI_COUNT.get()
			, MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleType.HOME
			, MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM
			, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS);

	private final SimpleContainer inventory = new SimpleContainer(5);
	private int holdTime;

	public AnimationState warmingAnimation = new AnimationState();

	public static final Predicate<? super ItemEntity> ALLOWED_ITEMS = (p_213616_0_) -> {
		return p_213616_0_.getItem().getItem().getFoodProperties() != null && p_213616_0_.getItem().getItem() != Items.SPIDER_EYE && p_213616_0_.getItem().getItem() != Items.PUFFERFISH || p_213616_0_.getItem().is(FrostTags.Items.YETI_CURRENCY) || p_213616_0_.getItem().is(FrostTags.Items.YETI_BIG_CURRENCY);
	};


	public Yeti(EntityType<? extends Yeti> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		this.getNavigation().setCanFloat(true);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void customServerAiStep() {
		this.level.getProfiler().push("boarBrain");
		this.getBrain().tick((ServerLevel) this.level, this);
		this.level.getProfiler().pop();
		this.level.getProfiler().push("boarActivityUpdate");
		YetiAi.updateActivity(this);
		this.level.getProfiler().pop();
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
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TRADE_ID, false);
		this.entityData.define(PANIC_ID, false);
		this.entityData.define(HUNT_ID, false);
		this.entityData.define(HUNT_LEADER_ID, false);
	}

	public void setTrade(boolean trade) {
		this.entityData.set(TRADE_ID, trade);
	}

	public boolean isTrade() {
		return this.entityData.get(TRADE_ID);
	}

	public void setPanic(boolean panic) {
		this.entityData.set(PANIC_ID, panic);
	}

	public boolean isPanic() {
		return this.entityData.get(PANIC_ID);
	}

	public void setHunt(boolean hunt) {
		this.entityData.set(HUNT_ID, hunt);
	}

	public boolean isHunt() {
		return this.entityData.get(HUNT_ID);
	}

	public void setHuntLeader(boolean leader) {
		this.entityData.set(HUNT_LEADER_ID, leader);
	}

	public boolean isHuntLeader() {
		return this.entityData.get(HUNT_LEADER_ID);
	}

	@Override
	protected PathNavigation createNavigation(Level p_33348_) {
		return new FrostPathNavigation(this, p_33348_);
	}

	public static AttributeSupplier.Builder createAttributeMap() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.24F).add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ATTACK_DAMAGE, 6.0F);
	}

	@Override
	protected void completeUsingItem() {
		InteractionHand hand = this.getUsedItemHand();
		if (this.useItem.equals(this.getItemInHand(hand))) {
			if (!this.useItem.isEmpty() && this.isUsingItem()) {
				ItemStack copy = this.useItem.copy();

				if (copy.getItem().getFoodProperties() != null) {
					this.heal(copy.getItem().getFoodProperties().getNutrition());
				}
			}
		}
		super.completeUsingItem();
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void aiStep() {
		this.updateSwingTime();
		if (!this.level.isClientSide && this.isAlive()) {
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
			if (!itemstack.isEmpty() && itemstack.getItem().getFoodProperties() != null) {
				return itemstack.split(1);
			}
		}
		return ItemStack.EMPTY;
	}


	public boolean wantsToPickUp(ItemStack p_34777_) {
		return net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) && this.canPickUpLoot() && YetiAi.wantsToPickup(this, p_34777_);
	}

	@Override
	public void pickUpItem(ItemEntity p_175445_1_) {
		ItemStack itemstack = p_175445_1_.getItem();
		Item item = itemstack.getItem();
		if (itemstack.is(FrostTags.Items.YETI_CURRENCY)) {
			this.onItemPickup(p_175445_1_);
			this.take(p_175445_1_, 1);
			YetiAi.holdInOffHand(this, itemstack.split(1));
			this.setTrade(true);
			if (itemstack.isEmpty()) {
				p_175445_1_.discard();
			} else {
				itemstack.setCount(itemstack.getCount());
			}
			this.holdTime = 200;
		} else if (item.getFoodProperties() != null) {
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

	public boolean canReplaceCurrentItem(ItemStack p_34788_) {
		EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(p_34788_);
		ItemStack itemstack = this.getItemBySlot(equipmentslot);
		return this.canReplaceCurrentItem(p_34788_, itemstack);
	}

	private boolean wantsFood(ItemStack p_213672_1_) {
		return p_213672_1_.getItem().getFoodProperties() != null;
	}


	protected void dropEquipment() {
		super.dropEquipment();
		if (this.inventory != null) {
			for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
				ItemStack itemstack = this.inventory.getItem(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}
		}
	}
	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public int getHoldTime() {
		return holdTime;
	}

	@Override
	public boolean wasKilled(ServerLevel p_216988_, LivingEntity p_216989_) {
		return super.wasKilled(p_216988_, p_216989_);
	}

	public void readAdditionalSaveData(CompoundTag p_29541_) {
		super.readAdditionalSaveData(p_29541_);
		ListTag listnbt = p_29541_.getList("Inventory", 10);

		for (int i = 0; i < listnbt.size(); ++i) {
			ItemStack itemstack = ItemStack.of(listnbt.getCompound(i));
			if (!itemstack.isEmpty()) {
				this.inventory.addItem(itemstack);
			}
		}

		this.setHunt(p_29541_.getBoolean("Hunt"));
		this.setHuntLeader(p_29541_.getBoolean("HuntLeader"));
		this.setHoldTime(p_29541_.getInt("HoldTime"));
		this.setTrade(p_29541_.getBoolean("Trade"));
	}

	public void addAdditionalSaveData(CompoundTag p_29548_) {
		super.addAdditionalSaveData(p_29548_);
		ListTag listnbt = new ListTag();

		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty()) {
				listnbt.add(itemstack.save(new CompoundTag()));
			}
		}

		p_29548_.put("Inventory", listnbt);

		p_29548_.putBoolean("Hunt", this.isHunt());
		p_29548_.putBoolean("HuntLeader", this.isHuntLeader());
		p_29548_.putInt("HoldTime", holdTime);
		p_29548_.putBoolean("Trade", this.isTrade());
	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, MobSpawnType p_29535_, @Nullable SpawnGroupData p_29536_, @Nullable CompoundTag p_29537_) {
		if (p_29536_ == null) {

			if (p_29535_ == MobSpawnType.PATROL) {
				p_29536_ = new YetiGroupData(true, 0);
			} else {
				p_29536_ = new YetiGroupData(false, 1F);
			}
		}
		this.inventory.addItem(new ItemStack(Items.SALMON, 4));
		YetiAi.initMemories(this, p_29533_.getRandom(), p_29535_);


		this.populateDefaultEquipmentSlots(p_29533_.getRandom(), p_29534_);
		this.populateDefaultEquipmentEnchantments(p_29533_.getRandom(), p_29534_);

		return super.finalizeSpawn(p_29533_, p_29534_, p_29535_, p_29536_, p_29537_);
	}

	protected void populateDefaultEquipmentSlots(RandomSource p_219165_, DifficultyInstance p_219166_) {
		boolean flag = false;
		boolean flag2 = false;

		if (this.isHuntLeader()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.ASTRIUM_AXE.get()));
			flag = true;
		}

		if (!this.isHuntLeader() && this.isHunt()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.ASTRIUM_SWORD.get()));
			flag2 = true;
		}

		if (flag) {
			this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.ASTRIUM_HELMET.get()));
		}
		if (flag2) {
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
		if (this.level.isClientSide) {
			return false;
		} else {
			YetiAi.stopHoldingOffHandItem(this, false);
			if (flag && p_34503_.getEntity() instanceof LivingEntity) {
				YetiAi.wasHurtBy(this, (LivingEntity) p_34503_.getEntity());
			}

			return flag;
		}
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.YETI.get().create(p_146743_);
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return this.isHunt();
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
}
