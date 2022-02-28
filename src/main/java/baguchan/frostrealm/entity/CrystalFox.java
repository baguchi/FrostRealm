package baguchan.frostrealm.entity;

import baguchan.frostrealm.api.animation.Animation;
import baguchan.frostrealm.api.animation.IAnimatable;
import baguchan.frostrealm.block.BearBerryBushBlock;
import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class CrystalFox extends Animal implements IAnimatable {
	private static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIMATION_TICK = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.INT);

	public static final Animation EAT_ANIMATION = Animation.create(40);

	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CrystalFox.class, EntityDataSerializers.OPTIONAL_UUID);

	public static final Predicate<LivingEntity> FROST_PREY_SELECTOR = (p_30437_) -> {
		EntityType<?> entitytype = p_30437_.getType();
		return entitytype == FrostEntities.SNOWPILE_QUAIL;
	};
	private static final Predicate<Entity> AVOID_PLAYERS = (p_28463_) -> {
		return !p_28463_.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(p_28463_);
	};

	public static final Ingredient FOOD_ITEMS = Ingredient.of(FrostItems.BEARBERRY.asItem());


	public CrystalFox(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
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
		this.goalSelector.addGoal(5, new FoxMeleeAttackGoal((double) 1.2F, true));
		this.goalSelector.addGoal(6, new FoxEatBerriesGoal(1.25D, 8, 4));
		this.goalSelector.addGoal(7, new SeekShelterEvenBlizzardGoal(this, 1.25D, true));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Animal.class, false, FROST_PREY_SELECTOR));

	}

	public static boolean checkCrystalFoxSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected PathNavigation createNavigation(Level p_33348_) {
		return new FrostPathNavigation(this, p_33348_);
	}


	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANIMATION_ID, -1);
		this.entityData.define(ANIMATION_TICK, 0);
		this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
		this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
	}

	List<UUID> getTrustedUUIDs() {
		List<UUID> list = Lists.newArrayList();
		list.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse((UUID) null));
		list.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse((UUID) null));
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

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.CRYSTAL_FOX.create(p_146743_);
	}

	@Override
	public int getAnimationTick() {
		return this.entityData.get(ANIMATION_TICK);
	}

	@Override
	public void setAnimationTick(int tick) {
		this.entityData.set(ANIMATION_TICK, tick);
	}

	@Override
	public Animation getAnimation() {
		int index = this.entityData.get(ANIMATION_ID);
		if (index < 0) {
			return NO_ANIMATION;
		} else {
			return this.getAnimations()[index];
		}
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{
				EAT_ANIMATION
		};
	}

	@Override
	public void setAnimation(Animation animation) {
		this.entityData.set(ANIMATION_ID, ArrayUtils.indexOf(this.getAnimations(), animation));
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
			return blockstate.is(FrostBlocks.BEARBERRY_BUSH) && blockstate.getValue(BearBerryBushBlock.AGE) > 2;
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
				if (blockstate.is(FrostBlocks.BEARBERRY_BUSH)) {
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
				CrystalFox.this.setAnimation(CrystalFox.EAT_ANIMATION);
				CrystalFox.this.heal(1);
				--j;
			}

			if (j > 0) {
				Block.popResource(CrystalFox.this.level, this.blockPos, new ItemStack(FrostItems.BEARBERRY, j));
			}

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
