package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.block.SnowPileQuailEggBlock;
import baguchan.frostrealm.entity.IHasEgg;
import baguchan.frostrealm.entity.goal.BreedAndEggGoal;
import baguchan.frostrealm.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SnowPileQuail extends FrostAnimal implements IHasEgg {
	private static final EntityDimensions BABY_DIMENSIONS = FrostEntities.SNOWPILE_QUAIL.get().getDimensions().scale(0.5F).withEyeHeight(0.2F);
	private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(SnowPileQuail.class, EntityDataSerializers.BOOLEAN);

	private static final Ingredient FOOD_ITEMS = Ingredient.of(FrostTags.Items.SNOWPILE_FOODS);

	@Nullable
	private BlockPos homeTarget;

	private int ticksSinceEaten;

	public SnowPileQuail(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
		this.setCanPickUpLoot(true);
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_316516_) {
		return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(p_316516_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, CrystalFox.class, 8.0F, 1.55D, 1.45D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolfflue.class, 8.0F, 1.55D, 1.45D, (p_28590_) -> {
			return !((Wolfflue) p_28590_).isTame();
		}));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Ferret.class, 8.0F, 1.55D, 1.45D, (p_28590_) -> {
			return !((Ferret) p_28590_).isTame();
		}));
        this.goalSelector.addGoal(4, new BreedAndEggGoal<>(this, 1.0D));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new MoveToGoal(this, 8.0D, 1.1D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
	}

	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ANGRY, false);
		builder.define(HAS_EGG, false);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_) {
		this.populateDefaultEquipmentSlots(p_146746_.getRandom(), p_146747_);
		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource p_218171_, DifficultyInstance p_218172_) {
		float f = p_218171_.nextFloat();
		ItemStack itemstack;
		if (f < 0.5F) {
			if (f < 0.8F) {
				itemstack = new ItemStack(FrostItems.YETI_FUR.get());
			} else {
				itemstack = new ItemStack(Items.STICK);
			}

			this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return FrostSounds.SNOWPILE_QUAIL_IDLE.get();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return FrostSounds.SNOWPILE_QUAIL_HURT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return FrostSounds.SNOWPILE_QUAIL_DEATH.get();
	}

	@Override
	public void aiStep() {
		if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
			++this.ticksSinceEaten;
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (this.canEat(itemstack)) {
				if (this.ticksSinceEaten > 600) {
					ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
					this.heal(2);
					if (!itemstack1.isEmpty()) {
						this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
					}

					this.ticksSinceEaten = 0;
				} else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
					this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
					this.level().broadcastEntityEvent(this, (byte) 45);
				}
			}
		}

		if (this.isSleeping() || this.isImmobile()) {
			this.jumping = false;
			this.xxa = 0.0F;
			this.zza = 0.0F;
		}

		super.aiStep();

		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0D) {
			this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
		}

		if (this.isAlive() && this.hasEgg() && this.getTarget() == null) {
			BlockPos blockpos = this.blockPosition();
			if (SnowPileQuailEggBlock.onDirt(this.level(), blockpos)) {
				level().playSound(null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level().random.nextFloat() * 0.2F);
				level().setBlock(blockpos, FrostBlocks.SNOWPILE_QUAIL_EGG.get().defaultBlockState().setValue(SnowPileQuailEggBlock.EGGS, Integer.valueOf(this.random.nextInt(1) + 1)), 3);
				this.setHasEgg(false);
				this.setHomeTarget(blockpos);
				this.setAge(2400);
			}
		}

		if (this.homeTarget != null && !this.level().getBlockState(this.homeTarget).is(FrostBlocks.SNOWPILE_QUAIL_EGG.get())) {
			this.setHomeTarget(null);
		}
	}

	protected void pickUpItem(ItemEntity p_28514_) {
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
		if (!p_28602_.isEmpty() && !this.level().isClientSide) {
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

	public boolean canHoldItem(ItemStack p_28578_) {
		Item item = p_28578_.getItem();
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		return itemstack.isEmpty() || this.ticksSinceEaten > 0 && p_28578_.getFoodProperties(this) != null && !p_28578_.is(ItemTags.MEAT) && itemstack.getFoodProperties(this) == null;
	}

	private boolean canEat(ItemStack p_28598_) {
		return p_28598_.getFoodProperties(this) != null && !p_28598_.is(ItemTags.MEAT) && this.getTarget() == null && this.onGround() && !this.isSleeping();
	}

	@Override
	public boolean isFood(ItemStack p_28271_) {
		return FOOD_ITEMS.test(p_28271_);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.homeTarget != null) {
			compoundTag.put("HomeTarget", NbtUtils.writeBlockPos(this.homeTarget));
		}
		compoundTag.putBoolean("HasEgg", hasEgg());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("HomeTarget")) {
			this.homeTarget = NbtUtils.readBlockPos(compoundTag, "HomeTarget").orElse(null);
		}
		this.setHasEgg(compoundTag.getBoolean("HasEgg"));
	}

	public void setHomeTarget(@Nullable BlockPos pos) {
		this.homeTarget = pos;
	}

	@Nullable
	private BlockPos getHomeTarget() {
		return this.homeTarget;
	}

	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}

	public void setHasEgg(boolean hasEgg) {
		this.entityData.set(HAS_EGG, hasEgg);
	}


	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
		return FrostEntities.SNOWPILE_QUAIL.get().create(level);
	}

	@Override
	protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
	}


	class MoveToGoal extends Goal {
		final SnowPileQuail quail;
		final double stopDistance;
		final double speedModifier;

		MoveToGoal(SnowPileQuail snowPileQuail, double distance, double speed) {
			this.quail = snowPileQuail;
			this.stopDistance = distance;
			this.speedModifier = speed;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public void stop() {
			SnowPileQuail.this.navigation.stop();
		}

		public boolean canUse() {
			BlockPos blockpos = this.quail.getHomeTarget();

			double distance = this.quail.level().isDay() ? this.stopDistance : this.stopDistance / 4.0F;

			return blockpos != null && this.isTooFarAway(blockpos, distance);
		}

		public void tick() {
			BlockPos blockpos = this.quail.getHomeTarget();
			if (blockpos != null && SnowPileQuail.this.navigation.isDone()) {
				if (this.isTooFarAway(blockpos, 10.0D)) {
					Vec3 vector3d = (new Vec3((double) blockpos.getX() - this.quail.getX(), (double) blockpos.getY() - this.quail.getY(), (double) blockpos.getZ() - this.quail.getZ())).normalize();
					Vec3 vector3d1 = vector3d.scale(10.0D).add(this.quail.getX(), this.quail.getY(), this.quail.getZ());
					SnowPileQuail.this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
				} else {
					SnowPileQuail.this.navigation.moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos pos, double distance) {
			return !pos.closerThan(this.quail.blockPosition(), distance);
		}
	}
}
