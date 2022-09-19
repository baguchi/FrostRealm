package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.CreatureFollowParentGoal;
import baguchan.frostrealm.entity.goal.GetFoodGoal;
import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.item.ItemEntity;
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

public class Yeti extends AgeableMob implements NeutralMob {
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(30, 59);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	private final SimpleContainer inventory = new SimpleContainer(5);
	@Nullable
	private BlockPos homeTarget;
	private int huntTime;

	public static final Predicate<? super ItemEntity> ALLOWED_ITEMS = (p_213616_0_) -> {
		return p_213616_0_.getItem().getItem().getFoodProperties() != null && p_213616_0_.getItem().getItem() != Items.SPIDER_EYE && p_213616_0_.getItem().getItem() != Items.PUFFERFISH;
	};


	public Yeti(EntityType<? extends Yeti> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
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
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new Yeti.YetiHurtByTargetGoal());
		this.targetSelector.addGoal(3, new HuntTargetGoal(this));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	protected PathNavigation createNavigation(Level p_33348_) {
		return new FrostPathNavigation(this, p_33348_);
	}

	public static AttributeSupplier.Builder createAttributeMap() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.24F).add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ATTACK_DAMAGE, 6.0F);
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

	public void aiStep() {
		this.updateSwingTime();
		if (!this.level.isClientSide && this.isAlive()) {
			ItemStack mainhand = this.getItemInHand(InteractionHand.MAIN_HAND);

			if (!this.isUsingItem() && this.getItemInHand(InteractionHand.OFF_HAND).isEmpty() && (mainhand.getItem() == Items.BOW && this.getTarget() == null || mainhand.getItem() != Items.BOW)) {
				ItemStack food = ItemStack.EMPTY;

				if (this.getHealth() < this.getMaxHealth() && this.random.nextFloat() < 0.0025F) {
					food = this.findFood();
				}

				if (!food.isEmpty()) {
					this.setItemSlot(EquipmentSlot.OFFHAND, food);
					this.startUsingItem(InteractionHand.OFF_HAND);
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

	public void pickUpItem(ItemEntity p_175445_1_) {
		ItemStack itemstack = p_175445_1_.getItem();
		Item item = itemstack.getItem();
		if (this.wantsFood(itemstack)) {
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
			p_29536_ = new AgeableMob.AgeableMobGroupData(1.0F);
		}
		this.inventory.addItem(new ItemStack(Items.SALMON, 4));
		if (p_29535_ == MobSpawnType.STRUCTURE) {
			this.homeTarget = this.blockPosition();
		}

		return super.finalizeSpawn(p_29533_, p_29534_, p_29535_, p_29536_, p_29537_);
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
			return (Yeti.this.isBaby() || Yeti.this.isOnFire()) && super.canUse();
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

	static class HuntTargetGoal extends NearestAttackableTargetGoal<AbstractFish> {
		HuntTargetGoal(Yeti p_27966_) {
			super(p_27966_, AbstractFish.class, 10, true, false, null);
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
			return !yeti.isAngry() && !yeti.isBaby() && !yeti.isHunted();
		}
	}

	class MoveToGoal extends Goal {
		final Yeti yeti;
		final double stopDistance;
		final double speedModifier;

		MoveToGoal(Yeti p_i50459_2_, double p_i50459_3_, double p_i50459_5_) {
			this.yeti = p_i50459_2_;
			this.stopDistance = p_i50459_3_;
			this.speedModifier = p_i50459_5_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public void stop() {
			Yeti.this.navigation.stop();
		}

		public boolean canUse() {
			BlockPos blockpos = this.yeti.homeTarget;

			return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
		}

		public void tick() {
			BlockPos blockpos = this.yeti.homeTarget;
			if (blockpos != null && Yeti.this.navigation.isDone()) {
				if (this.isTooFarAway(blockpos, 10.0D)) {
					Vec3 vector3d = (new Vec3((double) blockpos.getX() - this.yeti.getX(), (double) blockpos.getY() - this.yeti.getY(), (double) blockpos.getZ() - this.yeti.getZ())).normalize();
					Vec3 vector3d1 = vector3d.scale(10.0D).add(this.yeti.getX(), this.yeti.getY(), this.yeti.getZ());
					Yeti.this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
				} else {
					Yeti.this.navigation.moveTo((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos p_220846_1_, double p_220846_2_) {
			return !p_220846_1_.closerThan(this.yeti.blockPosition(), p_220846_2_);
		}
	}
}
