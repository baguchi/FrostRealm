package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.*;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostTags;
import baguchan.frostrealm.utils.ai.YetiAi;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Yeti extends AgeableMob implements NeutralMob, HuntMob {

	private static final EntityDataAccessor<Boolean> TRADE_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PANIC_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HUNT_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HUNT_LEADER_ID = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(30, 59);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	private final SimpleContainer inventory = new SimpleContainer(5);
	@Nullable
	private BlockPos homeTarget;
	private int huntTime;
	private int holdTime;

	public AnimationState warmingAnimation = new AnimationState();

	public static final Predicate<? super ItemEntity> ALLOWED_ITEMS = (p_213616_0_) -> {
		return p_213616_0_.getItem().getItem().getFoodProperties() != null && p_213616_0_.getItem().getItem() != Items.SPIDER_EYE && p_213616_0_.getItem().getItem() != Items.PUFFERFISH || p_213616_0_.getItem().is(FrostTags.Items.YETI_CURRENCY) || p_213616_0_.getItem().is(FrostTags.Items.YETI_BIG_CURRENCY);
	};


	public Yeti(EntityType<? extends Yeti> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
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
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new Yeti.YetiMeleeAttackGoal());
		this.goalSelector.addGoal(1, new Yeti.YetiPanicGoal());
		this.goalSelector.addGoal(3, new GetFoodGoal<>(this));
		this.goalSelector.addGoal(4, new CreatureFollowParentGoal(this, 1.15D));
		this.goalSelector.addGoal(5, new SeekShelterEvenBlizzardGoal(this, 1.2D));
		this.goalSelector.addGoal(6, new MoveToGoal(this, 40.0D, 1.2D));
		this.goalSelector.addGoal(7, new FollowHuntLeaderGoal(this));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.9D));
		this.goalSelector.addGoal(8, new LookAtPlayerAndPanicGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new Yeti.YetiHurtByTargetGoal());
		this.targetSelector.addGoal(3, new YetiTargetGoal<>(this, AbstractPiglin.class));
		this.targetSelector.addGoal(3, new YetiTargetGoal<>(this, Villager.class));
		this.targetSelector.addGoal(3, new YetiTargetGoal<>(this, WitherSkeleton.class));
		this.targetSelector.addGoal(4, new HuntTargetGoal<>(this, FrostBoar.class));
		this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	protected PathNavigation createNavigation(Level p_33348_) {
		return new FrostPathNavigation(this, p_33348_);
	}

	public static AttributeSupplier.Builder createAttributeMap() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.24F).add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ATTACK_DAMAGE, 6.0F);
	}

	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) {
		YetiAi.stopHoldingMainHandItem(this, false);
		if (this.isBaby() && !this.isPanic()) {
			this.setPanic(true);
		}
		return super.hurt(p_21016_, p_21017_);
	}

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
						YetiAi.stopHoldingMainHandItem(this, true);
					}
				}
			}
		}

		super.aiStep();
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level, true);
		}
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

	@Override
	public void pickUpItem(ItemEntity p_175445_1_) {
		ItemStack itemstack = p_175445_1_.getItem();
		Item item = itemstack.getItem();
		if (itemstack.is(FrostTags.Items.YETI_LOVED)) {
			this.onItemPickup(p_175445_1_);
			this.take(p_175445_1_, itemstack.getCount());
			YetiAi.holdInMainHand(this, itemstack.copy());
			this.setTrade(true);
			p_175445_1_.discard();
			this.holdTime = 200;
		} else if (this.wantsFood(itemstack)) {
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

	public void holdInMainHand(ItemStack p_34784_) {
		this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, p_34784_);
	}

	public ItemStack addToInventory(ItemStack p_34779_) {
		return this.inventory.addItem(p_34779_);
	}

	protected boolean canAddToInventory(ItemStack p_34781_) {
		return this.inventory.canAddItem(p_34781_);
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

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();

		if (huntTime > 0) {
			--this.huntTime;
		}
	}

	public static void angerNearbyYeti(Player p_34874_, boolean p_34875_) {
		List<Yeti> list = p_34874_.level.getEntitiesOfClass(Yeti.class, p_34874_.getBoundingBox().inflate(16.0D));
		list.stream().filter((p_34881_) -> {
			return !p_34875_ || p_34881_.hasLineOfSight(p_34874_);
		}).forEach((p_34872_) -> {
			p_34872_.setTarget(p_34874_);
			p_34872_.setRemainingPersistentAngerTime(600);
			p_34872_.setPersistentAngerTarget(p_34874_.getUUID());
		});
	}

	public boolean isHunted() {
		return huntTime > 0;
	}

	public void setHuntTime(int huntTime) {
		this.huntTime = huntTime;
	}

	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public int getHoldTime() {
		return holdTime;
	}

	@Override
	public boolean wasKilled(ServerLevel p_216988_, LivingEntity p_216989_) {
		setHuntTime(600);
		return super.wasKilled(p_216988_, p_216989_);
	}

	public void readAdditionalSaveData(CompoundTag p_29541_) {
		super.readAdditionalSaveData(p_29541_);
		this.readPersistentAngerSaveData(this.level, p_29541_);
		if (p_29541_.contains("HomeTarget")) {
			this.homeTarget = NbtUtils.readBlockPos(p_29541_.getCompound("HomeTarget"));
		}
		ListTag listnbt = p_29541_.getList("Inventory", 10);

		for (int i = 0; i < listnbt.size(); ++i) {
			ItemStack itemstack = ItemStack.of(listnbt.getCompound(i));
			if (!itemstack.isEmpty()) {
				this.inventory.addItem(itemstack);
			}
		}

		this.setHunt(p_29541_.getBoolean("Hunt"));
		this.setHuntLeader(p_29541_.getBoolean("HuntLeader"));
		this.setHuntTime(p_29541_.getInt("HuntTime"));
		this.setHoldTime(p_29541_.getInt("HoldTime"));
		this.setTrade(p_29541_.getBoolean("Trade"));
	}

	public void addAdditionalSaveData(CompoundTag p_29548_) {
		super.addAdditionalSaveData(p_29548_);
		this.addPersistentAngerSaveData(p_29548_);
		if (this.homeTarget != null) {
			p_29548_.put("HomeTarget", NbtUtils.writeBlockPos(this.homeTarget));
		}
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
		p_29548_.putInt("HuntTime", huntTime);
		p_29548_.putInt("HoldTime", holdTime);
		p_29548_.putBoolean("Trade", this.isTrade());
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	public void setRemainingPersistentAngerTime(int p_29543_) {
		this.remainingPersistentAngerTime = p_29543_;
	}

	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	public void setPersistentAngerTarget(@Nullable UUID p_29539_) {
		this.persistentAngerTarget = p_29539_;
	}

	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, MobSpawnType p_29535_, @Nullable SpawnGroupData p_29536_, @Nullable CompoundTag p_29537_) {
		if (p_29536_ == null) {

			if (p_29535_ == MobSpawnType.PATROL) {
				this.setHuntLeader(true);
				p_29536_ = new YetiGroupData(true, 0);
			} else {
				p_29536_ = new YetiGroupData(false, 1F);
			}
		}
		this.inventory.addItem(new ItemStack(Items.SALMON, 4));
		if (p_29535_ == MobSpawnType.STRUCTURE) {
			this.homeTarget = this.blockPosition();
		}

		if (p_29536_ instanceof YetiGroupData yetiGroupData) {
			this.setHunt(yetiGroupData.isHunt);
		}

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

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.YETI.get().create(p_146743_);
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}

	public class YetiMeleeAttackGoal extends MeleeAttackGoal {
		public YetiMeleeAttackGoal() {
			super(Yeti.this, 1.25D, true);
		}

		protected double getAttackReachSqr(LivingEntity p_29587_) {
			return 3.0F + p_29587_.getBbWidth();
		}
	}

	class YetiPanicGoal extends PanicGoal {
		public YetiPanicGoal() {
			super(Yeti.this, 2.0D);
		}

		public boolean canUse() {
			return (Yeti.this.isBaby() || Yeti.this.isOnFire() || Yeti.this.isPanic()) && super.canUse();
		}

		@Override
		protected boolean shouldPanic() {
			return super.shouldPanic() || Yeti.this.isPanic();
		}

		@Override
		public void stop() {
			super.stop();
			Yeti.this.setPanic(false);
		}
	}

	class YetiHurtByTargetGoal extends HurtByTargetGoal {
		public YetiHurtByTargetGoal() {
			super(Yeti.this);
		}

		public void start() {
			super.start();
			if (Yeti.this.isBaby()) {
				this.alertOthers();
				this.stop();
			}

		}

		protected void alertOther(Mob p_29580_, LivingEntity p_29581_) {
			if (p_29580_ instanceof Yeti && !p_29580_.isBaby()) {
				super.alertOther(p_29580_, p_29581_);
			}

		}
	}

	public static class YetiTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		YetiTargetGoal(Yeti p_27966_, Class<T> tClass) {
			super(p_27966_, tClass, 10, true, false, null);
		}

		public boolean canUse() {
			return this.canTarget() && super.canUse();
		}

		public boolean canContinueToUse() {
			boolean flag = this.canTarget();
			if (flag && this.mob.getTarget() != null) {
                return super.canContinueToUse();
            } else {
                this.targetMob = null;
                return false;
            }
        }

        private boolean canTarget() {
            Yeti yeti = (Yeti) this.mob;
            return !yeti.isAngry() && !yeti.isBaby();
        }
    }



    public static class MoveToGoal extends Goal {
        final Yeti yeti;
        final double stopDistance;
        final double speedModifier;

        public MoveToGoal(Yeti p_i50459_2_, double p_i50459_3_, double p_i50459_5_) {
            this.yeti = p_i50459_2_;
            this.stopDistance = p_i50459_3_;
            this.speedModifier = p_i50459_5_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

		public void stop() {
            this.yeti.navigation.stop();
		}

		public boolean canUse() {
			BlockPos blockpos = this.yeti.homeTarget;

			return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
		}

		public void tick() {
			BlockPos blockpos = this.yeti.homeTarget;
            if (blockpos != null && this.yeti.navigation.isDone()) {
                if (this.isTooFarAway(blockpos, 10.0D)) {
                    Vec3 vector3d = (new Vec3((double) blockpos.getX() - this.yeti.getX(), (double) blockpos.getY() - this.yeti.getY(), (double) blockpos.getZ() - this.yeti.getZ())).normalize();
                    Vec3 vector3d1 = vector3d.scale(10.0D).add(this.yeti.getX(), this.yeti.getY(), this.yeti.getZ());
                    this.yeti.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
                } else {
					this.yeti.navigation.moveTo((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos p_220846_1_, double p_220846_2_) {
			return !p_220846_1_.closerThan(this.yeti.blockPosition(), p_220846_2_);
		}
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
