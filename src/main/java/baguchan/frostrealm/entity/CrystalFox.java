package baguchan.frostrealm.entity;

import baguchan.frostrealm.block.BearBerryBushBlock;
import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class CrystalFox extends Animal implements IForgeShearable {
	private static final EntityDataAccessor<Boolean> SHEARABLE = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.BOOLEAN);


	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.OPTIONAL_UUID);

	public static final Predicate<LivingEntity> FROST_PREY_SELECTOR = (p_30437_) -> {
		EntityType<?> entitytype = p_30437_.getType();
		return entitytype == FrostEntities.SNOWPILE_QUAIL.get();
	};
	private static final Predicate<Entity> AVOID_PLAYERS = (p_28463_) -> {
		return !p_28463_.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(p_28463_);
	};

	public static final Ingredient FOOD_ITEMS = Ingredient.of(FrostItems.BEARBERRY.get().asItem());

	public final AnimationState eatAnimationState = new AnimationState();

	private int ticksSinceEaten;

	public CrystalFox(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
		this.setCanPickUpLoot(true);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.2F));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.95D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 16.0F, 1.6D, 1.4D, (p_28596_) -> {
			return AVOID_PLAYERS.test(p_28596_) && !this.trusts(p_28596_.getUUID());
		}));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 1.6D, 1.4D, (p_28590_) -> {
			return !((Wolf) p_28590_).isTame();
		}));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Kolossus.class, 16.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(5, new FoxMeleeAttackGoal(1.2F, true));
		this.goalSelector.addGoal(6, new FoxEatBerriesGoal(1.25D, 8, 4));
		this.goalSelector.addGoal(7, new SeekShelterEvenBlizzardGoal(this, 1.25D, true));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Animal.class, false, FROST_PREY_SELECTOR));

	}

	public static boolean checkCrystalFoxSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHEARABLE, true);
		this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
		this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
	}

	@Override
	public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
		return this.readyForShearing();
	}

	public boolean readyForShearing() {
		return this.isAlive() && !this.isBaby() && isShearableWithoutConditions();
	}

	public boolean isShearableWithoutConditions() {
		return this.entityData.get(SHEARABLE);
	}

	public void setShearable(boolean shear) {
		this.entityData.set(SHEARABLE, shear);
	}

	List<UUID> getTrustedUUIDs() {
		List<UUID> list = Lists.newArrayList();
		list.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse(null));
		list.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse(null));
		return list;
	}

	void addTrustedUUID(@javax.annotation.Nullable UUID p_28516_) {
		if (this.entityData.get(DATA_TRUSTED_ID_0).isPresent()) {
			this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(p_28516_));
		} else {
			this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(p_28516_));
		}

	}

	boolean trusts(UUID p_28530_) {
		return this.getTrustedUUIDs().contains(p_28530_);
	}

	@Override
	public boolean isFood(ItemStack p_27600_) {
		return FOOD_ITEMS.test(p_27600_);
	}

	public void aiStep() {
		if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
			++this.ticksSinceEaten;
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (this.canEat(itemstack)) {
				if (this.ticksSinceEaten > 600) {
					ItemStack itemstack1 = itemstack.finishUsingItem(this.level, this);
					this.heal(2);
					if (!itemstack1.isEmpty()) {
						this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
					}

					if (itemstack1.is(FrostItems.BEARBERRY.get())) {
						itemstack1.shrink(1);
						this.setShearable(true);
					}

					this.ticksSinceEaten = 0;
				} else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
					this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
					this.level.broadcastEntityEvent(this, (byte) 45);
				}
			}

			LivingEntity livingentity = this.getTarget();
		}

		if (this.isSleeping() || this.isImmobile()) {
			this.jumping = false;
			this.xxa = 0.0F;
			this.zza = 0.0F;
		}

		super.aiStep();
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
		if (!p_28602_.isEmpty() && !this.level.isClientSide) {
			ItemEntity itementity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, p_28602_);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this.getUUID());
			this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
			this.level.addFreshEntity(itementity);
		}
	}

	private void dropItemStack(ItemStack p_28606_) {
		ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), p_28606_);
		this.level.addFreshEntity(itementity);
	}

	public boolean canHoldItem(ItemStack p_28578_) {
		Item item = p_28578_.getItem();
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		return itemstack.isEmpty() || this.ticksSinceEaten > 0 && (item.isEdible() || item == FrostItems.BEARBERRY.get() && item != p_28578_.getItem()) && !itemstack.getItem().isEdible();
	}

	private boolean canEat(ItemStack p_28598_) {
		return (p_28598_.getItem().isEdible() || p_28598_.is(FrostItems.BEARBERRY.get())) && this.getTarget() == null && this.onGround && !this.isSleeping();
	}

	@javax.annotation.Nonnull
	@Override
	public java.util.List<ItemStack> onSheared(@javax.annotation.Nullable Player player, @javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
		if (player == null || this.trusts(player.getUUID())) {
			world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
			this.gameEvent(GameEvent.SHEAR, player);
			if (!world.isClientSide) {
				this.setShearable(false);
				int i = 1 + this.random.nextInt(3);

				java.util.List<ItemStack> items = new java.util.ArrayList<>();
				for (int j = 0; j < i; ++j) {

					items.add(new ItemStack(FrostItems.CRYONITE.get()));
				}
				return items;
			}
		} else {
			if (player != null) {
				player.hurt(DamageSource.thorns(this), 2.0F);
				player.getCooldowns().addCooldown(item.getItem(), 80);
			}
		}
		return java.util.Collections.emptyList();
	}

	public boolean hurt(DamageSource p_32820_, float p_32821_) {
		if (this.isShearableWithoutConditions()) {
			if (!p_32820_.isMagic() && p_32820_.getDirectEntity() instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) p_32820_.getDirectEntity();
				if (!p_32820_.isExplosion()) {
					livingentity.hurt(DamageSource.thorns(this), 2.0F);
				}
			}
		}

		return super.hurt(p_32820_, p_32821_);
	}

	@Nullable
	@Override
	@javax.annotation.Nullable
	protected SoundEvent getAmbientSound() {
		if (this.isSleeping()) {
			return SoundEvents.FOX_SLEEP;
		} else {
			if (!this.level.isDay() && this.random.nextFloat() < 0.1F) {
				List<Player> list = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(16.0D, 16.0D, 16.0D), EntitySelector.NO_SPECTATORS);
				if (list.isEmpty()) {
					return SoundEvents.FOX_SCREECH;
				}
			}

			return SoundEvents.FOX_AMBIENT;
		}
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return SoundEvents.FOX_HURT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.FOX_DEATH;
	}

	@Override
	public SoundEvent getEatingSound(ItemStack p_21202_) {
		return SoundEvents.FOX_EAT;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.CRYSTAL_FOX.get().create(p_146743_);
	}

	public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Item item = itemstack.getItem();
		if (this.trusts(p_30412_.getUUID())) {
			if (this.isFood(itemstack) && (this.getHealth() < this.getMaxHealth() || !this.isShearableWithoutConditions())) {
				if (!p_30412_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}
				if (!this.level.isClientSide()) {
					if (!this.isShearableWithoutConditions()) {
						if (this.random.nextInt(3) == 0) {

							this.setShearable(true);

						}
						this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
						return InteractionResult.SUCCESS;
					}
				}

				this.level.broadcastEntityEvent(this, (byte) 5);
				this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
				this.heal(item.getFoodProperties() != null ? (float) item.getFoodProperties().getNutrition() : 1);
				this.gameEvent(GameEvent.ENTITY_INTERACT, this);
				return InteractionResult.SUCCESS;
			}
		} else if (FOOD_ITEMS.test(itemstack) && this.getTarget() == null) {
			if (!p_30412_.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(5) == 0) {
				this.addTrustedUUID(p_30412_.getUUID());
				this.level.broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level.broadcastEntityEvent(this, (byte) 6);
			}

			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(p_30412_, p_30413_);
	}

	protected void spawnTamingParticles(boolean p_21835_) {
		ParticleOptions particleoptions = ParticleTypes.HEART;
		if (!p_21835_) {
			particleoptions = ParticleTypes.SMOKE;
		}

		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}

	}

	public void handleEntityEvent(byte p_21807_) {
		if (p_21807_ == 5) {
			this.eatAnimationState.start(this.tickCount);
		} else if (p_21807_ == 7) {
			this.spawnTamingParticles(true);
		} else if (p_21807_ == 6) {
			this.spawnTamingParticles(false);
		} else {
			super.handleEntityEvent(p_21807_);
		}

	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_27576_) {
		super.readAdditionalSaveData(p_27576_);
		ListTag listtag = p_27576_.getList("Trusted", 11);

		for (int i = 0; i < listtag.size(); ++i) {
			this.addTrustedUUID(NbtUtils.loadUUID(listtag.get(i)));
		}

		this.setShearable(p_27576_.getBoolean("Shearable"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_27587_) {
		super.addAdditionalSaveData(p_27587_);
		List<UUID> list = this.getTrustedUUIDs();
		ListTag listtag = new ListTag();

		for (UUID uuid : list) {
			if (uuid != null) {
				listtag.add(NbtUtils.createUUID(uuid));
			}
		}

		p_27587_.put("Trusted", listtag);
		p_27587_.putBoolean("Shearable", this.isShearableWithoutConditions());
	}

	protected int calculateFallDamage(float p_28545_, float p_28546_) {
		return Mth.ceil((p_28545_ - 5.0F) * p_28546_);
	}

	public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
		return p_27574_.getBlockState(p_27573_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) ? 10.0F : p_27574_.getPathfindingCostFromLightLevels(p_27573_) - 0.5F;
	}

	public class FoxEatBerriesGoal extends MoveToBlockGoal {
		private static final int WAIT_TICKS = 40;
		protected int ticksWaited;

		public FoxEatBerriesGoal(double p_28675_, int p_28676_, int p_28677_) {
			super(CrystalFox.this, p_28675_, p_28676_, p_28677_);
		}

		public double acceptedDistance() {
			return 2.0D;
		}

		public boolean shouldRecalculatePath() {
			return this.tryTicks % 100 == 0;
		}

		protected boolean isValidTarget(LevelReader p_28680_, BlockPos p_28681_) {
			BlockState blockstate = p_28680_.getBlockState(p_28681_);
			return blockstate.is(FrostBlocks.BEARBERRY_BUSH.get()) && blockstate.getValue(BearBerryBushBlock.AGE) > 2;
		}

		public void tick() {
			if (this.isReachedTarget()) {
				if (this.ticksWaited >= 40) {
					this.onReachedTarget();
				} else {
					++this.ticksWaited;
				}
			} else if (!this.isReachedTarget() && CrystalFox.this.random.nextFloat() < 0.05F) {
				CrystalFox.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
			}

			super.tick();
		}

		protected void onReachedTarget() {
			if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(CrystalFox.this.level, CrystalFox.this)) {
				BlockState blockstate = CrystalFox.this.level.getBlockState(this.blockPos);
				if (blockstate.is(FrostBlocks.BEARBERRY_BUSH.get())) {
					this.pickBearBerry(blockstate);
				}

			}
		}


		private void pickBearBerry(BlockState p_148929_) {
			int i = p_148929_.getValue(BearBerryBushBlock.AGE);
			p_148929_.setValue(BearBerryBushBlock.AGE, Integer.valueOf(1));
			int j = 1 + CrystalFox.this.level.random.nextInt(2) + (i == 3 ? 1 : 0);
			ItemStack itemstack = CrystalFox.this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (itemstack.isEmpty()) {
				CrystalFox.this.heal(1);
				CrystalFox.this.setShearable(true);
				--j;
			}

			if (j > 0) {
				Block.popResource(CrystalFox.this.level, this.blockPos, new ItemStack(FrostItems.BEARBERRY.get(), j));
			}

			CrystalFox.this.level.broadcastEntityEvent(CrystalFox.this, (byte) 5);

			CrystalFox.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
			CrystalFox.this.level.setBlock(this.blockPos, p_148929_.setValue(BearBerryBushBlock.AGE, Integer.valueOf(1)), 2);
		}

		public boolean canUse() {
			return !CrystalFox.this.isSleeping() && super.canUse();
		}

		public void start() {
			this.ticksWaited = 0;
			super.start();
		}
	}


	class FoxMeleeAttackGoal extends MeleeAttackGoal {
		public FoxMeleeAttackGoal(double p_28720_, boolean p_28721_) {
			super(CrystalFox.this, p_28720_, p_28721_);
		}

		protected void checkAndPerformAttack(LivingEntity p_28724_, double p_28725_) {
			double d0 = this.getAttackReachSqr(p_28724_);
			if (p_28725_ <= d0 && this.isTimeToAttack()) {
				this.resetAttackCooldown();
				this.mob.doHurtTarget(p_28724_);
				CrystalFox.this.playSound(SoundEvents.FOX_BITE, 1.0F, 1.0F);
			}

		}

		public void start() {
			super.start();
		}

		public boolean canUse() {
			return super.canUse();
		}
	}
}
