package baguchan.frostrealm.entity;

import bagu_chan.bagus_lib.entity.goal.TimeConditionGoal;
import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.entity.path.FrostPathNavigation;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Marmot extends FrostAnimal {
	public static final Ingredient FOOD_ITEMS = Ingredient.of(ItemTags.SMALL_FLOWERS);
	private static final UniformInt TIME_BETWEEN_STANDS = UniformInt.of(300, 600);
	private static final UniformInt TIME_BETWEEN_STANDS_COOLDOWN = UniformInt.of(600, 1200);


	private static final EntityDataAccessor<Boolean> IS_STANDING = SynchedEntityData.defineId(Marmot.class, EntityDataSerializers.BOOLEAN);

	public Marmot(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.3F));
		this.goalSelector.addGoal(2, new SeekShelterEvenBlizzardGoal(this, 1.25D));
		this.goalSelector.addGoal(3, new StandingGoal(this, TIME_BETWEEN_STANDS_COOLDOWN, TIME_BETWEEN_STANDS));
		//this.goalSelector.addGoal(2, new BreedGoal(this, 0.95D));
		//this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.1F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
	}

	@Override
	public boolean isFood(ItemStack p_27600_) {
		return FOOD_ITEMS.test(p_27600_);
	}

	@Override
	protected PathNavigation createNavigation(Level p_33348_) {
		return new FrostPathNavigation(this, p_33348_);
	}

	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(IS_STANDING, false);
	}

	public void setStanding(boolean standing) {
		this.entityData.set(IS_STANDING, standing);
	}

	public boolean isStanding() {
		return this.entityData.get(IS_STANDING);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return this.isStanding() ? FrostSounds.MARMOT_IDLE.get() : super.getAmbientSound();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return FrostSounds.MARMOT_HURT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return FrostSounds.MARMOT_DEATH.get();
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.MARMOT.get().create(p_146743_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public static class StandingGoal extends TimeConditionGoal {
		public final Marmot marmot;

		public StandingGoal(Marmot marmot, UniformInt uniformInt, UniformInt uniformInt2) {
			super(marmot, uniformInt, uniformInt2);
			this.marmot = marmot;
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean isMatchCondition() {
			return this.marmot.getTarget() == null;
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
