package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class Marmot extends Animal {
	private static final EntityDataAccessor<Boolean> IS_STANDING = SynchedEntityData.defineId(Marmot.class, EntityDataSerializers.BOOLEAN);

	public Marmot(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.3F));
		this.goalSelector.addGoal(2, new StandingGoal(this));
		//this.goalSelector.addGoal(2, new BreedGoal(this, 0.95D));
		//this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.1F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_STANDING, false);
	}

	public void setStanding(boolean standing) {
		this.entityData.set(IS_STANDING, standing);
	}

	public boolean isStanding() {
		return this.entityData.get(IS_STANDING);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.MARMOT.get().create(p_146743_);
	}

	public static boolean checkMarmotSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK.get()) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public class StandingGoal extends Goal {
		public final Marmot marmot;
		protected int cooldown;

		public StandingGoal(Marmot marmot) {
			this.marmot = marmot;
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			if (cooldown > 300) {
				this.cooldown = 0;
				return this.marmot.getTarget() == null && this.marmot.random.nextInt(5) == 0;
			} else {
				++this.cooldown;
				return false;
			}
		}

		@Override
		public boolean canContinueToUse() {
			return this.marmot.random.nextInt(400) != 0;
		}

		@Override
		public void start() {
			super.start();
			this.marmot.setStanding(true);
			this.marmot.getNavigation().stop();
		}

		@Override
		public void stop() {
			super.stop();
			this.marmot.setStanding(false);
		}
	}
}
