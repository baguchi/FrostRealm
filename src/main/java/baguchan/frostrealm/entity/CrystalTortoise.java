package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;

public class CrystalTortoise extends Animal {
	public static final Ingredient FOOD_ITEMS = Ingredient.of(FrostBlocks.COLD_GRASS.get().asItem());

	public CrystalTortoise(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.moveControl = new CrystalTortoiseMoveControl(this);
		this.maxUpStep = 1.0F;
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25F));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.95D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
	}

	public static boolean checkTortoiseSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 4.0D);
	}

	@Override
	public boolean isFood(ItemStack p_27600_) {
		return FOOD_ITEMS.test(p_27600_);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.CRYSTAL_TORTOISE.get().create(p_146743_);
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return !this.isInWater() && this.onGround && !this.isBaby() ? SoundEvents.TURTLE_AMBIENT_LAND : super.getAmbientSound();
	}


	@Nullable
	protected SoundEvent getHurtSound(DamageSource p_30202_) {
		return this.isBaby() ? SoundEvents.TURTLE_HURT_BABY : SoundEvents.TURTLE_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return this.isBaby() ? SoundEvents.TURTLE_DEATH_BABY : SoundEvents.TURTLE_DEATH;
	}

	protected SoundEvent getSwimSound() {
		return SoundEvents.TURTLE_SWIM;
	}

	protected void playStepSound(BlockPos p_30173_, BlockState p_30174_) {
		SoundEvent soundevent = this.isBaby() ? SoundEvents.TURTLE_SHAMBLE_BABY : SoundEvents.TURTLE_SHAMBLE;
		this.playSound(soundevent, 0.15F, 1.0F);
	}

	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		return false;
	}

	public MobType getMobType() {
		return MobType.WATER;
	}

	public int getAmbientSoundInterval() {
		return 200;
	}

	public float getScale() {
		return this.isBaby() ? 0.3F : 1.0F;
	}

	protected PathNavigation createNavigation(Level p_30171_) {
		return new CrystalTortoisePathNavigation(this, p_30171_);
	}

	public void travel(Vec3 p_30218_) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(0.1F, p_30218_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		} else {
			super.travel(p_30218_);
		}

	}

	static class CrystalTortoiseMoveControl extends MoveControl {
		private final CrystalTortoise turtle;

		CrystalTortoiseMoveControl(CrystalTortoise p_30286_) {
			super(p_30286_);
			this.turtle = p_30286_;
		}

		private void updateSpeed() {
			if (this.turtle.isInWater()) {
				if (this.turtle.isBaby()) {
					this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 3.0F, 0.035F));
				} else {
					this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 3.0F, 0.05F));
				}
			} else if (this.turtle.onGround) {
				this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.1F));
			}

		}

		public void tick() {
			this.updateSpeed();
			if (this.operation == MoveControl.Operation.MOVE_TO && !this.turtle.getNavigation().isDone()) {
				double d0 = this.wantedX - this.turtle.getX();
				double d1 = this.wantedY - this.turtle.getY();
				double d2 = this.wantedZ - this.turtle.getZ();
				double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				d1 = d1 / d3;
				float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
				this.turtle.setYRot(this.rotlerp(this.turtle.getYRot(), f, 90.0F));
				this.turtle.yBodyRot = this.turtle.getYRot();
				float f1 = (float) (this.speedModifier * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
				this.turtle.setSpeed(Mth.lerp(0.125F, this.turtle.getSpeed(), f1));
				this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, (double) this.turtle.getSpeed() * d1 * 0.1D, 0.0D));
			} else {
				this.turtle.setSpeed(0.0F);
			}
		}
	}

	static class CrystalTortoisePathNavigation extends WaterBoundPathNavigation {
		CrystalTortoisePathNavigation(CrystalTortoise p_30294_, Level p_30295_) {
			super(p_30294_, p_30295_);
		}

		protected boolean canUpdatePath() {
			return true;
		}

		protected PathFinder createPathFinder(int p_30298_) {
			this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
			this.nodeEvaluator.setCanOpenDoors(false);
			this.nodeEvaluator.setCanPassDoors(true);
			return new PathFinder(this.nodeEvaluator, p_30298_);
		}

		public boolean isStableDestination(BlockPos p_30300_) {
			return !this.level.getBlockState(p_30300_.below()).isAir() || this.level.getBlockState(p_30300_).is(Blocks.WATER);
		}
	}
}
